// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityIronFurnace.java

package ic2.core.block.machine.tileentity;

import ic2.core.*;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.*;
import ic2.core.block.machine.ContainerIronFurnace;
import ic2.core.block.machine.gui.GuiIronFurnace;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityIronFurnace extends TileEntityInventory
	implements IHasGui
{

	public int fuel;
	public int maxFuel;
	public short progress;
	public final short operationLength = 160;
	public final InvSlotProcessable inputSlot = new InvSlotProcessableSmelting(this, "input", 0, 1);
	public final InvSlotOutput outputSlot = new InvSlotOutput(this, "output", 2, 1);
	public final InvSlotConsumableFuel fuelSlot = new InvSlotConsumableFuel(this, "fuel", 1, 1, true);

	public TileEntityIronFurnace()
	{
		fuel = 0;
		maxFuel = 0;
		progress = 0;
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
		try
		{
			maxFuel = nbttagcompound.getInteger("maxFuel");
		}
		catch (Throwable e)
		{
			maxFuel = nbttagcompound.getShort("maxFuel");
		}
		progress = nbttagcompound.getShort("progress");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("fuel", fuel);
		nbttagcompound.setInteger("maxFuel", maxFuel);
		nbttagcompound.setShort("progress", progress);
	}

	public int gaugeProgressScaled(int i)
	{
		return (progress * i) / 160;
	}

	public int gaugeFuelScaled(int i)
	{
		if (maxFuel == 0)
		{
			maxFuel = fuel;
			if (maxFuel == 0)
				maxFuel = 160;
		}
		return (fuel * i) / maxFuel;
	}

	public boolean enableUpdateEntity()
	{
		return IC2.platform.isSimulating();
	}

	public void updateEntity()
	{
		super.updateEntity();
		boolean wasOperating = isBurning();
		boolean needsInvUpdate = false;
		if (fuel <= 0 && canOperate())
		{
			fuel = maxFuel = fuelSlot.consumeFuel();
			if (fuel > 0)
				needsInvUpdate = true;
		}
		if (isBurning() && canOperate())
		{
			progress++;
			if (progress >= 160)
			{
				progress = 0;
				operate();
				needsInvUpdate = true;
			}
		} else
		{
			progress = 0;
		}
		if (fuel > 0)
			fuel--;
		if (wasOperating != isBurning())
		{
			setActive(isBurning());
			needsInvUpdate = true;
		}
		if (needsInvUpdate)
			onInventoryChanged();
	}

	public void operate()
	{
		outputSlot.add(inputSlot.process(false));
	}

	public boolean isBurning()
	{
		return fuel > 0;
	}

	public boolean canOperate()
	{
		ItemStack result = inputSlot.process(true);
		if (result == null)
			return false;
		else
			return outputSlot.canAdd(result);
	}

	public ItemStack getResultFor(ItemStack itemstack)
	{
		return FurnaceRecipes.smelting().getSmeltingResult(itemstack);
	}

	public String getInvName()
	{
		return "Iron Furnace";
	}

	public ContainerBase getGuiContainer(EntityPlayer entityPlayer)
	{
		return new ContainerIronFurnace(entityPlayer, this);
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		return new GuiIronFurnace(new ContainerIronFurnace(entityPlayer, this));
	}

	public void onGuiClosed(EntityPlayer entityplayer)
	{
	}
}
