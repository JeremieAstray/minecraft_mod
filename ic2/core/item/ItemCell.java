// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemCell.java

package ic2.core.item;

import ic2.core.*;
import ic2.core.init.InternalName;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item:
//			ItemIC2

public class ItemCell extends ItemIC2
{

	public ItemCell(Configuration config, InternalName internalName)
	{
		super(config, internalName);
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		float f = 1.0F;
		float f1 = ((Entity) (entityplayer)).prevRotationPitch + (((Entity) (entityplayer)).rotationPitch - ((Entity) (entityplayer)).prevRotationPitch) * f;
		float f2 = ((Entity) (entityplayer)).prevRotationYaw + (((Entity) (entityplayer)).rotationYaw - ((Entity) (entityplayer)).prevRotationYaw) * f;
		double d = ((Entity) (entityplayer)).prevPosX + (((Entity) (entityplayer)).posX - ((Entity) (entityplayer)).prevPosX) * (double)f;
		double d1 = (((Entity) (entityplayer)).prevPosY + (((Entity) (entityplayer)).posY - ((Entity) (entityplayer)).prevPosY) * (double)f + 1.6200000000000001D) - (double)((Entity) (entityplayer)).yOffset;
		double d2 = ((Entity) (entityplayer)).prevPosZ + (((Entity) (entityplayer)).posZ - ((Entity) (entityplayer)).prevPosZ) * (double)f;
		Vec3 vec3d = Vec3.createVectorHelper(d, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
		float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
		float f5 = -MathHelper.cos(-f1 * 0.01745329F);
		float f6 = MathHelper.sin(-f1 * 0.01745329F);
		float f7 = f4 * f5;
		float f8 = f6;
		float f9 = f3 * f5;
		double d3 = 5D;
		Vec3 vec3d1 = vec3d.addVector((double)f7 * d3, (double)f8 * d3, (double)f9 * d3);
		MovingObjectPosition movingobjectposition = world.clip(vec3d, vec3d1, true);
		if (movingobjectposition == null)
			return itemstack;
		if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE)
		{
			int i = movingobjectposition.blockX;
			int j = movingobjectposition.blockY;
			int k = movingobjectposition.blockZ;
			if (!world.canMineBlock(entityplayer, i, j, k))
				return itemstack;
			if (world.getBlockId(i, j, k) == Block.waterStill.blockID && world.getBlockMetadata(i, j, k) == 0 && storeCell(Ic2Items.waterCell.copy(), entityplayer))
			{
				world.setBlock(i, j, k, 0, 0, 3);
				itemstack.stackSize--;
				return itemstack;
			}
			if (world.getBlockId(i, j, k) == Block.lavaStill.blockID && world.getBlockMetadata(i, j, k) == 0 && storeCell(Ic2Items.lavaCell.copy(), entityplayer))
			{
				world.setBlock(i, j, k, 0, 0, 3);
				itemstack.stackSize--;
				return itemstack;
			}
		}
		return itemstack;
	}

	public boolean storeCell(ItemStack cell, EntityPlayer player)
	{
		if (player.inventory.addItemStackToInventory(cell))
		{
			if (!IC2.platform.isRendering())
				player.openContainer.detectAndSendChanges();
			return true;
		} else
		{
			return false;
		}
	}
}
