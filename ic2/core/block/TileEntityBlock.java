// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityBlock.java

package ic2.core.block;

import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkUpdateListener;
import ic2.api.tile.IWrenchable;
import ic2.core.*;
import ic2.core.network.NetworkManager;
import java.util.List;
import java.util.Vector;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

// Referenced classes of package ic2.core.block:
//			BlockTextureStitched

public class TileEntityBlock extends TileEntity
	implements INetworkDataProvider, INetworkUpdateListener, IWrenchable
{

	private boolean active;
	private short facing;
	public boolean prevActive;
	public short prevFacing;
	public boolean loaded;
	private Icon lastRenderIcons[];
	private int tesrMask;
	public int tesrTtl;
	private static final int defaultTesrTtl = 500;

	public TileEntityBlock()
	{
		active = false;
		facing = 0;
		prevActive = false;
		prevFacing = 0;
		loaded = false;
	}

	public void validate()
	{
		super.validate();
		IC2.addSingleTickCallback(super.worldObj, new ITickCallback() {

			final TileEntityBlock this$0;

			public void tickCallback(World world)
			{
				if (isInvalid() || !world.blockExists(xCoord, yCoord, zCoord))
					return;
				onLoaded();
				if (enableUpdateEntity())
					world.loadedTileEntityList.add(TileEntityBlock.this);
			}

			
			{
				this$0 = TileEntityBlock.this;
				super();
			}
		});
	}

	public void invalidate()
	{
		if (loaded)
			onUnloaded();
		super.invalidate();
	}

	public void onChunkUnload()
	{
		if (loaded)
			onUnloaded();
		super.onChunkUnload();
	}

	public void onLoaded()
	{
		loaded = true;
	}

	public void onUnloaded()
	{
		loaded = false;
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		prevFacing = facing = nbttagcompound.getShort("facing");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setShort("facing", facing);
	}

	public final boolean canUpdate()
	{
		return false;
	}

	public boolean enableUpdateEntity()
	{
		return false;
	}

	public void onRender()
	{
		Block block = getBlockType();
		if (lastRenderIcons == null)
			lastRenderIcons = new Icon[6];
		for (int side = 0; side < 6; side++)
			lastRenderIcons[side] = block.getBlockTexture(super.worldObj, super.xCoord, super.yCoord, super.zCoord, side);

		tesrMask = 0;
	}

	public boolean getActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
		if (prevActive != active)
			IC2.network.updateTileEntityField(this, "active");
		prevActive = active;
	}

	public void setActiveWithoutNotify(boolean active)
	{
		this.active = active;
		prevActive = active;
	}

	public short getFacing()
	{
		return facing;
	}

	public List getNetworkedFields()
	{
		List ret = new Vector(2);
		ret.add("active");
		ret.add("facing");
		return ret;
	}

	public void onNetworkUpdate(String field)
	{
		if (field.equals("active") && prevActive != active || field.equals("facing") && prevFacing != facing)
		{
			int reRenderMask = 0;
			Block block = getBlockType();
			if (lastRenderIcons == null)
			{
				reRenderMask = -1;
			} else
			{
				for (int side = 0; side < 6; side++)
				{
					Icon oldIcon = lastRenderIcons[side];
					if (oldIcon instanceof BlockTextureStitched)
						oldIcon = ((BlockTextureStitched)oldIcon).getRealTexture();
					Icon newIcon = block.getBlockTexture(super.worldObj, super.xCoord, super.yCoord, super.zCoord, side);
					if (newIcon instanceof BlockTextureStitched)
						newIcon = ((BlockTextureStitched)newIcon).getRealTexture();
					if (oldIcon != newIcon)
						reRenderMask |= 1 << side;
				}

			}
			if (reRenderMask != 0)
				if (reRenderMask < 0 || prevFacing != facing || block.getRenderType() != IC2.platform.getRenderId("default"))
				{
					super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
				} else
				{
					tesrMask = reRenderMask;
					tesrTtl = 500;
				}
			prevActive = active;
			prevFacing = facing;
		}
	}

	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side)
	{
		return false;
	}

	public void setFacing(short facing)
	{
		this.facing = facing;
		if (prevFacing != facing)
			IC2.network.updateTileEntityField(this, "facing");
		prevFacing = facing;
	}

	public boolean wrenchCanRemove(EntityPlayer entityPlayer)
	{
		return true;
	}

	public float getWrenchDropRate()
	{
		return 1.0F;
	}

	public ItemStack getWrenchDrop(EntityPlayer entityPlayer)
	{
		return new ItemStack(super.worldObj.getBlockId(super.xCoord, super.yCoord, super.zCoord), 1, super.worldObj.getBlockMetadata(super.xCoord, super.yCoord, super.zCoord));
	}

	public int getTesrMask()
	{
		return tesrMask;
	}

	public void onBlockBreak(int i, int j)
	{
	}
}
