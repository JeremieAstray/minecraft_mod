// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemReactorHeatStorage.java

package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.init.InternalName;
import ic2.core.item.ItemGradual;
import ic2.core.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.Configuration;

public class ItemReactorHeatStorage extends ItemGradual
	implements IReactorComponent
{

	public int heatStorage;

	public ItemReactorHeatStorage(Configuration config, InternalName internalName, int heatStorage)
	{
		super(config, internalName);
		this.heatStorage = heatStorage;
	}

	public void processChamber(IReactor ireactor, ItemStack itemstack, int i, int j)
	{
	}

	public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int i, int j, int k, int l)
	{
		return false;
	}

	public boolean canStoreHeat(IReactor reactor, ItemStack yourStack, int x, int i)
	{
		return true;
	}

	public int getMaxHeat(IReactor reactor, ItemStack yourStack, int x, int y)
	{
		return heatStorage;
	}

	public int getCurrentHeat(IReactor reactor, ItemStack yourStack, int x, int y)
	{
		return getHeatOfStack(yourStack);
	}

	public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y, int heat)
	{
		int myHeat = getHeatOfStack(yourStack);
		myHeat += heat;
		if (myHeat > heatStorage)
		{
			reactor.setItemAt(x, y, null);
			heat = (heatStorage - myHeat) + 1;
		} else
		{
			if (myHeat < 0)
			{
				heat = myHeat;
				myHeat = 0;
			} else
			{
				heat = 0;
			}
			setHeatForStack(yourStack, myHeat);
		}
		return heat;
	}

	public float influenceExplosion(IReactor reactor, ItemStack yourStack)
	{
		return 0.0F;
	}

	private void setHeatForStack(ItemStack stack, int heat)
	{
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
		nbtData.setInteger("heat", heat);
		if (heatStorage > 0)
		{
			double p = (double)heat / (double)heatStorage;
			int newDmg = (int)((double)stack.getMaxDamage() * p);
			if (newDmg >= stack.getMaxDamage())
				newDmg = stack.getMaxDamage() - 1;
			stack.setItemDamage(newDmg);
		}
	}

	private int getHeatOfStack(ItemStack stack)
	{
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
		return nbtData.getInteger("heat");
	}
}
