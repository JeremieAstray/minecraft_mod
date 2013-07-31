// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WorldGenRubTree.java

package ic2.core.block;

import ic2.core.IC2;
import ic2.core.Ic2Items;
import ic2.core.network.NetworkManager;
import java.util.Random;
import java.util.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class WorldGenRubTree extends WorldGenerator
{

	public static final int maxHeight = 8;

	public WorldGenRubTree()
	{
	}

	public boolean generate(World world, Random random, int x, int count, int z)
	{
		for (; count > 0; count--)
		{
			int y;
			for (y = IC2.getWorldHeight(world) - 1; world.getBlockId(x, y - 1, z) == 0 && y > 0; y--);
			if (!grow(world, x, y, z, random))
				count -= 3;
			x += random.nextInt(15) - 7;
			z += random.nextInt(15) - 7;
		}

		return true;
	}

	public boolean grow(World world, int x, int y, int z, Random random)
	{
		if (world == null || Ic2Items.rubberWood == null)
		{
			IC2.log.warning((new StringBuilder()).append("Had a null that shouldn't have been. RubberTree did not spawn! w=").append(world).append(" r=").append(Ic2Items.rubberWood).toString());
			return false;
		}
		int treeholechance = 25;
		int h = getGrowHeight(world, x, y, z);
		if (h < 2)
			return false;
		int height = h / 2;
		h -= h / 2;
		height += random.nextInt(h + 1);
label0:
		for (int i = 0; i < height; i++)
		{
			world.setBlock(x, y + i, z, Ic2Items.rubberWood.itemID, 0, 7);
			if (random.nextInt(100) <= treeholechance)
			{
				treeholechance -= 10;
				world.setBlockMetadataWithNotify(x, y + i, z, random.nextInt(4) + 2, 7);
			} else
			{
				world.setBlockMetadataWithNotify(x, y + i, z, 1, 7);
			}
			IC2.network.announceBlockUpdate(world, x, y + i, z);
			if (height >= 4 && (height >= 7 || i <= 1) && i <= 2)
				continue;
			int a = x - 2;
			do
			{
				if (a > x + 2)
					continue label0;
				for (int b = z - 2; b <= z + 2; b++)
				{
					int c = (i + 4) - height;
					if (c < 1)
						c = 1;
					boolean gen = a > x - 2 && a < x + 2 && b > z - 2 && b < z + 2 || a > x - 2 && a < x + 2 && random.nextInt(c) == 0 || b > z - 2 && b < z + 2 && random.nextInt(c) == 0;
					if (gen && world.getBlockId(a, y + i, b) == 0)
						world.setBlock(a, y + i, b, Ic2Items.rubberLeaves.itemID, 0, 7);
				}

				a++;
			} while (true);
		}

		for (int i = 0; i <= height / 4 + random.nextInt(2); i++)
			if (world.getBlockId(x, y + height + i, z) == 0)
				world.setBlock(x, y + height + i, z, Ic2Items.rubberLeaves.itemID, 0, 7);

		return true;
	}

	public int getGrowHeight(World world, int x, int y, int z)
	{
		int id = world.getBlockId(x, y - 1, z);
		if (Block.blocksList[id] == null || !Block.blocksList[id].canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (IPlantable)Block.blocksList[Ic2Items.rubberSapling.itemID]) || world.getBlockId(x, y, z) != 0 && world.getBlockId(x, y, z) != Ic2Items.rubberSapling.itemID)
			return 0;
		int height;
		for (height = 1; world.getBlockId(x, y + 1, z) == 0 && height < 8; y++)
			height++;

		return height;
	}
}
