// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityPersonalChestBc32x.java

package ic2.bcIntegration.core;

import buildcraft.api.inventory.ISecuredInventory;
import ic2.core.block.personal.TileEntityPersonalChest;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityPersonalChestBc32x extends TileEntityPersonalChest
	implements ISecuredInventory
{

	public TileEntityPersonalChestBc32x()
	{
	}

	public boolean canAccess(String name)
	{
		MinecraftServer server = MinecraftServer.getServer();
		return name.equalsIgnoreCase(owner) || name.equalsIgnoreCase(server.getServerOwner()) || server.getConfigurationManager().areCommandsAllowed(name);
	}

	public void prepareTransaction(ForgeDirection forgedirection, String s)
	{
	}
}
