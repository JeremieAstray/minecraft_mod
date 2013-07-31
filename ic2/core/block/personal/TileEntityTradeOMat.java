// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityTradeOMat.java

package ic2.core.block.personal;

import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.*;
import ic2.core.audio.AudioManager;
import ic2.core.audio.PositionSpec;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.*;
import ic2.core.network.NetworkManager;
import ic2.core.util.StackUtil;
import java.util.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.tileentity.TileEntity;

// Referenced classes of package ic2.core.block.personal:
//			IPersonalBlock, ContainerTradeOMatOpen, ContainerTradeOMatClosed, GuiTradeOMatOpen, 
//			GuiTradeOMatClosed

public class TileEntityTradeOMat extends TileEntityInventory
	implements IPersonalBlock, IHasGui, INetworkTileEntityEventListener, INetworkClientTileEntityEventListener
{

	private int ticker;
	public String owner;
	public int totalTradeCount;
	public int stock;
	public boolean infinite;
	private static final int stockUpdateRate = 64;
	private static final int EventTrade = 0;
	public final InvSlot demandSlot;
	public final InvSlot offerSlot;
	public final InvSlotConsumableLinked inputSlot;
	public final InvSlotOutput outputSlot = new InvSlotOutput(this, "output", 3, 1);

	public TileEntityTradeOMat()
	{
		owner = "null";
		totalTradeCount = 0;
		stock = 0;
		infinite = false;
		ticker = IC2.random.nextInt(64);
		demandSlot = new InvSlot(this, "demand", 0, ic2.core.block.invslot.InvSlot.Access.NONE, 1);
		offerSlot = new InvSlot(this, "offer", 1, ic2.core.block.invslot.InvSlot.Access.NONE, 1);
		inputSlot = new InvSlotConsumableLinked(this, "input", 2, 1, demandSlot);
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		owner = nbttagcompound.getString("owner");
		totalTradeCount = nbttagcompound.getInteger("totalTradeCount");
		if (nbttagcompound.hasKey("infinite"))
			infinite = nbttagcompound.getBoolean("infinite");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setString("owner", owner);
		nbttagcompound.setInteger("totalTradeCount", totalTradeCount);
		if (infinite)
			nbttagcompound.setBoolean("infinite", infinite);
	}

	public List getNetworkedFields()
	{
		List ret = new Vector(1);
		ret.add("owner");
		ret.addAll(super.getNetworkedFields());
		return ret;
	}

	public boolean enableUpdateEntity()
	{
		return IC2.platform.isSimulating();
	}

	public void updateEntity()
	{
		super.updateEntity();
		ItemStack tradedIn = inputSlot.consumeLinked(true);
		if (!offerSlot.isEmpty() && tradedIn != null && outputSlot.canAdd(offerSlot.get()))
			if (infinite)
			{
				inputSlot.consumeLinked(false);
				outputSlot.add(offerSlot.get().copy());
			} else
			{
				ItemStack transferredIn = StackUtil.fetch(this, offerSlot.get(), true);
				if (transferredIn != null && transferredIn.stackSize == offerSlot.get().stackSize)
				{
					int transferredOut = StackUtil.distribute(this, tradedIn, true);
					if (transferredOut == tradedIn.stackSize)
					{
						StackUtil.distribute(this, inputSlot.consumeLinked(false), false);
						outputSlot.add(StackUtil.fetch(this, offerSlot.get(), false));
						totalTradeCount++;
						stock -= offerSlot.get().stackSize;
						IC2.network.initiateTileEntityEvent(this, 0, true);
						onInventoryChanged();
					}
				}
			}
		if (infinite)
			stock = -1;
		else
		if (ticker++ % 64 == 0)
			updateStock();
	}

	public void onLoaded()
	{
		super.onLoaded();
		if (IC2.platform.isSimulating())
			updateStock();
	}

	public void updateStock()
	{
		stock = 0;
		ItemStack offer = offerSlot.get();
		if (offer != null)
		{
			ItemStack available = StackUtil.fetch(this, StackUtil.copyWithSize(offer, 0x7fffffff), true);
			if (available == null)
				stock = 0;
			else
				stock = available.stackSize;
		}
	}

	public boolean wrenchCanRemove(EntityPlayer entityPlayer)
	{
		return permitsAccess(entityPlayer);
	}

	public boolean permitsAccess(EntityPlayer player)
	{
		if (player == null)
			return false;
		if (owner.equals("null"))
		{
			if (IC2.platform.isSimulating())
			{
				owner = player.username;
				IC2.network.updateTileEntityField(this, "owner");
			}
			return true;
		}
		if (MinecraftServer.getServer() != null && !MinecraftServer.getServer().isDedicatedServer())
			return true;
		else
			return owner.equalsIgnoreCase(player.username);
	}

	public boolean permitsAccess(String username)
	{
		return owner.equals(username);
	}

	public String getUsername()
	{
		return owner;
	}

	public String getInvName()
	{
		return "Trade-O-Mat";
	}

	public ContainerBase getGuiContainer(EntityPlayer entityPlayer)
	{
		if (permitsAccess(entityPlayer))
			return new ContainerTradeOMatOpen(entityPlayer, this);
		else
			return new ContainerTradeOMatClosed(entityPlayer, this);
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		if (permitsAccess(entityPlayer))
			return new GuiTradeOMatOpen(new ContainerTradeOMatOpen(entityPlayer, this), isAdmin);
		else
			return new GuiTradeOMatClosed(new ContainerTradeOMatClosed(entityPlayer, this));
	}

	public void onGuiClosed(EntityPlayer entityplayer)
	{
	}

	public void onNetworkEvent(int event)
	{
		switch (event)
		{
		case 0: // '\0'
			IC2.audioManager.playOnce(this, PositionSpec.Center, "Machines/o-mat.ogg", true, IC2.audioManager.defaultVolume);
			break;

		default:
			IC2.platform.displayError((new StringBuilder()).append("An unknown event type was received over multiplayer.\nThis could happen due to corrupted data or a bug.\n\n(Technical information: event ID ").append(event).append(", tile entity below)\n").append("T: ").append(this).append(" (").append(super.xCoord).append(",").append(super.yCoord).append(",").append(super.zCoord).append(")").toString());
			break;
		}
	}

	public void onNetworkEvent(EntityPlayer player, int event)
	{
		if (event == 0)
		{
			MinecraftServer server = MinecraftServer.getServer();
			if (server.getConfigurationManager().areCommandsAllowed(player.username))
			{
				infinite = !infinite;
				if (!infinite)
					updateStock();
			}
		}
	}
}
