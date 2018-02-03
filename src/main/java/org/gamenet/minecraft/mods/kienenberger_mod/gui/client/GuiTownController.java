package org.gamenet.minecraft.mods.kienenberger_mod.gui.client;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.gamenet.minecraft.mods.kienenberger_mod.tileentity.TownControllerBlockTileEntity;
import org.gamenet.minecraft.mods.kienenberger_mod.town.Citizen;
import org.gamenet.minecraft.mods.kienenberger_mod.town.Job;

public class GuiTownController extends GuiScreen {
	private TownControllerBlockTileEntity townControllerBlockTileEntity;
	private GuiButton someButton;
	
    public GuiTownController(TownControllerBlockTileEntity townControllerBlockTileEntity) {
		this.townControllerBlockTileEntity = townControllerBlockTileEntity;
	}

	/**
	 * Position 0, 0 is the top left screen corner.
	 * Must call superclass method which draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
	    int anchorX = width / 2; //center of screen x
		int anchorY = height / 2; //center of screen y
		
		this.drawDefaultBackground();
//	    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
//	    ResourceLocation resourceLocation = new ResourceLocation("kienenberger_mod:textures/gui/container/mod_tile_entity.png");
//	    this.mc.getTextureManager().bindTexture(resourceLocation);
//	    this.drawTexturedModalRect(anchorX - 255 / 2, anchorY - 255 / 2, 0, 0, 255, 255);

		List<Citizen> citizenList = townControllerBlockTileEntity.getCitizenList();
		List<Job> unassignedJobList = townControllerBlockTileEntity.getUnassignedJobList();
		Map<Job,Citizen> assignedJobByCitizenMap = townControllerBlockTileEntity.getAssignedJobByCitizenMap();
		
		int totalLines = 1 + citizenList.size() + 1 + 1 + unassignedJobList.size() + assignedJobByCitizenMap.size();
		
		int lineSpacing = fontRendererObj.FONT_HEIGHT + 1;
		int startY = anchorY - ((totalLines / 2) * lineSpacing);
		
		int lineCount = 0;
		
	    drawString(anchorX, startY, lineSpacing, "CITIZENS:", lineCount++);
		for (Citizen citizen : citizenList) {
			String citizenName = citizen.getEntityCreature().getCustomNameTag();
			// GL11.glScalef
			// ScaledResolution var5 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
		    drawString(anchorX, startY, lineSpacing, citizenName, lineCount++);
		}
		
		lineCount++;
		
	    drawString(anchorX, startY, lineSpacing, "JOBS:", lineCount++);
		for (Entry<Job, Citizen> entry : assignedJobByCitizenMap.entrySet()) {
			Job job = entry.getKey();
			Citizen citizen = entry.getValue();
			String jobName = job.getJobName() + " - " + citizen.getEntityCreature().getName();
			// GL11.glScalef
			// ScaledResolution var5 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
		    drawString(anchorX, startY, lineSpacing, jobName, lineCount++);
		}
		for (Job job : unassignedJobList) {
			String jobName = job.getJobName() + " - Unassigned";
			// GL11.glScalef
			// ScaledResolution var5 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
		    drawString(anchorX, startY, lineSpacing, jobName, lineCount++);
		}
		

		// this.drawString(this.fontRendererObj , "This is a test", 150,  150, 0);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	private void drawString(int anchorX, int startY, int lineSpacing, String citizenName, int lineCount) {
		this.fontRendererObj.drawString(citizenName, anchorX - (this.fontRendererObj.getStringWidth(citizenName) / 2), startY + (lineCount * lineSpacing), 4210752);            //#404040
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