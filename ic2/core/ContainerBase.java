// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerBase.java

package ic2.core;

import ic2.core.block.invslot.InvSlot;
import ic2.core.slot.SlotInvSlot;
import java.util.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.core:
//			IC2, Platform

public abstract class ContainerBase extends Container
{

	public final IInventory base;

	public ContainerBase(IInventory base)
	{
		this.base = base;
	}

	public final ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotIndex)
	{
		Slot sourceSlot = (Slot)super.inventorySlots.get(sourceSlotIndex);
		if (sourceSlot != null && sourceSlot.getHasStack())
		{
			ItemStack sourceItemStack = sourceSlot.getStack();
			int oldSourceItemStackSize = sourceItemStack.stackSize;
			if (sourceSlot.inventory == player.inventory)
			{
				for (int run = 0; run < 4 && sourceItemStack.stackSize > 0; run++)
					if (run < 2)
					{
						Iterator i$ = super.inventorySlots.iterator();
label0:
						do
						{
							Slot targetSlot;
							do
							{
								if (!i$.hasNext())
									break label0;
								targetSlot = (Slot)i$.next();
							} while (!(targetSlot instanceof SlotInvSlot) || !((SlotInvSlot)targetSlot).invSlot.canInput() || !targetSlot.isItemValid(sourceItemStack) || targetSlot.getStack() == null && run != 1);
							mergeItemStack(sourceItemStack, targetSlot.slotNumber, targetSlot.slotNumber + 1, false);
						} while (sourceItemStack.stackSize != 0);
					} else
					{
						Iterator i$ = super.inventorySlots.iterator();
label1:
						do
						{
							Slot targetSlot;
							do
							{
								if (!i$.hasNext())
									break label1;
								targetSlot = (Slot)i$.next();
							} while (targetSlot.inventory == player.inventory || !targetSlot.isItemValid(sourceItemStack) || targetSlot.getStack() == null && run != 3);
							mergeItemStack(sourceItemStack, targetSlot.slotNumber, targetSlot.slotNumber + 1, false);
						} while (sourceItemStack.stackSize != 0);
					}

			} else
			{
label2:
				for (int run = 0; run < 2 && sourceItemStack.stackSize > 0; run++)
				{
					ListIterator it = super.inventorySlots.listIterator(super.inventorySlots.size());
					do
					{
						Slot targetSlot;
						do
						{
							if (!it.hasPrevious())
								continue label2;
							targetSlot = (Slot)it.previous();
						} while (targetSlot.inventory != player.inventory || !targetSlot.isItemValid(sourceItemStack) || targetSlot.getStack() == null && run != 1);
						mergeItemStack(sourceItemStack, targetSlot.slotNumber, targetSlot.slotNumber + 1, false);
					} while (sourceItemStack.stackSize != 0);
				}

			}
			if (sourceItemStack.stackSize != oldSourceItemStackSize)
			{
				if (sourceItemStack.stackSize == 0)
					sourceSlot.putStack(null);
				else
					sourceSlot.onPickupFromSlot(player, sourceItemStack);
				if (IC2.platform.isSimulating())
					detectAndSendChanges();
			}
		}
		return null;
	}

	public void updateProgressBar(int i, int j)
	{
	}

	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return base.isUseableByPlayer(entityplayer);
	}
}
