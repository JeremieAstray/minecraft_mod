// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockMiningTip.java

package ic2.core.block.machine;

import ic2.core.Ic2Items;
import ic2.core.block.BlockSimple;
import ic2.core.init.InternalName;
import ic2.core.item.block.ItemBlockRare;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

public class BlockMiningTip extends BlockSimple
{

	public BlockMiningTip(Configuration config, InternalName internalName)
	{
		super(config, internalName, Material.iron, ic2/core/item/block/ItemBlockRare);
		setHardness(6F);
		setResistance(10F);
		Ic2Items.miningPipeTip = new ItemStack(this);
	}

	public String getTextureFolder()
	{
		return "machine";
	}

	public boolean canPlaceBlockAt(World world, int i, int j, int l)
	{
		return false;
	}

	public int idDropped(int i, Random random, int j)
	{
		return Ic2Items.miningPipe.itemID;
	}

	public void getSubBlocks(int j, CreativeTabs creativetabs, List list)
	{
	}
}
