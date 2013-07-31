// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StackUtil.java

package ic2.core.util;

import ic2.api.Direction;
import ic2.core.block.personal.IPersonalBlock;
import java.util.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public final class StackUtil
{

	private static final Direction directions[] = Direction.values();
	private static final Random random = new Random();

	public StackUtil()
	{
	}

	public static List getAdjacentInventories(TileEntity source)
	{
		List inventories = new ArrayList();
		Direction arr$[] = directions;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Direction direction = arr$[i$];
			TileEntity target = direction.applyToTileEntity(source);
			if (!(target instanceof IInventory))
				continue;
			IInventory inventory = (IInventory)target;
			if (target instanceof TileEntityChest)
			{
				Direction arr$[] = directions;
				int len$ = arr$.length;
				for (int i$ = 0; i$ < len$; i$++)
				{
					Direction direction2 = arr$[i$];
					if (direction2 == Direction.YN || direction2 == Direction.YP)
						continue;
					TileEntity target2 = direction2.applyToTileEntity(target);
					if (!(target2 instanceof TileEntityChest))
						continue;
					inventory = new InventoryLargeChest("", inventory, (IInventory)target2);
					break;
				}

			}
			if (!(target instanceof IPersonalBlock) || (source instanceof IPersonalBlock) && ((IPersonalBlock)target).permitsAccess(((IPersonalBlock)source).getUsername()))
				inventories.add(inventory);
		}

		Collections.sort(inventories, new Comparator() {

			public int compare(IInventory a, IInventory b)
			{
				if ((a instanceof IPersonalBlock) || !(b instanceof IPersonalBlock))
					return -1;
				if ((b instanceof IPersonalBlock) || !(a instanceof IPersonalBlock))
					return 1;
				else
					return b.getSizeInventory() - a.getSizeInventory();
			}

			public volatile int compare(Object x0, Object x1)
			{
				return compare((IInventory)x0, (IInventory)x1);
			}

		});
		return inventories;
	}

	public static int distribute(TileEntity source, ItemStack itemStack, boolean simulate)
	{
		int transferred = 0;
		Iterator i$ = getAdjacentInventories(source).iterator();
		do
		{
			if (!i$.hasNext())
				break;
			IInventory inventory = (IInventory)i$.next();
			int amount = putInInventory(inventory, itemStack, simulate);
			transferred += amount;
			itemStack.stackSize -= amount;
		} while (itemStack.stackSize != 0);
		itemStack.stackSize += transferred;
		return transferred;
	}

	public static ItemStack fetch(TileEntity source, ItemStack itemStack, boolean simulate)
	{
		ItemStack ret = null;
		int oldStackSize = itemStack.stackSize;
		Iterator i$ = getAdjacentInventories(source).iterator();
label0:
		do
		{
			ItemStack transferred;
			do
			{
				if (!i$.hasNext())
					break label0;
				IInventory inventory = (IInventory)i$.next();
				transferred = getFromInventory(inventory, itemStack, simulate);
			} while (transferred == null);
			if (ret == null)
			{
				ret = transferred;
			} else
			{
				ret.stackSize += transferred.stackSize;
				itemStack.stackSize -= transferred.stackSize;
			}
		} while (ret.stackSize != itemStack.stackSize);
		itemStack.stackSize = oldStackSize;
		return ret;
	}

	public static void distributeDrop(TileEntity source, List itemStacks)
	{
		for (Iterator it = itemStacks.iterator(); it.hasNext();)
		{
			ItemStack itemStack = (ItemStack)it.next();
			int amount = distribute(source, itemStack, false);
			if (amount == itemStack.stackSize)
				it.remove();
			else
				itemStack.stackSize -= amount;
		}

		ItemStack itemStack;
		for (Iterator i$ = itemStacks.iterator(); i$.hasNext(); dropAsEntity(source.worldObj, source.xCoord, source.yCoord, source.zCoord, itemStack))
			itemStack = (ItemStack)i$.next();

		itemStacks.clear();
	}

	public static ItemStack getFromInventory(IInventory inventory, ItemStack itemStackDestination, boolean simulate)
	{
		ItemStack ret = null;
		int toTransfer = itemStackDestination.stackSize;
		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			ItemStack itemStack = inventory.getStackInSlot(i);
			if (itemStack == null || !isStackEqual(itemStack, itemStackDestination))
				continue;
			if (ret == null)
				ret = copyWithSize(itemStack, 0);
			int transfer = Math.min(toTransfer, itemStack.stackSize);
			if (!simulate)
			{
				itemStack.stackSize -= transfer;
				if (itemStack.stackSize == 0)
					inventory.setInventorySlotContents(i, null);
			}
			toTransfer -= transfer;
			ret.stackSize += transfer;
			if (toTransfer == 0)
				return ret;
		}

		return ret;
	}

	public static int putInInventory(IInventory inventory, ItemStack itemStackSource, boolean simulate)
	{
		int transferred = 0;
		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			if (!inventory.isItemValidForSlot(i, itemStackSource))
				continue;
			ItemStack itemStack = inventory.getStackInSlot(i);
			if (itemStack == null || !itemStack.isItemEqual(itemStackSource))
				continue;
			int transfer = Math.min(itemStackSource.stackSize - transferred, itemStack.getMaxStackSize() - itemStack.stackSize);
			if (!simulate)
				itemStack.stackSize += transfer;
			transferred += transfer;
			if (transferred == itemStackSource.stackSize)
				return transferred;
		}

		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			if (!inventory.isItemValidForSlot(i, itemStackSource))
				continue;
			ItemStack itemStack = inventory.getStackInSlot(i);
			if (itemStack != null)
				continue;
			int transfer = Math.min(itemStackSource.stackSize - transferred, itemStackSource.getMaxStackSize());
			if (!simulate)
			{
				ItemStack dest = copyWithSize(itemStackSource, transfer);
				inventory.setInventorySlotContents(i, dest);
			}
			transferred += transfer;
			if (transferred == itemStackSource.stackSize)
				return transferred;
		}

		return transferred;
	}

	public static void dropAsEntity(World world, int x, int y, int z, ItemStack itemStack)
	{
		if (itemStack == null)
		{
			return;
		} else
		{
			double f = 0.69999999999999996D;
			double dx = (double)world.rand.nextFloat() * f + (1.0D - f) * 0.5D;
			double dy = (double)world.rand.nextFloat() * f + (1.0D - f) * 0.5D;
			double dz = (double)world.rand.nextFloat() * f + (1.0D - f) * 0.5D;
			EntityItem entityItem = new EntityItem(world, (double)x + dx, (double)y + dy, (double)z + dz, itemStack.copy());
			entityItem.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(entityItem);
			return;
		}
	}

	public static ItemStack copyWithSize(ItemStack itemStack, int newSize)
	{
		ItemStack ret = itemStack.copy();
		ret.stackSize = newSize;
		return ret;
	}

	public static NBTTagCompound getOrCreateNbtData(ItemStack itemStack)
	{
		NBTTagCompound ret = itemStack.getTagCompound();
		if (ret == null)
		{
			ret = new NBTTagCompound("tag");
			itemStack.setTagCompound(ret);
		}
		return ret;
	}

	public static boolean isStackEqual(ItemStack stack1, ItemStack stack2)
	{
		return stack1 != null && stack2 != null && stack1.itemID == stack2.itemID && (!stack1.getHasSubtypes() && !stack1.isItemStackDamageable() || stack1.getItemDamage() == stack2.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack1, stack2);
	}

	public static boolean damageItemStack(ItemStack itemStack, int amount)
	{
		if (itemStack.attemptDamageItem(amount, random))
		{
			itemStack.stackSize--;
			itemStack.setItemDamage(0);
			return itemStack.stackSize <= 0;
		} else
		{
			return false;
		}
	}

}
