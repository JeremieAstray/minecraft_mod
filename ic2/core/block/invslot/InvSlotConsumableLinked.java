// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InvSlotConsumableLinked.java

package ic2.core.block.invslot;

import ic2.core.block.TileEntityInventory;
import ic2.core.util.StackUtil;
import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.core.block.invslot:
//			InvSlotConsumable, InvSlot

public class InvSlotConsumableLinked extends InvSlotConsumable
{

	public final InvSlot linkedSlot;

	public InvSlotConsumableLinked(TileEntityInventory base, String name, int oldStartIndex, int count, InvSlot linkedSlot)
	{
		super(base, name, oldStartIndex, count);
		this.linkedSlot = linkedSlot;
	}

	public boolean accepts(ItemStack itemStack)
	{
		ItemStack required = linkedSlot.get();
		if (required == null)
			return false;
		else
			return StackUtil.isStackEqual(required, itemStack);
	}

	public ItemStack consumeLinked(boolean simulate)
	{
		ItemStack required = linkedSlot.get();
		if (required == null || required.stackSize <= 0)
			return null;
		ItemStack available = consume(required.stackSize, true, true);
		if (available != null && available.stackSize == required.stackSize)
			return consume(required.stackSize, simulate, true);
		else
			return null;
	}
}
