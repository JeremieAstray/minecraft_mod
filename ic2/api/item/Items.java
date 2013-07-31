// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Items.java

package ic2.api.item;

import java.io.PrintStream;
import java.lang.reflect.Field;
import net.minecraft.item.ItemStack;

public final class Items
{

	private static Class Ic2Items;

	public Items()
	{
	}

	public static ItemStack getItem(String name)
	{
		Object ret;
		if (Ic2Items == null)
			Ic2Items = Class.forName((new StringBuilder()).append(getPackage()).append(".core.Ic2Items").toString());
		ret = Ic2Items.getField(name).get(null);
		if (ret instanceof ItemStack)
			return (ItemStack)ret;
		return null;
		Exception e;
		e;
		System.out.println((new StringBuilder()).append("IC2 API: Call getItem failed for ").append(name).toString());
		return null;
	}

	private static String getPackage()
	{
		Package pkg = ic2/api/item/Items.getPackage();
		if (pkg != null)
		{
			String packageName = pkg.getName();
			return packageName.substring(0, packageName.length() - ".api.item".length());
		} else
		{
			return "ic2";
		}
	}
}
