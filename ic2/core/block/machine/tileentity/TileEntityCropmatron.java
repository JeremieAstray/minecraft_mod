// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityCropmatron.java

package ic2.core.block.machine.tileentity;

import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.core.*;
import ic2.core.block.TileEntityCrop;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotConsumableId;
import ic2.core.block.machine.ContainerCropmatron;
import ic2.core.block.machine.gui.GuiCropmatron;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;

public class TileEntityCropmatron extends TileEntityInventory
	implements IEnergySink, IHasGui
{

	public int energy;
	public int ticker;
	public int maxEnergy;
	public int scanX;
	public int scanY;
	public int scanZ;
	public boolean addedToEnergyNet;
	public static int maxInput = 32;
	public final InvSlotConsumable fertilizerSlot;
	public final InvSlotConsumable hydrationSlot;
	public final InvSlotConsumable weedExSlot;

	public TileEntityCropmatron()
	{
		energy = 0;
		ticker = 0;
		maxEnergy = 1000;
		scanX = -4;
		scanY = -1;
		scanZ = -4;
		addedToEnergyNet = false;
		fertilizerSlot = new InvSlotConsumableId(this, "fertilizer", 0, 3, new int[] {
			Ic2Items.fertilizer.itemID
		});
		hydrationSlot = new InvSlotConsumableId(this, "hydration", 3, 3, new int[] {
			Ic2Items.hydratingCell.itemID
		});
		weedExSlot = new InvSlotConsumableId(this, "weedEx", 6, 3, new int[] {
			Ic2Items.weedEx.itemID
		});
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
		fertilizerSlot.organize();
		hydrationSlot.organize();
		weedExSlot.organize();
		if (energy >= 31)
			scan();
	}

	public void scan()
	{
		scanX++;
		if (scanX > 4)
		{
			scanX = -4;
			scanZ++;
			if (scanZ > 4)
			{
				scanZ = -4;
				scanY++;
				if (scanY > 1)
					scanY = -1;
			}
		}
		energy--;
		TileEntity te = super.worldObj.getBlockTileEntity(super.xCoord + scanX, super.yCoord + scanY, super.zCoord + scanZ);
		if (te instanceof TileEntityCrop)
		{
			TileEntityCrop crop = (TileEntityCrop)te;
			if (!fertilizerSlot.isEmpty() && crop.applyFertilizer(false))
			{
				energy -= 10;
				fertilizerSlot.consume(1);
			}
			if (!hydrationSlot.isEmpty() && crop.applyHydration(false, hydrationSlot))
				energy -= 10;
			if (!weedExSlot.isEmpty() && crop.applyWeedEx(false))
			{
				energy -= 10;
				weedExSlot.damage(1);
			}
		}
	}

	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction)
	{
		return true;
	}

	public boolean isAddedToEnergyNet()
	{
		return addedToEnergyNet;
	}

	public int demandsEnergy()
	{
		return maxEnergy - energy;
	}

	public int gaugeEnergyScaled(int i)
	{
		if (energy <= 0)
			return 0;
		int r = (energy * i) / maxEnergy;
		if (r > i)
			r = i;
		return r;
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

	public String getInvName()
	{
		return "Crop-Matron";
	}

	public ContainerBase getGuiContainer(EntityPlayer entityPlayer)
	{
		return new ContainerCropmatron(entityPlayer, this);
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		return new GuiCropmatron(new ContainerCropmatron(entityPlayer, this));
	}

	public void onGuiClosed(EntityPlayer entityplayer)
	{
	}

}
