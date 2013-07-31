// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityPersonalChest.java

package ic2.core.block.personal;

import ic2.core.*;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.network.NetworkManager;
import java.util.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

// Referenced classes of package ic2.core.block.personal:
//			IPersonalBlock, ContainerPersonalChest, GuiPersonalChest

public class TileEntityPersonalChest extends TileEntityInventory
	implements IPersonalBlock, IHasGui
{

	private int ticksSinceSync;
	private int numUsingPlayers;
	public float lidAngle;
	public float prevLidAngle;
	public String owner;
	public final InvSlot contentSlot;

	public TileEntityPersonalChest()
	{
		owner = "null";
		contentSlot = new InvSlot(this, "content", 0, ic2.core.block.invslot.InvSlot.Access.NONE, 54);
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		owner = nbttagcompound.getString("owner");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setString("owner", owner);
	}

	public String getInvName()
	{
		return "Personal Safe";
	}

	public boolean enableUpdateEntity()
	{
		return true;
	}

	public void updateEntity()
	{
		if ((++ticksSinceSync % 20) * 4 == 0 && IC2.platform.isSimulating())
			syncNumUsingPlayers();
		prevLidAngle = lidAngle;
		float var1 = 0.1F;
		if (numUsingPlayers > 0 && lidAngle == 0.0F)
		{
			double var2 = (double)super.xCoord + 0.5D;
			double var4 = (double)super.zCoord + 0.5D;
			super.worldObj.playSoundEffect(var2, (double)super.yCoord + 0.5D, var4, "random.chestopen", 0.5F, super.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}
		if (numUsingPlayers == 0 && lidAngle > 0.0F || numUsingPlayers > 0 && lidAngle < 1.0F)
		{
			float var8 = lidAngle;
			if (numUsingPlayers > 0)
				lidAngle += var1;
			else
				lidAngle -= var1;
			if (lidAngle > 1.0F)
				lidAngle = 1.0F;
			float var3 = 0.5F;
			if (lidAngle < var3 && var8 >= var3)
			{
				double var4 = (double)super.xCoord + 0.5D;
				double var6 = (double)super.zCoord + 0.5D;
				super.worldObj.playSoundEffect(var4, (double)super.yCoord + 0.5D, var6, "random.chestclosed", 0.5F, super.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}
			if (lidAngle < 0.0F)
				lidAngle = 0.0F;
		}
	}

	public void openChest()
	{
		numUsingPlayers++;
		syncNumUsingPlayers();
	}

	public void closeChest()
	{
		numUsingPlayers--;
		syncNumUsingPlayers();
	}

	public boolean receiveClientEvent(int event, int data)
	{
		if (event == 1)
		{
			numUsingPlayers = data;
			return true;
		} else
		{
			return false;
		}
	}

	private void syncNumUsingPlayers()
	{
		super.worldObj.addBlockEvent(super.xCoord, super.yCoord, super.zCoord, super.worldObj.getBlockId(super.xCoord, super.yCoord, super.zCoord), 1, numUsingPlayers);
	}

	public List getNetworkedFields()
	{
		List ret = new Vector(1);
		ret.add("owner");
		ret.addAll(super.getNetworkedFields());
		return ret;
	}

	public boolean wrenchCanRemove(EntityPlayer entityPlayer)
	{
		if (!permitsAccess(entityPlayer))
			return false;
		if (!contentSlot.isEmpty())
		{
			IC2.platform.messagePlayer(entityPlayer, "Can't wrench non-empty safe", new Object[0]);
			return false;
		} else
		{
			return true;
		}
	}

	public boolean permitsAccess(EntityPlayer player)
	{
		if (player == null)
			return false;
		if (owner.equals("null"))
		{
			owner = player.username;
			IC2.network.updateTileEntityField(this, "owner");
			return true;
		}
		if (IC2.platform.isSimulating())
		{
			MinecraftServer server = MinecraftServer.getServer();
			if (player.username.equals(server.getServerOwner()) || server.getConfigurationManager().areCommandsAllowed(player.username))
				return true;
		}
		if (owner.equalsIgnoreCase(player.username))
			return true;
		if (IC2.platform.isSimulating())
			IC2.platform.messagePlayer(player, (new StringBuilder()).append("This safe is owned by ").append(owner).toString(), new Object[0]);
		return false;
	}

	public boolean permitsAccess(String username)
	{
		return owner.equals(username);
	}

	public String getUsername()
	{
		return owner;
	}

	public ContainerBase getGuiContainer(EntityPlayer entityPlayer)
	{
		return new ContainerPersonalChest(entityPlayer, this);
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		return new GuiPersonalChest(new ContainerPersonalChest(entityPlayer, this));
	}

	public void onGuiClosed(EntityPlayer entityplayer)
	{
	}
}
