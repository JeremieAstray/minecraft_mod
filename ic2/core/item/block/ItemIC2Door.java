// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemIC2Door.java

package ic2.core.item.block;

import cpw.mods.fml.common.registry.GameRegistry;
import ic2.core.IC2;
import ic2.core.init.DefaultIds;
import ic2.core.init.InternalName;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

public class ItemIC2Door extends ItemDoor
{

	public Block block;

	public ItemIC2Door(Configuration config, InternalName internalName, Block doorblock)
	{
		super(IC2.getItemIdFor(config, internalName, DefaultIds.get(internalName)), Material.iron);
		block = doorblock;
		setUnlocalizedName(internalName.name());
		setCreativeTab(IC2.tabIC2);
		GameRegistry.registerItem(this, internalName.name());
	}

	public void registerIcons(IconRegister iconRegister)
	{
		super.itemIcon = iconRegister.registerIcon((new StringBuilder()).append("ic2:").append(getUnlocalizedName()).toString());
	}

	public String getUnlocalizedName()
	{
		return super.getUnlocalizedName().substring(5);
	}

	public String getUnlocalizedName(ItemStack itemStack)
	{
		return getUnlocalizedName();
	}

	public String getItemDisplayName(ItemStack itemStack)
	{
		return StatCollector.translateToLocal((new StringBuilder()).append("ic2.").append(getUnlocalizedName(itemStack)).toString());
	}

	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, 
			float hitX, float hitY, float hitZ)
	{
		if (side != 1)
			return false;
		y++;
		if (!player.canPlayerEdit(x, y, z, side, itemStack) || !player.canPlayerEdit(x, y + 1, z, side, itemStack))
			return false;
		if (!block.canPlaceBlockAt(world, x, y, z))
		{
			return false;
		} else
		{
			int meta = MathHelper.floor_double((double)(((((Entity) (player)).rotationYaw + 180F) * 4F) / 360F) - 0.5D) & 3;
			placeDoorBlock(world, x, y, z, meta, block);
			itemStack.stackSize--;
			return true;
		}
	}
}
