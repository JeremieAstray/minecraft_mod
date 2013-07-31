// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockMachine.java

package ic2.core.block.machine;

import ic2.core.*;
import ic2.core.block.BlockMultiID;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.machine.tileentity.TileEntityCanner;
import ic2.core.block.machine.tileentity.TileEntityCompressor;
import ic2.core.block.machine.tileentity.TileEntityElectricFurnace;
import ic2.core.block.machine.tileentity.TileEntityElectrolyzer;
import ic2.core.block.machine.tileentity.TileEntityExtractor;
import ic2.core.block.machine.tileentity.TileEntityInduction;
import ic2.core.block.machine.tileentity.TileEntityIronFurnace;
import ic2.core.block.machine.tileentity.TileEntityMacerator;
import ic2.core.block.machine.tileentity.TileEntityMagnetizer;
import ic2.core.block.machine.tileentity.TileEntityMatter;
import ic2.core.block.machine.tileentity.TileEntityMiner;
import ic2.core.block.machine.tileentity.TileEntityPump;
import ic2.core.block.machine.tileentity.TileEntityRecycler;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.block.machine.tileentity.TileEntityTerra;
import ic2.core.init.InternalName;
import ic2.core.item.block.ItemMachine;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

public class BlockMachine extends BlockMultiID
{

	public BlockMachine(Configuration config, InternalName internalName)
	{
		super(config, internalName, Material.iron, ic2/core/item/block/ItemMachine);
		setHardness(2.0F);
		setStepSound(Block.soundMetalFootstep);
		Ic2Items.machine = new ItemStack(this, 1, 0);
		Ic2Items.advancedMachine = new ItemStack(this, 1, 12);
		Ic2Items.ironFurnace = new ItemStack(this, 1, 1);
		Ic2Items.electroFurnace = new ItemStack(this, 1, 2);
		Ic2Items.macerator = new ItemStack(this, 1, 3);
		Ic2Items.extractor = new ItemStack(this, 1, 4);
		Ic2Items.compressor = new ItemStack(this, 1, 5);
		Ic2Items.canner = new ItemStack(this, 1, 6);
		Ic2Items.miner = new ItemStack(this, 1, 7);
		Ic2Items.pump = new ItemStack(this, 1, 8);
		Ic2Items.magnetizer = new ItemStack(this, 1, 9);
		Ic2Items.electrolyzer = new ItemStack(this, 1, 10);
		Ic2Items.recycler = new ItemStack(this, 1, 11);
		Ic2Items.inductionFurnace = new ItemStack(this, 1, 13);
		Ic2Items.massFabricator = new ItemStack(this, 1, 14);
		Ic2Items.terraformer = new ItemStack(this, 1, 15);
	}

	public String getTextureFolder()
	{
		return "machine";
	}

	public int idDropped(int meta, Random random, int j)
	{
		switch (meta)
		{
		default:
			return super.blockID;
		}
	}

	public int damageDropped(int meta)
	{
		switch (meta)
		{
		case 1: // '\001'
			return meta;

		case 2: // '\002'
			return meta;

		case 9: // '\t'
			return meta;

		case 12: // '\f'
			return 12;

		case 13: // '\r'
			return 12;

		case 14: // '\016'
			return 12;

		case 15: // '\017'
			return 12;

		case 3: // '\003'
		case 4: // '\004'
		case 5: // '\005'
		case 6: // '\006'
		case 7: // '\007'
		case 8: // '\b'
		case 10: // '\n'
		case 11: // '\013'
		default:
			return 0;
		}
	}

	public TileEntityBlock createTileEntity(World world, int meta)
	{
		switch (meta)
		{
		case 1: // '\001'
			return new TileEntityIronFurnace();

		case 2: // '\002'
			return new TileEntityElectricFurnace();

		case 3: // '\003'
			return new TileEntityMacerator();

		case 4: // '\004'
			return new TileEntityExtractor();

		case 5: // '\005'
			return new TileEntityCompressor();

		case 6: // '\006'
			return new TileEntityCanner();

		case 7: // '\007'
			return new TileEntityMiner();

		case 8: // '\b'
			return new TileEntityPump();

		case 9: // '\t'
			return new TileEntityMagnetizer();

		case 10: // '\n'
			return new TileEntityElectrolyzer();

		case 11: // '\013'
			return new TileEntityRecycler();

		case 13: // '\r'
			return new TileEntityInduction();

		case 14: // '\016'
			return new TileEntityMatter();

		case 15: // '\017'
			return new TileEntityTerra();

		case 12: // '\f'
		default:
			return null;
		}
	}

	public void randomDisplayTick(World world, int i, int j, int k, Random random)
	{
		if (!IC2.platform.isRendering())
			return;
		int meta = world.getBlockMetadata(i, j, k);
		if (meta == 1 && isActive(world, i, j, k))
		{
			TileEntity te = world.getBlockTileEntity(i, j, k);
			int facing = (te instanceof TileEntityBlock) ? ((int) (((TileEntityBlock)te).getFacing())) : 0;
			float f = (float)i + 0.5F;
			float f1 = (float)j + 0.0F + (random.nextFloat() * 6F) / 16F;
			float f2 = (float)k + 0.5F;
			float f3 = 0.52F;
			float f4 = random.nextFloat() * 0.6F - 0.3F;
			switch (facing)
			{
			case 4: // '\004'
				world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
				break;

			case 5: // '\005'
				world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
				break;

			case 2: // '\002'
				world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
				break;

			case 3: // '\003'
				world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
				break;
			}
		}
		if (meta == 3 && isActive(world, i, j, k))
		{
			float f = (float)i + 1.0F;
			float f1 = (float)j + 1.0F;
			float f2 = (float)k + 1.0F;
			for (int z = 0; z < 4; z++)
			{
				float fmod = -0.2F - random.nextFloat() * 0.6F;
				float f1mod = -0.1F + random.nextFloat() * 0.2F;
				float f2mod = -0.2F - random.nextFloat() * 0.6F;
				world.spawnParticle("smoke", f + fmod, f1 + f1mod, f2 + f2mod, 0.0D, 0.0D, 0.0D);
			}

		}
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return stack.getItemDamage() != 14 ? stack.getItemDamage() != 15 && stack.getItemDamage() != 13 && stack.getItemDamage() != 12 ? EnumRarity.common : EnumRarity.uncommon : EnumRarity.rare;
	}

	public boolean hasComparatorInputOverride()
	{
		return true;
	}

	public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
	{
		TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
		if (te != null)
		{
			Class cls = te.getClass();
			if (cls == ic2/core/block/machine/tileentity/TileEntityInduction)
			{
				TileEntityInduction tei = (TileEntityInduction)te;
				return (int)Math.floor(((float)tei.heat / (float)TileEntityInduction.maxHeat) * 15F);
			}
			if (cls == ic2/core/block/machine/tileentity/TileEntityMatter)
				return (int)Math.floor(((float)((TileEntityMatter)te).energy / 1000000F) * 15F);
			if (cls == ic2/core/block/machine/tileentity/TileEntityElectrolyzer)
				return (int)Math.floor(((float)((TileEntityElectrolyzer)te).energy / 20000F) * 15F);
			if (te instanceof TileEntityStandardMachine)
			{
				TileEntityStandardMachine tem = (TileEntityStandardMachine)te;
				return (int)Math.floor(((float)tem.progress / (float)tem.operationLength) * 15F);
			}
		}
		return 0;
	}

	public volatile TileEntity createTileEntity(World x0, int x1)
	{
		return createTileEntity(x0, x1);
	}
}
