// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityReactorChamberSteam.java

package ic2.bcIntegration.core;

import ic2.core.block.generator.tileentity.TileEntityReactorChamber;
import net.minecraftforge.fluids.*;

// Referenced classes of package ic2.bcIntegration.core:
//			TileEntityNuclearReactorSteam

public class TileEntityReactorChamberSteam extends TileEntityReactorChamber
	implements IFluidTank
{

	public TileEntityReactorChamberSteam()
	{
	}

	public int sendEnergy(int energy)
	{
		return TileEntityNuclearReactorSteam.outputSteam(this, energy);
	}

	public FluidStack getFluid()
	{
		return null;
	}

	public int getFluidAmount()
	{
		return 0;
	}

	public int getCapacity()
	{
		return 0;
	}

	public FluidTankInfo getInfo()
	{
		return new FluidTankInfo(this);
	}

	public int fill(FluidStack resource, boolean doFill)
	{
		return 0;
	}

	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		return null;
	}
}
