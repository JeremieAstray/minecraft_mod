// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityTransformer.java

package ic2.core.block.wiring;

import ic2.api.Direction;
import ic2.api.energy.event.*;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.block.TileEntityBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;

public abstract class TileEntityTransformer extends TileEntityBlock
	implements IEnergySink, IEnergySource
{

	public int lowOutput;
	public int highOutput;
	public int maxStorage;
	public int energy;
	public boolean redstone;
	public boolean addedToEnergyNet;

	public TileEntityTransformer(int low, int high, int max)
	{
		energy = 0;
		redstone = false;
		addedToEnergyNet = false;
		lowOutput = low;
		highOutput = high;
		maxStorage = max;
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		energy = nbttagcompound.getInteger("energy");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("energy", energy);
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
		updateRedstone();
		if (redstone)
		{
			if (energy >= highOutput)
			{
				EnergyTileSourceEvent event = new EnergyTileSourceEvent(this, highOutput);
				MinecraftForge.EVENT_BUS.post(event);
				energy -= highOutput - event.amount;
			}
		} else
		{
			for (int i = 0; i < 4 && energy >= lowOutput; i++)
			{
				EnergyTileSourceEvent event = new EnergyTileSourceEvent(this, lowOutput);
				MinecraftForge.EVENT_BUS.post(event);
				energy -= lowOutput - event.amount;
			}

		}
	}

	public void updateRedstone()
	{
		boolean red = super.worldObj.isBlockIndirectlyGettingPowered(super.xCoord, super.yCoord, super.zCoord);
		if (red != redstone)
		{
			if (addedToEnergyNet)
				MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			addedToEnergyNet = false;
			redstone = red;
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			addedToEnergyNet = true;
			setActive(redstone);
		}
	}

	public boolean isAddedToEnergyNet()
	{
		return addedToEnergyNet;
	}

	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction)
	{
		if (redstone)
			return !facingMatchesDirection(direction);
		else
			return facingMatchesDirection(direction);
	}

	public boolean emitsEnergyTo(TileEntity receiver, Direction direction)
	{
		if (redstone)
			return facingMatchesDirection(direction);
		else
			return !facingMatchesDirection(direction);
	}

	public boolean facingMatchesDirection(Direction direction)
	{
		return direction.toSideValue() == getFacing();
	}

	public int getMaxEnergyOutput()
	{
		if (redstone)
			return highOutput;
		else
			return lowOutput;
	}

	public int demandsEnergy()
	{
		return maxStorage - energy;
	}

	public int injectEnergy(Direction directionFrom, int amount)
	{
		if (amount > getMaxSafeInput())
		{
			IC2.explodeMachineAt(super.worldObj, super.xCoord, super.yCoord, super.zCoord);
			return 0;
		}
		if (energy >= maxStorage)
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
		if (redstone)
			return lowOutput;
		if (highOutput != 2048)
			return highOutput;
		else
			return 0x7fffffff;
	}

	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side)
	{
		return getFacing() != side;
	}

	public void setFacing(short side)
	{
		if (addedToEnergyNet)
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
		super.setFacing(side);
		if (addedToEnergyNet)
		{
			addedToEnergyNet = false;
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			addedToEnergyNet = true;
		}
	}
}
