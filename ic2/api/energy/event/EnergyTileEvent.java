// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EnergyTileEvent.java

package ic2.api.energy.event;

import ic2.api.energy.tile.IEnergyTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.world.WorldEvent;

public class EnergyTileEvent extends WorldEvent
{

	public final IEnergyTile energyTile;

	public EnergyTileEvent(IEnergyTile energyTile)
	{
		super(((TileEntity)energyTile).worldObj);
		this.energyTile = energyTile;
	}
}
