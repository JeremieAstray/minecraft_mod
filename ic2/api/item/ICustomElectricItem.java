// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ICustomElectricItem.java

package ic2.api.item;

import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.api.item:
//			IElectricItem

/**
 * @deprecated Interface ICustomElectricItem is deprecated
 */

public interface ICustomElectricItem
	extends IElectricItem
{

	public abstract int charge(ItemStack itemstack, int i, int j, boolean flag, boolean flag1);

	public abstract int discharge(ItemStack itemstack, int i, int j, boolean flag, boolean flag1);

	public abstract boolean canUse(ItemStack itemstack, int i);

	public abstract boolean canShowChargeToolTip(ItemStack itemstack);
}
