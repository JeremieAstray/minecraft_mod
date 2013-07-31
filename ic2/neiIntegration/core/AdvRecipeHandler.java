// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdvRecipeHandler.java

package ic2.neiIntegration.core;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.ShapedRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ic2.api.item.IElectricItem;
import ic2.core.AdvRecipe;
import ic2.core.IC2;
import java.util.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class AdvRecipeHandler extends ShapedRecipeHandler
{
	public class CachedShapedRecipe2 extends codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe
	{

		public ArrayList ingredients;
		public PositionedStack result;
		public boolean isAvailable;
		final AdvRecipeHandler this$0;

		public void setIngredients(int width, int height, Object items[])
		{
			for (int x = 0; x < width; x++)
			{
				for (int y = 0; y < height; y++)
				{
					Object obj = items[y * width + x];
					if (obj == null)
						continue;
					if (obj instanceof String)
					{
						obj = AdvRecipe.resolveOreDict(obj);
						if (((List)obj).isEmpty())
							return;
					}
					PositionedStack stack = new PositionedStack(obj, 25 + x * 18, 6 + y * 18);
					stack.setMaxSize(1);
					ingredients.add(stack);
				}

			}

			isAvailable = true;
		}

		public ArrayList getIngredients()
		{
			return getCycledIngredients(cycleticks / 20, ingredients);
		}

		public PositionedStack getResult()
		{
			return result;
		}

		public CachedShapedRecipe2(int width, int height, Object items[], ItemStack out)
		{
			this$0 = AdvRecipeHandler.this;
			super(AdvRecipeHandler.this);
			result = new PositionedStack(out, 119, 24);
			ingredients = new ArrayList();
			isAvailable = false;
			setIngredients(width, height, items);
		}
	}


	public AdvRecipeHandler()
	{
	}

	public transient void loadCraftingRecipes(String outputId, Object results[])
	{
		if (outputId.equals("crafting") && getClass() == ic2/neiIntegration/core/AdvRecipeHandler)
		{
			List allrecipes = CraftingManager.getInstance().getRecipeList();
			Iterator i$ = allrecipes.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				IRecipe irecipe = (IRecipe)i$.next();
				if (irecipe instanceof AdvRecipe)
				{
					AdvRecipe advrecipe = (AdvRecipe)irecipe;
					if (!IC2.enableSecretRecipeHiding || AdvRecipe.canShow(advrecipe))
					{
						CachedShapedRecipe2 recipe = new CachedShapedRecipe2(advrecipe.inputWidth, advrecipe.input.length / advrecipe.inputWidth, advrecipe.input, advrecipe.output);
						if (recipe.isAvailable)
							arecipes.add(recipe);
					}
				}
			} while (true);
		} else
		{
			super.loadCraftingRecipes(outputId, results);
		}
	}

	public void loadUsageRecipes(ItemStack ingredient)
	{
		List allrecipes = CraftingManager.getInstance().getRecipeList();
		Iterator i$ = allrecipes.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			IRecipe irecipe = (IRecipe)i$.next();
			if (irecipe instanceof AdvRecipe)
			{
				AdvRecipe advrecipe = (AdvRecipe)irecipe;
				if (!IC2.enableSecretRecipeHiding || AdvRecipe.canShow(advrecipe))
				{
					CachedShapedRecipe2 recipe = new CachedShapedRecipe2(advrecipe.inputWidth, advrecipe.input.length / advrecipe.inputWidth, advrecipe.input, advrecipe.output);
					if (recipe.isAvailable && containsIc2(recipe.ingredients, ingredient))
					{
						recipe.setIngredientPermutation(recipe.ingredients, ingredient);
						arecipes.add(recipe);
					}
				}
			}
		} while (true);
	}

	public void loadCraftingRecipes(ItemStack result)
	{
		List allrecipes = CraftingManager.getInstance().getRecipeList();
		Iterator i$ = allrecipes.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			IRecipe irecipe = (IRecipe)i$.next();
			if (irecipe instanceof AdvRecipe)
			{
				AdvRecipe advrecipe = (AdvRecipe)irecipe;
				if ((!IC2.enableSecretRecipeHiding || AdvRecipe.canShow(advrecipe)) && areStacksSameTypeCraftingIc2(advrecipe.output, result))
				{
					CachedShapedRecipe2 recipe = new CachedShapedRecipe2(advrecipe.inputWidth, advrecipe.input.length / advrecipe.inputWidth, advrecipe.input, advrecipe.output);
					if (recipe.isAvailable)
						arecipes.add(recipe);
				}
			}
		} while (true);
	}

	public boolean areStacksSameTypeCraftingIc2(ItemStack stack1, ItemStack stack2)
	{
		if (stack1 == null || stack2 == null)
			return false;
		else
			return stack1.itemID == stack2.itemID && (stack1.getItemDamage() == stack2.getItemDamage() || stack1.getItemDamage() == -1 || stack2.getItemDamage() == -1 || (stack1.getItem() instanceof IElectricItem) || stack1.getItem().isDamageable());
	}

	public boolean containsIc2(Collection ingredients, ItemStack ingredient)
	{
		for (Iterator i$ = ingredients.iterator(); i$.hasNext();)
		{
			PositionedStack stack = (PositionedStack)i$.next();
			ItemStack arr$[] = stack.items;
			int len$ = arr$.length;
			int i$ = 0;
			while (i$ < len$) 
			{
				ItemStack item = arr$[i$];
				if (areStacksSameTypeCraftingIc2(ingredient, item))
					return true;
				i$++;
			}
		}

		return false;
	}

	public String getRecipeName()
	{
		return "Shaped IC2 Crafting";
	}
}
