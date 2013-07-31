// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemReactorCondensator.java

package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.init.InternalName;
import ic2.core.item.ItemGradualInt;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

public class ItemReactorCondensator extends ItemGradualInt
	implements IReactorComponent
{

	public ItemReactorCondensator(Configuration config, InternalName internalName, int maxdmg)
	{
		super(config, internalName, maxdmg + 1);
	}

	public void processChamber(IReactor ireactor, ItemStack itemstack, int i, int j)
	{
	}

	public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int i, int j, int k, int l)
	{
		return false;
	}

	public boolean canStoreHeat(IReactor reactor, ItemStack yourStack, int x, int y)
	{
		return getDamageOfStack(yourStack) + 1 < maxDmg;
	}

	public int getMaxHeat(IReactor reactor, ItemStack yourStack, int x, int y)
	{
		return maxDmg;
	}

	public int getCurrentHeat(IReactor reactor, ItemStack yourStack, int x, int i)
	{
		return 0;
	}

	public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y, int heat)
	{
		int can = maxDmg - (getDamageOfStack(yourStack) + 1);
		if (can > heat)
			can = heat;
		heat -= can;
		setDamageForStack(yourStack, getDamageOfStack(yourStack) + can);
		return heat;
	}

	public float influenceExplosion(IReactor reactor, ItemStack yourStack)
	{
		return 0.0F;
	}
}
