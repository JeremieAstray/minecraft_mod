// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockMachine2.java

package ic2.core.block.machine;

import ic2.core.Ic2Items;
import ic2.core.block.BlockMultiID;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.machine.tileentity.TileEntityCropmatron;
import ic2.core.block.machine.tileentity.TileEntityTeleporter;
import ic2.core.block.machine.tileentity.TileEntityTesla;
import ic2.core.init.InternalName;
import ic2.core.item.block.ItemMachine2;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

public class BlockMachine2 extends BlockMultiID
{

	public BlockMachine2(Configuration config, InternalName internalName)
	{
		super(config, internalName, Material.iron, ic2/core/item/block/ItemMachine2);
		setHardness(2.0F);
		setStepSound(Block.soundMetalFootstep);
		Ic2Items.teleporter = new ItemStack(this, 1, 0);
		Ic2Items.teslaCoil = new ItemStack(this, 1, 1);
		Ic2Items.cropmatron = new ItemStack(this, 1, 2);
	}

	public String getTextureFolder()
	{
		return "machine";
	}

	public int idDropped(int meta, Random random, int j)
	{
		switch (meta)
		{
		case 0: // '\0'
			return Ic2Items.advancedMachine.itemID;
		}
		return Ic2Items.machine.itemID;
	}

	public int damageDropped(int meta)
	{
		switch (meta)
		{
		case 0: // '\0'
			return Ic2Items.advancedMachine.getItemDamage();
		}
		return Ic2Items.machine.getItemDamage();
	}

	public TileEntityBlock createTileEntity(World world, int meta)
	{
		switch (meta)
		{
		case 0: // '\0'
			return new TileEntityTeleporter();

		case 1: // '\001'
			return new TileEntityTesla();

		case 2: // '\002'
			return new TileEntityCropmatron();
		}
		return null;
	}

	public void randomDisplayTick(World world, int i, int j, int k, Random random)
	{
		world.getBlockMetadata(i, j, k);
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return stack.getItemDamage() != 0 ? EnumRarity.common : EnumRarity.rare;
	}

	public boolean hasComparatorInputOverride()
	{
		return true;
	}

	public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
	{
		TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
		if (te instanceof TileEntityTeleporter)
			return ((TileEntityTeleporter)te).targetSet ? 15 : 0;
		else
			return 0;
	}

	public volatile TileEntity createTileEntity(World x0, int x1)
	{
		return createTileEntity(x0, x1);
	}
}
