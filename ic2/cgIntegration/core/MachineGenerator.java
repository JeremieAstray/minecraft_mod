// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MachineGenerator.java

package ic2.cgIntegration.core;

import CraftGuide.API.*;
import java.util.*;
import net.minecraft.item.ItemStack;

public class MachineGenerator extends CraftGuideAPIObject
	implements IRecipeProvider
{

	private final CraftGuide.API.ICraftGuideRecipe.ItemSlot slots[] = {
		new CraftGuide.API.ICraftGuideRecipe.ItemSlot(13, 21, 16, 16, 0, true), new CraftGuide.API.ICraftGuideRecipe.ItemSlot(50, 21, 16, 16, 1, true)
	};
	private final List recipeMap;
	private final int backgroundX;
	private final int backgroundY;
	private final int backgroundSelectedX;
	private final int backgroundSelectedY;
	private final ItemStack craftingType;

	public MachineGenerator(List recipes, int backgroundX, int backgroundY, int backgroundSelectedX, int backgroundSelectedY, ItemStack craftingType)
	{
		recipeMap = recipes;
		this.backgroundX = backgroundX;
		this.backgroundY = backgroundY;
		this.backgroundSelectedX = backgroundSelectedX;
		this.backgroundSelectedY = backgroundSelectedY;
		this.craftingType = craftingType;
	}

	public void generateRecipes(IRecipeGenerator generator)
	{
		IRecipeTemplate template = generator.createRecipeTemplate(slots, craftingType, "/ic2/sprites/craftguide.png", backgroundX, backgroundY, backgroundSelectedX, backgroundSelectedY);
		java.util.Map.Entry entry;
		for (Iterator i$ = recipeMap.iterator(); i$.hasNext(); generator.addRecipe(template, new ItemStack[] {
	((ItemStack)entry.getKey()).copy(), ((ItemStack)entry.getValue()).copy()
}))
			entry = (java.util.Map.Entry)i$.next();

	}
}
