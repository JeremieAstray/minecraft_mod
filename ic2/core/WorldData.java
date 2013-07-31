// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WorldData.java

package ic2.core;

import ic2.core.network.NetworkManager;
import java.util.*;
import net.minecraft.world.World;

// Referenced classes of package ic2.core:
//			EnergyNet

public class WorldData
{

	private static Map mapping = new WeakHashMap();
	public Queue singleTickCallbacks;
	public Set continuousTickCallbacks;
	public boolean continuousTickCallbacksInUse;
	public List continuousTickCallbacksToAdd;
	public List continuousTickCallbacksToRemove;
	public EnergyNet energyNet;
	public Set networkedFieldsToUpdate;
	public int ticksLeftToNetworkUpdate;

	public WorldData()
	{
		singleTickCallbacks = new ArrayDeque();
		continuousTickCallbacks = new HashSet();
		continuousTickCallbacksInUse = false;
		continuousTickCallbacksToAdd = new ArrayList();
		continuousTickCallbacksToRemove = new ArrayList();
		energyNet = new EnergyNet();
		networkedFieldsToUpdate = new HashSet();
		ticksLeftToNetworkUpdate = 2;
	}

	public static WorldData get(World world)
	{
		if (world == null)
			throw new IllegalArgumentException("world is null");
		WorldData ret = (WorldData)mapping.get(world);
		if (ret == null)
		{
			ret = new WorldData();
			mapping.put(world, ret);
		}
		return ret;
	}

	public static void onWorldUnload(World world)
	{
		mapping.remove(world);
	}

}
