// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SlotInvSlotReadOnly.java

package ic2.core.slot;

import ic2.core.block.invslot.InvSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.core.slot:
//			SlotInvSlot

public class SlotInvSlotReadOnly extends SlotInvSlot
{

	public SlotInvSlotReadOnly(InvSlot invSlot, int index, int xDisplayPosition, int yDisplayPosition)
	{
		super(invSlot, index, xDisplayPosition, yDisplayPosition);
	}

	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}

	public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack itemstack)
	{
	}

	public boolean canTakeStack(EntityPlayer player)
	{
		return false;
	}

	public ItemStack decrStackSize(int par1)
	{
		return null;
	}
}
