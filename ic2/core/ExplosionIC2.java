// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ExplosionIC2.java

package ic2.core;

import ic2.api.tile.ExplosionWhitelist;
import ic2.core.block.EntityNuke;
import ic2.core.item.armor.ItemArmorHazmat;
import ic2.core.network.NetworkManager;
import ic2.core.util.Util;
import java.util.*;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.*;

// Referenced classes of package ic2.core:
//			IC2, IC2Potion, IC2Achievements, IC2DamageSource

public class ExplosionIC2 extends Explosion
{
	static class DropData
	{

		int n;
		int maxY;

		public DropData add(int n, int y)
		{
			this.n += n;
			if (y > maxY)
				maxY = y;
			return this;
		}

		DropData(int n, int y)
		{
			this.n = n;
			maxY = y;
		}
	}

	static class ItemWithMeta
	{

		int itemId;
		int metaData;

		public boolean equals(Object obj)
		{
			if (obj instanceof ItemWithMeta)
			{
				ItemWithMeta itemWithMeta = (ItemWithMeta)obj;
				return itemWithMeta.itemId == itemId && itemWithMeta.metaData == metaData;
			} else
			{
				return false;
			}
		}

		public int hashCode()
		{
			return itemId * 31 ^ metaData;
		}

		ItemWithMeta(int itemId, int metaData)
		{
			this.itemId = itemId;
			this.metaData = metaData;
		}
	}

	static class XZposition
	{

		int x;
		int z;

		public boolean equals(Object obj)
		{
			if (obj instanceof XZposition)
			{
				XZposition xZposition = (XZposition)obj;
				return xZposition.x == x && xZposition.z == z;
			} else
			{
				return false;
			}
		}

		public int hashCode()
		{
			return x * 31 ^ z;
		}

		XZposition(int x, int z)
		{
			this.x = x;
			this.z = z;
		}
	}


	private final Random ExplosionRNG;
	private final World field_77287_j;
	private final int mapHeight;
	public double field_77284_b;
	public double field_77285_c;
	public double field_77282_d;
	public Entity field_77283_e;
	public float power;
	public float explosionDropRate;
	public float explosionDamage;
	public boolean nuclear;
	public EntityLivingBase igniter;
	public List entitiesInRange;
	public Map vecMap;
	public Map destroyedBlockPositions;
	private ChunkCache chunkCache;
	private static final double dropPowerLimit = 8D;
	private static final double damageAtDropPowerLimit = 32D;
	private static final double accelerationAtDropPowerLimit = 0.69999999999999996D;
	private static final double motionLimit = 60D;
	private static final int secondaryRayCount = 5;

	public ExplosionIC2(World world, Entity entity, double x, double y, double z, float power, float drop, float entitydamage)
	{
		super(world, entity, x, y, z, power);
		ExplosionRNG = new Random();
		nuclear = false;
		entitiesInRange = new ArrayList();
		vecMap = new HashMap();
		destroyedBlockPositions = new HashMap();
		super.worldObj = world;
		mapHeight = IC2.getWorldHeight(world);
		super.exploder = entity;
		this.power = power;
		explosionDropRate = drop;
		explosionDamage = entitydamage;
		super.explosionX = x;
		super.explosionY = y;
		super.explosionZ = z;
	}

	public ExplosionIC2(World world, Entity entity, double x, double y, double z, float power, float drop, float entitydamage, boolean nuclear)
	{
		this(world, entity, x, y, z, power, drop, entitydamage);
		this.nuclear = nuclear;
	}

	public ExplosionIC2(World world, Entity entity, double x, double y, double z, float power, float drop, float entitydamage, EntityLivingBase igniter)
	{
		this(world, entity, x, y, z, power, drop, entitydamage);
		this.igniter = igniter;
	}

	public void doExplosion()
	{
		if (power <= 0.0F)
			return;
		double maxDistance = (double)power / 0.40000000000000002D;
		int maxDistanceInt = (int)Math.ceil(maxDistance);
		chunkCache = new ChunkCache(super.worldObj, (int)super.explosionX - maxDistanceInt, (int)super.explosionY - maxDistanceInt, (int)super.explosionZ - maxDistanceInt, (int)super.explosionX + maxDistanceInt, (int)super.explosionY + maxDistanceInt, (int)super.explosionZ + maxDistanceInt, 0);
		List entities = super.worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getAABBPool().getAABB(super.explosionX - maxDistance, super.explosionY - maxDistance, super.explosionZ - maxDistance, super.explosionX + maxDistance, super.explosionY + maxDistance, super.explosionZ + maxDistance));
		Iterator i$ = entities.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			Entity entity = (Entity)i$.next();
			if ((entity instanceof EntityLivingBase) || (entity instanceof EntityItem))
				entitiesInRange.add(new java.util.AbstractMap.SimpleEntry(Integer.valueOf((int)((entity.posX - super.explosionX) * (entity.posX - super.explosionX) + (entity.posY - super.explosionY) * (entity.posY - super.explosionY) + (entity.posZ - super.explosionZ) * (entity.posZ - super.explosionZ))), entity));
		} while (true);
		boolean entitiesAreInRange = !entitiesInRange.isEmpty();
		if (entitiesAreInRange)
			Collections.sort(entitiesInRange, new Comparator() {

				final ExplosionIC2 this$0;

				public int compare(java.util.Map.Entry a, java.util.Map.Entry b)
				{
					return ((Integer)a.getKey()).intValue() - ((Integer)b.getKey()).intValue();
				}

				public volatile int compare(Object x0, Object x1)
				{
					return compare((java.util.Map.Entry)x0, (java.util.Map.Entry)x1);
				}

			
			{
				this$0 = ExplosionIC2.this;
				super();
			}
			});
		int steps = (int)Math.ceil(3.1415926535897931D / Math.atan(1.0D / maxDistance));
		for (int phi_n = 0; phi_n < 2 * steps; phi_n++)
		{
			for (int theta_n = 0; theta_n < steps; theta_n++)
			{
				double phi = (6.2831853071795862D / (double)steps) * (double)phi_n;
				double theta = (3.1415926535897931D / (double)steps) * (double)theta_n;
				shootRay(super.explosionX, super.explosionY, super.explosionZ, phi, theta, power, entitiesAreInRange && phi_n % 8 == 0 && theta_n % 8 == 0);
			}

		}

		Iterator i$ = entitiesInRange.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			java.util.Map.Entry entry = (java.util.Map.Entry)i$.next();
			Entity entity = (Entity)entry.getValue();
			double motionSq = entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ;
			if (motionSq > 3600D)
			{
				double reduction = Math.sqrt(3600D / motionSq);
				entity.motionX *= reduction;
				entity.motionY *= reduction;
				entity.motionZ *= reduction;
			}
		} while (true);
		if (isNuclear())
		{
			i$ = super.worldObj.getEntitiesWithinAABB(net/minecraft/entity/EntityLiving, AxisAlignedBB.getAABBPool().getAABB(super.explosionX - 100D, super.explosionY - 100D, super.explosionZ - 100D, super.explosionX + 100D, super.explosionY + 100D, super.explosionZ + 100D)).iterator();
			do
			{
				if (!i$.hasNext())
					break;
				EntityLiving entity = (EntityLiving)i$.next();
				if (!ItemArmorHazmat.hasCompleteHazmat(entity))
				{
					double distance = entity.getDistance(super.explosionX, super.explosionY, super.explosionZ);
					int hungerLength = (int)(120D * (100D - distance));
					int poisonLength = (int)(80D * (30D - distance));
					if (hungerLength >= 0)
						entity.addPotionEffect(new PotionEffect(Potion.hunger.id, hungerLength, 0));
					if (poisonLength >= 0)
						entity.addPotionEffect(new PotionEffect(((Potion) (IC2Potion.radiation)).id, poisonLength, 0));
				}
			} while (true);
		}
		IC2.network.initiateExplosionEffect(super.worldObj, super.explosionX, super.explosionY, super.explosionZ);
		Map blocksToDrop = new HashMap();
		Iterator i$ = destroyedBlockPositions.entrySet().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			java.util.Map.Entry entry = (java.util.Map.Entry)i$.next();
			int x = ((ChunkPosition)entry.getKey()).x;
			int y = ((ChunkPosition)entry.getKey()).y;
			int z = ((ChunkPosition)entry.getKey()).z;
			int blockId = chunkCache.getBlockId(x, y, z);
			if (blockId != 0)
			{
				if (((Boolean)entry.getValue()).booleanValue())
				{
					double effectX = (float)x + super.worldObj.rand.nextFloat();
					double effectY = (float)y + super.worldObj.rand.nextFloat();
					double effectZ = (float)z + super.worldObj.rand.nextFloat();
					double d3 = effectX - super.explosionX;
					double d4 = effectY - super.explosionY;
					double d5 = effectZ - super.explosionZ;
					double effectDistance = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
					d3 /= effectDistance;
					d4 /= effectDistance;
					d5 /= effectDistance;
					double d7 = 0.5D / (effectDistance / (double)power + 0.10000000000000001D);
					d7 *= super.worldObj.rand.nextFloat() * super.worldObj.rand.nextFloat() + 0.3F;
					d3 *= d7;
					d4 *= d7;
					d5 *= d7;
					super.worldObj.spawnParticle("explode", (effectX + super.explosionX) / 2D, (effectY + super.explosionY) / 2D, (effectZ + super.explosionZ) / 2D, d3, d4, d5);
					super.worldObj.spawnParticle("smoke", effectX, effectY, effectZ, d3, d4, d5);
					Block block = Block.blocksList[blockId];
					int meta = super.worldObj.getBlockMetadata(x, y, z);
					Iterator i$ = block.getBlockDropped(super.worldObj, x, y, z, meta, 0).iterator();
					do
					{
						if (!i$.hasNext())
							break;
						ItemStack itemStack = (ItemStack)i$.next();
						if (super.worldObj.rand.nextFloat() <= explosionDropRate)
						{
							XZposition xZposition = new XZposition(x / 2, z / 2);
							if (!blocksToDrop.containsKey(xZposition))
								blocksToDrop.put(xZposition, new HashMap());
							Map map = (Map)blocksToDrop.get(xZposition);
							ItemWithMeta itemWithMeta = new ItemWithMeta(itemStack.itemID, itemStack.getItemDamage());
							if (!map.containsKey(itemWithMeta))
								map.put(itemWithMeta, new DropData(itemStack.stackSize, y));
							else
								map.put(itemWithMeta, ((DropData)map.get(itemWithMeta)).add(itemStack.stackSize, y));
						}
					} while (true);
				}
				super.worldObj.setBlock(x, y, z, 0, 0, 7);
				Block.blocksList[blockId].onBlockDestroyedByExplosion(super.worldObj, x, y, z, this);
			}
		} while (true);
		for (i$ = blocksToDrop.entrySet().iterator(); i$.hasNext();)
		{
			java.util.Map.Entry entry = (java.util.Map.Entry)i$.next();
			XZposition xZposition = (XZposition)entry.getKey();
			Iterator i$ = ((Map)entry.getValue()).entrySet().iterator();
			while (i$.hasNext()) 
			{
				java.util.Map.Entry entry2 = (java.util.Map.Entry)i$.next();
				ItemWithMeta itemWithMeta = (ItemWithMeta)entry2.getKey();
				int count = ((DropData)entry2.getValue()).n;
				while (count > 0) 
				{
					int stackSize = Math.min(count, 64);
					EntityItem entityitem = new EntityItem(super.worldObj, (double)((float)xZposition.x + super.worldObj.rand.nextFloat()) * 2D, (double)((DropData)entry2.getValue()).maxY + 0.5D, (double)((float)xZposition.z + super.worldObj.rand.nextFloat()) * 2D, new ItemStack(itemWithMeta.itemId, stackSize, itemWithMeta.metaData));
					entityitem.delayBeforeCanPickup = 10;
					super.worldObj.spawnEntityInWorld(entityitem);
					count -= stackSize;
				}
			}
		}

	}

	private void shootRay(double x, double y, double z, double phi, double theta, double power, boolean killEntities)
	{
		double deltaX = Math.sin(theta) * Math.cos(phi);
		double deltaY = Math.cos(theta);
		double deltaZ = Math.sin(theta) * Math.sin(phi);
		int step = 0;
		do
		{
			int blockY = Util.roundToNegInf(y);
			if (blockY < 0 || blockY >= mapHeight)
				break;
			int blockX = Util.roundToNegInf(x);
			int blockZ = Util.roundToNegInf(z);
			int blockId = chunkCache.getBlockId(blockX, blockY, blockZ);
			double absorption = 0.5D;
			if (blockId > 0)
				absorption += ((double)Block.blocksList[blockId].getExplosionResistance(super.exploder, super.worldObj, blockX, blockY, blockZ, super.explosionX, super.explosionY, super.explosionZ) + 4D) * 0.29999999999999999D;
			if (absorption > 1000D && !ExplosionWhitelist.isBlockWhitelisted(Block.blocksList[blockId]))
			{
				absorption = 0.5D;
			} else
			{
				if (absorption > power)
					break;
				if (blockId > 0)
				{
					ChunkPosition position = new ChunkPosition(blockX, blockY, blockZ);
					if (power > 8D)
						destroyedBlockPositions.put(position, Boolean.valueOf(false));
					else
					if (!destroyedBlockPositions.containsKey(position))
						destroyedBlockPositions.put(position, Boolean.valueOf(true));
				}
			}
			if (killEntities && (step + 4) % 8 == 0 && power >= 0.25D)
			{
				int index;
				if (step != 4)
				{
					int distanceMin = step * step - 25;
					int indexStart = 0;
					int indexEnd = entitiesInRange.size() - 1;
					do
					{
						index = (indexStart + indexEnd) / 2;
						int distance = ((Integer)((java.util.Map.Entry)entitiesInRange.get(index)).getKey()).intValue();
						if (distance < distanceMin)
							indexStart = index + 1;
						else
						if (distance > distanceMin)
							indexEnd = index - 1;
						else
							indexEnd = index;
					} while (indexStart < indexEnd);
				} else
				{
					index = 0;
				}
				int distanceMax = step * step + 25;
				for (int i = index; i < entitiesInRange.size() && ((Integer)((java.util.Map.Entry)entitiesInRange.get(index)).getKey()).intValue() < distanceMax; i++)
				{
					Entity entity = (Entity)((java.util.Map.Entry)entitiesInRange.get(index)).getValue();
					if ((entity.posX - x) * (entity.posX - x) + (entity.posY - y) * (entity.posY - y) + (entity.posZ - z) * (entity.posZ - z) > 25D)
						continue;
					entity.attackEntityFrom(getDamageSource(), (int)((32D * power) / 8D));
					if (entity instanceof EntityPlayer)
					{
						EntityPlayer entityPlayer = (EntityPlayer)entity;
						if (isNuclear() && igniter != null && entityPlayer.username.equals(igniter) && entityPlayer.func_110143_aJ() <= 0.0F)
							IC2.achievements.issueAchievement(entityPlayer, "dieFromOwnNuke");
					}
					double dx = entity.posX - super.explosionX;
					double dy = entity.posY - super.explosionY;
					double dz = entity.posZ - super.explosionZ;
					double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
					entity.motionX += ((dx / distance) * 0.69999999999999996D * power) / 8D;
					entity.motionY += ((dy / distance) * 0.69999999999999996D * power) / 8D;
					entity.motionZ += ((dz / distance) * 0.69999999999999996D * power) / 8D;
					if (!entity.isEntityAlive())
					{
						entitiesInRange.remove(i);
						i--;
					}
				}

			}
			if (absorption > 10D)
			{
				for (int i = 0; i < 5; i++)
					shootRay(x, y, z, ExplosionRNG.nextDouble() * 2D * 3.1415926535897931D, ExplosionRNG.nextDouble() * 3.1415926535897931D, absorption * 0.40000000000000002D, false);

			}
			power -= absorption;
			x += deltaX;
			y += deltaY;
			z += deltaZ;
			step++;
		} while (true);
	}

	public EntityLivingBase func_94613_c()
	{
		return igniter;
	}

	private DamageSource getDamageSource()
	{
		if (isNuclear())
			return IC2DamageSource.setNukeSource(this);
		else
			return DamageSource.setExplosionSource(this);
	}

	private boolean isNuclear()
	{
		return nuclear || (super.exploder instanceof EntityNuke);
	}
}
