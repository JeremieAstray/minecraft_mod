// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerStandardMachine.java

package ic2.core.block.machine;

import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

// Referenced classes of package ic2.core.block.machine:
//			ContainerElectricMachine

public class ContainerStandardMachine extends ContainerElectricMachine
{

	public final TileEntityStandardMachine tileEntity;
	private float lastChargeLevel;
	private float lastProgress;

	public ContainerStandardMachine(EntityPlayer entityPlayer, TileEntityStandardMachine tileEntity)
	{
		super(entityPlayer, tileEntity, 166, 56, 53);
		lastChargeLevel = -1F;
		lastProgress = -1F;
		this.tileEntity = tileEntity;
		addSlotToContainer(new SlotInvSlot(tileEntity.inputSlot, 0, 56, 17));
		addSlotToContainer(new SlotInvSlot(tileEntity.outputSlot, 0, 116, 35));
		for (int i = 0; i < 4; i++)
			addSlotToContainer(new SlotInvSlot(tileEntity.upgradeSlot, i, 152, 8 + i * 18));

	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		float chargeLevel = tileEntity.getChargeLevel();
		float progress = tileEntity.getProgress();
		for (int i = 0; i < super.crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)super.crafters.get(i);
			if (lastChargeLevel != chargeLevel)
				icrafting.sendProgressBarUpdate(this, 3, (short)(int)(chargeLevel * 32767F));
			if (lastProgress != progress)
				icrafting.sendProgressBarUpdate(this, 4, (short)(int)(progress * 32767F));
		}

		lastChargeLevel = chargeLevel;
		lastProgress = progress;
	}

	public void updateProgressBar(int index, int value)
	{
		super.updateProgressBar(index, value);
		switch (index)
		{
		case 3: // '\003'
			tileEntity.setChargeLevel((float)value / 32767F);
			break;

		case 4: // '\004'
			tileEntity.setProgress((float)value / 32767F);
			break;
		}
	}
}
