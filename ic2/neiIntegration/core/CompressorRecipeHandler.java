// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CompressorRecipeHandler.java

package ic2.neiIntegration.core;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.Recipes;
import ic2.core.block.machine.gui.GuiCompressor;
import java.util.Map;

// Referenced classes of package ic2.neiIntegration.core:
//			MachineRecipeHandler

public class CompressorRecipeHandler extends MachineRecipeHandler
{

	public CompressorRecipeHandler()
	{
	}

	public Class getGuiClass()
	{
		return ic2/core/block/machine/gui/GuiCompressor;
	}

	public String getRecipeName()
	{
		return "Compressor";
	}

	public String getRecipeId()
	{
		return "ic2.compressor";
	}

	public String getGuiTexture()
	{
		return "/mods/ic2/textures/gui/GUICompressor.png";
	}

	public String getOverlayIdentifier()
	{
		return "compressor";
	}

	public Map getRecipeList()
	{
		return Recipes.compressor.getRecipes();
	}
}
