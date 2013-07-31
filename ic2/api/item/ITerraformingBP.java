// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITerraformingBP.java

package ic2.api.item;

import net.minecraft.world.World;

public interface ITerraformingBP
{

	public abstract int getConsume();

	public abstract int getRange();

	public abstract boolean terraform(World world, int i, int j, int k);
}
