package net.pevori.tameablearachnereborn.entity.client;

import com.google.common.collect.Maps;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.pevori.tameablearachnereborn.TameableArachneReborn;
import net.pevori.tameablearachnereborn.entity.custom.HarpyEntity;
import net.pevori.tameablearachnereborn.entity.variant.HarpyVariant;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

public class HarpyRenderer extends GeoEntityRenderer<HarpyEntity> {
    public static final Map<HarpyVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(HarpyVariant.class), (map) -> {
                map.put(HarpyVariant.SKY,
                        new Identifier(TameableArachneReborn.MOD_ID, "textures/entity/harpy/harpy_00.png"));
                map.put(HarpyVariant.PINK,
                        new Identifier(TameableArachneReborn.MOD_ID, "textures/entity/harpy/harpy_01.png"));
                map.put(HarpyVariant.LIGHTGREEN,
                        new Identifier(TameableArachneReborn.MOD_ID, "textures/entity/harpy/harpy_02.png"));
                map.put(HarpyVariant.YELLOW,
                        new Identifier(TameableArachneReborn.MOD_ID, "textures/entity/harpy/harpy_03.png"));
                map.put(HarpyVariant.MAGENTA,
                        new Identifier(TameableArachneReborn.MOD_ID, "textures/entity/harpy/harpy_04.png"));
                map.put(HarpyVariant.ORANGE,
                        new Identifier(TameableArachneReborn.MOD_ID, "textures/entity/harpy/harpy_05.png"));
                map.put(HarpyVariant.WHITE,
                        new Identifier(TameableArachneReborn.MOD_ID, "textures/entity/harpy/harpy_06.png"));
                map.put(HarpyVariant.LIGHTBLUE,
                        new Identifier(TameableArachneReborn.MOD_ID, "textures/entity/harpy/harpy_07.png"));
                map.put(HarpyVariant.BROWN,
                        new Identifier(TameableArachneReborn.MOD_ID, "textures/entity/harpy/harpy_08.png"));
                map.put(HarpyVariant.GREEN,
                        new Identifier(TameableArachneReborn.MOD_ID, "textures/entity/harpy/harpy_09.png"));
                map.put(HarpyVariant.GOLD,
                        new Identifier(TameableArachneReborn.MOD_ID, "textures/entity/harpy/harpy_10.png"));
                map.put(HarpyVariant.GRAY,
                        new Identifier(TameableArachneReborn.MOD_ID, "textures/entity/harpy/harpy_11.png"));
                map.put(HarpyVariant.RED,
                        new Identifier(TameableArachneReborn.MOD_ID, "textures/entity/harpy/harpy_12.png"));
                map.put(HarpyVariant.BLACK,
                        new Identifier(TameableArachneReborn.MOD_ID, "textures/entity/harpy/harpy_13.png"));
            });

    public HarpyRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new HarpyModel());
    }

    @Override
    public Identifier getTextureLocation(HarpyEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }
}