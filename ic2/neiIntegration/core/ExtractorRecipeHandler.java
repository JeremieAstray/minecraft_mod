// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ExtractorRecipeHandler.java

package ic2.neiIntegration.core;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.Recipes;
import ic2.core.block.machine.gui.GuiExtractor;
import java.util.Map;

// Referenced classes of package ic2.neiIntegration.core:
//			MachineRecipeHandler

public class ExtractorRecipeHandler extends MachineRecipeHandler
{

	public ExtractorRecipeHandler()
	{
	}

	public Class getGuiClass()
	{
		return ic2/core/block/machine/gui/GuiExtractor;
	}

	public String getRecipeName()
	{
		return "Extractor";
	}

	public String getRecipeId()
	{
		return "ic2.extractor";
	}

	public String getGuiTexture()
	{
		return "/mods/ic2/textures/gui/GUIExtractor.png";
	}

	public String getOverlayIdentifier()
	{
		return "extractor";
	}

	public Map getRecipeList()
	{
		return Recipes.extractor.getRecipes();
	}
}
