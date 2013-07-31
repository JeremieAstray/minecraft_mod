// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITickCallback.java

package ic2.core;

import net.minecraft.world.World;

public interface ITickCallback
{

	public abstract void tickCallback(World world);
}
