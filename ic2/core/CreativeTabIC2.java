// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CreativeTabIC2.java

package ic2.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.core:
//			Ic2Items, IC2

public class CreativeTabIC2 extends CreativeTabs
{

	private static ItemStack laser;
	private static ItemStack a;
	private static ItemStack b;
	private static ItemStack z;
	private int ticker;

	public CreativeTabIC2()
	{
		super("IC2");
	}

	public ItemStack getIconItemStack()
	{
		if (laser == null)
			laser = Ic2Items.miningLaser.copy();
		if (IC2.seasonal)
		{
			if (a == null)
				a = new ItemStack(Item.skull, 1, 2);
			if (b == null)
				b = new ItemStack(Item.skull, 1, 0);
			if (z == null)
				z = Ic2Items.nanoBodyarmor.copy();
			if (++ticker >= 5000)
				ticker = 0;
			return ticker >= 2500 ? ticker >= 3000 ? ticker >= 4500 ? z : b : a : laser;
		} else
		{
			return laser;
		}
	}
}
