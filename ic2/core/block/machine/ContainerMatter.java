// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerMatter.java

package ic2.core.block.machine;

import ic2.core.ContainerFullInv;
import ic2.core.block.machine.tileentity.TileEntityMatter;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public class ContainerMatter extends ContainerFullInv
{

	public final TileEntityMatter tileEntity;
	private int lastEnergy;
	private int lastScrap;

	public ContainerMatter(EntityPlayer entityPlayer, TileEntityMatter tileEntity)
	{
		super(entityPlayer, tileEntity, 166);
		lastEnergy = -1;
		lastScrap = -1;
		this.tileEntity = tileEntity;
		addSlotToContainer(new SlotInvSlot(tileEntity.amplifierSlot, 0, 114, 54));
		addSlotToContainer(new SlotInvSlot(tileEntity.outputSlot, 0, 114, 18));
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
			if (tileEntity.scrap != lastScrap)
			{
				icrafting.sendProgressBarUpdate(this, 2, tileEntity.scrap & 0xffff);
				icrafting.sendProgressBarUpdate(this, 3, tileEntity.scrap >>> 16);
			}
		}

		lastEnergy = tileEntity.energy;
		lastScrap = tileEntity.scrap;
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

		case 2: // '\002'
			tileEntity.scrap = tileEntity.scrap & 0xffff0000 | value;
			break;

		case 3: // '\003'
			tileEntity.scrap = tileEntity.scrap & 0xffff | value << 16;
			break;
		}
	}
}
