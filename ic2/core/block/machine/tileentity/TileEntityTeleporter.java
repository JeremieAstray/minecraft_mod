// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityTeleporter.java

package ic2.core.block.machine.tileentity;

import ic2.api.Direction;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.api.tile.IEnergyStorage;
import ic2.core.*;
import ic2.core.audio.*;
import ic2.core.block.TileEntityBlock;
import ic2.core.network.NetworkManager;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class TileEntityTeleporter extends TileEntityBlock
	implements INetworkTileEntityEventListener
{

	private static final Direction directions[] = Direction.values();
	public boolean targetSet;
	public int targetX;
	public int targetY;
	public int targetZ;
	private AudioSource audioSource;
	private static final int EventTeleport = 0;

	public TileEntityTeleporter()
	{
		targetSet = false;
		audioSource = null;
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		targetSet = nbttagcompound.getBoolean("targetSet");
		targetX = nbttagcompound.getInteger("targetX");
		targetY = nbttagcompound.getInteger("targetY");
		targetZ = nbttagcompound.getInteger("targetZ");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setBoolean("targetSet", targetSet);
		nbttagcompound.setInteger("targetX", targetX);
		nbttagcompound.setInteger("targetY", targetY);
		nbttagcompound.setInteger("targetZ", targetZ);
	}

	public boolean enableUpdateEntity()
	{
		return true;
	}

	public void updateEntity()
	{
		super.updateEntity();
		if (IC2.platform.isSimulating())
			if (super.worldObj.isBlockIndirectlyGettingPowered(super.xCoord, super.yCoord, super.zCoord) && targetSet)
			{
				boolean prevWorldChunkLoadOverride = super.worldObj.findingSpawnPoint;
				super.worldObj.findingSpawnPoint = true;
				Chunk chunk = super.worldObj.getChunkProvider().provideChunk(targetX >> 4, targetZ >> 4);
				super.worldObj.findingSpawnPoint = prevWorldChunkLoadOverride;
				if (chunk == null || chunk.getBlockID(targetX & 0xf, targetY, targetZ & 0xf) != Ic2Items.teleporter.itemID || chunk.getBlockMetadata(targetX & 0xf, targetY, targetZ & 0xf) != Ic2Items.teleporter.getItemDamage())
				{
					targetSet = false;
					setActive(false);
				} else
				{
					setActive(true);
					List entitiesNearby = super.worldObj.getEntitiesWithinAABB(net/minecraft/entity/Entity, AxisAlignedBB.getBoundingBox(super.xCoord - 1, super.yCoord, super.zCoord - 1, super.xCoord + 2, super.yCoord + 3, super.zCoord + 2));
					if (!entitiesNearby.isEmpty())
					{
						double minDistanceSquared = 1.7976931348623157E+308D;
						Entity closestEntity = null;
						Iterator i$ = entitiesNearby.iterator();
						do
						{
							if (!i$.hasNext())
								break;
							Entity entity = (Entity)i$.next();
							if (entity.ridingEntity == null)
							{
								double distSquared = ((double)super.xCoord - entity.posX) * ((double)super.xCoord - entity.posX) + ((double)(super.yCoord + 1) - entity.posY) * ((double)(super.yCoord + 1) - entity.posY) + ((double)super.zCoord - entity.posZ) * ((double)super.zCoord - entity.posZ);
								if (distSquared < minDistanceSquared)
								{
									minDistanceSquared = distSquared;
									closestEntity = entity;
								}
							}
						} while (true);
						teleport(closestEntity);
					}
				}
			} else
			{
				setActive(false);
			}
		if (IC2.platform.isRendering() && getActive())
			spawnBlueParticles(2, super.xCoord, super.yCoord, super.zCoord);
	}

	public void onUnloaded()
	{
		if (IC2.platform.isRendering() && audioSource != null)
		{
			IC2.audioManager.removeSources(this);
			audioSource = null;
		}
		super.onUnloaded();
	}

	public void teleport(Entity user)
	{
		double distance = Math.sqrt((super.xCoord - targetX) * (super.xCoord - targetX) + (super.yCoord - targetY) * (super.yCoord - targetY) + (super.zCoord - targetZ) * (super.zCoord - targetZ));
		int weight = getWeightOf(user);
		if (weight == 0)
			return;
		int energyCost = (int)((double)weight * Math.pow(distance + 10D, 0.69999999999999996D) * 5D);
		if (energyCost > getAvailableEnergy())
			return;
		consumeEnergy(energyCost);
		if (user instanceof EntityPlayerMP)
			((EntityPlayerMP)user).setPositionAndUpdate((double)targetX + 0.5D, (double)targetY + 1.5D + user.getYOffset(), (double)targetZ + 0.5D);
		else
			user.setPositionAndRotation((double)targetX + 0.5D, (double)targetY + 1.5D + user.getYOffset(), (double)targetZ + 0.5D, user.rotationYaw, user.rotationPitch);
		IC2.network.initiateTileEntityEvent(this, 0, true);
		if ((user instanceof EntityPlayer) && distance >= 1000D)
			IC2.achievements.issueAchievement((EntityPlayer)user, "teleportFarAway");
	}

	public void spawnBlueParticles(int n, int x, int y, int z)
	{
		for (int i = 0; i < n; i++)
		{
			super.worldObj.spawnParticle("reddust", (float)x + super.worldObj.rand.nextFloat(), (float)(y + 1) + super.worldObj.rand.nextFloat(), (float)z + super.worldObj.rand.nextFloat(), -1D, 0.0D, 1.0D);
			super.worldObj.spawnParticle("reddust", (float)x + super.worldObj.rand.nextFloat(), (float)(y + 2) + super.worldObj.rand.nextFloat(), (float)z + super.worldObj.rand.nextFloat(), -1D, 0.0D, 1.0D);
		}

	}

	public void consumeEnergy(int energy)
	{
		List energySources = new LinkedList();
		Direction arr$[] = directions;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Direction direction = arr$[i$];
			TileEntity target = direction.applyToTileEntity(this);
			if (!(target instanceof IEnergyStorage))
				continue;
			IEnergyStorage energySource = (IEnergyStorage)target;
			if (energySource.isTeleporterCompatible(direction.getInverse()) && energySource.getStored() > 0)
				energySources.add(energySource);
		}

		while (energy > 0) 
		{
			int drain = ((energy + energySources.size()) - 1) / energySources.size();
			Iterator it = energySources.iterator();
			while (it.hasNext()) 
			{
				IEnergyStorage energySource = (IEnergyStorage)it.next();
				if (drain > energy)
					drain = energy;
				if (energySource.getStored() <= drain)
				{
					energy -= energySource.getStored();
					energySource.setStored(0);
					it.remove();
				} else
				{
					energy -= drain;
					energySource.addEnergy(-drain);
				}
			}
		}
	}

	public int getAvailableEnergy()
	{
		int energy = 0;
		Direction arr$[] = directions;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Direction direction = arr$[i$];
			TileEntity target = direction.applyToTileEntity(this);
			if (!(target instanceof IEnergyStorage))
				continue;
			IEnergyStorage storage = (IEnergyStorage)target;
			if (storage.isTeleporterCompatible(direction.getInverse()))
				energy += storage.getStored();
		}

		return energy;
	}

	public int getWeightOf(Entity user)
	{
		int weight = 0;
		for (Entity ce = user; ce != null; ce = ce.riddenByEntity)
		{
			if (ce instanceof EntityItem)
			{
				ItemStack is = ((EntityItem)ce).getEntityItem();
				weight += (100 * is.stackSize) / is.getMaxStackSize();
			} else
			if ((ce instanceof EntityAnimal) || (ce instanceof EntityMinecart) || (ce instanceof EntityBoat))
				weight += 100;
			else
			if (ce instanceof EntityPlayer)
			{
				weight += 1000;
				if (IC2.enableTeleporterInventory)
				{
					InventoryPlayer inv = ((EntityPlayer)ce).inventory;
					for (int i = 0; i < inv.mainInventory.length; i++)
						if (inv.mainInventory[i] != null)
							weight += (100 * inv.mainInventory[i].stackSize) / inv.mainInventory[i].getMaxStackSize();

				}
			} else
			if (ce instanceof EntityGhast)
				weight += 2500;
			else
			if (ce instanceof EntityDragon)
				weight += 10000;
			else
			if (ce instanceof EntityCreature)
				weight += 500;
			if (!IC2.enableTeleporterInventory || !(ce instanceof EntityLivingBase))
				continue;
			EntityLivingBase living = (EntityLivingBase)ce;
			for (int i = (ce instanceof EntityPlayer) ? 1 : 0; i <= 4; i++)
			{
				ItemStack item = living.getCurrentItemOrArmor(i);
				if (item != null)
					weight += (100 * item.stackSize) / item.getMaxStackSize();
			}

		}

		return weight;
	}

	public void setTarget(int x, int y, int z)
	{
		targetSet = true;
		targetX = x;
		targetY = y;
		targetZ = z;
		IC2.network.updateTileEntityField(this, "targetX");
		IC2.network.updateTileEntityField(this, "targetY");
		IC2.network.updateTileEntityField(this, "targetZ");
	}

	public List getNetworkedFields()
	{
		List ret = new Vector(3);
		ret.add("targetX");
		ret.add("targetY");
		ret.add("targetZ");
		ret.addAll(super.getNetworkedFields());
		return ret;
	}

	public void onNetworkUpdate(String field)
	{
		if (field.equals("active") && prevActive != getActive())
		{
			if (audioSource == null)
				audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Machines/Teleporter/TeleChargedLoop.ogg", true, false, IC2.audioManager.defaultVolume);
			if (getActive())
			{
				if (audioSource != null)
					audioSource.play();
			} else
			if (audioSource != null)
				audioSource.stop();
		}
		super.onNetworkUpdate(field);
	}

	public void onNetworkEvent(int event)
	{
		switch (event)
		{
		case 0: // '\0'
			IC2.audioManager.playOnce(this, PositionSpec.Center, "Machines/Teleporter/TeleUse.ogg", true, IC2.audioManager.defaultVolume);
			IC2.audioManager.playOnce(new AudioPosition(super.worldObj, (float)targetX + 0.5F, (float)targetY + 0.5F, (float)targetZ + 0.5F), PositionSpec.Center, "Machines/Teleporter/TeleUse.ogg", true, IC2.audioManager.defaultVolume);
			spawnBlueParticles(20, super.xCoord, super.yCoord, super.zCoord);
			spawnBlueParticles(20, targetX, targetY, targetZ);
			break;

		default:
			IC2.platform.displayError((new StringBuilder()).append("An unknown event type was received over multiplayer.\nThis could happen due to corrupted data or a bug.\n\n(Technical information: event ID ").append(event).append(", tile entity below)\n").append("T: ").append(this).append(" (").append(super.xCoord).append(",").append(super.yCoord).append(",").append(super.zCoord).append(")").toString());
			break;
		}
	}

}
