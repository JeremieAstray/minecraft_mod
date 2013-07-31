// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityBarrel.java

package ic2.core.block;

import ic2.core.Ic2Items;
import ic2.core.item.ItemBooze;
import java.util.Random;
import net.minecraft.entity.player.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityBarrel extends TileEntity
{

	public int type;
	public int boozeAmount;
	public int age;
	public boolean detailed;
	public int treetapSide;
	public int hopsCount;
	public int wheatCount;
	public int solidRatio;
	public int hopsRatio;
	public int timeRatio;

	public TileEntityBarrel()
	{
		type = 0;
		boozeAmount = 0;
		age = 0;
		detailed = true;
		treetapSide = 0;
		hopsCount = 0;
		wheatCount = 0;
		solidRatio = 0;
		hopsRatio = 0;
		timeRatio = 0;
	}

	public void set(int value)
	{
		type = ItemBooze.getTypeOfValue(value);
		if (type > 0)
			boozeAmount = ItemBooze.getAmountOfValue(value);
		if (type == 1)
		{
			detailed = false;
			hopsRatio = ItemBooze.getHopsRatioOfBeerValue(value);
			solidRatio = ItemBooze.getSolidRatioOfBeerValue(value);
			timeRatio = ItemBooze.getTimeRatioOfBeerValue(value);
		}
		if (type == 2)
		{
			detailed = true;
			age = (timeNedForRum(boozeAmount) * ItemBooze.getProgressOfRumValue(value)) / 100;
		}
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		type = nbttagcompound.getByte("type");
		boozeAmount = nbttagcompound.getByte("waterCount");
		age = nbttagcompound.getInteger("age");
		treetapSide = nbttagcompound.getByte("treetapSide");
		detailed = nbttagcompound.getBoolean("detailed");
		if (type == 1)
		{
			if (detailed)
			{
				hopsCount = nbttagcompound.getByte("hopsCount");
				wheatCount = nbttagcompound.getByte("wheatCount");
			}
			solidRatio = nbttagcompound.getByte("solidRatio");
			hopsRatio = nbttagcompound.getByte("hopsRatio");
			timeRatio = nbttagcompound.getByte("timeRatio");
		}
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setByte("type", (byte)type);
		nbttagcompound.setByte("waterCount", (byte)boozeAmount);
		nbttagcompound.setInteger("age", age);
		nbttagcompound.setByte("treetapSide", (byte)treetapSide);
		nbttagcompound.setBoolean("detailed", detailed);
		if (type == 1)
		{
			if (detailed)
			{
				nbttagcompound.setByte("hopsCount", (byte)hopsCount);
				nbttagcompound.setByte("wheatCount", (byte)wheatCount);
			}
			nbttagcompound.setByte("solidRatio", (byte)solidRatio);
			nbttagcompound.setByte("hopsRatio", (byte)hopsRatio);
			nbttagcompound.setByte("timeRatio", (byte)timeRatio);
		}
	}

	public void updateEntity()
	{
		if (!isEmpty() && treetapSide < 2)
		{
			age++;
			if (type == 1 && timeRatio < 5)
			{
				int x = timeRatio;
				if (x == 4)
					x += 2;
				if ((double)age >= 24000D * Math.pow(3D, x))
				{
					age = 0;
					timeRatio++;
				}
			}
		}
	}

	public boolean isEmpty()
	{
		return type == 0 || boozeAmount <= 0;
	}

	public boolean rightclick(EntityPlayer player)
	{
		ItemStack cur = player.getCurrentEquippedItem();
		if (cur == null)
			return false;
		if (cur.itemID == Item.bucketWater.itemID)
			if (!detailed || boozeAmount + 1 > 32 || type > 1)
			{
				return false;
			} else
			{
				type = 1;
				cur.itemID = Item.bucketEmpty.itemID;
				boozeAmount++;
				return true;
			}
		if (cur.itemID == Ic2Items.waterCell.itemID)
		{
			if (!detailed || type > 1)
				return false;
			type = 1;
			int wantgive = cur.stackSize;
			if (player.isSneaking())
				wantgive = 1;
			if (boozeAmount + wantgive > 32)
				wantgive = 32 - boozeAmount;
			if (wantgive <= 0)
				return false;
			boozeAmount += wantgive;
			cur.stackSize -= wantgive;
			if (cur.stackSize <= 0)
				player.inventory.mainInventory[player.inventory.currentItem] = null;
			return true;
		}
		if (cur.itemID == Item.wheat.itemID)
		{
			if (!detailed || type > 1)
				return false;
			type = 1;
			int wantgive = cur.stackSize;
			if (player.isSneaking())
				wantgive = 1;
			if (wantgive > 64 - wheatCount)
				wantgive = 64 - wheatCount;
			if (wantgive <= 0)
				return false;
			wheatCount += wantgive;
			cur.stackSize -= wantgive;
			if (cur.stackSize <= 0)
				player.inventory.mainInventory[player.inventory.currentItem] = null;
			alterComposition();
			return true;
		}
		if (cur.itemID == Ic2Items.hops.itemID)
		{
			if (!detailed || type > 1)
				return false;
			type = 1;
			int wantgive = cur.stackSize;
			if (player.isSneaking())
				wantgive = 1;
			if (wantgive > 64 - hopsCount)
				wantgive = 64 - hopsCount;
			if (wantgive <= 0)
				return false;
			hopsCount += wantgive;
			cur.stackSize -= wantgive;
			if (cur.stackSize <= 0)
				player.inventory.mainInventory[player.inventory.currentItem] = null;
			alterComposition();
			return true;
		}
		if (cur.itemID == Item.reed.itemID)
		{
			if (age > 600 || type > 0 && type != 2)
				return false;
			type = 2;
			int wantgive = cur.stackSize;
			if (player.isSneaking())
				wantgive = 1;
			if (boozeAmount + wantgive > 32)
				wantgive = 32 - boozeAmount;
			if (wantgive <= 0)
				return false;
			boozeAmount += wantgive;
			cur.stackSize -= wantgive;
			if (cur.stackSize <= 0)
				player.inventory.mainInventory[player.inventory.currentItem] = null;
			return true;
		} else
		{
			return false;
		}
	}

	public void alterComposition()
	{
		if (timeRatio == 0)
			age = 0;
		if (timeRatio == 1)
			if (super.worldObj.rand.nextBoolean())
				timeRatio = 0;
			else
			if (super.worldObj.rand.nextBoolean())
				timeRatio = 5;
		if (timeRatio == 2 && super.worldObj.rand.nextBoolean())
			timeRatio = 5;
		if (timeRatio > 2)
			timeRatio = 5;
	}

	public boolean drainLiquid(int amount)
	{
		if (isEmpty())
			return false;
		if (amount > boozeAmount)
			return false;
		enforceUndetailed();
		if (type == 2)
		{
			int progress = (age * 100) / timeNedForRum(boozeAmount);
			boozeAmount -= amount;
			age = (progress / 100) * timeNedForRum(boozeAmount);
		} else
		{
			boozeAmount -= amount;
		}
		if (boozeAmount <= 0)
		{
			if (type == 1)
			{
				hopsCount = 0;
				wheatCount = 0;
				hopsRatio = 0;
				solidRatio = 0;
				timeRatio = 0;
			}
			type = 0;
			detailed = true;
			boozeAmount = 0;
		}
		return true;
	}

	public void enforceUndetailed()
	{
		if (!detailed)
			return;
		detailed = false;
		if (type == 1)
		{
			float hops = wheatCount <= 0 ? 10F : (float)hopsCount / (float)wheatCount;
			if (hopsCount <= 0 && wheatCount <= 0)
				hops = 0.0F;
			float solid = boozeAmount <= 0 ? 10F : (float)(hopsCount + wheatCount) / (float)boozeAmount;
			if (hops <= 0.25F)
				hopsRatio = 0;
			if (hops > 0.25F && hops <= 0.3333333F)
				hopsRatio = 1;
			if (hops > 0.3333333F && hops <= 0.5F)
				hopsRatio = 2;
			if (hops > 0.5F && hops < 2.0F)
				hopsRatio = 3;
			if (hops >= 2.0F && hops < 3F)
				hopsRatio = 4;
			if (hops >= 3F && hops < 4F)
				hopsRatio = 5;
			if (hops >= 4F && hops < 5F)
				hopsRatio = 6;
			if (hops >= 5F)
				timeRatio = 5;
			if (solid <= 0.4166667F && solid > 0.4166667F && solid <= 0.5F)
				solidRatio = 1;
			if (solid > 0.5F && solid < 1.0F)
				solidRatio = 2;
			if (solid == 1.0F)
				solidRatio = 3;
			if (solid > 1.0F && solid < 2.0F)
				solidRatio = 4;
			if (solid >= 2.0F && solid < 2.4F)
				solidRatio = 5;
			if (solid >= 2.4F && solid < 4F)
				solidRatio = 6;
			if (solid >= 4F)
				timeRatio = 5;
		}
	}

	public boolean useTreetapOn(EntityPlayer player, int side)
	{
		ItemStack cur = player.getCurrentEquippedItem();
		if (cur != null && cur.itemID == Ic2Items.treetap.itemID && cur.getItemDamage() == 0 && side > 1)
		{
			treetapSide = side;
			update();
			if (!player.capabilities.isCreativeMode)
			{
				player.inventory.mainInventory[player.inventory.currentItem].stackSize--;
				if (player.inventory.mainInventory[player.inventory.currentItem].stackSize == 0)
					player.inventory.mainInventory[player.inventory.currentItem] = null;
			}
			return true;
		} else
		{
			return false;
		}
	}

	public void update()
	{
		super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
	}

	public int calculateMetaValue()
	{
		if (isEmpty())
			return 0;
		if (type == 1)
		{
			enforceUndetailed();
			int value = 0;
			value |= timeRatio;
			value <<= 3;
			value |= hopsRatio;
			value <<= 3;
			value |= solidRatio;
			value <<= 5;
			value |= boozeAmount - 1;
			value <<= 2;
			value |= type;
			return value;
		}
		if (type == 2)
		{
			enforceUndetailed();
			int value = 0;
			int progress = (age * 100) / timeNedForRum(boozeAmount);
			if (progress > 100)
				progress = 100;
			value |= progress;
			value <<= 5;
			value |= boozeAmount - 1;
			value <<= 2;
			value |= type;
			return value;
		} else
		{
			return 0;
		}
	}

	public int timeNedForRum(int amount)
	{
		return (int)((double)(1200 * amount) * Math.pow(0.94999999999999996D, amount - 1));
	}
}
