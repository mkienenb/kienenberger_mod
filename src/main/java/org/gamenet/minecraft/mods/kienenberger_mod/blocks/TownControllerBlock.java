package org.gamenet.minecraft.mods.kienenberger_mod.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.gamenet.minecraft.mods.kienenberger_mod.RainbowWorldMain;
import org.gamenet.minecraft.mods.kienenberger_mod.entity.monster.EntityDiamondGolem;
import org.gamenet.minecraft.mods.kienenberger_mod.gui.ModGuiHandler;
import org.gamenet.minecraft.mods.kienenberger_mod.tileentity.TownControllerBlockTileEntity;
import org.gamenet.minecraft.mods.kienenberger_mod.town.Citizen;

public class TownControllerBlock extends BasicBlock implements ITileEntityProvider {
    public TownControllerBlock(String string) {
    	super(string);
	}

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state,
    		EntityLivingBase placer, ItemStack stack) {
    	super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

    	if (!worldIn.isRemote) {
        	TownControllerBlockTileEntity tileEntity = (TownControllerBlockTileEntity) worldIn.getTileEntity(pos);

    		this.spawnCitizen(worldIn, tileEntity, pos.east());
    		this.spawnCitizen(worldIn, tileEntity, pos.north());
    		this.spawnCitizen(worldIn, tileEntity, pos.west());
    		this.spawnCitizen(worldIn, tileEntity, pos.south());
    	}
    }
    
	private void spawnCitizen(World worldIn, TownControllerBlockTileEntity tileEntity, BlockPos blockpos) {
		EntityDiamondGolem entityirongolem = new EntityDiamondGolem(worldIn);
		
		entityirongolem.setLocationAndAngles((double) blockpos.getX() + 0.5D,
				(double) blockpos.getY() + 0.05D,
				(double) blockpos.getZ() + 0.5D, 0.0F, 0.0F);
		entityirongolem.setHomePosAndDistance(blockpos, 50);
		entityirongolem.setCustomNameTag(Citizen.generateCitizenName(entityirongolem));

		worldIn.spawnEntityInWorld(entityirongolem);

		Citizen citizen = new Citizen(tileEntity, entityirongolem);
		tileEntity.addCitizen(citizen);
		
		tileEntity.assignJob(citizen);
	}

    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos,
    		IBlockState state, EntityPlayer playerIn, EnumHand hand,
    		ItemStack heldItem, EnumFacing side, float hitX, float hitY,
    		float hitZ) {
    	
		playerIn.openGui(RainbowWorldMain.instance, ModGuiHandler.TOWN_CONTROLLER_GUI, worldIn,
				pos.getX(), pos.getY(), pos.getZ());
		
		return true;
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		TownControllerBlockTileEntity townControllerBlockTileEntity = new TownControllerBlockTileEntity(worldIn);
		return townControllerBlockTileEntity;
	}
}
