package org.gamenet.minecraft.mods.kienenberger_mod.entity;

import net.minecraftforge.fml.common.registry.EntityRegistry;

import org.gamenet.minecraft.mods.kienenberger_mod.RainbowWorldMain;
import org.gamenet.minecraft.mods.kienenberger_mod.entity.monster.EntityDiamondGolem;

public class ModEntities {
	
	static public void createEntities() {
		int id = 1;
        EntityRegistry.registerModEntity(EntityDiamondGolem.class,
        		"DiamondGolem", id++, RainbowWorldMain.instance,
        		64, 3, true, 0x996600, 0x00ff00);
	}


}
