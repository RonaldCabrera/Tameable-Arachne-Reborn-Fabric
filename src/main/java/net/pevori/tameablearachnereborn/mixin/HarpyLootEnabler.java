package net.pevori.tameablearachnereborn.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface HarpyLootEnabler {
    @Invoker("dropLoot")
    public void TameableArachneReborn$dropLoot(DamageSource damageSource, boolean playerLoot);
}
