package net.pevori.tameablearachnereborn.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.pevori.tameablearachnereborn.entity.ModEntities;
import net.pevori.tameablearachnereborn.entity.custom.HarpyEntity;
import net.pevori.tameablearachnereborn.entity.variant.HarpyVariant;
import net.pevori.tameablearachnereborn.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnimalEntity.class)
public abstract class HarpyMixin extends LivingEntity {
    protected HarpyMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "interactMob", at = @At("HEAD"))
    private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> info) {
        if((Object) this instanceof ChickenEntity){
            ItemStack itemStack = player.getStackInHand(hand);
            ChickenEntity chickenEntity = ((ChickenEntity)(Object)this);

            if(itemStack.getItem() == ModItems.MAGIC_CANDY){
                if (!player.getAbilities().creativeMode) {
                    player.getStackInHand(hand).decrement(1);
                }

                HarpyEntity harpyEntity = ModEntities.HARPY.create(chickenEntity.world);
                spawnHarpy(harpyEntity, chickenEntity, player);
            }
        }
    }

    public void spawnHarpy(HarpyEntity harpyEntity, ChickenEntity chickenEntity, PlayerEntity player){
        harpyEntity.refreshPositionAndAngles(chickenEntity.getX(), chickenEntity.getY(), chickenEntity.getZ(), chickenEntity.getYaw(), chickenEntity.getPitch());
        harpyEntity.setAiDisabled(chickenEntity.isAiDisabled());

        if (chickenEntity.hasCustomName()) {
            harpyEntity.setCustomName(chickenEntity.getCustomName());
            harpyEntity.setCustomNameVisible(chickenEntity.isCustomNameVisible());
        }

        harpyEntity.setPersistent();
        //harpyEntity.setOwnerUuid(player.getUuid());
        //harpyEntity.setTamed(true);
        harpyEntity.setSit(true);

        HarpyVariant variant = Util.getRandom(HarpyVariant.values(), this.random);
        harpyEntity.setVariant(variant);

        chickenEntity.world.spawnEntity(harpyEntity);
        chickenEntity.discard();
        harpyEntity.lovePlayer(player);
    }
}

