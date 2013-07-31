// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemReactorUranium.java

package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import ic2.core.init.InternalName;
import ic2.core.item.ItemGradual;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

public class ItemReactorUranium extends ItemGradual
	implements IReactorComponent
{
	private class ItemStackCoord
	{

		public ItemStack stack;
		public int x;
		public int y;
		final ItemReactorUranium this$0;

		public ItemStackCoord(ItemStack stack, int x, int y)
		{
			this$0 = ItemReactorUranium.this;
			super();
			this.stack = stack;
			this.x = x;
			this.y = y;
		}
	}


	public int numberOfCells;

	public ItemReactorUranium(Configuration config, InternalName internalName, int cells)
	{
		super(config, internalName);
		numberOfCells = cells;
	}

	public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y)
	{
		if (!reactor.produceEnergy())
			return;
		for (int iteration = 0; iteration < numberOfCells; iteration++)
		{
			int pulses = 1 + numberOfCells / 2;
			for (int i = 0; i < pulses; i++)
				acceptUraniumPulse(reactor, yourStack, yourStack, x, y, x, y);

			pulses += checkPulseable(reactor, x - 1, y, yourStack, x, y) + checkPulseable(reactor, x + 1, y, yourStack, x, y) + checkPulseable(reactor, x, y - 1, yourStack, x, y) + checkPulseable(reactor, x, y + 1, yourStack, x, y);
			int heat = sumUp(pulses) * 4;
			ArrayList heatAcceptors = new ArrayList();
			checkHeatAcceptor(reactor, x - 1, y, heatAcceptors);
			checkHeatAcceptor(reactor, x + 1, y, heatAcceptors);
			checkHeatAcceptor(reactor, x, y - 1, heatAcceptors);
			checkHeatAcceptor(reactor, x, y + 1, heatAcceptors);
			for (; heatAcceptors.size() > 0 && heat > 0; heatAcceptors.remove(0))
			{
				int dheat = heat / heatAcceptors.size();
				heat -= dheat;
				dheat = ((IReactorComponent)((ItemStackCoord)heatAcceptors.get(0)).stack.getItem()).alterHeat(reactor, ((ItemStackCoord)heatAcceptors.get(0)).stack, ((ItemStackCoord)heatAcceptors.get(0)).x, ((ItemStackCoord)heatAcceptors.get(0)).y, dheat);
				heat += dheat;
			}

			if (heat > 0)
				reactor.addHeat(heat);
		}

		if (yourStack.getItemDamage() >= getMaxDamage() - 1)
		{
			if (IC2.random.nextInt(3) == 0)
				reactor.setItemAt(x, y, new ItemStack(Ic2Items.nearDepletedUraniumCell.getItem(), numberOfCells));
			else
				reactor.setItemAt(x, y, null);
		} else
		{
			StackUtil.damageItemStack(yourStack, 1);
		}
	}

	private int checkPulseable(IReactor reactor, int x, int y, ItemStack me, int mex, int mey)
	{
		ItemStack other = reactor.getItemAt(x, y);
		return other == null || !(other.getItem() instanceof IReactorComponent) || !((IReactorComponent)other.getItem()).acceptUraniumPulse(reactor, other, me, x, y, mex, mey) ? 0 : 1;
	}

	private int sumUp(int x)
	{
		int sum = 0;
		for (int i = 1; i <= x; i++)
			sum += i;

		return sum;
	}

	private void checkHeatAcceptor(IReactor reactor, int x, int y, ArrayList heatAcceptors)
	{
		ItemStack thing = reactor.getItemAt(x, y);
		if (thing != null && (thing.getItem() instanceof IReactorComponent) && ((IReactorComponent)thing.getItem()).canStoreHeat(reactor, thing, x, y))
			heatAcceptors.add(new ItemStackCoord(thing, x, y));
	}

	public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY)
	{
		reactor.addOutput(1.0F);
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
		return (float)(2 * numberOfCells);
	}
}
