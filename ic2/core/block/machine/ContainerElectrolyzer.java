// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerElectrolyzer.java

package ic2.core.block.machine;

import ic2.core.ContainerFullInv;
import ic2.core.block.machine.tileentity.TileEntityElectrolyzer;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public class ContainerElectrolyzer extends ContainerFullInv
{

	public final TileEntityElectrolyzer tileEntity;
	private short lastEnergy;

	public ContainerElectrolyzer(EntityPlayer entityPlayer, TileEntityElectrolyzer tileEntity)
	{
		super(entityPlayer, tileEntity, 166);
		lastEnergy = -1;
		this.tileEntity = tileEntity;
		addSlotToContainer(new SlotInvSlot(tileEntity.waterSlot, 0, 53, 35));
		addSlotToContainer(new SlotInvSlot(tileEntity.hydrogenSlot, 0, 112, 35));
	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0; i < super.crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)super.crafters.get(i);
			if (tileEntity.energy != lastEnergy)
				icrafting.sendProgressBarUpdate(this, 0, tileEntity.energy);
		}

		lastEnergy = tileEntity.energy;
	}

	public void updateProgressBar(int index, int value)
	{
		super.updateProgressBar(index, value);
		switch (index)
		{
		case 0: // '\0'
			tileEntity.energy = (short)value;
			break;
		}
	}
}
