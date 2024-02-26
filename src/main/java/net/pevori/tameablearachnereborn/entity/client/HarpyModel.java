package net.pevori.tameablearachnereborn.entity.client;

import net.minecraft.util.Identifier;
import net.pevori.tameablearachnereborn.TameableArachneReborn;
import net.pevori.tameablearachnereborn.entity.custom.HarpyEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class HarpyModel extends AnimatedGeoModel<HarpyEntity> {
    @Override
    public Identifier getModelResource(HarpyEntity object) {
        return new Identifier(TameableArachneReborn.MOD_ID, "geo/harpy.geo.json");
    }

    @Override
    public Identifier getTextureResource(HarpyEntity object) {
        return HarpyRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getAnimationResource(HarpyEntity animatable) {
        return new Identifier(TameableArachneReborn.MOD_ID, "animations/harpy.animation.json");
    }

    @Override
    public void setCustomAnimations(HarpyEntity animatable, int instanceId) {
        super.setCustomAnimations(animatable, instanceId);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(HarpyEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        IBone face = this.getAnimationProcessor().getBone("face");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }

        if(face != null){
            face.setHidden(entity.getBlinkTimer() == 0);
        }
    }
}
