package com.NCOpossum.init;

import java.util.ArrayList;
import java.util.List;

import com.NCOpossum.objects.blocks.BlockBase;
import com.NCOpossum.objects.blocks.BlockTrashCan;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInit 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block BLOCK_NEST = new BlockBase("block_nest", Material.WOOD);
	public static final Block BLOCK_TRASH = new BlockTrashCan("block_trashcan");

}
