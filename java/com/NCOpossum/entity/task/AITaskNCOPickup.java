package com.NCOpossum.entity.task;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.NCOpossum.entity.EntityNCOpossum;
import com.NCOpossum.init.ItemInit;
import com.NCOpossum.util.AISearch;

public class AITaskNCOPickup extends EntityAIBase
{

	private final EntityNCOpossum opossum; // The opossum entity performing the task
	private static final Set<EntityItem> itemsAlreadyPickedUp = new HashSet<>();
	private static final long THREE_SECONDS = 1000L; // 3 seconds in milliseconds
	private long lastRunTime = 0; // Track the last run time

	public AITaskNCOPickup(EntityNCOpossum opossum, float movementSpeed)
	{
		// this.setMutexBits(1);
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

			AISearch search = new AISearch(opossum);
			SearchForPlayers(search);

		}
		lastRunTime = currentTime;
		return false;
	}

	private void SearchForPlayers(AISearch search)
	{
		
		List<EntityPlayer> playersInRadius = this.opossum.world.getEntitiesWithinAABB(EntityPlayer.class, search.searchArea);
		for (EntityPlayer player : playersInRadius)
			if (player != null && player.getHeldItemMainhand().getItem() == ItemInit.ALFREDO_EGG)
			{
				this.opossum.entityDropItem(this.opossum.getHeldItemMainhand(), 0.0F);
				opossum.heldItem = ItemStack.EMPTY;
				opossum.setHeldItemMainhand(ItemStack.EMPTY);
				resetTask();
			} else
			{
				SearchForItem(search);
			}
	}

	public void SearchForItem(AISearch search)
	{
		List<EntityItem> items = opossum.world.getEntitiesWithinAABB(EntityItem.class, search.searchArea);

		for (EntityItem item : items)
		{
			if (itemsAlreadyPickedUp.contains(item))
			{
				continue; // Skip the item if it has already been picked up in this cycle
			}

			// Move the opossum towards the item

			if (opossum.getHeldItemMainhand().isEmpty() || opossum.getHeldItemMainhand() == null)
			{
				opossum.getNavigator().tryMoveToXYZ(item.posX, item.posY, item.posZ, 0.4F);

				// If close enough, pick up the item
				if (opossum.getDistanceSq(item.getPosition()) < 1.0F)
				{
					opossum.setHeldItemMainhand(item.getItem());
					item.setDead();
					itemsAlreadyPickedUp.add(item); // Mark this item as picked up
					resetTask();
					break;
				}
			}
		}
	}

	@Override
	public void resetTask()
	{
		this.opossum.getNavigator().clearPath();
		super.resetTask();
	}
}