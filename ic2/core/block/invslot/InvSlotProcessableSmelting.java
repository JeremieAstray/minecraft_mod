// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InvSlotProcessableSmelting.java

package ic2.core.block.invslot;

import ic2.core.block.TileEntityInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

// Referenced classes of package ic2.core.block.invslot:
//			InvSlotProcessable

public class InvSlotProcessableSmelting extends InvSlotProcessable
{

	public InvSlotProcessableSmelting(TileEntityInventory base, String name, int oldStartIndex, int count)
	{
		super(base, name, oldStartIndex, count);
	}

	public boolean accepts(ItemStack itemStack)
	{
		return FurnaceRecipes.smelting().getSmeltingResult(itemStack) != null;
	}

	public ItemStack process(boolean simulate)
	{
		ItemStack input = consume(1, simulate, true);
		if (input == null)
			return null;
		else
			return FurnaceRecipes.smelting().getSmeltingResult(input).copy();
	}
}
