// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ScrapboxRecipeHandler.java

package ic2.neiIntegration.core;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.forge.GuiContainerManager;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.Recipes;
import ic2.core.Ic2Items;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ScrapboxRecipeHandler extends TemplateRecipeHandler
{
	public class CachedScrapboxRecipe extends codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe
	{

		public PositionedStack output;
		public Float chance;
		final ScrapboxRecipeHandler this$0;

		public PositionedStack getIngredient()
		{
			return ScrapboxRecipeHandler.scrapboxPositionedStack;
		}

		public PositionedStack getResult()
		{
			return output;
		}

		public CachedScrapboxRecipe(java.util.Map.Entry entry)
		{
			this$0 = ScrapboxRecipeHandler.this;
			super(ScrapboxRecipeHandler.this);
			output = new PositionedStack(entry.getKey(), 111, 24);
			chance = (Float)entry.getValue();
		}
	}


	public static DecimalFormat liquidAmountFormat = new DecimalFormat("0.##%");
	public static PositionedStack scrapboxPositionedStack;

	public ScrapboxRecipeHandler()
	{
	}

	public String getRecipeName()
	{
		return "Scrapbox";
	}

	public String getRecipeId()
	{
		return "ic2.scrapbox";
	}

	public String getGuiTexture()
	{
		return "/mods/ic2/textures/gui/ScrapboxRecipes.png";
	}

	public void drawExtras(GuiContainerManager gui, int recipe)
	{
		Float chance = ((CachedScrapboxRecipe)arecipes.get(recipe)).chance;
		String costString = liquidAmountFormat.format(chance);
		gui.drawTextCentered(costString, 85, 11, 0xff808080, false);
	}

	public void loadTransferRects()
	{
		transferRects.add(new codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect(new Rectangle(74, 23, 25, 16), getRecipeId(), new Object[0]));
	}

	protected java.util.List getRecipeList()
	{
		java.util.List result = new Vector();
		Map input = null;
		input = Recipes.scrapboxDrops.getRecipes();
		java.util.Map.Entry entry;
		for (Iterator i$ = input.entrySet().iterator(); i$.hasNext(); result.add(new java.util.AbstractMap.SimpleEntry(entry.getKey(), entry.getValue())))
			entry = (java.util.Map.Entry)i$.next();

		return result;
	}

	public transient void loadCraftingRecipes(String outputId, Object results[])
	{
		if (outputId.equals(getRecipeId()))
		{
			java.util.Map.Entry irecipe;
			for (Iterator i$ = getRecipeList().iterator(); i$.hasNext(); arecipes.add(new CachedScrapboxRecipe(irecipe)))
				irecipe = (java.util.Map.Entry)i$.next();

		} else
		{
			super.loadCraftingRecipes(outputId, results);
		}
	}

	public void loadCraftingRecipes(ItemStack result)
	{
		Iterator i$ = getRecipeList().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			java.util.Map.Entry irecipe = (java.util.Map.Entry)i$.next();
			if (NEIServerUtils.areStacksSameTypeCrafting((ItemStack)irecipe.getKey(), result))
				arecipes.add(new CachedScrapboxRecipe(irecipe));
		} while (true);
	}

	public void loadUsageRecipes(ItemStack ingredient)
	{
		if (NEIServerUtils.areStacksSameTypeCrafting(Ic2Items.scrapBox, ingredient))
		{
			java.util.Map.Entry irecipe;
			for (Iterator i$ = getRecipeList().iterator(); i$.hasNext(); arecipes.add(new CachedScrapboxRecipe(irecipe)))
				irecipe = (java.util.Map.Entry)i$.next();

		}
	}

	public boolean hasOverlay(GuiContainer gui, Container container, int recipe)
	{
		return false;
	}

	static 
	{
		scrapboxPositionedStack = new PositionedStack(Ic2Items.scrapBox, 51, 24);
	}
}
