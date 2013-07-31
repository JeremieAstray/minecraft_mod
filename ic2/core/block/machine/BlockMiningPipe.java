// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockMiningPipe.java

package ic2.core.block.machine;

import ic2.core.*;
import ic2.core.block.BlockSimple;
import ic2.core.init.InternalName;
import ic2.core.item.block.ItemBlockRare;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

public class BlockMiningPipe extends BlockSimple
{

	public BlockMiningPipe(Configuration config, InternalName internalName)
	{
		super(config, internalName, Material.iron, ic2/core/item/block/ItemBlockRare);
		setHardness(6F);
		setResistance(10F);
		Ic2Items.miningPipe = new ItemStack(this);
	}

	public String getTextureFolder()
	{
		return "machine";
	}

	public boolean canPlaceBlockAt(World world, int i, int j, int l)
	{
		return false;
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public boolean isBlockNormalCube(World world, int i, int j, int l)
	{
		return false;
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public int getRenderType()
	{
		return IC2.platform.getRenderId("miningPipe");
	}

	public void setBlockBoundsBasedOnState(IBlockAccess par1iBlockAccess, int par2, int par3, int par4)
	{
		setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
	}
}
