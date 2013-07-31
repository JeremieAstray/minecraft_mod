// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CropStickReed.java

package ic2.core.block.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CropStickReed extends CropCard
{

	public CropStickReed()
	{
	}

	public String name()
	{
		return "Stickreed";
	}

	public String discoveredBy()
	{
		return "raa1337";
	}

	public int tier()
	{
		return 4;
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
			return 1;

		case 3: // '\003'
			return 0;

		case 4: // '\004'
			return 1;
		}
		return 0;
	}

	public String[] attributes()
	{
		return (new String[] {
			"Reed", "Resin"
		});
	}

	public int maxSize()
	{
		return 4;
	}

	public boolean canGrow(ICropTile crop)
	{
		return crop.getSize() < 4;
	}

	public int weightInfluences(ICropTile crop, float humidity, float nutrients, float air)
	{
		return (int)((double)humidity * 1.2D + (double)nutrients + (double)air * 0.80000000000000004D);
	}

	public boolean canBeHarvested(ICropTile crop)
	{
		return crop.getSize() > 1;
	}

	public ItemStack getGain(ICropTile crop)
	{
		if (crop.getSize() <= 3)
			return new ItemStack(Item.reed, crop.getSize() - 1);
		else
			return new ItemStack(Ic2Items.resin.getItem());
	}

	public byte getSizeAfterHarvest(ICropTile crop)
	{
		if (crop.getSize() == 4)
			return (byte)(3 - IC2.random.nextInt(3));
		else
			return 1;
	}

	public boolean onEntityCollision(ICropTile crop, Entity entity)
	{
		return false;
	}

	public int growthDuration(ICropTile crop)
	{
		return crop.getSize() != 4 ? 100 : 400;
	}
}
