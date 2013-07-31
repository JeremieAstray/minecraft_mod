// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IEnergyStorage.java

package ic2.api.tile;

import ic2.api.Direction;

public interface IEnergyStorage
{

	public abstract int getStored();

	public abstract void setStored(int i);

	public abstract int addEnergy(int i);

	public abstract int getCapacity();

	public abstract int getOutput();

	public abstract boolean isTeleporterCompatible(Direction direction);
}
