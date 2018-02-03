package org.gamenet.minecraft.mods.kienenberger_mod.tileentity;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {
	static public void createEntities() {
        GameRegistry.registerTileEntity(RainbowChestBlockTileEntity.class, "rainbow_tile_entity");;
        GameRegistry.registerTileEntity(TownControllerBlockTileEntity.class, "town_controller_tile_entity");;
	}

}
