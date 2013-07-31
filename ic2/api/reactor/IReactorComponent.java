// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IReactorComponent.java

package ic2.api.reactor;

import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.api.reactor:
//			IReactor

public interface IReactorComponent
{

	public abstract void processChamber(IReactor ireactor, ItemStack itemstack, int i, int j);

	public abstract boolean acceptUraniumPulse(IReactor ireactor, ItemStack itemstack, ItemStack itemstack1, int i, int j, int k, int l);

	public abstract boolean canStoreHeat(IReactor ireactor, ItemStack itemstack, int i, int j);

	public abstract int getMaxHeat(IReactor ireactor, ItemStack itemstack, int i, int j);

	public abstract int getCurrentHeat(IReactor ireactor, ItemStack itemstack, int i, int j);

	public abstract int alterHeat(IReactor ireactor, ItemStack itemstack, int i, int j, int k);

	public abstract float influenceExplosion(IReactor ireactor, ItemStack itemstack);
}
