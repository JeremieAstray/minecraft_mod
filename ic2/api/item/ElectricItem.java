// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ElectricItem.java

package ic2.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.api.item:
//			IElectricItemManager

public final class ElectricItem
{

	public static IElectricItemManager manager;
	public static IElectricItemManager rawManager;

	public ElectricItem()
	{
	}

	/**
	 * @deprecated Method charge is deprecated
	 */

	public static int charge(ItemStack itemStack, int amount, int tier, boolean ignoreTransferLimit, boolean simulate)
	{
		return manager.charge(itemStack, amount, tier, ignoreTransferLimit, simulate);
	}

	/**
	 * @deprecated Method discharge is deprecated
	 */

	public static int discharge(ItemStack itemStack, int amount, int tier, boolean ignoreTransferLimit, boolean simulate)
	{
		return manager.discharge(itemStack, amount, tier, ignoreTransferLimit, simulate);
	}

	/**
	 * @deprecated Method canUse is deprecated
	 */

	public static boolean canUse(ItemStack itemStack, int amount)
	{
		return manager.canUse(itemStack, amount);
	}

	/**
	 * @deprecated Method use is deprecated
	 */

	public static boolean use(ItemStack itemStack, int amount, EntityPlayer player)
	{
		return manager.use(itemStack, amount, player);
	}

	/**
	 * @deprecated Method chargeFromArmor is deprecated
	 */

	public static void chargeFromArmor(ItemStack itemStack, EntityPlayer player)
	{
		manager.chargeFromArmor(itemStack, player);
	}
}
