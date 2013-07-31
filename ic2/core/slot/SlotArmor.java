// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SlotArmor.java

package ic2.core.slot;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.*;
import net.minecraft.util.Icon;

public class SlotArmor extends Slot
{

	private final InventoryPlayer field_75224_c;
	private final int armorType;

	public SlotArmor(InventoryPlayer inventory, int armorType, int xDisplayPosition, int yDisplayPosition)
	{
		super(inventory, 36 + (3 - armorType), xDisplayPosition, yDisplayPosition);
		field_75224_c = inventory;
		this.armorType = armorType;
	}

	public boolean isItemValid(ItemStack itemStack)
	{
		return itemStack.getItem().isValidArmor(itemStack, armorType, field_75224_c.player);
	}

	public Icon getBackgroundIconIndex()
	{
		return ItemArmor.func_94602_b(armorType);
	}
}
