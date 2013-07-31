// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemReactorPlating.java

package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.init.InternalName;
import ic2.core.item.ItemIC2;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

public class ItemReactorPlating extends ItemIC2
	implements IReactorComponent
{

	public int maxHeatAdd;
	public float effectModifier;

	public ItemReactorPlating(Configuration config, InternalName internalName, int maxheatadd, float effectmodifier)
	{
		super(config, internalName);
		maxHeatAdd = maxheatadd;
		effectModifier = effectmodifier;
	}

	public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y)
	{
		reactor.setMaxHeat(reactor.getMaxHeat() + maxHeatAdd);
		reactor.setHeatEffectModifier(reactor.getHeatEffectModifier() * effectModifier);
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
		if (effectModifier >= 1.0F)
			return 0.0F;
		else
			return effectModifier;
	}
}
