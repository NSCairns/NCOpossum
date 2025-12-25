package com.NCOpossum.objects.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockTrashCan extends BlockBase
{
	public static final AxisAlignedBB BLOCK_TRASH_AABB = new AxisAlignedBB(0.125, 0, 0.125, 0.875, 1.35, 0.875);
	
	public BlockTrashCan(String name)
	{
		super(name, Material.IRON);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		return BLOCK_TRASH_AABB;
	}
}
