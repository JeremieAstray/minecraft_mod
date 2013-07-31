// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InfiniteElectricItemManager.java

package ic2.core.item;

import ic2.api.item.IElectricItemManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class InfiniteElectricItemManager
	implements IElectricItemManager
{

	public InfiniteElectricItemManager()
	{
	}

	public int charge(ItemStack itemStack, int amount, int tier, boolean flag, boolean flag1)
	{
		return amount;
	}

	public int discharge(ItemStack itemStack, int amount, int tier, boolean flag, boolean flag1)
	{
		return amount;
	}

	public int getCharge(ItemStack itemStack)
	{
		return 0x7fffffff;
	}

	public boolean canUse(ItemStack itemStack, int amount)
	{
		return true;
	}

	public boolean use(ItemStack itemStack, int amount, EntityLivingBase entity)
	{
		return true;
	}

	public void chargeFromArmor(ItemStack itemstack, EntityLivingBase entitylivingbase)
	{
	}

	public String getToolTip(ItemStack itemStack)
	{
		return "infinite EU";
	}
}
