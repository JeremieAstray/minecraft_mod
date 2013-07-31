// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemArmorElectric.java

package ic2.core.item.armor;

import ic2.api.item.*;
import ic2.core.init.InternalName;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.ISpecialArmor;

// Referenced classes of package ic2.core.item.armor:
//			ItemArmorIC2

public abstract class ItemArmorElectric extends ItemArmorIC2
	implements ISpecialArmor, IElectricItem
{

	public int maxCharge;
	public int transferLimit;
	public int tier;

	public ItemArmorElectric(Configuration config, InternalName internalName, InternalName armorName, int armorType, int maxCharge, int transferLimit, int tier)
	{
		super(config, internalName, EnumArmorMaterial.DIAMOND, armorName, armorType, null);
		this.maxCharge = maxCharge;
		this.tier = tier;
		this.transferLimit = transferLimit;
		setMaxDamage(27);
		setMaxStackSize(1);
	}

	public int getItemEnchantability()
	{
		return 0;
	}

	public void getSubItems(int i, CreativeTabs tabs, List itemList)
	{
		ItemStack charged = new ItemStack(this, 1);
		ElectricItem.manager.charge(charged, 0x7fffffff, 0x7fffffff, true, false);
		itemList.add(charged);
		itemList.add(new ItemStack(this, 1, getMaxDamage()));
	}

	public net.minecraftforge.common.ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
	{
		if (source.isUnblockable())
		{
			return new net.minecraftforge.common.ISpecialArmor.ArmorProperties(0, 0.0D, 0);
		} else
		{
			double absorptionRatio = getBaseAbsorptionRatio() * getDamageAbsorptionRatio();
			int energyPerDamage = getEnergyPerDamage();
			int damageLimit = energyPerDamage <= 0 ? 0 : (25 * ElectricItem.manager.getCharge(armor)) / energyPerDamage;
			return new net.minecraftforge.common.ISpecialArmor.ArmorProperties(0, absorptionRatio, damageLimit);
		}
	}

	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
	{
		if (ElectricItem.manager.getCharge(armor) >= getEnergyPerDamage())
			return (int)Math.round(20D * getBaseAbsorptionRatio() * getDamageAbsorptionRatio());
		else
			return 0;
	}

	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
	{
		ElectricItem.manager.discharge(stack, damage * getEnergyPerDamage(), 0x7fffffff, true, false);
	}

	public boolean canProvideEnergy(ItemStack itemStack)
	{
		return false;
	}

	public int getChargedItemId(ItemStack itemStack)
	{
		return super.itemID;
	}

	public int getEmptyItemId(ItemStack itemStack)
	{
		return super.itemID;
	}

	public int getMaxCharge(ItemStack itemStack)
	{
		return maxCharge;
	}

	public int getTier(ItemStack itemStack)
	{
		return tier;
	}

	public int getTransferLimit(ItemStack itemStack)
	{
		return transferLimit;
	}

	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return false;
	}

	public abstract double getDamageAbsorptionRatio();

	public abstract int getEnergyPerDamage();

	private double getBaseAbsorptionRatio()
	{
		switch (super.armorType)
		{
		case 0: // '\0'
			return 0.14999999999999999D;

		case 1: // '\001'
			return 0.40000000000000002D;

		case 2: // '\002'
			return 0.29999999999999999D;

		case 3: // '\003'
			return 0.14999999999999999D;
		}
		return 0.0D;
	}
}
