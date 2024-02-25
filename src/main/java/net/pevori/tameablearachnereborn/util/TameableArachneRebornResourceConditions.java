package net.pevori.tameablearachnereborn.util;

import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.util.Identifier;
import net.pevori.tameablearachnereborn.TameableArachneReborn;
import net.pevori.tameablearachnereborn.config.TameableArachneRebornConfig;

public class TameableArachneRebornResourceConditions {
    private static final Identifier CRAFTABLE_SPAWN_EGGS = new Identifier(TameableArachneReborn.MOD_ID, "craftable_spawn_eggs");

    public static void init() {
        ResourceConditions.register(CRAFTABLE_SPAWN_EGGS, object -> TameableArachneRebornConfig.craftableSpawnEggs);
    }
}
