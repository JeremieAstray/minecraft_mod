// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityTesla.java

package ic2.core.block.machine.tileentity;

import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.core.*;
import ic2.core.block.TileEntityBlock;
import ic2.core.item.armor.ItemArmorHazmat;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;

public class TileEntityTesla extends TileEntityBlock
	implements IEnergySink
{

	public int energy;
	public int ticker;
	public int maxEnergy;
	public int maxInput;
	public boolean addedToEnergyNet;

	public TileEntityTesla()
	{
		energy = 0;
		ticker = 0;
		maxEnergy = 10000;
		maxInput = 128;
		addedToEnergyNet = false;
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		energy = nbttagcompound.getShort("energy");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setShort("energy", (short)energy);
	}

	public void onLoaded()
	{
		super.onLoaded();
		if (IC2.platform.isSimulating())
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			addedToEnergyNet = true;
		}
	}

	public void onUnloaded()
	{
		if (IC2.platform.isSimulating() && addedToEnergyNet)
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			addedToEnergyNet = false;
		}
		super.onUnloaded();
	}

	public boolean enableUpdateEntity()
	{
		return IC2.platform.isSimulating();
	}

	public void updateEntity()
	{
		super.updateEntity();
		if (!IC2.platform.isSimulating() || !redstoned())
			return;
		if (energy < getCost())
			return;
		int damage = energy / getCost();
		energy--;
		if (ticker++ % 32 == 0 && shock(damage))
			energy = 0;
	}

	public boolean shock(int damage)
	{
		boolean shock = false;
		List list1 = super.worldObj.getEntitiesWithinAABB(net/minecraft/entity/EntityLivingBase, AxisAlignedBB.getBoundingBox(super.xCoord - 4, super.yCoord - 4, super.zCoord - 4, super.xCoord + 5, super.yCoord + 5, super.zCoord + 5));
		for (int l = 0; l < list1.size(); l++)
		{
			EntityLivingBase victim = (EntityLivingBase)list1.get(l);
			if (ItemArmorHazmat.hasCompleteHazmat(victim))
				continue;
			shock = true;
			victim.attackEntityFrom(IC2DamageSource.electricity, damage);
			for (int i = 0; i < damage; i++)
				super.worldObj.spawnParticle("reddust", ((Entity) (victim)).posX + (double)super.worldObj.rand.nextFloat(), ((Entity) (victim)).posY + (double)(super.worldObj.rand.nextFloat() * 2.0F), ((Entity) (victim)).posZ + (double)super.worldObj.rand.nextFloat(), 0.0D, 0.0D, 1.0D);

		}

		return shock;
	}

	public boolean redstoned()
	{
		return super.worldObj.isBlockIndirectlyGettingPowered(super.xCoord, super.yCoord, super.zCoord) || super.worldObj.isBlockIndirectlyGettingPowered(super.xCoord, super.yCoord, super.zCoord);
	}

	public static int getCost()
	{
		return 400;
	}

	public boolean isAddedToEnergyNet()
	{
		return addedToEnergyNet;
	}

	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction)
	{
		return true;
	}

	public int demandsEnergy()
	{
		return maxEnergy - energy;
	}

	public int injectEnergy(Direction directionFrom, int amount)
	{
		if (amount > maxInput)
		{
			IC2.explodeMachineAt(super.worldObj, super.xCoord, super.yCoord, super.zCoord);
			return 0;
		}
		if (energy >= maxEnergy)
		{
			return amount;
		} else
		{
			energy += amount;
			return 0;
		}
	}

	public int getMaxSafeInput()
	{
		return maxInput;
	}
}
