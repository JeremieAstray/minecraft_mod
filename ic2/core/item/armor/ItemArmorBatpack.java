// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemArmorBatpack.java

package ic2.core.item.armor;

import ic2.core.init.InternalName;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.armor:
//			ItemArmorElectric

public class ItemArmorBatpack extends ItemArmorElectric
{

	public ItemArmorBatpack(Configuration config, InternalName internalName)
	{
		super(config, internalName, InternalName.batpack, 1, 60000, 100, 1);
	}

	public boolean canProvideEnergy(ItemStack itemStack)
	{
		return true;
	}

	public double getDamageAbsorptionRatio()
	{
		return 0.0D;
	}

	public int getEnergyPerDamage()
	{
		return 0;
	}
}
