package net.pevori.tameablearachnereborn.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.pevori.tameablearachnereborn.TameableArachneReborn;

public class TameableArachneScreenRegistries {
    public static final Identifier TAMEABLE_ARACHNE_SCREEN = new Identifier(TameableArachneReborn.MOD_ID, "humanoid_animal_screen");
    public static final Identifier TEXTURE = new Identifier(TameableArachneReborn.MOD_ID, "textures/gui/container/tameable_arachne.png");
    public static ScreenHandlerType<TameableArachneScreenHandler> TAMEABLE_ARACHNE_SCREEN_HANDLER;

    public static void registerScreenHandlers() {
        TAMEABLE_ARACHNE_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(TAMEABLE_ARACHNE_SCREEN, TameableArachneScreenHandler::new);
    }

    public static void registerScreenRenderers(){
        HandledScreens.register(TAMEABLE_ARACHNE_SCREEN_HANDLER, TameableArachneScreen::new);
    }
}
