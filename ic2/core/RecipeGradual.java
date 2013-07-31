// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RecipeGradual.java

package ic2.core;

import ic2.core.item.ItemGradual;
import java.util.List;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeGradual
	implements IRecipe
{

	public ItemGradual item;
	public ItemStack chargeMaterial;
	public int amount;

	public RecipeGradual(ItemGradual item, ItemStack chargeMaterial, int amount)
	{
		this.item = item;
		this.chargeMaterial = chargeMaterial;
		this.amount = amount;
		CraftingManager.getInstance().getRecipeList().add(this);
	}

	public boolean matches(InventoryCrafting var1, World world)
	{
		return getCraftingResult(var1) != null;
	}

	public ItemStack getCraftingResult(InventoryCrafting var1)
	{
		ItemStack gridItem = null;
		int chargeMats = 0;
		for (int i = 0; i < var1.getSizeInventory(); i++)
		{
			ItemStack stack = var1.getStackInSlot(i);
			if (stack == null)
				continue;
			if (gridItem == null && stack.getItem() == item)
			{
				gridItem = stack;
				continue;
			}
			if (stack.itemID == chargeMaterial.itemID && (stack.getItemDamage() == chargeMaterial.getItemDamage() || chargeMaterial.getItemDamage() == -1))
				chargeMats++;
			else
				return null;
		}

		if (gridItem != null && chargeMats > 0)
		{
			ItemStack stack = gridItem.copy();
			int damage = item.getDamageOfStack(stack) - amount * chargeMats;
			if (damage > item.getMaxDamageEx())
				damage = item.getMaxDamageEx();
			else
			if (damage < 0)
				damage = 0;
			item.setDamageForStack(stack, damage);
			return stack;
		} else
		{
			return null;
		}
	}

	public int getRecipeSize()
	{
		return 2;
	}

	public ItemStack getRecipeOutput()
	{
		return new ItemStack(item);
	}
}
