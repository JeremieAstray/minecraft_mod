// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Liquid.java

package ic2.core.util;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;

public class Liquid
{
	public static class LiquidData
	{

		public final Fluid liquid;
		public final boolean isSource;

		LiquidData(Fluid liquid, boolean isSource)
		{
			this.liquid = liquid;
			this.isSource = isSource;
		}
	}


	public Liquid()
	{
	}

	public static LiquidData getLiquid(World world, int x, int y, int z)
	{
		int blockId = world.getBlockId(x, y, z);
		Fluid liquid = null;
		boolean isSource = false;
		if (Block.blocksList[blockId] instanceof IFluidBlock)
		{
			IFluidBlock block = (IFluidBlock)Block.blocksList[blockId];
			liquid = block.getFluid();
			isSource = block.canDrain(world, x, y, z);
		} else
		if (blockId == Block.waterStill.blockID || blockId == ((Block) (Block.waterMoving)).blockID)
		{
			liquid = FluidRegistry.WATER;
			isSource = world.getBlockMetadata(x, y, z) == 0;
		} else
		if (blockId == Block.lavaStill.blockID || blockId == ((Block) (Block.lavaMoving)).blockID)
		{
			liquid = FluidRegistry.LAVA;
			isSource = world.getBlockMetadata(x, y, z) == 0;
		}
		if (liquid != null)
			return new LiquidData(liquid, isSource);
		else
			return null;
	}
}
