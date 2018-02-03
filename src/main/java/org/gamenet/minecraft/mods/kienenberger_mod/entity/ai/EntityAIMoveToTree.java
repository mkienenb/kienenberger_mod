package org.gamenet.minecraft.mods.kienenberger_mod.entity.ai;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAIMoveToTree extends EntityAIBase {
    private static final Logger LOGGER = LogManager.getLogger();

	private static final int TREE_CHOPPING_DISTANCE = 4;
    
    private final EntityCreature entity;
    private double movementSpeed;
    private final int searchLength;

    protected int runDelay;
    protected BlockPos destinationBlock = null;
    protected BlockPos locationAtStartOfPathing = BlockPos.ORIGIN;
    private BlockPos movePos;
    
    enum TreeSubtask {
    	NOTHING,
    	MOVE_TO_TREE,
    	CHOP,
    	PICK_UP,
    	STORE
    }
    
    private TreeSubtask currentSubtask = TreeSubtask.NOTHING;

	public EntityAIMoveToTree(EntityCreature creature, double movementSpeed, int searchLength) {
		this.entity = creature;
		this.movementSpeed = movementSpeed;
		this.searchLength = searchLength;
		currentSubtask = TreeSubtask.NOTHING;
		
        this.setMutexBits(1);
	}

//    public boolean shouldExecute() {
//        return this.searchForDestination();
//    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute()
    {
//        if (this.entity.isWithinHomeDistanceCurrentPosition())
//        {
//            return false;
//        }
//        else
//        {
    	
    	if (null != destinationBlock) {
            World world = this.entity.worldObj;
            IBlockState state = world.getBlockState(destinationBlock);

        	if (!isWood(world, destinationBlock, state)) {
        		movePos = null;
        		destinationBlock = null;
        		currentSubtask = TreeSubtask.NOTHING;
        	}
    	}
    	
    	if (null == destinationBlock) {
            if (this.runDelay > 0) {
                --this.runDelay;
                return false;
            } else {
            	this.runDelay = 5;
//                this.runDelay = 200 + this.entity.getRNG().nextInt(200);
            	if (TreeSubtask.NOTHING == currentSubtask) {
                    if (this.searchForDestination()) {
                    	currentSubtask = TreeSubtask.MOVE_TO_TREE;
                    	return true;
                    } else {
                    	currentSubtask = TreeSubtask.NOTHING;
                    	return false;
                    }
            	}
            }
            
        	
        	return pathTowardDestination();

    	}
    	
    	return true;
//        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
    	switch (currentSubtask) {
	    	case NOTHING:
	    		// TODO: temporary hack to deal with bad pathing
	    		currentSubtask = TreeSubtask.MOVE_TO_TREE;
	    	case MOVE_TO_TREE:
	    		if (null != destinationBlock) {
			        this.entity.getLookHelper().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.entity.getVerticalFaceSpeed());
	    		}
	    		
	    		if (null != movePos) {
                    LOGGER.info("Recalc: Entity " + entity.getName() + " moving to " + movePos);
		            boolean foundPath = this.entity.getNavigator().tryMoveToXYZ(this.movePos.getX(), this.movePos.getY(), this.movePos.getZ(), this.movementSpeed);
		            if (!foundPath) {
		            	pathFailed();
		            }
	    		}
	    		return;
    	}
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean continueExecuting()
    {
        World world = this.entity.worldObj;
    	switch (currentSubtask) {
	    	case MOVE_TO_TREE:
	            boolean noPath = this.entity.getNavigator().noPath();
	            // Assuming that if there's a path that we are moving along it
	            if (noPath) {
	              if (nearDestination())
	              {
	                  BlockPos blockpos = this.destinationBlock;
	                  IBlockState iblockstate = world.getBlockState(blockpos);
	                  Block block = iblockstate.getBlock();
	      
	                  // At some point this should be moved outside of MOVE_TO
                  	  this.currentSubtask = TreeSubtask.CHOP;

	                  if (this.currentSubtask == TreeSubtask.CHOP)
	                  {
	                      if (isWood(world, blockpos, iblockstate)) {
	                          world.destroyBlock(blockpos, true);
	                      }
	                      this.destinationBlock = null;
	                      this.movePos = null;
                    	  this.currentSubtask = TreeSubtask.NOTHING;
	                  }
	      
	                  this.runDelay = 10;
	              } else {
	            	  pathFailed();
	              }
	            } // else we are still pathing
    	}
    	
    	return false;
    }

	private void pathFailed() {
        World world = this.entity.worldObj;
		if (locationAtStartOfPathing.equals(movePos)) {
			  // The path failed
			  // The path is inaccessible
			  // Either build a path, or destroy obstacles or blacklist destination
		      LOGGER.info("Entity " + entity.getName() + " made no progress toward destination " + destinationBlock);
        	  this.currentSubtask = TreeSubtask.NOTHING;
        	  movePos = null;
		  } else {
			  if (entity.getNavigator().noPath()) {
		          LOGGER.info("Entity " + entity.getName() + " recalculating as path no longer valid");
		          // try destroying any nearby leaves
		          BlockPos entityPosition = entity.getPosition();
		          if (null == destinationBlock) {
		        	  searchForDestination();
		        	  return;
		          }
				BlockPos staticVector = destinationBlock.subtract(entityPosition);

				float entityHeight = entity.height;
				
				BlockPos blockpos = entityPosition;
				if (staticVector.getX() > 0) {
		    		  blockpos = blockpos.add(1, 0, 0);
		    		  blockpos = getAirAboveGroundPos(world, blockpos);
				} else if (staticVector.getX() < 0) {
		    		  blockpos = blockpos.add(-1, 0, 0);
		    		  blockpos = getAirAboveGroundPos(world, blockpos);
			    }
		        
				boolean destroyedLeafBlock = tryToDestroyLeafBlock(world, entityHeight, blockpos);
				if (!destroyedLeafBlock) {
					blockpos = entityPosition;
					if (staticVector.getZ() > 0) {
		        		  blockpos = blockpos.add(0, 0, 1);
			    		  blockpos = getAirAboveGroundPos(world, blockpos);
					} else if (staticVector.getZ() < 0) {
		        		  blockpos = blockpos.add(0, 0, -1);
			    		  blockpos = getAirAboveGroundPos(world, blockpos);
				    }
					destroyedLeafBlock = tryToDestroyLeafBlock(world, entityHeight, blockpos);
				}
				
				if (!destroyedLeafBlock) {
					blockpos = entityPosition;
					destroyedLeafBlock = tryToDestroyLeafBlock(world, entityHeight, blockpos);
				}
				
		          if (!pathTowardDestination()) {
		    		  // The path failed
		        	  // The path is inaccessible
		        	  // Either build a path, or destroy obsticles or blacklist destination
		              LOGGER.info("Entity " + entity.getName() + " cannot path to " + destinationBlock);
		        	  this.currentSubtask = TreeSubtask.NOTHING;
		          }
			  }
		  }
	}

	private BlockPos getAirAboveGroundPos(World world, BlockPos originalBlockPos) {
		BlockPos blockpos = originalBlockPos;
        IBlockState iblockstate = world.getBlockState(blockpos);
        while (iblockstate.getMaterial() == Material.AIR) {
        	blockpos = blockpos.down();
            iblockstate = world.getBlockState(blockpos);
        }
        while (iblockstate.getMaterial() != Material.AIR) {
        	blockpos = blockpos.up();
            iblockstate = world.getBlockState(blockpos);
        }
		return blockpos;
	}

	private boolean tryToDestroyLeafBlock(World world, float entityHeight,
			BlockPos blockpos) {
		boolean destroyedLeafBlock = false;
		int blockHeight = 0;
		while (blockHeight <= entityHeight+1) {
		      IBlockState iblockstate = world.getBlockState(blockpos);
		      Block block = iblockstate.getBlock();
		      if (isLeaves(world, blockpos, iblockstate)) {
		          world.destroyBlock(blockpos, true);
		          LOGGER.error("Entity " + entity.getName() + " destroying leaves at " + blockpos);
		          destroyedLeafBlock = true;
		      }
		      blockpos = blockpos.up();
		      ++blockHeight;
		}
		return destroyedLeafBlock;
	}

	private boolean pathTowardDestination() {
        LOGGER.info("Entity " + entity.getName() + " pathing toward destination");
		this.locationAtStartOfPathing = entity.getPosition();
		int distance = (int)Math.sqrt(destinationBlock.distanceSq(locationAtStartOfPathing));
		int currentDistance = -1;
		  Vec3d vec3d = null;
		  for (int i = distance; i > 0; --i) {
			  vec3d = RandomPositionGenerator.findRandomTargetBlockTowards(this.entity, distance, 7, new Vec3d((double)destinationBlock.getX(), (double)destinationBlock.getY(), (double)destinationBlock.getZ()));
			  if (vec3d != null) {
				  currentDistance = i;
				  break;
			  }
		}

		  if (vec3d == null)
		  {
		      this.movePos = null;
		      return false;
		  }
		  else
		  {
            LOGGER.info("Entity " + entity.getName() + " setting intermediate destination of " + movePos + ", distance " + Math.sqrt(currentDistance));
		      this.movePos = new BlockPos(vec3d);
		      return true;
		  }
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

	private boolean nearDestination() {
		if (null == destinationBlock) {
			return false;
		}
		BlockPos entityPosition = entity.getPosition();
		int xdiff = entityPosition.getX() - destinationBlock.getX();
		int ydiff = entityPosition.getZ() - destinationBlock.getZ();
		if ( (Math.abs(xdiff) <= TREE_CHOPPING_DISTANCE) && (Math.abs(ydiff) <= TREE_CHOPPING_DISTANCE) ) {
			return true;
		}
		return false;
	}


}
