package org.gamenet.minecraft.mods.kienenberger_mod.world_modification;

import java.util.Random;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.gamenet.minecraft.mods.kienenberger_mod.blocks.ModBlocks;
import org.gamenet.minecraft.mods.kienenberger_mod.blocks.RainbowBlock;

public class BiomeDecorationModifier {
    public boolean generateRainbow(World worldIn, Random rand, BlockPos position)
    {
        int direction = rand.nextInt(3);
        int dx;
        int dz;
        EnumFacing horizontalFacing;
        switch(direction) {
        case 0:
        	dx = 1;
        	dz = 0;
        	horizontalFacing = EnumFacing.EAST;
        	break;
        case 1:
        	dx = -1;
        	dz = 0;
        	horizontalFacing = EnumFacing.WEST;
        	break;
        case 2:
        	dx = 0;
        	dz = 1;
        	horizontalFacing = EnumFacing.SOUTH;
        	break;
        default:
        	dx = 0;
        	dz = -1;
        	horizontalFacing = EnumFacing.NORTH;
        	break;

        }

        int size = 5 + rand.nextInt(5);
        for (int count = 0; count < size; ++count)
        {
        	if (0 != count) {
                BlockPos blockpos = position.add(1 * dx, count, 1 * dz);
                BlockPos blockpos2 = position.add((size + 2) * dx, count, (size + 2) * dz);

                if (worldIn.isAirBlock(blockpos)) {
                    worldIn.setBlockState(blockpos, ModBlocks.rainbowBlock.getDefaultState(), 2);
                }

                if (worldIn.isAirBlock(blockpos2)) {
                    worldIn.setBlockState(blockpos2, ModBlocks.rainbowBlock.getDefaultState(), 2);
                }
        	}

            BlockPos blockpos3 = position.add((count + 2) * dx, size, (count + 2) * dz);
            if (worldIn.isAirBlock(blockpos3)) {
                worldIn.setBlockState(blockpos3, ModBlocks.rainbowBlock.getDefaultState().withProperty(RainbowBlock.FACING, horizontalFacing), 2);
            }
        }

        BlockPos blockpos = position.add(1 * dx, 0, 1 * dz);
        BlockPos blockpos2 = position.add((size + 2) * dx, 0, (size + 2) * dz);
        if (worldIn.isAirBlock(blockpos)) {
            worldIn.setBlockState(blockpos, ModBlocks.rainbowChestBlock.getDefaultState(), 2);
        }

        if (worldIn.isAirBlock(blockpos2)) {
            worldIn.setBlockState(blockpos2, ModBlocks.rainbowChestBlock.getDefaultState(), 2);
        }

        return true;
    }

    @SubscribeEvent(priority=EventPriority.LOWEST, receiveCanceled=true)
	public void onBiomeDecoration(DecorateBiomeEvent.Decorate event) {
    	if (event.getType() == DecorateBiomeEvent.Decorate.EventType.FLOWERS) {

    		Random random = event.getRand();
    		if (random.nextInt(4) == 0) {
                generateRainbows(event);
    		}

    		return;
    	}
	}

	private void generateRainbows(DecorateBiomeEvent.Decorate event) {
		BlockPos chunkPos = event.getPos();
		World worldIn = event.getWorld();
		Random random = event.getRand();
		
	    int x = random.nextInt(16) + 8;
	    int y = 0;
	    int z = random.nextInt(16) + 8;
	    chunkPos.add(x, y, z);
        BlockPos blockPos = worldIn.getTopSolidOrLiquidBlock(chunkPos);
	    
        generateRainbow(worldIn, random, blockPos);
	}
}