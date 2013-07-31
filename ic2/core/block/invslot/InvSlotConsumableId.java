// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InvSlotConsumableId.java

package ic2.core.block.invslot;

import ic2.core.block.TileEntityInventory;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.core.block.invslot:
//			InvSlotConsumable, InvSlot

public class InvSlotConsumableId extends InvSlotConsumable
{

	private final Set itemIds;

	public transient InvSlotConsumableId(TileEntityInventory base, String name, int oldStartIndex, int count, int itemIds[])
	{
		this(base, name, oldStartIndex, InvSlot.Access.I, count, InvSlot.InvSide.TOP, itemIds);
	}

	public transient InvSlotConsumableId(TileEntityInventory base, String name, int oldStartIndex, InvSlot.Access access, int count, InvSlot.InvSide preferredSide, int itemIds[])
	{
		super(base, name, oldStartIndex, access, count, preferredSide);
		this.itemIds = new HashSet();
		int arr$[] = itemIds;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			int itemId = arr$[i$];
			this.itemIds.add(Integer.valueOf(itemId));
		}

	}

	public boolean accepts(ItemStack itemStack)
	{
		return itemIds.contains(Integer.valueOf(itemStack.itemID));
	}
}
