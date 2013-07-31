// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemReactorReflector.java

package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.init.InternalName;
import ic2.core.item.ItemGradualInt;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

public class ItemReactorReflector extends ItemGradualInt
	implements IReactorComponent
{

	public ItemReactorReflector(Configuration config, InternalName internalName, int maxDamage)
	{
		super(config, internalName, maxDamage);
	}

	public void processChamber(IReactor ireactor, ItemStack itemstack, int i, int j)
	{
	}

	public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY)
	{
		reactor.addOutput(1.0F);
		if (getDamageOfStack(yourStack) + 1 >= maxDmg)
			reactor.setItemAt(youX, youY, null);
		else
			setDamageForStack(yourStack, getDamageOfStack(yourStack) + 1);
		return true;
	}

	public boolean canStoreHeat(IReactor reactor, ItemStack yourStack, int x, int i)
	{
		return false;
	}

	public int getMaxHeat(IReactor reactor, ItemStack yourStack, int x, int i)
	{
		return 0;
	}

	public int getCurrentHeat(IReactor reactor, ItemStack yourStack, int x, int i)
	{
		return 0;
	}

	public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y, int heat)
	{
		return heat;
	}

	public float influenceExplosion(IReactor reactor, ItemStack yourStack)
	{
		return -1F;
	}
}
