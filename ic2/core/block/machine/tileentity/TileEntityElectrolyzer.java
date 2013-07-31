// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityElectrolyzer.java

package ic2.core.block.machine.tileentity;

import ic2.core.*;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableId;
import ic2.core.block.machine.ContainerElectrolyzer;
import ic2.core.block.machine.gui.GuiElectrolyzer;
import ic2.core.block.wiring.TileEntityElectricBlock;
import java.util.Random;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityElectrolyzer extends TileEntityInventory
	implements IHasGui
{

	public static Random randomizer = new Random();
	public short energy;
	public TileEntityElectricBlock mfe;
	public int ticker;
	public final InvSlotConsumableId waterSlot;
	public final InvSlotConsumableId hydrogenSlot;

	public TileEntityElectrolyzer()
	{
		energy = 0;
		mfe = null;
		ticker = randomizer.nextInt(16);
		waterSlot = new InvSlotConsumableId(this, "water", 0, ic2.core.block.invslot.InvSlot.Access.IO, 1, ic2.core.block.invslot.InvSlot.InvSide.TOP, new int[] {
			Ic2Items.waterCell.itemID
		});
		hydrogenSlot = new InvSlotConsumableId(this, "hydrogen", 1, ic2.core.block.invslot.InvSlot.Access.IO, 1, ic2.core.block.invslot.InvSlot.InvSide.BOTTOM, new int[] {
			Ic2Items.electrolyzedWaterCell.itemID
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
		nbttagcompound.setShort("energy", energy);
	}

	public String getInvName()
	{
		return "Electrolyzer";
	}

	public boolean enableUpdateEntity()
	{
		return IC2.platform.isSimulating();
	}

	public void updateEntity()
	{
		super.updateEntity();
		boolean needsInvUpdate = false;
		boolean turnActive = false;
		if (ticker++ % 16 == 0)
			mfe = lookForMFE();
		if (mfe == null)
			return;
		if (shouldDrain() && canDrain())
		{
			needsInvUpdate = drain();
			turnActive = true;
		}
		if (shouldPower() && (canPower() || energy > 0))
		{
			needsInvUpdate = power();
			turnActive = true;
		}
		if (getActive() != turnActive)
		{
			setActive(turnActive);
			needsInvUpdate = true;
		}
		if (needsInvUpdate)
			onInventoryChanged();
	}

	public boolean shouldDrain()
	{
		return mfe != null && (double)mfe.energy / (double)mfe.maxStorage >= 0.69999999999999996D;
	}

	public boolean shouldPower()
	{
		return mfe != null && (double)mfe.energy / (double)mfe.maxStorage <= 0.29999999999999999D;
	}

	public boolean canDrain()
	{
		return !waterSlot.isEmpty() && (hydrogenSlot.isEmpty() || hydrogenSlot.get().stackSize < hydrogenSlot.get().getMaxStackSize());
	}

	public boolean canPower()
	{
		return !hydrogenSlot.isEmpty() && (waterSlot.isEmpty() || waterSlot.get().stackSize < waterSlot.get().getMaxStackSize());
	}

	public boolean drain()
	{
		mfe.energy -= processRate();
		energy += processRate();
		if (energy >= 20000)
		{
			energy -= 20000;
			waterSlot.consume(1);
			if (hydrogenSlot.isEmpty())
				hydrogenSlot.put(Ic2Items.electrolyzedWaterCell.copy());
			else
				hydrogenSlot.get().stackSize++;
			return true;
		} else
		{
			return false;
		}
	}

	public boolean power()
	{
		if (energy > 0)
		{
			int out = Math.min(energy, processRate());
			energy -= out;
			mfe.energy += out;
			return false;
		}
		energy += 12000 + 2000 * mfe.tier;
		hydrogenSlot.consume(1);
		if (waterSlot.isEmpty())
			waterSlot.put(Ic2Items.waterCell.copy());
		else
			waterSlot.get().stackSize++;
		return true;
	}

	public int processRate()
	{
		switch (mfe.tier)
		{
		default:
			return 2;

		case 2: // '\002'
			return 8;

		case 3: // '\003'
			return 32;
		}
	}

	public TileEntityElectricBlock lookForMFE()
	{
		ForgeDirection arr$[] = ForgeDirection.VALID_DIRECTIONS;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			ForgeDirection dir = arr$[i$];
			TileEntity te = super.worldObj.getBlockTileEntity(super.xCoord + dir.offsetX, super.yCoord + dir.offsetY, super.zCoord + dir.offsetZ);
			if (te instanceof TileEntityElectricBlock)
				return (TileEntityElectricBlock)te;
		}

		return null;
	}

	public int gaugeEnergyScaled(int i)
	{
		if (energy <= 0)
			return 0;
		int r = (energy * i) / 20000;
		if (r > i)
			r = i;
		return r;
	}

	public ContainerBase getGuiContainer(EntityPlayer entityPlayer)
	{
		return new ContainerElectrolyzer(entityPlayer, this);
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		return new GuiElectrolyzer(new ContainerElectrolyzer(entityPlayer, this));
	}

	public void onGuiClosed(EntityPlayer entityplayer)
	{
	}

}
