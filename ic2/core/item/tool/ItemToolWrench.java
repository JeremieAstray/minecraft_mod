// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemToolWrench.java

package ic2.core.item.tool;

import ic2.api.tile.IWrenchable;
import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.audio.AudioManager;
import ic2.core.audio.PositionSpec;
import ic2.core.block.machine.tileentity.TileEntityTerra;
import ic2.core.init.InternalName;
import ic2.core.item.ItemIC2;
import ic2.core.util.Keyboard;
import ic2.core.util.StackUtil;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.ForgeDirection;

public class ItemToolWrench extends ItemIC2
{

	public ItemToolWrench(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		setMaxDamage(160);
		setMaxStackSize(1);
	}

	public boolean canTakeDamage(ItemStack stack, int amount)
	{
		return true;
	}

	public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, 
			float hitX, float hitY, float hitZ)
	{
		if (!canTakeDamage(itemstack, 1))
			return false;
		int blockId = world.getBlockId(x, y, z);
		Block block = Block.blocksList[blockId];
		if (block == null)
			return false;
		int metaData = world.getBlockMetadata(x, y, z);
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity instanceof TileEntityTerra)
		{
			TileEntityTerra tileEntityTerra = (TileEntityTerra)tileEntity;
			if (tileEntityTerra.ejectBlueprint())
			{
				if (IC2.platform.isSimulating())
					damage(itemstack, 1, entityPlayer);
				if (IC2.platform.isRendering())
					IC2.audioManager.playOnce(entityPlayer, PositionSpec.Hand, "Tools/wrench.ogg", true, IC2.audioManager.defaultVolume);
				return IC2.platform.isSimulating();
			}
		}
		if (tileEntity instanceof IWrenchable)
		{
			IWrenchable wrenchable = (IWrenchable)tileEntity;
			if (IC2.keyboard.isAltKeyDown(entityPlayer))
			{
				int step = 1;
				do
				{
					if (step >= 6)
						break;
					if (entityPlayer.isSneaking())
						side = ((wrenchable.getFacing() + 6) - step) % 6;
					else
						side = (wrenchable.getFacing() + step) % 6;
					if (wrenchable.wrenchCanSetFacing(entityPlayer, side))
						break;
					step++;
				} while (true);
			} else
			if (entityPlayer.isSneaking())
				side += (side % 2) * -2 + 1;
			if (wrenchable.wrenchCanSetFacing(entityPlayer, side))
			{
				if (IC2.platform.isSimulating())
				{
					wrenchable.setFacing((short)side);
					damage(itemstack, 1, entityPlayer);
				}
				if (IC2.platform.isRendering())
					IC2.audioManager.playOnce(entityPlayer, PositionSpec.Hand, "Tools/wrench.ogg", true, IC2.audioManager.defaultVolume);
				return IC2.platform.isSimulating();
			}
			if (canTakeDamage(itemstack, 10) && wrenchable.wrenchCanRemove(entityPlayer))
			{
				if (IC2.platform.isSimulating())
				{
					if (IC2.enableLoggingWrench)
					{
						String blockName = tileEntity.getClass().getName().replace("TileEntity", "");
						IC2.log.log(Level.INFO, (new StringBuilder()).append("Player ").append(entityPlayer.username).append(" used the wrench to remove the ").append(blockName).append(" (").append(blockId).append("-").append(metaData).append(") at ").append(x).append("/").append(y).append("/").append(z).toString());
					}
					boolean dropOriginalBlock = false;
					if (wrenchable.getWrenchDropRate() < 1.0F && overrideWrenchSuccessRate(itemstack))
					{
						if (!canTakeDamage(itemstack, 200))
						{
							IC2.platform.messagePlayer(entityPlayer, "Not enough energy for lossless wrench operation", new Object[0]);
							return true;
						}
						dropOriginalBlock = true;
						damage(itemstack, 200, entityPlayer);
					} else
					{
						dropOriginalBlock = world.rand.nextFloat() <= wrenchable.getWrenchDropRate();
						damage(itemstack, 10, entityPlayer);
					}
					List drops = block.getBlockDropped(world, x, y, z, metaData, 0);
					if (dropOriginalBlock)
					{
						ItemStack wrenchDrop = wrenchable.getWrenchDrop(entityPlayer);
						if (wrenchDrop != null)
							if (drops.isEmpty())
								drops.add(wrenchDrop);
							else
								drops.set(0, wrenchDrop);
					}
					ItemStack itemStack;
					for (Iterator i$ = drops.iterator(); i$.hasNext(); StackUtil.dropAsEntity(world, x, y, z, itemStack))
						itemStack = (ItemStack)i$.next();

					world.setBlock(x, y, z, 0, 0, 3);
				}
				if (IC2.platform.isRendering())
					IC2.audioManager.playOnce(entityPlayer, PositionSpec.Hand, "Tools/wrench.ogg", true, IC2.audioManager.defaultVolume);
				return IC2.platform.isSimulating();
			}
		}
		if (block.rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side)))
		{
			if (IC2.platform.isSimulating())
				damage(itemstack, 1, entityPlayer);
			if (IC2.platform.isRendering())
				IC2.audioManager.playOnce(entityPlayer, PositionSpec.Hand, "Tools/wrench.ogg", true, IC2.audioManager.defaultVolume);
			return IC2.platform.isSimulating();
		} else
		{
			return false;
		}
	}

	public boolean shouldPassSneakingClickToBlock(World par2World, int par4, int par5, int i)
	{
		return true;
	}

	public void damage(ItemStack is, int damage, EntityPlayer player)
	{
		is.damageItem(damage, player);
	}

	public boolean overrideWrenchSuccessRate(ItemStack itemStack)
	{
		return false;
	}
}
