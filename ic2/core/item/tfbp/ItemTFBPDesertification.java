// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemTFBPDesertification.java

package ic2.core.item.tfbp;

import ic2.core.Ic2Items;
import ic2.core.block.machine.tileentity.TileEntityTerra;
import ic2.core.init.InternalName;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.tfbp:
//			ItemTFBP, ItemTFBPCultivation

public class ItemTFBPDesertification extends ItemTFBP
{

	public ItemTFBPDesertification(Configuration config, InternalName internalName)
	{
		super(config, internalName);
	}

	public int getConsume()
	{
		return 2500;
	}

	public int getRange()
	{
		return 40;
	}

	public boolean terraform(World world, int x, int z, int yCoord)
	{
		int y = TileEntityTerra.getFirstBlockFrom(world, x, z, yCoord + 10);
		if (y == -1)
			return false;
		if (TileEntityTerra.switchGround(world, Block.dirt, Block.sand, x, y, z, false) || TileEntityTerra.switchGround(world, Block.grass, Block.sand, x, y, z, false) || TileEntityTerra.switchGround(world, Block.tilledField, Block.sand, x, y, z, false))
		{
			TileEntityTerra.switchGround(world, Block.dirt, Block.sand, x, y, z, false);
			return true;
		}
		int id = world.getBlockId(x, y, z);
		if (id == ((Block) (Block.waterMoving)).blockID || id == Block.waterStill.blockID || id == Block.snow.blockID || id == ((Block) (Block.leaves)).blockID || id == Ic2Items.rubberLeaves.itemID || isPlant(id))
		{
			world.setBlock(x, y, z, 0, 0, 7);
			return true;
		}
		if (id == Block.ice.blockID || id == Block.blockSnow.blockID)
		{
			world.setBlock(x, y, z, ((Block) (Block.waterMoving)).blockID, 0, 7);
			return true;
		}
		if ((id == Block.planks.blockID || id == Block.wood.blockID || id == Ic2Items.rubberWood.itemID) && world.rand.nextInt(15) == 0)
		{
			world.setBlock(x, y, z, ((Block) (Block.fire)).blockID, 0, 7);
			return true;
		} else
		{
			return false;
		}
	}

	public boolean isPlant(int id)
	{
		for (int i = 0; i < ItemTFBPCultivation.plantIDs.size(); i++)
			if (((Integer)ItemTFBPCultivation.plantIDs.get(i)).intValue() == id)
				return true;

		return false;
	}
}
