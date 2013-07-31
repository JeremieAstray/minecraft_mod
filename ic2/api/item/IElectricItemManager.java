// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IElectricItemManager.java

package ic2.api.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IElectricItemManager
{

	public abstract int charge(ItemStack itemstack, int i, int j, boolean flag, boolean flag1);

	public abstract int discharge(ItemStack itemstack, int i, int j, boolean flag, boolean flag1);

	public abstract int getCharge(ItemStack itemstack);

	public abstract boolean canUse(ItemStack itemstack, int i);

	public abstract boolean use(ItemStack itemstack, int i, EntityLivingBase entitylivingbase);

	public abstract void chargeFromArmor(ItemStack itemstack, EntityLivingBase entitylivingbase);

	public abstract String getToolTip(ItemStack itemstack);
}
