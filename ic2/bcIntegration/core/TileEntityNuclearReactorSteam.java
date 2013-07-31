// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityNuclearReactorSteam.java

package ic2.bcIntegration.core;

import ic2.api.Direction;
import ic2.core.IC2;
import ic2.core.block.generator.tileentity.TileEntityNuclearReactor;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.*;

public class TileEntityNuclearReactorSteam extends TileEntityNuclearReactor
	implements IFluidTank
{

	private static final double STEAM_PER_EU = 3.2000000000000002D;
	private static final Direction directions[] = Direction.values();

	public TileEntityNuclearReactorSteam()
	{
	}

	public int sendEnergy(int energy)
	{
		return outputSteam(this, energy);
	}

	protected static int outputSteam(TileEntity tile, int energy)
	{
		int steam = (int)Math.floor((double)(energy * IC2.energyGeneratorNuclear) * 3.2000000000000002D);
		Direction arr$[] = directions;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Direction value = arr$[i$];
			TileEntity te = value.applyToTileEntity(tile);
			if (!(te instanceof IFluidTank))
				continue;
			steam -= ((IFluidTank)te).fill(new FluidStack(FluidRegistry.getFluid("Steam"), 1000), true);
			if (steam <= 0)
				break;
		}

		return (int)Math.floor((double)steam / 3.2000000000000002D / (double)IC2.energyGeneratorNuclear);
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
