// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerTradeOMatClosed.java

package ic2.core.block.personal;

import ic2.core.ContainerFullInv;
import ic2.core.slot.SlotInvSlot;
import ic2.core.slot.SlotInvSlotReadOnly;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

// Referenced classes of package ic2.core.block.personal:
//			TileEntityTradeOMat

public class ContainerTradeOMatClosed extends ContainerFullInv
{

	public final TileEntityTradeOMat tileEntity;
	private int lastStock;

	public ContainerTradeOMatClosed(EntityPlayer entityPlayer, TileEntityTradeOMat tileEntity)
	{
		super(entityPlayer, tileEntity, 166);
		lastStock = -2;
		this.tileEntity = tileEntity;
		addSlotToContainer(new SlotInvSlotReadOnly(tileEntity.demandSlot, 0, 50, 19));
		addSlotToContainer(new SlotInvSlotReadOnly(tileEntity.offerSlot, 0, 50, 38));
		addSlotToContainer(new SlotInvSlot(tileEntity.inputSlot, 0, 143, 19));
		addSlotToContainer(new SlotInvSlot(tileEntity.outputSlot, 0, 143, 53));
	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0; i < super.crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)super.crafters.get(i);
			if (tileEntity.stock != lastStock)
			{
				icrafting.sendProgressBarUpdate(this, 0, tileEntity.stock & 0xffff);
				icrafting.sendProgressBarUpdate(this, 1, tileEntity.stock >>> 16);
			}
		}

		lastStock = tileEntity.stock;
	}

	public void updateProgressBar(int index, int value)
	{
		super.updateProgressBar(index, value);
		switch (index)
		{
		case 0: // '\0'
			tileEntity.stock = tileEntity.stock & 0xffff0000 | value;
			break;

		case 1: // '\001'
			tileEntity.stock = tileEntity.stock & 0xffff | value << 16;
			break;
		}
	}
}
