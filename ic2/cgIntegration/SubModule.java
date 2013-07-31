// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SubModule.java

package ic2.cgIntegration;

import ic2.cgIntegration.core.AdvRecipeGenerator;
import ic2.cgIntegration.core.MachineGenerator;
import ic2.core.Ic2Items;
import ic2.core.block.machine.tileentity.*;
import net.minecraft.item.ItemStack;

public class SubModule
{

	public SubModule()
	{
	}

	public static boolean init()
	{
		try
		{
			new AdvRecipeGenerator();
			new MachineGenerator(TileEntityMacerator.recipes, 1, 61, 82, 61, Ic2Items.macerator.copy());
			new MachineGenerator(TileEntityCompressor.recipes, 1, 1, 82, 1, Ic2Items.compressor.copy());
			new MachineGenerator(TileEntityExtractor.recipes, 1, 121, 82, 121, Ic2Items.extractor.copy());
		}
		catch (Throwable e)
		{
			return false;
		}
		return true;
	}
}
