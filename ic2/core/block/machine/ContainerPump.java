// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerPump.java

package ic2.core.block.machine;

import ic2.core.block.machine.tileentity.TileEntityPump;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

// Referenced classes of package ic2.core.block.machine:
//			ContainerElectricMachine

public class ContainerPump extends ContainerElectricMachine
{

	public final TileEntityPump tileEntity;
	private short lastPumpCharge;

	public ContainerPump(EntityPlayer entityPlayer, TileEntityPump tileEntity)
	{
		super(entityPlayer, tileEntity, 166, 62, 53);
		lastPumpCharge = -1;
		this.tileEntity = tileEntity;
		addSlotToContainer(new SlotInvSlot(tileEntity.containerSlot, 0, 62, 17));
	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0; i < super.crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)super.crafters.get(i);
			if (tileEntity.pumpCharge != lastPumpCharge)
				icrafting.sendProgressBarUpdate(this, 3, tileEntity.pumpCharge);
		}

		lastPumpCharge = tileEntity.pumpCharge;
	}

	public void updateProgressBar(int index, int value)
	{
		super.updateProgressBar(index, value);
		switch (index)
		{
		case 3: // '\003'
			tileEntity.pumpCharge = (short)value;
			break;
		}
	}
}
