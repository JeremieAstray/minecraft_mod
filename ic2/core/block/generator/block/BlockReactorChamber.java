// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockReactorChamber.java

package ic2.core.block.generator.block;

import ic2.core.*;
import ic2.core.block.BlockMultiID;
import ic2.core.block.generator.tileentity.TileEntityNuclearReactor;
import ic2.core.block.generator.tileentity.TileEntityReactorChamberElectric;
import ic2.core.init.InternalName;
import ic2.core.item.block.ItemBlockRare;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

public class BlockReactorChamber extends BlockMultiID
{

	public static Class tileEntityReactorChamberClass = ic2/core/block/generator/tileentity/TileEntityReactorChamberElectric;

	public BlockReactorChamber(Configuration config, InternalName internalName)
	{
		super(config, internalName, Material.iron, ic2/core/item/block/ItemBlockRare);
		setHardness(2.0F);
		setStepSound(Block.soundMetalFootstep);
		Ic2Items.reactorChamber = new ItemStack(this);
	}

	public String getTextureFolder()
	{
		return "generator";
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		if (!canPlaceBlockAt(world, i, j, k))
		{
			dropBlockAsItem_do(world, i, j, k, new ItemStack(world.getBlockId(i, j, k), 1, 0));
			world.setBlock(i, j, k, 0, 0, 7);
		}
	}

	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		int count = 0;
		if (isReactorAt(world, i + 1, j, k))
			count++;
		if (isReactorAt(world, i - 1, j, k))
			count++;
		if (isReactorAt(world, i, j + 1, k))
			count++;
		if (isReactorAt(world, i, j - 1, k))
			count++;
		if (isReactorAt(world, i, j, k + 1))
			count++;
		if (isReactorAt(world, i, j, k - 1))
			count++;
		return count == 1;
	}

	public void randomDisplayTick(World world, int i, int j, int k, Random random)
	{
		TileEntityNuclearReactor reactor = getReactorEntity(world, i, j, k);
		if (reactor == null)
		{
			onNeighborBlockChange(world, i, j, k, super.blockID);
			return;
		}
		int puffs = reactor.heat / 1000;
		if (puffs <= 0)
			return;
		puffs = world.rand.nextInt(puffs);
		for (int n = 0; n < puffs; n++)
			world.spawnParticle("smoke", (float)i + random.nextFloat(), (float)j + 0.95F, (float)k + random.nextFloat(), 0.0D, 0.0D, 0.0D);

		puffs -= world.rand.nextInt(4) + 3;
		for (int n = 0; n < puffs; n++)
			world.spawnParticle("flame", (float)i + random.nextFloat(), (float)j + 1.0F, (float)k + random.nextFloat(), 0.0D, 0.0D, 0.0D);

	}

	public boolean isReactorAt(World world, int x, int y, int z)
	{
		return (world.getBlockTileEntity(x, y, z) instanceof TileEntityNuclearReactor) && world.getBlockId(x, y, z) == Ic2Items.nuclearReactor.itemID && world.getBlockMetadata(x, y, z) == Ic2Items.nuclearReactor.getItemDamage();
	}

	public TileEntityNuclearReactor getReactorEntity(World world, int i, int j, int k)
	{
		if (isReactorAt(world, i + 1, j, k))
			return (TileEntityNuclearReactor)world.getBlockTileEntity(i + 1, j, k);
		if (isReactorAt(world, i - 1, j, k))
			return (TileEntityNuclearReactor)world.getBlockTileEntity(i - 1, j, k);
		if (isReactorAt(world, i, j + 1, k))
			return (TileEntityNuclearReactor)world.getBlockTileEntity(i, j + 1, k);
		if (isReactorAt(world, i, j - 1, k))
			return (TileEntityNuclearReactor)world.getBlockTileEntity(i, j - 1, k);
		if (isReactorAt(world, i, j, k + 1))
			return (TileEntityNuclearReactor)world.getBlockTileEntity(i, j, k + 1);
		if (isReactorAt(world, i, j, k - 1))
		{
			return (TileEntityNuclearReactor)world.getBlockTileEntity(i, j, k - 1);
		} else
		{
			onNeighborBlockChange(world, i, j, k, world.getBlockId(i, j, k));
			return null;
		}
	}

	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float a, 
			float b, float c)
	{
		if (entityplayer.isSneaking())
			return false;
		TileEntityNuclearReactor reactor = getReactorEntity(world, i, j, k);
		if (reactor == null)
		{
			onNeighborBlockChange(world, i, j, k, super.blockID);
			return false;
		}
		if (!IC2.platform.isSimulating())
			return true;
		else
			return IC2.platform.launchGui(entityplayer, reactor);
	}

	public TileEntity createTileEntity(World world, int meta)
	{
		return (TileEntity)tileEntityReactorChamberClass.newInstance();
		Throwable e;
		e;
		throw new RuntimeException(e);
	}

	public int idDropped(int meta, Random random, int j)
	{
		return Ic2Items.machine.itemID;
	}

	public int damageDropped(int meta)
	{
		return Ic2Items.machine.getItemDamage();
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.uncommon;
	}

}
