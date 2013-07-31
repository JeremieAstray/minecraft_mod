// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdvShapelessRecipeHandler.java

package ic2.neiIntegration.core;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.ShapelessRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ic2.api.item.IElectricItem;
import ic2.core.*;
import java.util.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class AdvShapelessRecipeHandler extends ShapelessRecipeHandler
{
	public class CachedShapelessRecipe2 extends codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe
	{

		ArrayList ingredients;
		PositionedStack result;
		public boolean isAvailable;
		final AdvShapelessRecipeHandler this$0;

		public void setIngredients(List items)
		{
			for (int ingred = 0; ingred < items.size(); ingred++)
			{
				Object obj = items.get(ingred);
				if (obj instanceof String)
				{
					obj = AdvRecipe.resolveOreDict(obj);
					if (((List)obj).isEmpty())
						return;
				}
				PositionedStack stack = new PositionedStack(obj, 25 + AdvShapelessRecipeHandler.stackorder[ingred][0] * 18, 6 + AdvShapelessRecipeHandler.stackorder[ingred][1] * 18);
				stack.setMaxSize(1);
				ingredients.add(stack);
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

		public CachedShapelessRecipe2(Object input[], ItemStack output)
		{
			this$0 = AdvShapelessRecipeHandler.this;
			super(AdvShapelessRecipeHandler.this);
			result = new PositionedStack(output, 119, 24);
			ingredients = new ArrayList();
			isAvailable = false;
			setIngredients(Arrays.asList(input));
		}
	}


	static final int stackorder[][] = {
		{
			0, 0
		}, {
			1, 0
		}, {
			0, 1
		}, {
			1, 1
		}, {
			0, 2
		}, {
			1, 2
		}, {
			2, 0
		}, {
			2, 1
		}, {
			2, 2
		}
	};

	public AdvShapelessRecipeHandler()
	{
	}

	public transient void loadCraftingRecipes(String outputId, Object results[])
	{
		if (outputId.equals("crafting") && getClass() == ic2/neiIntegration/core/AdvShapelessRecipeHandler)
		{
			List allrecipes = CraftingManager.getInstance().getRecipeList();
			Iterator i$ = allrecipes.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				IRecipe irecipe = (IRecipe)i$.next();
				if (irecipe instanceof AdvShapelessRecipe)
				{
					AdvShapelessRecipe advshapelessrecipe = (AdvShapelessRecipe)irecipe;
					if (!IC2.enableSecretRecipeHiding || AdvRecipe.canShow(advshapelessrecipe))
					{
						CachedShapelessRecipe2 recipe = new CachedShapelessRecipe2(advshapelessrecipe.input, advshapelessrecipe.output);
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
			if (irecipe instanceof AdvShapelessRecipe)
			{
				AdvShapelessRecipe advshapelessrecipe = (AdvShapelessRecipe)irecipe;
				if (!IC2.enableSecretRecipeHiding || AdvRecipe.canShow(advshapelessrecipe))
				{
					CachedShapelessRecipe2 recipe = new CachedShapelessRecipe2(advshapelessrecipe.input, advshapelessrecipe.output);
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
			if (irecipe instanceof AdvShapelessRecipe)
			{
				AdvShapelessRecipe advshapelessrecipe = (AdvShapelessRecipe)irecipe;
				if ((!IC2.enableSecretRecipeHiding || AdvRecipe.canShow(advshapelessrecipe)) && areStacksSameTypeCraftingIc2(advshapelessrecipe.output, result))
				{
					CachedShapelessRecipe2 recipe = new CachedShapelessRecipe2(advshapelessrecipe.input, advshapelessrecipe.output);
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
		return "Shapeless IC2 Crafting";
	}

}
