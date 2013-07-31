// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockSimple.java

package ic2.core.block;

import ic2.core.Ic2Items;
import ic2.core.init.InternalName;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.block:
//			BlockMultiID

public class BlockSimple extends BlockMultiID
{

	public BlockSimple(Configuration config, InternalName internalName, Material mat)
	{
		super(config, internalName, mat);
	}

	public BlockSimple(Configuration config, InternalName internalName, Material material, Class itemClass)
	{
		super(config, internalName, material, itemClass);
	}

	public int idDropped(int i, Random random, int j)
	{
		if (Ic2Items.uraniumOre != null && super.blockID == Ic2Items.uraniumOre.itemID)
			return Ic2Items.uraniumDrop.itemID;
		else
			return super.blockID;
	}

	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
	{
		super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
		if (Ic2Items.uraniumOre != null && super.blockID == Ic2Items.uraniumOre.itemID)
			dropXpOnBlockBreak(par1World, par2, par3, par4, MathHelper.getRandomIntegerInRange(par1World.rand, 1, 3));
	}

	public boolean hasTileEntity(int metadata)
	{
		return false;
	}

	public TileEntity createTileEntity(World world, int metadata)
	{
		return null;
	}
}
