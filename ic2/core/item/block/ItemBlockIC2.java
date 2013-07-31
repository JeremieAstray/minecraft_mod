// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemBlockIC2.java

package ic2.core.item.block;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemBlockIC2 extends ItemBlock
{

	public ItemBlockIC2(int id)
	{
		super(id);
	}

	public String getUnlocalizedName()
	{
		return super.getUnlocalizedName().substring(5);
	}

	public String getItemDisplayName(ItemStack itemStack)
	{
		return StatCollector.translateToLocal((new StringBuilder()).append("ic2.").append(getUnlocalizedName(itemStack)).toString());
	}
}
