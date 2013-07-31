// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdvShapelessRecipe.java

package ic2.core;

import ic2.api.item.*;
import ic2.core.util.Util;
import java.util.*;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

// Referenced classes of package ic2.core:
//			AdvRecipe

public class AdvShapelessRecipe
	implements IRecipe
{

	public ItemStack output;
	public Object input[];
	public boolean hidden;

	public static transient void addAndRegister(ItemStack result, Object args[])
	{
		CraftingManager.getInstance().getRecipeList().add(new AdvShapelessRecipe(result, args));
	}

	public transient AdvShapelessRecipe(ItemStack result, Object args[])
	{
		if (result == null)
			AdvRecipe.displayError("null result", null, null, true);
		input = new Object[args.length - Util.countInArray(args, java/lang/Boolean)];
		int inputIndex = 0;
		Object arr$[] = args;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Object o = arr$[i$];
			if (o instanceof String)
			{
				input[inputIndex++] = o;
				continue;
			}
			if ((o instanceof ItemStack) || (o instanceof Block) || (o instanceof Item))
			{
				if (o instanceof Block)
					o = new ItemStack((Block)o, 1, 32767);
				else
				if (o instanceof Item)
					o = new ItemStack((Item)o, 1, 32767);
				input[inputIndex++] = o;
				continue;
			}
			if (o instanceof Boolean)
				hidden = ((Boolean)o).booleanValue();
			else
				AdvRecipe.displayError("unknown type", (new StringBuilder()).append("O: ").append(o).append("\nT: ").append(o != null ? o.getClass().getName() : "null").toString(), result, true);
		}

		if (inputIndex != input.length)
			AdvRecipe.displayError("length calculation error", (new StringBuilder()).append("I: ").append(inputIndex).append("\nL: ").append(input.length).toString(), result, true);
		output = result;
	}

	public boolean matches(InventoryCrafting inventorycrafting, World world)
	{
		return getCraftingResult(inventorycrafting) != null;
	}

	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting)
	{
		int offerSize = inventorycrafting.getSizeInventory();
		if (offerSize < input.length)
			return null;
		List unmatched = new Vector();
		Object arr$[] = input;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Object o = arr$[i$];
			unmatched.add(o);
		}

		int outputCharge = 0;
label0:
		for (int i = 0; i < offerSize; i++)
		{
			ItemStack offer = inventorycrafting.getStackInSlot(i);
			if (offer == null)
				continue;
			int j = 0;
label1:
			do
			{
label2:
				{
					if (j >= unmatched.size())
						break label1;
					List requestedItemStacks = AdvRecipe.resolveOreDict(unmatched.get(j));
					Iterator i$ = requestedItemStacks.iterator();
					ItemStack requestedItemStack;
label3:
					do
					{
						do
						{
							if (!i$.hasNext())
								break label2;
							requestedItemStack = (ItemStack)i$.next();
							if (!(offer.getItem() instanceof IElectricItem))
								continue label3;
						} while (offer.itemID != requestedItemStack.itemID);
						outputCharge += ElectricItem.manager.getCharge(offer);
						unmatched.remove(j);
						continue label0;
					} while (!offer.isItemEqual(requestedItemStack) && (requestedItemStack.getItemDamage() != 32767 || offer.itemID != requestedItemStack.itemID));
					unmatched.remove(j);
					continue label0;
				}
				j++;
			} while (true);
			return null;
		}

		if (!unmatched.isEmpty())
			return null;
		ItemStack ret = output.copy();
		if (ret.getItem() instanceof IElectricItem)
			ElectricItem.manager.charge(ret, outputCharge, 0x7fffffff, true, false);
		return ret;
	}

	public int getRecipeSize()
	{
		return input.length;
	}

	public ItemStack getRecipeOutput()
	{
		return output;
	}
}
