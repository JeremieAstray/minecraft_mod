// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BasicMachineRecipeManager.java

package ic2.core;

import ic2.api.recipe.IMachineRecipeManager;
import java.util.*;
import net.minecraft.item.ItemStack;

public class BasicMachineRecipeManager
	implements IMachineRecipeManager
{

	private final Map recipes = new HashMap();

	public BasicMachineRecipeManager()
	{
	}

	public void addRecipe(ItemStack input, Object output)
	{
		recipes.put(input, output);
	}

	public Object getOutputFor(ItemStack input, boolean adjustInput)
	{
		if (input == null)
			return null;
		for (Iterator i$ = recipes.entrySet().iterator(); i$.hasNext();)
		{
			java.util.Map.Entry entry = (java.util.Map.Entry)i$.next();
			ItemStack recipeInput = (ItemStack)entry.getKey();
			if (input.itemID == recipeInput.itemID && (input.getItemDamage() == recipeInput.getItemDamage() || recipeInput.getItemDamage() == 32767) && input.stackSize >= recipeInput.stackSize)
			{
				if (adjustInput)
					input.stackSize -= recipeInput.stackSize;
				return entry.getValue();
			}
		}

		return null;
	}

	public Map getRecipes()
	{
		return recipes;
	}
}
