// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerNuclearReactor.java

package ic2.core.block.generator.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.generator.tileentity.TileEntityNuclearReactor;
import ic2.core.block.invslot.InvSlotReactor;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public class ContainerNuclearReactor extends ContainerFullInv
{

	public TileEntityNuclearReactor tileEntity;
	public short output;
	public int heat;
	public short size;

	public ContainerNuclearReactor(EntityPlayer entityPlayer, TileEntityNuclearReactor tileEntity)
	{
		super(entityPlayer, tileEntity, 222);
		output = -1;
		heat = -1;
		this.tileEntity = tileEntity;
		size = tileEntity.getReactorSize();
		int startX = 89 - 9 * size;
		int startY = 18;
		int x = 0;
		int y = 0;
		for (int i = 0; i < tileEntity.reactorSlot.size(); i++)
		{
			if (x < size)
				addSlotToContainer(new SlotInvSlot(tileEntity.reactorSlot, i, startX + 18 * x, startY + 18 * y));
			if (++x >= 9)
			{
				y++;
				x = 0;
			}
		}

	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0; i < super.crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)super.crafters.get(i);
			if (output != tileEntity.getOutput())
				icrafting.sendProgressBarUpdate(this, 0, tileEntity.getOutput());
			if (heat != tileEntity.heat)
			{
				icrafting.sendProgressBarUpdate(this, 1, tileEntity.heat & 0xffff);
				icrafting.sendProgressBarUpdate(this, 2, tileEntity.heat >>> 16);
			}
		}

		output = (short)tileEntity.getOutput();
		heat = tileEntity.heat;
	}

	public void updateProgressBar(int index, int value)
	{
		super.updateProgressBar(index, value);
		switch (index)
		{
		case 0: // '\0'
			tileEntity.output = (short)value;
			break;

		case 1: // '\001'
			tileEntity.heat = tileEntity.heat & 0xffff0000 | value;
			break;

		case 2: // '\002'
			tileEntity.heat = tileEntity.heat & 0xffff | value << 16;
			break;
		}
	}
}
