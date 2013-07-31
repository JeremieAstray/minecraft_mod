// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityElectricMachine.java

package ic2.core.block.machine.tileentity;

import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlotDischarge;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;

public abstract class TileEntityElectricMachine extends TileEntityInventory
	implements IEnergySink
{

	public int energy;
	public int maxEnergy;
	public int maxInput;
	private boolean addedToEnergyNet;
	public final InvSlotDischarge dischargeSlot;

	public TileEntityElectricMachine(int maxenergy, int tier, int oldDischargeIndex)
	{
		energy = 0;
		addedToEnergyNet = false;
		maxEnergy = maxenergy;
		switch (tier)
		{
		case 1: // '\001'
			maxInput = 32;
			break;

		case 2: // '\002'
			maxInput = 128;
			break;

		case 3: // '\003'
			maxInput = 512;
			break;

		default:
			maxInput = 2048;
			break;
		}
		dischargeSlot = new InvSlotDischarge(this, oldDischargeIndex, tier);
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

	public boolean enableUpdateEntity()
	{
		return IC2.platform.isSimulating();
	}

	public void updateEntity()
	{
		super.updateEntity();
		if (energy < maxEnergy)
		{
			int amount = dischargeSlot.discharge(maxEnergy - energy, true);
			if (amount > 0)
			{
				energy += amount;
				onInventoryChanged();
			}
		}
	}

	public boolean isAddedToEnergyNet()
	{
		return addedToEnergyNet;
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

	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction)
	{
		return true;
	}

	public boolean isRedstonePowered()
	{
		return super.worldObj.isBlockIndirectlyGettingPowered(super.xCoord, super.yCoord, super.zCoord);
	}

	public void setTier(int tier)
	{
		dischargeSlot.setTier(tier);
		switch (tier)
		{
		case 1: // '\001'
			maxInput = 32;
			break;

		case 2: // '\002'
			maxInput = 128;
			break;

		case 3: // '\003'
			maxInput = 512;
			break;

		default:
			maxInput = 2048;
			break;
		}
	}
}
