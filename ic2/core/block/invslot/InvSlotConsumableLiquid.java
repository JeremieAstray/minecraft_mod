// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InvSlotConsumableLiquid.java

package ic2.core.block.invslot;

import ic2.core.block.TileEntityInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.*;

// Referenced classes of package ic2.core.block.invslot:
//			InvSlotConsumable, InvSlot

public class InvSlotConsumableLiquid extends InvSlotConsumable
{

	public final Fluid fluid;

	public InvSlotConsumableLiquid(TileEntityInventory base, String name, int oldStartIndex, int count, Fluid fluid)
	{
		this(base, name, oldStartIndex, InvSlot.Access.I, count, InvSlot.InvSide.TOP, fluid);
	}

	public InvSlotConsumableLiquid(TileEntityInventory base, String name, int oldStartIndex, InvSlot.Access access, int count, InvSlot.InvSide preferredSide, Fluid fluid)
	{
		super(base, name, oldStartIndex, access, count, preferredSide);
		this.fluid = fluid;
	}

	public boolean accepts(ItemStack itemStack)
	{
		FluidStack offeredLiquid = FluidContainerRegistry.getFluidForFilledItem(itemStack);
		return offeredLiquid != null && offeredLiquid.getFluid() == fluid;
	}
}
