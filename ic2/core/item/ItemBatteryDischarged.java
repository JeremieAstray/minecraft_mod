// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemBatteryDischarged.java

package ic2.core.item;

import ic2.api.item.IBoxable;
import ic2.core.Ic2Items;
import ic2.core.init.InternalName;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item:
//			ItemBattery

public class ItemBatteryDischarged extends ItemBattery
	implements IBoxable
{

	public ItemBatteryDischarged(Configuration config, InternalName internalName, int maxCharge, int transferLimit, int tier)
	{
		super(config, internalName, maxCharge, transferLimit, tier);
		setMaxDamage(0);
		setMaxStackSize(16);
	}

	public String getTextureName(int index)
	{
		return null;
	}

	public Icon getIconFromDamage(int meta)
	{
		return Ic2Items.chargedReBattery.getItem().getIconFromDamage(Ic2Items.chargedReBattery.getItem().getMaxDamage());
	}

	public int getChargedItemId(ItemStack itemstack)
	{
		return Ic2Items.chargedReBattery.itemID;
	}

	public boolean canBeStoredInToolbox(ItemStack itemstack)
	{
		return true;
	}
}
