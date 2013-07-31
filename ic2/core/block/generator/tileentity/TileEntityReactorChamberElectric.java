// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityReactorChamberElectric.java

package ic2.core.block.generator.tileentity;

import ic2.api.Direction;
import ic2.api.energy.event.*;
import ic2.api.energy.tile.IEnergySource;
import ic2.core.IC2;
import ic2.core.Platform;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;

// Referenced classes of package ic2.core.block.generator.tileentity:
//			TileEntityReactorChamber

public class TileEntityReactorChamberElectric extends TileEntityReactorChamber
	implements IEnergySource
{

	public boolean addedToEnergyNet;

	public TileEntityReactorChamberElectric()
	{
		addedToEnergyNet = false;
	}

	public void onLoaded()
	{
		if (IC2.platform.isSimulating() && !addedToEnergyNet)
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			addedToEnergyNet = true;
		}
		super.onLoaded();
	}

	public void onUnloaded()
	{
		if (IC2.platform.isSimulating() && addedToEnergyNet)
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			addedToEnergyNet = false;
		}
		super.onUnloaded();
	}

	public boolean isAddedToEnergyNet()
	{
		return addedToEnergyNet;
	}

	public boolean emitsEnergyTo(TileEntity receiver, Direction direction)
	{
		return true;
	}

	public int getMaxEnergyOutput()
	{
		return 240 * IC2.energyGeneratorNuclear;
	}

	public int sendEnergy(int send)
	{
		EnergyTileSourceEvent event = new EnergyTileSourceEvent(this, send);
		MinecraftForge.EVENT_BUS.post(event);
		return event.amount;
	}
}
