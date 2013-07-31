// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BaseElectricItem.java

package ic2.core.item;

import ic2.api.item.*;
import ic2.core.init.InternalName;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item:
//			ItemIC2

public abstract class BaseElectricItem extends ItemIC2
	implements IElectricItem
{

	public int maxCharge;
	public int transferLimit;
	public int tier;

	public BaseElectricItem(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		transferLimit = 100;
		tier = 1;
		setMaxDamage(27);
		setMaxStackSize(1);
	}

	public boolean canProvideEnergy(ItemStack itemStack)
	{
		return false;
	}

	public int getChargedItemId(ItemStack itemStack)
	{
		return super.itemID;
	}

	public int getEmptyItemId(ItemStack itemStack)
	{
		return super.itemID;
	}

	public int getMaxCharge(ItemStack itemStack)
	{
		return maxCharge;
	}

	public int getTier(ItemStack itemStack)
	{
		return tier;
	}

	public int getTransferLimit(ItemStack itemStack)
	{
		return transferLimit;
	}

	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List itemList)
	{
		ItemStack itemStack = new ItemStack(this, 1);
		if (getChargedItemId(itemStack) == super.itemID)
		{
			ItemStack charged = new ItemStack(this, 1);
			ElectricItem.manager.charge(charged, 0x7fffffff, 0x7fffffff, true, false);
			itemList.add(charged);
		}
		if (getEmptyItemId(itemStack) == super.itemID)
			itemList.add(new ItemStack(this, 1, getMaxDamage()));
	}
}
