package com.NCOpossum.entity.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.NCOpossum.entity.EntityNCOpossum;
import com.NCOpossum.objects.blocks.BlockTrashCan;
import com.NCOpossum.util.AISearch;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class AITaskNCOTrashRand extends EntityAIBase
{
	protected Block block;
	protected EntityNCOpossum opossum;
	private Long Cooldown = 0L;
	private AISearch search;
	private long lastRunTime = 0; // Track the last run time
	private static final long THREE_SECONDS = 1000L; // 3 seconds in milliseconds

	public AITaskNCOTrashRand(EntityNCOpossum opossum, float movementSpeed)
	{
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
				FindCan();
			}
		}
		
		lastRunTime = currentTime;
		
		if (Cooldown > 0L)
		{
			Cooldown--;
		}
		return false;

	}
	
	public void FindCan()
	{
		this.search = new AISearch(opossum);

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
					IBlockState state = this.opossum.world.getBlockState(pos);
					block = state.getBlock();
					if (block instanceof BlockTrashCan)
					{
						SearchCan(pos);
					}
				}
			}
		}			
	}
	
	public void SearchCan(BlockPos Trashpos)
	{
		Path path = this.opossum.getNavigator().getPathToPos(Trashpos);
		this.opossum.getNavigator().setPath(path, 0.4F);

		if (this.opossum.getDistanceSq(Trashpos) < 3.0D)
		{
			// Drop a random item when close to the trash can
			ItemStack randomItem = getRandomItem();

			// Now dropping the ItemStack correctly
			this.opossum.dropItem(randomItem.getItem(), 1); // 'false' means don't send to
															// client, adjust
			resetTask(); // Task is complete once an item is dropped
		}
	}
	
	private static String getRarity(Item item)
	{

		// Categorize based on item type and name (simplified example)
		String itemName = item.getRegistryName().toString();
		if (itemName.contains("diamond") || itemName.contains("nether_star") || itemName.contains("shulker")
				|| itemName.contains("matter") || itemName.contains("nuclear") || itemName.contains("nuke"))
		{
			return "Rare"; // Rare items (diamonds, nether star, etc.)
		} else if (itemName.contains("iron") || itemName.contains("gold") || itemName.contains("osmium")
				|| itemName.contains("copper"))
		{
			return "Uncommon"; // Uncommon items (iron, gold, etc.)
		} else
		{
			return "Common"; // Default to common for most other items
		}
	}

	// Method to get a random item from the game based on rarity
	private static ItemStack getRandomItem()
	{
		List<Item> commonItems = new ArrayList<>();
		List<Item> uncommonItems = new ArrayList<>();
		List<Item> rareItems = new ArrayList<>();

		// Categorize all items in the registry based on rarity
		for (Item item : ForgeRegistries.ITEMS)
		{
			String rarity = getRarity(item);
			if (rarity.equals("Common"))
			{
				commonItems.add(item);
			} else if (rarity.equals("Uncommon"))
			{
				uncommonItems.add(item);
			} else if (rarity.equals("Rare"))
			{
				rareItems.add(item);
			}
		}

		// Weighted random selection - higher chance for common items
		Random rand = new Random();
		int rarityRoll = rand.nextInt(100); // 0-99 range

		List<Item> selectedList;
		if (rarityRoll <= 95)
		{
			selectedList = commonItems; // 95% chance to pick common
		} else if (rarityRoll < 98)
		{
			selectedList = uncommonItems; // 3% chance to pick uncommon
		} else
		{
			selectedList = rareItems; // 2% chance to pick rare
		}

		// Pick a random item from the selected list
		Item selectedItem = selectedList.get(rand.nextInt(selectedList.size()));

		// Return the selected item as an ItemStack (drop 1 item)
		return new ItemStack(selectedItem, 1);
	}

	@Override
	public void resetTask()
	{
		block = null;
		Cooldown = 24000L;
		super.resetTask();
		this.opossum.getNavigator().clearPath();
	}
}