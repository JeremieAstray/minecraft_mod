// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IRareBlock.java

package ic2.core.block;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public interface IRareBlock
{

	public abstract EnumRarity getRarity(ItemStack itemstack);
}