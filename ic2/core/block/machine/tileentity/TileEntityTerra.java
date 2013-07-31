// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityTerra.java

package ic2.core.block.machine.tileentity;

import ic2.api.Direction;
import ic2.api.item.ITerraformingBP;
import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.audio.*;
import ic2.core.block.invslot.InvSlotTfbp;
import ic2.core.util.StackUtil;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

// Referenced classes of package ic2.core.block.machine.tileentity:
//			TileEntityElectricMachine

public class TileEntityTerra extends TileEntityElectricMachine
{

	public int failedAttempts;
	public int lastX;
	public int lastY;
	public int lastZ;
	public AudioSource audioSource;
	public int inactiveTicks;
	public final InvSlotTfbp tfbpSlot = new InvSlotTfbp(this, "tfbp", 0, 1);

	public TileEntityTerra()
	{
		super(0x186a0, 3, -1);
		failedAttempts = 0;
		lastX = -1;
		lastY = -1;
		lastZ = -1;
		inactiveTicks = 0;
	}

	public String getInvName()
	{
		return "Terraformer";
	}

	public void updateEntity()
	{
		super.updateEntity();
		boolean newActive = false;
		if (!tfbpSlot.isEmpty())
		{
			ITerraformingBP tfbp = (ITerraformingBP)tfbpSlot.get().getItem();
			if (energy >= tfbp.getConsume())
			{
				newActive = true;
				int x = super.xCoord;
				int z = super.zCoord;
				int range = 1;
				if (lastY > -1)
				{
					range = tfbp.getRange() / 10;
					x = (lastX - super.worldObj.rand.nextInt(range + 1)) + super.worldObj.rand.nextInt(range + 1);
					z = (lastZ - super.worldObj.rand.nextInt(range + 1)) + super.worldObj.rand.nextInt(range + 1);
				} else
				{
					if (failedAttempts > 4)
						failedAttempts = 4;
					range = (tfbp.getRange() * (failedAttempts + 1)) / 5;
					x = (x - super.worldObj.rand.nextInt(range + 1)) + super.worldObj.rand.nextInt(range + 1);
					z = (z - super.worldObj.rand.nextInt(range + 1)) + super.worldObj.rand.nextInt(range + 1);
				}
				if (tfbp.terraform(super.worldObj, x, z, super.yCoord))
				{
					energy -= tfbp.getConsume();
					failedAttempts = 0;
					lastX = x;
					lastZ = z;
					lastY = super.yCoord;
				} else
				{
					energy -= tfbp.getConsume() / 10;
					failedAttempts++;
					lastY = -1;
				}
			}
		}
		if (newActive)
		{
			inactiveTicks = 0;
			setActive(true);
		} else
		if (!newActive && getActive() && inactiveTicks++ > 30)
			setActive(false);
	}

	public void onUnloaded()
	{
		if (IC2.platform.isRendering() && audioSource != null)
		{
			IC2.audioManager.removeSources(this);
			audioSource = null;
		}
		super.onUnloaded();
	}

	public int injectEnergy(Direction directionFrom, int amount)
	{
		if (amount > 512)
		{
			IC2.explodeMachineAt(super.worldObj, super.xCoord, super.yCoord, super.zCoord);
			return 0;
		}
		if (energy >= maxEnergy)
		{
			return amount;
		} else
		{
			energy += amount;
			return 0;
		}
	}

	public boolean ejectBlueprint()
	{
		if (tfbpSlot.isEmpty())
			return false;
		if (IC2.platform.isSimulating())
		{
			StackUtil.dropAsEntity(super.worldObj, super.xCoord, super.yCoord, super.zCoord, tfbpSlot.get());
			tfbpSlot.clear();
		}
		return true;
	}

	public void insertBlueprint(ItemStack tfbp)
	{
		ejectBlueprint();
		tfbpSlot.put(tfbp);
	}

	public static int getFirstSolidBlockFrom(World world, int x, int z, int y)
	{
		for (; y > 0; y--)
			if (world.isBlockOpaqueCube(x, y, z))
				return y;

		return -1;
	}

	public static int getFirstBlockFrom(World world, int x, int z, int y)
	{
		for (; y > 0; y--)
			if (world.getBlockId(x, y, z) != 0)
				return y;

		return -1;
	}

	public static boolean switchGround(World world, Block from, Block to, int x, int y, int z, boolean upwards)
	{
		if (upwards)
		{
			int saveY = ++y;
			do
			{
				int id = world.getBlockId(x, y - 1, z);
				if (id == 0 || Block.blocksList[id] != from)
					break;
				y--;
			} while (true);
			if (saveY == y)
			{
				return false;
			} else
			{
				world.setBlock(x, y, z, to.blockID, 0, 7);
				return true;
			}
		}
		int id;
		do
		{
			id = world.getBlockId(x, y, z);
			if (id == 0 || Block.blocksList[id] != to)
				break;
			y--;
		} while (true);
		id = world.getBlockId(x, y, z);
		if (id == 0 || Block.blocksList[id] != from)
		{
			return false;
		} else
		{
			world.setBlock(x, y, z, to.blockID, 0, 7);
			return true;
		}
	}

	public static BiomeGenBase getBiomeAt(World world, int x, int z)
	{
		return world.getChunkFromBlockCoords(x, z).getBiomeGenForWorldCoords(x & 0xf, z & 0xf, world.getWorldChunkManager());
	}

	public static void setBiomeAt(World world, int x, int z, BiomeGenBase biome)
	{
		Chunk chunk = world.getChunkFromBlockCoords(x, z);
		byte array[] = chunk.getBiomeArray();
		array[(z & 0xf) << 4 | x & 0xf] = (byte)(biome.biomeID & 0xff);
		chunk.setBiomeArray(array);
	}

	public void onNetworkUpdate(String field)
	{
		if (field.equals("active") && prevActive != getActive())
		{
			if (audioSource == null)
				audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Terraformers/TerraformerGenericloop.ogg", true, false, IC2.audioManager.defaultVolume);
			if (getActive())
			{
				if (audioSource != null)
					audioSource.play();
			} else
			if (audioSource != null)
				audioSource.stop();
		}
		super.onNetworkUpdate(field);
	}
}
