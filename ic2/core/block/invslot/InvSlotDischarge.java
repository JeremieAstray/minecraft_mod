// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InvSlotDischarge.java

package ic2.core.block.invslot;

import ic2.api.info.IEnergyValueProvider;
import ic2.api.info.Info;
import ic2.api.item.*;
import ic2.core.Ic2Items;
import ic2.core.block.TileEntityInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.core.block.invslot:
//			InvSlot

public class InvSlotDischarge extends InvSlot
{

	public int tier;

	public InvSlotDischarge(TileEntityInventory base, int oldStartIndex, int tier)
	{
		this(base, oldStartIndex, tier, InvSlot.InvSide.ANY);
	}

	public InvSlotDischarge(TileEntityInventory base, int oldStartIndex, int tier, InvSlot.InvSide preferredSide)
	{
		super(base, "discharge", oldStartIndex, InvSlot.Access.IO, 1, preferredSide);
		this.tier = tier;
	}

	public boolean accepts(ItemStack itemStack)
	{
		Item item = itemStack.getItem();
		if (item instanceof IElectricItem)
			return ((IElectricItem)item).canProvideEnergy(itemStack) && ((IElectricItem)item).getTier(itemStack) <= tier;
		if (item.itemID == Ic2Items.suBattery.itemID || item.itemID == Item.redstone.itemID)
			return true;
		else
			return Info.itemEnergy.getEnergyValue(itemStack) > 0 && (!(item instanceof IElectricItem) || ((IElectricItem)item).getTier(itemStack) <= tier);
	}

	public IElectricItem getItem()
	{
		ItemStack itemStack = get(0);
		if (itemStack == null)
			return null;
		else
			return (IElectricItem)itemStack.getItem();
	}

	public int discharge(int amount, boolean ignoreLimit)
	{
		ItemStack itemStack = get(0);
		if (itemStack == null)
			return 0;
		int energyValue = Info.itemEnergy.getEnergyValue(itemStack);
		if (energyValue == 0)
			return 0;
		Item item = itemStack.getItem();
		if (item instanceof IElectricItem)
		{
			IElectricItem elItem = (IElectricItem)item;
			if (!elItem.canProvideEnergy(itemStack) || elItem.getTier(itemStack) > tier)
				return 0;
			else
				return ElectricItem.manager.discharge(itemStack, amount, tier, ignoreLimit, false);
		}
		itemStack.stackSize--;
		if (itemStack.stackSize <= 0)
			put(0, null);
		return energyValue;
	}

	public void setTier(int tier)
	{
		this.tier = tier;
	}
}
