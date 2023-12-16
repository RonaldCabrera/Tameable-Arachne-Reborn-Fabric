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
                    new FabricItemSettings().group(ModItemGroup.TAMEABLEARACHNE).maxCount(1))
    );

    /*public static final Item ARACHNE_SPAWN_EGG = registerItem("princess_bunny_spawn_egg",
            new SpawnEggItem(ModEntities.PRINCESS_BUNNY, 0xF3F7FA, 0xDB68ED,
                    new FabricItemSettings().group(ModItemGroup.TAMEABLEARACHNE).maxCount(1))
    );

    public static final Item ARACHNE_MEDIUM_SPAWN_EGG = registerItem("princess_bunny_spawn_egg",
            new SpawnEggItem(ModEntities.PRINCESS_BUNNY, 0xF3F7FA, 0xDB68ED,
                    new FabricItemSettings().group(ModItemGroup.TAMEABLEARACHNE).maxCount(1))
    );*/

    private static Item registerItem(String name, Item item){
        return Registry.register(Registry.ITEM, new Identifier(TameableArachneReborn.MOD_ID, name), item);
    }

    public static void registerModItems(){
        TameableArachneReborn.LOGGER.info("Registering Mod Items for" + TameableArachneReborn.MOD_ID);
    }
}
