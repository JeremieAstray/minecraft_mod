// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CropPumpkin.java

package ic2.core.block.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import ic2.core.IC2;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CropPumpkin extends CropCard
{

	public CropPumpkin()
	{
	}

	public String name()
	{
		return "Pumpkin";
	}

	public String discoveredBy()
	{
		return "Notch";
	}

	public int tier()
	{
		return 1;
	}

	public int stat(int n)
	{
		switch (n)
		{
		case 0: // '\0'
			return 0;

		case 1: // '\001'
			return 1;

		case 2: // '\002'
			return 0;

		case 3: // '\003'
			return 3;

		case 4: // '\004'
			return 1;
		}
		return 0;
	}

	public String[] attributes()
	{
		return (new String[] {
			"Orange", "Decoration", "Stem"
		});
	}

	public int maxSize()
	{
		return 4;
	}

	public boolean canGrow(ICropTile crop)
	{
		return crop.getSize() <= 3;
	}

	public int weightInfluences(ICropTile crop, float humidity, float nutrients, float air)
	{
		return (int)((double)humidity * 1.1000000000000001D + (double)nutrients * 0.90000000000000002D + (double)air);
	}

	public boolean canBeHarvested(ICropTile crop)
	{
		return crop.getSize() == 4;
	}

	public ItemStack getGain(ICropTile crop)
	{
		return new ItemStack(Block.pumpkin);
	}

	public ItemStack getSeeds(ICropTile crop)
	{
		if (crop.getGain() <= 1 && crop.getGrowth() <= 1 && crop.getResistance() <= 1)
			return new ItemStack(Item.pumpkinSeeds, IC2.random.nextInt(3) + 1);
		else
			return super.getSeeds(crop);
	}

	public int growthDuration(ICropTile crop)
	{
		return crop.getSize() != 3 ? 200 : 600;
	}

	public byte getSizeAfterHarvest(ICropTile crop)
	{
		return 3;
	}
}
