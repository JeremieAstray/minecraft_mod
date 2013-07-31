// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemToolMiningLaser.java

package ic2.core.item.tool;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItemManager;
import ic2.api.network.INetworkItemEventListener;
import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.audio.AudioManager;
import ic2.core.audio.PositionSpec;
import ic2.core.init.InternalName;
import ic2.core.network.NetworkManager;
import ic2.core.util.Keyboard;
import ic2.core.util.StackUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.tool:
//			ItemElectricTool, EntityMiningLaser

public class ItemToolMiningLaser extends ItemElectricTool
	implements INetworkItemEventListener
{

	private static final int EventShotMining = 0;
	private static final int EventShotLowFocus = 1;
	private static final int EventShotLongRange = 2;
	private static final int EventShotHorizontal = 3;
	private static final int EventShotSuperHeat = 4;
	private static final int EventShotScatter = 5;
	private static final int EventShotExplosive = 6;

	public ItemToolMiningLaser(Configuration config, InternalName internalName)
	{
		super(config, internalName, EnumToolMaterial.IRON, 100);
		maxCharge = 0x30d40;
		transferLimit = 120;
		tier = 2;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (!IC2.platform.isSimulating())
			return itemstack;
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemstack);
		int laserSetting = nbtData.getInteger("laserSetting");
		if (IC2.keyboard.isModeSwitchKeyDown(entityplayer))
		{
			laserSetting = (laserSetting + 1) % 7;
			nbtData.setInteger("laserSetting", laserSetting);
			String laser = (new String[] {
				"Mining", "Low-Focus", "Long-Range", "Horizontal", "Super-Heat", "Scatter", "Explosive"
			})[laserSetting];
			IC2.platform.messagePlayer(entityplayer, (new StringBuilder()).append("Laser Mode: ").append(laser).toString(), new Object[0]);
		} else
		{
			int consume = (new int[] {
				1250, 100, 5000, 0, 2500, 10000, 5000
			})[laserSetting];
			if (!ElectricItem.manager.use(itemstack, consume, entityplayer))
				return itemstack;
			switch (laserSetting)
			{
			case 3: // '\003'
			default:
				break;

			case 0: // '\0'
				world.spawnEntityInWorld(new EntityMiningLaser(world, entityplayer, (1.0F / 0.0F), 5F, 0x7fffffff, false));
				IC2.network.initiateItemEvent(entityplayer, itemstack, 0, true);
				break;

			case 1: // '\001'
				world.spawnEntityInWorld(new EntityMiningLaser(world, entityplayer, 4F, 5F, 1, false));
				IC2.network.initiateItemEvent(entityplayer, itemstack, 1, true);
				break;

			case 2: // '\002'
				world.spawnEntityInWorld(new EntityMiningLaser(world, entityplayer, (1.0F / 0.0F), 20F, 0x7fffffff, false));
				IC2.network.initiateItemEvent(entityplayer, itemstack, 2, true);
				break;

			case 4: // '\004'
				world.spawnEntityInWorld(new EntityMiningLaser(world, entityplayer, (1.0F / 0.0F), 8F, 0x7fffffff, false, true));
				IC2.network.initiateItemEvent(entityplayer, itemstack, 4, true);
				break;

			case 5: // '\005'
				for (int x = -2; x <= 2; x++)
				{
					for (int y = -2; y <= 2; y++)
						world.spawnEntityInWorld(new EntityMiningLaser(world, entityplayer, (1.0F / 0.0F), 12F, 0x7fffffff, false, ((Entity) (entityplayer)).rotationYaw + 20F * (float)x, ((Entity) (entityplayer)).rotationPitch + 20F * (float)y));

				}

				IC2.network.initiateItemEvent(entityplayer, itemstack, 5, true);
				break;

			case 6: // '\006'
				world.spawnEntityInWorld(new EntityMiningLaser(world, entityplayer, (1.0F / 0.0F), 12F, 0x7fffffff, true));
				IC2.network.initiateItemEvent(entityplayer, itemstack, 6, true);
				break;
			}
		}
		return itemstack;
	}

	public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, 
			float hitX, float hitY, float hitZ)
	{
		if (!IC2.platform.isSimulating())
			return false;
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemstack);
		if (!IC2.keyboard.isModeSwitchKeyDown(entityPlayer) && nbtData.getInteger("laserSetting") == 3)
			if (Math.abs((((Entity) (entityPlayer)).posY + (double)entityPlayer.getEyeHeight()) - 0.10000000000000001D - ((double)y + 0.5D)) < 1.5D)
			{
				if (ElectricItem.manager.use(itemstack, 3000, entityPlayer))
				{
					world.spawnEntityInWorld(new EntityMiningLaser(world, entityPlayer, (1.0F / 0.0F), 5F, 0x7fffffff, false, ((Entity) (entityPlayer)).rotationYaw, 0.0D, (double)y + 0.5D));
					IC2.network.initiateItemEvent(entityPlayer, itemstack, 3, true);
				}
			} else
			{
				IC2.platform.messagePlayer(entityPlayer, "Mining laser aiming angle too steep", new Object[0]);
			}
		return false;
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.uncommon;
	}

	public void onNetworkEvent(int metaData, EntityPlayer player, int event)
	{
		switch (event)
		{
		case 0: // '\0'
			IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaser.ogg", true, IC2.audioManager.defaultVolume);
			break;

		case 1: // '\001'
			IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaserLowFocus.ogg", true, IC2.audioManager.defaultVolume);
			break;

		case 2: // '\002'
			IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaserLongRange.ogg", true, IC2.audioManager.defaultVolume);
			break;

		case 3: // '\003'
			IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaser.ogg", true, IC2.audioManager.defaultVolume);
			break;

		case 4: // '\004'
			IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaser.ogg", true, IC2.audioManager.defaultVolume);
			break;

		case 5: // '\005'
			IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaserScatter.ogg", true, IC2.audioManager.defaultVolume);
			break;

		case 6: // '\006'
			IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaserExplosive.ogg", true, IC2.audioManager.defaultVolume);
			break;
		}
	}
}
