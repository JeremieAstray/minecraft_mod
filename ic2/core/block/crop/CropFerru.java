// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CropFerru.java

package ic2.core.block.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import ic2.core.Ic2Items;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class CropFerru extends CropCard
{

	public CropFerru()
	{
	}

	public String name()
	{
		return "Ferru";
	}

	public int tier()
	{
		return 6;
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
			return 1;

		case 4: // '\004'
			return 0;
		}
		return 0;
	}

	public String[] attributes()
	{
		return (new String[] {
			"Gray", "Leaves", "Metal"
		});
	}

	public int maxSize()
	{
		return 4;
	}

	public boolean canGrow(ICropTile crop)
	{
		if (crop.getSize() < 3)
			return true;
		return crop.getSize() == 3 && crop.isBlockBelow(Block.oreIron);
	}

	public boolean canBeHarvested(ICropTile crop)
	{
		return crop.getSize() == 4;
	}

	public ItemStack getGain(ICropTile crop)
	{
		return new ItemStack(Ic2Items.smallIronDust.getItem());
	}

	public float dropGainChance()
	{
		return super.dropGainChance() / 2.0F;
	}

	public int growthDuration(ICropTile crop)
	{
		return crop.getSize() != 3 ? 800 : 2000;
	}

	public byte getSizeAfterHarvest(ICropTile crop)
	{
		return 2;
	}
}
