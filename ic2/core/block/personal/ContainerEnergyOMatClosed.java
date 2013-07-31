// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerEnergyOMatClosed.java

package ic2.core.block.personal;

import ic2.core.ContainerFullInv;
import ic2.core.block.invslot.InvSlotCharge;
import ic2.core.slot.SlotInvSlot;
import ic2.core.slot.SlotInvSlotReadOnly;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

// Referenced classes of package ic2.core.block.personal:
//			TileEntityEnergyOMat

public class ContainerEnergyOMatClosed extends ContainerFullInv
{

	public final TileEntityEnergyOMat tileEntity;
	private int lastPaidFor;
	private int lastEuOffer;
	private int lastTier;

	public ContainerEnergyOMatClosed(EntityPlayer entityPlayer, TileEntityEnergyOMat tileEntity)
	{
		super(entityPlayer, tileEntity, 166);
		lastPaidFor = -1;
		lastEuOffer = -1;
		lastTier = -1;
		this.tileEntity = tileEntity;
		addSlotToContainer(new SlotInvSlotReadOnly(tileEntity.demandSlot, 0, 50, 17));
		addSlotToContainer(new SlotInvSlot(tileEntity.inputSlot, 0, 143, 17));
		addSlotToContainer(new SlotInvSlot(tileEntity.chargeSlot, 0, 143, 53));
	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0; i < super.crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)super.crafters.get(i);
			if (tileEntity.paidFor != lastPaidFor)
			{
				icrafting.sendProgressBarUpdate(this, 0, tileEntity.paidFor & 0xffff);
				icrafting.sendProgressBarUpdate(this, 1, tileEntity.paidFor >>> 16);
			}
			if (tileEntity.euOffer != lastEuOffer)
			{
				icrafting.sendProgressBarUpdate(this, 2, tileEntity.euOffer & 0xffff);
				icrafting.sendProgressBarUpdate(this, 3, tileEntity.euOffer >>> 16);
			}
			if (tileEntity.chargeSlot.tier != lastTier)
				icrafting.sendProgressBarUpdate(this, 4, tileEntity.chargeSlot.tier);
		}

		lastPaidFor = tileEntity.paidFor;
		lastEuOffer = tileEntity.euOffer;
		lastTier = tileEntity.chargeSlot.tier;
	}

	public void updateProgressBar(int index, int value)
	{
		super.updateProgressBar(index, value);
		switch (index)
		{
		case 0: // '\0'
			tileEntity.paidFor = tileEntity.paidFor & 0xffff0000 | value;
			break;

		case 1: // '\001'
			tileEntity.paidFor = tileEntity.paidFor & 0xffff | value << 16;
			break;

		case 2: // '\002'
			tileEntity.euOffer = tileEntity.euOffer & 0xffff0000 | value;
			break;

		case 3: // '\003'
			tileEntity.euOffer = tileEntity.euOffer & 0xffff | value << 16;
			break;

		case 4: // '\004'
			tileEntity.chargeSlot.tier = value;
			break;
		}
	}
}
