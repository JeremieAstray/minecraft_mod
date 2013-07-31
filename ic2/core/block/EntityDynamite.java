// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EntityDynamite.java

package ic2.core.block;

import ic2.core.*;
import java.util.Random;
import net.minecraft.entity.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class EntityDynamite extends Entity
	implements IProjectile
{

	public boolean sticky;
	public static final int netId = 142;
	public int stickX;
	public int stickY;
	public int stickZ;
	public int fuse;
	private boolean inGround;
	public EntityLivingBase owner;
	private int ticksInGround;

	public EntityDynamite(World world, double x, double y, double z)
	{
		super(world);
		sticky = false;
		fuse = 100;
		inGround = false;
		setSize(0.5F, 0.5F);
		setPosition(x, y, z);
		super.yOffset = 0.0F;
	}

	public EntityDynamite(World world, double x, double y, double z, 
			boolean sticky)
	{
		this(world, x, y, z);
		this.sticky = sticky;
	}

	public EntityDynamite(World world)
	{
		this(world, 0.0D, 0.0D, 0.0D);
	}

	public EntityDynamite(World world, EntityLivingBase entityliving)
	{
		super(world);
		sticky = false;
		fuse = 100;
		inGround = false;
		owner = entityliving;
		setSize(0.5F, 0.5F);
		setLocationAndAngles(((Entity) (entityliving)).posX, ((Entity) (entityliving)).posY + (double)entityliving.getEyeHeight(), ((Entity) (entityliving)).posZ, ((Entity) (entityliving)).rotationYaw, ((Entity) (entityliving)).rotationPitch);
		super.posX -= MathHelper.cos((super.rotationYaw / 180F) * 3.141593F) * 0.16F;
		super.posY -= 0.10000000149011612D;
		super.posZ -= MathHelper.sin((super.rotationYaw / 180F) * 3.141593F) * 0.16F;
		setPosition(super.posX, super.posY, super.posZ);
		super.yOffset = 0.0F;
		super.motionX = -MathHelper.sin((super.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((super.rotationPitch / 180F) * 3.141593F);
		super.motionZ = MathHelper.cos((super.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((super.rotationPitch / 180F) * 3.141593F);
		super.motionY = -MathHelper.sin((super.rotationPitch / 180F) * 3.141593F);
		setThrowableHeading(super.motionX, super.motionY, super.motionZ, 1.0F, 1.0F);
	}

	protected void entityInit()
	{
	}

	public void setThrowableHeading(double d, double d1, double d2, float f, 
			float f1)
	{
		float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
		d /= f2;
		d1 /= f2;
		d2 /= f2;
		d += super.rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
		d1 += super.rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
		d2 += super.rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
		d *= f;
		d1 *= f;
		d2 *= f;
		super.motionX = d;
		super.motionY = d1;
		super.motionZ = d2;
		float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
		super.prevRotationYaw = super.rotationYaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
		super.prevRotationPitch = super.rotationPitch = (float)((Math.atan2(d1, f3) * 180D) / 3.1415927410125732D);
		ticksInGround = 0;
	}

	public void setVelocity(double d, double d1, double d2)
	{
		super.motionX = d;
		super.motionY = d1;
		super.motionZ = d2;
		if (super.prevRotationPitch == 0.0F && super.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt_double(d * d + d2 * d2);
			super.prevRotationYaw = super.rotationYaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
			super.prevRotationPitch = super.rotationPitch = (float)((Math.atan2(d1, f) * 180D) / 3.1415927410125732D);
			super.prevRotationPitch = super.rotationPitch;
			super.prevRotationYaw = super.rotationYaw;
			setLocationAndAngles(super.posX, super.posY, super.posZ, super.rotationYaw, super.rotationPitch);
			ticksInGround = 0;
		}
	}

	public void onUpdate()
	{
		super.onUpdate();
		if (super.prevRotationPitch == 0.0F && super.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionZ * super.motionZ);
			super.prevRotationYaw = super.rotationYaw = (float)((Math.atan2(super.motionX, super.motionZ) * 180D) / 3.1415927410125732D);
			super.prevRotationPitch = super.rotationPitch = (float)((Math.atan2(super.motionY, f) * 180D) / 3.1415927410125732D);
		}
		if (fuse-- <= 0)
		{
			if (IC2.platform.isSimulating())
			{
				setDead();
				explode();
			} else
			{
				setDead();
			}
		} else
		if (fuse < 100 && fuse % 2 == 0)
			super.worldObj.spawnParticle("smoke", super.posX, super.posY + 0.5D, super.posZ, 0.0D, 0.0D, 0.0D);
		if (inGround)
		{
			ticksInGround++;
			if (ticksInGround >= 200)
				setDead();
			if (sticky)
			{
				fuse -= 3;
				super.motionX = 0.0D;
				super.motionY = 0.0D;
				super.motionZ = 0.0D;
				if (super.worldObj.getBlockId(stickX, stickY, stickZ) != 0)
					return;
			}
		}
		Vec3 vec3d = Vec3.createVectorHelper(super.posX, super.posY, super.posZ);
		Vec3 vec3d1 = Vec3.createVectorHelper(super.posX + super.motionX, super.posY + super.motionY, super.posZ + super.motionZ);
		MovingObjectPosition movingobjectposition = super.worldObj.rayTraceBlocks_do_do(vec3d, vec3d1, false, true);
		vec3d = Vec3.createVectorHelper(super.posX, super.posY, super.posZ);
		vec3d1 = Vec3.createVectorHelper(super.posX + super.motionX, super.posY + super.motionY, super.posZ + super.motionZ);
		if (movingobjectposition != null)
		{
			vec3d1 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
			float remainX = (float)(movingobjectposition.hitVec.xCoord - super.posX);
			float remainY = (float)(movingobjectposition.hitVec.yCoord - super.posY);
			float remainZ = (float)(movingobjectposition.hitVec.zCoord - super.posZ);
			float f1 = MathHelper.sqrt_double(remainX * remainX + remainY * remainY + remainZ * remainZ);
			stickX = movingobjectposition.blockX;
			stickY = movingobjectposition.blockY;
			stickZ = movingobjectposition.blockZ;
			super.posX -= ((double)remainX / (double)f1) * 0.05000000074505806D;
			super.posY -= ((double)remainY / (double)f1) * 0.05000000074505806D;
			super.posZ -= ((double)remainZ / (double)f1) * 0.05000000074505806D;
			super.posX += remainX;
			super.posY += remainY;
			super.posZ += remainZ;
			super.motionX *= 0.75F - super.rand.nextFloat();
			super.motionY *= -0.30000001192092896D;
			super.motionZ *= 0.75F - super.rand.nextFloat();
			inGround = true;
		} else
		{
			super.posX += super.motionX;
			super.posY += super.motionY;
			super.posZ += super.motionZ;
			inGround = false;
		}
		float f2 = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionZ * super.motionZ);
		super.rotationYaw = (float)((Math.atan2(super.motionX, super.motionZ) * 180D) / 3.1415927410125732D);
		for (super.rotationPitch = (float)((Math.atan2(super.motionY, f2) * 180D) / 3.1415927410125732D); super.rotationPitch - super.prevRotationPitch < -180F; super.prevRotationPitch -= 360F);
		for (; super.rotationPitch - super.prevRotationPitch >= 180F; super.prevRotationPitch += 360F);
		for (; super.rotationYaw - super.prevRotationYaw < -180F; super.prevRotationYaw -= 360F);
		for (; super.rotationYaw - super.prevRotationYaw >= 180F; super.prevRotationYaw += 360F);
		super.rotationPitch = super.prevRotationPitch + (super.rotationPitch - super.prevRotationPitch) * 0.2F;
		super.rotationYaw = super.prevRotationYaw + (super.rotationYaw - super.prevRotationYaw) * 0.2F;
		float f3 = 0.98F;
		float f5 = 0.04F;
		if (isInWater())
		{
			fuse += 2000;
			for (int i1 = 0; i1 < 4; i1++)
			{
				float f6 = 0.25F;
				super.worldObj.spawnParticle("bubble", super.posX - super.motionX * (double)f6, super.posY - super.motionY * (double)f6, super.posZ - super.motionZ * (double)f6, super.motionX, super.motionY, super.motionZ);
			}

			f3 = 0.75F;
		}
		super.motionX *= f3;
		super.motionY *= f3;
		super.motionZ *= f3;
		super.motionY -= f5;
		setPosition(super.posX, super.posY, super.posZ);
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound.setByte("inGround", (byte)(inGround ? 1 : 0));
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		inGround = nbttagcompound.getByte("inGround") == 1;
	}

	public float getShadowSize()
	{
		return 0.0F;
	}

	public void explode()
	{
		PointExplosion explosion = new PointExplosion(super.worldObj, owner, super.posX, super.posY, super.posZ, 1.0F, 1.0F, 20);
		explosion.doExplosionA();
		explosion.doExplosionB(true);
	}
}
