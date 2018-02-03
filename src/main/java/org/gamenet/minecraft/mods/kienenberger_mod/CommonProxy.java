package org.gamenet.minecraft.mods.kienenberger_mod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import org.gamenet.minecraft.mods.kienenberger_mod.blocks.ModBlocks;
import org.gamenet.minecraft.mods.kienenberger_mod.crafting.ModCrafting;
import org.gamenet.minecraft.mods.kienenberger_mod.entity.ModEntities;
import org.gamenet.minecraft.mods.kienenberger_mod.gui.ModGuiHandler;
import org.gamenet.minecraft.mods.kienenberger_mod.item.ModItems;
import org.gamenet.minecraft.mods.kienenberger_mod.tileentity.ModTileEntities;
import org.gamenet.minecraft.mods.kienenberger_mod.world_modification.BiomeDecorationModifier;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
		ModItems.createItems();
		ModBlocks.createBlocks();
        ModEntities.createEntities();
        ModTileEntities.createEntities();
	}

	public void init(FMLInitializationEvent e) {
        registerEventListeners();

        ModCrafting.initCrafting();
		NetworkRegistry.INSTANCE.registerGuiHandler(RainbowWorldMain.instance,
				new ModGuiHandler());
	}

	public void postInit(FMLPostInitializationEvent e) {

	}


	public void registerEventListeners() {
		// DEBUG
		System.out.println("Registering event listeners");

		// MinecraftForge.EVENT_BUS.register(new WildAnimalsEventHandler());
		MinecraftForge.TERRAIN_GEN_BUS.register(new BiomeDecorationModifier());
		// MinecraftForge.ORE_GEN_BUS.register(new WildAnimalsOreGenEventHandler());

		// some events, especially tick, are handled on FML bus
		// FMLCommonHandler.instance().bus().register(new WildAnimalsFMLEventHandler());
	}

	
}