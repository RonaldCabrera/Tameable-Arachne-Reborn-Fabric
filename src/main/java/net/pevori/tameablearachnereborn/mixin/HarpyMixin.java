package net.pevori.tameablearachnereborn.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.pevori.tameablearachnereborn.entity.ModEntities;
import net.pevori.tameablearachnereborn.entity.custom.HarpyEntity;
import net.pevori.tameablearachnereborn.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChickenEntity.class)
public abstract class HarpyMixin extends AnimalEntity {
    protected HarpyMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        ChickenEntity chickenEntity = ((ChickenEntity)(Object)this);

        if(itemStack.getItem() == ModItems.MAGIC_CANDY){
            if (!player.getAbilities().creativeMode) {
                player.getStackInHand(hand).decrement(1);
            }

            HarpyEntity harpyEntity = ModEntities.HARPY.create(chickenEntity.world);
            harpyEntity.refreshPositionAndAngles(chickenEntity.getX(), chickenEntity.getY(), chickenEntity.getZ(), chickenEntity.getYaw(), chickenEntity.getPitch());
            harpyEntity.setAiDisabled(chickenEntity.isAiDisabled());

            if (chickenEntity.hasCustomName()) {
                harpyEntity.setCustomName(chickenEntity.getCustomName());
                harpyEntity.setCustomNameVisible(chickenEntity.isCustomNameVisible());
            }

            harpyEntity.setPersistent();
            harpyEntity.setOwnerUuid(player.getUuid());
            harpyEntity.setTamed(true);
            harpyEntity.setSit(true);
            chickenEntity.world.spawnEntity(harpyEntity);
            chickenEntity.discard();
        }

        return super.interactMob(player, hand);
    }
}
