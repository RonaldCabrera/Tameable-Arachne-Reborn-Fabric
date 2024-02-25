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
import net.pevori.tameablearachnereborn.entity.custom.HarpyEntity;
import net.pevori.tameablearachnereborn.entity.variant.ArachneVariant;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

public class ArachneRenderer extends GeoEntityRenderer<ArachneEntity> {
    public static final Map<ArachneVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(ArachneVariant.class), (map) -> {
                map.put(ArachneVariant.NOIRE,
                        new Identifier(TameableArachneReborn.MOD_ID, "textures/entity/arachne/arachne_00.png"));
                map.put(ArachneVariant.BLANC,
                        new Identifier(TameableArachneReborn.MOD_ID, "textures/entity/arachne/arachne_01.png"));
            });

    public ArachneRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ArachneModel());
    }

    @Override
    public Identifier getTextureLocation(ArachneEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }

    @Override
    public RenderLayer getRenderType(ArachneEntity animatable, float partialTick, MatrixStack poseStack, @Nullable VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer, int packedLight, Identifier texture) {
        return RenderLayer.getEntityCutoutNoCull(this.getTextureLocation(animatable));
    }
}
