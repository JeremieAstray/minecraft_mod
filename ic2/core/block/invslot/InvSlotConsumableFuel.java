// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InvSlotConsumableFuel.java

package ic2.core.block.invslot;

import ic2.api.info.IFuelValueProvider;
import ic2.api.info.Info;
import ic2.core.block.TileEntityInventory;
import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.core.block.invslot:
//			InvSlotConsumable, InvSlot

public class InvSlotConsumableFuel extends InvSlotConsumable
{

	public final boolean allowLava;

	public InvSlotConsumableFuel(TileEntityInventory base, String name, int oldStartIndex, int count, boolean allowLava)
	{
		super(base, name, oldStartIndex, InvSlot.Access.I, count, InvSlot.InvSide.SIDE);
		this.allowLava = allowLava;
	}

	public boolean accepts(ItemStack itemStack)
	{
		return Info.itemFuel.getFuelValue(itemStack, allowLava) > 0;
	}

	public int consumeFuel()
	{
		ItemStack fuel = consume(1);
		if (fuel == null)
			return 0;
		else
			return Info.itemFuel.getFuelValue(fuel, allowLava);
	}
}
