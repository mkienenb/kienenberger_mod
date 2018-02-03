package org.gamenet.minecraft.mods.kienenberger_mod.gui.client;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiConversation extends GuiScreen {
	private GuiButton someButton;
	
    /**
	 * Position 0, 0 is the top left screen corner.
	 * Must call superclass method which draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//		this.drawDefaultBackground();
	    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	    ResourceLocation resourceLocation = new ResourceLocation("kienenberger_mod:textures/gui/container/mod_tile_entity.png");
	    this.mc.getTextureManager().bindTexture(resourceLocation);
	    this.drawTexturedModalRect(100, 100, 0, 0, 255, 255);

		this.drawString(this.fontRendererObj , "This is a test", 150,  150, 0);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
	@Override
	public void initGui() {
//		this.buttonList.add(this.someButton = new GuiButton(1, 0, 0, "Something"));
	}

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
//		if (button == this.someButton) {
//			// RainbowWorldMain.packetHandler.sendToServer(...);
//			this.mc.displayGuiScreen(null);
//			if (this.mc.currentScreen == null)
//				this.mc.setIngameFocus();
//		}
	}

    /**
     * Called from the main game loop to update the screen.
     */
	@Override
    public void updateScreen() {
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
	@Override
    public void onGuiClosed() {
    }

}