package org.gamenet.minecraft.mods.kienenberger_mod.entity.client.render.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.gamenet.minecraft.mods.kienenberger_mod.blocks.ModBlocks;
import org.gamenet.minecraft.mods.kienenberger_mod.client.model.ModelDiamondGolem;
import org.gamenet.minecraft.mods.kienenberger_mod.entity.client.render.RenderDiamondGolem;
import org.gamenet.minecraft.mods.kienenberger_mod.entity.monster.EntityDiamondGolem;

@SideOnly(Side.CLIENT)
public class LayerDiamondGolemFlower implements LayerRenderer<EntityDiamondGolem>
{
    private final RenderDiamondGolem ironGolemRenderer;

    public LayerDiamondGolemFlower(RenderDiamondGolem ironGolemRendererIn)
    {
        this.ironGolemRenderer = ironGolemRendererIn;
    }

    public void doRenderLayer(EntityDiamondGolem entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (entitylivingbaseIn.getHoldRoseTick() != 0)
        {
            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
            GlStateManager.enableRescaleNormal();
            GlStateManager.pushMatrix();
            GlStateManager.rotate(5.0F + 180.0F * ((ModelDiamondGolem)this.ironGolemRenderer.getMainModel()).ironGolemRightArm.rotateAngleX / (float)Math.PI, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.translate(-0.9375F, -0.625F, -0.9375F);
            GlStateManager.scale(0.5F, -0.5F, 0.5F);
            int i = entitylivingbaseIn.getBrightnessForRender(partialTicks);
            int j = i % 65536;
            int k = i / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.ironGolemRenderer.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            blockrendererdispatcher.renderBlockBrightness(ModBlocks.graceBlock.getDefaultState(), 1.0F);
            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
        }
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}