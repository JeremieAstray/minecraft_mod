// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InvSlotCharge.java

package ic2.core.block.invslot;

import ic2.api.item.*;
import ic2.core.block.TileEntityInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.core.block.invslot:
//			InvSlot

public class InvSlotCharge extends InvSlot
{

	public int tier;

	public InvSlotCharge(TileEntityInventory base, int oldStartIndex, int tier)
	{
		super(base, "charge", oldStartIndex, InvSlot.Access.IO, 1, InvSlot.InvSide.TOP);
		this.tier = tier;
	}

	public boolean accepts(ItemStack itemStack)
	{
		Item item = itemStack.getItem();
		if (item instanceof IElectricItem)
			return ((IElectricItem)item).getTier(itemStack) <= tier;
		else
			return false;
	}

	public IElectricItem getItem()
	{
		ItemStack itemStack = get(0);
		if (itemStack == null)
			return null;
		else
			return (IElectricItem)itemStack.getItem();
	}

	public int charge(int amount)
	{
		ItemStack itemStack = get(0);
		if (itemStack == null)
			return 0;
		Item item = itemStack.getItem();
		if (item instanceof IElectricItem)
			return ElectricItem.manager.charge(itemStack, amount, tier, false, false);
		else
			return 0;
	}

	public void setTier(int tier)
	{
		this.tier = tier;
	}
}
