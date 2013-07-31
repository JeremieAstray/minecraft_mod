// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemArmorCFPack.java

package ic2.core.item.armor;

import ic2.core.init.InternalName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.armor:
//			ItemArmorUtility

public class ItemArmorCFPack extends ItemArmorUtility
{

	public ItemArmorCFPack(Configuration config, InternalName internalName)
	{
		super(config, internalName, InternalName.batpack, 1);
		setMaxDamage(260);
	}

	public boolean getCFPellet(EntityPlayer player, ItemStack pack)
	{
		if (pack.getItemDamage() < pack.getMaxDamage() - 1)
		{
			pack.setItemDamage(pack.getItemDamage() + 1);
			return true;
		} else
		{
			return false;
		}
	}

	public boolean isRepairable()
	{
		return true;
	}
}
