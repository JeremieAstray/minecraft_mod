// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CropNetherWart.java

package ic2.core.block.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import ic2.core.block.TileEntityCrop;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

// Referenced classes of package ic2.core.block.crop:
//			IC2Crops

public class CropNetherWart extends CropCard
{

	public CropNetherWart()
	{
	}

	public String name()
	{
		return "Nether Wart";
	}

	public String discoveredBy()
	{
		return "Notch";
	}

	public int tier()
	{
		return 5;
	}

	public int stat(int n)
	{
		switch (n)
		{
		case 0: // '\0'
			return 4;

		case 1: // '\001'
			return 2;

		case 2: // '\002'
			return 0;

		case 3: // '\003'
			return 2;

		case 4: // '\004'
			return 1;
		}
		return 0;
	}

	public String[] attributes()
	{
		return (new String[] {
			"Red", "Nether", "Ingredient", "Soulsand"
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

	public boolean canBeHarvested(ICropTile crop)
	{
		return crop.getSize() == 3;
	}

	public float dropGainChance()
	{
		return 2.0F;
	}

	public ItemStack getGain(ICropTile crop)
	{
		return new ItemStack(Item.netherStalkSeeds, 1);
	}

	public void tick(ICropTile crop)
	{
		TileEntityCrop te = (TileEntityCrop)crop;
		if (te.isBlockBelow(Block.slowSand))
		{
			if (canGrow(te))
				te.growthPoints += (double)te.calcGrowthRate() * 0.5D;
		} else
		if (te.isBlockBelow(Block.blockSnow) && crop.getWorld().rand.nextInt(300) == 0)
			te.id = (short)IC2Crops.cropTerraWart.getId();
	}
}
