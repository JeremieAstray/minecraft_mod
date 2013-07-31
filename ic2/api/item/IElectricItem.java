// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IElectricItem.java

package ic2.api.item;

import net.minecraft.item.ItemStack;

public interface IElectricItem
{

	public abstract boolean canProvideEnergy(ItemStack itemstack);

	public abstract int getChargedItemId(ItemStack itemstack);

	public abstract int getEmptyItemId(ItemStack itemstack);

	public abstract int getMaxCharge(ItemStack itemstack);

	public abstract int getTier(ItemStack itemstack);

	public abstract int getTransferLimit(ItemStack itemstack);
}
