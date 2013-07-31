// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerTradeOMatOpen.java

package ic2.core.block.personal;

import ic2.core.ContainerFullInv;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

// Referenced classes of package ic2.core.block.personal:
//			TileEntityTradeOMat

public class ContainerTradeOMatOpen extends ContainerFullInv
{

	public final TileEntityTradeOMat tileEntity;
	private int lastStock;
	private int lastTotalTradeCount;

	public ContainerTradeOMatOpen(EntityPlayer entityPlayer, TileEntityTradeOMat tileEntity)
	{
		super(entityPlayer, tileEntity, 166);
		lastStock = -2;
		lastTotalTradeCount = -1;
		this.tileEntity = tileEntity;
		addSlotToContainer(new SlotInvSlot(tileEntity.demandSlot, 0, 50, 19));
		addSlotToContainer(new SlotInvSlot(tileEntity.offerSlot, 0, 50, 53));
		addSlotToContainer(new SlotInvSlot(tileEntity.inputSlot, 0, 80, 19));
		addSlotToContainer(new SlotInvSlot(tileEntity.outputSlot, 0, 80, 53));
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
			if (tileEntity.totalTradeCount != lastTotalTradeCount)
			{
				icrafting.sendProgressBarUpdate(this, 2, tileEntity.totalTradeCount & 0xffff);
				icrafting.sendProgressBarUpdate(this, 3, tileEntity.totalTradeCount >>> 16);
			}
		}

		lastStock = tileEntity.stock;
		lastTotalTradeCount = tileEntity.totalTradeCount;
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

		case 2: // '\002'
			tileEntity.totalTradeCount = tileEntity.totalTradeCount & 0xffff0000 | value;
			break;

		case 3: // '\003'
			tileEntity.totalTradeCount = tileEntity.totalTradeCount & 0xffff | value << 16;
			break;
		}
	}
}
