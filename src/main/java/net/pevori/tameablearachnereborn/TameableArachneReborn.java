package net.pevori.tameablearachnereborn;

import net.fabricmc.api.ModInitializer;
import net.pevori.tameablearachnereborn.item.ModItems;
import net.pevori.tameablearachnereborn.util.ModRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TameableArachneReborn implements ModInitializer {
	public static final String MOD_ID = "tameablearachnereborn";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModRegistries.registerTameableArachne();
	}
}
