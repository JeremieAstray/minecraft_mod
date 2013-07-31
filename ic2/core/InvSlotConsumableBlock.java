// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InvSlotConsumableBlock.java

package ic2.core;

import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class InvSlotConsumableBlock extends InvSlotConsumable
{

	public InvSlotConsumableBlock(TileEntityInventory base, String name, int oldStartIndex, int count)
	{
		this(base, name, oldStartIndex, ic2.core.block.invslot.InvSlot.Access.I, count, ic2.core.block.invslot.InvSlot.InvSide.TOP);
	}

	public InvSlotConsumableBlock(TileEntityInventory base, String name, int oldStartIndex, ic2.core.block.invslot.InvSlot.Access access, int count, ic2.core.block.invslot.InvSlot.InvSide preferredSide)
	{
		super(base, name, oldStartIndex, access, count, preferredSide);
	}

	public boolean accepts(ItemStack itemStack)
	{
		return itemStack.getItem() instanceof ItemBlock;
	}
}
