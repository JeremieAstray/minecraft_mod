// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SlotInvSlot.java

package ic2.core.slot;

import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import java.util.Iterator;
import java.util.List;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotInvSlot extends Slot
{

	public final InvSlot invSlot;
	public final int index;

	public SlotInvSlot(InvSlot invSlot, int index, int xDisplayPosition, int yDisplayPosition)
	{
		super(invSlot.base, -1, xDisplayPosition, yDisplayPosition);
		this.invSlot = invSlot;
		this.index = index;
	}

	public boolean isItemValid(ItemStack itemStack)
	{
		return invSlot.accepts(itemStack);
	}

	public ItemStack getStack()
	{
		return invSlot.get(index);
	}

	public void putStack(ItemStack itemStack)
	{
		invSlot.put(index, itemStack);
		onSlotChanged();
	}

	public ItemStack decrStackSize(int amount)
	{
		ItemStack itemStack = invSlot.get(index);
		if (itemStack == null)
			return null;
		if (itemStack.stackSize <= amount)
		{
			invSlot.put(index, null);
			onSlotChanged();
			return itemStack;
		} else
		{
			ItemStack ret = itemStack.copy();
			ret.stackSize = amount;
			itemStack.stackSize -= amount;
			onSlotChanged();
			return ret;
		}
	}

	public boolean isSlotInInventory(IInventory inventory, int index)
	{
		if (inventory != this.invSlot.base)
			return false;
		for (Iterator i$ = this.invSlot.base.invSlots.iterator(); i$.hasNext();)
		{
			InvSlot invSlot = (InvSlot)i$.next();
			if (index < invSlot.size())
				return index == this.index;
			index -= invSlot.size();
		}

		return false;
	}
}
