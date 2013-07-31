// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemTreetap.java

package ic2.core.item.tool;

import ic2.core.*;
import ic2.core.audio.AudioManager;
import ic2.core.audio.PositionSpec;
import ic2.core.block.TileEntityBarrel;
import ic2.core.init.InternalName;
import ic2.core.item.ItemIC2;
import ic2.core.util.StackUtil;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

public class ItemTreetap extends ItemIC2
{

	public ItemTreetap(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		setMaxStackSize(1);
		setMaxDamage(16);
	}

	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, 
			float xOffset, float yOffset, float zOffset)
	{
		int blockId = world.getBlockId(x, y, z);
		if (blockId == Ic2Items.blockBarrel.itemID)
			return ((TileEntityBarrel)world.getBlockTileEntity(x, y, z)).useTreetapOn(entityplayer, side);
		if (Ic2Items.rubberWood != null && blockId == Ic2Items.rubberWood.itemID)
		{
			attemptExtract(entityplayer, world, x, y, z, side, null);
			if (IC2.platform.isSimulating())
				itemstack.damageItem(1, entityplayer);
			return true;
		} else
		{
			return false;
		}
	}

	public static void ejectHarz(World world, int x, int y, int z, int side, int quantity)
	{
		double ejectX = (double)x + 0.5D;
		double ejectY = (double)y + 0.5D;
		double ejectZ = (double)z + 0.5D;
		if (side == 2)
			ejectZ -= 0.29999999999999999D;
		else
		if (side == 5)
			ejectX += 0.29999999999999999D;
		else
		if (side == 3)
			ejectZ += 0.29999999999999999D;
		else
		if (side == 4)
			ejectX -= 0.29999999999999999D;
		for (int i = 0; i < quantity; i++)
		{
			EntityItem entityitem = new EntityItem(world, ejectX, ejectY, ejectZ, Ic2Items.resin.copy());
			entityitem.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(entityitem);
		}

	}

	public static boolean attemptExtract(EntityPlayer entityplayer, World world, int x, int y, int z, int side, List stacks)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (meta < 2 || meta % 6 != side)
			return false;
		if (meta < 6)
		{
			if (IC2.platform.isSimulating())
			{
				world.setBlockMetadataWithNotify(x, y, z, meta + 6, 3);
				if (stacks != null)
					stacks.add(StackUtil.copyWithSize(Ic2Items.resin, world.rand.nextInt(3) + 1));
				else
					ejectHarz(world, x, y, z, side, world.rand.nextInt(3) + 1);
				if (entityplayer != null)
					IC2.achievements.issueAchievement(entityplayer, "acquireResin");
				world.scheduleBlockUpdate(x, y, z, Ic2Items.rubberWood.itemID, Block.blocksList[Ic2Items.rubberWood.itemID].tickRate(world));
			}
			if (IC2.platform.isRendering() && entityplayer != null)
				IC2.audioManager.playOnce(entityplayer, PositionSpec.Hand, "Tools/Treetap.ogg", true, IC2.audioManager.defaultVolume);
			return true;
		}
		if (IC2.platform.isSimulating() && world.rand.nextInt(5) == 0)
			world.setBlockMetadataWithNotify(x, y, z, 1, 3);
		if (world.rand.nextInt(5) == 0)
		{
			if (IC2.platform.isSimulating())
			{
				ejectHarz(world, x, y, z, side, 1);
				if (stacks != null)
					stacks.add(StackUtil.copyWithSize(Ic2Items.resin, 1));
				else
					ejectHarz(world, x, y, z, side, 1);
			}
			if (IC2.platform.isRendering() && entityplayer != null)
				IC2.audioManager.playOnce(entityplayer, PositionSpec.Hand, "Tools/Treetap.ogg", true, IC2.audioManager.defaultVolume);
			return true;
		} else
		{
			return false;
		}
	}
}
