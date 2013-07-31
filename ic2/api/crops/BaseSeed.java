// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BaseSeed.java

package ic2.api.crops;


public class BaseSeed
{

	public int id;
	public int size;
	public int statGrowth;
	public int statGain;
	public int statResistance;
	public int stackSize;

	public BaseSeed(int id, int size, int statGrowth, int statGain, int statResistance, int stackSize)
	{
		this.id = id;
		this.size = size;
		this.statGrowth = statGrowth;
		this.statGain = statGain;
		this.statResistance = statResistance;
		this.stackSize = stackSize;
	}
}
