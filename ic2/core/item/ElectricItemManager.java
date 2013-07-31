// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ElectricItemManager.java

package ic2.core.item;

import ic2.api.item.*;
import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.util.StackUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ElectricItemManager
	implements IElectricItemManager
{

	public ElectricItemManager()
	{
	}

	public int charge(ItemStack itemStack, int amount, int tier, boolean ignoreTransferLimit, boolean simulate)
	{
		IElectricItem item = (IElectricItem)itemStack.getItem();
		if (amount < 0 || itemStack.stackSize > 1 || item.getTier(itemStack) > tier)
			return 0;
		if (amount > item.getTransferLimit(itemStack) && !ignoreTransferLimit)
			amount = item.getTransferLimit(itemStack);
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
		int charge = nbtData.getInteger("charge");
		if (amount > item.getMaxCharge(itemStack) - charge)
			amount = item.getMaxCharge(itemStack) - charge;
		charge += amount;
		if (!simulate)
		{
			nbtData.setInteger("charge", charge);
			itemStack.itemID = charge <= 0 ? item.getEmptyItemId(itemStack) : item.getChargedItemId(itemStack);
			if (itemStack.getItem() instanceof IElectricItem)
			{
				item = (IElectricItem)itemStack.getItem();
				if (itemStack.getMaxDamage() > 2)
					itemStack.setItemDamage(1 + ((item.getMaxCharge(itemStack) - charge) * (itemStack.getMaxDamage() - 2)) / item.getMaxCharge(itemStack));
				else
					itemStack.setItemDamage(0);
			} else
			{
				itemStack.setItemDamage(0);
			}
		}
		return amount;
	}

	public int discharge(ItemStack itemStack, int amount, int tier, boolean ignoreTransferLimit, boolean simulate)
	{
		IElectricItem item = (IElectricItem)itemStack.getItem();
		if (amount < 0 || itemStack.stackSize > 1 || item.getTier(itemStack) > tier)
			return 0;
		if (amount > item.getTransferLimit(itemStack) && !ignoreTransferLimit)
			amount = item.getTransferLimit(itemStack);
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
		int charge = nbtData.getInteger("charge");
		if (amount > charge)
			amount = charge;
		if (!simulate)
		{
			charge -= amount;
			nbtData.setInteger("charge", charge);
			itemStack.itemID = charge <= 0 ? item.getEmptyItemId(itemStack) : item.getChargedItemId(itemStack);
			if (itemStack.getItem() instanceof IElectricItem)
			{
				item = (IElectricItem)itemStack.getItem();
				if (itemStack.getMaxDamage() > 2)
					itemStack.setItemDamage(1 + ((item.getMaxCharge(itemStack) - charge) * (itemStack.getMaxDamage() - 2)) / item.getMaxCharge(itemStack));
				else
					itemStack.setItemDamage(0);
			} else
			{
				itemStack.setItemDamage(0);
			}
		}
		return amount;
	}

	public int getCharge(ItemStack itemStack)
	{
		return ElectricItem.manager.discharge(itemStack, 0x7fffffff, 0x7fffffff, true, true);
	}

	public boolean canUse(ItemStack itemStack, int amount)
	{
		return ElectricItem.manager.getCharge(itemStack) >= amount;
	}

	public boolean use(ItemStack itemStack, int amount, EntityLivingBase entity)
	{
		if (!IC2.platform.isSimulating())
			return false;
		ElectricItem.manager.chargeFromArmor(itemStack, entity);
		int transfer = ElectricItem.manager.discharge(itemStack, amount, 0x7fffffff, true, true);
		if (transfer == amount)
		{
			ElectricItem.manager.discharge(itemStack, amount, 0x7fffffff, true, false);
			ElectricItem.manager.chargeFromArmor(itemStack, entity);
			return true;
		} else
		{
			return false;
		}
	}

	public void chargeFromArmor(ItemStack itemStack, EntityLivingBase entity)
	{
		if (!IC2.platform.isSimulating())
			return;
		boolean inventoryChanged = false;
		for (int i = 0; i < 4; i++)
		{
			ItemStack armorItemStack = entity.getCurrentItemOrArmor(i + 1);
			if (armorItemStack == null || !(armorItemStack.getItem() instanceof IElectricItem))
				continue;
			IElectricItem armorItem = (IElectricItem)armorItemStack.getItem();
			if (!armorItem.canProvideEnergy(armorItemStack) || armorItem.getTier(armorItemStack) < ((IElectricItem)itemStack.getItem()).getTier(itemStack))
				continue;
			int transfer = ElectricItem.manager.charge(itemStack, 0x7fffffff, 0x7fffffff, true, true);
			transfer = ElectricItem.manager.discharge(armorItemStack, transfer, 0x7fffffff, true, false);
			if (transfer > 0)
			{
				ElectricItem.manager.charge(itemStack, transfer, 0x7fffffff, true, false);
				inventoryChanged = true;
			}
		}

		if (inventoryChanged && (entity instanceof EntityPlayer))
			((EntityPlayer)entity).openContainer.detectAndSendChanges();
	}

	public String getToolTip(ItemStack itemStack)
	{
		if (!(itemStack.getItem() instanceof IElectricItem))
		{
			return null;
		} else
		{
			IElectricItem item = (IElectricItem)itemStack.getItem();
			return (new StringBuilder()).append(ElectricItem.manager.getCharge(itemStack)).append("/").append(item.getMaxCharge(itemStack)).append(" EU").toString();
		}
	}
}
