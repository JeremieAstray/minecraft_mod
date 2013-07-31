// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MachineRecipeHandler.java

package ic2.neiIntegration.core;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.forge.GuiContainerManager;
import codechicken.nei.recipe.TemplateRecipeHandler;
import java.awt.Rectangle;
import java.util.*;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public abstract class MachineRecipeHandler extends TemplateRecipeHandler
{
	public class CachedIORecipe extends codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe
	{

		public PositionedStack input;
		public PositionedStack output;
		final MachineRecipeHandler this$0;

		public PositionedStack getIngredient()
		{
			return input;
		}

		public PositionedStack getResult()
		{
			return output;
		}

		public CachedIORecipe(ItemStack itemstack, ItemStack itemstack1)
		{
			this$0 = MachineRecipeHandler.this;
			super(MachineRecipeHandler.this);
			input = new PositionedStack(itemstack, 51, 6);
			output = new PositionedStack(itemstack1, 111, 24);
		}

		public CachedIORecipe(java.util.Map.Entry recipe)
		{
			this((ItemStack)recipe.getKey(), (ItemStack)recipe.getValue());
		}
	}


	int ticks;

	public MachineRecipeHandler()
	{
	}

	public abstract String getRecipeName();

	public abstract String getRecipeId();

	public abstract String getGuiTexture();

	public abstract String getOverlayIdentifier();

	public abstract Map getRecipeList();

	public void drawBackground(GuiContainerManager guimanager, int i)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		guimanager.bindTexture(getGuiTexture());
		guimanager.drawTexturedModalRect(0, 0, 5, 11, 140, 65);
	}

	public void drawExtras(GuiContainerManager guimanager, int i)
	{
		float f = ticks < 20 ? 0.0F : (float)((ticks - 20) % 20) / 20F;
		drawProgressBar(guimanager, 74, 23, 176, 14, 25, 16, f, 0);
		f = ticks > 20 ? 1.0F : (float)ticks / 20F;
		drawProgressBar(guimanager, 51, 25, 176, 0, 14, 14, f, 3);
	}

	public void onUpdate()
	{
		super.onUpdate();
		ticks++;
	}

	public void loadTransferRects()
	{
		transferRects.add(new codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect(new Rectangle(74, 23, 25, 16), getRecipeId(), new Object[0]));
	}

	public transient void loadCraftingRecipes(String outputId, Object results[])
	{
		if (outputId.equals(getRecipeId()))
		{
			java.util.Map.Entry irecipe;
			for (Iterator i$ = getRecipeList().entrySet().iterator(); i$.hasNext(); arecipes.add(new CachedIORecipe(irecipe)))
				irecipe = (java.util.Map.Entry)i$.next();

		} else
		{
			super.loadCraftingRecipes(outputId, results);
		}
	}

	public void loadCraftingRecipes(ItemStack result)
	{
		Iterator i$ = getRecipeList().entrySet().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			java.util.Map.Entry irecipe = (java.util.Map.Entry)i$.next();
			if (NEIServerUtils.areStacksSameTypeCrafting((ItemStack)irecipe.getValue(), result))
				arecipes.add(new CachedIORecipe(irecipe));
		} while (true);
	}

	public void loadUsageRecipes(ItemStack ingredient)
	{
		Iterator i$ = getRecipeList().entrySet().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			java.util.Map.Entry irecipe = (java.util.Map.Entry)i$.next();
			if (NEIServerUtils.areStacksSameTypeCrafting((ItemStack)irecipe.getKey(), ingredient))
				arecipes.add(new CachedIORecipe(irecipe));
		} while (true);
	}
}
