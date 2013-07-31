// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ICraftingRecipeManager.java

package ic2.api.recipe;

import net.minecraft.item.ItemStack;

public interface ICraftingRecipeManager
{

	public transient abstract void addRecipe(ItemStack itemstack, Object aobj[]);

	public transient abstract void addShapelessRecipe(ItemStack itemstack, Object aobj[]);
}
