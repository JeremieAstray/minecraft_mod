// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemArmorNanoSuit.java

package ic2.core.item.armor;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItemManager;
import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.init.InternalName;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.*;
import net.minecraftforge.event.EventBus;
import net.minecraftforge.event.entity.living.LivingFallEvent;

// Referenced classes of package ic2.core.item.armor:
//			ItemArmorElectric

public class ItemArmorNanoSuit extends ItemArmorElectric
{

	public ItemArmorNanoSuit(Configuration config, InternalName internalName, int armorType)
	{
		super(config, internalName, InternalName.nano, armorType, 0x186a0, 160, 2);
		if (armorType == 3)
			MinecraftForge.EVENT_BUS.register(this);
	}

	public net.minecraftforge.common.ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
	{
		if (source == DamageSource.fall && super.armorType == 3)
		{
			int energyPerDamage = getEnergyPerDamage();
			int damageLimit = energyPerDamage <= 0 ? 0 : (25 * ElectricItem.manager.getCharge(armor)) / energyPerDamage;
			return new net.minecraftforge.common.ISpecialArmor.ArmorProperties(10, damage >= 8D ? 0.875D : 1.0D, damageLimit);
		} else
		{
			return super.getProperties(player, armor, source, damage, slot);
		}
	}

	public void onEntityLivingFallEvent(LivingFallEvent event)
	{
		if (IC2.platform.isSimulating() && (event.entity instanceof EntityLivingBase))
		{
			EntityLivingBase entity = (EntityLivingBase)event.entity;
			ItemStack armor = entity.getCurrentItemOrArmor(1);
			if (armor != null && armor.getItem() == this)
			{
				int fallDamage = (int)event.distance - 3;
				if (fallDamage >= 8)
					return;
				int energyCost = getEnergyPerDamage() * fallDamage;
				if (energyCost <= ElectricItem.manager.getCharge(armor))
				{
					ElectricItem.manager.discharge(armor, energyCost, 0x7fffffff, true, false);
					event.setCanceled(true);
				}
			}
		}
	}

	public double getDamageAbsorptionRatio()
	{
		return 0.90000000000000002D;
	}

	public int getEnergyPerDamage()
	{
		return 800;
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.uncommon;
	}
}
