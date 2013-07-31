// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemArmorJetpack.java

package ic2.core.item.armor;

import ic2.core.*;
import ic2.core.audio.*;
import ic2.core.init.InternalName;
import ic2.core.util.Keyboard;
import ic2.core.util.StackUtil;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.armor:
//			ItemArmorUtility

public class ItemArmorJetpack extends ItemArmorUtility
{

	public static AudioSource audioSource;
	private static boolean lastJetpackUsed = false;

	public ItemArmorJetpack(Configuration config, InternalName internalName)
	{
		super(config, internalName, InternalName.jetpack, 1);
		setMaxDamage(18002);
	}

	public int getCharge(ItemStack itemStack)
	{
		int ret = getMaxCharge(itemStack) - itemStack.getItemDamage() - 1;
		return ret <= 0 ? 0 : ret;
	}

	public int getMaxCharge(ItemStack itemStack)
	{
		return itemStack.getMaxDamage() - 2;
	}

	public void use(ItemStack itemStack, int amount)
	{
		int newCharge = getCharge(itemStack) - amount;
		if (newCharge < 0)
			newCharge = 0;
		itemStack.setItemDamage((1 + itemStack.getMaxDamage()) - newCharge);
	}

	public boolean useJetpack(EntityPlayer player, boolean hoverMode)
	{
		ItemStack jetpack = player.inventory.armorInventory[2];
		if (getCharge(jetpack) == 0)
			return false;
		boolean electric = jetpack.itemID != Ic2Items.jetpack.itemID;
		float power = 1.0F;
		float dropPercentage = 0.2F;
		if (electric)
		{
			power = 0.7F;
			dropPercentage = 0.05F;
		}
		if ((float)getCharge(jetpack) / (float)getMaxCharge(jetpack) <= dropPercentage)
			power *= (float)getCharge(jetpack) / ((float)getMaxCharge(jetpack) * dropPercentage);
		if (IC2.keyboard.isForwardKeyDown(player))
		{
			float retruster = 0.15F;
			if (hoverMode)
				retruster = 0.5F;
			if (electric)
				retruster += 0.15F;
			float forwardpower = power * retruster * 2.0F;
			if (forwardpower > 0.0F)
				player.moveFlying(0.0F, 0.4F * forwardpower, 0.02F);
		}
		int worldHeight = IC2.getWorldHeight(((Entity) (player)).worldObj);
		int maxFlightHeight = electric ? (int)((float)worldHeight / 1.28F) : worldHeight;
		double y = ((Entity) (player)).posY;
		if (y > (double)(maxFlightHeight - 25))
		{
			if (y > (double)maxFlightHeight)
				y = maxFlightHeight;
			power = (float)((double)power * (((double)maxFlightHeight - y) / 25D));
		}
		double prevmotion = ((Entity) (player)).motionY;
		player.motionY = Math.min(((Entity) (player)).motionY + (double)(power * 0.2F), 0.60000002384185791D);
		if (hoverMode)
		{
			float maxHoverY = -0.1F;
			if (electric && IC2.keyboard.isJumpKeyDown(player))
				maxHoverY = 0.1F;
			if (((Entity) (player)).motionY > (double)maxHoverY)
			{
				player.motionY = maxHoverY;
				if (prevmotion > ((Entity) (player)).motionY)
					player.motionY = prevmotion;
			}
		}
		int consume = 9;
		if (hoverMode)
			consume = 6;
		if (electric)
			consume -= 2;
		use(jetpack, consume);
		player.fallDistance = 0.0F;
		player.distanceWalkedModified = 0.0F;
		IC2.platform.resetPlayerInAirTime(player);
		return true;
	}

	public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack)
	{
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
		boolean hoverMode = nbtData.getBoolean("hoverMode");
		byte toggleTimer = nbtData.getByte("toggleTimer");
		boolean jetpackUsed = false;
		if (IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isModeSwitchKeyDown(player) && toggleTimer == 0)
		{
			toggleTimer = 10;
			hoverMode = !hoverMode;
			if (IC2.platform.isSimulating())
			{
				nbtData.setBoolean("hoverMode", hoverMode);
				if (hoverMode)
					IC2.platform.messagePlayer(player, "Hover Mode enabled.", new Object[0]);
				else
					IC2.platform.messagePlayer(player, "Hover Mode disabled.", new Object[0]);
			}
		}
		if (IC2.keyboard.isJumpKeyDown(player) || hoverMode && ((Entity) (player)).motionY < -0.34999999403953552D)
			jetpackUsed = useJetpack(player, hoverMode);
		if (IC2.platform.isSimulating() && toggleTimer > 0)
		{
			toggleTimer--;
			nbtData.setByte("toggleTimer", toggleTimer);
		}
		if (IC2.platform.isRendering() && player == IC2.platform.getPlayerInstance())
		{
			if (lastJetpackUsed != jetpackUsed)
			{
				if (jetpackUsed)
				{
					if (audioSource == null)
						audioSource = IC2.audioManager.createSource(player, PositionSpec.Backpack, "Tools/Jetpack/JetpackLoop.ogg", true, false, IC2.audioManager.defaultVolume);
					if (audioSource != null)
						audioSource.play();
				} else
				if (audioSource != null)
				{
					audioSource.remove();
					audioSource = null;
				}
				lastJetpackUsed = jetpackUsed;
			}
			if (audioSource != null)
				audioSource.updatePosition();
		}
		if (jetpackUsed)
			player.inventoryContainer.detectAndSendChanges();
	}

	public void getSubItems(int i, CreativeTabs tabs, List itemList)
	{
		itemList.add(new ItemStack(this, 1, 1));
	}

}
