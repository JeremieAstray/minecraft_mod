// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityBaseGenerator.java

package ic2.core.block.generator.tileentity;

import ic2.api.Direction;
import ic2.api.energy.event.*;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItemManager;
import ic2.core.*;
import ic2.core.audio.*;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlotCharge;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;

public abstract class TileEntityBaseGenerator extends TileEntityInventory
	implements IEnergySource, IHasGui
{

	public static Random random = new Random();
	public int fuel;
	public short storage;
	public final short maxStorage;
	public int production;
	public int ticksSinceLastActiveUpdate;
	public int activityMeter;
	public boolean addedToEnergyNet;
	public AudioSource audioSource;
	public InvSlotCharge chargeSlot;

	public TileEntityBaseGenerator(int production, int maxStorage)
	{
		fuel = 0;
		storage = 0;
		activityMeter = 0;
		addedToEnergyNet = false;
		this.production = production;
		this.maxStorage = (short)maxStorage;
		ticksSinceLastActiveUpdate = random.nextInt(256);
		chargeSlot = new InvSlotCharge(this, 0, 1);
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		try
		{
			fuel = nbttagcompound.getInteger("fuel");
		}
		catch (Throwable e)
		{
			fuel = nbttagcompound.getShort("fuel");
		}
		storage = nbttagcompound.getShort("storage");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("fuel", fuel);
		nbttagcompound.setShort("storage", storage);
	}

	public int gaugeStorageScaled(int i)
	{
		return (storage * i) / maxStorage;
	}

	public abstract int gaugeFuelScaled(int i);

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
		if (IC2.platform.isRendering() && audioSource != null)
		{
			IC2.audioManager.removeSources(this);
			audioSource = null;
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
		boolean needsInvUpdate = false;
		if (needsFuel())
			needsInvUpdate = gainFuel();
		boolean newActive = gainEnergy();
		if (storage > maxStorage)
			storage = maxStorage;
		if (storage > 0)
		{
			if (chargeSlot.getItem() != null)
			{
				int used = ElectricItem.manager.charge(chargeSlot.get(), storage, 1, false, false);
				storage -= used;
				if (used > 0)
					needsInvUpdate = true;
			}
			int output = Math.min(production, storage);
			if (output > 0)
				storage += sendEnergy(output) - output;
		}
		if (needsInvUpdate)
			onInventoryChanged();
		if (!delayActiveUpdate())
		{
			setActive(newActive);
		} else
		{
			if (ticksSinceLastActiveUpdate % 256 == 0)
			{
				setActive(activityMeter > 0);
				activityMeter = 0;
			}
			if (newActive)
				activityMeter++;
			else
				activityMeter--;
			ticksSinceLastActiveUpdate++;
		}
	}

	public boolean gainEnergy()
	{
		if (isConverting())
		{
			storage += production;
			fuel--;
			return true;
		} else
		{
			return false;
		}
	}

	public boolean isConverting()
	{
		return fuel > 0 && storage + production <= maxStorage;
	}

	public boolean needsFuel()
	{
		return fuel <= 0 && storage + production <= maxStorage;
	}

	public abstract boolean gainFuel();

	public int sendEnergy(int send)
	{
		EnergyTileSourceEvent event = new EnergyTileSourceEvent(this, send);
		MinecraftForge.EVENT_BUS.post(event);
		return event.amount;
	}

	public boolean isAddedToEnergyNet()
	{
		return addedToEnergyNet;
	}

	public boolean emitsEnergyTo(TileEntity receiver, Direction direction)
	{
		return true;
	}

	public int getMaxEnergyOutput()
	{
		return production;
	}

	public abstract String getInvName();

	public void onGuiClosed(EntityPlayer entityplayer)
	{
	}

	public String getOperationSoundFile()
	{
		return null;
	}

	public boolean delayActiveUpdate()
	{
		return false;
	}

	public void onNetworkUpdate(String field)
	{
		if (field.equals("active") && prevActive != getActive())
		{
			if (audioSource == null && getOperationSoundFile() != null)
				audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, getOperationSoundFile(), true, false, IC2.audioManager.defaultVolume);
			if (getActive())
			{
				if (audioSource != null)
					audioSource.play();
			} else
			if (audioSource != null)
				audioSource.stop();
		}
		super.onNetworkUpdate(field);
	}

	public float getWrenchDropRate()
	{
		return 0.9F;
	}

}
