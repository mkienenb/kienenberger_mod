package org.gamenet.minecraft.mods.kienenberger_mod.blocks;

import javax.annotation.Nullable;

import org.gamenet.minecraft.mods.kienenberger_mod.entity.monster.EntityDiamondGolem;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMaterialMatcher;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.google.common.base.Predicate;

public class GraceBlock extends BasicBlock {

	public GraceBlock(String unlocalizedName, Material material,
			float hardness, float resistance) {
		super(unlocalizedName, material, hardness, resistance);
	}

    public GraceBlock(String string) {
    	super(string);
	}

	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        super.onBlockAdded(worldIn, pos, state);
        this.trySpawnGolem(worldIn, pos);
    }


    private void trySpawnGolem(World worldIn, BlockPos pos)
    {
        BlockPattern.PatternHelper blockpattern$patternhelper = this.getGolemPattern().match(worldIn, pos);

        if (blockpattern$patternhelper != null)
        {
            for (int k = 0; k < this.getGolemPattern().getPalmLength(); ++k)
            {
                for (int l = 0; l < this.getGolemPattern().getThumbLength(); ++l)
                {
                    worldIn.setBlockState(blockpattern$patternhelper.translateOffset(k, l, 0).getPos(), Blocks.AIR.getDefaultState(), 2);
                }
            }

            BlockPos blockpos = blockpattern$patternhelper.translateOffset(1, 2, 0).getPos();
            EntityDiamondGolem entityirongolem = new EntityDiamondGolem(worldIn);
            entityirongolem.setPlayerCreated(true);
            entityirongolem.setLocationAndAngles((double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.05D, (double)blockpos.getZ() + 0.5D, 0.0F, 0.0F);
            worldIn.spawnEntityInWorld(entityirongolem);

            for (int j1 = 0; j1 < 120; ++j1)
            {
                worldIn.spawnParticle(EnumParticleTypes.SNOWBALL, (double)blockpos.getX() + worldIn.rand.nextDouble(), (double)blockpos.getY() + worldIn.rand.nextDouble() * 3.9D, (double)blockpos.getZ() + worldIn.rand.nextDouble(), 0.0D, 0.0D, 0.0D, new int[0]);
            }

            for (int k1 = 0; k1 < this.getGolemPattern().getPalmLength(); ++k1)
            {
                for (int l1 = 0; l1 < this.getGolemPattern().getThumbLength(); ++l1)
                {
                    BlockWorldState blockworldstate2 = blockpattern$patternhelper.translateOffset(k1, l1, 0);
                    worldIn.notifyNeighborsRespectDebug(blockworldstate2.getPos(), Blocks.AIR);
                }
            }
        }
    }

    private static final Predicate<IBlockState> IS_PUMPKIN = new Predicate<IBlockState>()
    {
        public boolean apply(@Nullable IBlockState p_apply_1_)
        {
            return p_apply_1_ != null && (p_apply_1_.getBlock() == ModBlocks.graceBlock);
        }
    };

    private BlockPattern golemPattern;
    
    protected BlockPattern getGolemPattern()
    {
        if (this.golemPattern == null)
        {
			this.golemPattern = FactoryBlockPattern
					.start()
					.aisle(new String[] { "~^~", "###", "~#~" })
					.where('^', BlockWorldState.hasState(IS_PUMPKIN))
					.where('#',
							BlockWorldState.hasState(BlockStateMatcher
									.forBlock(Blocks.IRON_BLOCK)))
					.where('~',
							BlockWorldState.hasState(BlockMaterialMatcher
									.forMaterial(Material.AIR))).build();
        }

        return this.golemPattern;
    }
}