// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HandHeldInventory.java

package ic2.core.item.tool;

import ic2.core.*;
import ic2.core.util.StackUtil;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class HandHeldInventory
	implements IHasGui
{

	protected ItemStack itemStack;
	protected ItemStack inventory[];

	public HandHeldInventory(EntityPlayer entityPlayer, ItemStack itemStack, int inventorySize)
	{
		this.itemStack = itemStack;
		inventory = new ItemStack[inventorySize];
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

	public void onGuiClosed(EntityPlayer entityPlayer)
	{
		if (IC2.platform.isSimulating())
		{
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

	public boolean matchesUid(int uid)
	{
		NBTTagCompound nbtTagCompound = StackUtil.getOrCreateNbtData(itemStack);
		return nbtTagCompound.getInteger("uid") == uid;
	}
}
