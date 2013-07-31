// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerElectricBlock.java

package ic2.core.block.wiring;

import ic2.core.ContainerFullInv;
import ic2.core.slot.SlotArmor;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

// Referenced classes of package ic2.core.block.wiring:
//			TileEntityElectricBlock

public class ContainerElectricBlock extends ContainerFullInv
{

	public final TileEntityElectricBlock tileEntity;
	private int lastEnergy;

	public ContainerElectricBlock(EntityPlayer entityPlayer, TileEntityElectricBlock tileEntity)
	{
		super(entityPlayer, tileEntity, 196);
		lastEnergy = -1;
		this.tileEntity = tileEntity;
		for (int col = 0; col < 4; col++)
			addSlotToContainer(new SlotArmor(entityPlayer.inventory, col, 8 + col * 18, 84));

		addSlotToContainer(new SlotInvSlot(tileEntity.chargeSlot, 0, 56, 17));
		addSlotToContainer(new SlotInvSlot(tileEntity.dischargeSlot, 0, 56, 53));
	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0; i < super.crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)super.crafters.get(i);
			if (tileEntity.energy != lastEnergy)
			{
				icrafting.sendProgressBarUpdate(this, 0, tileEntity.energy & 0xffff);
				icrafting.sendProgressBarUpdate(this, 1, tileEntity.energy >>> 16);
			}
		}

		lastEnergy = tileEntity.energy;
	}

	public void updateProgressBar(int index, int value)
	{
		super.updateProgressBar(index, value);
		switch (index)
		{
		case 0: // '\0'
			tileEntity.energy = tileEntity.energy & 0xffff0000 | value;
			break;

		case 1: // '\001'
			tileEntity.energy = tileEntity.energy & 0xffff | value << 16;
			break;
		}
	}
}
