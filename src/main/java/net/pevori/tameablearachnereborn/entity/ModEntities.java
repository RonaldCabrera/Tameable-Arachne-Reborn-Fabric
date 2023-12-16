package net.pevori.tameablearachnereborn.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.pevori.tameablearachnereborn.TameableArachneReborn;
import net.pevori.tameablearachnereborn.entity.custom.HarpyEntity;

public class ModEntities {
    public static final float harpyHeight = 1.9f;
    public static final float harpyWidth = 0.5f;

    public static final EntityType<HarpyEntity> HARPY = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(TameableArachneReborn.MOD_ID, "harpy"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, HarpyEntity::new)
                    .dimensions(EntityDimensions.fixed(harpyWidth, harpyHeight)).build());
}
