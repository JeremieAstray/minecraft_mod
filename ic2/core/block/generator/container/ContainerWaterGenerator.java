// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerWaterGenerator.java

package ic2.core.block.generator.container;

import ic2.core.block.generator.tileentity.TileEntityWaterGenerator;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;

// Referenced classes of package ic2.core.block.generator.container:
//			ContainerBaseGenerator

public class ContainerWaterGenerator extends ContainerBaseGenerator
{

	public ContainerWaterGenerator(EntityPlayer entityPlayer, TileEntityWaterGenerator tileEntity)
	{
		super(entityPlayer, tileEntity, 166, 80, 17);
		addSlotToContainer(new SlotInvSlot(tileEntity.fuelSlot, 0, 80, 53));
	}
}
