// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerIronFurnace.java

package ic2.core.block.machine;

import ic2.core.ContainerFullInv;
import ic2.core.block.machine.tileentity.TileEntityIronFurnace;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public class ContainerIronFurnace extends ContainerFullInv
{

	public final TileEntityIronFurnace tileEntity;
	private short lastProgress;
	private int lastFuel;
	private int lastMaxFuel;

	public ContainerIronFurnace(EntityPlayer entityPlayer, TileEntityIronFurnace tileEntity)
	{
		super(entityPlayer, tileEntity, 166);
		lastProgress = -1;
		lastFuel = -1;
		lastMaxFuel = -1;
		this.tileEntity = tileEntity;
		addSlotToContainer(new SlotInvSlot(tileEntity.inputSlot, 0, 56, 17));
		addSlotToContainer(new SlotInvSlot(tileEntity.fuelSlot, 0, 56, 53));
		addSlotToContainer(new SlotInvSlot(tileEntity.outputSlot, 0, 116, 35));
	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0; i < super.crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)super.crafters.get(i);
			if (tileEntity.progress != lastProgress)
				icrafting.sendProgressBarUpdate(this, 0, tileEntity.progress);
			if (tileEntity.fuel != lastFuel)
			{
				icrafting.sendProgressBarUpdate(this, 1, tileEntity.fuel);
				icrafting.sendProgressBarUpdate(this, 2, tileEntity.fuel);
			}
			if (tileEntity.maxFuel != lastMaxFuel)
			{
				icrafting.sendProgressBarUpdate(this, 3, tileEntity.maxFuel);
				icrafting.sendProgressBarUpdate(this, 4, tileEntity.maxFuel);
			}
		}

		lastProgress = tileEntity.progress;
		lastFuel = tileEntity.fuel;
		lastMaxFuel = tileEntity.maxFuel;
	}

	public void updateProgressBar(int index, int value)
	{
		super.updateProgressBar(index, value);
		switch (index)
		{
		case 0: // '\0'
			tileEntity.progress = (short)value;
			break;

		case 1: // '\001'
			tileEntity.fuel = tileEntity.fuel & 0xffff0000 | value;
			break;

		case 2: // '\002'
			tileEntity.fuel = tileEntity.fuel & 0xffff | value << 16;
			break;

		case 3: // '\003'
			tileEntity.maxFuel = tileEntity.maxFuel & 0xffff0000 | value;
			break;

		case 4: // '\004'
			tileEntity.maxFuel = tileEntity.maxFuel & 0xffff | value << 16;
			break;
		}
	}
}
