// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemArmorJetpackElectric.java

package ic2.core.item.armor;

import ic2.api.item.*;
import ic2.core.init.InternalName;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.armor:
//			ItemArmorJetpack

public class ItemArmorJetpackElectric extends ItemArmorJetpack
	implements IElectricItem
{

	public ItemArmorJetpackElectric(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		setMaxDamage(27);
		setMaxStackSize(1);
	}

	public int getCharge(ItemStack itemStack)
	{
		return ElectricItem.manager.getCharge(itemStack);
	}

	public void use(ItemStack itemStack, int amount)
	{
		ElectricItem.manager.discharge(itemStack, amount, 0x7fffffff, true, false);
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
		return 30000;
	}

	public int getTier(ItemStack itemStack)
	{
		return 1;
	}

	public int getTransferLimit(ItemStack itemStack)
	{
		return 60;
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

	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return false;
	}
}
