// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemArmorHazmat.java

package ic2.core.item.armor;

import ic2.core.*;
import ic2.core.init.InternalName;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.*;
import net.minecraftforge.event.EventBus;
import net.minecraftforge.event.entity.living.LivingFallEvent;

// Referenced classes of package ic2.core.item.armor:
//			ItemArmorUtility

public class ItemArmorHazmat extends ItemArmorUtility
{

	public ItemArmorHazmat(Configuration config, InternalName internalName, int type)
	{
		super(config, internalName, InternalName.hazmat, type);
		setMaxDamage(64);
		if (super.armorType == 3)
			MinecraftForge.EVENT_BUS.register(this);
	}

	public net.minecraftforge.common.ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
	{
		if (super.armorType == 0 && hazmatAbsorbs(source) && hasCompleteHazmat(player))
		{
			if (source == DamageSource.inFire || source == DamageSource.lava)
				player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 60, 1));
			return new net.minecraftforge.common.ISpecialArmor.ArmorProperties(10, 1.0D, 0x7fffffff);
		}
		if (super.armorType == 3 && source == DamageSource.fall)
			return new net.minecraftforge.common.ISpecialArmor.ArmorProperties(10, damage >= 8D ? 0.875D : 1.0D, ((armor.getMaxDamage() - armor.getItemDamage()) + 1) * 2 * 25);
		else
			return new net.minecraftforge.common.ISpecialArmor.ArmorProperties(0, 1.0D, (((armor.getMaxDamage() - armor.getItemDamage()) + 1) / 2) * 25);
	}

	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
	{
		if (hazmatAbsorbs(source) && hasCompleteHazmat(entity))
			return;
		int damageTotal = damage * 2;
		if (super.armorType == 3 && source == DamageSource.fall)
			damageTotal = (damage + 1) / 2;
		stack.damageItem(damageTotal, entity);
	}

	public void onEntityLivingFallEvent(LivingFallEvent event)
	{
		if (IC2.platform.isSimulating() && (event.entity instanceof EntityPlayer))
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			ItemStack armor = player.inventory.armorInventory[0];
			if (armor != null && armor.getItem() == this)
			{
				int fallDamage = (int)event.distance - 3;
				if (fallDamage >= 8)
					return;
				int armorDamage = (fallDamage + 1) / 2;
				if (armorDamage <= armor.getMaxDamage() - armor.getItemDamage() && armorDamage >= 0)
				{
					armor.damageItem(armorDamage, player);
					event.setCanceled(true);
				}
			}
		}
	}

	public boolean isRepairable()
	{
		return true;
	}

	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
	{
		return 1;
	}

	public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack)
	{
		boolean ret = false;
		if (super.armorType == 0)
		{
			if (player.isBurning() && hasCompleteHazmat(player))
			{
				if (isInLava(player))
					player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 20, 0, true));
				player.extinguish();
			}
			if (player.getAir() <= 100 && player.inventory.hasItem(Ic2Items.airCell.itemID))
			{
				player.inventory.consumeInventoryItem(Ic2Items.airCell.itemID);
				player.inventory.addItemStackToInventory(new ItemStack(Ic2Items.cell.getItem()));
				player.setAir(player.getAir() + 150);
				ret = true;
			}
		}
		if (ret)
			player.inventoryContainer.detectAndSendChanges();
	}

	public boolean isInLava(EntityPlayer player)
	{
		double var2 = ((Entity) (player)).posY + 0.02D;
		int var4 = MathHelper.floor_double(((Entity) (player)).posX);
		int var5 = MathHelper.floor_float(MathHelper.floor_double(var2));
		int var6 = MathHelper.floor_double(((Entity) (player)).posZ);
		int var7 = ((Entity) (player)).worldObj.getBlockId(var4, var5, var6);
		if (var7 != 0 && (Block.blocksList[var7].blockMaterial == Material.lava || Block.blocksList[var7].blockMaterial == Material.fire))
		{
			float var8 = BlockFluid.getFluidHeightPercent(((Entity) (player)).worldObj.getBlockMetadata(var4, var5, var6)) - 0.1111111F;
			float var9 = (float)(var5 + 1) - var8;
			return var2 < (double)var9;
		} else
		{
			return false;
		}
	}

	public static boolean hasCompleteHazmat(EntityLivingBase living)
	{
		if (!(living instanceof EntityPlayer))
		{
			return false;
		} else
		{
			EntityPlayer player = (EntityPlayer)living;
			ItemStack armor[] = player.inventory.armorInventory;
			return armor[0] != null && (armor[0].getItem() instanceof ItemArmorHazmat) && armor[1] != null && (armor[1].getItem() instanceof ItemArmorHazmat) && armor[2] != null && (armor[2].getItem() instanceof ItemArmorHazmat) && armor[3] != null && (armor[3].getItem() instanceof ItemArmorHazmat);
		}
	}

	public boolean hazmatAbsorbs(DamageSource source)
	{
		return source == DamageSource.inFire || source == DamageSource.inWall || source == DamageSource.lava || source == DamageSource.onFire || source == IC2DamageSource.electricity || source == IC2DamageSource.radiation;
	}

	public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player)
	{
		return false;
	}
}
