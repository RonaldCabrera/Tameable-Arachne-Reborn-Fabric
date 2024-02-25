package net.pevori.tameablearachnereborn.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.pevori.tameablearachnereborn.TameableArachneReborn;
import net.pevori.tameablearachnereborn.entity.custom.ArachneEntity;
import net.pevori.tameablearachnereborn.entity.custom.ArachneMediumEntity;
import net.pevori.tameablearachnereborn.entity.custom.HarpyEntity;

public class ModEntities {
    public static final float harpyHeight = 1.9f;
    public static final float harpyWidth = 0.5f;
    public static final float arachneHeight = 1.4f;
    public static final float arachneWidth = 0.9f;
    public static final float arachneMediumHeight = 1.9f;
    public static final float arachneMediumWidth = 0.9f;

    public static final EntityType<HarpyEntity> HARPY = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(TameableArachneReborn.MOD_ID, "harpy"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, HarpyEntity::new)
                    .dimensions(EntityDimensions.fixed(harpyWidth, harpyHeight)).build());

    public static final EntityType<ArachneEntity> ARACHNE = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(TameableArachneReborn.MOD_ID, "arachne"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ArachneEntity::new)
                    .dimensions(EntityDimensions.fixed(arachneWidth, arachneHeight)).build());

    public static final EntityType<ArachneMediumEntity> ARACHNE_MEDIUM = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(TameableArachneReborn.MOD_ID, "arachne_medium"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ArachneMediumEntity::new)
                    .dimensions(EntityDimensions.fixed(arachneMediumWidth, arachneMediumHeight)).build());
}