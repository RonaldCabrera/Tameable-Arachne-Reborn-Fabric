package net.pevori.tameablearachnereborn.entity.client;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.pevori.tameablearachnereborn.TameableArachneReborn;
import net.pevori.tameablearachnereborn.entity.custom.ArachneEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import java.util.Random;

public class ArachneModel extends AnimatedGeoModel<ArachneEntity> {
    @Override
    public Identifier getModelResource(ArachneEntity object) {
        return new Identifier(TameableArachneReborn.MOD_ID, "geo/arachne.geo.json");
    }

    @Override
    public Identifier getTextureResource(ArachneEntity object) {
        return HarpyRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getAnimationResource(ArachneEntity animatable) {
        return new Identifier(TameableArachneReborn.MOD_ID, "animations/arachne.animation.json");
    }

    @Override
    public void setCustomAnimations(ArachneEntity animatable, int instanceId) {
        super.setCustomAnimations(animatable, instanceId);
    }

    @Override
    public void setLivingAnimations(ArachneEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
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
