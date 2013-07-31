// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityCableDetector.java

package ic2.core.block.wiring;

import ic2.core.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

// Referenced classes of package ic2.core.block.wiring:
//			TileEntityCable

public class TileEntityCableDetector extends TileEntityCable
{

	public long lastValue;
	public static int tickRate = 20;
	public int ticker;

	public TileEntityCableDetector(short meta)
	{
		super(meta);
		lastValue = -1L;
		ticker = 0;
	}

	public TileEntityCableDetector()
	{
		lastValue = -1L;
		ticker = 0;
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		setActiveWithoutNotify(nbttagcompound.getBoolean("active"));
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setBoolean("active", getActive());
	}

	public boolean enableUpdateEntity()
	{
		return IC2.platform.isSimulating();
	}

	public void updateEntity()
	{
		super.updateEntity();
		if (ticker++ % tickRate == 0)
		{
			long newValue = EnergyNet.getForWorld(super.worldObj).getTotalEnergyEmitted(this);
			if (lastValue != -1L)
				if (newValue > lastValue)
				{
					if (!getActive())
					{
						setActive(true);
						super.worldObj.notifyBlocksOfNeighborChange(super.xCoord, super.yCoord, super.zCoord, super.worldObj.getBlockId(super.xCoord, super.yCoord, super.zCoord));
					}
				} else
				if (getActive())
				{
					setActive(false);
					super.worldObj.notifyBlocksOfNeighborChange(super.xCoord, super.yCoord, super.zCoord, super.worldObj.getBlockId(super.xCoord, super.yCoord, super.zCoord));
				}
			lastValue = newValue;
		}
	}

}
