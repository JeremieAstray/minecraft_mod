// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IFuelValueProvider.java

package ic2.api.info;

import net.minecraft.item.ItemStack;

public interface IFuelValueProvider
{

	public abstract int getFuelValue(ItemStack itemstack, boolean flag);
}
