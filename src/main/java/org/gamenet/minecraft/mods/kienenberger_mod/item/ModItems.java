package org.gamenet.minecraft.mods.kienenberger_mod.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModItems {
	public static Item graceItem;
	public static Item rainbowItem;

	public static final CreativeTabs creativeTabKienenbergerMod = new CreativeTabs("kienenbergerMod") {
	    @Override public Item getTabIconItem() {
	        return graceItem;
	    }
	};
	
	public static final void createItems() {
		graceItem = new Item();
		graceItem.setUnlocalizedName("graceItem");
		graceItem.setCreativeTab(creativeTabKienenbergerMod);
		graceItem.setRegistryName("graceItem");
		GameRegistry.register(graceItem);
		
		rainbowItem = new Item();
		rainbowItem.setUnlocalizedName("rainbowItem");
		rainbowItem.setCreativeTab(creativeTabKienenbergerMod);
		rainbowItem.setRegistryName("rainbowItem");
		GameRegistry.register(rainbowItem);
	}
}
