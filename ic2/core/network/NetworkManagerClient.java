// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NetworkManagerClient.java

package ic2.core.network;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import ic2.api.network.*;
import ic2.core.*;
import ic2.core.item.IHandHeldInventory;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

// Referenced classes of package ic2.core.network:
//			NetworkManager, DataEncoder

public class NetworkManagerClient extends NetworkManager
{

	public NetworkManagerClient()
	{
	}

	/**
	 * @deprecated Method requestInitialData is deprecated
	 */

	public void requestInitialData(INetworkDataProvider inetworkdataprovider)
	{
	}

	public void initiateClientItemEvent(ItemStack itemStack, int event)
	{
		try
		{
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			DataOutputStream os = new DataOutputStream(buffer);
			os.writeByte(1);
			os.writeInt(itemStack.itemID);
			os.writeInt(itemStack.getItemDamage());
			os.writeInt(event);
			os.close();
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = "ic2";
			packet.isChunkDataPacket = false;
			packet.data = buffer.toByteArray();
			packet.length = buffer.size();
			PacketDispatcher.sendPacketToServer(packet);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void initiateKeyUpdate(int keyState)
	{
		try
		{
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			DataOutputStream os = new DataOutputStream(buffer);
			os.writeByte(2);
			os.writeInt(keyState);
			os.close();
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = "ic2";
			packet.isChunkDataPacket = false;
			packet.data = buffer.toByteArray();
			packet.length = buffer.size();
			PacketDispatcher.sendPacketToServer(packet);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void initiateClientTileEntityEvent(TileEntity te, int event)
	{
		try
		{
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			DataOutputStream os = new DataOutputStream(buffer);
			os.writeByte(3);
			os.writeInt(te.worldObj.provider.dimensionId);
			os.writeInt(te.xCoord);
			os.writeInt(te.yCoord);
			os.writeInt(te.zCoord);
			os.writeInt(event);
			os.close();
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = "ic2";
			packet.isChunkDataPacket = false;
			packet.data = buffer.toByteArray();
			packet.length = buffer.size();
			PacketDispatcher.sendPacketToServer(packet);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void sendLoginData()
	{
		try
		{
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			buffer.write(4);
			GZIPOutputStream gzip = new GZIPOutputStream(buffer);
			DataOutputStream os = new DataOutputStream(gzip);
			os.writeInt(1);
			os.writeByte(IC2.enableQuantumSpeedOnSprint ? 1 : 0);
			ByteArrayOutputStream buffer2 = new ByteArrayOutputStream();
			IC2.runtimeIdProperties.store(buffer2, "");
			os.writeInt(buffer2.size());
			buffer2.writeTo(os);
			os.close();
			gzip.close();
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = "ic2";
			packet.isChunkDataPacket = false;
			packet.data = buffer.toByteArray();
			packet.length = buffer.size();
			PacketDispatcher.sendPacketToServer(packet);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void onPacketData(INetworkManager network, Packet250CustomPayload packet, Player iplayer)
	{
		ByteArrayInputStream isRaw;
		if (packet.data.length == 0)
			return;
		if (packet.data[0] >= 10)
			super.onPacketData(network, packet, iplayer);
		isRaw = new ByteArrayInputStream(packet.data, 1, packet.data.length - 1);
		packet.data[0];
		JVM INSTR tableswitch 0 5: default 1222
	//	               0 92
	//	               1 444
	//	               2 548
	//	               3 730
	//	               4 825
	//	               5 1097;
		   goto _L1 _L2 _L3 _L4 _L5 _L6 _L7
_L1:
		break MISSING_BLOCK_LABEL_1232;
_L2:
		DataInputStream is;
		World world;
		GZIPInputStream gzip = new GZIPInputStream(isRaw, packet.data.length - 1);
		is = new DataInputStream(gzip);
		int dimensionId = is.readInt();
		world = Minecraft.getMinecraft().theWorld;
		if (world.provider.dimensionId != dimensionId)
			return;
		int x;
		int y;
		int z;
		do
		{
			try
			{
				x = is.readInt();
			}
			catch (EOFException e)
			{
				break;
			}
			y = is.readInt();
			z = is.readInt();
			TileEntity te = world.getBlockTileEntity(x, y, z);
			short fieldNameLength = is.readShort();
			char fieldNameRaw[] = new char[fieldNameLength];
			for (int i = 0; i < fieldNameLength; i++)
				fieldNameRaw[i] = is.readChar();

			String fieldName = new String(fieldNameRaw);
			Field field = null;
			try
			{
				if (te != null)
				{
					Class fieldDeclaringClass = te.getClass();
					do
						try
						{
							field = fieldDeclaringClass.getDeclaredField(fieldName);
						}
						catch (NoSuchFieldException e)
						{
							fieldDeclaringClass = fieldDeclaringClass.getSuperclass();
						}
					while (field == null && fieldDeclaringClass != null);
					if (field == null)
						IC2.log.warning((new StringBuilder()).append("Can't find field ").append(fieldName).append(" in te ").append(te).append(" at ").append(x).append("/").append(y).append("/").append(z).toString());
					else
						field.setAccessible(true);
				}
				Object value = DataEncoder.decode(is);
				if (field != null && te != null)
					field.set(te, value);
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
			if (te instanceof INetworkUpdateListener)
				((INetworkUpdateListener)te).onNetworkUpdate(fieldName);
		} while (true);
		is.close();
		break MISSING_BLOCK_LABEL_1232;
_L3:
		DataInputStream is;
		int x;
		int y;
		int z;
		int event;
		World world;
		is = new DataInputStream(isRaw);
		int dimensionId = is.readInt();
		x = is.readInt();
		y = is.readInt();
		z = is.readInt();
		event = is.readInt();
		world = Minecraft.getMinecraft().theWorld;
		if (world.provider.dimensionId != dimensionId)
			return;
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof INetworkTileEntityEventListener)
			((INetworkTileEntityEventListener)te).onNetworkEvent(event);
		break MISSING_BLOCK_LABEL_1232;
_L4:
		World world;
		is = new DataInputStream(isRaw);
		byte length = is.readByte();
		char usernameRaw[] = new char[length];
		for (int i = 0; i < length; i++)
			usernameRaw[i] = is.readChar();

		String username = new String(usernameRaw);
		int itemId = is.readInt();
		int itemDamage = is.readInt();
		int event = is.readInt();
		world = Minecraft.getMinecraft().theWorld;
		Iterator i$ = world.playerEntities.iterator();
		EntityPlayer entityPlayer;
		do
		{
			if (!i$.hasNext())
				break MISSING_BLOCK_LABEL_1232;
			Object obj = i$.next();
			entityPlayer = (EntityPlayer)obj;
		} while (!entityPlayer.username.equals(username));
		Item item = Item.itemsList[itemId];
		if (item instanceof INetworkItemEventListener)
			((INetworkItemEventListener)item).onNetworkEvent(itemDamage, entityPlayer, event);
		break MISSING_BLOCK_LABEL_1232;
_L5:
		int id;
		int meta;
		is = new DataInputStream(isRaw);
		int dimensionId = is.readInt();
		usernameRaw = is.readInt();
		username = is.readInt();
		itemId = is.readInt();
		id = is.readShort();
		meta = is.readByte();
		world = Minecraft.getMinecraft().theWorld;
		if (world.provider.dimensionId != dimensionId)
			return;
		world.setBlock(usernameRaw, username, itemId, id, meta, 3);
		break MISSING_BLOCK_LABEL_1232;
_L6:
		EntityPlayer entityPlayer;
		boolean isAdmin;
		is = new DataInputStream(isRaw);
		entityPlayer = IC2.platform.getPlayerInstance();
		isAdmin = is.readByte() != 0;
		is.readByte();
		JVM INSTR lookupswitch 2: default 1094
	//	               0: 892
	//	               1: 999;
		   goto _L8 _L9 _L10
_L8:
		break MISSING_BLOCK_LABEL_1232;
_L9:
		int windowId;
		World world;
		int dimensionId = is.readInt();
		itemId = is.readInt();
		id = is.readInt();
		meta = is.readInt();
		windowId = is.readInt();
		world = Minecraft.getMinecraft().theWorld;
		if (world.provider.dimensionId != dimensionId)
			return;
		TileEntity te = world.getBlockTileEntity(itemId, id, meta);
		if (te instanceof IHasGui)
			IC2.platform.launchGuiClient(entityPlayer, (IHasGui)te, isAdmin);
		entityPlayer.openContainer.windowId = windowId;
		break MISSING_BLOCK_LABEL_1232;
_L10:
		int windowId;
		int currentItemPosition = is.readInt();
		windowId = is.readInt();
		if (currentItemPosition != entityPlayer.inventory.currentItem)
			return;
		ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
		if (currentItem != null && (currentItem.getItem() instanceof IHandHeldInventory))
			IC2.platform.launchGuiClient(entityPlayer, ((IHandHeldInventory)currentItem.getItem()).getInventory(entityPlayer, currentItem), isAdmin);
		entityPlayer.openContainer.windowId = windowId;
		break MISSING_BLOCK_LABEL_1232;
_L7:
		double y;
		is = new DataInputStream(isRaw);
		int dimensionId = is.readInt();
		isAdmin = is.readDouble();
		y = is.readDouble();
		meta = is.readDouble();
		world = Minecraft.getMinecraft().theWorld;
		if (world.provider.dimensionId != dimensionId)
			return;
		try
		{
			world.playSoundEffect(isAdmin, y, meta, "random.explode", 4F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
			world.spawnParticle("hugeexplosion", isAdmin, y, meta, 0.0D, 0.0D, 0.0D);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void announceBlockUpdate(World world, int x, int y, int z)
	{
		if (IC2.platform.isSimulating())
			super.announceBlockUpdate(world, x, y, z);
	}
}
