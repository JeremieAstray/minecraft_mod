// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GradualRecipeHandler.java

package ic2.neiIntegration.core;

import codechicken.nei.NEIClientUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.RecipeInfo;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ic2.core.RecipeGradual;
import ic2.core.item.ItemGradual;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.src.ModLoader;

public class GradualRecipeHandler extends TemplateRecipeHandler
{
	public class CachedGradualRecipe extends codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe
	{

		ArrayList ingredients;
		PositionedStack result;
		int restoreAmount;
		int capacity;
		final GradualRecipeHandler this$0;

		public ArrayList getIngredients()
		{
			return getCycledIngredients(cycleticks / 20, ingredients);
		}

		public PositionedStack getResult()
		{
			return result;
		}

		public CachedGradualRecipe(RecipeGradual recipe)
		{
			this$0 = GradualRecipeHandler.this;
			super(GradualRecipeHandler.this);
			result = new PositionedStack(recipe.getRecipeOutput(), 119, 24);
			ingredients = new ArrayList();
			ItemStack chargeMaterial = (ItemStack)ModLoader.getPrivateValue(ic2/core/RecipeGradual, recipe, 1);
			ingredients.add(new PositionedStack(recipe.getRecipeOutput(), 25 + GradualRecipeHandler.stackorder[0][0] * 18, 6 + GradualRecipeHandler.stackorder[0][1] * 18));
			ingredients.add(new PositionedStack(chargeMaterial, 25 + GradualRecipeHandler.stackorder[1][0] * 18, 6 + GradualRecipeHandler.stackorder[1][1] * 18));
			restoreAmount = 0;
			ItemGradual resultItem = (ItemGradual)recipe.getRecipeOutput().getItem();
			capacity = resultItem.getMaxDamageEx();
			restoreAmount = recipe.amount;
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

	public GradualRecipeHandler()
	{
	}

	public transient void loadCraftingRecipes(String outputId, Object results[])
	{
		if (outputId.equals("ic2.gradual") && getClass() == ic2/neiIntegration/core/GradualRecipeHandler)
		{
			List allrecipes = CraftingManager.getInstance().getRecipeList();
			Iterator i$ = allrecipes.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				IRecipe irecipe = (IRecipe)i$.next();
				if (irecipe instanceof RecipeGradual)
					arecipes.add(new CachedGradualRecipe((RecipeGradual)irecipe));
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
			if (irecipe instanceof RecipeGradual)
			{
				CachedGradualRecipe recipe = new CachedGradualRecipe((RecipeGradual)irecipe);
				if (recipe.contains(recipe.ingredients, ingredient))
					arecipes.add(recipe);
			}
		} while (true);
	}

	public void loadTransferRects()
	{
		transferRects.add(new codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect(new java.awt.Rectangle(84, 23, 24, 18), "ic2.gradual", new Object[0]));
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
			if ((irecipe instanceof RecipeGradual) && NEIClientUtils.areStacksSameTypeCrafting(irecipe.getRecipeOutput(), result))
				arecipes.add(new CachedGradualRecipe((RecipeGradual)irecipe));
		} while (true);
	}

	public List handleItemTooltip(GuiRecipe guiRecipe, ItemStack stack, List currenttip, int recipe)
	{
		CachedGradualRecipe crecipe = (CachedGradualRecipe)arecipes.get(recipe);
		if (guiRecipe.isMouseOver((PositionedStack)crecipe.ingredients.get(1), recipe) && crecipe.restoreAmount > 0 && crecipe.capacity > 0)
			currenttip.add((new StringBuilder()).append("\2477Can restore ").append(Math.round((100F * (float)crecipe.restoreAmount) / (float)crecipe.capacity)).append("%").toString());
		return currenttip;
	}

	public String getOverlayIdentifier()
	{
		return "crafting";
	}

	public boolean hasOverlay(GuiContainer gui, Container container, int recipe)
	{
		return RecipeInfo.hasDefaultOverlay(gui, getOverlayIdentifier()) || RecipeInfo.hasOverlayHandler(gui, getOverlayIdentifier());
	}

	public String getRecipeName()
	{
		return "IC2 Recharging";
	}

	public String getGuiTexture()
	{
		return "/gui/crafting.png";
	}

}
