package net.pevori.tameablearachnereborn.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import eu.midnightdust.lib.config.MidnightConfig;
import net.pevori.tameablearachnereborn.TameableArachneReborn;

public class TameableArachneRebornModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory()
    {
        return parent -> MidnightConfig.getScreen(parent, TameableArachneReborn.MOD_ID);
    }
}
