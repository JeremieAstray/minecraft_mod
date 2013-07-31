// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemGradual.java

package ic2.core.item;

import ic2.api.item.IBoxable;
import ic2.core.Ic2Items;
import ic2.core.init.InternalName;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item:
//			ItemIC2

public class ItemGradual extends ItemIC2
	implements IBoxable
{

	public ItemGradual(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		setMaxStackSize(1);
		setMaxDamage(10000);
		setNoRepair();
	}

	public boolean canBeStoredInToolbox(ItemStack itemstack)
	{
		return itemstack.itemID == Ic2Items.hydratingCell.itemID;
	}

	public void setDamageForStack(ItemStack stack, int dmg)
	{
		stack.setItemDamage(dmg);
	}

	public int getDamageOfStack(ItemStack stack)
	{
		return stack.getItemDamage();
	}

	public int getMaxDamageEx()
	{
		return getMaxDamage();
	}
}