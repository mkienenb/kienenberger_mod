package org.gamenet.minecraft.mods.kienenberger_mod.entity.client.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.gamenet.minecraft.mods.kienenberger_mod.RainbowWorldMain;
import org.gamenet.minecraft.mods.kienenberger_mod.client.model.ModelDiamondGolem;
import org.gamenet.minecraft.mods.kienenberger_mod.entity.client.render.layers.LayerDiamondGolemFlower;
import org.gamenet.minecraft.mods.kienenberger_mod.entity.monster.EntityDiamondGolem;


    @SideOnly(Side.CLIENT)
    public class RenderDiamondGolem extends RenderLiving<EntityDiamondGolem>
    {
        public static class RenderDiamondGolemFactory implements IRenderFactory<EntityDiamondGolem> {

            @Override
            public Render<? super EntityDiamondGolem> createRenderFor(RenderManager manager) {
                return new RenderDiamondGolem(manager);
            }

        }
        
        public static RenderDiamondGolemFactory FACTORY = new RenderDiamondGolemFactory();
    	public static String modid = RainbowWorldMain.MODID;

        private static final ResourceLocation IRON_GOLEM_TEXTURES = new ResourceLocation(modid, "textures/entities/diamond_golem.png");

        public RenderDiamondGolem(RenderManager renderManagerIn)
        {
            super(renderManagerIn, new ModelDiamondGolem(), 0.5F);
            this.addLayer(new LayerDiamondGolemFlower(this));
        }

        /**
         * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
         */
        protected ResourceLocation getEntityTexture(EntityDiamondGolem entity)
        {
            return IRON_GOLEM_TEXTURES;
        }

        protected void rotateCorpse(EntityDiamondGolem entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks)
        {
            super.rotateCorpse(entityLiving, p_77043_2_, p_77043_3_, partialTicks);

            if ((double)entityLiving.limbSwingAmount >= 0.01D)
            {
                float f1 = entityLiving.limbSwing - entityLiving.limbSwingAmount * (1.0F - partialTicks) + 6.0F;
                float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
                GlStateManager.rotate(6.5F * f2, 0.0F, 0.0F, 1.0F);
            }
        }
    }