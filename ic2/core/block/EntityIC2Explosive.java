// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EntityIC2Explosive.java

package ic2.core.block;

import ic2.core.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityIC2Explosive extends Entity
{

	public DamageSource damageSource;
	public EntityLivingBase igniter;
	public int fuse;
	public float explosivePower;
	public float dropRate;
	public float damageVsEntitys;
	public Block renderBlock;

	public EntityIC2Explosive(World world)
	{
		super(world);
		fuse = 80;
		explosivePower = 4F;
		dropRate = 0.3F;
		damageVsEntitys = 1.0F;
		renderBlock = Block.dirt;
		super.preventEntitySpawning = true;
		setSize(0.98F, 0.98F);
		super.yOffset = super.height / 2.0F;
	}

	public EntityIC2Explosive(World world, double d, double d1, double d2, 
			int fuselength, float power, float rate, float damage, Block block)
	{
		this(world);
		setPosition(d, d1, d2);
		float f = (float)(Math.random() * 3.1415927410125732D * 2D);
		super.motionX = -MathHelper.sin((f * 3.141593F) / 180F) * 0.02F;
		super.motionY = 0.20000000298023224D;
		super.motionZ = -MathHelper.cos((f * 3.141593F) / 180F) * 0.02F;
		super.prevPosX = d;
		super.prevPosY = d1;
		super.prevPosZ = d2;
		fuse = fuselength;
		explosivePower = power;
		dropRate = rate;
		damageVsEntitys = damage;
		renderBlock = block;
	}

	protected void entityInit()
	{
	}

	protected boolean canTriggerWalking()
	{
		return false;
	}

	public boolean canBeCollidedWith()
	{
		return !super.isDead;
	}

	public void onUpdate()
	{
		super.prevPosX = super.posX;
		super.prevPosY = super.posY;
		super.prevPosZ = super.posZ;
		super.motionY -= 0.040000000000000001D;
		moveEntity(super.motionX, super.motionY, super.motionZ);
		super.motionX *= 0.97999999999999998D;
		super.motionY *= 0.97999999999999998D;
		super.motionZ *= 0.97999999999999998D;
		if (super.onGround)
		{
			super.motionX *= 0.69999999999999996D;
			super.motionZ *= 0.69999999999999996D;
			super.motionY *= -0.5D;
		}
		if (fuse-- <= 0)
		{
			setDead();
			if (IC2.platform.isSimulating())
				explode();
		} else
		{
			super.worldObj.spawnParticle("smoke", super.posX, super.posY + 0.5D, super.posZ, 0.0D, 0.0D, 0.0D);
		}
	}

	private void explode()
	{
		ExplosionIC2 explosion = new ExplosionIC2(super.worldObj, this, super.posX, super.posY, super.posZ, explosivePower, dropRate, damageVsEntitys, igniter);
		explosion.doExplosion();
	}

	protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound.setByte("Fuse", (byte)fuse);
	}

	protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		fuse = nbttagcompound.getByte("Fuse");
	}

	public float getShadowSize()
	{
		return 0.0F;
	}

	public EntityIC2Explosive setIgniter(EntityLivingBase igniter)
	{
		this.igniter = igniter;
		return this;
	}
}
