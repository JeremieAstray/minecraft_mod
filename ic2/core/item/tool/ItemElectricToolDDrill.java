// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemElectricToolDDrill.java

package ic2.core.item.tool;

import ic2.core.init.InternalName;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.tool:
//			ItemElectricToolDrill

public class ItemElectricToolDDrill extends ItemElectricToolDrill
{

	public ItemElectricToolDDrill(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		super.toolMaterial = EnumToolMaterial.EMERALD;
		operationEnergyCost = 80;
		maxCharge = 10000;
		transferLimit = 100;
		tier = 1;
		super.efficiencyOnProperMaterial = 16F;
	}

	public void init()
	{
		super.init();
		mineableBlocks.add(Block.obsidian);
	}
}
