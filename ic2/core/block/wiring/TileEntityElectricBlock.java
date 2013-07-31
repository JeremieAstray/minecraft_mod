// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityElectricBlock.java

package ic2.core.block.wiring;

import ic2.api.Direction;
import ic2.api.energy.event.*;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.tile.IEnergyStorage;
import ic2.core.*;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;

// Referenced classes of package ic2.core.block.wiring:
//			ContainerElectricBlock, GuiElectricBlock

public abstract class TileEntityElectricBlock extends TileEntityInventory
	implements IEnergySink, IEnergySource, IHasGui, INetworkClientTileEntityEventListener, IEnergyStorage
{

	public final int tier;
	public int output;
	public int maxStorage;
	public int energy;
	public byte redstoneMode;
	public static byte redstoneModes = 6;
	private boolean isEmittingRedstone;
	private int redstoneUpdateInhibit;
	public boolean addedToEnergyNet;
	public final InvSlotCharge chargeSlot;
	public final InvSlotDischarge dischargeSlot;

	public TileEntityElectricBlock(int tier, int output, int maxStorage)
	{
		energy = 0;
		redstoneMode = 0;
		isEmittingRedstone = false;
		redstoneUpdateInhibit = 5;
		addedToEnergyNet = false;
		this.tier = tier;
		this.output = output;
		this.maxStorage = maxStorage;
		chargeSlot = new InvSlotCharge(this, 0, tier);
		dischargeSlot = new InvSlotDischarge(this, 1, tier, ic2.core.block.invslot.InvSlot.InvSide.BOTTOM);
	}

	public float getChargeLevel()
	{
		float ret = (float)energy / (float)maxStorage;
		if (ret > 1.0F)
			ret = 1.0F;
		return ret;
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		setActiveWithoutNotify(nbttagcompound.getBoolean("active"));
		energy = nbttagcompound.getInteger("energy");
		if (maxStorage > 0x7fffffff)
			energy *= 10;
		redstoneMode = nbttagcompound.getByte("redstoneMode");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		int write = energy;
		if (maxStorage > 0x7fffffff)
			write /= 10;
		nbttagcompound.setInteger("energy", write);
		nbttagcompound.setBoolean("active", getActive());
		nbttagcompound.setByte("redstoneMode", redstoneMode);
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
		boolean needsInvUpdate = false;
		if (energy > 0)
		{
			int sent = chargeSlot.charge(energy);
			energy -= sent;
			needsInvUpdate = sent > 0;
		}
		if (demandsEnergy() > 0)
		{
			int gain = dischargeSlot.discharge(maxStorage - energy, false);
			energy += gain;
			needsInvUpdate = gain > 0;
		}
		if (energy >= output && (redstoneMode != 4 || !super.worldObj.isBlockIndirectlyGettingPowered(super.xCoord, super.yCoord, super.zCoord)) && (redstoneMode != 5 || !super.worldObj.isBlockIndirectlyGettingPowered(super.xCoord, super.yCoord, super.zCoord) || energy >= maxStorage))
		{
			EnergyTileSourceEvent event = new EnergyTileSourceEvent(this, output);
			MinecraftForge.EVENT_BUS.post(event);
			energy -= output - event.amount;
		}
		boolean shouldEmitRedstone = shouldEmitRedstone();
		if (shouldEmitRedstone != isEmittingRedstone)
		{
			isEmittingRedstone = shouldEmitRedstone;
			setActive(isEmittingRedstone);
			super.worldObj.notifyBlocksOfNeighborChange(super.xCoord, super.yCoord, super.zCoord, super.worldObj.getBlockId(super.xCoord, super.yCoord, super.zCoord));
		}
		if (needsInvUpdate)
			onInventoryChanged();
	}

	public boolean isAddedToEnergyNet()
	{
		return addedToEnergyNet;
	}

	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction)
	{
		return !facingMatchesDirection(direction);
	}

	public boolean emitsEnergyTo(TileEntity receiver, Direction direction)
	{
		return facingMatchesDirection(direction);
	}

	public boolean facingMatchesDirection(Direction direction)
	{
		return direction.toSideValue() == getFacing();
	}

	public int getMaxEnergyOutput()
	{
		return output;
	}

	public int demandsEnergy()
	{
		return maxStorage - energy;
	}

	public int injectEnergy(Direction directionFrom, int amount)
	{
		if (amount > output)
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
		return output;
	}

	public ContainerBase getGuiContainer(EntityPlayer entityPlayer)
	{
		return new ContainerElectricBlock(entityPlayer, this);
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		return new GuiElectricBlock(new ContainerElectricBlock(entityPlayer, this));
	}

	public void onGuiClosed(EntityPlayer entityplayer)
	{
	}

	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side)
	{
		return getFacing() != side;
	}

	public void setFacing(short facing)
	{
		if (addedToEnergyNet)
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
		super.setFacing(facing);
		if (addedToEnergyNet)
		{
			addedToEnergyNet = false;
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			addedToEnergyNet = true;
		}
	}

	public boolean isEmittingRedstone()
	{
		return isEmittingRedstone;
	}

	public boolean shouldEmitRedstone()
	{
		boolean shouldEmitRedstone = false;
		switch (redstoneMode)
		{
		case 1: // '\001'
			shouldEmitRedstone = energy >= maxStorage;
			break;

		case 2: // '\002'
			shouldEmitRedstone = energy > output && energy < maxStorage;
			break;

		case 3: // '\003'
			shouldEmitRedstone = energy < output;
			break;
		}
		if (isEmittingRedstone == shouldEmitRedstone || redstoneUpdateInhibit == 0)
		{
			redstoneUpdateInhibit = 5;
			return shouldEmitRedstone;
		} else
		{
			redstoneUpdateInhibit--;
			return isEmittingRedstone;
		}
	}

	public void onNetworkEvent(EntityPlayer player, int event)
	{
		redstoneMode++;
		if (redstoneMode >= redstoneModes)
			redstoneMode = 0;
		switch (redstoneMode)
		{
		case 0: // '\0'
			IC2.platform.messagePlayer(player, "Redstone Behavior: Nothing", new Object[0]);
			break;

		case 1: // '\001'
			IC2.platform.messagePlayer(player, "Redstone Behavior: Emit if full", new Object[0]);
			break;

		case 2: // '\002'
			IC2.platform.messagePlayer(player, "Redstone Behavior: Emit if partially filled", new Object[0]);
			break;

		case 3: // '\003'
			IC2.platform.messagePlayer(player, "Redstone Behavior: Emit if empty", new Object[0]);
			break;

		case 4: // '\004'
			IC2.platform.messagePlayer(player, "Redstone Behavior: Do not output energy", new Object[0]);
			break;

		case 5: // '\005'
			IC2.platform.messagePlayer(player, "Redstone Behavior: Do not output energy unless full", new Object[0]);
			break;
		}
	}

	public int getStored()
	{
		return energy;
	}

	public int getCapacity()
	{
		return maxStorage;
	}

	public int getOutput()
	{
		return output;
	}

	public void setStored(int energy)
	{
		this.energy = energy;
	}

	public int addEnergy(int amount)
	{
		energy += amount;
		return amount;
	}

	public boolean isTeleporterCompatible(Direction side)
	{
		return true;
	}

}
