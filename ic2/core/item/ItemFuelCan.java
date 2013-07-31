// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemFuelCan.java

package ic2.core.item;

import ic2.api.item.IBoxable;
import ic2.core.init.InternalName;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item:
//			ItemIC2

public class ItemFuelCan extends ItemIC2
	implements IBoxable
{

	public ItemFuelCan(Configuration config, InternalName internalName)
	{
		super(config, internalName);
	}

	public boolean canBeStoredInToolbox(ItemStack itemstack)
	{
		return false;
	}
}
