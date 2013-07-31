// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CropAurelia.java

package ic2.core.block.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CropAurelia extends CropCard
{

	public CropAurelia()
	{
	}

	public String name()
	{
		return "Aurelia";
	}

	public int tier()
	{
		return 8;
	}

	public int stat(int n)
	{
		switch (n)
		{
		case 0: // '\0'
			return 2;

		case 1: // '\001'
			return 0;

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
			"Gold", "Leaves", "Metal"
		});
	}

	public int maxSize()
	{
		return 5;
	}

	public boolean canGrow(ICropTile crop)
	{
		if (crop.getSize() < 4)
			return true;
		return crop.getSize() == 4 && crop.isBlockBelow(Block.oreGold);
	}

	public boolean canBeHarvested(ICropTile crop)
	{
		return crop.getSize() == 5;
	}

	public ItemStack getGain(ICropTile crop)
	{
		return new ItemStack(Item.goldNugget);
	}

	public int growthDuration(ICropTile crop)
	{
		return crop.getSize() != 4 ? 750 : 2200;
	}

	public byte getSizeAfterHarvest(ICropTile crop)
	{
		return 2;
	}
}
