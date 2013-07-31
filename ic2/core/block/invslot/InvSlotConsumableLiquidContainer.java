// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InvSlotConsumableLiquidContainer.java

package ic2.core.block.invslot;

import ic2.core.block.TileEntityInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

// Referenced classes of package ic2.core.block.invslot:
//			InvSlotConsumable

public class InvSlotConsumableLiquidContainer extends InvSlotConsumable
{

	public InvSlotConsumableLiquidContainer(TileEntityInventory base, String name, int oldStartIndex, int count)
	{
		super(base, name, oldStartIndex, count);
	}

	public boolean accepts(ItemStack itemStack)
	{
		return FluidContainerRegistry.isEmptyContainer(itemStack);
	}

	public ItemStack fill(FluidStack liquid, boolean simulate)
	{
		ItemStack container = consume(1, true, true);
		if (container == null)
			return null;
		ItemStack filled = FluidContainerRegistry.fillFluidContainer(liquid, container);
		if (filled == null)
			return null;
		if (!simulate)
			consume(1, false, true);
		return filled;
	}
}
