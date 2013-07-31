// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemFrequencyTransmitter.java

package ic2.core.item.tool;

import ic2.core.*;
import ic2.core.block.machine.tileentity.TileEntityTeleporter;
import ic2.core.init.InternalName;
import ic2.core.item.ItemIC2;
import ic2.core.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.Configuration;

public class ItemFrequencyTransmitter extends ItemIC2
{

	public ItemFrequencyTransmitter(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		super.maxStackSize = 1;
		setMaxDamage(0);
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (IC2.platform.isSimulating())
			if (itemstack.getItemDamage() == 0)
			{
				NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemstack);
				if (nbtData.getBoolean("targetSet"))
				{
					nbtData.setBoolean("targetSet", false);
					IC2.platform.messagePlayer(entityplayer, "Frequency Transmitter unlinked", new Object[0]);
				}
			} else
			{
				itemstack.setItemDamage(0);
			}
		return itemstack;
	}

	public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, 
			float hitX, float hitY, float hitZ)
	{
		TileEntity tileEntity = world.getBlockTileEntity(i, j, k);
		if ((tileEntity instanceof TileEntityTeleporter) && IC2.platform.isSimulating())
		{
			NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemstack);
			boolean targetSet = nbtData.getBoolean("targetSet");
			int targetX = nbtData.getInteger("targetX");
			int targetY = nbtData.getInteger("targetY");
			int targetZ = nbtData.getInteger("targetZ");
			TileEntityTeleporter tp = (TileEntityTeleporter)tileEntity;
			if (targetSet)
			{
				boolean prevWorldChunkLoadOverride = world.findingSpawnPoint;
				world.findingSpawnPoint = true;
				Chunk chunk = world.getChunkProvider().provideChunk(targetX >> 4, targetZ >> 4);
				world.findingSpawnPoint = prevWorldChunkLoadOverride;
				if (chunk == null || chunk.getBlockID(targetX & 0xf, targetY, targetZ & 0xf) != Ic2Items.teleporter.itemID || chunk.getBlockMetadata(targetX & 0xf, targetY, targetZ & 0xf) != Ic2Items.teleporter.getItemDamage())
					targetSet = false;
			}
			if (!targetSet)
			{
				targetSet = true;
				targetX = ((TileEntity) (tp)).xCoord;
				targetY = ((TileEntity) (tp)).yCoord;
				targetZ = ((TileEntity) (tp)).zCoord;
				IC2.platform.messagePlayer(entityplayer, "Frequency Transmitter linked to Teleporter.", new Object[0]);
			} else
			if (((TileEntity) (tp)).xCoord == targetX && ((TileEntity) (tp)).yCoord == targetY && ((TileEntity) (tp)).zCoord == targetZ)
				IC2.platform.messagePlayer(entityplayer, "Can't link Teleporter to itself.", new Object[0]);
			else
			if (tp.targetSet && tp.targetX == targetX && tp.targetY == targetY && tp.targetZ == targetZ)
			{
				IC2.platform.messagePlayer(entityplayer, "Teleportation link unchanged.", new Object[0]);
			} else
			{
				tp.setTarget(targetX, targetY, targetZ);
				TileEntity te = world.getBlockTileEntity(targetX, targetY, targetZ);
				if (te instanceof TileEntityTeleporter)
				{
					TileEntityTeleporter tp2 = (TileEntityTeleporter)te;
					if (!tp2.targetSet)
						tp2.setTarget(((TileEntity) (tp)).xCoord, ((TileEntity) (tp)).yCoord, ((TileEntity) (tp)).zCoord);
				}
				IC2.platform.messagePlayer(entityplayer, "Teleportation link established.", new Object[0]);
			}
			nbtData.setBoolean("targetSet", targetSet);
			nbtData.setInteger("targetX", targetX);
			nbtData.setInteger("targetY", targetY);
			nbtData.setInteger("targetZ", targetZ);
			itemstack.setItemDamage(1);
			return false;
		} else
		{
			return false;
		}
	}
}
