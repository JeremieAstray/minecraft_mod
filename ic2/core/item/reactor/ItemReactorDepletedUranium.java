// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemReactorDepletedUranium.java

package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.Ic2Items;
import ic2.core.init.InternalName;
import ic2.core.item.ItemGradual;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

public class ItemReactorDepletedUranium extends ItemGradual
	implements IReactorComponent
{

	public ItemReactorDepletedUranium(Configuration config, InternalName internalName)
	{
		super(config, internalName);
	}

	public void processChamber(IReactor ireactor, ItemStack itemstack, int i, int j)
	{
	}

	public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY)
	{
		int myLevel = yourStack.getItemDamage() - 1 - reactor.getHeat() / 3000;
		if (myLevel <= 0)
			reactor.setItemAt(youX, youY, new ItemStack(Ic2Items.reEnrichedUraniumCell.getItem()));
		else
			yourStack.setItemDamage(myLevel);
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
		return 0.0F;
	}
}
