// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SlotDischarge.java

package ic2.core.slot;

import ic2.api.item.IElectricItem;
import ic2.core.item.ItemBatterySU;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotDischarge extends Slot
{

	public int tier;

	public SlotDischarge(IInventory par1iInventory, int tier, int par2, int par3, int par4)
	{
		super(par1iInventory, par2, par3, par4);
		this.tier = 0x7fffffff;
		this.tier = tier;
	}

	public SlotDischarge(IInventory par1iInventory, int par2, int par3, int par4)
	{
		super(par1iInventory, par2, par3, par4);
		tier = 0x7fffffff;
	}

	public boolean isItemValid(ItemStack stack)
	{
		if (stack == null)
			return false;
		if (stack.itemID == Item.redstone.itemID || (stack.getItem() instanceof ItemBatterySU))
			return true;
		if (stack.getItem() instanceof IElectricItem)
		{
			IElectricItem iee = (IElectricItem)stack.getItem();
			if (iee.canProvideEnergy(stack) && iee.getTier(stack) <= tier)
				return true;
		}
		return false;
	}
}
