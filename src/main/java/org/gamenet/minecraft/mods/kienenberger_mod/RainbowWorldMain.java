package org.gamenet.minecraft.mods.kienenberger_mod;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.gamenet.minecraft.mods.kienenberger_mod.Events.RainbowMapEventHandler;
import org.gamenet.minecraft.mods.kienenberger_mod.WorldGen.WorldProviderRainbow;
import org.gamenet.minecraft.mods.kienenberger_mod.WorldGen.WorldTypeRainbow;

@Mod(modid = RainbowWorldMain.MODID, name = RainbowWorldMain.MODNAME, version = RainbowWorldMain.VERSION)
public class RainbowWorldMain {

	public static final String MODID = "kienenberger_mod";
	public static final String MODNAME = "Kienenberger Mod";
	public static final String VERSION = "1.0.1";

	static WorldType RAINBOW;
	private static int dimID = 0;

	private final RainbowMapEventHandler INIT_MAP_GEN_EVENT_HANDLER = new RainbowMapEventHandler();

	@SidedProxy(clientSide="org.gamenet.minecraft.mods.kienenberger_mod.ClientProxy", serverSide="org.gamenet.minecraft.mods.kienenberger_mod.ServerProxy")
	public static CommonProxy proxy;
	
	// @Instance("RainbowWorld")
	@Instance
	public static RainbowWorldMain instance = new RainbowWorldMain();

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		System.out.println("Called method: [preInit]");
	    proxy.preInit(e);
	    
		MinecraftForge.TERRAIN_GEN_BUS.register(INIT_MAP_GEN_EVENT_HANDLER);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		System.out.println("Called method: [init]");
	    proxy.init(e);

		// See WorldProviderRainbow on how this doesn't mess up vanilla WorldTypes
		DimensionManager.unregisterDimension(dimID);
		DimensionManager.registerDimension(dimID, DimensionType.register("RAINBOW", "_rainbow", dimID, WorldProviderRainbow.class, true));

		// CustomWorldGenerators.register();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		System.out.println("Called method: [postInit]");
	    proxy.postInit(e);
	    
		RAINBOW = new WorldTypeRainbow("RAINBOW");
	}
	
	public static WorldType getRainbowWorldType() {
		return RAINBOW;
	}
	
}
