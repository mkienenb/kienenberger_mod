package org.gamenet.minecraft.mods.kienenberger_mod.entity.client.render;

import org.gamenet.minecraft.mods.kienenberger_mod.entity.monster.EntityDiamondGolem;

import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class EntityRenderRegister {
	static public void registerEntityRenderer() {
		RenderingRegistry.registerEntityRenderingHandler(
				EntityDiamondGolem.class, RenderDiamondGolem.FACTORY);
	}

}
