package net.pevori.tameablearachnereborn.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.pevori.tameablearachnereborn.TameableArachneReborn;

public class ModItemGroup {
    public static final ItemGroup TAMEABLEARACHNE = FabricItemGroupBuilder.build(new Identifier(TameableArachneReborn.MOD_ID, "tameablearachnereborn"),
            () -> new ItemStack(ModItems.MAGIC_CANDY));
}
