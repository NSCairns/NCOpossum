package com.NCOpossum.objects.items;

import com.NCOpossum.MainNCO;
import com.NCOpossum.init.ItemInit;
import com.NCOpossum.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel
{
	public ItemBase(String name) 
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		
		ItemInit.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() 
	{
		MainNCO.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
