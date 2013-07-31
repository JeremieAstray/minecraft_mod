// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemArmorQuantumSuit.java

package ic2.core.item.armor;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItemManager;
import ic2.core.*;
import ic2.core.init.InternalName;
import ic2.core.item.ItemTinCan;
import ic2.core.util.Keyboard;
import java.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import net.minecraftforge.common.*;
import net.minecraftforge.event.EventBus;
import net.minecraftforge.event.entity.living.LivingFallEvent;

// Referenced classes of package ic2.core.item.armor:
//			ItemArmorElectric

public class ItemArmorQuantumSuit extends ItemArmorElectric
{

	private static final Map potionRemovalCost = new HashMap();
	public static Map speedTickerMap = new HashMap();
	public static Map onGroundMap = new HashMap();
	public static Map enableQuantumSpeedOnSprintMap = new HashMap();
	private static final int extraFallDistanceProtection = 7;
	private float jumpCharge;

	public ItemArmorQuantumSuit(Configuration config, InternalName internalName, int armorType)
	{
		super(config, internalName, InternalName.quantum, armorType, 0xf4240, 1000, 3);
		if (armorType == 3)
			MinecraftForge.EVENT_BUS.register(this);
		potionRemovalCost.put(Integer.valueOf(Potion.poison.id), Integer.valueOf(10000));
		potionRemovalCost.put(Integer.valueOf(((Potion) (IC2Potion.radiation)).id), Integer.valueOf(10000));
		potionRemovalCost.put(Integer.valueOf(Potion.wither.id), Integer.valueOf(25000));
	}

	public net.minecraftforge.common.ISpecialArmor.ArmorProperties getProperties(EntityLivingBase entity, ItemStack armor, DamageSource source, double damage, int slot)
	{
		if (source == DamageSource.fall && super.armorType == 3)
		{
			int energyPerDamage = getEnergyPerDamage();
			int damageLimit = energyPerDamage <= 0 ? 0 : (25 * ElectricItem.manager.getCharge(armor)) / energyPerDamage;
			return new net.minecraftforge.common.ISpecialArmor.ArmorProperties(10, 1.0D, damageLimit);
		} else
		{
			return super.getProperties(entity, armor, source, damage, slot);
		}
	}

	public void onEntityLivingFallEvent(LivingFallEvent event)
	{
		if (IC2.platform.isSimulating() && (event.entity instanceof EntityLivingBase))
		{
			EntityLivingBase entity = (EntityLivingBase)event.entity;
			ItemStack armor = entity.getCurrentItemOrArmor(1);
			if (armor != null && armor.itemID == super.itemID)
			{
				int fallDamage = Math.max((int)event.distance - 3 - 7, 0);
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
		return super.armorType != 1 ? 1.0D : 1.1000000000000001D;
	}

	public int getEnergyPerDamage()
	{
		return 900;
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.rare;
	}

	public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack)
	{
		boolean ret = false;
		switch (super.armorType)
		{
		case 0: // '\0'
			IC2.platform.profilerStartSection("QuantumHelmet");
			int air = player.getAir();
			if (ElectricItem.manager.canUse(itemStack, 1000) && air < 100)
			{
				player.setAir(air + 200);
				ElectricItem.manager.use(itemStack, 1000, null);
				ret = true;
			} else
			if (air <= 0)
				IC2.achievements.issueAchievement(player, "starveWithQHelmet");
			if (ElectricItem.manager.canUse(itemStack, 1000) && player.getFoodStats().needFood())
			{
				int slot = -1;
				int i = 0;
				do
				{
					if (i >= player.inventory.mainInventory.length)
						break;
					if (player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].itemID == Ic2Items.filledTinCan.itemID)
					{
						slot = i;
						break;
					}
					i++;
				} while (true);
				if (slot > -1)
				{
					ItemTinCan can = (ItemTinCan)player.inventory.mainInventory[slot].getItem();
					player.getFoodStats().addStats(can.getHealAmount(), can.getSaturationModifier());
					can.onFoodEaten(player.inventory.mainInventory[slot], ((Entity) (player)).worldObj, player);
					can.onEaten(player);
					if (--player.inventory.mainInventory[slot].stackSize <= 0)
						player.inventory.mainInventory[slot] = null;
					ElectricItem.manager.use(itemStack, 1000, null);
					ret = true;
				}
			} else
			if (player.getFoodStats().getFoodLevel() <= 0)
				IC2.achievements.issueAchievement(player, "starveWithQHelmet");
			Iterator i$ = (new LinkedList(player.getActivePotionEffects())).iterator();
			do
			{
				if (!i$.hasNext())
					break;
				PotionEffect effect = (PotionEffect)i$.next();
				int id = effect.getPotionID();
				Integer cost = (Integer)potionRemovalCost.get(Integer.valueOf(id));
				if (cost != null)
				{
					cost = Integer.valueOf(cost.intValue() * (effect.getAmplifier() + 1));
					if (ElectricItem.manager.canUse(itemStack, cost.intValue()))
					{
						ElectricItem.manager.use(itemStack, cost.intValue(), null);
						IC2.platform.removePotion(player, id);
					}
				}
			} while (true);
			IC2.platform.profilerEndSection();
			break;

		case 1: // '\001'
			IC2.platform.profilerStartSection("QuantumBodyarmor");
			player.extinguish();
			IC2.platform.profilerEndSection();
			break;

		case 2: // '\002'
			IC2.platform.profilerStartSection("QuantumLeggings");
			boolean enableQuantumSpeedOnSprint = true;
			if (IC2.platform.isRendering())
				enableQuantumSpeedOnSprint = IC2.enableQuantumSpeedOnSprint;
			else
			if (enableQuantumSpeedOnSprintMap.containsKey(player))
				enableQuantumSpeedOnSprint = ((Boolean)enableQuantumSpeedOnSprintMap.get(player)).booleanValue();
			if (ElectricItem.manager.canUse(itemStack, 1000) && (((Entity) (player)).onGround || player.isInWater()) && IC2.keyboard.isForwardKeyDown(player) && (enableQuantumSpeedOnSprint && player.isSprinting() || !enableQuantumSpeedOnSprint && IC2.keyboard.isBoostKeyDown(player)))
			{
				int speedTicker = speedTickerMap.containsKey(player) ? ((Integer)speedTickerMap.get(player)).intValue() : 0;
				if (++speedTicker >= 10)
				{
					speedTicker = 0;
					ElectricItem.manager.use(itemStack, 1000, null);
					ret = true;
				}
				speedTickerMap.put(player, Integer.valueOf(speedTicker));
				float speed = 0.22F;
				if (player.isInWater())
				{
					speed = 0.1F;
					if (IC2.keyboard.isJumpKeyDown(player))
						player.motionY += 0.10000000149011612D;
				}
				if (speed > 0.0F)
					player.moveFlying(0.0F, 1.0F, speed);
			}
			IC2.platform.profilerEndSection();
			break;

		case 3: // '\003'
			IC2.platform.profilerStartSection("QuantumBoots");
			if (IC2.platform.isSimulating())
			{
				boolean wasOnGround = onGroundMap.containsKey(player) ? ((Boolean)onGroundMap.get(player)).booleanValue() : true;
				if (wasOnGround && !((Entity) (player)).onGround && IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isBoostKeyDown(player))
				{
					ElectricItem.manager.use(itemStack, 4000, null);
					ret = true;
				}
				onGroundMap.put(player, Boolean.valueOf(((Entity) (player)).onGround));
			} else
			{
				if (ElectricItem.manager.canUse(itemStack, 4000) && ((Entity) (player)).onGround)
					jumpCharge = 1.0F;
				if (((Entity) (player)).motionY >= 0.0D && jumpCharge > 0.0F && !player.isInWater())
					if (IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isBoostKeyDown(player))
					{
						if (jumpCharge == 1.0F)
						{
							player.motionX *= 3.5D;
							player.motionZ *= 3.5D;
						}
						player.motionY += jumpCharge * 0.3F;
						jumpCharge *= 0.75D;
					} else
					if (jumpCharge < 1.0F)
						jumpCharge = 0.0F;
			}
			IC2.platform.profilerEndSection();
			break;
		}
		if (ret)
			player.inventoryContainer.detectAndSendChanges();
	}

	public static void removePlayerReferences(EntityPlayer player)
	{
		speedTickerMap.remove(player);
		onGroundMap.remove(player);
		enableQuantumSpeedOnSprintMap.remove(player);
	}

}
