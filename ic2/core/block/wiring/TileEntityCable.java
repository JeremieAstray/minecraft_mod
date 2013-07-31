// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityCable.java

package ic2.core.block.wiring;

import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.*;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.*;
import ic2.core.block.TileEntityBlock;
import ic2.core.network.NetworkManager;
import java.util.*;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;

// Referenced classes of package ic2.core.block.wiring:
//			TileEntityLuminator, BlockCable

public class TileEntityCable extends TileEntityBlock
	implements IEnergyConductor, INetworkTileEntityEventListener
{

	public short cableType;
	public short color;
	public byte foamed;
	public byte foamColor;
	public int retextureRefId[];
	public int retextureRefMeta[];
	public int retextureRefSide[];
	public byte connectivity;
	public byte renderSide;
	private byte prevFoamed;
	public boolean addedToEnergyNet;
	private ITickCallback continuousTickCallback;
	private static final int EventRemoveConductor = 0;

	public TileEntityCable(short type)
	{
		cableType = 0;
		color = 0;
		foamed = 0;
		foamColor = 0;
		connectivity = 0;
		renderSide = 0;
		prevFoamed = 0;
		addedToEnergyNet = false;
		continuousTickCallback = null;
		cableType = type;
	}

	public TileEntityCable()
	{
		cableType = 0;
		color = 0;
		foamed = 0;
		foamColor = 0;
		connectivity = 0;
		renderSide = 0;
		prevFoamed = 0;
		addedToEnergyNet = false;
		continuousTickCallback = null;
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		cableType = nbttagcompound.getShort("cableType");
		color = nbttagcompound.getShort("color");
		foamColor = nbttagcompound.getByte("foamColor");
		foamed = nbttagcompound.getByte("foamed");
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
		nbttagcompound.setShort("cableType", cableType);
		nbttagcompound.setShort("color", color);
		nbttagcompound.setByte("foamed", foamed);
		nbttagcompound.setByte("foamColor", foamColor);
		if (retextureRefId != null)
		{
			nbttagcompound.setIntArray("retextureRefId", retextureRefId);
			nbttagcompound.setIntArray("retextureRefMeta", retextureRefMeta);
			nbttagcompound.setIntArray("retextureRefSide", retextureRefSide);
		}
	}

	public void onLoaded()
	{
		super.onLoaded();
		if (IC2.platform.isSimulating())
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			addedToEnergyNet = true;
			onNeighborBlockChange();
			if (foamed == 1)
				changeFoam(foamed, true);
		}
	}

	public void onUnloaded()
	{
		if (IC2.platform.isSimulating() && addedToEnergyNet)
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			addedToEnergyNet = false;
		}
		if (continuousTickCallback != null)
		{
			IC2.removeContinuousTickCallback(super.worldObj, continuousTickCallback);
			continuousTickCallback = null;
		}
		super.onUnloaded();
	}

	public void onNeighborBlockChange()
	{
		byte newConnectivity = 0;
		byte newRenderSide = 0;
		int mask = 1;
		Direction arr$[] = Direction.values();
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Direction direction = arr$[i$];
			TileEntity neighbor = EnergyNet.getForWorld(super.worldObj).getNeighbor(this, direction);
			if (((neighbor instanceof IEnergyAcceptor) && ((IEnergyAcceptor)neighbor).acceptsEnergyFrom(this, direction.getInverse()) || (neighbor instanceof IEnergyEmitter) && ((IEnergyEmitter)neighbor).emitsEnergyTo(this, direction.getInverse())) && canInteractWith(neighbor))
			{
				newConnectivity |= mask;
				if ((neighbor instanceof TileEntityCable) && ((TileEntityCable)neighbor).getCableThickness() < getCableThickness())
					newRenderSide |= mask;
			}
			mask *= 2;
		}

		if (connectivity != newConnectivity)
		{
			connectivity = newConnectivity;
			IC2.network.updateTileEntityField(this, "connectivity");
		}
		if (renderSide != newRenderSide)
		{
			renderSide = newRenderSide;
			IC2.network.updateTileEntityField(this, "renderSide");
		}
	}

	public boolean shouldRefresh(int oldID, int newID, int oldMeta, int newMeta, World world, int x, int y, 
			int z)
	{
		return oldID != newID;
	}

	public boolean changeColor(int newColor)
	{
		if (foamed == 0 && (color == newColor || cableType == 1 || cableType == 2 || cableType == 5 || cableType == 10 || cableType == 11 || cableType == 12) || foamed > 0 && foamColor == newColor)
			return false;
		if (IC2.platform.isSimulating())
			if (foamed == 0)
			{
				if (addedToEnergyNet)
					MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
				addedToEnergyNet = false;
				color = (short)newColor;
				MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
				addedToEnergyNet = true;
				IC2.network.updateTileEntityField(this, "color");
				onNeighborBlockChange();
			} else
			{
				foamColor = (byte)newColor;
				IC2.network.updateTileEntityField(this, "foamColor");
				retextureRefId = null;
				retextureRefMeta = null;
				retextureRefSide = null;
				IC2.network.updateTileEntityField(this, "retextureRefId");
				IC2.network.updateTileEntityField(this, "retextureRefMeta");
				IC2.network.updateTileEntityField(this, "retextureRefSide");
			}
		return true;
	}

	public boolean changeFoam(byte foamed)
	{
		return changeFoam(foamed, false);
	}

	public boolean tryAddInsulation()
	{
		short target;
		switch (cableType)
		{
		case 1: // '\001'
			target = 0;
			break;

		case 2: // '\002'
			target = 3;
			break;

		case 3: // '\003'
			target = 4;
			break;

		case 5: // '\005'
			target = 6;
			break;

		case 6: // '\006'
			target = 7;
			break;

		case 7: // '\007'
			target = 8;
			break;

		case 4: // '\004'
		default:
			target = cableType;
			break;
		}
		if (target != cableType)
		{
			if (IC2.platform.isSimulating())
				changeType(target);
			return true;
		} else
		{
			return false;
		}
	}

	public boolean tryRemoveInsulation()
	{
		short target;
		switch (cableType)
		{
		case 0: // '\0'
			target = 1;
			break;

		case 3: // '\003'
			target = 2;
			break;

		case 4: // '\004'
			target = 3;
			break;

		case 6: // '\006'
			target = 5;
			break;

		case 7: // '\007'
			target = 6;
			break;

		case 8: // '\b'
			target = 7;
			break;

		case 1: // '\001'
		case 2: // '\002'
		case 5: // '\005'
		default:
			target = cableType;
			break;
		}
		if (target != cableType)
		{
			if (IC2.platform.isSimulating())
				changeType(target);
			return true;
		} else
		{
			return false;
		}
	}

	public void changeType(short cableType)
	{
		super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord, super.zCoord, cableType, 7);
		if (addedToEnergyNet)
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
		addedToEnergyNet = false;
		this.cableType = cableType;
		MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
		addedToEnergyNet = true;
		IC2.network.updateTileEntityField(this, "cableType");
	}

	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side)
	{
		return false;
	}

	public boolean wrenchCanRemove(EntityPlayer entityPlayer)
	{
		return false;
	}

	public boolean isAddedToEnergyNet()
	{
		return addedToEnergyNet;
	}

	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction)
	{
		return canInteractWith(emitter);
	}

	public boolean emitsEnergyTo(TileEntity receiver, Direction direction)
	{
		return canInteractWith(receiver);
	}

	public boolean canInteractWith(TileEntity te)
	{
		if (!(te instanceof IEnergyTile))
			return false;
		if (te instanceof TileEntityCable)
			return canInteractWithCable((TileEntityCable)te);
		if (te instanceof TileEntityLuminator)
			return ((TileEntityLuminator)te).canCableConnectFrom(super.xCoord, super.yCoord, super.zCoord);
		else
			return true;
	}

	public boolean canInteractWithCable(TileEntityCable cable)
	{
		return color == 0 || cable.color == 0 || color == cable.color;
	}

	public float getCableThickness()
	{
		if (foamed == 2)
			return 1.0F;
		else
			return getCableThickness(cableType);
	}

	public static float getCableThickness(int cableType)
	{
		float p = 1.0F;
		switch (cableType)
		{
		case 0: // '\0'
			p = 6F;
			break;

		case 1: // '\001'
			p = 4F;
			break;

		case 2: // '\002'
			p = 3F;
			break;

		case 3: // '\003'
			p = 5F;
			break;

		case 4: // '\004'
			p = 6F;
			break;

		case 5: // '\005'
			p = 6F;
			break;

		case 6: // '\006'
			p = 8F;
			break;

		case 7: // '\007'
			p = 10F;
			break;

		case 8: // '\b'
			p = 12F;
			break;

		case 9: // '\t'
			p = 4F;
			break;

		case 10: // '\n'
			p = 5F;
			break;

		case 11: // '\013'
			p = 8F;
			break;

		case 12: // '\f'
			p = 8F;
			break;

		case 13: // '\r'
			p = 16F;
			break;
		}
		return p / 16F;
	}

	public double getConductionLoss()
	{
		switch (cableType)
		{
		case 0: // '\0'
			return 0.20000000000000001D;

		case 1: // '\001'
			return 0.29999999999999999D;

		case 2: // '\002'
			return 0.5D;

		case 3: // '\003'
			return 0.45000000000000001D;

		case 4: // '\004'
			return 0.40000000000000002D;

		case 5: // '\005'
			return 1.0D;

		case 6: // '\006'
			return 0.94999999999999996D;

		case 7: // '\007'
			return 0.90000000000000002D;

		case 8: // '\b'
			return 0.80000000000000004D;

		case 9: // '\t'
			return 0.025000000000000001D;

		case 10: // '\n'
			return 0.025000000000000001D;

		case 11: // '\013'
			return 0.5D;

		case 12: // '\f'
			return 0.5D;
		}
		return 0.025000000000000001D;
	}

	public int getInsulationEnergyAbsorption()
	{
		switch (cableType)
		{
		case 0: // '\0'
			return 32;

		case 1: // '\001'
			return 8;

		case 2: // '\002'
			return 8;

		case 3: // '\003'
			return 32;

		case 4: // '\004'
			return 128;

		case 5: // '\005'
			return 0;

		case 6: // '\006'
			return 128;

		case 7: // '\007'
			return 512;

		case 8: // '\b'
			return 9001;

		case 9: // '\t'
			return 9001;

		case 10: // '\n'
			return 3;

		case 11: // '\013'
			return 9001;

		case 12: // '\f'
			return 9001;
		}
		return 0;
	}

	public int getInsulationBreakdownEnergy()
	{
		return 9001;
	}

	public int getConductorBreakdownEnergy()
	{
		switch (cableType)
		{
		case 0: // '\0'
			return 33;

		case 1: // '\001'
			return 33;

		case 2: // '\002'
			return 129;

		case 3: // '\003'
			return 129;

		case 4: // '\004'
			return 129;

		case 5: // '\005'
			return 2049;

		case 6: // '\006'
			return 2049;

		case 7: // '\007'
			return 2049;

		case 8: // '\b'
			return 2049;

		case 9: // '\t'
			return 513;

		case 10: // '\n'
			return 6;

		case 11: // '\013'
			return 2049;

		case 12: // '\f'
			return 2049;
		}
		return 0;
	}

	public void removeInsulation()
	{
	}

	public void removeConductor()
	{
		super.worldObj.setBlock(super.xCoord, super.yCoord, super.zCoord, 0, 0, 7);
		IC2.network.initiateTileEntityEvent(this, 0, true);
	}

	public List getNetworkedFields()
	{
		List ret = new Vector();
		ret.add("cableType");
		ret.add("color");
		ret.add("foamed");
		ret.add("foamColor");
		ret.add("retextureRefId");
		ret.add("retextureRefMeta");
		ret.add("retextureRefSide");
		ret.add("connectivity");
		ret.add("renderSide");
		ret.addAll(super.getNetworkedFields());
		return ret;
	}

	public void onNetworkUpdate(String field)
	{
		if (field.equals("foamed"))
		{
			if (prevFoamed != foamed)
			{
				if (foamed == 0 && prevFoamed != 1 || foamed == 2)
				{
					super.worldObj.setBlock(super.xCoord, super.yCoord, super.zCoord, Ic2Items.insulatedCopperCableBlock.itemID, (cableType + 1) % 16, 3);
					super.worldObj.setBlock(super.xCoord, super.yCoord, super.zCoord, Ic2Items.insulatedCopperCableBlock.itemID, cableType, 3);
				} else
				{
					super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
				}
				prevFoamed = foamed;
			}
		} else
		{
			super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
		}
		super.onNetworkUpdate(field);
	}

	public void onNetworkEvent(int event)
	{
		switch (event)
		{
		case 0: // '\0'
			super.worldObj.playSoundEffect((float)super.xCoord + 0.5F, (float)super.yCoord + 0.5F, (float)super.zCoord + 0.5F, "random.fizz", 0.5F, 2.6F + (super.worldObj.rand.nextFloat() - super.worldObj.rand.nextFloat()) * 0.8F);
			for (int l = 0; l < 8; l++)
				super.worldObj.spawnParticle("largesmoke", (double)super.xCoord + Math.random(), (double)super.yCoord + 1.2D, (double)super.zCoord + Math.random(), 0.0D, 0.0D, 0.0D);

			break;

		default:
			IC2.platform.displayError((new StringBuilder()).append("An unknown event type was received over multiplayer.\nThis could happen due to corrupted data or a bug.\n\n(Technical information: event ID ").append(event).append(", tile entity below)\n").append("T: ").append(this).append(" (").append(super.xCoord).append(",").append(super.yCoord).append(",").append(super.zCoord).append(")").toString());
			break;
		}
	}

	public float getWrenchDropRate()
	{
		return 0.0F;
	}

	private boolean changeFoam(byte foamed, boolean duringLoad)
	{
		if (this.foamed == foamed && !duringLoad)
			return false;
		if (!IC2.platform.isSimulating())
			return true;
		byte prevFoamed = this.foamed;
		this.foamed = foamed;
		if (continuousTickCallback != null)
		{
			IC2.removeContinuousTickCallback(super.worldObj, continuousTickCallback);
			continuousTickCallback = null;
		}
		if (foamed == 0 || foamed == 1)
		{
			if (retextureRefId != null)
			{
				retextureRefId = null;
				retextureRefMeta = null;
				retextureRefSide = null;
				if (!duringLoad)
				{
					IC2.network.updateTileEntityField(this, "retextureRefId");
					IC2.network.updateTileEntityField(this, "retextureRefMeta");
					IC2.network.updateTileEntityField(this, "retextureRefSide");
				}
			}
			if (foamColor != 7)
			{
				foamColor = 7;
				if (!duringLoad)
					IC2.network.updateTileEntityField(this, "foamColor");
			}
		}
		if (foamed == 0 && prevFoamed != 1 || foamed == 2)
		{
			BlockCable blockCable = (BlockCable)Block.blocksList[Ic2Items.insulatedCopperCableBlock.itemID];
			blockCable.enableBreakBlock = false;
			super.worldObj.setBlock(super.xCoord, super.yCoord, super.zCoord, Ic2Items.insulatedCopperCableBlock.itemID, (cableType + 1) % 16, 7);
			super.worldObj.setBlock(super.xCoord, super.yCoord, super.zCoord, Ic2Items.insulatedCopperCableBlock.itemID, cableType, 7);
			onLoaded();
			blockCable.enableBreakBlock = true;
		} else
		if (foamed == 1)
		{
			continuousTickCallback = new ITickCallback() {

				final TileEntityCable this$0;

				public void tickCallback(World world)
				{
					if (world.rand.nextInt(500) == 0 && world.getBlockLightValue(xCoord, yCoord, zCoord) * 6 >= worldObj.rand.nextInt(1000))
						changeFoam((byte)2);
				}

			
			{
				this$0 = TileEntityCable.this;
				super();
			}
			};
			IC2.addContinuousTickCallback(super.worldObj, continuousTickCallback);
		}
		if (!duringLoad)
			IC2.network.updateTileEntityField(this, "foamed");
		return true;
	}

	public boolean retexture(int side, int referencedBlockId, int referencedMeta, int referencedSide)
	{
		if (foamed != 2)
			return false;
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
