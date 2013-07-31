// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CropRedWheat.java

package ic2.core.block.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import java.util.Random;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class CropRedWheat extends CropCard
{

	public CropRedWheat()
	{
	}

	public String name()
	{
		return "Redwheat";
	}

	public String discoveredBy()
	{
		return "raa1337";
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
			return 3;

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
			"Red", "Redstone", "Wheat"
		});
	}

	public int maxSize()
	{
		return 7;
	}

	public boolean canGrow(ICropTile crop)
	{
		return crop.getSize() < 7 && crop.getLightLevel() <= 10 && crop.getLightLevel() >= 5;
	}

	public boolean canBeHarvested(ICropTile crop)
	{
		return crop.getSize() == 7;
	}

	public float dropGainChance()
	{
		return 0.5F;
	}

	public ItemStack getGain(ICropTile crop)
	{
		ChunkCoordinates coords = crop.getLocation();
		if (crop.getWorld().isBlockIndirectlyGettingPowered(coords.posX, coords.posY, coords.posZ) || crop.getWorld().rand.nextBoolean())
			return new ItemStack(Item.redstone, 1);
		else
			return new ItemStack(Item.wheat, 1);
	}

	public int emitRedstone(ICropTile crop)
	{
		return crop.getSize() != 7 ? 0 : 15;
	}

	public int getEmittedLight(ICropTile crop)
	{
		return crop.getSize() != 7 ? 0 : 7;
	}

	public int growthDuration(ICropTile crop)
	{
		return 600;
	}

	public byte getSizeAfterHarvest(ICropTile crop)
	{
		return 2;
	}
}
