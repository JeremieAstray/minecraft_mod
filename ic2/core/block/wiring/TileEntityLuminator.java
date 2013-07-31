// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityLuminator.java

package ic2.core.block.wiring;

import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.core.*;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;

// Referenced classes of package ic2.core.block.wiring:
//			TileEntityCable

public class TileEntityLuminator extends TileEntity
	implements IEnergySink
{

	public int energy;
	public int ticker;
	public boolean ignoreBlockStay;
	public int maxInput;
	public boolean addedToEnergyNet;
	private boolean loaded;

	public TileEntityLuminator()
	{
		energy = 0;
		ticker = -1;
		ignoreBlockStay = false;
		maxInput = 32;
		addedToEnergyNet = false;
		loaded = false;
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

	public void validate()
	{
		super.validate();
		IC2.addSingleTickCallback(super.worldObj, new ITickCallback() {

			final TileEntityLuminator this$0;

			public void tickCallback(World world)
			{
				if (isInvalid() || !world.blockExists(xCoord, yCoord, zCoord))
					return;
				onLoaded();
				if (enableUpdateEntity())
					world.loadedTileEntityList.add(TileEntityLuminator.this);
			}

			
			{
				this$0 = TileEntityLuminator.this;
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
		if (ticker % 4 == 0)
		{
			energy--;
			if (energy <= 0)
				super.worldObj.setBlock(super.xCoord, super.yCoord, super.zCoord, Ic2Items.luminator.itemID, super.worldObj.getBlockMetadata(super.xCoord, super.yCoord, super.zCoord), 7);
		}
	}

	public boolean isAddedToEnergyNet()
	{
		return addedToEnergyNet;
	}

	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction)
	{
		return emitter instanceof TileEntityCable;
	}

	public int demandsEnergy()
	{
		return getMaxEnergy() - energy;
	}

	public int injectEnergy(Direction directionFrom, int amount)
	{
		if (amount > maxInput)
		{
			poof();
			return 0;
		}
		if (energy >= getMaxEnergy() || amount <= 0)
			return amount;
		if (super.worldObj.getBlockId(super.xCoord, super.yCoord, super.zCoord) == Ic2Items.luminator.itemID)
		{
			super.worldObj.setBlock(super.xCoord, super.yCoord, super.zCoord, Ic2Items.activeLuminator.itemID, super.worldObj.getBlockMetadata(super.xCoord, super.yCoord, super.zCoord), 7);
			TileEntityLuminator newLumi = (TileEntityLuminator)super.worldObj.getBlockTileEntity(super.xCoord, super.yCoord, super.zCoord);
			return newLumi.injectEnergy(directionFrom, amount);
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
		return 10000;
	}

	public void poof()
	{
		super.worldObj.setBlock(super.xCoord, super.yCoord, super.zCoord, 0, 0, 7);
		ExplosionIC2 explosion = new ExplosionIC2(super.worldObj, null, 0.5D + (double)super.xCoord, 0.5D + (double)super.yCoord, 0.5D + (double)super.zCoord, 0.5F, 0.85F, 2.0F);
		explosion.doExplosion();
	}

	public boolean canCableConnectFrom(int x, int y, int z)
	{
		int facing = super.worldObj.getBlockMetadata(super.xCoord, super.yCoord, super.zCoord);
		switch (facing)
		{
		case 0: // '\0'
			return x == super.xCoord && y == super.yCoord + 1 && z == super.zCoord;

		case 1: // '\001'
			return x == super.xCoord && y == super.yCoord - 1 && z == super.zCoord;

		case 2: // '\002'
			return x == super.xCoord && y == super.yCoord && z == super.zCoord + 1;

		case 3: // '\003'
			return x == super.xCoord && y == super.yCoord && z == super.zCoord - 1;

		case 4: // '\004'
			return x == super.xCoord + 1 && y == super.yCoord && z == super.zCoord;

		case 5: // '\005'
			return x == super.xCoord - 1 && y == super.yCoord && z == super.zCoord;
		}
		return false;
	}
}
