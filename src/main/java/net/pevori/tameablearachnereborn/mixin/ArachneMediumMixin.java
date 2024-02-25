package net.pevori.tameablearachnereborn.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.pevori.tameablearachnereborn.entity.ModEntities;
import net.pevori.tameablearachnereborn.entity.custom.ArachneEntity;
import net.pevori.tameablearachnereborn.entity.variant.ArachneVariant;
import net.pevori.tameablearachnereborn.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class ArachneMediumMixin extends LivingEntity {

    protected ArachneMediumMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "interactMob", at = @At("HEAD"))
    private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> info) {
        if((Object) this instanceof SpiderEntity){
            ItemStack itemStack = player.getStackInHand(hand);
            SpiderEntity spiderEntity = ((SpiderEntity)(Object)this);

            if(itemStack.getItem() == ModItems.MAGIC_CANDY){
                if (!player.getAbilities().creativeMode) {
                    player.getStackInHand(hand).decrement(1);
                }

                ArachneEntity arachneEntity = ModEntities.ARACHNE.create(spiderEntity.world);
                spawnArachne(arachneEntity, spiderEntity, player);
            }
        }
    }

    public void spawnArachne(ArachneEntity arachneEntity, SpiderEntity spiderEntity, PlayerEntity player){
        arachneEntity.refreshPositionAndAngles(spiderEntity.getX(), spiderEntity.getY(), spiderEntity.getZ(), spiderEntity.getYaw(), spiderEntity.getPitch());
        arachneEntity.setAiDisabled(spiderEntity.isAiDisabled());

        if (spiderEntity.hasCustomName()) {
            arachneEntity.setCustomName(spiderEntity.getCustomName());
            arachneEntity.setCustomNameVisible(spiderEntity.isCustomNameVisible());
        }

        arachneEntity.setPersistent();
        //arachneEntity.setOwnerUuid(player.getUuid());
        //arachneEntity.setTamed(true);
        arachneEntity.setSit(true);

        ArachneVariant variant = Util.getRandom(ArachneVariant.values(), this.random);
        arachneEntity.setVariant(variant);

        spiderEntity.world.spawnEntity(arachneEntity);
        spiderEntity.discard();
        arachneEntity.lovePlayer(player);
    }
}
