// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NetworkManager.java

package ic2.core.network;

import cpw.mods.fml.common.network.*;
import ic2.api.network.*;
import ic2.core.*;
import ic2.core.item.IHandHeldInventory;
import ic2.core.item.armor.ItemArmorQuantumSuit;
import ic2.core.util.Keyboard;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;

// Referenced classes of package ic2.core.network:
//			DataEncoder, IPlayerItemDataListener

public class NetworkManager
	implements IPacketHandler
{
	public class TileEntityField
	{

		TileEntity te;
		String field;
		EntityPlayerMP target;
		final NetworkManager this$0;

		public boolean equals(Object obj)
		{
			if (obj instanceof TileEntityField)
			{
				TileEntityField tef = (TileEntityField)obj;
				return tef.te == te && tef.field.equals(field);
			} else
			{
				return false;
			}
		}

		public int hashCode()
		{
			return te.hashCode() * 31 ^ field.hashCode();
		}

		TileEntityField(TileEntity te, String field)
		{
			this$0 = NetworkManager.this;
			super();
			target = null;
			this.te = te;
			this.field = field;
		}

		TileEntityField(TileEntity te, String field, EntityPlayerMP target)
		{
			this$0 = NetworkManager.this;
			super();
			this.target = null;
			this.te = te;
			this.field = field;
			this.target = target;
		}
	}


	public static final int updatePeriod = 2;
	private static int maxNetworkedFieldsToUpdate = 4000;
	private static final int maxPacketDataLength = 32766;

	public NetworkManager()
	{
	}

	public void onTick(World world)
	{
		WorldData worldData = WorldData.get(world);
		if (--worldData.ticksLeftToNetworkUpdate == 0)
		{
			sendUpdatePacket(world);
			worldData.ticksLeftToNetworkUpdate = 2;
		}
	}

	public transient void sendPlayerItemData(EntityPlayer player, int slot, Object data[])
	{
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		DataOutputStream os = new DataOutputStream(buffer);
		try
		{
			os.writeByte(10);
			os.writeByte(slot);
			os.writeInt(player.inventory.mainInventory[slot].itemID);
			os.writeShort(data.length);
			Object arr$[] = data;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Object o = arr$[i$];
				DataEncoder.encode(os, o);
			}

			os.close();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "ic2";
		packet.isChunkDataPacket = false;
		packet.data = buffer.toByteArray();
		packet.length = buffer.size();
		if (IC2.platform.isSimulating())
			PacketDispatcher.sendPacketToPlayer(packet, (Player)player);
		else
			PacketDispatcher.sendPacketToServer(packet);
	}

	public void updateTileEntityField(TileEntity te, String field)
	{
		WorldData worldData = WorldData.get(te.worldObj);
		worldData.networkedFieldsToUpdate.add(new TileEntityField(te, field));
		if (worldData.networkedFieldsToUpdate.size() >= maxNetworkedFieldsToUpdate)
			sendUpdatePacket(te.worldObj);
	}

	public void initiateTileEntityEvent(TileEntity te, int event, boolean limitRange)
	{
		int maxDistance = limitRange ? 400 : MinecraftServer.getServer().getConfigurationManager().getEntityViewDistance() + 16;
		World world = te.worldObj;
		Packet250CustomPayload packet = null;
		Iterator i$ = world.playerEntities.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			Object obj = i$.next();
			EntityPlayerMP entityPlayer = (EntityPlayerMP)obj;
			int distanceX = te.xCoord - (int)((Entity) (entityPlayer)).posX;
			int distanceZ = te.zCoord - (int)((Entity) (entityPlayer)).posZ;
			int distance;
			if (limitRange)
				distance = distanceX * distanceX + distanceZ * distanceZ;
			else
				distance = Math.max(Math.abs(distanceX), Math.abs(distanceZ));
			if (distance <= maxDistance)
			{
				if (packet == null)
					try
					{
						ByteArrayOutputStream buffer = new ByteArrayOutputStream();
						DataOutputStream os = new DataOutputStream(buffer);
						os.writeByte(1);
						os.writeInt(world.provider.dimensionId);
						os.writeInt(te.xCoord);
						os.writeInt(te.yCoord);
						os.writeInt(te.zCoord);
						os.writeInt(event);
						os.close();
						packet = new Packet250CustomPayload();
						packet.channel = "ic2";
						packet.isChunkDataPacket = false;
						packet.data = buffer.toByteArray();
						packet.length = buffer.size();
					}
					catch (IOException e)
					{
						throw new RuntimeException(e);
					}
				PacketDispatcher.sendPacketToPlayer(packet, (Player)entityPlayer);
			}
		} while (true);
	}

	public void initiateItemEvent(EntityPlayer player, ItemStack itemStack, int event, boolean limitRange)
	{
		if (player.username.length() > 127)
			return;
		int maxDistance = limitRange ? 400 : MinecraftServer.getServer().getConfigurationManager().getEntityViewDistance() + 16;
		Packet250CustomPayload packet = null;
		Iterator i$ = ((Entity) (player)).worldObj.playerEntities.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			Object obj = i$.next();
			EntityPlayerMP entityPlayer = (EntityPlayerMP)obj;
			int distanceX = (int)((Entity) (player)).posX - (int)((Entity) (entityPlayer)).posX;
			int distanceZ = (int)((Entity) (player)).posZ - (int)((Entity) (entityPlayer)).posZ;
			int distance;
			if (limitRange)
				distance = distanceX * distanceX + distanceZ * distanceZ;
			else
				distance = Math.max(Math.abs(distanceX), Math.abs(distanceZ));
			if (distance <= maxDistance)
			{
				if (packet == null)
					try
					{
						ByteArrayOutputStream buffer = new ByteArrayOutputStream();
						DataOutputStream os = new DataOutputStream(buffer);
						os.writeByte(2);
						os.writeByte(player.username.length());
						os.writeChars(player.username);
						os.writeInt(itemStack.itemID);
						os.writeInt(itemStack.getItemDamage());
						os.writeInt(event);
						os.close();
						packet = new Packet250CustomPayload();
						packet.channel = "ic2";
						packet.isChunkDataPacket = false;
						packet.data = buffer.toByteArray();
						packet.length = buffer.size();
					}
					catch (IOException e)
					{
						throw new RuntimeException(e);
					}
				PacketDispatcher.sendPacketToPlayer(packet, (Player)entityPlayer);
			}
		} while (true);
	}

	public void announceBlockUpdate(World world, int x, int y, int z)
	{
		Packet250CustomPayload packet = null;
		Iterator i$ = world.playerEntities.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			Object obj = i$.next();
			EntityPlayerMP entityPlayer = (EntityPlayerMP)obj;
			int distance = Math.min(Math.abs(x - (int)((Entity) (entityPlayer)).posX), Math.abs(z - (int)((Entity) (entityPlayer)).posZ));
			if (distance <= MinecraftServer.getServer().getConfigurationManager().getEntityViewDistance() + 16)
			{
				if (packet == null)
					try
					{
						ByteArrayOutputStream buffer = new ByteArrayOutputStream();
						DataOutputStream os = new DataOutputStream(buffer);
						os.writeByte(3);
						os.writeInt(world.provider.dimensionId);
						os.writeInt(x);
						os.writeInt(y);
						os.writeInt(z);
						os.writeShort(world.getBlockId(x, y, z));
						os.writeByte(world.getBlockMetadata(x, y, z));
						os.close();
						packet = new Packet250CustomPayload();
						packet.channel = "ic2";
						packet.isChunkDataPacket = true;
						packet.data = buffer.toByteArray();
						packet.length = buffer.size();
					}
					catch (IOException e)
					{
						throw new RuntimeException(e);
					}
				PacketDispatcher.sendPacketToPlayer(packet, (Player)entityPlayer);
			}
		} while (true);
	}

	/**
	 * @deprecated Method requestInitialData is deprecated
	 */

	public void requestInitialData(INetworkDataProvider inetworkdataprovider)
	{
	}

	public void initiateClientItemEvent(ItemStack itemstack, int i)
	{
	}

	public void initiateClientTileEntityEvent(TileEntity tileentity, int i)
	{
	}

	public void initiateGuiDisplay(EntityPlayerMP entityPlayer, IHasGui inventory, int windowId)
	{
		try
		{
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			DataOutputStream os = new DataOutputStream(buffer);
			os.writeByte(4);
			MinecraftServer server = MinecraftServer.getServer();
			boolean isAdmin = server.getConfigurationManager().areCommandsAllowed(((EntityPlayer) (entityPlayer)).username);
			os.writeByte(isAdmin ? 1 : 0);
			if (inventory instanceof TileEntity)
			{
				TileEntity te = (TileEntity)inventory;
				os.writeByte(0);
				os.writeInt(te.worldObj.provider.dimensionId);
				os.writeInt(te.xCoord);
				os.writeInt(te.yCoord);
				os.writeInt(te.zCoord);
			} else
			if (((EntityPlayer) (entityPlayer)).inventory.getCurrentItem() != null && (((EntityPlayer) (entityPlayer)).inventory.getCurrentItem().getItem() instanceof IHandHeldInventory))
			{
				os.writeByte(1);
				os.writeInt(((EntityPlayer) (entityPlayer)).inventory.currentItem);
			} else
			{
				IC2.platform.displayError((new StringBuilder()).append("An unknown GUI type was attempted to be displayed.\nThis could happen due to corrupted data from a player or a bug.\n\n(Technical information: ").append(inventory).append(")").toString());
			}
			os.writeInt(windowId);
			os.close();
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = "ic2";
			packet.isChunkDataPacket = false;
			packet.data = buffer.toByteArray();
			packet.length = buffer.size();
			PacketDispatcher.sendPacketToPlayer(packet, (Player)entityPlayer);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void sendInitialData(TileEntity te, EntityPlayerMP player)
	{
		if (te instanceof INetworkDataProvider)
		{
			WorldData worldData = WorldData.get(te.worldObj);
			Iterator i$ = ((INetworkDataProvider)te).getNetworkedFields().iterator();
			do
			{
				if (!i$.hasNext())
					break;
				String field = (String)i$.next();
				worldData.networkedFieldsToUpdate.add(new TileEntityField(te, field, player));
				if (worldData.networkedFieldsToUpdate.size() >= maxNetworkedFieldsToUpdate)
					sendUpdatePacket(te.worldObj);
			} while (true);
		}
	}

	private void sendUpdatePacket(World world)
	{
		WorldData worldData = WorldData.get(world);
		if (worldData.networkedFieldsToUpdate.isEmpty())
			return;
		EntityPlayerMP entityPlayer;
		for (Iterator i$ = world.playerEntities.iterator(); i$.hasNext(); sendUpdatePacket(((Collection) (worldData.networkedFieldsToUpdate)), ((EntityPlayer) (entityPlayer))))
		{
			Object obj = i$.next();
			entityPlayer = (EntityPlayerMP)obj;
		}

		worldData.networkedFieldsToUpdate.clear();
	}

	private void sendUpdatePacket(Collection networkedFieldsToUpdate, EntityPlayer entityPlayer)
	{
		byte fieldData[] = getFieldData(networkedFieldsToUpdate, entityPlayer);
		if (fieldData.length > 32766)
		{
			List networkedFieldsToUpdateList;
			if (networkedFieldsToUpdate instanceof List)
				networkedFieldsToUpdateList = (List)networkedFieldsToUpdate;
			else
				networkedFieldsToUpdateList = new ArrayList(networkedFieldsToUpdate);
			do
			{
				maxNetworkedFieldsToUpdate = (Math.min(networkedFieldsToUpdateList.size(), maxNetworkedFieldsToUpdate) * 7) / 8;
				fieldData = getFieldData(networkedFieldsToUpdateList.subList(0, maxNetworkedFieldsToUpdate), entityPlayer);
			} while (fieldData.length > 32766);
			sendUpdatePacket(((Collection) (networkedFieldsToUpdateList.subList(maxNetworkedFieldsToUpdate, networkedFieldsToUpdateList.size()))), entityPlayer);
		}
		if (fieldData.length <= 1)
		{
			return;
		} else
		{
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = "ic2";
			packet.isChunkDataPacket = true;
			packet.data = fieldData;
			packet.length = fieldData.length;
			PacketDispatcher.sendPacketToPlayer(packet, (Player)entityPlayer);
			return;
		}
	}

	public void onPacketData(INetworkManager network, Packet250CustomPayload packet, Player iplayer)
	{
		if (packet.data.length == 0)
			return;
		EntityPlayer player = (EntityPlayer)iplayer;
		ByteArrayInputStream isRaw = new ByteArrayInputStream(packet.data, 1, packet.data.length - 1);
		try
		{
label0:
			switch (packet.data[0])
			{
			case 0: // '\0'
			case 5: // '\005'
			case 6: // '\006'
			case 7: // '\007'
			case 8: // '\b'
			case 9: // '\t'
			default:
				break;

			case 1: // '\001'
			{
				DataInputStream is = new DataInputStream(isRaw);
				int itemId = is.readInt();
				int itemDamage = is.readInt();
				int event = is.readInt();
				if (itemId >= Item.itemsList.length)
					break;
				Item item = Item.itemsList[itemId];
				if (item instanceof INetworkItemEventListener)
					((INetworkItemEventListener)item).onNetworkEvent(itemDamage, player, event);
				break;
			}

			case 2: // '\002'
			{
				DataInputStream is = new DataInputStream(isRaw);
				int keyState = is.readInt();
				IC2.keyboard.processKeyUpdate(player, keyState);
				break;
			}

			case 3: // '\003'
			{
				DataInputStream is = new DataInputStream(isRaw);
				int dimensionId = is.readInt();
				int x = is.readInt();
				int y = is.readInt();
				int z = is.readInt();
				int event = is.readInt();
				net.minecraft.world.WorldServer arr$[] = DimensionManager.getWorlds();
				int len$ = arr$.length;
				int i$ = 0;
				do
				{
					if (i$ >= len$)
						break label0;
					World world = arr$[i$];
					if (dimensionId == world.provider.dimensionId)
					{
						TileEntity te = world.getBlockTileEntity(x, y, z);
						if (te instanceof INetworkClientTileEntityEventListener)
							((INetworkClientTileEntityEventListener)te).onNetworkEvent(player, event);
						break label0;
					}
					i$++;
				} while (true);
			}

			case 4: // '\004'
			{
				GZIPInputStream gzip = new GZIPInputStream(isRaw, packet.data.length - 1);
				DataInputStream is = new DataInputStream(gzip);
				int clientNetworkProtocolVersion = is.readInt();
				if (clientNetworkProtocolVersion != 1)
					((EntityPlayerMP)player).playerNetServerHandler.kickPlayerFromServer((new StringBuilder()).append("IC2 network protocol version mismatch (expected 1 (1.118.401-lf), got ").append(clientNetworkProtocolVersion).append(")").toString());
				boolean enableQuantumSpeedOnSprint = is.readByte() != 0;
				ItemArmorQuantumSuit.enableQuantumSpeedOnSprintMap.put(player, Boolean.valueOf(enableQuantumSpeedOnSprint));
				is.readInt();
				Properties clientRuntimeIdProperties = new Properties();
				clientRuntimeIdProperties.load(is);
				is.close();
				Iterator i$ = IC2.runtimeIdProperties.entrySet().iterator();
				do
				{
					String key;
					String value;
					String section;
					do
					{
						int separatorPos;
						do
						{
							if (!i$.hasNext())
								break label0;
							java.util.Map.Entry mapEntry = (java.util.Map.Entry)i$.next();
							key = (String)mapEntry.getKey();
							value = (String)mapEntry.getValue();
							if (!clientRuntimeIdProperties.containsKey(key))
							{
								((EntityPlayerMP)player).playerNetServerHandler.kickPlayerFromServer((new StringBuilder()).append("IC2 id value missing (").append(key).append(")").toString());
								break label0;
							}
							separatorPos = key.indexOf('.');
						} while (separatorPos == -1);
						section = key.substring(0, separatorPos);
						key.substring(separatorPos + 1);
					} while (!section.equals("block") && !section.equals("item") || value.equals(clientRuntimeIdProperties.get(key)));
					((EntityPlayerMP)player).playerNetServerHandler.kickPlayerFromServer((new StringBuilder()).append("IC2 id mismatch (").append(key).append(": expected ").append(value).append(", got ").append(clientRuntimeIdProperties.get(key)).append(")").toString());
				} while (true);
			}

			case 10: // '\n'
			{
				DataInputStream is = new DataInputStream(isRaw);
				int slot = is.readByte();
				int itemId = is.readInt();
				int dataCount = is.readShort();
				Object data[] = new Object[dataCount];
				for (int i = 0; i < dataCount; i++)
					data[i] = DataEncoder.decode(is);

				if (slot < 0 || slot > 9)
					break;
				ItemStack itemStack = player.inventory.mainInventory[slot];
				if (itemStack == null || itemStack.itemID != itemId)
					break;
				Item item = Item.itemsList[itemStack.itemID];
				if (item instanceof IPlayerItemDataListener)
					((IPlayerItemDataListener)item).onPlayerItemNetworkData(player, slot, data);
				break;
			}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void initiateKeyUpdate(int i)
	{
	}

	public void sendLoginData()
	{
	}

	public void initiateExplosionEffect(World world, double x, double y, double z)
	{
		try
		{
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			DataOutputStream os = new DataOutputStream(buffer);
			os.writeByte(5);
			os.writeInt(world.provider.dimensionId);
			os.writeDouble(x);
			os.writeDouble(y);
			os.writeDouble(z);
			os.close();
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = "ic2";
			packet.isChunkDataPacket = false;
			packet.data = buffer.toByteArray();
			packet.length = buffer.size();
			Iterator i$ = world.playerEntities.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				Object player = i$.next();
				EntityPlayerMP entityPlayer = (EntityPlayerMP)player;
				if (entityPlayer.getDistanceSq(x, y, z) < 128D)
					PacketDispatcher.sendPacketToPlayer(packet, (Player)entityPlayer);
			} while (true);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private byte[] getFieldData(Collection networkedFieldsToUpdate, EntityPlayer entityPlayer)
	{
		ByteArrayOutputStream buffer;
		buffer = new ByteArrayOutputStream();
		buffer.write(0);
		GZIPOutputStream gzip = new GZIPOutputStream(buffer);
		DataOutputStream os = new DataOutputStream(gzip);
		os.writeInt(((Entity) (entityPlayer)).worldObj.provider.dimensionId);
		Iterator i$ = networkedFieldsToUpdate.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			TileEntityField tef = (TileEntityField)i$.next();
			if (!tef.te.isInvalid() && tef.te.worldObj == ((Entity) (entityPlayer)).worldObj && (tef.target == null || tef.target == entityPlayer))
			{
				int distance = Math.min(Math.abs(tef.te.xCoord - (int)((Entity) (entityPlayer)).posX), Math.abs(tef.te.zCoord - (int)((Entity) (entityPlayer)).posZ));
				if (distance <= MinecraftServer.getServer().getConfigurationManager().getEntityViewDistance() + 16)
				{
					os.writeInt(tef.te.xCoord);
					os.writeInt(tef.te.yCoord);
					os.writeInt(tef.te.zCoord);
					os.writeShort(tef.field.length());
					os.writeChars(tef.field);
					Field field = null;
					try
					{
						Class fieldDeclaringClass = tef.te.getClass();
						do
							try
							{
								field = fieldDeclaringClass.getDeclaredField(tef.field);
							}
							catch (NoSuchFieldException e)
							{
								fieldDeclaringClass = fieldDeclaringClass.getSuperclass();
							}
						while (field == null && fieldDeclaringClass != null);
						if (field == null)
							throw new NoSuchFieldException(tef.field);
						field.setAccessible(true);
						DataEncoder.encode(os, field.get(tef.te));
					}
					catch (Exception e)
					{
						throw new RuntimeException(e);
					}
				}
			}
		} while (true);
		os.close();
		gzip.close();
		return buffer.toByteArray();
		IOException e;
		e;
		throw new RuntimeException(e);
	}

}
