// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IHasGui.java

package ic2.core;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

// Referenced classes of package ic2.core:
//			ContainerBase

public interface IHasGui
	extends IInventory
{

	public abstract ContainerBase getGuiContainer(EntityPlayer entityplayer);

	public abstract GuiScreen getGui(EntityPlayer entityplayer, boolean flag);

	public abstract void onGuiClosed(EntityPlayer entityplayer);
}
