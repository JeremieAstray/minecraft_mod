// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockGenerator.java

package ic2.core.block.generator.block;

import ic2.core.*;
import ic2.core.block.BlockMultiID;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.generator.tileentity.*;
import ic2.core.init.InternalName;
import ic2.core.item.block.ItemGenerator;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

public class BlockGenerator extends BlockMultiID
{

	public static Class tileEntityNuclearReactorClass = ic2/core/block/generator/tileentity/TileEntityNuclearReactorElectric;

	public BlockGenerator(Configuration config, InternalName internalName)
	{
		super(config, internalName, Material.iron, ic2/core/item/block/ItemGenerator);
		setHardness(3F);
		setStepSound(Block.soundMetalFootstep);
		Ic2Items.generator = new ItemStack(this, 1, 0);
		Ic2Items.geothermalGenerator = new ItemStack(this, 1, 1);
		Ic2Items.waterMill = new ItemStack(this, 1, 2);
		Ic2Items.solarPanel = new ItemStack(this, 1, 3);
		Ic2Items.windMill = new ItemStack(this, 1, 4);
		Ic2Items.nuclearReactor = new ItemStack(this, 1, 5);
	}

	public String getTextureFolder()
	{
		return "generator";
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
		case 2: // '\002'
			return 2;
		}
		return 0;
	}

	public int quantityDropped(Random random)
	{
		return 1;
	}

	public TileEntityBlock createTileEntity(World world, int meta)
	{
		meta;
		JVM INSTR tableswitch 0 5: default 90
	//	               0 40
	//	               1 48
	//	               2 56
	//	               3 64
	//	               4 72
	//	               5 80;
		   goto _L1 _L2 _L3 _L4 _L5 _L6 _L7
_L2:
		return new TileEntityGenerator();
_L3:
		return new TileEntityGeoGenerator();
_L4:
		return new TileEntityWaterGenerator();
_L5:
		return new TileEntitySolarGenerator();
_L6:
		return new TileEntityWindGenerator();
_L7:
		return (TileEntityBlock)tileEntityNuclearReactorClass.newInstance();
		Exception e;
		e;
		throw new RuntimeException(e);
_L1:
		return null;
	}

	public void randomDisplayTick(World world, int i, int j, int k, Random random)
	{
		if (!IC2.platform.isRendering())
			return;
		int meta = world.getBlockMetadata(i, j, k);
		if (meta == 0 && isActive(world, i, j, k))
		{
			TileEntityBlock te = (TileEntityBlock)world.getBlockTileEntity(i, j, k);
			int l = te.getFacing();
			float f = (float)i + 0.5F;
			float f1 = (float)j + 0.0F + (random.nextFloat() * 6F) / 16F;
			float f2 = (float)k + 0.5F;
			float f3 = 0.52F;
			float f4 = random.nextFloat() * 0.6F - 0.3F;
			switch (l)
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
		} else
		if (meta == 5)
		{
			int puffs = ((TileEntityNuclearReactor)world.getBlockTileEntity(i, j, k)).heat / 1000;
			if (puffs <= 0)
				return;
			puffs = world.rand.nextInt(puffs);
			for (int n = 0; n < puffs; n++)
				world.spawnParticle("smoke", (float)i + random.nextFloat(), (float)j + 0.95F, (float)k + random.nextFloat(), 0.0D, 0.0D, 0.0D);

			puffs -= world.rand.nextInt(4) + 3;
			for (int n = 0; n < puffs; n++)
				world.spawnParticle("flame", (float)i + random.nextFloat(), (float)j + 1.0F, (float)k + random.nextFloat(), 0.0D, 0.0D, 0.0D);

		}
	}

	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float a, 
			float b, float c)
	{
		if (entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().isItemEqual(Ic2Items.reactorChamber))
			return false;
		else
			return super.onBlockActivated(world, i, j, k, entityplayer, side, a, b, c);
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return stack.getItemDamage() != 5 ? EnumRarity.common : EnumRarity.uncommon;
	}

	public volatile TileEntity createTileEntity(World x0, int x1)
	{
		return createTileEntity(x0, x1);
	}

}
