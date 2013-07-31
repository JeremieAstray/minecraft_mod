// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CropWeed.java

package ic2.core.block.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class CropWeed extends CropCard
{

	public CropWeed()
	{
	}

	public String name()
	{
		return "Weed";
	}

	public int tier()
	{
		return 0;
	}

	public int stat(int n)
	{
		switch (n)
		{
		case 0: // '\0'
			return 0;

		case 1: // '\001'
			return 0;

		case 2: // '\002'
			return 1;

		case 3: // '\003'
			return 0;

		case 4: // '\004'
			return 5;
		}
		return 0;
	}

	public String[] attributes()
	{
		return (new String[] {
			"Weed", "Bad"
		});
	}

	public int maxSize()
	{
		return 3;
	}

	public boolean canGrow(ICropTile crop)
	{
		return crop.getSize() < 3;
	}

	public boolean leftclick(ICropTile crop, EntityPlayer player)
	{
		crop.reset();
		return true;
	}

	public boolean canBeHarvested(ICropTile crop)
	{
		return false;
	}

	public ItemStack getGain(ICropTile crop)
	{
		return null;
	}

	public int growthDuration(ICropTile crop)
	{
		return 300;
	}

	public boolean onEntityCollision(ICropTile crop, Entity entity)
	{
		return false;
	}
}
