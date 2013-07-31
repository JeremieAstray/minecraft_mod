// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EntityMiningLaser.java

package ic2.core.item.tool;

import cpw.mods.fml.common.registry.IThrowableEntity;
import ic2.core.*;
import java.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class EntityMiningLaser extends Entity
	implements IThrowableEntity
{

	public float range;
	public float power;
	public int blockBreaks;
	public boolean explosive;
	public static Block unmineableBlocks[];
	public static final int netId = 141;
	public static final double laserSpeed = 1D;
	public EntityLivingBase owner;
	public boolean headingSet;
	public boolean smelt;
	private int ticksInAir;

	public EntityMiningLaser(World world, double x, double y, double z)
	{
		super(world);
		range = 0.0F;
		power = 0.0F;
		blockBreaks = 0;
		explosive = false;
		headingSet = false;
		smelt = false;
		ticksInAir = 0;
		setSize(0.8F, 0.8F);
		super.yOffset = 0.0F;
		setPosition(x, y, z);
	}

	public EntityMiningLaser(World world)
	{
		this(world, 0.0D, 0.0D, 0.0D);
	}

	public EntityMiningLaser(World world, EntityLivingBase entityliving, float range, float power, int blockBreaks, boolean explosive)
	{
		this(world, entityliving, range, power, blockBreaks, explosive, ((Entity) (entityliving)).rotationYaw, ((Entity) (entityliving)).rotationPitch);
	}

	public EntityMiningLaser(World world, EntityLivingBase entityliving, float range, float power, int blockBreaks, boolean explosive, boolean smelt)
	{
		this(world, entityliving, range, power, blockBreaks, explosive, ((Entity) (entityliving)).rotationYaw, ((Entity) (entityliving)).rotationPitch);
		this.smelt = smelt;
	}

	public EntityMiningLaser(World world, EntityLivingBase entityliving, float range, float power, int blockBreaks, boolean explosive, double yawDeg, double pitchDeg)
	{
		this(world, entityliving, range, power, blockBreaks, explosive, yawDeg, pitchDeg, (((Entity) (entityliving)).posY + (double)entityliving.getEyeHeight()) - 0.10000000000000001D);
	}

	public EntityMiningLaser(World world, EntityLivingBase entityliving, float range, float power, int blockBreaks, boolean explosive, double yawDeg, double pitchDeg, double y)
	{
		super(world);
		this.range = 0.0F;
		this.power = 0.0F;
		this.blockBreaks = 0;
		this.explosive = false;
		headingSet = false;
		smelt = false;
		ticksInAir = 0;
		owner = entityliving;
		setSize(0.8F, 0.8F);
		super.yOffset = 0.0F;
		double yaw = Math.toRadians(yawDeg);
		double pitch = Math.toRadians(pitchDeg);
		double x = ((Entity) (entityliving)).posX - Math.cos(yaw) * 0.16D;
		double z = ((Entity) (entityliving)).posZ - Math.sin(yaw) * 0.16D;
		double startMotionX = -Math.sin(yaw) * Math.cos(pitch);
		double startMotionY = -Math.sin(pitch);
		double startMotionZ = Math.cos(yaw) * Math.cos(pitch);
		setPosition(x, y, z);
		setLaserHeading(startMotionX, startMotionY, startMotionZ, 1.0D);
		this.range = range;
		this.power = power;
		this.blockBreaks = blockBreaks;
		this.explosive = explosive;
	}

	protected void entityInit()
	{
	}

	public void setLaserHeading(double motionX, double motionY, double motionZ, double speed)
	{
		double currentSpeed = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
		super.motionX = (motionX / currentSpeed) * speed;
		super.motionY = (motionY / currentSpeed) * speed;
		super.motionZ = (motionZ / currentSpeed) * speed;
		super.prevRotationYaw = super.rotationYaw = (float)Math.toDegrees(Math.atan2(motionX, motionZ));
		super.prevRotationPitch = super.rotationPitch = (float)Math.toDegrees(Math.atan2(motionY, MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ)));
		headingSet = true;
	}

	public void setVelocity(double motionX, double motionY, double motionZ)
	{
		setLaserHeading(motionX, motionY, motionZ, 1.0D);
	}

	public void onUpdate()
	{
		super.onUpdate();
		if (IC2.platform.isSimulating() && (range < 1.0F || power <= 0.0F || blockBreaks <= 0))
		{
			if (explosive)
				explode();
			setDead();
			return;
		}
		ticksInAir++;
		Vec3 oldPosition = Vec3.createVectorHelper(super.posX, super.posY, super.posZ);
		Vec3 newPosition = Vec3.createVectorHelper(super.posX + super.motionX, super.posY + super.motionY, super.posZ + super.motionZ);
		MovingObjectPosition movingobjectposition = super.worldObj.rayTraceBlocks_do_do(oldPosition, newPosition, false, true);
		oldPosition = Vec3.createVectorHelper(super.posX, super.posY, super.posZ);
		if (movingobjectposition != null)
			newPosition = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
		else
			newPosition = Vec3.createVectorHelper(super.posX + super.motionX, super.posY + super.motionY, super.posZ + super.motionZ);
		Entity entity = null;
		List list = super.worldObj.getEntitiesWithinAABBExcludingEntity(this, super.boundingBox.addCoord(super.motionX, super.motionY, super.motionZ).expand(1.0D, 1.0D, 1.0D));
		double d = 0.0D;
		for (int l = 0; l < list.size(); l++)
		{
			Entity entity1 = (Entity)list.get(l);
			if (!entity1.canBeCollidedWith() || entity1 == owner && ticksInAir < 5)
				continue;
			float f4 = 0.3F;
			AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(f4, f4, f4);
			MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(oldPosition, newPosition);
			if (movingobjectposition1 == null)
				continue;
			double d1 = oldPosition.distanceTo(movingobjectposition1.hitVec);
			if (d1 < d || d == 0.0D)
			{
				entity = entity1;
				d = d1;
			}
		}

		if (entity != null)
			movingobjectposition = new MovingObjectPosition(entity);
		if (movingobjectposition != null && IC2.platform.isSimulating())
		{
			if (explosive)
			{
				explode();
				setDead();
				return;
			}
			if (movingobjectposition.entityHit != null)
			{
				int damage = (int)power;
				if (damage > 0)
				{
					entity.setFire(damage * (smelt ? 2 : 1));
					if (movingobjectposition.entityHit.attackEntityFrom((new EntityDamageSourceIndirect("arrow", this, owner)).setProjectile(), damage) && (owner instanceof EntityPlayer) && ((movingobjectposition.entityHit instanceof EntityDragon) && ((EntityDragon)movingobjectposition.entityHit).func_110143_aJ() <= 0.0F || (movingobjectposition.entityHit instanceof EntityDragonPart) && (((EntityDragonPart)movingobjectposition.entityHit).entityDragonObj instanceof EntityDragon) && ((EntityLivingBase)((EntityDragonPart)movingobjectposition.entityHit).entityDragonObj).func_110143_aJ() <= 0.0F))
						IC2.achievements.issueAchievement((EntityPlayer)owner, "killDragonMiningLaser");
				}
				setDead();
			} else
			{
				int xTile = movingobjectposition.blockX;
				int yTile = movingobjectposition.blockY;
				int zTile = movingobjectposition.blockZ;
				int blockId = super.worldObj.getBlockId(xTile, yTile, zTile);
				int blockMeta = super.worldObj.getBlockMetadata(xTile, yTile, zTile);
				boolean blockSet = false;
				boolean removeBlock = true;
				boolean dropBlock = true;
				if (!canMine(blockId))
					setDead();
				else
				if (IC2.platform.isSimulating())
				{
					float resis = 0.0F;
					resis = Block.blocksList[blockId].getExplosionResistance(this, super.worldObj, xTile, yTile, zTile, super.posX, super.posY, super.posZ) + 0.3F;
					power -= resis / 10F;
					if (power >= 0.0F)
					{
						if (Block.blocksList[blockId].blockMaterial == Material.tnt)
							Block.blocksList[blockId].onBlockDestroyedByPlayer(super.worldObj, xTile, yTile, zTile, 1);
						else
						if (smelt)
							if (Block.blocksList[blockId].blockMaterial == Material.wood)
							{
								removeBlock = true;
								dropBlock = false;
							} else
							{
								Iterator i$ = Block.blocksList[blockId].getBlockDropped(super.worldObj, xTile, yTile, zTile, blockMeta, 0).iterator();
								do
								{
									if (!i$.hasNext())
										break;
									ItemStack isa = (ItemStack)i$.next();
									ItemStack isb = FurnaceRecipes.smelting().getSmeltingResult(isa);
									if (isb != null)
									{
										ItemStack is = isb.copy();
										if (!blockSet && is.itemID != blockId && is.itemID < Block.blocksList.length && Block.blocksList[is.itemID] != null && Block.blocksList[is.itemID].blockID != 0)
										{
											blockSet = true;
											removeBlock = false;
											dropBlock = false;
											super.worldObj.setBlock(xTile, yTile, zTile, is.itemID, is.getItemDamage(), 7);
										} else
										{
											dropBlock = false;
											float var6 = 0.7F;
											double var7 = (double)(super.worldObj.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
											double var9 = (double)(super.worldObj.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
											double var11 = (double)(super.worldObj.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
											EntityItem var13 = new EntityItem(super.worldObj, (double)xTile + var7, (double)yTile + var9, (double)zTile + var11, is);
											var13.delayBeforeCanPickup = 10;
											super.worldObj.spawnEntityInWorld(var13);
										}
										power = 0.0F;
									}
								} while (true);
							}
						if (removeBlock)
						{
							if (dropBlock)
								Block.blocksList[blockId].dropBlockAsItemWithChance(super.worldObj, xTile, yTile, zTile, super.worldObj.getBlockMetadata(xTile, yTile, zTile), 0.9F, 0);
							super.worldObj.setBlock(xTile, yTile, zTile, 0, 0, 7);
							if (super.worldObj.rand.nextInt(10) == 0 && Block.blocksList[blockId].blockMaterial.getCanBurn())
								super.worldObj.setBlock(xTile, yTile, zTile, ((Block) (Block.fire)).blockID, 0, 7);
						}
						blockBreaks--;
					}
				}
			}
		} else
		{
			power -= 0.5F;
		}
		setPosition(super.posX + super.motionX, super.posY + super.motionY, super.posZ + super.motionZ);
		range -= Math.sqrt(super.motionX * super.motionX + super.motionY * super.motionY + super.motionZ * super.motionZ);
		if (isInWater())
			setDead();
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound1)
	{
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound1)
	{
	}

	public float getShadowSize()
	{
		return 0.0F;
	}

	public void explode()
	{
		if (IC2.platform.isSimulating())
		{
			ExplosionIC2 explosion = new ExplosionIC2(super.worldObj, null, super.posX, super.posY, super.posZ, 5F, 0.85F, 0.55F);
			explosion.doExplosion();
		}
	}

	public boolean canMine(int blockId)
	{
		for (int i = 0; i < unmineableBlocks.length; i++)
			if (blockId == unmineableBlocks[i].blockID)
				return false;

		return true;
	}

	public Entity getThrower()
	{
		return owner;
	}

	public void setThrower(Entity entity)
	{
		if (entity instanceof EntityLivingBase)
			owner = (EntityLivingBase)entity;
	}

	static 
	{
		unmineableBlocks = (new Block[] {
			Block.brick, Block.obsidian, Block.lavaMoving, Block.lavaStill, Block.waterMoving, Block.waterStill, Block.bedrock, Block.glass
		});
	}
}
