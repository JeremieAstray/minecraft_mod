// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityReactorChamber.java

package ic2.core.block.generator.tileentity;

import ic2.api.Direction;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorChamber;
import ic2.api.tile.IWrenchable;
import ic2.core.IC2;
import ic2.core.ITickCallback;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

// Referenced classes of package ic2.core.block.generator.tileentity:
//			TileEntityNuclearReactor

public abstract class TileEntityReactorChamber extends TileEntity
	implements IWrenchable, IInventory, IReactorChamber
{

	private static final Direction directions[] = Direction.values();
	private boolean loaded;

	public TileEntityReactorChamber()
	{
		loaded = false;
	}

	public void validate()
	{
		super.validate();
		IC2.addSingleTickCallback(super.worldObj, new ITickCallback() {

			final TileEntityReactorChamber this$0;

			public void tickCallback(World world)
			{
				if (isInvalid() || !world.blockExists(xCoord, yCoord, zCoord))
					return;
				onLoaded();
				if (enableUpdateEntity())
					world.loadedTileEntityList.add(TileEntityReactorChamber.this);
			}

			
			{
				this$0 = TileEntityReactorChamber.this;
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
		loaded = true;
	}

	public void onUnloaded()
	{
		loaded = false;
	}

	public final boolean canUpdate()
	{
		return false;
	}

	public boolean enableUpdateEntity()
	{
		return false;
	}

	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side)
	{
		return false;
	}

	public short getFacing()
	{
		return 0;
	}

	public void setFacing(short word0)
	{
	}

	public boolean wrenchCanRemove(EntityPlayer entityPlayer)
	{
		return true;
	}

	public float getWrenchDropRate()
	{
		return 0.8F;
	}

	public ItemStack getWrenchDrop(EntityPlayer entityPlayer)
	{
		return new ItemStack(super.worldObj.getBlockId(super.xCoord, super.yCoord, super.zCoord), 1, super.worldObj.getBlockMetadata(super.xCoord, super.yCoord, super.zCoord));
	}

	public int getSizeInventory()
	{
		TileEntityNuclearReactor reactor = getReactor();
		if (reactor == null)
			return 0;
		else
			return reactor.getSizeInventory();
	}

	public ItemStack getStackInSlot(int i)
	{
		TileEntityNuclearReactor reactor = getReactor();
		if (reactor == null)
			return null;
		else
			return reactor.getStackInSlot(i);
	}

	public ItemStack decrStackSize(int i, int j)
	{
		TileEntityNuclearReactor reactor = getReactor();
		if (reactor == null)
			return null;
		else
			return reactor.decrStackSize(i, j);
	}

	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		TileEntityNuclearReactor reactor = getReactor();
		if (reactor == null)
		{
			return;
		} else
		{
			reactor.setInventorySlotContents(i, itemstack);
			return;
		}
	}

	public String getInvName()
	{
		TileEntityNuclearReactor reactor = getReactor();
		if (reactor == null)
			return "Nuclear Reactor";
		else
			return reactor.getInvName();
	}

	public boolean isInvNameLocalized()
	{
		return false;
	}

	public int getInventoryStackLimit()
	{
		TileEntityNuclearReactor reactor = getReactor();
		if (reactor == null)
			return 64;
		else
			return reactor.getInventoryStackLimit();
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		TileEntityNuclearReactor reactor = getReactor();
		if (reactor == null)
			return false;
		else
			return reactor.isUseableByPlayer(entityplayer);
	}

	public void openChest()
	{
		TileEntityNuclearReactor reactor = getReactor();
		if (reactor == null)
		{
			return;
		} else
		{
			reactor.openChest();
			return;
		}
	}

	public void closeChest()
	{
		TileEntityNuclearReactor reactor = getReactor();
		if (reactor == null)
		{
			return;
		} else
		{
			reactor.closeChest();
			return;
		}
	}

	public ItemStack getStackInSlotOnClosing(int var1)
	{
		TileEntityNuclearReactor reactor = getReactor();
		if (reactor == null)
			return null;
		else
			return reactor.getStackInSlotOnClosing(var1);
	}

	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		TileEntityNuclearReactor reactor = getReactor();
		if (reactor == null)
			return false;
		else
			return reactor.isItemValidForSlot(i, itemstack);
	}

	public TileEntityNuclearReactor getReactor()
	{
		Direction arr$[] = directions;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Direction value = arr$[i$];
			TileEntity te = value.applyToTileEntity(this);
			if (te instanceof TileEntityNuclearReactor)
				return (TileEntityNuclearReactor)te;
		}

		Block blk = Block.blocksList[super.worldObj.getBlockId(super.xCoord, super.yCoord, super.zCoord)];
		if (blk != null)
			blk.onNeighborBlockChange(super.worldObj, super.xCoord, super.yCoord, super.zCoord, blk.blockID);
		return null;
	}

	public abstract int sendEnergy(int i);

	public volatile IReactor getReactor()
	{
		return getReactor();
	}

}
