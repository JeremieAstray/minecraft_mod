// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CropCoffee.java

package ic2.core.block.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import ic2.core.Ic2Items;
import net.minecraft.item.ItemStack;

public class CropCoffee extends CropCard
{

	public CropCoffee()
	{
	}

	public String name()
	{
		return "Coffee";
	}

	public String discoveredBy()
	{
		return "Snoochy";
	}

	public int tier()
	{
		return 7;
	}

	public int stat(int n)
	{
		switch (n)
		{
		case 0: // '\0'
			return 1;

		case 1: // '\001'
			return 4;

		case 2: // '\002'
			return 1;

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
			"Leaves", "Ingrident", "Beans"
		});
	}

	public int maxSize()
	{
		return 5;
	}

	public boolean canGrow(ICropTile crop)
	{
		return crop.getSize() < 5 && crop.getLightLevel() >= 9;
	}

	public int weightInfluences(ICropTile crop, float humidity, float nutrients, float air)
	{
		return (int)(0.40000000000000002D * (double)humidity + 1.3999999999999999D * (double)nutrients + 1.2D * (double)air);
	}

	public int growthDuration(ICropTile crop)
	{
		if (crop.getSize() == 3)
			return (int)((double)super.growthDuration(crop) * 0.5D);
		if (crop.getSize() == 4)
			return (int)((double)super.growthDuration(crop) * 1.5D);
		else
			return super.growthDuration(crop);
	}

	public boolean canBeHarvested(ICropTile crop)
	{
		return crop.getSize() >= 4;
	}

	public ItemStack getGain(ICropTile crop)
	{
		if (crop.getSize() == 4)
			return null;
		else
			return new ItemStack(Ic2Items.coffeeBeans.getItem());
	}

	public byte getSizeAfterHarvest(ICropTile crop)
	{
		return 3;
	}
}
