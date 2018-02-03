package org.gamenet.minecraft.mods.kienenberger_mod.WorldGen;

import org.gamenet.minecraft.mods.kienenberger_mod.RainbowWorldMain;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldTypeRainbow extends WorldType{
	
	public WorldTypeRainbow(String name) {
        super(name);
	}

//	@Override
//    public BiomeProvider getBiomeProvider(World world){
//        return new BiomeProviderRainbow(world.getWorldInfo());
//    }
    
    @Override
    public IChunkGenerator getChunkGenerator(World world, String generatorOptions){
        return new ChunkProviderRainbow(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), world.getWorldInfo().getGeneratorOptions());
    }
    
    @Override
    public boolean isCustomizable()
    {
        return false;
    }
    
    @Override
    public float getCloudHeight()
    {
        return 256.0F;
    }
    
    @SideOnly(Side.CLIENT)
    public void onCustomizeButton(net.minecraft.client.Minecraft mc, net.minecraft.client.gui.GuiCreateWorld guiCreateWorld)
    {
        if (this == WorldType.FLAT)
        {
            mc.displayGuiScreen(new net.minecraft.client.gui.GuiCreateFlatWorld(guiCreateWorld, guiCreateWorld.chunkProviderSettingsJson));
        }
        else if (this == RainbowWorldMain.getRainbowWorldType())
        {
            mc.displayGuiScreen(new net.minecraft.client.gui.GuiCustomizeWorldScreen(guiCreateWorld, guiCreateWorld.chunkProviderSettingsJson));
        }
    }

}

