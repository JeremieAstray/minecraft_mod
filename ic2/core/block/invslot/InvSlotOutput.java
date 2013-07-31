// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InvSlotOutput.java

package ic2.core.block.invslot;

import ic2.core.block.TileEntityInventory;
import ic2.core.util.StackUtil;
import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.core.block.invslot:
//			InvSlot

public class InvSlotOutput extends InvSlot
{

	public InvSlotOutput(TileEntityInventory base, String name, int oldStartIndex, int count)
	{
		super(base, name, oldStartIndex, InvSlot.Access.O, count, InvSlot.InvSide.BOTTOM);
	}

	public boolean accepts(ItemStack itemStack)
	{
		return false;
	}

	public int add(ItemStack itemStack)
	{
		return add(itemStack, false);
	}

	public boolean canAdd(ItemStack itemStack)
	{
		return add(itemStack, true) == 0;
	}

	private int add(ItemStack itemStack, boolean simulate)
	{
		if (itemStack == null)
			return 0;
		int amount = itemStack.stackSize;
		for (int i = 0; i < size(); i++)
		{
			ItemStack existingItemStack = get(i);
			if (existingItemStack == null)
			{
				if (!simulate)
					put(i, itemStack);
				return 0;
			}
			int space = existingItemStack.getMaxStackSize() - existingItemStack.stackSize;
			if (space <= 0 || !StackUtil.isStackEqual(itemStack, existingItemStack))
				continue;
			if (space >= amount)
			{
				if (!simulate)
					existingItemStack.stackSize += amount;
				return 0;
			}
			amount -= space;
			if (!simulate)
				existingItemStack.stackSize += space;
		}

		return amount;
	}
}
