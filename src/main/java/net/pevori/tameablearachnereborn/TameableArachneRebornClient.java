package net.pevori.tameablearachnereborn;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.pevori.tameablearachnereborn.entity.ModEntities;
import net.pevori.tameablearachnereborn.entity.client.ArachneMediumRenderer;
import net.pevori.tameablearachnereborn.entity.client.ArachneRenderer;
import net.pevori.tameablearachnereborn.entity.client.HarpyRenderer;
import net.pevori.tameablearachnereborn.screen.TameableArachneScreenRegistries;

public class TameableArachneRebornClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.HARPY, HarpyRenderer::new);
        EntityRendererRegistry.register(ModEntities.ARACHNE, ArachneRenderer::new);
        EntityRendererRegistry.register(ModEntities.ARACHNE_MEDIUM, ArachneMediumRenderer::new);

        TameableArachneScreenRegistries.registerScreenRenderers();
    }
}
