package net.pevori.tameablearachnereborn.entity.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LongDoorInteractGoal;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.pevori.tameablearachnereborn.screen.TameableArachneScreenHandler;

import javax.annotation.Nullable;

public class TameableArachneEntity extends TameableEntity implements ExtendedScreenHandlerFactory, InventoryChangedListener {
    protected static final String INVENTORY_KEY = "Humanoid_Animal_Inventory";
    protected static final String SLOT_KEY = "Humanoid_Animal_Inventory_Slot";
    protected Inventory inventory;

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
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (!this.world.isClient && this.isOwner(player) && player.isSneaking()) {
            player.openHandledScreen(this);
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
        NbtList nbtList = new NbtList();

        // Writes the rest of the inventory (slot 2 to 18th)
        for(int i = 1; i < this.inventory.size(); ++i) {
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
        NbtList nbtList = nbt.getList(INVENTORY_KEY, NbtElement.COMPOUND_TYPE);

        // Reads the rest of the inventory (slot 2 to 18th)
        for(int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            int j = nbtCompound.getByte(SLOT_KEY) & 255;
            if (j >= 1 && j < this.inventory.size()) {
                this.inventory.setStack(j, ItemStack.fromNbt(nbtCompound));
            }
        }
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        //Sends the entity id to pick it up later in the screenHandler and get back the entity reference.
        int entityId = getId();

        buf.writeInt(entityId);
    }

    public void setInventory(Inventory inventory){
        for(int i = 0; i < inventory.size(); i++){
            this.inventory.setStack(i, inventory.getStack(i));
        }
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        //We provide this to the screenHandler as our class Implements Inventory
        //Only the Server has the Inventory at the start, this will be synced to the client in the ScreenHandler
        return new TameableArachneScreenHandler(syncId, playerInventory, inventory);
    }

    @Override
    public Text getDisplayName() {
        return super.getDisplayName();
    }
}
