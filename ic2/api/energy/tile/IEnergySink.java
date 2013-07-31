// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IEnergySink.java

package ic2.api.energy.tile;

import ic2.api.Direction;

// Referenced classes of package ic2.api.energy.tile:
//			IEnergyAcceptor

public interface IEnergySink
	extends IEnergyAcceptor
{

	public abstract int demandsEnergy();

	public abstract int injectEnergy(Direction direction, int i);

	public abstract int getMaxSafeInput();
}
