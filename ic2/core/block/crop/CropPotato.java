// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CropPotato.java

package ic2.core.block.crop;

import ic2.api.crops.ICropTile;
import java.util.Random;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

// Referenced classes of package ic2.core.block.crop:
//			CropSeedFood

public class CropPotato extends CropSeedFood
{

	public CropPotato()
	{
		super("Potato", "Yellow", new ItemStack(Item.potato));
	}

	public ItemStack getGain(ICropTile crop)
	{
		return crop.getWorld().rand.nextInt(50) != 0 ? super.getGain(crop) : new ItemStack(Item.poisonousPotato);
	}
}
