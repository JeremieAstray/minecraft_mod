// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerEnergyOMatOpen.java

package ic2.core.block.personal;

import ic2.core.ContainerFullInv;
import ic2.core.block.invslot.InvSlotCharge;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

// Referenced classes of package ic2.core.block.personal:
//			TileEntityEnergyOMat

public class ContainerEnergyOMatOpen extends ContainerFullInv
{

	public final TileEntityEnergyOMat tileEntity;
	private int lastPaidFor;
	private int lastEuBuffer;
	private int lastEuOffer;
	private int lastTier;

	public ContainerEnergyOMatOpen(EntityPlayer entityPlayer, TileEntityEnergyOMat tileEntity)
	{
		super(entityPlayer, tileEntity, 166);
		lastPaidFor = -1;
		lastEuBuffer = -1;
		lastEuOffer = -1;
		lastTier = -1;
		this.tileEntity = tileEntity;
		addSlotToContainer(new SlotInvSlot(tileEntity.demandSlot, 0, 24, 17));
		addSlotToContainer(new SlotInvSlot(tileEntity.upgradeSlot, 0, 24, 53));
		addSlotToContainer(new SlotInvSlot(tileEntity.inputSlot, 0, 60, 17));
		addSlotToContainer(new SlotInvSlot(tileEntity.chargeSlot, 0, 60, 53));
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
			if (tileEntity.euBuffer != lastEuBuffer)
			{
				icrafting.sendProgressBarUpdate(this, 2, tileEntity.euBuffer & 0xffff);
				icrafting.sendProgressBarUpdate(this, 3, tileEntity.euBuffer >>> 16);
			}
			if (tileEntity.euOffer != lastEuOffer)
			{
				icrafting.sendProgressBarUpdate(this, 4, tileEntity.euOffer & 0xffff);
				icrafting.sendProgressBarUpdate(this, 5, tileEntity.euOffer >>> 16);
			}
			if (tileEntity.chargeSlot.tier != lastTier)
				icrafting.sendProgressBarUpdate(this, 6, tileEntity.chargeSlot.tier);
		}

		lastPaidFor = tileEntity.paidFor;
		lastEuBuffer = tileEntity.euBuffer;
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
			tileEntity.euBuffer = tileEntity.euBuffer & 0xffff0000 | value;
			break;

		case 3: // '\003'
			tileEntity.euBuffer = tileEntity.euBuffer & 0xffff | value << 16;
			break;

		case 4: // '\004'
			tileEntity.euOffer = tileEntity.euOffer & 0xffff0000 | value;
			break;

		case 5: // '\005'
			tileEntity.euOffer = tileEntity.euOffer & 0xffff | value << 16;
			break;

		case 6: // '\006'
			tileEntity.chargeSlot.tier = value;
			break;
		}
	}
}
