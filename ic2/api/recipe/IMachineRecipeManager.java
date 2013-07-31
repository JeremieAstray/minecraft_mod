// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IMachineRecipeManager.java

package ic2.api.recipe;

import java.util.Map;
import net.minecraft.item.ItemStack;

public interface IMachineRecipeManager
{

	public abstract void addRecipe(ItemStack itemstack, Object obj);

	public abstract Object getOutputFor(ItemStack itemstack, boolean flag);

	public abstract Map getRecipes();
}
