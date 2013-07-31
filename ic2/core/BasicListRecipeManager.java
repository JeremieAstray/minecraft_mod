// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BasicListRecipeManager.java

package ic2.core;

import ic2.api.recipe.IListRecipeManager;
import java.util.*;
import net.minecraft.item.ItemStack;

public class BasicListRecipeManager
	implements IListRecipeManager
{

	private final List list = new ArrayList();

	public BasicListRecipeManager()
	{
	}

	public Iterator iterator()
	{
		return list.iterator();
	}

	public void add(ItemStack stack)
	{
		list.add(stack);
	}

	public boolean contains(ItemStack input)
	{
		if (input == null)
			return false;
		for (Iterator i$ = list.iterator(); i$.hasNext();)
		{
			ItemStack stack = (ItemStack)i$.next();
			if (input.itemID == stack.itemID && (input.getItemDamage() == stack.getItemDamage() || stack.getItemDamage() == 32767) && input.stackSize >= stack.stackSize)
				return true;
		}

		return false;
	}

	public List getStacks()
	{
		return list;
	}
}
