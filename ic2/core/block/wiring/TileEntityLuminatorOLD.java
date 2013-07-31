// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityLuminatorOLD.java

package ic2.core.block.wiring;

import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergySink;
import ic2.core.*;
import java.io.PrintStream;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;

public class TileEntityLuminatorOLD extends TileEntity
	implements IEnergySink, IEnergyConductor
{

	public int energy;
	public int mode;
	public boolean powered;
	public int ticker;
	public int maxInput;
	public boolean addedToEnergyNet;
	private boolean loaded;

	public TileEntityLuminatorOLD()
	{
		energy = 0;
		mode = 0;
		powered = false;
		ticker = 0;
		maxInput = 32;
		addedToEnergyNet = false;
		loaded = false;
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		energy = nbttagcompound.getShort("energy");
		mode = nbttagcompound.getShort("mode");
		powered = nbttagcompound.getBoolean("powered");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setShort("energy", (short)energy);
		nbttagcompound.setShort("mode", (short)mode);
		nbttagcompound.setBoolean("poweredy", powered);
	}

	public void validate()
	{
		super.validate();
		IC2.addSingleTickCallback(super.worldObj, new ITickCallback() {

			final TileEntityLuminatorOLD this$0;

			public void tickCallback(World world)
			{
				if (isInvalid() || !world.blockExists(xCoord, yCoord, zCoord))
					return;
				onLoaded();
				if (enableUpdateEntity())
					world.loadedTileEntityList.add(TileEntityLuminatorOLD.this);
			}

			
			{
				this$0 = TileEntityLuminatorOLD.this;
				super();
			}
		});
	}

	public void invalidate()
	{
		super.invalidate();
		if (loaded)
			onUnloaded();
	}

	public void onChunkUnload()
	{
		super.onChunkUnload();
		if (loaded)
			onUnloaded();
	}

	public void onLoaded()
	{
		if (IC2.platform.isSimulating() && !addedToEnergyNet)
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			addedToEnergyNet = true;
		}
		loaded = true;
	}

	public void onUnloaded()
	{
		if (IC2.platform.isSimulating() && addedToEnergyNet)
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			addedToEnergyNet = false;
		}
	}

	public final boolean canUpdate()
	{
		return false;
	}

	public boolean enableUpdateEntity()
	{
		return IC2.platform.isSimulating();
	}

	public void updateEntity()
	{
		ticker++;
		if (ticker % 20 == 0)
		{
			if (ticker % 160 == 0)
			{
				System.out.println((new StringBuilder()).append("Consume for Mode: ").append(mode).toString());
				int consume = 5;
				switch (mode)
				{
				case 1: // '\001'
					consume = 10;
					// fall through

				case 2: // '\002'
					consume = 40;
					break;
				}
				if (consume > energy)
				{
					energy = 0;
					powered = false;
					System.out.println("Out of energy");
				} else
				{
					System.out.println("Energized");
					energy -= consume;
					powered = true;
				}
				updateLightning();
			}
			if (powered)
				burnMobs();
		}
	}

	public float getLightLevel()
	{
		if (powered)
			System.out.println("get powered");
		System.out.println("get unpowered");
		return 0.9375F;
	}

	public void switchStrength()
	{
		mode = (mode + 1) % 3;
		updateLightning();
	}

	public void updateLightning()
	{
		System.out.println("Update Lightning");
		super.worldObj.updateLightByType(EnumSkyBlock.Sky, super.xCoord, super.yCoord, super.zCoord);
		super.worldObj.updateLightByType(EnumSkyBlock.Block, super.xCoord, super.yCoord, super.zCoord);
	}

	public boolean isAddedToEnergyNet()
	{
		return addedToEnergyNet;
	}

	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction)
	{
		return true;
	}

	public boolean emitsEnergyTo(TileEntity receiver, Direction direction)
	{
		return true;
	}

	public double getConductionLoss()
	{
		return 0.0D;
	}

	public int getInsulationEnergyAbsorption()
	{
		return maxInput;
	}

	public int getInsulationBreakdownEnergy()
	{
		return maxInput + 1;
	}

	public int getConductorBreakdownEnergy()
	{
		return maxInput + 1;
	}

	public void removeInsulation()
	{
		System.out.println("REmove Insulation");
		poof();
	}

	public void removeConductor()
	{
		System.out.println("REmove Confuctor");
		poof();
	}

	public int demandsEnergy()
	{
		return getMaxEnergy() - energy;
	}

	public int injectEnergy(Direction directionFrom, int amount)
	{
		if (amount > maxInput)
		{
			System.out.println((new StringBuilder()).append("Injecting > ").append(maxInput).toString());
			poof();
			return 0;
		}
		if (energy >= getMaxEnergy())
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

	public int getMaxEnergy()
	{
		switch (mode)
		{
		case 1: // '\001'
			return 20;

		case 2: // '\002'
			return 80;
		}
		return 10;
	}

	public void poof()
	{
		super.worldObj.setBlock(super.xCoord, super.yCoord, super.zCoord, 0, 0, 7);
		ExplosionIC2 explosion = new ExplosionIC2(super.worldObj, null, 0.5D + (double)super.xCoord, 0.5D + (double)super.yCoord, 0.5D + (double)super.zCoord, 0.5F, 0.85F, 2.0F);
		explosion.doExplosion();
	}

	public void burnMobs()
	{
		int x = super.xCoord;
		int y = super.yCoord;
		int z = super.zCoord;
		boolean xplus = false;
		boolean xminus = false;
		boolean yplus = false;
		boolean yminus = false;
		boolean zplus = false;
		boolean zminus = false;
		if (super.worldObj.getBlockId(x + 1, y, z) == 0 || super.worldObj.getBlockId(x + 1, y, z) == Block.glass.blockID || super.worldObj.getBlockId(x + 1, y, z) == Ic2Items.reinforcedGlass.itemID)
			xplus = true;
		if (super.worldObj.getBlockId(x - 1, y, z) == 0 || super.worldObj.getBlockId(x - 1, y, z) == Block.glass.blockID || super.worldObj.getBlockId(x - 1, y, z) == Ic2Items.reinforcedGlass.itemID)
			xminus = true;
		if (super.worldObj.getBlockId(x, y + 1, z) == 0 || super.worldObj.getBlockId(x, y + 1, z) == Block.glass.blockID || super.worldObj.getBlockId(x, y + 1, z) == Ic2Items.reinforcedGlass.itemID)
			yplus = true;
		if (super.worldObj.getBlockId(x, y - 1, z) == 0 || super.worldObj.getBlockId(x, y - 1, z) == Block.glass.blockID || super.worldObj.getBlockId(x, y - 1, z) == Ic2Items.reinforcedGlass.itemID)
			yminus = true;
		if (super.worldObj.getBlockId(x, y, z + 1) == 0 || super.worldObj.getBlockId(x, y, z + 1) == Block.glass.blockID || super.worldObj.getBlockId(x, y, z + 1) == Ic2Items.reinforcedGlass.itemID)
			zplus = true;
		if (super.worldObj.getBlockId(x, y, z - 1) == 0 || super.worldObj.getBlockId(x, y, z - 1) == Block.glass.blockID || super.worldObj.getBlockId(x, y, z - 1) == Ic2Items.reinforcedGlass.itemID)
			zminus = true;
		int xplusI = 0;
		int xminusI = 0;
		int yplusI = 0;
		int yminusI = 0;
		int zplusI = 0;
		int zminusI = 0;
		if (xplus)
			xplusI = 3;
		else
		if (yplus || yminus || zplus || zminus)
			xplusI = 1;
		if (xminus)
			xminusI = 3;
		else
		if (yplus || yminus || zplus || zminus)
			xminusI = 1;
		if (yplus)
			yplusI = 3;
		else
		if (xplus || xminus || zplus || zminus)
			yplusI = 1;
		if (yminus)
			yminusI = 3;
		else
		if (xplus || xminus || zplus || zminus)
			yminusI = 1;
		if (zplus)
			zplusI = 3;
		else
		if (yplus || yminus || xplus || xminus)
			zplusI = 1;
		if (zminus)
			zminusI = 3;
		else
		if (yplus || yminus || xplus || xminus)
			zminusI = 1;
		xminusI = x - xminusI;
		yminusI = y - yminusI;
		zminusI = z - zminusI;
		xplusI = x + xplusI;
		yplusI = y + yplusI;
		zplusI = z + zplusI;
		AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		List list1 = super.worldObj.getEntitiesWithinAABBExcludingEntity(null, boundingBox.addCoord(x, y, z).expand(3D, 3D, 3D));
		for (int l = 0; l < list1.size(); l++)
		{
			Entity ent = (Entity)list1.get(l);
			if (!(ent instanceof EntityMob))
				continue;
			double ex = ent.posX;
			double ey = ent.posY;
			double ez = ent.posZ;
			if (ex >= (double)xminusI && ex <= (double)(xplusI + 1) && ey >= (double)yminusI && ey <= (double)(yplusI + 2) && ez >= (double)zminusI && ez <= (double)(zplusI + 1))
				ent.setFire(10);
		}

	}
}
