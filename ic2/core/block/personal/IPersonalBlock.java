// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IPersonalBlock.java

package ic2.core.block.personal;

import net.minecraft.entity.player.EntityPlayer;

public interface IPersonalBlock
{

	public abstract boolean permitsAccess(EntityPlayer entityplayer);

	public abstract boolean permitsAccess(String s);

	public abstract String getUsername();
}
