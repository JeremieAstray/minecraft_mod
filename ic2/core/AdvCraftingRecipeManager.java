// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdvCraftingRecipeManager.java

package ic2.core;

import ic2.api.recipe.ICraftingRecipeManager;
import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.core:
//			AdvRecipe, AdvShapelessRecipe

public class AdvCraftingRecipeManager
	implements ICraftingRecipeManager
{

	public AdvCraftingRecipeManager()
	{
	}

	public transient void addRecipe(ItemStack output, Object input[])
	{
		AdvRecipe.addAndRegister(output, input);
	}

	public transient void addShapelessRecipe(ItemStack output, Object input[])
	{
		AdvShapelessRecipe.addAndRegister(output, input);
	}
}
