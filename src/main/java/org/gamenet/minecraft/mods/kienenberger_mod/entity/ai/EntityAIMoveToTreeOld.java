package org.gamenet.minecraft.mods.kienenberger_mod.entity.ai;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAIMoveToTreeOld extends EntityAIMoveToBlock {
    private static final Logger LOGGER = LogManager.getLogger();
    private final EntityCreature entity;
    private final int searchLength;

    enum TreeSubtask {
    	NOTHING,
    	MOVE_TO_TREE,
    	CHOP,
    	PICK_UP,
    	STORE
    }
    
    private TreeSubtask currentSubtask = TreeSubtask.NOTHING;

	public EntityAIMoveToTreeOld(EntityCreature creature, double movementSpeed, int searchLength) {
		super(creature, movementSpeed, searchLength);
		this.entity = creature;
		this.searchLength = searchLength;
		currentSubtask = TreeSubtask.NOTHING;
	}

	@Override
	protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
		IBlockState blockState = worldIn.getBlockState(pos);
		if (TreeSubtask.MOVE_TO_TREE == currentSubtask || TreeSubtask.STORE == currentSubtask) {
			if (isWood(worldIn, pos, blockState)
					|| isLeaves(worldIn, pos, blockState)) {
				return true;
			}
		}
		return false;
	}
	
    public boolean shouldExecute() {
        return this.searchForDestination();
    }

	public boolean searchForDestination() {
		World world = entity.worldObj;
		
		BlockPos entityPos = entity.getPosition();

		int radius = searchLength;
		int xS = -radius + entityPos.getX();
		int xL = radius + entityPos.getX();
		int yS = -radius + entityPos.getY();
		int yL = radius + entityPos.getY();
		int zS = -radius + entityPos.getZ();
		int zL = radius + entityPos.getZ();
		
        LOGGER.info("Entity " + entity.getName() + " searching for destination");

        List<BlockPos> possibleWoodDestinationList = new ArrayList<BlockPos>();
        List<BlockPos> possibleLeaveDestinationList = new ArrayList<BlockPos>();
		for (int x = xS; x <= xL; ++x) {
			for (int z = zS; z <= zL; ++z) {
				Chunk chunk = world.getChunkFromBlockCoords(new BlockPos(x, 0, z));
				for (int y = yS; y <= yL; ++y) {
					BlockPos pos = new BlockPos(x, y, z);
					IBlockState state = chunk.getBlockState(pos);
					if (isWood(world, pos, state)) {
						possibleWoodDestinationList.add(pos);
					}
					if (isLeaves(world, pos, state)) {
	                    possibleLeaveDestinationList.add(pos);
					}
				}
			}
		}

		if (!possibleWoodDestinationList.isEmpty()) {
			double shortestDistance = Integer.MAX_VALUE;
			for (BlockPos blockPos : possibleWoodDestinationList) {
				double distance = entityPos.distanceSq(blockPos);
				if (distance < shortestDistance) {
					shortestDistance = distance;
		            this.destinationBlock = blockPos;
				}
			}
            LOGGER.info("Entity " + entity.getName() + " destination is " + destinationBlock + ", distance " + Math.sqrt(shortestDistance));
            return true;
		}
		
		return false;
	}

	private boolean isLeaves(World world, BlockPos pos, IBlockState state) {
		return state.getBlock().isLeaves(state, world, pos);
	}

	private boolean isWood(World world, BlockPos pos, IBlockState state) {
		return state.getBlock().isWood(world, pos);
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
   public boolean continueExecuting()
   {
       return TreeSubtask.NOTHING != this.currentSubtask  && super.continueExecuting();
   }

	private boolean nearDestination() {
		if (null == destinationBlock) {
			return false;
		}
		BlockPos entityPosition = entity.getPosition();
		int xdiff = entityPosition.getX() - destinationBlock.getX();
		int ydiff = entityPosition.getZ() - destinationBlock.getZ();
		if ( (Math.abs(xdiff) < 4) && (Math.abs(ydiff) < 4) ) {
			return true;
		}
		return false;
	}

    public void updateTask() {
        super.updateTask();
        this.entity.getLookHelper().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.entity.getVerticalFaceSpeed());

        if (nearDestination())
        {
            World world = this.entity.worldObj;
            BlockPos blockpos = this.destinationBlock;
            IBlockState iblockstate = world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            if (this.currentSubtask == TreeSubtask.MOVE_TO_TREE || this.currentSubtask == TreeSubtask.NOTHING) {
            	this.currentSubtask = TreeSubtask.CHOP;
            }

            if (this.currentSubtask == TreeSubtask.CHOP)
            {
                if (isWood(world, blockpos, iblockstate))
                {
                    world.destroyBlock(blockpos, true);
                	this.currentSubtask = TreeSubtask.MOVE_TO_TREE;
                } else {
                	this.currentSubtask = TreeSubtask.MOVE_TO_TREE;
                }
            }

            this.runDelay = 10;
        } else {
        	this.currentSubtask = TreeSubtask.MOVE_TO_TREE;
        }
    }

}
