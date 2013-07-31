// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityCableSplitter.java

package ic2.core.block.wiring;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.core.IC2;
import ic2.core.Platform;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;

// Referenced classes of package ic2.core.block.wiring:
//			TileEntityCable

public class TileEntityCableSplitter extends TileEntityCable
{

	public static final int tickRate = 20;
	public int ticksUntilNextUpdate;

	public TileEntityCableSplitter(short type)
	{
		super(type);
		ticksUntilNextUpdate = 0;
	}

	public TileEntityCableSplitter()
	{
		ticksUntilNextUpdate = 0;
	}

	public boolean enableUpdateEntity()
	{
		return IC2.platform.isSimulating();
	}

	public void updateEntity()
	{
		if (ticksUntilNextUpdate == 0)
		{
			ticksUntilNextUpdate = 20;
			if (super.worldObj.isBlockIndirectlyGettingPowered(super.xCoord, super.yCoord, super.zCoord) == addedToEnergyNet)
				if (addedToEnergyNet)
				{
					MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
					addedToEnergyNet = false;
				} else
				{
					MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
					addedToEnergyNet = true;
				}
			setActive(addedToEnergyNet);
		}
		ticksUntilNextUpdate--;
	}
}
