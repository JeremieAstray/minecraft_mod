// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemScanner.java

package ic2.core.item.tool;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItemManager;
import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.audio.AudioManager;
import ic2.core.audio.PositionSpec;
import ic2.core.init.InternalName;
import ic2.core.item.BaseElectricItem;
import ic2.core.util.Util;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

public class ItemScanner extends BaseElectricItem
{

	public ItemScanner(Configuration config, InternalName internalName, int t)
	{
		super(config, internalName);
		maxCharge = 10000;
		transferLimit = 50;
		tier = t;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (tier == 1 && !ElectricItem.manager.use(itemstack, 50, entityplayer) || tier == 2 && !ElectricItem.manager.use(itemstack, 250, entityplayer))
			return itemstack;
		if (IC2.platform.isSimulating())
		{
			if (tier == 2)
			{
				int value = valueOfArea(world, Util.roundToNegInf(((Entity) (entityplayer)).posX), Util.roundToNegInf(((Entity) (entityplayer)).posY), Util.roundToNegInf(((Entity) (entityplayer)).posZ), true);
				IC2.platform.messagePlayer(entityplayer, (new StringBuilder()).append("SCAN RESULT: Ore value in this area is ").append(value).toString(), new Object[0]);
			} else
			{
				int value = valueOfArea(world, Util.roundToNegInf(((Entity) (entityplayer)).posX), Util.roundToNegInf(((Entity) (entityplayer)).posY), Util.roundToNegInf(((Entity) (entityplayer)).posZ), false);
				IC2.platform.messagePlayer(entityplayer, (new StringBuilder()).append("SCAN RESULT: Ore density in this area is ").append(value).toString(), new Object[0]);
			}
		} else
		{
			IC2.audioManager.playOnce(entityplayer, PositionSpec.Hand, "Tools/ODScanner.ogg", true, IC2.audioManager.defaultVolume);
		}
		return itemstack;
	}

	public static int valueOfArea(World worldObj, int x, int y, int z, boolean advancedMode)
	{
		int totalScore = 0;
		int blocksScanned = 0;
		int range = advancedMode ? 4 : 2;
		for (int blockY = y; blockY > 0; blockY--)
		{
			for (int blockX = x - range; blockX <= x + range; blockX++)
			{
				for (int blockZ = z - range; blockZ <= z + range; blockZ++)
				{
					int blockId = worldObj.getBlockId(blockX, blockY, blockZ);
					int metaData = worldObj.getBlockMetadata(blockX, blockY, blockZ);
					if (advancedMode)
						totalScore += valueOf(blockId, metaData);
					else
					if (isValuable(blockId, metaData))
						totalScore++;
					blocksScanned++;
				}

			}

		}

		return (blocksScanned <= 0 ? null : Integer.valueOf((int)((1000D * (double)totalScore) / (double)blocksScanned))).intValue();
	}

	public static boolean isValuable(int blockId, int metaData)
	{
		return valueOf(blockId, metaData) > 0;
	}

	public static int valueOf(int blockId, int metaData)
	{
		if (IC2.valuableOres.containsKey(Integer.valueOf(blockId)))
		{
			Map metaMap = (Map)IC2.valuableOres.get(Integer.valueOf(blockId));
			if (metaMap.containsKey(Integer.valueOf(32767)))
				return ((Integer)metaMap.get(Integer.valueOf(32767))).intValue();
			if (metaMap.containsKey(Integer.valueOf(metaData)))
				return ((Integer)metaMap.get(Integer.valueOf(metaData))).intValue();
		}
		return 0;
	}

	public int startLayerScan(ItemStack itemStack)
	{
		return ElectricItem.manager.use(itemStack, 50, null) ? 3 : 0;
	}
}
