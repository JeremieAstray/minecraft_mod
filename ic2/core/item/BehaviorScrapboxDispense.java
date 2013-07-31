// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BehaviorScrapboxDispense.java

package ic2.core.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

// Referenced classes of package ic2.core.item:
//			ItemScrapbox

public class BehaviorScrapboxDispense extends BehaviorDefaultDispenseItem
{

	public BehaviorScrapboxDispense()
	{
	}

	protected ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
	{
		EnumFacing var3 = EnumFacing.getFront(par1IBlockSource.getBlockMetadata());
		net.minecraft.dispenser.IPosition var4 = BlockDispenser.getIPositionFromBlockSource(par1IBlockSource);
		par2ItemStack.splitStack(1);
		doDispense(par1IBlockSource.getWorld(), ItemScrapbox.getDrop(par1IBlockSource.getWorld()), 6, var3, var4);
		return par2ItemStack;
	}
}
