package net.pevori.tameablearachnereborn.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.pevori.tameablearachnereborn.TameableArachneReborn;
import net.pevori.tameablearachnereborn.entity.ModEntities;

public class ModItems {
    public static final Item MAGIC_CANDY = registerItem("magic_candy",
            new Item(new FabricItemSettings().group(ModItemGroup.TAMEABLEARACHNE))
    );

    public static final Item HARPY_SPAWN_EGG = registerItem("harpy_spawn_egg",
            new SpawnEggItem(ModEntities.HARPY, 0xF3F7FA, 0xA020F0,
                    new FabricItemSettings().group(ModItemGroup.TAMEABLEARACHNE))
    );

    public static final Item ARACHNE_SPAWN_EGG = registerItem("arachne_spawn_egg",
            new SpawnEggItem(ModEntities.ARACHNE, 0x0F1230, 0xA9B0F6,
                    new FabricItemSettings().group(ModItemGroup.TAMEABLEARACHNE))
    );

    public static final Item ARACHNE_MEDIUM_SPAWN_EGG = registerItem("arachne_medium_spawn_egg",
            new SpawnEggItem(ModEntities.ARACHNE_MEDIUM, 0x2A1A23, 0xC82D4E,
                    new FabricItemSettings().group(ModItemGroup.TAMEABLEARACHNE))
    );

    private static Item registerItem(String name, Item item){
        return Registry.register(Registry.ITEM, new Identifier(TameableArachneReborn.MOD_ID, name), item);
    }

    public static void registerModItems(){
        TameableArachneReborn.LOGGER.info("Registering Mod Items for " + TameableArachneReborn.MOD_ID);
    }
}
