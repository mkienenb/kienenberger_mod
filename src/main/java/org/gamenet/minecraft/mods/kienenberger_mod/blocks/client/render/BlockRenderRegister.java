package org.gamenet.minecraft.mods.kienenberger_mod.blocks.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

import org.gamenet.minecraft.mods.kienenberger_mod.RainbowWorldMain;
import org.gamenet.minecraft.mods.kienenberger_mod.blocks.ModBlocks;

public class BlockRenderRegister {
	public static String modid = RainbowWorldMain.MODID;

	public static void registerBlockRenderer() {
	    reg(ModBlocks.graceBlock);
	    reg(ModBlocks.rainbowBlock);
	    reg(ModBlocks.townControllerBlock);
	}

	public static void reg(Block block) {
	    Minecraft
	    	.getMinecraft()
	    	.getRenderItem()
	    	.getItemModelMesher()
	    	.register(
	    			Item.getItemFromBlock(block),
	    			0,
	    			new ModelResourceLocation(
	    					modid
	    						+ ":"
	    						+ block
	    							.getUnlocalizedName()
	    							.substring(5),
	    					"inventory"));
	    }
}