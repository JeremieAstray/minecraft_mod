// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdvRecipeGenerator.java

package ic2.cgIntegration.core;

import CraftGuide.API.*;
import ic2.core.*;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class AdvRecipeGenerator extends CraftGuideAPIObject
	implements IRecipeProvider
{

	private final CraftGuide.API.ICraftGuideRecipe.ItemSlot slots[] = {
		new CraftGuide.API.ICraftGuideRecipe.ItemSlot(3, 3, 16, 16, 0), new CraftGuide.API.ICraftGuideRecipe.ItemSlot(21, 3, 16, 16, 1), new CraftGuide.API.ICraftGuideRecipe.ItemSlot(39, 3, 16, 16, 2), new CraftGuide.API.ICraftGuideRecipe.ItemSlot(3, 21, 16, 16, 3), new CraftGuide.API.ICraftGuideRecipe.ItemSlot(21, 21, 16, 16, 4), new CraftGuide.API.ICraftGuideRecipe.ItemSlot(39, 21, 16, 16, 5), new CraftGuide.API.ICraftGuideRecipe.ItemSlot(3, 39, 16, 16, 6), new CraftGuide.API.ICraftGuideRecipe.ItemSlot(21, 39, 16, 16, 7), new CraftGuide.API.ICraftGuideRecipe.ItemSlot(39, 39, 16, 16, 8), new CraftGuide.API.ICraftGuideRecipe.ItemSlot(59, 21, 16, 16, 9, true)
	};
	private final CraftGuide.API.ICraftGuideRecipe.ItemSlot smallSlots[] = {
		new CraftGuide.API.ICraftGuideRecipe.ItemSlot(12, 12, 16, 16, 0), new CraftGuide.API.ICraftGuideRecipe.ItemSlot(30, 12, 16, 16, 1), new CraftGuide.API.ICraftGuideRecipe.ItemSlot(12, 30, 16, 16, 2), new CraftGuide.API.ICraftGuideRecipe.ItemSlot(30, 30, 16, 16, 3), new CraftGuide.API.ICraftGuideRecipe.ItemSlot(59, 21, 16, 16, 4, true)
	};

	public AdvRecipeGenerator()
	{
	}

	public void generateRecipes(IRecipeGenerator generator)
	{
		IRecipeTemplate shapedTemplate = generator.createRecipeTemplate(slots, Ic2Items.machine.copy(), "/gui/CraftGuideRecipe.png", 1, 1, 82, 1);
		IRecipeTemplate shapedSmallTemplate = generator.createRecipeTemplate(smallSlots, Ic2Items.machine.copy(), "/gui/CraftGuideRecipe.png", 1, 61, 82, 61);
		Iterator i$ = CraftingManager.getInstance().getRecipeList().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			Object o = i$.next();
			if (o instanceof AdvRecipe)
			{
				AdvRecipe recipe = (AdvRecipe)o;
				if (AdvRecipe.canShow(recipe))
				{
					ItemStack temp[] = new ItemStack[9];
					for (int i = 0; i < 9 && i < recipe.input.length; i++)
					{
						if (recipe.input[i] == null)
							continue;
						List inputs = AdvRecipe.resolveOreDict(recipe.input[i]);
						if (inputs.size() > 0)
							temp[i] = (ItemStack)inputs.get(0);
						else
							System.out.println((new StringBuilder()).append("[IC2] craftguide recipe gen: can't find ore dict match for ").append(recipe.input[i]).toString());
					}

					switch (recipe.inputWidth)
					{
					case 1: // '\001'
						temp = (new ItemStack[] {
							null, temp[0], null, null, temp[1], null, null, temp[2], null, recipe.output
						});
						break;

					case 2: // '\002'
						temp = (new ItemStack[] {
							null, temp[0], temp[1], null, temp[2], temp[3], null, temp[4], temp[5], recipe.output
						});
						break;

					default:
						temp = (new ItemStack[] {
							temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7], temp[8], recipe.output
						});
						break;
					}
					if (temp[2] == null && temp[5] == null && temp[8] == null && temp[6] == null && temp[7] == null)
						generator.addRecipe(shapedSmallTemplate, new ItemStack[] {
							temp[0], temp[1], temp[6], temp[7], temp[9]
						});
					else
						generator.addRecipe(shapedTemplate, temp);
				}
			}
		} while (true);
		IRecipeTemplate shapelessTemplate = generator.createRecipeTemplate(slots, Ic2Items.machine.copy(), "/gui/CraftGuideRecipe.png", 1, 121, 82, 121);
		Iterator i$ = CraftingManager.getInstance().getRecipeList().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			Object o = i$.next();
			if (o instanceof AdvShapelessRecipe)
			{
				AdvShapelessRecipe recipe = (AdvShapelessRecipe)o;
				if (AdvRecipe.canShow(recipe))
				{
					ItemStack temp[] = new ItemStack[10];
					for (int i = 0; i < 9 && i < recipe.input.length; i++)
					{
						List inputs = AdvRecipe.resolveOreDict(recipe.input[i]);
						if (inputs.size() > 0)
							temp[i] = (ItemStack)inputs.get(0);
						else
							System.out.println((new StringBuilder()).append("[IC2] craftguide recipe gen: can't find ore dict match for ").append(recipe.input[i]).toString());
					}

					temp[9] = recipe.output;
					generator.addRecipe(shapelessTemplate, temp);
				}
			}
		} while (true);
	}
}
