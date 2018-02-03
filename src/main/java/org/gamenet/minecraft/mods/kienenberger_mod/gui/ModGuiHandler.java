package org.gamenet.minecraft.mods.kienenberger_mod.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import org.gamenet.minecraft.mods.kienenberger_mod.gui.client.GuiRainbowChestBlockTileEntity;
import org.gamenet.minecraft.mods.kienenberger_mod.gui.client.GuiConversation;
import org.gamenet.minecraft.mods.kienenberger_mod.gui.client.GuiTownController;
import org.gamenet.minecraft.mods.kienenberger_mod.tileentity.ContainerRainbowChestBlockTileEntity;
import org.gamenet.minecraft.mods.kienenberger_mod.tileentity.RainbowChestBlockTileEntity;
import org.gamenet.minecraft.mods.kienenberger_mod.tileentity.TownControllerBlockTileEntity;

public class ModGuiHandler implements IGuiHandler {

	public static final int CONVERSATION_GUI = 0;
	public static final int RAINBOW_CHEST_BLOCK_TILE_ENTITY_GUI = 1;
	public static final int TOWN_CONTROLLER_GUI = 2;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (ID == RAINBOW_CHEST_BLOCK_TILE_ENTITY_GUI) {
			RainbowChestBlockTileEntity tileEntity = (RainbowChestBlockTileEntity) world.getTileEntity(new BlockPos(x, y, z));
			if (null != tileEntity) {
				return new ContainerRainbowChestBlockTileEntity(player.inventory, tileEntity);
			} else {
				new RuntimeException("Where'd my tile entity go?").printStackTrace();;
			}
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (ID == RAINBOW_CHEST_BLOCK_TILE_ENTITY_GUI) {
			RainbowChestBlockTileEntity tileEntity = (RainbowChestBlockTileEntity) world.getTileEntity(new BlockPos(x, y, z));
			if (null != tileEntity) {
				return new GuiRainbowChestBlockTileEntity(player.inventory, tileEntity);
			} else {
				new RuntimeException("Where'd my tile entity go?").printStackTrace();;
			}
		}

		if (ID == TOWN_CONTROLLER_GUI) {
			TownControllerBlockTileEntity tileEntity = (TownControllerBlockTileEntity) world.getTileEntity(new BlockPos(x, y, z));
			if (null != tileEntity) {
				return new GuiTownController(tileEntity);
			} else {
				new RuntimeException("Where'd my tile entity go?").printStackTrace();;
			}
		}
		
		if (ID == CONVERSATION_GUI) {
			return new GuiConversation();
		}
		return null;
	}
}