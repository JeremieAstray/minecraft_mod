// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdvRecipe.java

package ic2.core;

import ic2.api.item.*;
import java.util.*;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import net.minecraftforge.oredict.OreDictionary;

// Referenced classes of package ic2.core:
//			IC2, Ic2Items, AdvShapelessRecipe, Platform

public class AdvRecipe
	implements IRecipe
{

	public ItemStack output;
	public Object input[];
	public int inputWidth;
	public boolean hidden;

	public static transient void addAndRegister(ItemStack result, Object args[])
	{
		CraftingManager.getInstance().getRecipeList().add(new AdvRecipe(result, args));
	}

	public transient AdvRecipe(ItemStack result, Object args[])
	{
		if (result == null)
			displayError("null result", null, null, false);
		Map charMapping = new HashMap();
		List inputArrangement = new Vector();
		Character lastChar = null;
		Object arr$[] = args;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Object o = arr$[i$];
			if (o instanceof String)
			{
				if (lastChar == null)
				{
					if (!charMapping.isEmpty())
						displayError("oredict name without preceding char", (new StringBuilder()).append("N: ").append(o).toString(), result, false);
					inputArrangement.add((String)o);
				} else
				{
					charMapping.put(lastChar, o);
					lastChar = null;
				}
				continue;
			}
			if (o instanceof Character)
			{
				if (lastChar != null)
					displayError("two consecutive char definitions", (new StringBuilder()).append("O: ").append(o).append("\nC: ").append(lastChar).toString(), result, false);
				lastChar = (Character)o;
				continue;
			}
			if ((o instanceof ItemStack) || (o instanceof Block) || (o instanceof Item))
			{
				if (lastChar == null)
					displayError("item without preceding char", (new StringBuilder()).append("O: ").append(o).append("\nT: ").append(o != null ? o.getClass().getName() : "null").append("\nC: ").append(lastChar).toString(), result, false);
				if (o instanceof Block)
					o = new ItemStack((Block)o, 1, 32767);
				else
				if (o instanceof Item)
					o = new ItemStack((Item)o, 1, 32767);
				charMapping.put(lastChar, o);
				lastChar = null;
				continue;
			}
			if (o instanceof Boolean)
				hidden = ((Boolean)o).booleanValue();
			else
				displayError("unknown type", (new StringBuilder()).append("O: ").append(o).append("\nT: ").append(o != null ? o.getClass().getName() : "null").toString(), result, false);
		}

		if (lastChar != null)
			displayError("one or more unused mapping chars", (new StringBuilder()).append("L: ").append(lastChar).toString(), result, false);
		if (inputArrangement.size() == 0 || inputArrangement.size() > 3)
			displayError("none or too many crafting rows", (new StringBuilder()).append("S: ").append(inputArrangement.size()).toString(), result, false);
		if (charMapping.size() == 0)
			displayError("no mapping chars", null, result, false);
		inputWidth = ((String)inputArrangement.get(0)).length();
		input = new Object[inputWidth * inputArrangement.size()];
		int inputIndex = 0;
		for (Iterator i$ = inputArrangement.iterator(); i$.hasNext();)
		{
			String str = (String)i$.next();
			if (str.length() != inputWidth)
				displayError("no fixed width", (new StringBuilder()).append("W: ").append(inputWidth).append("\nL: ").append(str.length()).toString(), result, false);
			int i = 0;
			while (i < str.length()) 
			{
				char c = str.charAt(i);
				if (c == ' ')
				{
					input[inputIndex++] = null;
				} else
				{
					if (!charMapping.containsKey(Character.valueOf(c)))
						displayError("missing char mapping", (new StringBuilder()).append("C: ").append(c).toString(), result, false);
					input[inputIndex++] = charMapping.get(Character.valueOf(c));
				}
				i++;
			}
		}

		output = result;
	}

	public boolean matches(InventoryCrafting inventorycrafting, World world)
	{
		return getCraftingResult(inventorycrafting) != null;
	}

	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting)
	{
		int inputHeight = input.length / inputWidth;
		int offerSize = inventorycrafting.getSizeInventory() != 9 ? 2 : 3;
		if (offerSize < inputWidth || offerSize < inputHeight)
			return null;
label0:
		for (int xOffset = 0; xOffset <= offerSize - inputWidth; xOffset++)
		{
			int yOffset = 0;
			do
			{
label1:
				{
					if (yOffset > offerSize - inputHeight)
						continue label0;
					int outputCharge = 0;
					for (int x = 0; x < inputWidth; x++)
					{
						for (int y = 0; y < inputHeight; y++)
						{
							ItemStack offer = inventorycrafting.getStackInRowAndColumn(x + xOffset, y + yOffset);
							Object request = input[x + y * inputWidth];
							if (offer == null && request != null)
								break label1;
							if (offer == null)
								continue;
							if (request == null)
								return null;
							List requestedItemStacks = resolveOreDict(request);
							boolean found = false;
							Iterator i$ = requestedItemStacks.iterator();
							do
							{
								if (!i$.hasNext())
									break;
								ItemStack requestedItemStack = (ItemStack)i$.next();
								if (offer.getItem() instanceof IElectricItem)
								{
									if (offer.itemID != requestedItemStack.itemID)
										continue;
									outputCharge += ElectricItem.manager.getCharge(offer);
									found = true;
									break;
								}
								if (!offer.isItemEqual(requestedItemStack) && (requestedItemStack.getItemDamage() != 32767 || offer.itemID != requestedItemStack.itemID))
									continue;
								found = true;
								break;
							} while (true);
							if (!found)
								return null;
						}

					}

					for (int x = 0; x < xOffset; x++)
					{
						for (int y = 0; y < offerSize; y++)
							if (inventorycrafting.getStackInRowAndColumn(x, y) != null)
								return null;

					}

					for (int y = 0; y < yOffset; y++)
					{
						for (int x = 0; x < offerSize; x++)
							if (inventorycrafting.getStackInRowAndColumn(x, y) != null)
								return null;

					}

					for (int x = xOffset + inputWidth; x < offerSize; x++)
					{
						for (int y = 0; y < offerSize; y++)
							if (inventorycrafting.getStackInRowAndColumn(x, y) != null)
								return null;

					}

					for (int y = yOffset + inputHeight; y < offerSize; y++)
					{
						for (int x = 0; x < offerSize; x++)
							if (inventorycrafting.getStackInRowAndColumn(x, y) != null)
								return null;

					}

					ItemStack ret = output.copy();
					if (ret.getItem() instanceof IElectricItem)
						ElectricItem.manager.charge(ret, outputCharge, 0x7fffffff, true, false);
					return ret;
				}
				yOffset++;
			} while (true);
		}

		return null;
	}

	public int getRecipeSize()
	{
		return input.length;
	}

	public ItemStack getRecipeOutput()
	{
		return output;
	}

	public static boolean recipeContains(Object inputs[], ItemStack item)
	{
		Object arr$[] = inputs;
		int len$ = arr$.length;
label0:
		for (int i$ = 0; i$ < len$; i$++)
		{
			Object input = arr$[i$];
			if (input == null)
				continue;
			List realInputs = resolveOreDict(input);
			Iterator i$ = realInputs.iterator();
			ItemStack realInput;
			do
			{
				if (!i$.hasNext())
					continue label0;
				realInput = (ItemStack)i$.next();
			} while (!item.isItemEqual(realInput));
			return true;
		}

		return false;
	}

	public static boolean canShow(Object input[], ItemStack output, boolean hidden)
	{
		return (!IC2.enableSecretRecipeHiding || !hidden) && !recipeContains(input, Ic2Items.reBattery) && (!recipeContains(input, Ic2Items.industrialDiamond) || output.itemID != Item.diamond.itemID);
	}

	public static boolean canShow(AdvRecipe recipe)
	{
		return canShow(recipe.input, recipe.output, recipe.hidden);
	}

	public static boolean canShow(AdvShapelessRecipe recipe)
	{
		return canShow(recipe.input, recipe.output, recipe.hidden);
	}

	public static List resolveOreDict(Object o)
	{
		List ret;
		if (o instanceof String)
		{
			String s = (String)o;
			if (s.startsWith("liquid$"))
			{
				String name = s.substring(7);
				ret = new Vector();
				net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData arr$[] = FluidContainerRegistry.getRegisteredFluidContainerData();
				int len$ = arr$.length;
				for (int i$ = 0; i$ < len$; i$++)
				{
					net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData data = arr$[i$];
					if (data.fluid.getFluid().getName().equals(name))
						ret.add(data.filledContainer);
				}

			} else
			{
				ret = OreDictionary.getOres((String)o);
			}
		} else
		if (o instanceof ItemStack)
		{
			ret = new Vector(1);
			ret.add((ItemStack)o);
		} else
		{
			displayError("unknown type", (new StringBuilder()).append("O: ").append(o).append("\nT: ").append(o != null ? o.getClass().getName() : "null").toString(), null, false);
			return null;
		}
		return ret;
	}

	public static void displayError(String cause, String tech, ItemStack result, boolean shapeless)
	{
		IC2.platform.displayError((new StringBuilder()).append("An invalid crafting recipe was attempted to be added. This could\nhappen due to a bug in IndustrialCraft 2 or an addon.\n\n(Technical information: Adv").append(shapeless ? "Shapeless" : "").append("Recipe, ").append(cause).append(")\n").append(result == null ? "" : (new StringBuilder()).append("R: ").append(result).append("\n").toString()).append(tech == null ? "" : tech).toString());
	}
}
