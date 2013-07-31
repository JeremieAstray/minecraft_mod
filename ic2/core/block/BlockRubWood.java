// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockRubWood.java

package ic2.core.block;

import ic2.core.*;
import ic2.core.init.InternalName;
import ic2.core.item.block.ItemBlockRare;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.block:
//			BlockSimple

public class BlockRubWood extends BlockSimple
{

	private static final int textureIndexNormal = 0;
	private static final int textureIndexWet = 1;
	private static final int textureIndexDry = 2;

	public BlockRubWood(Configuration config, InternalName internalName)
	{
		super(config, internalName, Material.wood, ic2/core/item/block/ItemBlockRare);
		setTickRandomly(true);
		setHardness(1.0F);
		setStepSound(Block.soundWoodFootstep);
		Ic2Items.rubberWood = new ItemStack(this);
	}

	public String getTextureName(int index)
	{
		if (index == 0)
			return getUnlocalizedName();
		if (index == 1)
			return (new StringBuilder()).append(getUnlocalizedName()).append(".").append(InternalName.wet.name()).toString();
		if (index == 2)
			return (new StringBuilder()).append(getUnlocalizedName()).append(".").append(InternalName.dry.name()).toString();
		else
			return null;
	}

	public int getTextureIndex(int meta)
	{
		if (meta % 6 >= 2)
			return meta < 6 ? 1 : 2;
		else
			return 0;
	}

	public int getFacing(int meta)
	{
		int ret = meta % 6;
		return ret >= 2 ? ret : super.getFacing(meta);
	}

	public void dropBlockAsItemWithChance(World world, int i, int j, int k, int l, float f, int dropBuff)
	{
		if (!IC2.platform.isSimulating())
			return;
		int i1 = quantityDropped(world.rand);
		for (int j1 = 0; j1 < i1; j1++)
		{
			if (world.rand.nextFloat() > f)
				continue;
			int k1 = idDropped(l, world.rand, 0);
			if (k1 > 0)
				dropBlockAsItem_do(world, i, j, k, new ItemStack(k1, 1, 0));
			if (l != 0 && world.rand.nextInt(6) == 0)
				dropBlockAsItem_do(world, i, j, k, new ItemStack(Ic2Items.resin.getItem()));
		}

	}

	public void breakBlock(World world, int i, int j, int k, int a, int b)
	{
		byte byte0 = 4;
		int l = byte0 + 1;
		if (world.checkChunksExist(i - l, j - l, k - l, i + l, j + l, k + l))
		{
			for (int i1 = -byte0; i1 <= byte0; i1++)
			{
				for (int j1 = -byte0; j1 <= byte0; j1++)
				{
					for (int k1 = -byte0; k1 <= byte0; k1++)
					{
						int l1 = world.getBlockId(i + i1, j + j1, k + k1);
						if (l1 != Ic2Items.rubberLeaves.itemID)
							continue;
						int i2 = world.getBlockMetadata(i + i1, j + j1, k + k1);
						if ((i2 & 8) == 0)
							world.setBlockMetadataWithNotify(i + i1, j + j1, k + k1, i2 | 8, 3);
					}

				}

			}

		}
	}

	public void updateTick(World world, int x, int y, int z, Random random)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (meta < 6)
			return;
		if (random.nextInt(200) == 0)
			world.setBlockMetadataWithNotify(x, y, z, meta % 6, 7);
		else
			world.scheduleBlockUpdate(x, y, z, super.blockID, tickRate(world));
	}

	public int tickRate(World world)
	{
		return 100;
	}

	public int getMobilityFlag()
	{
		return 2;
	}

	public boolean canSustainLeaves(World world, int x, int y, int i)
	{
		return true;
	}

	public boolean isWood(World world, int x, int y, int i)
	{
		return true;
	}
}
