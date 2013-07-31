// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityInventory.java

package ic2.core.block;

import ic2.core.block.invslot.InvSlot;
import java.util.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

// Referenced classes of package ic2.core.block:
//			TileEntityBlock

public abstract class TileEntityInventory extends TileEntityBlock
	implements ISidedInventory
{

	public final List invSlots = new ArrayList();

	public TileEntityInventory()
	{
	}

	public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);
		if (nbtTagCompound.hasKey("Items"))
		{
			NBTTagList nbtTagList = nbtTagCompound.getTagList("Items");
			for (int i = 0; i < nbtTagList.tagCount(); i++)
			{
				NBTTagCompound nbtTagCompoundSlot = (NBTTagCompound)nbtTagList.tagAt(i);
				byte slot = nbtTagCompoundSlot.getByte("Slot");
				int maxOldStartIndex = -1;
				InvSlot maxSlot = null;
				Iterator i$ = invSlots.iterator();
				do
				{
					if (!i$.hasNext())
						break;
					InvSlot invSlot = (InvSlot)i$.next();
					if (invSlot.oldStartIndex <= slot && invSlot.oldStartIndex > maxOldStartIndex)
					{
						maxOldStartIndex = invSlot.oldStartIndex;
						maxSlot = invSlot;
					}
				} while (true);
				if (maxSlot != null)
				{
					int index = Math.min(slot - maxOldStartIndex, maxSlot.size() - 1);
					maxSlot.put(index, ItemStack.loadItemStackFromNBT(nbtTagCompoundSlot));
				}
			}

		}
		NBTTagCompound invSlotsTag = nbtTagCompound.getCompoundTag("InvSlots");
		InvSlot invSlot;
		for (Iterator i$ = invSlots.iterator(); i$.hasNext(); invSlot.readFromNbt(invSlotsTag.getCompoundTag(invSlot.name)))
			invSlot = (InvSlot)i$.next();

	}

	public void writeToNBT(NBTTagCompound nbtTagCompound)
	{
		super.writeToNBT(nbtTagCompound);
		NBTTagCompound invSlotsTag = new NBTTagCompound();
		InvSlot invSlot;
		NBTTagCompound invSlotTag;
		for (Iterator i$ = invSlots.iterator(); i$.hasNext(); invSlotsTag.setTag(invSlot.name, invSlotTag))
		{
			invSlot = (InvSlot)i$.next();
			invSlotTag = new NBTTagCompound();
			invSlot.writeToNbt(invSlotTag);
		}

		nbtTagCompound.setTag("InvSlots", invSlotsTag);
	}

	public int getSizeInventory()
	{
		int ret = 0;
		for (Iterator i$ = invSlots.iterator(); i$.hasNext();)
		{
			InvSlot invSlot = (InvSlot)i$.next();
			ret += invSlot.size();
		}

		return ret;
	}

	public ItemStack getStackInSlot(int index)
	{
		for (Iterator i$ = invSlots.iterator(); i$.hasNext();)
		{
			InvSlot invSlot = (InvSlot)i$.next();
			if (index < invSlot.size())
				return invSlot.get(index);
			index -= invSlot.size();
		}

		return null;
	}

	public ItemStack decrStackSize(int index, int amount)
	{
		ItemStack itemStack = getStackInSlot(index);
		if (itemStack == null)
			return null;
		if (amount >= itemStack.stackSize)
		{
			setInventorySlotContents(index, null);
			return itemStack;
		} else
		{
			itemStack.stackSize -= amount;
			ItemStack ret = itemStack.copy();
			ret.stackSize = amount;
			return ret;
		}
	}

	public ItemStack getStackInSlotOnClosing(int index)
	{
		ItemStack ret = getStackInSlot(index);
		if (ret != null)
			setInventorySlotContents(index, null);
		return ret;
	}

	public void setInventorySlotContents(int index, ItemStack itemStack)
	{
		Iterator i$ = invSlots.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			InvSlot invSlot = (InvSlot)i$.next();
			if (index < invSlot.size())
			{
				invSlot.put(index, itemStack);
				break;
			}
			index -= invSlot.size();
		} while (true);
	}

	public abstract String getInvName();

	public boolean isInvNameLocalized()
	{
		return false;
	}

	public int getInventoryStackLimit()
	{
		return 64;
	}

	public boolean isUseableByPlayer(EntityPlayer entityPlayer)
	{
		return entityPlayer.getDistance((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D) <= 64D;
	}

	public void openChest()
	{
	}

	public void closeChest()
	{
	}

	public boolean isItemValidForSlot(int index, ItemStack itemStack)
	{
		InvSlot invSlot = getInvSlot(index);
		return invSlot != null && invSlot.canInput() && invSlot.accepts(itemStack);
	}

	public int[] getAccessibleSlotsFromSide(int var1)
	{
		int ret[] = new int[getSizeInventory()];
		for (int i = 0; i < ret.length; i++)
			ret[i] = i;

		return ret;
	}

	public boolean canInsertItem(int index, ItemStack itemStack, int side)
	{
		InvSlot targetSlot = getInvSlot(index);
		if (targetSlot == null)
			return false;
		if (!targetSlot.canInput() || !targetSlot.accepts(itemStack))
			return false;
		if (targetSlot.preferredSide != ic2.core.block.invslot.InvSlot.InvSide.ANY && targetSlot.preferredSide.matches(side))
			return true;
		for (Iterator i$ = invSlots.iterator(); i$.hasNext();)
		{
			InvSlot invSlot = (InvSlot)i$.next();
			if (invSlot != targetSlot && invSlot.preferredSide != ic2.core.block.invslot.InvSlot.InvSide.ANY && invSlot.preferredSide.matches(side) && invSlot.canInput() && invSlot.accepts(itemStack))
				return false;
		}

		return true;
	}

	public boolean canExtractItem(int index, ItemStack itemStack, int side)
	{
		InvSlot targetSlot = getInvSlot(index);
		if (targetSlot == null)
			return false;
		if (!targetSlot.canOutput())
			return false;
		if (targetSlot.preferredSide != ic2.core.block.invslot.InvSlot.InvSide.ANY && targetSlot.preferredSide.matches(side))
			return true;
		for (Iterator i$ = invSlots.iterator(); i$.hasNext();)
		{
			InvSlot invSlot = (InvSlot)i$.next();
			if (invSlot != targetSlot && invSlot.preferredSide != ic2.core.block.invslot.InvSlot.InvSide.ANY && invSlot.preferredSide.matches(side) && invSlot.canOutput())
				return false;
		}

		return true;
	}

	public void addInvSlot(InvSlot invSlot)
	{
		invSlots.add(invSlot);
	}

	private InvSlot getInvSlot(int index)
	{
		for (Iterator i$ = invSlots.iterator(); i$.hasNext();)
		{
			InvSlot invSlot = (InvSlot)i$.next();
			if (index < invSlot.size())
				return invSlot;
			index -= invSlot.size();
		}

		return null;
	}
}
