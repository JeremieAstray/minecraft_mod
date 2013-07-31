// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InvSlotProcessableGeneric.java

package ic2.core.block.invslot;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.core.block.TileEntityInventory;
import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.core.block.invslot:
//			InvSlotProcessable

public class InvSlotProcessableGeneric extends InvSlotProcessable
{

	public final IMachineRecipeManager recipeManager;

	public InvSlotProcessableGeneric(TileEntityInventory base, String name, int oldStartIndex, int count, IMachineRecipeManager recipeManager)
	{
		super(base, name, oldStartIndex, count);
		this.recipeManager = recipeManager;
	}

	public boolean accepts(ItemStack itemStack)
	{
		ItemStack tmp = itemStack.copy();
		tmp.stackSize = 0x7fffffff;
		return recipeManager.getOutputFor(tmp, false) != null;
	}

	public ItemStack process(boolean simulate)
	{
		Object ret = processRaw(simulate);
		if (ret instanceof ItemStack)
			return (ItemStack)ret;
		else
			return null;
	}

	public Object processRaw(boolean simulate)
	{
		ItemStack input = get();
		if (input == null)
			return null;
		Object output = recipeManager.getOutputFor(input, !simulate);
		if (output instanceof ItemStack)
			output = ((ItemStack)output).copy();
		if (input.stackSize <= 0)
			put(null);
		return output;
	}
}
