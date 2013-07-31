// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerCropmatron.java

package ic2.core.block.machine;

import ic2.core.ContainerFullInv;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.machine.tileentity.TileEntityCropmatron;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public class ContainerCropmatron extends ContainerFullInv
{

	public final TileEntityCropmatron tileEntity;
	private int lastEnergy;

	public ContainerCropmatron(EntityPlayer entityPlayer, TileEntityCropmatron tileEntity)
	{
		super(entityPlayer, tileEntity, 166);
		lastEnergy = -1;
		this.tileEntity = tileEntity;
		for (int i = 0; i < tileEntity.fertilizerSlot.size(); i++)
			addSlotToContainer(new SlotInvSlot(tileEntity.fertilizerSlot, i, 62, 20 + i * 18));

		for (int i = 0; i < tileEntity.hydrationSlot.size(); i++)
			addSlotToContainer(new SlotInvSlot(tileEntity.hydrationSlot, i, 98, 20 + i * 18));

		for (int i = 0; i < tileEntity.weedExSlot.size(); i++)
			addSlotToContainer(new SlotInvSlot(tileEntity.weedExSlot, i, 134, 20 + i * 18));

	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0; i < super.crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)super.crafters.get(i);
			if (tileEntity.energy != lastEnergy)
			{
				icrafting.sendProgressBarUpdate(this, 1, tileEntity.energy & 0xffff);
				icrafting.sendProgressBarUpdate(this, 2, tileEntity.energy >>> 16);
			}
		}

		lastEnergy = tileEntity.energy;
	}

	public void updateProgressBar(int index, int value)
	{
		super.updateProgressBar(index, value);
		switch (index)
		{
		case 1: // '\001'
			tileEntity.energy = tileEntity.energy & 0xffff0000 | value;
			break;

		case 2: // '\002'
			tileEntity.energy = tileEntity.energy & 0xffff | value << 16;
			break;
		}
	}
}
