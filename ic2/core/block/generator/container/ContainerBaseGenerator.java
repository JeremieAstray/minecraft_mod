// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerBaseGenerator.java

package ic2.core.block.generator.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public abstract class ContainerBaseGenerator extends ContainerFullInv
{

	public final TileEntityBaseGenerator tileEntity;
	public short lastStorage;
	public int lastFuel;

	public ContainerBaseGenerator(EntityPlayer entityPlayer, TileEntityBaseGenerator tileEntity, int height, int chargeX, int chargeY)
	{
		super(entityPlayer, tileEntity, height);
		lastStorage = -1;
		lastFuel = -1;
		this.tileEntity = tileEntity;
		addSlotToContainer(new SlotInvSlot(tileEntity.chargeSlot, 0, chargeX, chargeY));
	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0; i < super.crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)super.crafters.get(i);
			if (tileEntity.storage != lastStorage)
				icrafting.sendProgressBarUpdate(this, 0, tileEntity.storage);
			if (tileEntity.fuel != lastFuel)
			{
				icrafting.sendProgressBarUpdate(this, 1, tileEntity.fuel & 0xffff);
				icrafting.sendProgressBarUpdate(this, 2, tileEntity.fuel >>> 16);
			}
		}

		lastStorage = tileEntity.storage;
		lastFuel = tileEntity.fuel;
	}

	public void updateProgressBar(int index, int value)
	{
		super.updateProgressBar(index, value);
		switch (index)
		{
		case 0: // '\0'
			tileEntity.storage = (short)value;
			break;

		case 1: // '\001'
			tileEntity.fuel = tileEntity.fuel & 0xffff0000 | value;
			break;

		case 2: // '\002'
			tileEntity.fuel = tileEntity.fuel & 0xffff | value << 16;
			break;
		}
	}
}
