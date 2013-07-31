// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemCropnalyzer.java

package ic2.core.item.tool;

import ic2.core.*;
import ic2.core.init.InternalName;
import ic2.core.item.IHandHeldInventory;
import ic2.core.item.ItemIC2;
import ic2.core.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.tool:
//			HandHeldCropnalyzer, ContainerCropnalyzer

public class ItemCropnalyzer extends ItemIC2
	implements IHandHeldInventory
{

	public ItemCropnalyzer(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		setMaxStackSize(1);
	}

	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		if (IC2.platform.isSimulating())
			IC2.platform.launchGui(entityPlayer, getInventory(entityPlayer, itemStack));
		return itemStack;
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.uncommon;
	}

	public IHasGui getInventory(EntityPlayer entityPlayer, ItemStack itemStack)
	{
		return new HandHeldCropnalyzer(entityPlayer, itemStack);
	}

	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player)
	{
		if (item != null && (player.openContainer instanceof ContainerCropnalyzer))
		{
			HandHeldCropnalyzer cropnalyzer = ((ContainerCropnalyzer)player.openContainer).cropnalyzer;
			NBTTagCompound nbtTagCompound = StackUtil.getOrCreateNbtData(item);
			if (cropnalyzer.matchesUid(nbtTagCompound.getInteger("uid")))
				player.closeScreen();
		}
		return true;
	}
}
