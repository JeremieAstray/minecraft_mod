// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MappedIconProvider.java

package ic2.bcIntegration.core;

import buildcraft.api.core.IIconProvider;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class MappedIconProvider
	implements IIconProvider
{

	private final ItemStack mappedItemStacks[];

	public transient MappedIconProvider(ItemStack mappedItemStacks[])
	{
		this.mappedItemStacks = mappedItemStacks;
	}

	public Icon getIcon(int iconIndex)
	{
		return mappedItemStacks[iconIndex].getIconIndex();
	}

	public void registerIcons(IconRegister iconregister)
	{
	}
}
