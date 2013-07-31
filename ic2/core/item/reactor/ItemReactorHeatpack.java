// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemReactorHeatpack.java

package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.init.InternalName;
import ic2.core.item.ItemIC2;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

public class ItemReactorHeatpack extends ItemIC2
	implements IReactorComponent
{

	public int maxPer;
	public int heatPer;

	public ItemReactorHeatpack(Configuration config, InternalName internalName, int maxper, int heatper)
	{
		super(config, internalName);
		maxPer = maxper;
		heatPer = heatper;
	}

	public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y)
	{
		heat(reactor, yourStack.stackSize, x + 1, y);
		heat(reactor, yourStack.stackSize, x - 1, y);
		heat(reactor, yourStack.stackSize, x, y + 1);
		heat(reactor, yourStack.stackSize, x, y - 1);
	}

	private void heat(IReactor reactor, int stacksize, int x, int y)
	{
		int want = maxPer * stacksize;
		if (reactor.getHeat() >= want)
			return;
		ItemStack stack = reactor.getItemAt(x, y);
		if (stack != null && (stack.getItem() instanceof IReactorComponent))
		{
			IReactorComponent comp = (IReactorComponent)stack.getItem();
			if (comp.canStoreHeat(reactor, stack, x, y))
			{
				int add = heatPer * stacksize;
				int curr = comp.getCurrentHeat(reactor, stack, x, y);
				if (add > want - curr)
					add = want - curr;
				if (add > 0)
					comp.alterHeat(reactor, stack, x, y, add);
			}
		}
	}

	public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int i, int j, int k, int l)
	{
		return false;
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

	public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int i, int j)
	{
		return 0;
	}

	public float influenceExplosion(IReactor reactor, ItemStack yourStack)
	{
		return (float)(yourStack.stackSize / 10);
	}
}
