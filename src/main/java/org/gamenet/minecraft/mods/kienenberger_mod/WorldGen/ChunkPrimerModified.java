package org.gamenet.minecraft.mods.kienenberger_mod.WorldGen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.ChunkPrimer;

public class ChunkPrimerModified extends ChunkPrimer {
	
	boolean finishedBiomes = false;

	public boolean isFinishedBiomes() {
		return finishedBiomes;
	}

	public void setFinishedBiomes(boolean finishedBiomes) {
		this.finishedBiomes = finishedBiomes;
	}

	@Override
	public IBlockState getBlockState(int x, int y, int z) {
		if (!isFinishedBiomes()) {
			return super.getBlockState(x, y, z);
		}
		
		IBlockState blockState = super.getBlockState(x, y, z);
		
		boolean shouldReplaceBlocks = false;
		if (blockState == Blocks.DIRT.getDefaultState()) {
			shouldReplaceBlocks = true;
		} else if (blockState == Blocks.STONE.getDefaultState()) {
			if (y < 254) {
				IBlockState blockStateUp = super.getBlockState(x, y+1, z);
				if (blockStateUp == Blocks.DIRT.getDefaultState()) {
					shouldReplaceBlocks = true;
				}
			}
		}
		
		if (shouldReplaceBlocks) {
			if ((0 == x % 16) && (0 == z % 16)) {
				return Blocks.MOSSY_COBBLESTONE.getDefaultState();
			}
			IBlockState blockStateW = safeGetBlockState(x-1, y, z);
			IBlockState blockStateE = safeGetBlockState(x+1, y, z);
			IBlockState blockStateN = safeGetBlockState(x, y, z-1);
			IBlockState blockStateS = safeGetBlockState(x, y, z+1);
			if (isSolidBlock(blockStateW) && isSolidBlock(blockStateE) && isSolidBlock(blockStateN) && isSolidBlock(blockStateS)) {
				if ((4 == x % 16) && (4 == z % 16)) {
					IBlockState blockStateUnder = super.getBlockState(x, y-1, z);
					if (blockStateUnder == Blocks.STONE.getDefaultState()) {
						return Blocks.TORCH.getDefaultState();
					} else {
						return Blocks.AIR.getDefaultState();
					}
				} else {
					return Blocks.AIR.getDefaultState();
				}
			} else {
//				if ((null == blockStateW) || (null == blockStateE) || (null == blockStateN) || (null == blockStateS))  {
//					return Blocks.GLASS.getDefaultState();
//				}
			}
		}
		return blockState;
	}

	private IBlockState safeGetBlockState(int x, int y, int z) {
		if (x < 0 || x > 15 || z < 0 || z > 15) {
			return null;
		}
		return super.getBlockState(x, y, z);
	}

	private boolean isSolidBlock(IBlockState blockState) {
		if (null == blockState) {
			return true;
		}
		
		
		if (blockState == Blocks.STONE.getDefaultState()) {
			return true;
		}
		
		if (blockState == Blocks.DIRT.getDefaultState()) {
			return true;
		}
		
		if (blockState == Blocks.GRAVEL.getDefaultState()) {
			return true;
		}
		
		if (blockState == Blocks.CLAY.getDefaultState()) {
			return true;
		}
		
		
		if (blockState == Blocks.GRASS.getDefaultState()) {
			return true;
		}
		
		if (blockState == Blocks.HARDENED_CLAY.getDefaultState()) {
			return true;
		}
		
		if (blockState == Blocks.SAND.getDefaultState()) {
			return true;
		}
		
		if (blockState == Blocks.SANDSTONE.getDefaultState()) {
			return true;
		}
		
		return false;
	}
}
