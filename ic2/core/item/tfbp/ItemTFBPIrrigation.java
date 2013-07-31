// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemTFBPIrrigation.java

package ic2.core.item.tfbp;

import ic2.core.Ic2Items;
import ic2.core.block.BlockRubSapling;
import ic2.core.block.machine.tileentity.TileEntityTerra;
import ic2.core.init.InternalName;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.tfbp:
//			ItemTFBP

public class ItemTFBPIrrigation extends ItemTFBP
{

	public ItemTFBPIrrigation(Configuration config, InternalName internalName)
	{
		super(config, internalName);
	}

	public int getConsume()
	{
		return 3000;
	}

	public int getRange()
	{
		return 60;
	}

	public boolean terraform(World world, int x, int z, int yCoord)
	{
		if (world.rand.nextInt(48000) == 0)
		{
			world.getWorldInfo().setRaining(true);
			return true;
		}
		int y = TileEntityTerra.getFirstBlockFrom(world, x, z, yCoord + 10);
		if (y == -1)
			return false;
		if (TileEntityTerra.switchGround(world, Block.sand, Block.dirt, x, y, z, true))
		{
			TileEntityTerra.switchGround(world, Block.sand, Block.dirt, x, y, z, true);
			return true;
		}
		int id = world.getBlockId(x, y, z);
		if (id == ((Block) (Block.tallGrass)).blockID)
			return spreadGrass(world, x + 1, y, z) || spreadGrass(world, x - 1, y, z) || spreadGrass(world, x, y, z + 1) || spreadGrass(world, x, y, z - 1);
		if (id == Block.sapling.blockID)
		{
			((BlockSapling)Block.sapling).growTree(world, x, y, z, world.rand);
			return true;
		}
		if (id == Ic2Items.rubberSapling.itemID)
		{
			((BlockRubSapling)Block.blocksList[Ic2Items.rubberSapling.itemID]).growTree(world, x, y, z, world.rand);
			return true;
		}
		if (id == Block.wood.blockID)
		{
			int meta = world.getBlockMetadata(x, y, z);
			world.setBlock(x, y + 1, z, Block.wood.blockID, meta, 7);
			createLeaves(world, x, y + 2, z, meta);
			createLeaves(world, x + 1, y + 1, z, meta);
			createLeaves(world, x - 1, y + 1, z, meta);
			createLeaves(world, x, y + 1, z + 1, meta);
			createLeaves(world, x, y + 1, z - 1, meta);
			return true;
		}
		if (id == Block.crops.blockID)
		{
			world.setBlockMetadataWithNotify(x, y, z, 7, 7);
			return true;
		}
		if (id == ((Block) (Block.fire)).blockID)
		{
			world.setBlock(x, y, z, 0, 0, 7);
			return true;
		} else
		{
			return false;
		}
	}

	public void createLeaves(World world, int x, int y, int z, int meta)
	{
		if (world.getBlockId(x, y, z) == 0)
			world.setBlock(x, y, z, ((Block) (Block.leaves)).blockID, meta, 7);
	}

	public boolean spreadGrass(World world, int x, int y, int z)
	{
		if (world.rand.nextBoolean())
			return false;
		y = TileEntityTerra.getFirstBlockFrom(world, x, z, y);
		int id = world.getBlockId(x, y, z);
		if (id == Block.dirt.blockID)
		{
			world.setBlock(x, y, z, ((Block) (Block.grass)).blockID, 0, 7);
			return true;
		}
		if (id == ((Block) (Block.grass)).blockID)
		{
			world.setBlock(x, y + 1, z, ((Block) (Block.tallGrass)).blockID, 1, 7);
			return true;
		} else
		{
			return false;
		}
	}
}
