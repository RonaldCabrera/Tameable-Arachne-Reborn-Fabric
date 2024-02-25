package net.pevori.tameablearachnereborn;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.pevori.tameablearachnereborn.config.TameableArachneRebornConfig;
import net.pevori.tameablearachnereborn.entity.ModEntities;
import net.pevori.tameablearachnereborn.entity.custom.TameableArachneEntity;
import net.pevori.tameablearachnereborn.item.ModItems;
import net.pevori.tameablearachnereborn.util.ModRegistries;
import net.pevori.tameablearachnereborn.util.TameableArachneRebornResourceConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TameableArachneReborn implements ModInitializer {
	public static final String MOD_ID = "tameablearachnereborn";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModRegistries.registerTameableArachne();
		MidnightConfig.init(MOD_ID, TameableArachneRebornConfig.class);
		TameableArachneRebornResourceConditions.init();

		if(TameableArachneRebornConfig.arachneSpawnFlag){
			BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.JUNGLE, BiomeKeys.BIRCH_FOREST, BiomeKeys.DARK_FOREST, BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.FLOWER_FOREST, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.SPARSE_JUNGLE), SpawnGroup.CREATURE,
					ModEntities.ARACHNE, 35, 1, 4);
		}

		if(TameableArachneRebornConfig.arachneMediumSpawnFlag){
			BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.JUNGLE, BiomeKeys.BIRCH_FOREST, BiomeKeys.DARK_FOREST, BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.FLOWER_FOREST, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.SPARSE_JUNGLE), SpawnGroup.CREATURE,
					ModEntities.ARACHNE_MEDIUM, 25, 1, 3);
		}

		if(TameableArachneRebornConfig.harpySpawnFlag){
			BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.JUNGLE, BiomeKeys.BIRCH_FOREST, BiomeKeys.DARK_FOREST, BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.FLOWER_FOREST, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.SPARSE_JUNGLE), SpawnGroup.CREATURE,
					ModEntities.HARPY, 30, 1, 3);
		}
	}
}
