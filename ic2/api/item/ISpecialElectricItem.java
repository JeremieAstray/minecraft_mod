// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ISpecialElectricItem.java

package ic2.api.item;

import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.api.item:
//			IElectricItem, IElectricItemManager

public interface ISpecialElectricItem
	extends IElectricItem
{

	public abstract IElectricItemManager getManager(ItemStack itemstack);
}
