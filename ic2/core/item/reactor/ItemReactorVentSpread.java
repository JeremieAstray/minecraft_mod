// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemReactorVentSpread.java

package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.init.InternalName;
import ic2.core.item.ItemIC2;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

public class ItemReactorVentSpread extends ItemIC2
	implements IReactorComponent
{

	public int sideVent;

	public ItemReactorVentSpread(Configuration config, InternalName internalName, int sidevent)
	{
		super(config, internalName);
		setMaxStackSize(1);
		sideVent = sidevent;
	}

	public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y)
	{
		cool(reactor, x - 1, y);
		cool(reactor, x + 1, y);
		cool(reactor, x, y - 1);
		cool(reactor, x, y + 1);
	}

	private void cool(IReactor reactor, int x, int y)
	{
		ItemStack stack = reactor.getItemAt(x, y);
		if (stack != null && (stack.getItem() instanceof IReactorComponent))
		{
			IReactorComponent comp = (IReactorComponent)stack.getItem();
			if (comp.canStoreHeat(reactor, stack, x, y))
				comp.alterHeat(reactor, stack, x, y, -sideVent);
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

	public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y, int heat)
	{
		return heat;
	}

	public float influenceExplosion(IReactor reactor, ItemStack yourStack)
	{
		return 0.0F;
	}
}
