// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityEnergyOMat.java

package ic2.core.block.personal;

import ic2.api.Direction;
import ic2.api.energy.event.*;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.core.*;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.*;
import ic2.core.network.NetworkManager;
import ic2.core.util.StackUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;

// Referenced classes of package ic2.core.block.personal:
//			IPersonalBlock, ContainerEnergyOMatOpen, ContainerEnergyOMatClosed, GuiEnergyOMatOpen, 
//			GuiEnergyOMatClosed

public class TileEntityEnergyOMat extends TileEntityInventory
	implements IPersonalBlock, IHasGui, IEnergySink, IEnergySource, INetworkClientTileEntityEventListener
{

	public int euOffer;
	public String owner;
	private boolean addedToEnergyNet;
	public int paidFor;
	public int euBuffer;
	private int euBufferMax;
	private int maxOutputRate;
	public final InvSlot demandSlot;
	public final InvSlotConsumableLinked inputSlot;
	public final InvSlotCharge chargeSlot = new InvSlotCharge(this, -1, 1);
	public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade(this, "upgrade", 2, 1);

	public TileEntityEnergyOMat()
	{
		euOffer = 1000;
		owner = "null";
		addedToEnergyNet = false;
		euBufferMax = 10000;
		maxOutputRate = 32;
		demandSlot = new InvSlot(this, "demand", 0, ic2.core.block.invslot.InvSlot.Access.NONE, 1);
		inputSlot = new InvSlotConsumableLinked(this, "input", 1, 1, demandSlot);
	}

	public String getInvName()
	{
		return "Energy-O-Mat";
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		owner = nbttagcompound.getString("owner");
		euOffer = nbttagcompound.getInteger("euOffer");
		paidFor = nbttagcompound.getInteger("paidFor");
		euBuffer = nbttagcompound.getInteger("euBuffer");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setString("owner", owner);
		nbttagcompound.setInteger("euOffer", euOffer);
		nbttagcompound.setInteger("paidFor", paidFor);
		nbttagcompound.setInteger("euBuffer", euBuffer);
	}

	public boolean wrenchCanRemove(EntityPlayer entityPlayer)
	{
		return permitsAccess(entityPlayer);
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
		if (IC2.platform.isSimulating())
		{
			boolean invChanged = false;
			euBufferMax = 10000;
			maxOutputRate = 32;
			chargeSlot.setTier(1);
			if (!upgradeSlot.isEmpty())
				if (upgradeSlot.get().isItemEqual(Ic2Items.energyStorageUpgrade))
					euBufferMax = 10000 * (upgradeSlot.get().stackSize + 1);
				else
				if (upgradeSlot.get().isItemEqual(Ic2Items.transformerUpgrade))
				{
					maxOutputRate = 32 * (int)Math.pow(4D, Math.min(4, upgradeSlot.get().stackSize));
					chargeSlot.setTier(1 + upgradeSlot.get().stackSize);
				}
			ItemStack tradedIn = inputSlot.consumeLinked(true);
			if (tradedIn != null)
			{
				int transferred = StackUtil.distribute(this, tradedIn, true);
				if (transferred == tradedIn.stackSize)
				{
					StackUtil.distribute(this, inputSlot.consumeLinked(false), false);
					paidFor += euOffer;
					invChanged = true;
				}
			}
			if (euBuffer > 0)
			{
				int sent = chargeSlot.charge(euBuffer);
				if (sent > 0)
				{
					euBuffer -= sent;
					invChanged = true;
				}
			}
			if (euBuffer > euBufferMax)
				euBuffer = euBufferMax;
			int min = Math.min(maxOutputRate, euBuffer);
			EnergyTileSourceEvent event = new EnergyTileSourceEvent(this, min);
			MinecraftForge.EVENT_BUS.post(event);
			euBuffer -= min - event.amount;
			if (invChanged)
				onInventoryChanged();
		}
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

	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side)
	{
		return getFacing() != side && permitsAccess(entityPlayer);
	}

	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction)
	{
		return !facingMatchesDirection(direction);
	}

	public boolean facingMatchesDirection(Direction direction)
	{
		return direction.toSideValue() == getFacing();
	}

	public boolean isAddedToEnergyNet()
	{
		return addedToEnergyNet;
	}

	public boolean emitsEnergyTo(TileEntity receiver, Direction direction)
	{
		return facingMatchesDirection(direction);
	}

	public int getMaxEnergyOutput()
	{
		return 32;
	}

	public int demandsEnergy()
	{
		return Math.min(paidFor, euBufferMax - euBuffer);
	}

	public int injectEnergy(Direction directionFrom, int amount)
	{
		int toAdd = Math.min(Math.min(amount, paidFor), euBufferMax - euBuffer);
		paidFor -= toAdd;
		euBuffer += toAdd;
		return amount - toAdd;
	}

	public int getMaxSafeInput()
	{
		return 0x7fffffff;
	}

	public ContainerBase getGuiContainer(EntityPlayer entityPlayer)
	{
		if (permitsAccess(entityPlayer))
			return new ContainerEnergyOMatOpen(entityPlayer, this);
		else
			return new ContainerEnergyOMatClosed(entityPlayer, this);
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		if (permitsAccess(entityPlayer))
			return new GuiEnergyOMatOpen(new ContainerEnergyOMatOpen(entityPlayer, this));
		else
			return new GuiEnergyOMatClosed(new ContainerEnergyOMatClosed(entityPlayer, this));
	}

	public void onGuiClosed(EntityPlayer entityplayer)
	{
	}

	public void onNetworkEvent(EntityPlayer player, int event)
	{
		if (!permitsAccess(player))
			return;
		switch (event)
		{
		case 0: // '\0'
			attemptSet(0xfffe7960);
			break;

		case 1: // '\001'
			attemptSet(-10000);
			break;

		case 2: // '\002'
			attemptSet(-1000);
			break;

		case 3: // '\003'
			attemptSet(-100);
			break;

		case 4: // '\004'
			attemptSet(0x186a0);
			break;

		case 5: // '\005'
			attemptSet(10000);
			break;

		case 6: // '\006'
			attemptSet(1000);
			break;

		case 7: // '\007'
			attemptSet(100);
			break;
		}
	}

	private void attemptSet(int amount)
	{
		euOffer += amount;
		if (euOffer < 100)
			euOffer = 100;
	}
}
