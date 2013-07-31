// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemElectricToolChainsaw.java

package ic2.core.item.tool;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItemManager;
import ic2.core.*;
import ic2.core.audio.*;
import ic2.core.init.InternalName;
import java.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.*;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.*;
import net.minecraftforge.event.EventBus;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

// Referenced classes of package ic2.core.item.tool:
//			ItemElectricTool

public class ItemElectricToolChainsaw extends ItemElectricTool
	implements IHitSoundOverride
{

	public static boolean wasEquipped = false;
	public static AudioSource audioSource;

	public ItemElectricToolChainsaw(Configuration config, InternalName internalName)
	{
		super(config, internalName, EnumToolMaterial.IRON, 100);
		maxCharge = 10000;
		transferLimit = 100;
		tier = 1;
		super.efficiencyOnProperMaterial = 12F;
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void init()
	{
		mineableBlocks.add(Block.planks);
		mineableBlocks.add(Block.bookShelf);
		mineableBlocks.add(Block.wood);
		mineableBlocks.add(Block.chest);
		mineableBlocks.add(Block.leaves);
		mineableBlocks.add(Block.web);
		mineableBlocks.add(Block.blocksList[Ic2Items.crop.itemID]);
		if (Ic2Items.rubberLeaves != null)
			mineableBlocks.add(Block.blocksList[Ic2Items.rubberLeaves.itemID]);
	}

	public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker)
	{
		if (attacker instanceof EntityPlayer)
		{
			if (ElectricItem.manager.use(itemstack, operationEnergyCost, attacker))
				entityliving.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)attacker), 9F);
			if ((entityliving instanceof EntityCreeper) && entityliving.func_110143_aJ() <= 0.0F)
				IC2.achievements.issueAchievement((EntityPlayer)attacker, "killCreeperChainsaw");
		}
		return false;
	}

	public boolean canHarvestBlock(Block block)
	{
		if (block.blockMaterial == Material.wood)
			return true;
		else
			return super.canHarvestBlock(block);
	}

	public void onEntityInteract(EntityInteractEvent event)
	{
		Entity entity = event.target;
		if (entity.worldObj.isRemote)
			return;
		EntityPlayer player = event.entityPlayer;
		ItemStack itemstack = player.inventory.mainInventory[player.inventory.currentItem];
		if (itemstack != null && itemstack.itemID == super.itemID && (entity instanceof IShearable) && ElectricItem.manager.use(itemstack, operationEnergyCost, player))
		{
			IShearable target = (IShearable)entity;
			if (target.isShearable(itemstack, entity.worldObj, (int)entity.posX, (int)entity.posY, (int)entity.posZ))
			{
				ArrayList drops = target.onSheared(itemstack, entity.worldObj, (int)entity.posX, (int)entity.posY, (int)entity.posZ, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemstack));
				for (Iterator i$ = drops.iterator(); i$.hasNext();)
				{
					ItemStack stack = (ItemStack)i$.next();
					EntityItem ent = entity.entityDropItem(stack, 1.0F);
					ent.motionY += Item.itemRand.nextFloat() * 0.05F;
					ent.motionX += (Item.itemRand.nextFloat() - Item.itemRand.nextFloat()) * 0.1F;
					ent.motionZ += (Item.itemRand.nextFloat() - Item.itemRand.nextFloat()) * 0.1F;
				}

			}
		}
	}

	public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player)
	{
		if (((Entity) (player)).worldObj.isRemote)
			return false;
		int id = ((Entity) (player)).worldObj.getBlockId(X, Y, Z);
		if (Block.blocksList[id] != null && (Block.blocksList[id] instanceof IShearable))
		{
			IShearable target = (IShearable)Block.blocksList[id];
			if (target.isShearable(itemstack, ((Entity) (player)).worldObj, X, Y, Z) && ElectricItem.manager.use(itemstack, operationEnergyCost, player))
			{
				ArrayList drops = target.onSheared(itemstack, ((Entity) (player)).worldObj, X, Y, Z, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemstack));
				EntityItem entityitem;
				for (Iterator i$ = drops.iterator(); i$.hasNext(); ((Entity) (player)).worldObj.spawnEntityInWorld(entityitem))
				{
					ItemStack stack = (ItemStack)i$.next();
					float f = 0.7F;
					double d = (double)(Item.itemRand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
					double d1 = (double)(Item.itemRand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
					double d2 = (double)(Item.itemRand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
					entityitem = new EntityItem(((Entity) (player)).worldObj, (double)X + d, (double)Y + d1, (double)Z + d2, stack);
					entityitem.delayBeforeCanPickup = 10;
				}

				player.addStat(StatList.mineBlockStatArray[id], 1);
			}
		}
		return false;
	}

	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
	{
		boolean isEquipped = flag && (entity instanceof EntityLivingBase);
		if (IC2.platform.isRendering())
		{
			if (isEquipped && !wasEquipped)
			{
				if (audioSource == null)
					audioSource = IC2.audioManager.createSource(entity, PositionSpec.Hand, "Tools/Chainsaw/ChainsawIdle.ogg", true, false, IC2.audioManager.defaultVolume);
				if (audioSource != null)
					audioSource.play();
			} else
			if (!isEquipped && audioSource != null)
			{
				audioSource.stop();
				audioSource.remove();
				audioSource = null;
				if (entity instanceof EntityLivingBase)
					IC2.audioManager.playOnce(entity, PositionSpec.Hand, "Tools/Chainsaw/ChainsawStop.ogg", true, IC2.audioManager.defaultVolume);
			} else
			if (audioSource != null)
				audioSource.updatePosition();
			wasEquipped = isEquipped;
		}
	}

	public String getHitSoundForBlock(int x, int y, int z)
	{
		String soundEffects[] = {
			"Tools/Chainsaw/ChainsawUseOne.ogg", "Tools/Chainsaw/ChainsawUseTwo.ogg"
		};
		return soundEffects[Item.itemRand.nextInt(soundEffects.length)];
	}

}
