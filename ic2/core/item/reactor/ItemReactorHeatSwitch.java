// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemReactorHeatSwitch.java

package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.init.InternalName;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.reactor:
//			ItemReactorHeatStorage

public class ItemReactorHeatSwitch extends ItemReactorHeatStorage
{
	private class ItemStackCoord
	{

		public ItemStack stack;
		public int x;
		public int y;
		final ItemReactorHeatSwitch this$0;

		public ItemStackCoord(ItemStack stack, int x, int y)
		{
			this$0 = ItemReactorHeatSwitch.this;
			super();
			this.stack = stack;
			this.x = x;
			this.y = y;
		}
	}


	public int switchSide;
	public int switchReactor;

	public ItemReactorHeatSwitch(Configuration config, InternalName internalName, int heatStorage, int switchside, int switchreactor)
	{
		super(config, internalName, heatStorage);
		switchSide = switchside;
		switchReactor = switchreactor;
	}

	public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y)
	{
		int myHeat = 0;
		ArrayList heatAcceptors = new ArrayList();
		double med = (double)getCurrentHeat(reactor, yourStack, x, y) / (double)getMaxHeat(reactor, yourStack, x, y);
		int c = 1;
		if (switchReactor > 0)
		{
			c++;
			med += (double)reactor.getHeat() / (double)reactor.getMaxHeat();
		}
		if (switchSide > 0)
		{
			med += checkHeatAcceptor(reactor, x - 1, y, heatAcceptors);
			med += checkHeatAcceptor(reactor, x + 1, y, heatAcceptors);
			med += checkHeatAcceptor(reactor, x, y - 1, heatAcceptors);
			med += checkHeatAcceptor(reactor, x, y + 1, heatAcceptors);
		}
		med /= c + heatAcceptors.size();
		if (switchSide > 0)
		{
			for (Iterator i$ = heatAcceptors.iterator(); i$.hasNext();)
			{
				ItemStackCoord stackcoord = (ItemStackCoord)i$.next();
				IReactorComponent heatable = (IReactorComponent)stackcoord.stack.getItem();
				int add = (int)(med * (double)heatable.getMaxHeat(reactor, stackcoord.stack, stackcoord.x, stackcoord.y)) - heatable.getCurrentHeat(reactor, stackcoord.stack, stackcoord.x, stackcoord.y);
				if (add > switchSide)
					add = switchSide;
				if (add < -switchSide)
					add = -switchSide;
				myHeat -= add;
				add = heatable.alterHeat(reactor, stackcoord.stack, stackcoord.x, stackcoord.y, add);
				myHeat += add;
			}

		}
		if (switchReactor > 0)
		{
			int add = (int)(med * (double)reactor.getMaxHeat()) - reactor.getHeat();
			if (add > switchReactor)
				add = switchReactor;
			if (add < -switchReactor)
				add = -switchReactor;
			myHeat -= add;
			reactor.setHeat(reactor.getHeat() + add);
		}
		alterHeat(reactor, yourStack, x, y, myHeat);
	}

	private double checkHeatAcceptor(IReactor reactor, int x, int y, ArrayList heatAcceptors)
	{
		ItemStack thing = reactor.getItemAt(x, y);
		if (thing != null && (thing.getItem() instanceof IReactorComponent))
		{
			IReactorComponent comp = (IReactorComponent)thing.getItem();
			if (comp.canStoreHeat(reactor, thing, x, y))
			{
				heatAcceptors.add(new ItemStackCoord(thing, x, y));
				double max = comp.getMaxHeat(reactor, thing, x, y);
				if (max <= 0.0D)
				{
					return 0.0D;
				} else
				{
					double cur = comp.getCurrentHeat(reactor, thing, x, y);
					return cur / max;
				}
			}
		}
		return 0.0D;
	}
}
