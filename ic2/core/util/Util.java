// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Util.java

package ic2.core.util;

import ic2.core.init.InternalName;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public final class Util
{

	public Util()
	{
	}

	public static int roundToNegInf(float x)
	{
		int ret = (int)x;
		if ((float)ret > x)
			ret--;
		return ret;
	}

	public static int roundToNegInf(double x)
	{
		int ret = (int)x;
		if ((double)ret > x)
			ret--;
		return ret;
	}

	public static int countInArray(Object oa[], Class cls)
	{
		int ret = 0;
		Object arr$[] = oa;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Object o = arr$[i$];
			if (cls.isAssignableFrom(o.getClass()))
				ret++;
		}

		return ret;
	}

	public static InternalName getColorName(int color)
	{
		switch (color)
		{
		case 0: // '\0'
			return InternalName.black;

		case 1: // '\001'
			return InternalName.red;

		case 2: // '\002'
			return InternalName.green;

		case 3: // '\003'
			return InternalName.brown;

		case 4: // '\004'
			return InternalName.blue;

		case 5: // '\005'
			return InternalName.purple;

		case 6: // '\006'
			return InternalName.cyan;

		case 7: // '\007'
			return InternalName.lightGray;

		case 8: // '\b'
			return InternalName.gray;

		case 9: // '\t'
			return InternalName.pink;

		case 10: // '\n'
			return InternalName.lime;

		case 11: // '\013'
			return InternalName.yellow;

		case 12: // '\f'
			return InternalName.lightBlue;

		case 13: // '\r'
			return InternalName.magenta;

		case 14: // '\016'
			return InternalName.orange;

		case 15: // '\017'
			return InternalName.white;
		}
		return null;
	}

	public static boolean inDev()
	{
		return System.getProperty("INDEV") != null;
	}

	public static boolean matchesOD(ItemStack stack, Object match)
	{
		if (match instanceof ItemStack)
			return stack == null || stack.isItemEqual((ItemStack)match);
		if (match instanceof String)
		{
			if (stack == null)
				return false;
			int id = OreDictionary.getOreID(stack);
			if (id == -1)
				return false;
			for (Iterator i$ = OreDictionary.getOres(Integer.valueOf(id)).iterator(); i$.hasNext();)
			{
				ItemStack ore = (ItemStack)i$.next();
				if (OreDictionary.itemMatches(stack, ore, false))
					return true;
			}

			return false;
		} else
		{
			return stack == match;
		}
	}
}
