// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerCanner.java

package ic2.core.block.machine;

import ic2.core.block.machine.tileentity.TileEntityCanner;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

// Referenced classes of package ic2.core.block.machine:
//			ContainerElectricMachine

public class ContainerCanner extends ContainerElectricMachine
{

	public final TileEntityCanner tileEntity;
	private short lastProgress;

	public ContainerCanner(EntityPlayer entityPlayer, TileEntityCanner tileEntity)
	{
		super(entityPlayer, tileEntity, 166, 30, 45);
		lastProgress = -1;
		this.tileEntity = tileEntity;
		addSlotToContainer(new SlotInvSlot(tileEntity.resInputSlot, 0, 69, 17));
		addSlotToContainer(new SlotInvSlot(tileEntity.outputSlot, 0, 119, 35));
		addSlotToContainer(new SlotInvSlot(tileEntity.inputSlot, 0, 69, 53));
	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0; i < super.crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)super.crafters.get(i);
			if (tileEntity.progress != lastProgress)
				icrafting.sendProgressBarUpdate(this, 3, tileEntity.progress);
		}

		lastProgress = tileEntity.progress;
	}

	public void updateProgressBar(int index, int value)
	{
		super.updateProgressBar(index, value);
		switch (index)
		{
		case 3: // '\003'
			tileEntity.progress = (short)value;
			break;
		}
	}
}
