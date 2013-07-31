// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EntityIC2Boat.java

package ic2.core.item;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public abstract class EntityIC2Boat extends EntityBoat
{

	private boolean field_70279_a;
	private double field_70276_b;
	private int field_70277_c;
	private double field_70274_d;
	private double field_70275_e;
	private double field_70272_f;
	private double field_70273_g;
	private double field_70281_h;
	private double field_70282_i;
	private double field_70280_j;
	private double field_70278_an;

	public EntityIC2Boat(World par1World)
	{
		super(par1World);
		field_70279_a = true;
		super.speedMultiplier = 0.070000000000000007D;
	}

	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if (isEntityInvulnerable())
			return false;
		if (!super.worldObj.isRemote && !super.isDead)
		{
			setForwardDirection(-getForwardDirection());
			setTimeSinceHit(10);
			setDamageTaken(getDamageTaken() + par2 * 10F);
			setBeenAttacked();
			boolean flag = (par1DamageSource.getEntity() instanceof EntityPlayer) && ((EntityPlayer)par1DamageSource.getEntity()).capabilities.isCreativeMode;
			if (flag || getDamageTaken() > 40F)
			{
				if (super.riddenByEntity != null)
					super.riddenByEntity.mountEntity(this);
				if (!flag)
					dropItem(par1DamageSource);
				setDead();
			}
			return true;
		} else
		{
			return true;
		}
	}

	public void onUpdate()
	{
		onEntityUpdate();
		if (getTimeSinceHit() > 0)
			setTimeSinceHit(getTimeSinceHit() - 1);
		if (getDamageTaken() > 0.0F)
			setDamageTaken(getDamageTaken() - 1.0F);
		super.prevPosX = super.posX;
		super.prevPosY = super.posY;
		super.prevPosZ = super.posZ;
		byte b0 = 5;
		double d0 = 0.0D;
		for (int i = 0; i < b0; i++)
		{
			double d1 = (super.boundingBox.minY + ((super.boundingBox.maxY - super.boundingBox.minY) * (double)(i + 0)) / (double)b0) - 0.125D;
			double d2 = (super.boundingBox.minY + ((super.boundingBox.maxY - super.boundingBox.minY) * (double)(i + 1)) / (double)b0) - 0.125D;
			AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool().getAABB(super.boundingBox.minX, d1, super.boundingBox.minZ, super.boundingBox.maxX, d2, super.boundingBox.maxZ);
			if (isOnWater(axisalignedbb))
				d0 += 1.0D / (double)b0;
		}

		double d3 = Math.sqrt(super.motionX * super.motionX + super.motionZ * super.motionZ);
		if (d3 > 0.26249999999999996D)
		{
			double d4 = Math.cos(((double)super.rotationYaw * 3.1415926535897931D) / 180D);
			double d5 = Math.sin(((double)super.rotationYaw * 3.1415926535897931D) / 180D);
			for (int j = 0; (double)j < 1.0D + d3 * 60D; j++)
			{
				double d6 = super.rand.nextFloat() * 2.0F - 1.0F;
				double d7 = (double)(super.rand.nextInt(2) * 2 - 1) * 0.69999999999999996D;
				if (super.rand.nextBoolean())
				{
					double d8 = (super.posX - d4 * d6 * 0.80000000000000004D) + d5 * d7;
					double d9 = super.posZ - d5 * d6 * 0.80000000000000004D - d4 * d7;
					super.worldObj.spawnParticle("splash", d8, super.posY - 0.125D, d9, super.motionX, super.motionY, super.motionZ);
				} else
				{
					double d8 = super.posX + d4 + d5 * d6 * 0.69999999999999996D;
					double d9 = (super.posZ + d5) - d4 * d6 * 0.69999999999999996D;
					super.worldObj.spawnParticle("splash", d8, super.posY - 0.125D, d9, super.motionX, super.motionY, super.motionZ);
				}
			}

		}
		if (super.worldObj.isRemote && field_70279_a)
		{
			if (super.boatPosRotationIncrements > 0)
			{
				double d4 = super.posX + (super.boatX - super.posX) / (double)super.boatPosRotationIncrements;
				double d5 = super.posY + (super.boatY - super.posY) / (double)super.boatPosRotationIncrements;
				double d11 = super.posZ + (super.boatZ - super.posZ) / (double)super.boatPosRotationIncrements;
				double d10 = MathHelper.wrapAngleTo180_double(super.boatYaw - (double)super.rotationYaw);
				super.rotationYaw = (float)((double)super.rotationYaw + d10 / (double)super.boatPosRotationIncrements);
				super.rotationPitch = (float)((double)super.rotationPitch + (super.boatPitch - (double)super.rotationPitch) / (double)super.boatPosRotationIncrements);
				super.boatPosRotationIncrements--;
				setPosition(d4, d5, d11);
				setRotation(super.rotationYaw, super.rotationPitch);
			} else
			{
				double d4 = super.posX + super.motionX;
				double d5 = super.posY + super.motionY;
				double d11 = super.posZ + super.motionZ;
				setPosition(d4, d5, d11);
				if (super.onGround)
				{
					super.motionX *= 0.5D;
					super.motionY *= 0.5D;
					super.motionZ *= 0.5D;
				}
				super.motionX *= 0.99000000953674316D;
				super.motionY *= 0.94999998807907104D;
				super.motionZ *= 0.99000000953674316D;
			}
		} else
		{
			double d4;
			if (d0 < 1.0D)
			{
				d4 = d0 * 2D - 1.0D;
				super.motionY += 0.039999999105930328D * d4;
			} else
			{
				if (super.motionY < 0.0D)
					super.motionY /= 2D;
				super.motionY += 0.0070000002160668373D;
			}
			if (super.riddenByEntity != null)
			{
				super.motionX += super.riddenByEntity.motionX * super.speedMultiplier * getAccelerationFactor();
				super.motionZ += super.riddenByEntity.motionZ * super.speedMultiplier * getAccelerationFactor();
			}
			d4 = Math.sqrt(super.motionX * super.motionX + super.motionZ * super.motionZ);
			double topSpeed = getTopSpeed();
			double d5;
			if (d4 > topSpeed)
			{
				d5 = topSpeed / d4;
				super.motionX *= d5;
				super.motionZ *= d5;
				d4 = topSpeed;
			}
			if (d4 > d3 && super.speedMultiplier < 0.34999999999999998D)
			{
				super.speedMultiplier += (0.34999999999999998D - super.speedMultiplier) / 35D;
				if (super.speedMultiplier > 0.34999999999999998D)
					super.speedMultiplier = 0.34999999999999998D;
			} else
			{
				super.speedMultiplier -= (super.speedMultiplier - 0.070000000000000007D) / 35D;
				if (super.speedMultiplier < 0.070000000000000007D)
					super.speedMultiplier = 0.070000000000000007D;
			}
			if (super.onGround)
			{
				super.motionX *= 0.5D;
				super.motionY *= 0.5D;
				super.motionZ *= 0.5D;
			}
			moveEntity(super.motionX, super.motionY, super.motionZ);
			if (super.isCollidedHorizontally && d3 > getBreakMotion())
			{
				if (!super.worldObj.isRemote)
				{
					setDead();
					breakBoat(d3);
				}
			} else
			{
				super.motionX *= 0.99000000953674316D;
				super.motionY *= 0.94999998807907104D;
				super.motionZ *= 0.99000000953674316D;
			}
			super.rotationPitch = 0.0F;
			d5 = super.rotationYaw;
			double d11 = super.prevPosX - super.posX;
			double d10 = super.prevPosZ - super.posZ;
			if (d11 * d11 + d10 * d10 > 0.001D)
				d5 = (float)((Math.atan2(d10, d11) * 180D) / 3.1415926535897931D);
			double d12 = MathHelper.wrapAngleTo180_double(d5 - (double)super.rotationYaw);
			if (d12 > 20D)
				d12 = 20D;
			if (d12 < -20D)
				d12 = -20D;
			super.rotationYaw = (float)((double)super.rotationYaw + d12);
			setRotation(super.rotationYaw, super.rotationPitch);
			if (!super.worldObj.isRemote)
			{
				List list = super.worldObj.getEntitiesWithinAABBExcludingEntity(this, super.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
				if (list != null && !list.isEmpty())
				{
					for (int l = 0; l < list.size(); l++)
					{
						Entity entity = (Entity)list.get(l);
						if (entity != super.riddenByEntity && entity.canBePushed() && (entity instanceof EntityBoat))
							entity.applyEntityCollision(this);
					}

				}
				for (int l = 0; l < 4; l++)
				{
					int i1 = MathHelper.floor_double(super.posX + ((double)(l % 2) - 0.5D) * 0.80000000000000004D);
					int j1 = MathHelper.floor_double(super.posZ + ((double)(l / 2) - 0.5D) * 0.80000000000000004D);
					for (int k1 = 0; k1 < 2; k1++)
					{
						int l1 = MathHelper.floor_double(super.posY) + k1;
						int i2 = super.worldObj.getBlockId(i1, l1, j1);
						if (i2 == Block.snow.blockID)
						{
							super.worldObj.setBlockToAir(i1, l1, j1);
							continue;
						}
						if (i2 == Block.waterlily.blockID)
							super.worldObj.destroyBlock(i1, l1, j1, true);
					}

				}

				if (super.riddenByEntity != null && super.riddenByEntity.isDead)
					super.riddenByEntity = null;
			}
		}
	}

	public void func_70270_d(boolean par1)
	{
		field_70279_a = par1;
	}

	public void setPositionAndRotation2(double par1, double par3, double par5, float par7, 
			float par8, int par9)
	{
		if (field_70279_a)
		{
			super.boatPosRotationIncrements = par9 + 5;
		} else
		{
			double d3 = par1 - super.posX;
			double d4 = par3 - super.posY;
			double d5 = par5 - super.posZ;
			double d6 = d3 * d3 + d4 * d4 + d5 * d5;
			if (d6 <= 1.0D)
				return;
			super.boatPosRotationIncrements = 3;
		}
		super.boatX = par1;
		super.boatY = par3;
		super.boatZ = par5;
		super.boatYaw = par7;
		super.boatPitch = par8;
		super.motionX = super.velocityX;
		super.motionY = super.velocityY;
		super.motionZ = super.velocityZ;
	}

	public void setVelocity(double par1, double par3, double par5)
	{
		super.velocityX = super.motionX = par1;
		super.velocityY = super.motionY = par3;
		super.velocityZ = super.motionZ = par5;
	}

	public abstract String getTexture();

	public ItemStack getPickedResult(MovingObjectPosition target)
	{
		return getItem();
	}

	protected ItemStack getItem()
	{
		return new ItemStack(Item.boat);
	}

	protected void dropItem(DamageSource killer)
	{
		entityDropItem(getItem(), 0.0F);
	}

	protected double getBreakMotion()
	{
		return 0.20000000000000001D;
	}

	protected void breakBoat(double motion)
	{
		for (int k = 0; k < 3; k++)
			dropItemWithOffset(Block.planks.blockID, 1, 0.0F);

		for (int k = 0; k < 2; k++)
			dropItemWithOffset(Item.stick.itemID, 1, 0.0F);

	}

	protected double getAccelerationFactor()
	{
		return 1.0D;
	}

	protected double getTopSpeed()
	{
		return 0.34999999999999998D;
	}

	protected boolean isOnWater(AxisAlignedBB aabb)
	{
		return super.worldObj.isAABBInMaterial(aabb, Material.water);
	}
}
