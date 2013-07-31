// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CropSeedFood.java

package ic2.core.block.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import net.minecraft.item.ItemStack;

public class CropSeedFood extends CropCard
{

	private final String name;
	private final String color;
	private final ItemStack gain;

	public CropSeedFood(String name, String color, ItemStack gain)
	{
		this.name = name;
		this.color = color;
		this.gain = gain;
	}

	public String name()
	{
		return name;
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
			return 0;

		case 4: // '\004'
			return 2;
		}
		return 0;
	}

	public String[] attributes()
	{
		return (new String[] {
			color, "Food", name
		});
	}

	public int maxSize()
	{
		return 3;
	}

	public boolean canGrow(ICropTile crop)
	{
		return crop.getSize() < 3 && crop.getLightLevel() >= 9;
	}

	public boolean canBeHarvested(ICropTile crop)
	{
		return crop.getSize() == 3;
	}

	public ItemStack getGain(ICropTile crop)
	{
		return gain.copy();
	}

	public byte getSizeAfterHarvest(ICropTile crop)
	{
		return 1;
	}
}
