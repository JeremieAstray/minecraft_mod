// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GatewayElectricItemManager.java

package ic2.core.item;

import ic2.api.item.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GatewayElectricItemManager
	implements IElectricItemManager
{

	public GatewayElectricItemManager()
	{
	}

	public int charge(ItemStack itemStack, int amount, int tier, boolean ignoreTransferLimit, boolean simulate)
	{
		Item item = itemStack.getItem();
		if (!(item instanceof IElectricItem))
			return 0;
		if (item instanceof ICustomElectricItem)
			return ((ICustomElectricItem)item).charge(itemStack, amount, tier, ignoreTransferLimit, simulate);
		if (item instanceof ISpecialElectricItem)
			return ((ISpecialElectricItem)item).getManager(itemStack).charge(itemStack, amount, tier, ignoreTransferLimit, simulate);
		else
			return ElectricItem.rawManager.charge(itemStack, amount, tier, ignoreTransferLimit, simulate);
	}

	public int discharge(ItemStack itemStack, int amount, int tier, boolean ignoreTransferLimit, boolean simulate)
	{
		Item item = itemStack.getItem();
		if (!(item instanceof IElectricItem))
			return 0;
		if (item instanceof ICustomElectricItem)
			return ((ICustomElectricItem)item).discharge(itemStack, amount, tier, ignoreTransferLimit, simulate);
		if (item instanceof ISpecialElectricItem)
			return ((ISpecialElectricItem)item).getManager(itemStack).discharge(itemStack, amount, tier, ignoreTransferLimit, simulate);
		else
			return ElectricItem.rawManager.discharge(itemStack, amount, tier, ignoreTransferLimit, simulate);
	}

	public int getCharge(ItemStack itemStack)
	{
		Item item = itemStack.getItem();
		if (!(item instanceof IElectricItem))
			return 0;
		if (item instanceof ISpecialElectricItem)
			return ((ISpecialElectricItem)item).getManager(itemStack).getCharge(itemStack);
		else
			return ElectricItem.rawManager.getCharge(itemStack);
	}

	public boolean canUse(ItemStack itemStack, int amount)
	{
		Item item = itemStack.getItem();
		if (!(item instanceof IElectricItem))
			return false;
		if (item instanceof ICustomElectricItem)
			return ((ICustomElectricItem)item).canUse(itemStack, amount);
		if (item instanceof ISpecialElectricItem)
			return ((ISpecialElectricItem)item).getManager(itemStack).canUse(itemStack, amount);
		else
			return ElectricItem.rawManager.canUse(itemStack, amount);
	}

	public boolean use(ItemStack itemStack, int amount, EntityLivingBase entity)
	{
		Item item = itemStack.getItem();
		if (!(item instanceof IElectricItem))
			return false;
		if (item instanceof ISpecialElectricItem)
			return ((ISpecialElectricItem)item).getManager(itemStack).use(itemStack, amount, entity);
		else
			return ElectricItem.rawManager.use(itemStack, amount, entity);
	}

	public void chargeFromArmor(ItemStack itemStack, EntityLivingBase entity)
	{
		Item item = itemStack.getItem();
		if (entity == null || !(item instanceof IElectricItem))
			return;
		if (item instanceof ISpecialElectricItem)
			((ISpecialElectricItem)item).getManager(itemStack).chargeFromArmor(itemStack, entity);
		else
			ElectricItem.rawManager.chargeFromArmor(itemStack, entity);
	}

	public String getToolTip(ItemStack itemStack)
	{
		Item item = itemStack.getItem();
		if (!(item instanceof IElectricItem))
			return null;
		if (item instanceof ICustomElectricItem)
			if (((ICustomElectricItem)item).canShowChargeToolTip(itemStack))
				return ElectricItem.rawManager.getToolTip(itemStack);
			else
				return null;
		if (item instanceof ISpecialElectricItem)
			return ((ISpecialElectricItem)item).getManager(itemStack).getToolTip(itemStack);
		else
			return ElectricItem.rawManager.getToolTip(itemStack);
	}
}
