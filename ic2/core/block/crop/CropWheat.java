// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CropWheat.java

package ic2.core.block.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CropWheat extends CropCard
{

	public CropWheat()
	{
	}

	public String name()
	{
		return "Wheat";
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
			return 4;

		case 2: // '\002'
			return 0;

		case 3: // '\003'
			return 0;

		case 4: // '\004'
			return 2;
		}
		return 0;
	}

	public String[] attributes()
	{
		return (new String[] {
			"Yellow", "Food", "Wheat"
		});
	}

	public int maxSize()
	{
		return 7;
	}

	public boolean canGrow(ICropTile crop)
	{
		return crop.getSize() < 7 && crop.getLightLevel() >= 9;
	}

	public boolean canBeHarvested(ICropTile crop)
	{
		return crop.getSize() == 7;
	}

	public ItemStack getGain(ICropTile crop)
	{
		return new ItemStack(Item.wheat, 1);
	}

	public ItemStack getSeeds(ICropTile crop)
	{
		if (crop.getGain() <= 1 && crop.getGrowth() <= 1 && crop.getResistance() <= 1)
			return new ItemStack(Item.seeds);
		else
			return super.getSeeds(crop);
	}

	public byte getSizeAfterHarvest(ICropTile crop)
	{
		return 2;
	}
}
