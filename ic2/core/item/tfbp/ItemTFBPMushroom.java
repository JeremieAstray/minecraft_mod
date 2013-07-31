// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemTFBPMushroom.java

package ic2.core.item.tfbp;

import ic2.core.block.machine.tileentity.TileEntityTerra;
import ic2.core.init.InternalName;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMushroom;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.tfbp:
//			ItemTFBP

public class ItemTFBPMushroom extends ItemTFBP
{

	public ItemTFBPMushroom(Configuration config, InternalName internalName)
	{
		super(config, internalName);
	}

	public int getConsume()
	{
		return 8000;
	}

	public int getRange()
	{
		return 25;
	}

	public boolean terraform(World world, int x, int z, int yCoord)
	{
		int y = TileEntityTerra.getFirstSolidBlockFrom(world, x, z, yCoord + 20);
		if (y == -1)
			return false;
		return growBlockWithDependancy(world, x, y, z, Block.mushroomCapBrown.blockID, ((Block) (Block.mushroomBrown)).blockID);
	}

	public boolean growBlockWithDependancy(World world, int x, int y, int z, int id, int dependancy)
	{
		for (int xm = x - 1; dependancy != -1 && xm < x + 1; xm++)
		{
			for (int zm = z - 1; zm < z + 1; zm++)
			{
				for (int ym = y + 5; ym > y - 2; ym--)
				{
					int block = world.getBlockId(xm, ym, zm);
					if (dependancy == ((Block) (Block.mycelium)).blockID)
					{
						if (block == dependancy || block == Block.mushroomCapBrown.blockID || block == Block.mushroomCapRed.blockID)
							break;
						if (block == 0)
							continue;
						if (block == Block.dirt.blockID || block == ((Block) (Block.grass)).blockID)
						{
							world.setBlock(xm, ym, zm, dependancy, 0, 7);
							TileEntityTerra.setBiomeAt(world, x, z, BiomeGenBase.mushroomIsland);
							return true;
						}
					}
					if (dependancy != ((Block) (Block.mushroomBrown)).blockID)
						continue;
					if (block == ((Block) (Block.mushroomBrown)).blockID || block == ((Block) (Block.mushroomRed)).blockID)
						break;
					if (block != 0 && growBlockWithDependancy(world, xm, ym, zm, ((Block) (Block.mushroomBrown)).blockID, ((Block) (Block.mycelium)).blockID))
						return true;
				}

			}

		}

		if (id == ((Block) (Block.mushroomBrown)).blockID)
		{
			int base = world.getBlockId(x, y, z);
			if (base != ((Block) (Block.mycelium)).blockID)
				if (base == Block.mushroomCapBrown.blockID || base == Block.mushroomCapRed.blockID)
					world.setBlock(x, y, z, ((Block) (Block.mycelium)).blockID, 0, 7);
				else
					return false;
			int above = world.getBlockId(x, y + 1, z);
			if (above != 0 && above != ((Block) (Block.tallGrass)).blockID)
				return false;
			int shroom = ((Block) (Block.mushroomBrown)).blockID;
			if (world.rand.nextBoolean())
				shroom = ((Block) (Block.mushroomRed)).blockID;
			world.setBlock(x, y + 1, z, shroom, 0, 7);
			return true;
		}
		if (id == Block.mushroomCapBrown.blockID)
		{
			int base = world.getBlockId(x, y + 1, z);
			if (base != ((Block) (Block.mushroomBrown)).blockID && base != ((Block) (Block.mushroomRed)).blockID)
				return false;
			if (((BlockMushroom)Block.blocksList[base]).fertilizeMushroom(world, x, y + 1, z, world.rand))
			{
				for (int xm = x - 1; xm < x + 1; xm++)
				{
					for (int zm = z - 1; zm < z + 1; zm++)
					{
						int block = world.getBlockId(xm, y + 1, zm);
						if (block == ((Block) (Block.mushroomBrown)).blockID || block == ((Block) (Block.mushroomRed)).blockID)
							world.setBlock(xm, y + 1, zm, 0, 0, 7);
					}

				}

				return true;
			}
		}
		return false;
	}
}
