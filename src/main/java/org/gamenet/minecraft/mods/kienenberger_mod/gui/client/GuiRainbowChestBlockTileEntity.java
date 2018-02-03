package org.gamenet.minecraft.mods.kienenberger_mod.gui.client;

import org.gamenet.minecraft.mods.kienenberger_mod.tileentity.ContainerRainbowChestBlockTileEntity;
import org.gamenet.minecraft.mods.kienenberger_mod.tileentity.RainbowChestBlockTileEntity;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiRainbowChestBlockTileEntity extends GuiContainer {
	private IInventory playerInv;
	private RainbowChestBlockTileEntity te;

	public GuiRainbowChestBlockTileEntity(IInventory playerInv, RainbowChestBlockTileEntity te) {
	    super(new ContainerRainbowChestBlockTileEntity(playerInv, te));

	    this.playerInv = playerInv;
	    this.te = te;

	    this.xSize = 176;
	    this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	    this.mc.getTextureManager().bindTexture(new ResourceLocation("kienenberger_mod:textures/gui/container/mod_tile_entity.png"));
	    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	    String s = this.te.getDisplayName().getUnformattedText();
	    this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);            //#404040
	    this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 72, 4210752);      //#404040
	}
}