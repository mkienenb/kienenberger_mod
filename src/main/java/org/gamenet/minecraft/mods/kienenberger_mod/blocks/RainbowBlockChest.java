package org.gamenet.minecraft.mods.kienenberger_mod.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.gamenet.minecraft.mods.kienenberger_mod.RainbowWorldMain;
import org.gamenet.minecraft.mods.kienenberger_mod.gui.ModGuiHandler;
import org.gamenet.minecraft.mods.kienenberger_mod.tileentity.RainbowChestBlockTileEntity;

public class RainbowBlockChest extends RainbowBlock implements ITileEntityProvider {

	public RainbowBlockChest(String string) {
		super(string);
	}

    public static EnumFacing getFacing(int meta) {
        return EnumFacing.getFront(meta & 7);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos,
    		IBlockState state, EntityPlayer playerIn, EnumHand hand,
    		ItemStack heldItem, EnumFacing side, float hitX, float hitY,
    		float hitZ) {
    	
		playerIn.openGui(RainbowWorldMain.instance, ModGuiHandler.RAINBOW_CHEST_BLOCK_TILE_ENTITY_GUI, worldIn,
				pos.getX(), pos.getY(), pos.getZ());
		
		return true;
    }
    
    
    ///////// Tile entity stuff
    

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new RainbowChestBlockTileEntity();
	}
    
    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(ModBlocks.rainbowBlock);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState blockstate) {
        RainbowChestBlockTileEntity te = (RainbowChestBlockTileEntity) world.getTileEntity(pos);
        InventoryHelper.dropInventoryItems(world, pos, te);
        super.breakBlock(world, pos, blockstate);
    }


    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            ((RainbowChestBlockTileEntity) worldIn.getTileEntity(pos)).setCustomName(stack.getDisplayName());
        }
    }
}
