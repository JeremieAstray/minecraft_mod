// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Keyboard.java

package ic2.core.util;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;

public class Keyboard
{

	private Map altKeyState;
	private Map boostKeyState;
	private Map forwardKeyState;
	private Map modeSwitchKeyState;
	private Map jumpKeyState;
	private Map sideinventoryKeyState;

	public Keyboard()
	{
		altKeyState = new HashMap();
		boostKeyState = new HashMap();
		forwardKeyState = new HashMap();
		modeSwitchKeyState = new HashMap();
		jumpKeyState = new HashMap();
		sideinventoryKeyState = new HashMap();
	}

	public boolean isAltKeyDown(EntityPlayer player)
	{
		if (altKeyState.containsKey(player))
			return ((Boolean)altKeyState.get(player)).booleanValue();
		else
			return false;
	}

	public boolean isBoostKeyDown(EntityPlayer player)
	{
		if (boostKeyState.containsKey(player))
			return ((Boolean)boostKeyState.get(player)).booleanValue();
		else
			return false;
	}

	public boolean isForwardKeyDown(EntityPlayer player)
	{
		if (forwardKeyState.containsKey(player))
			return ((Boolean)forwardKeyState.get(player)).booleanValue();
		else
			return false;
	}

	public boolean isJumpKeyDown(EntityPlayer player)
	{
		if (jumpKeyState.containsKey(player))
			return ((Boolean)jumpKeyState.get(player)).booleanValue();
		else
			return false;
	}

	public boolean isModeSwitchKeyDown(EntityPlayer player)
	{
		if (modeSwitchKeyState.containsKey(player))
			return ((Boolean)modeSwitchKeyState.get(player)).booleanValue();
		else
			return false;
	}

	public boolean isSideinventoryKeyDown(EntityPlayer player)
	{
		if (sideinventoryKeyState.containsKey(player))
			return ((Boolean)sideinventoryKeyState.get(player)).booleanValue();
		else
			return false;
	}

	public boolean isSneakKeyDown(EntityPlayer player)
	{
		return player.isSneaking();
	}

	public void sendKeyUpdate()
	{
	}

	public void processKeyUpdate(EntityPlayer player, int keyState)
	{
		altKeyState.put(player, Boolean.valueOf((keyState & 1) != 0));
		boostKeyState.put(player, Boolean.valueOf((keyState & 2) != 0));
		forwardKeyState.put(player, Boolean.valueOf((keyState & 4) != 0));
		modeSwitchKeyState.put(player, Boolean.valueOf((keyState & 8) != 0));
		jumpKeyState.put(player, Boolean.valueOf((keyState & 0x10) != 0));
		sideinventoryKeyState.put(player, Boolean.valueOf((keyState & 0x20) != 0));
	}

	public void removePlayerReferences(EntityPlayer player)
	{
		altKeyState.remove(player);
		boostKeyState.remove(player);
		forwardKeyState.remove(player);
		modeSwitchKeyState.remove(player);
		jumpKeyState.remove(player);
		sideinventoryKeyState.remove(player);
	}
}
