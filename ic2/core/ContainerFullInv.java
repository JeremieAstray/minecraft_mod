// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerFullInv.java

package ic2.core;

import ic2.core.block.TileEntityInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

// Referenced classes of package ic2.core:
//			ContainerBase

public abstract class ContainerFullInv extends ContainerBase
{

	protected static final int windowBorder = 8;
	protected static final int slotSize = 16;
	protected static final int slotDistance = 2;
	protected static final int slotSeparator = 4;
	protected static final int hotbarYOffset = -24;
	protected static final int inventoryYOffset = -82;

	public ContainerFullInv(EntityPlayer entityPlayer, TileEntityInventory base, int height)
	{
		super(base);
		for (int row = 0; row < 3; row++)
		{
			for (int col = 0; col < 9; col++)
				addSlotToContainer(new Slot(entityPlayer.inventory, col + row * 9 + 9, 8 + col * 18, height + -82 + row * 18));

		}

		for (int col = 0; col < 9; col++)
			addSlotToContainer(new Slot(entityPlayer.inventory, col, 8 + col * 18, height + -24));

	}
}
