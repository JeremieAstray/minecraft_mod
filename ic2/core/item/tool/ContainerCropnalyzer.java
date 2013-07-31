// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerCropnalyzer.java

package ic2.core.item.tool;

import ic2.core.*;
import ic2.core.item.ItemCropSeed;
import ic2.core.slot.SlotCustom;
import ic2.core.slot.SlotDischarge;
import ic2.core.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

// Referenced classes of package ic2.core.item.tool:
//			HandHeldCropnalyzer

public class ContainerCropnalyzer extends ContainerBase
{

	public HandHeldCropnalyzer cropnalyzer;

	public ContainerCropnalyzer(EntityPlayer entityPlayer, HandHeldCropnalyzer cropnalyzer)
	{
		super(cropnalyzer);
		this.cropnalyzer = cropnalyzer;
		addSlotToContainer(new SlotCustom(cropnalyzer, new Object[] {
			ic2/core/item/ItemCropSeed
		}, 0, 8, 7));
		addSlotToContainer(new SlotCustom(cropnalyzer, new Object[0], 1, 41, 7));
		addSlotToContainer(new SlotDischarge(cropnalyzer, 2, 152, 7));
		for (int j = 0; j < 9; j++)
			addSlotToContainer(new Slot(entityPlayer.inventory, j, 8 + j * 18, 142));

	}

	public ItemStack slotClick(int slot, int button, int par3, EntityPlayer entityPlayer)
	{
		if (IC2.platform.isSimulating() && slot == -999 && (button == 0 || button == 1))
		{
			ItemStack itemStackSlot = entityPlayer.inventory.getItemStack();
			if (itemStackSlot != null)
			{
				NBTTagCompound nbtTagCompoundSlot = StackUtil.getOrCreateNbtData(itemStackSlot);
				if (cropnalyzer.matchesUid(nbtTagCompoundSlot.getInteger("uid")))
					entityPlayer.closeScreen();
			}
		}
		return super.slotClick(slot, button, par3, entityPlayer);
	}

	public void onContainerClosed(EntityPlayer entityPlayer)
	{
		cropnalyzer.onGuiClosed(entityPlayer);
		super.onContainerClosed(entityPlayer);
	}
}
