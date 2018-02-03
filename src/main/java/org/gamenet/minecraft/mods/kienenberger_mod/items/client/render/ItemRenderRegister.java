package org.gamenet.minecraft.mods.kienenberger_mod.items.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

import org.gamenet.minecraft.mods.kienenberger_mod.RainbowWorldMain;
import org.gamenet.minecraft.mods.kienenberger_mod.item.ModItems;

public class ItemRenderRegister {
	public static String modid = RainbowWorldMain.MODID;

	public static void registerItemRenderer() {
		reg(ModItems.graceItem);
		reg(ModItems.rainbowItem);
	}

	public static void reg(Item item) {
		Minecraft
				.getMinecraft()
				.getRenderItem()
				.getItemModelMesher()
				.register(
						item,
						0,
						new ModelResourceLocation(modid + ":"
								+ item.getUnlocalizedName().substring(5),
								"inventory"));
	}
}