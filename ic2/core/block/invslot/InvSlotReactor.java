// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InvSlotReactor.java

package ic2.core.block.invslot;

import ic2.core.block.TileEntityInventory;
import ic2.core.block.generator.tileentity.TileEntityNuclearReactor;
import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.core.block.invslot:
//			InvSlot

public class InvSlotReactor extends InvSlot
{

	public InvSlotReactor(TileEntityInventory base, String name, int oldStartIndex, int count)
	{
		super(base, name, oldStartIndex, InvSlot.Access.IO, count);
	}

	public boolean accepts(ItemStack itemStack)
	{
		return TileEntityNuclearReactor.isUsefulItem(itemStack);
	}
}
