// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HandHeldCropnalyzer.java

package ic2.core.item.tool;

import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.api.item.*;
import ic2.core.*;
import ic2.core.item.ItemCropSeed;
import ic2.core.util.StackUtil;
import java.util.Random;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

// Referenced classes of package ic2.core.item.tool:
//			ContainerCropnalyzer, GuiCropnalyzer

public class HandHeldCropnalyzer
	implements IHasGui, ITickCallback
{

	private final ItemStack itemStack;
	private final ItemStack inventory[] = new ItemStack[3];

	public HandHeldCropnalyzer(EntityPlayer entityPlayer, ItemStack itemStack)
	{
		this.itemStack = itemStack;
		if (IC2.platform.isSimulating())
		{
			NBTTagCompound nbtTagCompound = StackUtil.getOrCreateNbtData(itemStack);
			nbtTagCompound.setInteger("uid", (new Random()).nextInt());
			NBTTagList nbtTagList = nbtTagCompound.getTagList("Items");
			for (int i = 0; i < nbtTagList.tagCount(); i++)
			{
				NBTTagCompound nbtTagCompoundSlot = (NBTTagCompound)nbtTagList.tagAt(i);
				int slot = nbtTagCompoundSlot.getByte("Slot");
				if (slot >= 0 && slot < inventory.length)
					inventory[slot] = ItemStack.loadItemStackFromNBT(nbtTagCompoundSlot);
			}

			IC2.addContinuousTickCallback(((Entity) (entityPlayer)).worldObj, this);
		}
	}

	public int getSizeInventory()
	{
		return inventory.length;
	}

	public ItemStack getStackInSlot(int i)
	{
		return inventory[i];
	}

	public ItemStack decrStackSize(int slot, int amount)
	{
		if (inventory[slot] != null)
		{
			if (inventory[slot].stackSize <= amount)
			{
				ItemStack itemstack = inventory[slot];
				inventory[slot] = null;
				return itemstack;
			}
			ItemStack ret = inventory[slot].splitStack(amount);
			if (inventory[slot].stackSize == 0)
				inventory[slot] = null;
			return ret;
		} else
		{
			return null;
		}
	}

	public void setInventorySlotContents(int slot, ItemStack itemStack)
	{
		inventory[slot] = itemStack;
		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
			itemStack.stackSize = getInventoryStackLimit();
	}

	public String getInvName()
	{
		return "Cropnalyzer";
	}

	public int getInventoryStackLimit()
	{
		return 64;
	}

	public void onInventoryChanged()
	{
	}

	public boolean isUseableByPlayer(EntityPlayer entityPlayer)
	{
		return true;
	}

	public void openChest()
	{
	}

	public void closeChest()
	{
	}

	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return null;
	}

	public boolean isInvNameLocalized()
	{
		return false;
	}

	public boolean isItemValidForSlot(int slot, ItemStack itemStack)
	{
		return false;
	}

	public ContainerBase getGuiContainer(EntityPlayer entityPlayer)
	{
		return new ContainerCropnalyzer(entityPlayer, this);
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		return new GuiCropnalyzer(new ContainerCropnalyzer(entityPlayer, this));
	}

	public void onGuiClosed(EntityPlayer entityPlayer)
	{
		if (IC2.platform.isSimulating())
		{
			IC2.removeContinuousTickCallback(((Entity) (entityPlayer)).worldObj, this);
			NBTTagCompound nbtTagCompound = StackUtil.getOrCreateNbtData(itemStack);
			boolean dropItself = false;
			for (int i = 0; i < getSizeInventory(); i++)
			{
				if (inventory[i] == null)
					continue;
				NBTTagCompound nbtTagCompoundSlot = StackUtil.getOrCreateNbtData(inventory[i]);
				if (nbtTagCompound.getInteger("uid") != nbtTagCompoundSlot.getInteger("uid"))
					continue;
				itemStack.stackSize = 1;
				inventory[i] = null;
				dropItself = true;
				break;
			}

			NBTTagList nbtTagList = new NBTTagList();
			for (int i = 0; i < inventory.length; i++)
				if (inventory[i] != null)
				{
					NBTTagCompound nbtTagCompoundSlot = new NBTTagCompound();
					nbtTagCompoundSlot.setByte("Slot", (byte)i);
					inventory[i].writeToNBT(nbtTagCompoundSlot);
					nbtTagList.appendTag(nbtTagCompoundSlot);
				}

			nbtTagCompound.setTag("Items", nbtTagList);
			if (dropItself)
			{
				StackUtil.dropAsEntity(((Entity) (entityPlayer)).worldObj, (int)((Entity) (entityPlayer)).posX, (int)((Entity) (entityPlayer)).posY, (int)((Entity) (entityPlayer)).posZ, itemStack);
			} else
			{
				for (int i = -1; i < entityPlayer.inventory.getSizeInventory(); i++)
				{
					ItemStack itemStackSlot;
					if (i == -1)
						itemStackSlot = entityPlayer.inventory.getItemStack();
					else
						itemStackSlot = entityPlayer.inventory.getStackInSlot(i);
					if (itemStackSlot == null)
						continue;
					NBTTagCompound nbtTagCompoundSlot = itemStackSlot.getTagCompound();
					if (nbtTagCompoundSlot == null || nbtTagCompound.getInteger("uid") != nbtTagCompoundSlot.getInteger("uid"))
						continue;
					itemStack.stackSize = 1;
					if (i == -1)
						entityPlayer.inventory.setItemStack(itemStack);
					else
						entityPlayer.inventory.setInventorySlotContents(i, itemStack);
					break;
				}

			}
		}
	}

	public void tickCallback(World world)
	{
		if (inventory[1] == null && inventory[0] != null && inventory[0].itemID == Ic2Items.cropSeed.itemID)
		{
			int level = ItemCropSeed.getScannedFromStack(inventory[0]);
			if (level == 4)
			{
				inventory[1] = inventory[0];
				inventory[0] = null;
				return;
			}
			if (inventory[2] == null || !(inventory[2].getItem() instanceof IElectricItem))
				return;
			int ned = energyForLevel(level);
			int got = ElectricItem.manager.discharge(inventory[2], ned, 2, true, false);
			if (got < ned)
				return;
			ItemCropSeed.incrementScannedOfStack(inventory[0]);
			inventory[1] = inventory[0];
			inventory[0] = null;
		}
	}

	public int energyForLevel(int i)
	{
		switch (i)
		{
		default:
			return 10;

		case 1: // '\001'
			return 90;

		case 2: // '\002'
			return 900;

		case 3: // '\003'
			return 9000;
		}
	}

	public CropCard crop()
	{
		return Crops.instance.getCropList()[ItemCropSeed.getIdFromStack(inventory[1])];
	}

	public int getScannedLevel()
	{
		if (inventory[1] == null || inventory[1].getItem() != Ic2Items.cropSeed.getItem())
			return -1;
		else
			return ItemCropSeed.getScannedFromStack(inventory[1]);
	}

	public String getSeedName()
	{
		return crop().name();
	}

	public String getSeedTier()
	{
		switch (crop().tier())
		{
		default:
			return "0";

		case 1: // '\001'
			return "I";

		case 2: // '\002'
			return "II";

		case 3: // '\003'
			return "III";

		case 4: // '\004'
			return "IV";

		case 5: // '\005'
			return "V";

		case 6: // '\006'
			return "VI";

		case 7: // '\007'
			return "VII";

		case 8: // '\b'
			return "VIII";

		case 9: // '\t'
			return "IX";

		case 10: // '\n'
			return "X";

		case 11: // '\013'
			return "XI";

		case 12: // '\f'
			return "XII";

		case 13: // '\r'
			return "XIII";

		case 14: // '\016'
			return "XIV";

		case 15: // '\017'
			return "XV";

		case 16: // '\020'
			return "XVI";
		}
	}

	public String getSeedDiscovered()
	{
		return crop().discoveredBy();
	}

	public String getSeedDesc(int i)
	{
		return crop().desc(i);
	}

	public int getSeedGrowth()
	{
		return ItemCropSeed.getGrowthFromStack(inventory[1]);
	}

	public int getSeedGain()
	{
		return ItemCropSeed.getGainFromStack(inventory[1]);
	}

	public int getSeedResistence()
	{
		return ItemCropSeed.getResistanceFromStack(inventory[1]);
	}

	public boolean matchesUid(int uid)
	{
		NBTTagCompound nbtTagCompound = StackUtil.getOrCreateNbtData(itemStack);
		return nbtTagCompound.getInteger("uid") == uid;
	}
}
