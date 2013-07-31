// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemWrapper.java

package ic2.api.item;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.api.item:
//			IBoxable, IMetalArmor

public class ItemWrapper
{

	private static final Multimap boxableItems = ArrayListMultimap.create();
	private static final Multimap metalArmorItems = ArrayListMultimap.create();

	public ItemWrapper()
	{
	}

	public static void registerBoxable(Item item, IBoxable boxable)
	{
		boxableItems.put(item, boxable);
	}

	public static boolean canBeStoredInToolbox(ItemStack stack)
	{
		Item item = stack.getItem();
		for (Iterator i$ = boxableItems.get(item).iterator(); i$.hasNext();)
		{
			IBoxable boxable = (IBoxable)i$.next();
			if (boxable.canBeStoredInToolbox(stack))
				return true;
		}

		return (item instanceof IBoxable) && ((IBoxable)item).canBeStoredInToolbox(stack);
	}

	public static void registerMetalArmor(Item item, IMetalArmor armor)
	{
		metalArmorItems.put(item, armor);
	}

	public static boolean isMetalArmor(ItemStack stack, EntityPlayer player)
	{
		Item item = stack.getItem();
		for (Iterator i$ = metalArmorItems.get(item).iterator(); i$.hasNext();)
		{
			IMetalArmor metalArmor = (IMetalArmor)i$.next();
			if (metalArmor.isMetalArmor(stack, player))
				return true;
		}

		return (item instanceof IMetalArmor) && ((IMetalArmor)item).isMetalArmor(stack, player);
	}

}
