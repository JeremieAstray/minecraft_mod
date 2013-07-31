// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemTFBPCultivation.java

package ic2.core.item.tfbp;

import ic2.core.*;
import ic2.core.block.machine.tileentity.TileEntityTerra;
import ic2.core.init.InternalName;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.tfbp:
//			ItemTFBP

public class ItemTFBPCultivation extends ItemTFBP
{

	public static ArrayList plantIDs = new ArrayList();

	public ItemTFBPCultivation(Configuration config, InternalName internalName)
	{
		super(config, internalName);
	}

	public static void init()
	{
		plantIDs.add(Integer.valueOf(((Block) (Block.tallGrass)).blockID));
		plantIDs.add(Integer.valueOf(((Block) (Block.plantRed)).blockID));
		plantIDs.add(Integer.valueOf(((Block) (Block.plantYellow)).blockID));
		plantIDs.add(Integer.valueOf(Block.sapling.blockID));
		plantIDs.add(Integer.valueOf(Block.crops.blockID));
		plantIDs.add(Integer.valueOf(((Block) (Block.mushroomRed)).blockID));
		plantIDs.add(Integer.valueOf(((Block) (Block.mushroomBrown)).blockID));
		plantIDs.add(Integer.valueOf(Block.pumpkin.blockID));
		if (Ic2Items.rubberSapling != null)
			plantIDs.add(Integer.valueOf(Ic2Items.rubberSapling.itemID));
	}

	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, 
			float a, float b, float c)
	{
		if (super.onItemUse(itemstack, entityplayer, world, i, j, k, l, a, b, c))
		{
			if (((Entity) (entityplayer)).dimension == 1)
				IC2.achievements.issueAchievement(entityplayer, "terraformEndCultivation");
			return true;
		} else
		{
			return false;
		}
	}

	public int getConsume()
	{
		return 4000;
	}

	public int getRange()
	{
		return 40;
	}

	public boolean terraform(World world, int x, int z, int yCoord)
	{
		int y = TileEntityTerra.getFirstSolidBlockFrom(world, x, z, yCoord + 10);
		if (y == -1)
			return false;
		if (TileEntityTerra.switchGround(world, Block.sand, Block.dirt, x, y, z, true))
			return true;
		int id = world.getBlockId(x, y, z);
		if (id == Block.dirt.blockID)
		{
			world.setBlock(x, y, z, ((Block) (Block.grass)).blockID, 0, 7);
			return true;
		}
		if (id == ((Block) (Block.grass)).blockID)
			return growPlantsOn(world, x, y + 1, z);
		else
			return false;
	}

	public boolean growPlantsOn(World world, int x, int y, int z)
	{
		int id = world.getBlockId(x, y, z);
		if (id == 0 || id == ((Block) (Block.tallGrass)).blockID && world.rand.nextInt(4) == 0)
		{
			int plant = pickRandomPlantId(world.rand);
			if (plant == Block.crops.blockID)
				world.setBlock(x, y - 1, z, Block.tilledField.blockID, 0, 7);
			if (plant == ((Block) (Block.tallGrass)).blockID)
				world.setBlock(x, y, z, plant, 1, 7);
			else
				world.setBlock(x, y, z, plant, 0, 7);
			return true;
		} else
		{
			return false;
		}
	}

	public int pickRandomPlantId(Random random)
	{
		for (int i = 0; i < plantIDs.size(); i++)
			if (random.nextInt(5) <= 1)
				return ((Integer)plantIDs.get(i)).intValue();

		return ((Block) (Block.tallGrass)).blockID;
	}

}
