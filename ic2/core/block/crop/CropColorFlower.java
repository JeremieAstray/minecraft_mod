// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CropColorFlower.java

package ic2.core.block.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CropColorFlower extends CropCard
{

	public String name;
	public String attributes[];
	public int color;

	public CropColorFlower(String n, String a[], int c)
	{
		name = n;
		attributes = a;
		color = c;
	}

	public String discoveredBy()
	{
		if (name.equals("Dandelion") || name.equals("Rose"))
			return "Notch";
		else
			return "Alblaka";
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
			return 1;

		case 1: // '\001'
			return 1;

		case 2: // '\002'
			return 0;

		case 3: // '\003'
			return 5;

		case 4: // '\004'
			return 1;
		}
		return 0;
	}

	public String[] attributes()
	{
		return attributes;
	}

	public int maxSize()
	{
		return 4;
	}

	public boolean canGrow(ICropTile crop)
	{
		return crop.getSize() <= 3 && crop.getLightLevel() >= 12;
	}

	public boolean canBeHarvested(ICropTile crop)
	{
		return crop.getSize() == 4;
	}

	public ItemStack getGain(ICropTile crop)
	{
		return new ItemStack(Item.dyePowder, 1, color);
	}

	public byte getSizeAfterHarvest(ICropTile crop)
	{
		return 3;
	}

	public int growthDuration(ICropTile crop)
	{
		return crop.getSize() != 3 ? 400 : 600;
	}
}
