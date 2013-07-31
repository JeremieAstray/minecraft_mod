// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityMagnetizer.java

package ic2.core.block.machine.tileentity;

import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.core.*;
import ic2.core.block.TileEntityBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;

public class TileEntityMagnetizer extends TileEntityBlock
	implements IEnergySink
{

	public int energy;
	public int ticker;
	public int maxEnergy;
	public int maxInput;
	public boolean addedToEnergyNet;

	public TileEntityMagnetizer()
	{
		energy = 0;
		ticker = 0;
		maxEnergy = 100;
		maxInput = 32;
		addedToEnergyNet = false;
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		energy = nbttagcompound.getShort("energy");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setShort("energy", (short)energy);
	}

	public boolean enableUpdateEntity()
	{
		return IC2.platform.isSimulating();
	}

	public void onLoaded()
	{
		super.onLoaded();
		if (IC2.platform.isSimulating())
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			addedToEnergyNet = true;
		}
	}

	public void onUnloaded()
	{
		if (IC2.platform.isSimulating() && addedToEnergyNet)
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			addedToEnergyNet = false;
		}
		super.onUnloaded();
	}

	public void updateEntity()
	{
		super.updateEntity();
		if (ticker-- > 0)
			return;
		boolean change = false;
		for (int y = super.yCoord - 1; y > 0 && y >= super.yCoord - 20 && energy > 0 && super.worldObj.getBlockId(super.xCoord, y, super.zCoord) == Ic2Items.ironFence.itemID; y--)
		{
			int need = 15 - super.worldObj.getBlockMetadata(super.xCoord, y, super.zCoord);
			if (need <= 0)
				continue;
			change = true;
			if (need > energy)
				energy = need;
			super.worldObj.setBlockMetadataWithNotify(super.xCoord, y, super.zCoord, super.worldObj.getBlockMetadata(super.xCoord, y, super.zCoord) + need, 7);
			energy -= need;
		}

		for (int y = super.yCoord + 1; y < IC2.getWorldHeight(super.worldObj) && y <= super.yCoord + 20 && energy > 0 && super.worldObj.getBlockId(super.xCoord, y, super.zCoord) == Ic2Items.ironFence.itemID; y++)
		{
			int need = 15 - super.worldObj.getBlockMetadata(super.xCoord, y, super.zCoord);
			if (need <= 0)
				continue;
			change = true;
			if (need > energy)
				energy = need;
			super.worldObj.setBlockMetadataWithNotify(super.xCoord, y, super.zCoord, super.worldObj.getBlockMetadata(super.xCoord, y, super.zCoord) + need, 7);
			energy -= need;
		}

		if (!change)
			ticker = 10;
	}

	public boolean isAddedToEnergyNet()
	{
		return addedToEnergyNet;
	}

	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction)
	{
		return true;
	}

	public int demandsEnergy()
	{
		return maxEnergy - energy;
	}

	public int injectEnergy(Direction directionFrom, int amount)
	{
		if (amount > maxInput)
		{
			IC2.explodeMachineAt(super.worldObj, super.xCoord, super.yCoord, super.zCoord);
			return 0;
		}
		if (energy >= maxEnergy)
		{
			return amount;
		} else
		{
			energy += amount;
			return 0;
		}
	}

	public int getMaxSafeInput()
	{
		return maxInput;
	}
}
