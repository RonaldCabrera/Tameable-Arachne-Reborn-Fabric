package net.pevori.tameablearachnereborn.util;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.pevori.tameablearachnereborn.entity.ModEntities;
import net.pevori.tameablearachnereborn.entity.custom.HarpyEntity;
import net.pevori.tameablearachnereborn.screen.TameableArachneScreenRegistries;

public class ModRegistries {
    public static void registerTameableArachne() {
        registerAttributes();
        registerScreens();
    }

    private static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.HARPY, HarpyEntity.setAttributes());
        //FabricDefaultAttributeRegistry.register(ModEntities.ARACHNE, ArachneEntity.setAttributes());
        //FabricDefaultAttributeRegistry.register(ModEntities.ARACHNE_MEDIUM, ArachneMediumEntity.setAttributes());
    }

    private static void registerScreens(){
        TameableArachneScreenRegistries.registerScreenHandlers();
    }
}
