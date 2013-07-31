// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerMiner.java

package ic2.core.block.machine;

import ic2.core.block.machine.tileentity.TileEntityMiner;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

// Referenced classes of package ic2.core.block.machine:
//			ContainerElectricMachine

public class ContainerMiner extends ContainerElectricMachine
{

	public final TileEntityMiner tileEntity;
	private short lastProgress;

	public ContainerMiner(EntityPlayer entityPlayer, TileEntityMiner tileEntity)
	{
		super(entityPlayer, tileEntity, 166, 81, 59);
		lastProgress = -1;
		this.tileEntity = tileEntity;
		addSlotToContainer(new SlotInvSlot(tileEntity.scannerSlot, 0, 117, 22));
		addSlotToContainer(new SlotInvSlot(tileEntity.pipeSlot, 0, 81, 22));
		addSlotToContainer(new SlotInvSlot(tileEntity.drillSlot, 0, 45, 22));
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

		lastProgress = (short)tileEntity.progress;
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
