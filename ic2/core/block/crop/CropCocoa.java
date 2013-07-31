// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CropCocoa.java

package ic2.core.block.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CropCocoa extends CropCard
{

	public CropCocoa()
	{
	}

	public String name()
	{
		return "Cocoa";
	}

	public String discoveredBy()
	{
		return "Notch";
	}

	public int tier()
	{
		return 3;
	}

	public int stat(int n)
	{
		switch (n)
		{
		case 0: // '\0'
			return 1;

		case 1: // '\001'
			return 3;

		case 2: // '\002'
			return 0;

		case 3: // '\003'
			return 4;

		case 4: // '\004'
			return 0;
		}
		return 0;
	}

	public String[] attributes()
	{
		return (new String[] {
			"Brown", "Food", "Stem"
		});
	}

	public int maxSize()
	{
		return 4;
	}

	public boolean canGrow(ICropTile crop)
	{
		return crop.getSize() <= 3 && crop.getNutrients() >= 3;
	}

	public int weightInfluences(ICropTile crop, float humidity, float nutrients, float air)
	{
		return (int)((double)humidity * 0.80000000000000004D + (double)nutrients * 1.3D + (double)air * 0.90000000000000002D);
	}

	public boolean canBeHarvested(ICropTile crop)
	{
		return crop.getSize() == 4;
	}

	public ItemStack getGain(ICropTile crop)
	{
		return new ItemStack(Item.dyePowder, 1, 3);
	}

	public int growthDuration(ICropTile crop)
	{
		return crop.getSize() != 3 ? 400 : 900;
	}

	public byte getSizeAfterHarvest(ICropTile crop)
	{
		return 3;
	}
}
