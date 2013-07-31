// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IWrenchable.java

package ic2.api.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IWrenchable
{

	public abstract boolean wrenchCanSetFacing(EntityPlayer entityplayer, int i);

	public abstract short getFacing();

	public abstract void setFacing(short word0);

	public abstract boolean wrenchCanRemove(EntityPlayer entityplayer);

	public abstract float getWrenchDropRate();

	public abstract ItemStack getWrenchDrop(EntityPlayer entityplayer);
}
