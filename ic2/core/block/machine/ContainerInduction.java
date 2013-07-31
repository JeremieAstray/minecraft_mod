// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerInduction.java

package ic2.core.block.machine;

import ic2.core.block.machine.tileentity.TileEntityInduction;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

// Referenced classes of package ic2.core.block.machine:
//			ContainerElectricMachine

public class ContainerInduction extends ContainerElectricMachine
{

	public final TileEntityInduction tileEntity;
	private short lastProgress;
	private short lastHeat;

	public ContainerInduction(EntityPlayer entityPlayer, TileEntityInduction tileEntity)
	{
		super(entityPlayer, tileEntity, 166, 56, 53);
		lastProgress = -1;
		lastHeat = -1;
		this.tileEntity = tileEntity;
		addSlotToContainer(new SlotInvSlot(tileEntity.inputSlotA, 0, 47, 17));
		addSlotToContainer(new SlotInvSlot(tileEntity.inputSlotB, 0, 63, 17));
		addSlotToContainer(new SlotInvSlot(tileEntity.outputSlotA, 0, 113, 35));
		addSlotToContainer(new SlotInvSlot(tileEntity.outputSlotB, 0, 131, 35));
	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0; i < super.crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)super.crafters.get(i);
			if (tileEntity.progress != lastProgress)
				icrafting.sendProgressBarUpdate(this, 3, tileEntity.progress);
			if (tileEntity.heat != lastHeat)
				icrafting.sendProgressBarUpdate(this, 4, tileEntity.heat);
		}

		lastProgress = tileEntity.progress;
		lastHeat = tileEntity.heat;
	}

	public void updateProgressBar(int index, int value)
	{
		super.updateProgressBar(index, value);
		switch (index)
		{
		case 3: // '\003'
			tileEntity.progress = (short)value;
			break;

		case 4: // '\004'
			tileEntity.heat = (short)value;
			break;
		}
	}
}
