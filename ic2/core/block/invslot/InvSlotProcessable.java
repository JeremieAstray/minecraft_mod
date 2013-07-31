// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InvSlotProcessable.java

package ic2.core.block.invslot;

import ic2.core.block.TileEntityInventory;
import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.core.block.invslot:
//			InvSlotConsumable

public abstract class InvSlotProcessable extends InvSlotConsumable
{

	public InvSlotProcessable(TileEntityInventory base, String name, int oldStartIndex, int count)
	{
		super(base, name, oldStartIndex, count);
	}

	public abstract boolean accepts(ItemStack itemstack);

	public abstract ItemStack process(boolean flag);
}
