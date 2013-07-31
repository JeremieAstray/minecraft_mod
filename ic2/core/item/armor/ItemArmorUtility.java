// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemArmorUtility.java

package ic2.core.item.armor;

import ic2.core.init.InternalName;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.ISpecialArmor;

// Referenced classes of package ic2.core.item.armor:
//			ItemArmorIC2

public class ItemArmorUtility extends ItemArmorIC2
	implements ISpecialArmor
{

	public ItemArmorUtility(Configuration config, InternalName internalName, InternalName armorName, int type)
	{
		super(config, internalName, EnumArmorMaterial.DIAMOND, armorName, type, null);
	}

	public int getItemEnchantability()
	{
		return 0;
	}

	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return false;
	}

	public net.minecraftforge.common.ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
	{
		return new net.minecraftforge.common.ISpecialArmor.ArmorProperties(0, 0.0D, 0);
	}

	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
	{
		return 0;
	}

	public void damageArmor(EntityLivingBase entitylivingbase, ItemStack itemstack, DamageSource damagesource, int i, int j)
	{
	}
}
