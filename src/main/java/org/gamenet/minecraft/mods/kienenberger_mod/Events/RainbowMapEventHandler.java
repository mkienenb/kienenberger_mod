package org.gamenet.minecraft.mods.kienenberger_mod.Events;

import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RainbowMapEventHandler {
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void initializeMap(InitMapGenEvent event){
//		if(WorldProviderRainbow.worldObject == null || WorldProviderRainbow.worldObject.provider == null){
//			return;
//		}
		
		switch (event.getType()) {
		case OCEAN_MONUMENT:{
			//event.setNewGen(new DirtyOceanMonument());
			break;
		}
		case CAVE:{
			break;
		}
		case CUSTOM:{
			break;
		}
		case MINESHAFT:{
			break;
		}
		case NETHER_BRIDGE:{
			break;
		}
		case NETHER_CAVE:{
			break;
		}
		case RAVINE:{
			break;
		}
		case SCATTERED_FEATURE:{
			break;
		}
		case STRONGHOLD:{
			break;
		}
		case VILLAGE:{
			break;
		}
		default:{
			break;
		}
		}
	}
	
}
