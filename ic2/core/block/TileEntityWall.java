// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityWall.java

package ic2.core.block;

import ic2.core.IC2;
import ic2.core.network.NetworkManager;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

// Referenced classes of package ic2.core.block:
//			TileEntityBlock

public class TileEntityWall extends TileEntityBlock
{

	public int retextureRefId[];
	public int retextureRefMeta[];
	public int retextureRefSide[];

	public TileEntityWall()
	{
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		retextureRefId = nbttagcompound.getIntArray("retextureRefId");
		retextureRefMeta = nbttagcompound.getIntArray("retextureRefMeta");
		retextureRefSide = nbttagcompound.getIntArray("retextureRefSide");
		if (retextureRefId.length != 6)
			retextureRefId = null;
		if (retextureRefMeta.length != 6)
			retextureRefMeta = null;
		if (retextureRefSide.length != 6)
			retextureRefSide = null;
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		if (retextureRefId != null)
		{
			nbttagcompound.setIntArray("retextureRefId", retextureRefId);
			nbttagcompound.setIntArray("retextureRefMeta", retextureRefMeta);
			nbttagcompound.setIntArray("retextureRefSide", retextureRefSide);
		}
	}

	public List getNetworkedFields()
	{
		List ret = new ArrayList();
		ret.add("retextureRefId");
		ret.add("retextureRefMeta");
		ret.add("retextureRefSide");
		ret.addAll(super.getNetworkedFields());
		return ret;
	}

	public void onNetworkUpdate(String field)
	{
		if (field.equals("retextureRefId") || field.equals("retextureRefMeta") || field.equals("retextureRefSide"))
			super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
		super.onNetworkUpdate(field);
	}

	public ItemStack getWrenchDrop(EntityPlayer entityPlayer)
	{
		return null;
	}

	public boolean retexture(int side, int referencedBlockId, int referencedMeta, int referencedSide)
	{
		boolean ret = false;
		boolean updateAll = false;
		if (retextureRefId == null)
		{
			retextureRefId = new int[6];
			retextureRefMeta = new int[6];
			retextureRefSide = new int[6];
			updateAll = true;
		}
		if (retextureRefId[side] != referencedBlockId || updateAll)
		{
			retextureRefId[side] = referencedBlockId;
			IC2.network.updateTileEntityField(this, "retextureRefId");
			ret = true;
		}
		if (retextureRefMeta[side] != referencedMeta || updateAll)
		{
			retextureRefMeta[side] = referencedMeta;
			IC2.network.updateTileEntityField(this, "retextureRefMeta");
			ret = true;
		}
		if (retextureRefSide[side] != referencedSide || updateAll)
		{
			retextureRefSide[side] = referencedSide;
			IC2.network.updateTileEntityField(this, "retextureRefSide");
			ret = true;
		}
		return ret;
	}
}
