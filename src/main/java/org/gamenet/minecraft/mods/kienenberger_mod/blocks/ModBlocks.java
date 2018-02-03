package org.gamenet.minecraft.mods.kienenberger_mod.blocks;

import org.gamenet.minecraft.mods.kienenberger_mod.item.ModItems;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static Block graceBlock;
	public static Block rainbowBlock;
	public static Block rainbowChestBlock;
	public static Block townControllerBlock;

	public static void createBlocks() {
		townControllerBlock = new TownControllerBlock("town_controller_block");
		townControllerBlock.setRegistryName("town_controller_block");
		townControllerBlock.setCreativeTab(ModItems.creativeTabKienenbergerMod);
		GameRegistry.register(townControllerBlock);
		GameRegistry.register(new ItemBlock(townControllerBlock).setRegistryName(townControllerBlock.getRegistryName()));

		graceBlock = new GraceBlock("grace_block");
		graceBlock.setRegistryName("grace_block");
		GameRegistry.register(graceBlock);
		GameRegistry.register(new ItemBlock(graceBlock).setRegistryName(graceBlock.getRegistryName()));

		rainbowBlock = new RainbowBlock("rainbow_block");
		rainbowBlock.setRegistryName("rainbow_block");
		GameRegistry.register(rainbowBlock);
		GameRegistry.register(new ItemBlock(rainbowBlock).setRegistryName(rainbowBlock.getRegistryName()));

		rainbowChestBlock = new RainbowBlockChest("rainbow_chest_block");
		rainbowChestBlock.setRegistryName("rainbow_chest_block");
		GameRegistry.register(rainbowChestBlock);
		// GameRegistry.register(new ItemBlock(rainbowBlock).setRegistryName(rainbowChestBlock.getRegistryName()));
	}
}
