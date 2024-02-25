package net.pevori.tameablearachnereborn.entity.client;

import com.google.common.collect.Maps;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.pevori.tameablearachnereborn.TameableArachneReborn;
import net.pevori.tameablearachnereborn.entity.custom.ArachneEntity;
import net.pevori.tameablearachnereborn.entity.custom.ArachneMediumEntity;
import net.pevori.tameablearachnereborn.entity.variant.ArachneMediumVariant;
import net.pevori.tameablearachnereborn.entity.variant.ArachneVariant;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

public class ArachneMediumRenderer extends GeoEntityRenderer<ArachneMediumEntity> {
    public static final Map<ArachneMediumVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(ArachneMediumVariant.class), (map) -> {
                map.put(ArachneMediumVariant.STRAWBERRY,
                        new Identifier(TameableArachneReborn.MOD_ID, "textures/entity/arachne_medium/arachne_medium_00.png"));
                map.put(ArachneMediumVariant.DANDELION,
                        new Identifier(TameableArachneReborn.MOD_ID, "textures/entity/arachne_medium/arachne_medium_01.png"));
            });

    public ArachneMediumRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ArachneMediumModel());
    }

    @Override
    public Identifier getTextureLocation(ArachneMediumEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }

    @Override
    public RenderLayer getRenderType(ArachneMediumEntity animatable, float partialTick, MatrixStack poseStack, @Nullable VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer, int packedLight, Identifier texture) {
        return RenderLayer.getEntityCutoutNoCull(this.getTextureLocation(animatable));
    }
}
