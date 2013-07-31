// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CropMelon.java

package ic2.core.block.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import ic2.core.IC2;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CropMelon extends CropCard
{

	public CropMelon()
	{
	}

	public String name()
	{
		return "Melon";
	}

	public String discoveredBy()
	{
		return "Chao";
	}

	public int tier()
	{
		return 2;
	}

	public int stat(int n)
	{
		switch (n)
		{
		case 0: // '\0'
			return 0;

		case 1: // '\001'
			return 4;

		case 2: // '\002'
			return 0;

		case 3: // '\003'
			return 2;

		case 4: // '\004'
			return 0;
		}
		return 0;
	}

	public String[] attributes()
	{
		return (new String[] {
			"Green", "Food", "Stem"
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
		if (IC2.random.nextInt(3) == 0)
			return new ItemStack(Block.melon);
		else
			return new ItemStack(Item.melon, IC2.random.nextInt(4) + 2);
	}

	public ItemStack getSeeds(ICropTile crop)
	{
		if (crop.getGain() <= 1 && crop.getGrowth() <= 1 && crop.getResistance() <= 1)
			return new ItemStack(Item.melonSeeds, IC2.random.nextInt(2) + 1);
		else
			return super.getSeeds(crop);
	}

	public int growthDuration(ICropTile crop)
	{
		return crop.getSize() != 3 ? 250 : 700;
	}

	public byte getSizeAfterHarvest(ICropTile crop)
	{
		return 3;
	}
}
