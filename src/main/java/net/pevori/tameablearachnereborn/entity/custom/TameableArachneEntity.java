package net.pevori.tameablearachnereborn.entity.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LongDoorInteractGoal;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.pevori.tameablearachnereborn.TameableArachneReborn;
import net.pevori.tameablearachnereborn.config.TameableArachneRebornConfig;
import net.pevori.tameablearachnereborn.item.ModItems;
import net.pevori.tameablearachnereborn.screen.TameableArachneScreenHandler;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Random;

public class TameableArachneEntity extends TameableEntity implements IAnimatable, ExtendedScreenHandlerFactory, InventoryChangedListener {
    protected static final String INVENTORY_KEY = "Humanoid_Animal_Inventory";
    protected static final String SLOT_KEY = "Humanoid_Animal_Inventory_Slot";
    protected Inventory inventory;
    protected Item itemForTaming = Items.CHICKEN;
    protected Ingredient itemForHealing = Ingredient.ofItems(Items.CHICKEN, Items.BEEF, Items.PORKCHOP, Items.MUTTON);
    protected Item itemForVariant = ModItems.MAGIC_CANDY;
    protected int blinkTimer;

    protected static final TrackedData<Integer> ADD_HP = DataTracker.registerData(TameableArachneEntity.class,
            TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> ADD_ATTACK = DataTracker.registerData(TameableArachneEntity.class,
            TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> ADD_DEFENSE = DataTracker.registerData(TameableArachneEntity.class,
            TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> PROTECTION = DataTracker.registerData(TameableArachneEntity.class,
            TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> FIRE_PROTECTION = DataTracker.registerData(TameableArachneEntity.class,
            TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> FALL_PROTECTION = DataTracker.registerData(TameableArachneEntity.class,
            TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> BLAST_PROTECTION = DataTracker.registerData(TameableArachneEntity.class,
            TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> PROJECTILE_PROTECTION = DataTracker.registerData(TameableArachneEntity.class,
            TrackedDataHandlerRegistry.INTEGER);

    private AnimationFactory factory = new AnimationFactory(this);


    protected TameableArachneEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.inventory = new SimpleInventory(19);
    }

    @Override
    protected void initGoals() {
        // Allows pathfinding through doors
        ((MobNavigation) this.getNavigation()).canEnterOpenDoors();
        ((MobNavigation) this.getNavigation()).setCanPathThroughDoors(true);

        // Allows them to actually open doors to walk through them, just like villagers.
        this.goalSelector.add(1, new LongDoorInteractGoal(this, true));
    }

    @Override
    public void tick() {
        if (TameableArachneRebornConfig.autoHeal){
            if (this.age % TameableArachneRebornConfig.autoHealInterval == 0){
                this.heal(TameableArachneRebornConfig.autoHealValue);

                if (this.getHealth() > this.getMaxHealth())
                {
                    this.setHealth(this.getMaxHealth());
                }
            }
        }

        if (this.blinkTimer == 0) {
            this.blinkTimer = random.nextInt(75);
        } else {
            this.blinkTimer--;
        }

        super.tick();
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public boolean canAttackWithOwner(LivingEntity target, LivingEntity owner) {
        if (target instanceof CreeperEntity || target instanceof GhastEntity) {
            return false;
        }
        if (target instanceof TameableArachneEntity) {
            TameableArachneEntity tameableArachneEntity = (TameableArachneEntity) target;
            return !tameableArachneEntity.isTamed() || tameableArachneEntity.getOwner() != owner;
        }
        if (target instanceof PlayerEntity && owner instanceof PlayerEntity
                && !((PlayerEntity) owner).shouldDamagePlayer((PlayerEntity) target)) {
            return false;
        }
        if (target instanceof HorseEntity && ((HorseEntity) target).isTame()) {
            return false;
        }
        return !(target instanceof TameableEntity) || !((TameableEntity) target).isTamed();
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!this.isInvulnerableTo(source))
        {
            int protection = this.getProtection();
            int fireProtection = this.getFireProtection();
            int fallProtection = this.getFallProtection();
            int blastProtection = this.getBlastProtection();
            int projectileProtection = this.getProjectileProtection();

            if (amount >= 1.0F && protection > 0)
            {
                amount *= ((100F - protection) / 100F);
            }

            if (source.isFire() && amount >= 1.0F && fireProtection > 0)
            {
                amount *= ((100F - fireProtection) / 100F);
            }

            if (source.isFromFalling() && amount >= 1.0F && fallProtection > 0)
            {
                amount *= ((100F - fallProtection) / 100F);
            }

            if (source.isExplosive() && amount >= 1.0F && blastProtection > 0)
            {
                amount *= ((100F - blastProtection) / 100F);
            }

            if (source.isProjectile() && amount >= 1.0F && projectileProtection > 0)
            {
                amount *= ((100F - projectileProtection) / 100F);
            }

            if (amount < 1.0F)
            {
                return false;
            }

            Entity entity = source.getSource();
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof ArrowEntity))
            {
                amount = (amount + 1.0F) / 2.0F;
            }
        }

        return super.damage(source, amount);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();

        if (!this.world.isClient() && this.isOwner(player) && player.isSneaking()) {
            player.openHandledScreen(this);
            return ActionResult.SUCCESS;
        }

        if (itemStack.isOf(Items.BUCKET)) {
            player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
            ItemStack itemStack2 = ItemUsage.exchangeStack(itemStack, player, Items.MILK_BUCKET.getDefaultStack());
            player.setStackInHand(hand, itemStack2);
            return ActionResult.success(this.world.isClient);
        }

        if (this.canPowerUp()){
            if (item == Items.GOLD_INGOT){
                this.eat(player, hand, itemStack);
                this.lovePlayer(player);
                this.setAddHp(this.getAddHp() + 1);
                this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(this.getHpValue());

                this.emitGameEvent(GameEvent.ENTITY_INTERACT, this);

                return ActionResult.SUCCESS;
            }

            if (item == Items.DIAMOND){
                int addAttack = this.getAddAttack() + 1;

                this.eat(player, hand, itemStack);
                this.lovePlayer(player);
                this.setAddAttack(addAttack);
                this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(addAttack);

                this.emitGameEvent(GameEvent.ENTITY_INTERACT, this);

                return ActionResult.SUCCESS;
            }

            if (item == Items.IRON_INGOT){
                this.eat(player, hand, itemStack);
                this.lovePlayer(player);
                this.setAddDefense(this.getAddDefense() + 1);

                this.emitGameEvent(GameEvent.ENTITY_INTERACT, this);

                return ActionResult.SUCCESS;
            }
        }

        if (this.getAddHp() > 0 && item == Item.BLOCK_ITEMS.get(Blocks.GOLD_BLOCK)){
            this.setAddHp(this.getAddHp() - 1);
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(this.getHpValue());
            this.heal(0F);
            this.lovePlayer(player);

            return ActionResult.SUCCESS;
        }

        if (this.getAddAttack() > 0 && item == Item.BLOCK_ITEMS.get(Blocks.DIAMOND_BLOCK)){
            int addAttack = this.getAddAttack() - 1;

            this.setAddAttack(addAttack);
            this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(addAttack);
            this.lovePlayer(player);

            return ActionResult.SUCCESS;
        }

        if (this.getAddDefense() > 0 && item == Item.BLOCK_ITEMS.get(Blocks.IRON_BLOCK)){
            this.setAddDefense(this.getAddDefense() - 1);
            this.lovePlayer(player);

            return ActionResult.SUCCESS;
        }

        if (itemStack.hasEnchantments() && item != Items.ENCHANTED_BOOK){
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(itemStack);

            if(enchantments.containsKey(Enchantments.PROTECTION)){
                int level = enchantments.get(Enchantments.PROTECTION).intValue();

                if (this.canUpProtection()) {
                    int temp = getProtection() + (2 * level);
                    if (temp > TameableArachneRebornConfig.protectionLimit) {
                        temp = TameableArachneRebornConfig.protectionLimit;
                    }
                    setProtection(temp);
                }

                if (!player.getAbilities().creativeMode){
                    enchantments.remove(Enchantments.PROTECTION);
                    EnchantmentHelper.set(enchantments, itemStack);
                }
            }
            if(enchantments.containsKey(Enchantments.FIRE_PROTECTION)){
                int level = enchantments.get(Enchantments.FIRE_PROTECTION).intValue();
                TameableArachneReborn.LOGGER.info("Found Fire Prot");

                if (this.canUpFireProtection()) {
                    int temp = getFireProtection() + (2 * level);
                    if (temp > TameableArachneRebornConfig.fireProtectionLimit) {
                        temp = TameableArachneRebornConfig.fireProtectionLimit;
                    }

                    setFireProtection(temp);
                    if (!player.getAbilities().creativeMode){
                        enchantments.remove(Enchantments.FIRE_PROTECTION);
                        EnchantmentHelper.set(enchantments, itemStack);
                    }
                }
            }
            if(enchantments.containsKey(Enchantments.BLAST_PROTECTION)){
                int level = enchantments.get(Enchantments.BLAST_PROTECTION).intValue();

                if (this.canUpBlastProtection()) {
                    int temp = getBlastProtection() + (2 * level);
                    if (temp > TameableArachneRebornConfig.blastProtectionLimit) {
                        temp = TameableArachneRebornConfig.blastProtectionLimit;
                    }

                    setBlastProtection(temp);
                    if (!player.getAbilities().creativeMode){
                        enchantments.remove(Enchantments.BLAST_PROTECTION);
                        EnchantmentHelper.set(enchantments, itemStack);
                    }
                }
            }
            if(enchantments.containsKey(Enchantments.PROJECTILE_PROTECTION)){
                int level = enchantments.get(Enchantments.PROJECTILE_PROTECTION).intValue();

                if (this.canUpProjectileProtection()) {
                    int temp = getProjectileProtection() + (2 * level);
                    if (temp > TameableArachneRebornConfig.projectileProtectionLimit) {
                        temp = TameableArachneRebornConfig.projectileProtectionLimit;
                    }

                    setProjectileProtection(temp);
                    if (!player.getAbilities().creativeMode){
                        enchantments.remove(Enchantments.PROJECTILE_PROTECTION);
                        EnchantmentHelper.set(enchantments, itemStack);
                    }
                }
            }
            if(enchantments.containsKey(Enchantments.FEATHER_FALLING)){
                int level = enchantments.get(Enchantments.FEATHER_FALLING).intValue();

                if (this.canUpFallProtection()) {
                    int temp = getFallProtection() + (2 * level);
                    if (temp > TameableArachneRebornConfig.fallProtectionLimit) {
                        temp = TameableArachneRebornConfig.fallProtectionLimit;
                    }

                    setFallProtection(temp);
                    if (!player.getAbilities().creativeMode){
                        enchantments.remove(Enchantments.FEATHER_FALLING);
                        EnchantmentHelper.set(enchantments, itemStack);
                    }
                }
            }

            this.lovePlayer(player);
            return ActionResult.SUCCESS;
        }

        if (isTamed() && this.isOwner(player) && !player.isSneaking() && !this.world.isClient() && hand == Hand.MAIN_HAND) {
            setSit(!isSitting());
            return ActionResult.SUCCESS;
        }

        return super.interactMob(player, hand);
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        if (this.inventory != null) {
            for(int i = 0; i < this.inventory.size(); ++i) {
                ItemStack itemStack = this.inventory.getStack(i);
                if (!itemStack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemStack)) {
                    this.dropStack(itemStack);
                }
            }
        }
    }

    @Override
    public void onInventoryChanged(Inventory sender) {
        this.inventory = sender;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("isSitting", this.dataTracker.get(SITTING));
        nbt.putInt("Variant", this.getTypeVariant());

        nbt.putInt("AddHp", this.getAddHp());
        nbt.putInt("AddAttack", this.getAddAttack());
        nbt.putInt("AddDefense", this.getAddDefense());

        nbt.putInt("Protection", this.getProtection());
        nbt.putInt("FireProtection", this.getFireProtection());
        nbt.putInt("FallProtection", this.getFallProtection());
        nbt.putInt("BlastProtection", this.getBlastProtection());
        nbt.putInt("ProjectileProtection", this.getProjectileProtection());

        NbtList nbtList = new NbtList();
        for(int i = 0; i < this.inventory.size(); ++i) {
            ItemStack itemStack = this.inventory.getStack(i);
            if (!itemStack.isEmpty()) {
                NbtCompound nbtCompound = new NbtCompound();
                nbtCompound.putByte(SLOT_KEY, (byte)i);
                itemStack.writeNbt(nbtCompound);
                nbtList.add(nbtCompound);
            }
        }

        nbt.put(INVENTORY_KEY, nbtList);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(SITTING, nbt.getBoolean("isSitting"));
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));

        this.setAddHp(nbt.getInt("AddHp"));
        this.setAddAttack(nbt.getInt("AddAttack"));
        this.setAddDefense(nbt.getInt("AddDefense"));

        this.setProtection(nbt.getInt("Protection"));
        this.setFireProtection(nbt.getInt("FireProtection"));
        this.setFallProtection(nbt.getInt("FallProtection"));
        this.setBlastProtection(nbt.getInt("BlastProtection"));
        this.setProjectileProtection(nbt.getInt("ProjectileProtection"));

        NbtList nbtList = nbt.getList(INVENTORY_KEY, NbtElement.COMPOUND_TYPE);

        for(int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            int j = nbtCompound.getByte(SLOT_KEY) & 255;
            if (j >= 1 && j < this.inventory.size()) {
                this.inventory.setStack(j, ItemStack.fromNbt(nbtCompound));
            }
        }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();

        this.dataTracker.startTracking(SITTING, false);
        this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);

        this.dataTracker.startTracking(ADD_HP, 0);
        this.dataTracker.startTracking(ADD_ATTACK, 0);
        this.dataTracker.startTracking(ADD_DEFENSE, 0);

        this.dataTracker.startTracking(PROTECTION, 0);
        this.dataTracker.startTracking(FIRE_PROTECTION, 0);
        this.dataTracker.startTracking(FALL_PROTECTION, 0);
        this.dataTracker.startTracking(BLAST_PROTECTION, 0);
        this.dataTracker.startTracking(PROJECTILE_PROTECTION, 0);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        int entityId = getId();

        buf.writeInt(entityId);
        buf.writeInt(entityId);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new TameableArachneScreenHandler(syncId, playerInventory, inventory);
    }

    @Override
    public Text getDisplayName() {
        return super.getDisplayName();
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    public int getBlinkTimer(){
        return this.blinkTimer;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    //#region TAMEABLE ENTITY
    protected static final TrackedData<Boolean> SITTING = DataTracker.registerData(TameableArachneEntity.class,
            TrackedDataHandlerRegistry.BOOLEAN);

    public void setSit(boolean sitting) {
        this.dataTracker.set(SITTING, sitting);
        super.setSitting(sitting);
    }

    public boolean isSitting() {
        return this.dataTracker.get(SITTING);
    }

    @Override
    public AbstractTeam getScoreboardTeam() {
        return super.getScoreboardTeam();
    }

    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }
    //endregion

    //#region VARIANTS
    protected static final TrackedData<Integer> DATA_ID_TYPE_VARIANT = DataTracker.registerData(TameableArachneEntity.class,
            TrackedDataHandlerRegistry.INTEGER);

    protected int getTypeVariant() {
        return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
    }

    //#endregion

    //region Power Up Methods

    protected int getAddHp()
    {
        return this.dataTracker.get(ADD_HP);
    }

    protected void setAddHp(int healthPoints)
    {
        this.dataTracker.set(ADD_HP, healthPoints);
    }

    protected int getAddAttack()
    {
        return this.dataTracker.get(ADD_ATTACK);
    }

    protected void setAddAttack(int attack)
    {
        this.dataTracker.set(ADD_ATTACK, attack);
    }

    protected int getAddDefense()
    {
        return this.dataTracker.get(ADD_DEFENSE);
    }

    protected void setAddDefense(int defense)
    {
        this.dataTracker.set(ADD_DEFENSE, defense);
    }

    protected int getHpValue(){
        return (this.getAddHp() * TameableArachneRebornConfig.hpUp);
    }

    protected int getAttackValue(){
        int attack = this.getAddAttack();
        if (isNameBonus())
        {
            attack += TameableArachneRebornConfig.nameBonusValue;
        }
        return (attack * TameableArachneRebornConfig.attackUp);
    }

    protected int getDefenseValue(){
        int defense = this.getAddDefense();
        if (isNameBonus())
        {
            defense += TameableArachneRebornConfig.nameBonusValue;
        }

        return (defense * TameableArachneRebornConfig.defenseUp);
    }

    protected boolean canPowerUp(){
        int totalValue = this.getAddHp() + this.getAddAttack() + this.getAddDefense();
        return totalValue < TameableArachneRebornConfig.powerUpLimit;
    }

    protected boolean isNameBonus(){
        if (TameableArachneRebornConfig.nameBonus)
        {
            return this.hasCustomName();
        }

        return false;
    }

    protected int getProtection()
    {
        return this.dataTracker.get(PROTECTION);
    }

    protected void setProtection(int protection)
    {
        this.dataTracker.set(PROTECTION, protection);
    }

    protected int getFireProtection()
    {
        return this.dataTracker.get(FIRE_PROTECTION);
    }

    protected void setFireProtection(int fireProtection)
    {
        this.dataTracker.set(FIRE_PROTECTION, fireProtection);
    }

    protected int getFallProtection()
    {
        return this.dataTracker.get(FALL_PROTECTION);
    }

    protected void setFallProtection(int fallProtection)
    {
        this.dataTracker.set(FALL_PROTECTION, fallProtection);
    }

    protected int getBlastProtection()
    {
        return this.dataTracker.get(BLAST_PROTECTION);
    }

    protected void setBlastProtection(int blastProtection)
    {
        this.dataTracker.set(BLAST_PROTECTION, blastProtection);
    }

    protected int getProjectileProtection()
    {
        return this.dataTracker.get(PROJECTILE_PROTECTION);
    }

    protected void setProjectileProtection(int projectileProtection)
    {
        this.dataTracker.set(PROJECTILE_PROTECTION, projectileProtection);
    }

    protected boolean canUpProtection()
    {
        return getProtection() < TameableArachneRebornConfig.protectionLimit;
    }

    protected boolean canUpFireProtection()
    {
        return getFireProtection() < TameableArachneRebornConfig.fireProtectionLimit;
    }

    protected boolean canUpFallProtection()
    {
        return getFallProtection() < TameableArachneRebornConfig.fallProtectionLimit;
    }

    protected boolean canUpBlastProtection()
    {
        return getBlastProtection() < TameableArachneRebornConfig.blastProtectionLimit;
    }

    protected boolean canUpProjectileProtection()
    {
        return getProjectileProtection() < TameableArachneRebornConfig.projectileProtectionLimit;
    }

    public Text getFormattedStats(NumberFormat numberFormat){
        float hp = this.getHealth();
        float maxHp = this.getHpValue() / 2F;
        float attack = this.getAttackValue() / 2F;
        float defense = this.getDefenseValue() / 2F;
        int powerUps = this.getAddHp() + this.getAddAttack() + this.getAddDefense();

        return Text.literal("<Stats> \n HP:" + numberFormat.format(hp) + "/" + numberFormat.format(maxHp) + " Attack:" + numberFormat.format(attack) + " Defense:" + numberFormat.format(defense) + " PowerUp:" + powerUps);
    }

    public Text getFormattedProtectionStats(){
        return Text.literal("<Protections> \n Normal:" + getProtection() + " Fire:" + getFireProtection() + " Fall:" + getFallProtection() + " Blast:" + getBlastProtection() + " Projectile:" + getProjectileProtection());
    }

    //endregion
}
