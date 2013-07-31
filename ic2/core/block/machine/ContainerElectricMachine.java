// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerElectricMachine.java

package ic2.core.block.machine;

import ic2.core.ContainerFullInv;
import ic2.core.block.invslot.InvSlotDischarge;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public abstract class ContainerElectricMachine extends ContainerFullInv
{

	public final TileEntityElectricMachine base;
	private int lastEnergy;
	private int lastTier;

	public ContainerElectricMachine(EntityPlayer entityPlayer, TileEntityElectricMachine base, int height, int dischargeX, int dischargeY)
	{
		super(entityPlayer, base, height);
		lastEnergy = -1;
		lastTier = -1;
		this.base = base;
		addSlotToContainer(new SlotInvSlot(base.dischargeSlot, 0, dischargeX, dischargeY));
	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0; i < super.crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)super.crafters.get(i);
			if (base.energy != lastEnergy)
			{
				icrafting.sendProgressBarUpdate(this, 0, base.energy & 0xffff);
				icrafting.sendProgressBarUpdate(this, 1, base.energy >>> 16);
			}
			if (base.dischargeSlot.tier != lastTier)
				icrafting.sendProgressBarUpdate(this, 2, base.dischargeSlot.tier);
		}

		lastEnergy = base.energy;
		lastTier = base.dischargeSlot.tier;
	}

	public void updateProgressBar(int index, int value)
	{
		super.updateProgressBar(index, value);
		switch (index)
		{
		case 0: // '\0'
			base.energy = base.energy & 0xffff0000 | value;
			break;

		case 1: // '\001'
			base.energy = base.energy & 0xffff | value << 16;
			break;

		case 2: // '\002'
			base.dischargeSlot.tier = value;
			break;
		}
	}
}
