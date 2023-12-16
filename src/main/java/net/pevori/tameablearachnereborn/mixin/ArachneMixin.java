package net.pevori.tameablearachnereborn.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SpiderEntity.class)
public abstract class ArachneMixin extends MobEntity {
    protected ArachneMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        SpiderEntity spiderEntity = ((SpiderEntity)(Object)this);

        /*if(itemStack.getItem() == ModItems.KEMOMIMI_POTION){
            if (!player.getAbilities().creativeMode) {
                player.getStackInHand(hand).decrement(1);
            }

            QueenBunnyEntity queenBunnyEntity = ModEntities.QUEEN_BUNNY.create(chickenEntity.world);
            queenBunnyEntity.refreshPositionAndAngles(spiderEntity.getX(), spiderEntity.getY(), spiderEntity.getZ(), spiderEntity.getYaw(), spiderEntity.getPitch());
            queenBunnyEntity.setAiDisabled(chickenEntity.isAiDisabled());

            if (chickenEntity.hasCustomName()) {
                queenBunnyEntity.setCustomName(spiderEntity.getCustomName());
                queenBunnyEntity.setCustomNameVisible(spiderEntity.isCustomNameVisible());
            }

            queenBunnyEntity.setPersistent();
            queenBunnyEntity.setOwnerUuid(player.getUuid());
            queenBunnyEntity.setTamed(true);
            queenBunnyEntity.setSit(true);
            spiderEntity.world.spawnEntity(queenBunnyEntity);
            spiderEntity.discard();
        }*/

        return super.interactMob(player, hand);
    }
}
