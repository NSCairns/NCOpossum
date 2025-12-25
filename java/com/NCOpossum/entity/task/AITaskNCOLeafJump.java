package com.NCOpossum.entity.task;

import com.NCOpossum.entity.EntityNCOpossum;
import com.NCOpossum.util.AISearch;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;

public class AITaskNCOLeafJump extends EntityAIBase
{

	protected Block block;
	private final EntityNCOpossum opossum; // The opossum entity performing the task
	private Long Cooldown = 0L;
	private long lastRunTime = 0; // Track the last run time
	private static final long THREE_SECONDS = 1000L; // 3 seconds in milliseconds

	public AITaskNCOLeafJump(EntityNCOpossum opossum, float movementSpeed)
	{
		this.setMutexBits(1);
		this.opossum = opossum;
	}

	@Override
	public boolean shouldExecute()
	{
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastRunTime < THREE_SECONDS)
		{
			return false;
		}

		else
		{
			if (Cooldown <= 0L)
			{
				GetLeafBlock();
			}
		}

		lastRunTime = currentTime;

		if (Cooldown > 0L)
		{
			Cooldown--;
		}
		return false;
	}

	private void GetLeafBlock()
	{
		AISearch search = new AISearch(opossum);

		int startX = (int) Math.floor(search.searchArea.minX);
		int startY = (int) Math.floor(search.searchArea.minY);
		int startZ = (int) Math.floor(search.searchArea.minZ);

		int endX = (int) Math.floor(search.searchArea.maxX);
		int endY = (int) Math.floor(search.searchArea.maxY);
		int endZ = (int) Math.floor(search.searchArea.maxZ);

		for (int x = startX; x <= endX; x++)
		{
			for (int y = startY; y <= endY; y++)
			{
				for (int z = startZ; z <= endZ; z++)
				{
					BlockPos pos = new BlockPos(x, y, z);
					IBlockState state = opossum.world.getBlockState(pos);
					block = state.getBlock();

					if (block instanceof BlockLeaves)
					{
						Path path = opossum.getNavigator().getPathToPos(pos);
						EngageLeaf(pos, path, block);
						break;
					}
				}
			}
		}

	}

	private void EngageLeaf(BlockPos leafblockpos, Path pathtopos, Block block)
	{
		// code block to be executed
		opossum.getNavigator().setPath(pathtopos, 0.4F);
		double distanceSq = opossum.getDistanceSq(leafblockpos);
		// If within 20 blocks but not yet within 10, make the opossum jump
		double x = leafblockpos.getX() + 0.5D;  // Drop item at the center of the block
	    double y = leafblockpos.getY() - 1.0D;  // Drop it slightly above the block, so it doesn't get stuck in the ground
	    double z = leafblockpos.getZ() + 0.5D;

		if (distanceSq < 20)
		{
			makeOpossumJump(leafblockpos);
			EntityItem entityItem = new EntityItem(opossum.world, x, y, z, new net.minecraft.item.ItemStack(Item.getItemById(260), 1));
			opossum.world.spawnEntity(entityItem);
			resetTask();
		}

	}

	private void makeOpossumJump(BlockPos leafPosition)
	{
		// Check the Y-coordinate to ensure the opossum should jump
		if (opossum.posY < leafPosition.getY())
		{
			double verticalGap = leafPosition.getY() - opossum.posY;

			opossum.motionY = verticalGap * 0.2D; // Adjust the multiplier (0.2D) based on desired jump strength

			opossum.onGround = false; // Ensure the opossum is in the air

			// Optional: Set horizontal motion towards the leaf (if needed)
			double deltaX = leafPosition.getX() - opossum.posX;
			double deltaZ = leafPosition.getZ() - opossum.posZ;
			double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
			opossum.motionX = (deltaX / distance) * 0.3D; // Adjust speed as necessary
			opossum.motionZ = (deltaZ / distance) * 0.3D; // Adjust speed as necessary
		}
	}

	@Override
	public void resetTask()
	{
		Cooldown = 2400L;
		super.resetTask();
		this.opossum.getNavigator().clearPath();
	}
}