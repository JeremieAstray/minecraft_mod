// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemGradualInt.java

package ic2.core.item;

import ic2.core.init.InternalName;
import ic2.core.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item:
//			ItemGradual

public class ItemGradualInt extends ItemGradual
{

	protected int maxDmg;

	public ItemGradualInt(Configuration config, InternalName internalName, int maxdmg)
	{
		super(config, internalName);
		maxDmg = maxdmg;
	}

	public void setDamageForStack(ItemStack stack, int advDmg)
	{
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
		nbtData.setInteger("advDmg", advDmg);
		if (maxDmg > 0)
		{
			double p = (double)advDmg / (double)maxDmg;
			int newDmg = (int)((double)stack.getMaxDamage() * p);
			if (newDmg >= stack.getMaxDamage())
				newDmg = stack.getMaxDamage() - 1;
			stack.setItemDamage(newDmg);
		}
	}

	public int getDamageOfStack(ItemStack stack)
	{
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
		return nbtData.getInteger("advDmg");
	}

	public int getMaxDamageEx()
	{
		return maxDmg;
	}
}
