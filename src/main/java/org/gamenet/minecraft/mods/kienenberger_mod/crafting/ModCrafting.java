package org.gamenet.minecraft.mods.kienenberger_mod.crafting;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import org.gamenet.minecraft.mods.kienenberger_mod.blocks.ModBlocks;
import org.gamenet.minecraft.mods.kienenberger_mod.item.ModItems;

public class ModCrafting {

	public static void initCrafting() {
		GameRegistry.addRecipe(new ItemStack(ModBlocks.graceBlock),
				new Object[] { "##", "##", '#', ModItems.graceItem });
		GameRegistry.addRecipe(new ItemStack(ModItems.graceItem, 4),
				new Object[] { "#", '#', ModBlocks.graceBlock });
		GameRegistry.addSmelting(Items.DIAMOND, new ItemStack(
				ModItems.graceItem), 1.0F);
	}
}
