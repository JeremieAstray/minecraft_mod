// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MaceratorRecipeHandler.java

package ic2.neiIntegration.core;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.Recipes;
import ic2.core.block.machine.gui.GuiMacerator;
import java.util.Map;

// Referenced classes of package ic2.neiIntegration.core:
//			MachineRecipeHandler

public class MaceratorRecipeHandler extends MachineRecipeHandler
{

	public MaceratorRecipeHandler()
	{
	}

	public Class getGuiClass()
	{
		return ic2/core/block/machine/gui/GuiMacerator;
	}

	public String getRecipeName()
	{
		return "Macerator";
	}

	public String getRecipeId()
	{
		return "ic2.macerator";
	}

	public String getGuiTexture()
	{
		return "/mods/ic2/textures/gui/GUIMacerator.png";
	}

	public String getOverlayIdentifier()
	{
		return "macerator";
	}

	public Map getRecipeList()
	{
		return Recipes.macerator.getRecipes();
	}
}