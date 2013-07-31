// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IEnergyConductor.java

package ic2.api.energy.tile;


// Referenced classes of package ic2.api.energy.tile:
//			IEnergyAcceptor, IEnergyEmitter

public interface IEnergyConductor
	extends IEnergyAcceptor, IEnergyEmitter
{

	public abstract double getConductionLoss();

	public abstract int getInsulationEnergyAbsorption();

	public abstract int getInsulationBreakdownEnergy();

	public abstract int getConductorBreakdownEnergy();

	public abstract void removeInsulation();

	public abstract void removeConductor();
}
