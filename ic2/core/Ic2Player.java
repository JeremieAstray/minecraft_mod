// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Ic2Player.java

package ic2.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class Ic2Player extends EntityPlayer
{

	public Ic2Player(World world)
	{
		super(world, "[IC2]");
	}

	public boolean canCommandSenderUseCommand(int i, String s)
	{
		return false;
	}

	public ChunkCoordinates getPlayerCoordinates()
	{
		return null;
	}

	public void sendChatToPlayer(ChatMessageComponent chatmessagecomponent1)
	{
	}
}
