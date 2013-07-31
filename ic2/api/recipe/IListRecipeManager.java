// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IListRecipeManager.java

package ic2.api.recipe;

import java.util.List;
import net.minecraft.item.ItemStack;

public interface IListRecipeManager
	extends Iterable
{

	public abstract void add(ItemStack itemstack);

	public abstract boolean contains(ItemStack itemstack);

	public abstract List getStacks();
}
