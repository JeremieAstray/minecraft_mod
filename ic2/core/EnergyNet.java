// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EnergyNet.java

package ic2.core;

import ic2.api.Direction;
import ic2.api.energy.event.*;
import ic2.api.energy.tile.*;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;

// Referenced classes of package ic2.core:
//			WorldData, IC2, Platform, IC2DamageSource, 
//			ITickCallback

public final class EnergyNet
{
	private static class PostPonedAddCallback
		implements ITickCallback
	{

		private final TileEntity te;

		public void tickCallback(World world)
		{
			if (world.blockExists(te.xCoord, te.yCoord, te.zCoord) && !te.isInvalid())
				EnergyNet.getForWorld(world).addTileEntity(te);
			else
				IC2.log.info((new StringBuilder()).append("EnergyNet.addTileEntity: ").append(te).append(" unloaded, aborting").toString());
		}

		public PostPonedAddCallback(TileEntity te)
		{
			this.te = te;
		}
	}

	class Tile
	{

		final TileEntity entity;
		final Tile neighbors[] = new Tile[6];
		final EnergyNet this$0;

		void destroy()
		{
			Direction arr$[] = Direction.directions;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Direction dir = arr$[i$];
				Tile neighbor = neighbors[dir.ordinal()];
				if (neighbor != null)
					neighbor.neighbors[dir.getInverse().ordinal()] = null;
			}

		}

		Tile(TileEntity te)
		{
			this$0 = EnergyNet.this;
			super();
			entity = te;
			Direction arr$[] = Direction.directions;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Direction dir = arr$[i$];
				ForgeDirection fdir = dir.toForgeDirection();
				ChunkCoordinates coords = new ChunkCoordinates(te.xCoord + fdir.offsetX, te.yCoord + fdir.offsetY, te.zCoord + fdir.offsetZ);
				int index = dir.ordinal();
				neighbors[index] = (Tile)registeredTiles.get(coords);
				if (neighbors[index] != null)
					neighbors[index].neighbors[dir.getInverse().ordinal()] = this;
			}

		}
	}

	static class EnergyTarget
	{

		Tile tile;
		Direction direction;

		EnergyTarget(Tile tile, Direction direction)
		{
			this.tile = tile;
			this.direction = direction;
		}
	}

	static class EnergyBlockLink
	{

		Direction direction;
		double loss;

		EnergyBlockLink(Direction direction, double loss)
		{
			this.direction = direction;
			this.loss = loss;
		}
	}

	static class EnergyPath
	{

		Tile target;
		Direction targetDirection;
		Set conductors;
		int minX;
		int minY;
		int minZ;
		int maxX;
		int maxY;
		int maxZ;
		double loss;
		int minInsulationEnergyAbsorption;
		int minInsulationBreakdownEnergy;
		int minConductorBreakdownEnergy;
		long totalEnergyConducted;

		EnergyPath()
		{
			target = null;
			conductors = new HashSet();
			minX = 0x7fffffff;
			minY = 0x7fffffff;
			minZ = 0x7fffffff;
			maxX = 0x80000000;
			maxY = 0x80000000;
			maxZ = 0x80000000;
			loss = 0.0D;
			minInsulationEnergyAbsorption = 0x7fffffff;
			minInsulationBreakdownEnergy = 0x7fffffff;
			minConductorBreakdownEnergy = 0x7fffffff;
			totalEnergyConducted = 0L;
		}
	}

	public static class EventHandler
	{

		public void onEnergyTileLoad(EnergyTileLoadEvent event)
		{
			EnergyNet.getForWorld(event.world).addTileEntity((TileEntity)event.energyTile);
		}

		public void onEnergyTileUnload(EnergyTileUnloadEvent event)
		{
			EnergyNet.getForWorld(event.world).removeTileEntity((TileEntity)event.energyTile);
		}

		public void onEnergyTileSource(EnergyTileSourceEvent event)
		{
			event.amount = EnergyNet.getForWorld(event.world).emitEnergyFrom((IEnergySource)event.energyTile, event.amount);
		}

		public EventHandler()
		{
			MinecraftForge.EVENT_BUS.register(this);
		}
	}


	public static final double minConductionLoss = 0.0001D;
	private static final Direction directions[] = Direction.values();
	private final Map energySourceToEnergyPathMap = new HashMap();
	private final Map entityLivingToShockEnergyMap = new HashMap();
	private final Map registeredTiles = new HashMap();
	private static int apiDemandsErrorCooldown = 0;
	private static int apiEmitErrorCooldown = 0;

	public static void initialize()
	{
		new EventHandler();
	}

	public static EnergyNet getForWorld(World world)
	{
		WorldData worldData = WorldData.get(world);
		return worldData.energyNet;
	}

	public static void onTick(World world)
	{
		IC2.platform.profilerStartSection("Shocking");
		EnergyNet energyNet = getForWorld(world);
		Iterator i$ = energyNet.entityLivingToShockEnergyMap.entrySet().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			java.util.Map.Entry entry = (java.util.Map.Entry)i$.next();
			EntityLivingBase target = (EntityLivingBase)entry.getKey();
			int damage = (((Integer)entry.getValue()).intValue() + 63) / 64;
			if (target.isEntityAlive())
				target.attackEntityFrom(IC2DamageSource.electricity, damage);
		} while (true);
		energyNet.entityLivingToShockEnergyMap.clear();
		if (world.provider.dimensionId == 0)
		{
			if (apiDemandsErrorCooldown > 0)
				apiDemandsErrorCooldown--;
			if (apiEmitErrorCooldown > 0)
				apiEmitErrorCooldown--;
		}
		IC2.platform.profilerEndSection();
	}

	protected EnergyNet()
	{
	}

	public void addTileEntity(TileEntity te)
	{
		if (!IC2.platform.isSimulating())
		{
			IC2.log.warning((new StringBuilder()).append("EnergyNet.addTileEntity: called for ").append(te).append(" client-side, aborting").toString());
			return;
		}
		if (!(te instanceof IEnergyTile))
		{
			IC2.log.warning((new StringBuilder()).append("EnergyNet.addTileEntity: ").append(te).append(" doesn't implement IEnergyTile, aborting").toString());
			return;
		}
		if (te.isInvalid())
		{
			IC2.log.warning((new StringBuilder()).append("EnergyNet.addTileEntity: ").append(te).append(" is invalid (TileEntity.isInvalid()), aborting").toString());
			return;
		}
		ChunkCoordinates coords = new ChunkCoordinates(te.xCoord, te.yCoord, te.zCoord);
		if (registeredTiles.containsKey(coords))
		{
			IC2.log.warning((new StringBuilder()).append("EnergyNet.addTileEntity: ").append(te).append(" is already added, aborting").toString());
			return;
		}
		if (!te.worldObj.blockExists(te.xCoord, te.yCoord, te.zCoord))
		{
			IC2.log.warning((new StringBuilder()).append("EnergyNet.addTileEntity: ").append(te).append(" was added too early, postponing").toString());
			IC2.addSingleTickCallback(te.worldObj, new PostPonedAddCallback(te));
			return;
		}
		Tile tile = new Tile(te);
		registeredTiles.put(coords, tile);
		if (te instanceof IEnergyAcceptor)
		{
			List reverseEnergyPaths = discover(tile, true, 0x7fffffff);
			Iterator i$ = reverseEnergyPaths.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				EnergyPath reverseEnergyPath = (EnergyPath)i$.next();
				Tile srcTile = reverseEnergyPath.target;
				if (energySourceToEnergyPathMap.containsKey(srcTile) && (double)((IEnergySource)srcTile.entity).getMaxEnergyOutput() > reverseEnergyPath.loss)
					energySourceToEnergyPathMap.remove(srcTile);
			} while (true);
		}
		if (!(te instanceof IEnergySource));
		ForgeDirection arr$[] = ForgeDirection.VALID_DIRECTIONS;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			ForgeDirection dir = arr$[i$];
			int x = te.xCoord + dir.offsetX;
			int y = te.yCoord + dir.offsetY;
			int z = te.zCoord + dir.offsetZ;
			if (te.worldObj.blockExists(x, y, z))
				te.worldObj.notifyBlockOfNeighborChange(x, y, z, te.getBlockType().blockID);
		}

	}

	public void removeTileEntity(TileEntity te)
	{
		if (!IC2.platform.isSimulating())
		{
			IC2.log.warning((new StringBuilder()).append("EnergyNet.removeTileEntity: called for ").append(te).append(" client-side, aborting").toString());
			return;
		}
		if (!(te instanceof IEnergyTile))
		{
			IC2.log.warning((new StringBuilder()).append("EnergyNet.removeTileEntity: ").append(te).append(" doesn't implement IEnergyTile, aborting").toString());
			return;
		}
		ChunkCoordinates coords = new ChunkCoordinates(te.xCoord, te.yCoord, te.zCoord);
		Tile tile = (Tile)registeredTiles.get(coords);
		if (tile == null)
		{
			IC2.log.warning((new StringBuilder()).append("EnergyNet.removeTileEntity: ").append(te).append(" is already removed, aborting").toString());
			return;
		}
		if (te instanceof IEnergyAcceptor)
		{
			List reverseEnergyPaths = discover(tile, true, 0x7fffffff);
			Iterator i$ = reverseEnergyPaths.iterator();
label0:
			do
			{
				if (!i$.hasNext())
					break;
				EnergyPath reverseEnergyPath = (EnergyPath)i$.next();
				Tile srcTile = reverseEnergyPath.target;
				if (!energySourceToEnergyPathMap.containsKey(srcTile) || (double)((IEnergySource)srcTile.entity).getMaxEnergyOutput() <= reverseEnergyPath.loss)
					continue;
				if (te instanceof IEnergyConductor)
				{
					energySourceToEnergyPathMap.remove(srcTile);
					continue;
				}
				Iterator it = ((List)energySourceToEnergyPathMap.get(srcTile)).iterator();
				do
					if (!it.hasNext())
						continue label0;
				while (((EnergyPath)it.next()).target != tile);
				it.remove();
			} while (true);
		}
		if (te instanceof IEnergySource)
			energySourceToEnergyPathMap.remove(tile);
		tile.destroy();
		registeredTiles.remove(coords);
		if (te.worldObj.blockExists(te.xCoord, te.yCoord, te.zCoord))
		{
			Block block = te.getBlockType();
			ForgeDirection arr$[] = ForgeDirection.VALID_DIRECTIONS;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				ForgeDirection dir = arr$[i$];
				int x = te.xCoord + dir.offsetX;
				int y = te.yCoord + dir.offsetY;
				int z = te.zCoord + dir.offsetZ;
				if (te.worldObj.blockExists(x, y, z))
					te.worldObj.notifyBlockOfNeighborChange(x, y, z, block == null ? 0 : block.blockID);
			}

		}
	}

	public int emitEnergyFrom(IEnergySource energySource, int amount)
	{
		if (!IC2.platform.isSimulating())
		{
			if (apiEmitErrorCooldown == 0)
			{
				apiEmitErrorCooldown = 600;
				IC2.log.warning((new StringBuilder()).append("EnergyNet.emitEnergyFrom: called for ").append(energySource).append(" client-side, aborting").toString());
			}
			return amount;
		}
		if (!(energySource instanceof TileEntity))
		{
			if (apiEmitErrorCooldown == 0)
			{
				apiEmitErrorCooldown = 600;
				IC2.log.warning((new StringBuilder()).append("EnergyNet.emitEnergyFrom: ").append(energySource).append(" is no tile entity, aborting").toString());
			}
			return amount;
		}
		TileEntity srcTe = (TileEntity)energySource;
		if (srcTe.isInvalid())
		{
			IC2.log.warning((new StringBuilder()).append("EnergyNet.emitEnergyFrom: ").append(srcTe).append(" is invalid (TileEntity.isInvalid()), aborting").toString());
			return amount;
		}
		ChunkCoordinates coords = new ChunkCoordinates(srcTe.xCoord, srcTe.yCoord, srcTe.zCoord);
		Tile tile = (Tile)registeredTiles.get(coords);
		if (tile == null)
		{
			if (apiEmitErrorCooldown == 0)
			{
				apiEmitErrorCooldown = 600;
				IC2.log.warning((new StringBuilder()).append("EnergyNet.emitEnergyFrom: ").append(energySource).append(" is not added to the enet, aborting").toString());
			}
			return amount;
		}
		if (!energySourceToEnergyPathMap.containsKey(tile))
			energySourceToEnergyPathMap.put(tile, discover(tile, false, energySource.getMaxEnergyOutput()));
		List activeEnergyPaths = new Vector();
		double totalInvLoss = 0.0D;
		Iterator i$ = ((List)energySourceToEnergyPathMap.get(tile)).iterator();
		do
		{
			if (!i$.hasNext())
				break;
			EnergyPath energyPath = (EnergyPath)i$.next();
			IEnergySink sink = (IEnergySink)energyPath.target.entity;
			if (sink.demandsEnergy() > 0 && energyPath.loss < (double)amount)
			{
				totalInvLoss += 1.0D / energyPath.loss;
				activeEnergyPaths.add(energyPath);
			}
		} while (true);
		Collections.shuffle(activeEnergyPaths);
		for (int i = activeEnergyPaths.size() - amount; i > 0; i--)
		{
			EnergyPath removedEnergyPath = (EnergyPath)activeEnergyPaths.remove(activeEnergyPaths.size() - 1);
			totalInvLoss -= 1.0D / removedEnergyPath.loss;
		}

		Map suppliedEnergyPaths = new HashMap();
		int energyConsumed;
		for (; !activeEnergyPaths.isEmpty() && amount > 0; amount -= energyConsumed)
		{
			energyConsumed = 0;
			double newTotalInvLoss = 0.0D;
			List currentActiveEnergyPaths = activeEnergyPaths;
			activeEnergyPaths = new Vector();
			activeEnergyPaths.iterator();
			for (Iterator i$ = currentActiveEnergyPaths.iterator(); i$.hasNext();)
			{
				EnergyPath energyPath = (EnergyPath)i$.next();
				Tile dstTile = energyPath.target;
				IEnergySink sink = (IEnergySink)dstTile.entity;
				int energyProvided = (int)Math.floor((double)Math.round(((double)amount / totalInvLoss / energyPath.loss) * 100000D) / 100000D);
				int energyLoss = (int)Math.floor(energyPath.loss);
				if (energyProvided > energyLoss)
				{
					int energyReturned = sink.injectEnergy(energyPath.targetDirection, energyProvided - energyLoss);
					if (energyReturned == 0 && sink.demandsEnergy() > 0)
					{
						activeEnergyPaths.add(energyPath);
						newTotalInvLoss += 1.0D / energyPath.loss;
					} else
					if (energyReturned >= energyProvided - energyLoss)
					{
						energyReturned = energyProvided - energyLoss;
						if (apiDemandsErrorCooldown == 0)
						{
							apiDemandsErrorCooldown = 600;
							TileEntity te = dstTile.entity;
							String c = (new StringBuilder()).append(te.worldObj != null ? ((Object) (Integer.valueOf(te.worldObj.provider.dimensionId))) : "unknown").append(":").append(te.xCoord).append(",").append(te.yCoord).append(",").append(te.zCoord).toString();
							IC2.log.warning((new StringBuilder()).append("API ERROR: ").append(dstTile).append(" (").append(c).append(") didn't implement demandsEnergy() properly, no energy from injectEnergy accepted (").append(energyReturned).append(") although demandsEnergy() requested ").append(energyProvided - energyLoss).append(".").toString());
						}
					}
					energyConsumed += energyProvided - energyReturned;
					int energyInjected = energyProvided - energyLoss - energyReturned;
					if (!suppliedEnergyPaths.containsKey(energyPath))
						suppliedEnergyPaths.put(energyPath, Integer.valueOf(energyInjected));
					else
						suppliedEnergyPaths.put(energyPath, Integer.valueOf(energyInjected + ((Integer)suppliedEnergyPaths.get(energyPath)).intValue()));
				} else
				{
					activeEnergyPaths.add(energyPath);
					newTotalInvLoss += 1.0D / energyPath.loss;
				}
			}

			if (energyConsumed == 0 && !activeEnergyPaths.isEmpty())
			{
				EnergyPath removedEnergyPath = (EnergyPath)activeEnergyPaths.remove(activeEnergyPaths.size() - 1);
				newTotalInvLoss -= 1.0D / removedEnergyPath.loss;
			}
			totalInvLoss = newTotalInvLoss;
		}

		for (Iterator i$ = suppliedEnergyPaths.entrySet().iterator(); i$.hasNext();)
		{
			java.util.Map.Entry entry = (java.util.Map.Entry)i$.next();
			EnergyPath energyPath = (EnergyPath)entry.getKey();
			int energyInjected = ((Integer)entry.getValue()).intValue();
			energyPath.totalEnergyConducted += energyInjected;
			if (energyInjected > energyPath.minInsulationEnergyAbsorption)
			{
				List entitiesNearEnergyPath = srcTe.worldObj.getEntitiesWithinAABB(net/minecraft/entity/EntityLivingBase, AxisAlignedBB.getBoundingBox(energyPath.minX - 1, energyPath.minY - 1, energyPath.minZ - 1, energyPath.maxX + 2, energyPath.maxY + 2, energyPath.maxZ + 2));
				for (Iterator i$ = entitiesNearEnergyPath.iterator(); i$.hasNext();)
				{
					EntityLivingBase entityLiving = (EntityLivingBase)i$.next();
					int maxShockEnergy = 0;
					Iterator i$ = energyPath.conductors.iterator();
					IEnergyConductor conductor;
label0:
					do
					{
						TileEntity te;
						do
						{
							if (!i$.hasNext())
								break label0;
							Tile condTile = (Tile)i$.next();
							te = condTile.entity;
							conductor = (IEnergyConductor)te;
						} while (!((Entity) (entityLiving)).boundingBox.intersectsWith(AxisAlignedBB.getBoundingBox(te.xCoord - 1, te.yCoord - 1, te.zCoord - 1, te.xCoord + 2, te.yCoord + 2, te.zCoord + 2)));
						int shockEnergy = energyInjected - conductor.getInsulationEnergyAbsorption();
						if (shockEnergy > maxShockEnergy)
							maxShockEnergy = shockEnergy;
					} while (conductor.getInsulationEnergyAbsorption() != energyPath.minInsulationEnergyAbsorption);
					if (entityLivingToShockEnergyMap.containsKey(entityLiving))
						entityLivingToShockEnergyMap.put(entityLiving, Integer.valueOf(((Integer)entityLivingToShockEnergyMap.get(entityLiving)).intValue() + maxShockEnergy));
					else
						entityLivingToShockEnergyMap.put(entityLiving, Integer.valueOf(maxShockEnergy));
				}

				if (energyInjected >= energyPath.minInsulationBreakdownEnergy)
				{
					Iterator i$ = energyPath.conductors.iterator();
					do
					{
						if (!i$.hasNext())
							break;
						Tile condTile = (Tile)i$.next();
						IEnergyConductor conductor = (IEnergyConductor)condTile.entity;
						if (energyInjected >= conductor.getInsulationBreakdownEnergy())
						{
							conductor.removeInsulation();
							if (conductor.getInsulationEnergyAbsorption() < energyPath.minInsulationEnergyAbsorption)
								energyPath.minInsulationEnergyAbsorption = conductor.getInsulationEnergyAbsorption();
						}
					} while (true);
				}
			}
			if (energyInjected >= energyPath.minConductorBreakdownEnergy)
			{
				Iterator i$ = energyPath.conductors.iterator();
				while (i$.hasNext()) 
				{
					Tile condTile = (Tile)i$.next();
					IEnergyConductor conductor = (IEnergyConductor)condTile.entity;
					if (energyInjected >= conductor.getConductorBreakdownEnergy())
						conductor.removeConductor();
				}
			}
		}

		return amount;
	}

	/**
	 * @deprecated Method getTotalEnergyConducted is deprecated
	 */

	public long getTotalEnergyConducted(TileEntity tileEntity)
	{
		Tile tile = (Tile)registeredTiles.get(new ChunkCoordinates(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord));
		if (tile == null)
		{
			IC2.log.warning((new StringBuilder()).append("EnergyNet.getTotalEnergyConducted: ").append(tileEntity).append(" is not added to the enet, aborting").toString());
			return 0L;
		}
		long ret = 0L;
		if ((tileEntity instanceof IEnergyConductor) || (tileEntity instanceof IEnergySink))
		{
			List reverseEnergyPaths = discover(tile, true, 0x7fffffff);
			for (Iterator i$ = reverseEnergyPaths.iterator(); i$.hasNext();)
			{
				EnergyPath reverseEnergyPath = (EnergyPath)i$.next();
				Tile srcTile = reverseEnergyPath.target;
				if (energySourceToEnergyPathMap.containsKey(srcTile) && (double)((IEnergySource)srcTile.entity).getMaxEnergyOutput() > reverseEnergyPath.loss)
				{
					Iterator i$ = ((List)energySourceToEnergyPathMap.get(srcTile)).iterator();
					while (i$.hasNext()) 
					{
						EnergyPath energyPath = (EnergyPath)i$.next();
						if ((tileEntity instanceof IEnergySink) && energyPath.target == tile || (tileEntity instanceof IEnergyConductor) && energyPath.conductors.contains(tile))
							ret += energyPath.totalEnergyConducted;
					}
				}
			}

		}
		if ((tileEntity instanceof IEnergySource) && energySourceToEnergyPathMap.containsKey(tile))
		{
			for (Iterator i$ = ((List)energySourceToEnergyPathMap.get(tile)).iterator(); i$.hasNext();)
			{
				EnergyPath energyPath = (EnergyPath)i$.next();
				ret += energyPath.totalEnergyConducted;
			}

		}
		return ret;
	}

	public long getTotalEnergyEmitted(TileEntity tileEntity)
	{
		Tile tile = (Tile)registeredTiles.get(new ChunkCoordinates(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord));
		if (tile == null)
		{
			IC2.log.warning((new StringBuilder()).append("EnergyNet.getTotalEnergyEmitted: ").append(tileEntity).append(" is not added to the enet, aborting").toString());
			return 0L;
		}
		long ret = 0L;
		if (tileEntity instanceof IEnergyConductor)
		{
			List reverseEnergyPaths = discover(tile, true, 0x7fffffff);
			for (Iterator i$ = reverseEnergyPaths.iterator(); i$.hasNext();)
			{
				EnergyPath reverseEnergyPath = (EnergyPath)i$.next();
				Tile srcTile = reverseEnergyPath.target;
				if (energySourceToEnergyPathMap.containsKey(srcTile) && (double)((IEnergySource)srcTile.entity).getMaxEnergyOutput() > reverseEnergyPath.loss)
				{
					Iterator i$ = ((List)energySourceToEnergyPathMap.get(srcTile)).iterator();
					while (i$.hasNext()) 
					{
						EnergyPath energyPath = (EnergyPath)i$.next();
						if ((tileEntity instanceof IEnergyConductor) && energyPath.conductors.contains(tile))
							ret += energyPath.totalEnergyConducted;
					}
				}
			}

		}
		if ((tileEntity instanceof IEnergySource) && energySourceToEnergyPathMap.containsKey(tile))
		{
			for (Iterator i$ = ((List)energySourceToEnergyPathMap.get(tile)).iterator(); i$.hasNext();)
			{
				EnergyPath energyPath = (EnergyPath)i$.next();
				ret += energyPath.totalEnergyConducted;
			}

		}
		return ret;
	}

	public long getTotalEnergySunken(TileEntity tileEntity)
	{
		Tile tile = (Tile)registeredTiles.get(new ChunkCoordinates(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord));
		if (tile == null)
		{
			IC2.log.warning((new StringBuilder()).append("EnergyNet.getTotalEnergySunken: ").append(tileEntity).append(" is not added to the enet, aborting").toString());
			return 0L;
		}
		long ret = 0L;
		if ((tileEntity instanceof IEnergyConductor) || (tileEntity instanceof IEnergySink))
		{
			List reverseEnergyPaths = discover(tile, true, 0x7fffffff);
			for (Iterator i$ = reverseEnergyPaths.iterator(); i$.hasNext();)
			{
				EnergyPath reverseEnergyPath = (EnergyPath)i$.next();
				Tile srcTile = reverseEnergyPath.target;
				if (energySourceToEnergyPathMap.containsKey(srcTile) && (double)((IEnergySource)srcTile.entity).getMaxEnergyOutput() > reverseEnergyPath.loss)
				{
					Iterator i$ = ((List)energySourceToEnergyPathMap.get(srcTile)).iterator();
					while (i$.hasNext()) 
					{
						EnergyPath energyPath = (EnergyPath)i$.next();
						if ((tileEntity instanceof IEnergySink) && energyPath.target == tile || (tileEntity instanceof IEnergyConductor) && energyPath.conductors.contains(tile))
							ret += energyPath.totalEnergyConducted;
					}
				}
			}

		}
		return ret;
	}

	public TileEntity getTileEntity(int x, int y, int z)
	{
		Tile ret = (Tile)registeredTiles.get(new ChunkCoordinates(x, y, z));
		if (ret == null)
			return null;
		else
			return ret.entity;
	}

	public TileEntity getNeighbor(TileEntity te, Direction dir)
	{
		static class 1
		{

			static final int $SwitchMap$ic2$api$Direction[];

			static 
			{
				$SwitchMap$ic2$api$Direction = new int[Direction.values().length];
				try
				{
					$SwitchMap$ic2$api$Direction[Direction.XN.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$api$Direction[Direction.XP.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$api$Direction[Direction.YN.ordinal()] = 3;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$api$Direction[Direction.YP.ordinal()] = 4;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$api$Direction[Direction.ZN.ordinal()] = 5;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$api$Direction[Direction.ZP.ordinal()] = 6;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		switch (1..SwitchMap.ic2.api.Direction[dir.ordinal()])
		{
		case 1: // '\001'
			return getTileEntity(te.xCoord - 1, te.yCoord, te.zCoord);

		case 2: // '\002'
			return getTileEntity(te.xCoord + 1, te.yCoord, te.zCoord);

		case 3: // '\003'
			return getTileEntity(te.xCoord, te.yCoord - 1, te.zCoord);

		case 4: // '\004'
			return getTileEntity(te.xCoord, te.yCoord + 1, te.zCoord);

		case 5: // '\005'
			return getTileEntity(te.xCoord, te.yCoord, te.zCoord - 1);

		case 6: // '\006'
			return getTileEntity(te.xCoord, te.yCoord, te.zCoord + 1);
		}
		return null;
	}

	public Tile getNeighbor(Tile te, Direction dir)
	{
		return te.neighbors[dir.ordinal()];
	}

	private List discover(Tile emitter, boolean reverse, int lossLimit)
	{
		Map reachedTileEntities = new HashMap();
		LinkedList tileEntitiesToCheck = new LinkedList();
		tileEntitiesToCheck.add(emitter);
		do
		{
			if (tileEntitiesToCheck.isEmpty())
				break;
			Tile tile = (Tile)tileEntitiesToCheck.remove();
			if (!tile.entity.isInvalid())
			{
				double currentLoss = 0.0D;
				if (tile != emitter)
					currentLoss = ((EnergyBlockLink)reachedTileEntities.get(tile)).loss;
				List validReceivers = getValidReceivers(tile, reverse);
				Iterator i$ = validReceivers.iterator();
				do
				{
					if (!i$.hasNext())
						break;
					EnergyTarget validReceiver = (EnergyTarget)i$.next();
					if (validReceiver.tile == emitter)
						continue;
					double additionalLoss = 0.0D;
					if (validReceiver.tile.entity instanceof IEnergyConductor)
					{
						additionalLoss = ((IEnergyConductor)validReceiver.tile.entity).getConductionLoss();
						if (additionalLoss < 0.0001D)
							additionalLoss = 0.0001D;
						if (currentLoss + additionalLoss >= (double)lossLimit)
							continue;
					}
					if (!reachedTileEntities.containsKey(validReceiver.tile) || ((EnergyBlockLink)reachedTileEntities.get(validReceiver.tile)).loss > currentLoss + additionalLoss)
					{
						reachedTileEntities.put(validReceiver.tile, new EnergyBlockLink(validReceiver.direction, currentLoss + additionalLoss));
						if (validReceiver.tile.entity instanceof IEnergyConductor)
						{
							tileEntitiesToCheck.remove(validReceiver.tile);
							tileEntitiesToCheck.add(validReceiver.tile);
						}
					}
				} while (true);
			}
		} while (true);
		List energyPaths = new LinkedList();
		Iterator i$ = reachedTileEntities.entrySet().iterator();
label0:
		do
		{
			if (!i$.hasNext())
				break;
			java.util.Map.Entry entry = (java.util.Map.Entry)i$.next();
			Tile tile = (Tile)entry.getKey();
			if ((reverse || !(tile.entity instanceof IEnergySink)) && (!reverse || !(tile.entity instanceof IEnergySource)))
				continue;
			EnergyBlockLink energyBlockLink = (EnergyBlockLink)entry.getValue();
			EnergyPath energyPath = new EnergyPath();
			if (energyBlockLink.loss > 0.10000000000000001D)
				energyPath.loss = energyBlockLink.loss;
			else
				energyPath.loss = 0.10000000000000001D;
			energyPath.target = tile;
			energyPath.targetDirection = energyBlockLink.direction;
			TileEntity te;
			IEnergyConductor energyConductor;
			TileEntity srcTe;
			TileEntity dstTe;
			if (!reverse && (emitter.entity instanceof IEnergySource))
				do
				{
					tile = getNeighbor(tile, energyBlockLink.direction);
					if (tile == emitter)
						break;
					if (tile.entity instanceof IEnergyConductor)
					{
						te = tile.entity;
						energyConductor = (IEnergyConductor)te;
						if (te.xCoord < energyPath.minX)
							energyPath.minX = te.xCoord;
						if (te.yCoord < energyPath.minY)
							energyPath.minY = te.yCoord;
						if (te.zCoord < energyPath.minZ)
							energyPath.minZ = te.zCoord;
						if (te.xCoord > energyPath.maxX)
							energyPath.maxX = te.xCoord;
						if (te.yCoord > energyPath.maxY)
							energyPath.maxY = te.yCoord;
						if (te.zCoord > energyPath.maxZ)
							energyPath.maxZ = te.zCoord;
						energyPath.conductors.add(tile);
						if (energyConductor.getInsulationEnergyAbsorption() < energyPath.minInsulationEnergyAbsorption)
							energyPath.minInsulationEnergyAbsorption = energyConductor.getInsulationEnergyAbsorption();
						if (energyConductor.getInsulationBreakdownEnergy() < energyPath.minInsulationBreakdownEnergy)
							energyPath.minInsulationBreakdownEnergy = energyConductor.getInsulationBreakdownEnergy();
						if (energyConductor.getConductorBreakdownEnergy() < energyPath.minConductorBreakdownEnergy)
							energyPath.minConductorBreakdownEnergy = energyConductor.getConductorBreakdownEnergy();
						energyBlockLink = (EnergyBlockLink)reachedTileEntities.get(tile);
						if (energyBlockLink == null)
						{
							srcTe = emitter.entity;
							dstTe = energyPath.target.entity;
							IC2.platform.displayError((new StringBuilder()).append("An energy network pathfinding entry is corrupted.\nThis could happen due to incorrect Minecraft behavior or a bug.\n\n(Technical information: energyBlockLink, tile entities below)\nE: ").append(srcTe).append(" (").append(srcTe.xCoord).append(",").append(srcTe.yCoord).append(",").append(srcTe.zCoord).append(")\n").append("C: ").append(te).append(" (").append(te.xCoord).append(",").append(te.yCoord).append(",").append(te.zCoord).append(")\n").append("R: ").append(dstTe).append(" (").append(dstTe.xCoord).append(",").append(dstTe.yCoord).append(",").append(dstTe.zCoord).append(")").toString());
						}
					} else
					{
						IC2.log.warning((new StringBuilder()).append("EnergyNet: EnergyBlockLink corrupted (").append(energyPath.target.entity).append(" [").append(energyPath.target.entity.xCoord).append(" ").append(energyPath.target.entity.yCoord).append(" ").append(energyPath.target.entity.zCoord).append("] -> ").append(tile.entity).append(" [").append(tile.entity.xCoord).append(" ").append(tile.entity.yCoord).append(" ").append(tile.entity.zCoord).append("] -> ").append(emitter.entity).append(" [").append(emitter.entity.xCoord).append(" ").append(emitter.entity.yCoord).append(" ").append(emitter.entity.zCoord).append("])").toString());
						continue label0;
					}
				} while (true);
			energyPaths.add(energyPath);
		} while (true);
		return energyPaths;
	}

	private List getValidReceivers(Tile emitter, boolean reverse)
	{
		List validReceivers = new LinkedList();
		Direction arr$[] = directions;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Direction direction = arr$[i$];
			Tile target = getNeighbor(emitter, direction);
			if (target == null)
				continue;
			Direction inverseDirection = direction.getInverse();
			if ((!reverse && (emitter.entity instanceof IEnergyEmitter) && ((IEnergyEmitter)emitter.entity).emitsEnergyTo(target.entity, direction) || reverse && (emitter.entity instanceof IEnergyAcceptor) && ((IEnergyAcceptor)emitter.entity).acceptsEnergyFrom(target.entity, direction)) && (!reverse && (target.entity instanceof IEnergyAcceptor) && ((IEnergyAcceptor)target.entity).acceptsEnergyFrom(emitter.entity, inverseDirection) || reverse && (target.entity instanceof IEnergyEmitter) && ((IEnergyEmitter)target.entity).emitsEnergyTo(emitter.entity, inverseDirection)))
				validReceivers.add(new EnergyTarget(target, inverseDirection));
		}

		return validReceivers;
	}


}
