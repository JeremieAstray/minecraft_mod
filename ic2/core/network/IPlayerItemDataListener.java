// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IPlayerItemDataListener.java

package ic2.core.network;

import net.minecraft.entity.player.EntityPlayer;

public interface IPlayerItemDataListener
{

	public transient abstract void onPlayerItemNetworkData(EntityPlayer entityplayer, int i, Object aobj[]);
}
