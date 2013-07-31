// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemReactorVent.java

package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.core.init.InternalName;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.reactor:
//			ItemReactorHeatStorage

public class ItemReactorVent extends ItemReactorHeatStorage
{

	public int selfVent;
	public int reactorVent;

	public ItemReactorVent(Configuration config, InternalName internalName, int heatStorage, int selfvent, int reactorvent)
	{
		super(config, internalName, heatStorage);
		selfVent = selfvent;
		reactorVent = reactorvent;
	}

	public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y)
	{
		if (reactorVent > 0)
		{
			int rheat = reactor.getHeat();
			int reactorDrain = rheat;
			if (reactorDrain > reactorVent)
				reactorDrain = reactorVent;
			rheat -= reactorDrain;
			if ((reactorDrain = alterHeat(reactor, yourStack, x, y, reactorDrain)) > 0)
				return;
			reactor.setHeat(rheat);
		}
		alterHeat(reactor, yourStack, x, y, -selfVent);
	}
}
