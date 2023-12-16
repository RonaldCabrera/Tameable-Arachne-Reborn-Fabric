package net.pevori.tameablearachnereborn;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.pevori.tameablearachnereborn.entity.ModEntities;
import net.pevori.tameablearachnereborn.entity.client.HarpyRenderer;
import net.pevori.tameablearachnereborn.screen.TameableArachneScreenRegistries;

public class TameableArachneRebornClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.HARPY, HarpyRenderer::new);
        //EntityRendererRegistry.register(ModEntities.PRINCESS_CAT, PrincessCatRenderer::new);
        //EntityRendererRegistry.register(ModEntities.QUEEN_DOG, QueenDogRenderer::new);

        TameableArachneScreenRegistries.registerScreenRenderers();
    }
}
