// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemBlockRare.java

package ic2.core.item.block;

import ic2.core.block.IRareBlock;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.core.item.block:
//			ItemBlockIC2

public class ItemBlockRare extends ItemBlockIC2
{

	public ItemBlockRare(int id)
	{
		super(id);
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		if (Block.blocksList[stack.itemID] instanceof IRareBlock)
			return ((IRareBlock)Block.blocksList[stack.itemID]).getRarity(stack);
		else
			return super.getRarity(stack);
	}
}
