// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EnergyTileLoadEvent.java

package ic2.api.energy.event;

import ic2.api.energy.tile.IEnergyTile;

// Referenced classes of package ic2.api.energy.event:
//			EnergyTileEvent

public class EnergyTileLoadEvent extends EnergyTileEvent
{

	public EnergyTileLoadEvent(IEnergyTile energyTile)
	{
		super(energyTile);
	}
}
