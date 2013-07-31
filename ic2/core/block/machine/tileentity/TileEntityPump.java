// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityPump.java

package ic2.core.block.machine.tileentity;

import ic2.api.Direction;
import ic2.core.*;
import ic2.core.audio.*;
import ic2.core.block.invslot.InvSlotConsumableLiquidContainer;
import ic2.core.block.machine.ContainerPump;
import ic2.core.block.machine.gui.GuiPump;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.*;

// Referenced classes of package ic2.core.block.machine.tileentity:
//			TileEntityElectricMachine, TileEntityMiner

public class TileEntityPump extends TileEntityElectricMachine
	implements IHasGui
{

	public short pumpCharge;
	private AudioSource audioSource;
	private int lastX;
	private int lastY;
	private int lastZ;
	private TileEntityMiner miner;
	public InvSlotConsumableLiquidContainer containerSlot;

	public TileEntityPump()
	{
		super(200, 2, 1);
		pumpCharge = 0;
		miner = null;
		containerSlot = new InvSlotConsumableLiquidContainer(this, "container", 0, 1);
	}

	public void onUnloaded()
	{
		if (IC2.platform.isRendering() && audioSource != null)
		{
			IC2.audioManager.removeSources(this);
			audioSource = null;
		}
		miner = null;
		super.onUnloaded();
	}

	public void updateEntity()
	{
		super.updateEntity();
		if (!isPumpReady())
		{
			if (energy > 0)
			{
				int extraCharge = Math.min(energy, 128);
				energy -= extraCharge;
				pumpCharge += extraCharge;
				setActive(true);
			} else
			{
				setActive(false);
			}
		} else
		if (operate())
		{
			pumpCharge -= 200;
			onInventoryChanged();
			setActive(true);
		} else
		{
			setActive(false);
		}
	}

	public String getInvName()
	{
		return "Pump";
	}

	public boolean operate()
	{
		if (!canHarvest())
			return false;
		if (miner == null || miner.isInvalid())
		{
			miner = null;
			Direction arr$[] = Direction.values();
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Direction dir = arr$[i$];
				if (dir == Direction.YP)
					continue;
				TileEntity te = dir.applyToTileEntity(this);
				if (!(te instanceof TileEntityMiner))
					continue;
				miner = (TileEntityMiner)te;
				break;
			}

		}
		FluidStack liquid = null;
		if (miner != null)
		{
			if (miner.canProvideLiquid)
				liquid = pump(miner.liquidX, miner.liquidY, miner.liquidZ);
		} else
		{
			ForgeDirection arr$[] = ForgeDirection.VALID_DIRECTIONS;
			int len$ = arr$.length;
			int i$ = 0;
			do
			{
				if (i$ >= len$)
					break;
				ForgeDirection dir = arr$[i$];
				liquid = pump(super.xCoord + dir.offsetX, super.yCoord + dir.offsetY, super.zCoord + dir.offsetZ);
				if (liquid != null)
					break;
				i$++;
			} while (true);
		}
		if (liquid != null)
		{
			ItemStack filled = containerSlot.fill(liquid, false);
			if (filled != null)
			{
				List drops = new ArrayList();
				drops.add(filled);
				StackUtil.distributeDrop(this, drops);
			} else
			if (!putInChestBucket(liquid))
				return false;
			clearLastBlock();
			return true;
		} else
		{
			return false;
		}
	}

	public void clearLastBlock()
	{
		super.worldObj.setBlockToAir(lastX, lastY, lastZ);
	}

	public FluidStack pump(int x, int y, int z)
	{
		lastX = x;
		lastY = y;
		lastZ = z;
		int blockId = super.worldObj.getBlockId(x, y, z);
		if (blockId == 0)
			return null;
		FluidStack ret = null;
		if (Block.blocksList[blockId] instanceof IFluidBlock)
		{
			IFluidBlock liquid = (IFluidBlock)Block.blocksList[blockId];
			ret = liquid.drain(super.worldObj, x, y, z, true);
		} else
		if (blockId == Block.waterStill.blockID || blockId == ((Block) (Block.waterMoving)).blockID)
		{
			if (super.worldObj.getBlockMetadata(x, y, z) != 0)
				return null;
			ret = new FluidStack(FluidRegistry.WATER, 1000);
		} else
		if (blockId == Block.lavaStill.blockID || blockId == ((Block) (Block.lavaMoving)).blockID)
		{
			if (super.worldObj.getBlockMetadata(x, y, z) != 0)
				return null;
			ret = new FluidStack(FluidRegistry.LAVA, 1000);
		}
		return ret;
	}

	public boolean putInChestBucket(FluidStack liquid)
	{
		return putInChestBucket(super.xCoord, super.yCoord + 1, super.zCoord, liquid) || putInChestBucket(super.xCoord, super.yCoord - 1, super.zCoord, liquid) || putInChestBucket(super.xCoord + 1, super.yCoord, super.zCoord, liquid) || putInChestBucket(super.xCoord - 1, super.yCoord, super.zCoord, liquid) || putInChestBucket(super.xCoord, super.yCoord, super.zCoord + 1, liquid) || putInChestBucket(super.xCoord, super.yCoord, super.zCoord - 1, liquid);
	}

	public boolean putInChestBucket(int x, int y, int z, FluidStack liquid)
	{
		if (!(super.worldObj.getBlockTileEntity(x, y, z) instanceof TileEntityChest))
			return false;
		TileEntityChest chest = (TileEntityChest)super.worldObj.getBlockTileEntity(x, y, z);
		for (int i = 0; i < chest.getSizeInventory(); i++)
		{
			ItemStack container = chest.getStackInSlot(i);
			if (container == null)
				continue;
			ItemStack filled = FluidContainerRegistry.fillFluidContainer(liquid, container);
			if (filled == null)
				continue;
			container.stackSize--;
			if (container.stackSize <= 0)
			{
				chest.setInventorySlotContents(i, filled);
			} else
			{
				for (int j = 0; j < chest.getSizeInventory(); j++)
				{
					ItemStack itemStack = chest.getStackInSlot(j);
					if (itemStack == null)
					{
						chest.setInventorySlotContents(j, filled);
						return true;
					}
					if (StackUtil.isStackEqual(itemStack, filled) && itemStack.stackSize < itemStack.getMaxStackSize())
					{
						itemStack.stackSize++;
						return true;
					}
				}

				List drops = new ArrayList();
				drops.add(filled);
				StackUtil.distributeDrop(this, drops);
			}
			return true;
		}

		return false;
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		pumpCharge = nbttagcompound.getShort("pumpCharge");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setShort("pumpCharge", pumpCharge);
	}

	public boolean isPumpReady()
	{
		return pumpCharge >= 200;
	}

	public boolean canHarvest()
	{
		if (!isPumpReady())
			return false;
		else
			return !containerSlot.isEmpty() || isBucketInChestAvaible();
	}

	public boolean isBucketInChestAvaible()
	{
		return isBucketInChestAvaible(super.xCoord, super.yCoord + 1, super.zCoord) || isBucketInChestAvaible(super.xCoord, super.yCoord - 1, super.zCoord) || isBucketInChestAvaible(super.xCoord + 1, super.yCoord, super.zCoord) || isBucketInChestAvaible(super.xCoord - 1, super.yCoord, super.zCoord) || isBucketInChestAvaible(super.xCoord, super.yCoord, super.zCoord + 1) || isBucketInChestAvaible(super.xCoord, super.yCoord, super.zCoord - 1);
	}

	public boolean isBucketInChestAvaible(int x, int y, int z)
	{
		if (!(super.worldObj.getBlockTileEntity(x, y, z) instanceof TileEntityChest))
			return false;
		TileEntityChest chest = (TileEntityChest)super.worldObj.getBlockTileEntity(x, y, z);
		for (int i = 0; i < chest.getSizeInventory(); i++)
			if (chest.getStackInSlot(i) != null && chest.getStackInSlot(i).itemID == Item.bucketEmpty.itemID)
				return true;

		return false;
	}

	public ContainerBase getGuiContainer(EntityPlayer entityPlayer)
	{
		return new ContainerPump(entityPlayer, this);
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		return new GuiPump(new ContainerPump(entityPlayer, this));
	}

	public void onGuiClosed(EntityPlayer entityplayer)
	{
	}

	public void onNetworkUpdate(String field)
	{
		if (field.equals("active") && prevActive != getActive())
		{
			if (audioSource == null)
				audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Machines/PumpOp.ogg", true, false, IC2.audioManager.defaultVolume);
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
}
