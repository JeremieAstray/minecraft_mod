// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerPersonalChest.java

package ic2.core.block.personal;

import ic2.core.ContainerFullInv;
import ic2.core.block.invslot.InvSlot;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;

// Referenced classes of package ic2.core.block.personal:
//			TileEntityPersonalChest

public class ContainerPersonalChest extends ContainerFullInv
{

	private final TileEntityPersonalChest tileEntity;

	public ContainerPersonalChest(EntityPlayer entityPlayer, TileEntityPersonalChest tileEntity)
	{
		super(entityPlayer, tileEntity, 222);
		this.tileEntity = tileEntity;
		tileEntity.openChest();
		for (int y = 0; y < tileEntity.contentSlot.size() / 9; y++)
		{
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new SlotInvSlot(tileEntity.contentSlot, x + y * 9, 8 + x * 18, 18 + y * 18));

		}

	}

	public void onContainerClosed(EntityPlayer entityplayer)
	{
		tileEntity.closeChest();
	}
}
