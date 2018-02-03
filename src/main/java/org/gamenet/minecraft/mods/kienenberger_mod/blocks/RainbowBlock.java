package org.gamenet.minecraft.mods.kienenberger_mod.blocks;

import javax.annotation.Nullable;

import org.gamenet.minecraft.mods.kienenberger_mod.RainbowWorldMain;
import org.gamenet.minecraft.mods.kienenberger_mod.gui.ModGuiHandler;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.base.Predicate;

public class RainbowBlock extends BasicBlock {

    public static final Material RAINBOW = (new MaterialTransparent(MapColor.YELLOW));

	public static final PropertyDirection FACING = PropertyDirection.create(
			"facing", new Predicate<EnumFacing>() {
				public boolean apply(@Nullable EnumFacing p_apply_1_) {
					return true;
				}
			});
	
	public RainbowBlock(String string) {
		super(string, RAINBOW, 1.0f, 10.0f);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.DOWN));
	}

    public static EnumFacing getFacing(int meta)
    {
        return EnumFacing.getFront(meta & 7);
    }


    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, getFacing(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | ((EnumFacing)state.getValue(FACING)).getIndex();

        return i;
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	return false;
    }
    
    /**
     * Checks if an IBlockState represents a block that is opaque and a full cube.
     */
    @Override
    public boolean isFullyOpaque(IBlockState state)
    {
        return false;
    }
    
    @Override
    public boolean isTranslucent(IBlockState state) {
    	return true;
    }
    
	@Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		IBlockState neighborBlockState = blockAccess.getBlockState(pos.offset(side));
		return (neighborBlockState.getMaterial() == RAINBOW ? false
				: super.shouldSideBeRendered(blockState, blockAccess, pos, side));
    }

	@Override
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	};

	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isPassable(IBlockAccess world, BlockPos pos)
    {
        return true;
    }
    
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    
    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        EnumFacing enumfacing = facing.getOpposite();

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos,
    		IBlockState state, EntityPlayer playerIn, EnumHand hand,
    		ItemStack heldItem, EnumFacing side, float hitX, float hitY,
    		float hitZ) {
    	
		playerIn.openGui(RainbowWorldMain.instance, ModGuiHandler.CONVERSATION_GUI, worldIn,
				pos.getX(), pos.getY(), pos.getZ());
		
		return true;
    }
    

}
