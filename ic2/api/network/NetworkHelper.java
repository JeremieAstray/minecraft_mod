// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NetworkHelper.java

package ic2.api.network;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

// Referenced classes of package ic2.api.network:
//			INetworkDataProvider

public final class NetworkHelper
{

	private static Object instance;
	private static Method NetworkManager_updateTileEntityField;
	private static Method NetworkManager_initiateTileEntityEvent;
	private static Method NetworkManager_initiateItemEvent;
	private static Method NetworkManager_announceBlockUpdate;
	private static Method NetworkManager_initiateClientTileEntityEvent;
	private static Method NetworkManager_initiateClientItemEvent;

	public NetworkHelper()
	{
	}

	public static void updateTileEntityField(TileEntity te, String field)
	{
		try
		{
			if (NetworkManager_updateTileEntityField == null)
				NetworkManager_updateTileEntityField = Class.forName((new StringBuilder()).append(getPackage()).append(".core.network.NetworkManager").toString()).getMethod("updateTileEntityField", new Class[] {
					net/minecraft/tileentity/TileEntity, java/lang/String
				});
			if (instance == null)
				instance = getInstance();
			NetworkManager_updateTileEntityField.invoke(instance, new Object[] {
				te, field
			});
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public static void initiateTileEntityEvent(TileEntity te, int event, boolean limitRange)
	{
		try
		{
			if (NetworkManager_initiateTileEntityEvent == null)
				NetworkManager_initiateTileEntityEvent = Class.forName((new StringBuilder()).append(getPackage()).append(".core.network.NetworkManager").toString()).getMethod("initiateTileEntityEvent", new Class[] {
					net/minecraft/tileentity/TileEntity, Integer.TYPE, Boolean.TYPE
				});
			if (instance == null)
				instance = getInstance();
			NetworkManager_initiateTileEntityEvent.invoke(instance, new Object[] {
				te, Integer.valueOf(event), Boolean.valueOf(limitRange)
			});
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public static void initiateItemEvent(EntityPlayer player, ItemStack itemStack, int event, boolean limitRange)
	{
		try
		{
			if (NetworkManager_initiateItemEvent == null)
				NetworkManager_initiateItemEvent = Class.forName((new StringBuilder()).append(getPackage()).append(".core.network.NetworkManager").toString()).getMethod("initiateItemEvent", new Class[] {
					net/minecraft/entity/player/EntityPlayer, net/minecraft/item/ItemStack, Integer.TYPE, Boolean.TYPE
				});
			if (instance == null)
				instance = getInstance();
			NetworkManager_initiateItemEvent.invoke(instance, new Object[] {
				player, itemStack, Integer.valueOf(event), Boolean.valueOf(limitRange)
			});
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public static void announceBlockUpdate(World world, int x, int y, int z)
	{
		try
		{
			if (NetworkManager_announceBlockUpdate == null)
				NetworkManager_announceBlockUpdate = Class.forName((new StringBuilder()).append(getPackage()).append(".core.network.NetworkManager").toString()).getMethod("announceBlockUpdate", new Class[] {
					net/minecraft/world/World, Integer.TYPE, Integer.TYPE, Integer.TYPE
				});
			if (instance == null)
				instance = getInstance();
			NetworkManager_announceBlockUpdate.invoke(instance, new Object[] {
				world, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z)
			});
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * @deprecated Method requestInitialData is deprecated
	 */

	public static void requestInitialData(INetworkDataProvider inetworkdataprovider)
	{
	}

	public static void initiateClientTileEntityEvent(TileEntity te, int event)
	{
		try
		{
			if (NetworkManager_initiateClientTileEntityEvent == null)
				NetworkManager_initiateClientTileEntityEvent = Class.forName((new StringBuilder()).append(getPackage()).append(".core.network.NetworkManager").toString()).getMethod("initiateClientTileEntityEvent", new Class[] {
					net/minecraft/tileentity/TileEntity, Integer.TYPE
				});
			if (instance == null)
				instance = getInstance();
			NetworkManager_initiateClientTileEntityEvent.invoke(instance, new Object[] {
				te, Integer.valueOf(event)
			});
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public static void initiateClientItemEvent(ItemStack itemStack, int event)
	{
		try
		{
			if (NetworkManager_initiateClientItemEvent == null)
				NetworkManager_initiateClientItemEvent = Class.forName((new StringBuilder()).append(getPackage()).append(".core.network.NetworkManager").toString()).getMethod("initiateClientItemEvent", new Class[] {
					net/minecraft/item/ItemStack, Integer.TYPE
				});
			if (instance == null)
				instance = getInstance();
			NetworkManager_initiateClientItemEvent.invoke(instance, new Object[] {
				itemStack, Integer.valueOf(event)
			});
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private static String getPackage()
	{
		Package pkg = ic2/api/network/NetworkHelper.getPackage();
		if (pkg != null)
		{
			String packageName = pkg.getName();
			return packageName.substring(0, packageName.length() - ".api.network".length());
		} else
		{
			return "ic2";
		}
	}

	private static Object getInstance()
	{
		return Class.forName((new StringBuilder()).append(getPackage()).append(".core.IC2").toString()).getDeclaredField("network").get(null);
		Throwable e;
		e;
		throw new RuntimeException(e);
	}
}
