// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EnergyNet.java

package ic2.api.energy;

import ic2.api.energy.tile.IEnergySource;
import java.lang.reflect.Method;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public final class EnergyNet
{

	Object energyNetInstance;
	private static Method EnergyNet_getForWorld;
	private static Method EnergyNet_addTileEntity;
	private static Method EnergyNet_removeTileEntity;
	private static Method EnergyNet_emitEnergyFrom;
	private static Method EnergyNet_getTotalEnergyConducted;
	private static Method EnergyNet_getTotalEnergyEmitted;
	private static Method EnergyNet_getTotalEnergySunken;

	public static EnergyNet getForWorld(World world)
	{
		if (EnergyNet_getForWorld == null)
			EnergyNet_getForWorld = Class.forName((new StringBuilder()).append(getPackage()).append(".core.EnergyNet").toString()).getMethod("getForWorld", new Class[] {
				net/minecraft/world/World
			});
		return new EnergyNet(EnergyNet_getForWorld.invoke(null, new Object[] {
			world
		}));
		Exception e;
		e;
		throw new RuntimeException(e);
	}

	private EnergyNet(Object energyNetInstance)
	{
		this.energyNetInstance = energyNetInstance;
	}

	/**
	 * @deprecated Method addTileEntity is deprecated
	 */

	public void addTileEntity(TileEntity addedTileEntity)
	{
		try
		{
			if (EnergyNet_addTileEntity == null)
				EnergyNet_addTileEntity = Class.forName((new StringBuilder()).append(getPackage()).append(".core.EnergyNet").toString()).getMethod("addTileEntity", new Class[] {
					net/minecraft/tileentity/TileEntity
				});
			EnergyNet_addTileEntity.invoke(energyNetInstance, new Object[] {
				addedTileEntity
			});
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * @deprecated Method removeTileEntity is deprecated
	 */

	public void removeTileEntity(TileEntity removedTileEntity)
	{
		try
		{
			if (EnergyNet_removeTileEntity == null)
				EnergyNet_removeTileEntity = Class.forName((new StringBuilder()).append(getPackage()).append(".core.EnergyNet").toString()).getMethod("removeTileEntity", new Class[] {
					net/minecraft/tileentity/TileEntity
				});
			EnergyNet_removeTileEntity.invoke(energyNetInstance, new Object[] {
				removedTileEntity
			});
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * @deprecated Method emitEnergyFrom is deprecated
	 */

	public int emitEnergyFrom(IEnergySource energySource, int amount)
	{
		if (EnergyNet_emitEnergyFrom == null)
			EnergyNet_emitEnergyFrom = Class.forName((new StringBuilder()).append(getPackage()).append(".core.EnergyNet").toString()).getMethod("emitEnergyFrom", new Class[] {
				ic2/api/energy/tile/IEnergySource, Integer.TYPE
			});
		return ((Integer)EnergyNet_emitEnergyFrom.invoke(energyNetInstance, new Object[] {
			energySource, Integer.valueOf(amount)
		})).intValue();
		Exception e;
		e;
		throw new RuntimeException(e);
	}

	/**
	 * @deprecated Method getTotalEnergyConducted is deprecated
	 */

	public long getTotalEnergyConducted(TileEntity tileEntity)
	{
		if (EnergyNet_getTotalEnergyConducted == null)
			EnergyNet_getTotalEnergyConducted = Class.forName((new StringBuilder()).append(getPackage()).append(".core.EnergyNet").toString()).getMethod("getTotalEnergyConducted", new Class[] {
				net/minecraft/tileentity/TileEntity
			});
		return ((Long)EnergyNet_getTotalEnergyConducted.invoke(energyNetInstance, new Object[] {
			tileEntity
		})).longValue();
		Exception e;
		e;
		throw new RuntimeException(e);
	}

	public long getTotalEnergyEmitted(TileEntity tileEntity)
	{
		if (EnergyNet_getTotalEnergyEmitted == null)
			EnergyNet_getTotalEnergyEmitted = Class.forName((new StringBuilder()).append(getPackage()).append(".core.EnergyNet").toString()).getMethod("getTotalEnergyEmitted", new Class[] {
				net/minecraft/tileentity/TileEntity
			});
		return ((Long)EnergyNet_getTotalEnergyEmitted.invoke(energyNetInstance, new Object[] {
			tileEntity
		})).longValue();
		Exception e;
		e;
		throw new RuntimeException(e);
	}

	public long getTotalEnergySunken(TileEntity tileEntity)
	{
		if (EnergyNet_getTotalEnergySunken == null)
			EnergyNet_getTotalEnergySunken = Class.forName((new StringBuilder()).append(getPackage()).append(".core.EnergyNet").toString()).getMethod("getTotalEnergySunken", new Class[] {
				net/minecraft/tileentity/TileEntity
			});
		return ((Long)EnergyNet_getTotalEnergySunken.invoke(energyNetInstance, new Object[] {
			tileEntity
		})).longValue();
		Exception e;
		e;
		throw new RuntimeException(e);
	}

	private static String getPackage()
	{
		Package pkg = ic2/api/energy/EnergyNet.getPackage();
		if (pkg != null)
		{
			String packageName = pkg.getName();
			return packageName.substring(0, packageName.length() - ".api.energy".length());
		} else
		{
			return "ic2";
		}
	}
}
