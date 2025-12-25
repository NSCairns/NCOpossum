package com.NCOpossum.objects.blocks;

import com.NCOpossum.MainNCO;
import com.NCOpossum.init.BlockInit;
import com.NCOpossum.init.ItemInit;
import com.NCOpossum.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements IHasModel
{
	public BlockBase(String name, Material material) 
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModels() 
	{
		MainNCO.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

}
