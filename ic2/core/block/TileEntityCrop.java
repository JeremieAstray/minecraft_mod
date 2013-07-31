// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityCrop.java

package ic2.core.block;

import ic2.api.crops.*;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkUpdateListener;
import ic2.core.*;
import ic2.core.block.crop.IC2Crops;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.item.ItemCropSeed;
import ic2.core.network.NetworkManager;
import ic2.core.util.StackUtil;
import java.util.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class TileEntityCrop extends TileEntity
	implements INetworkDataProvider, INetworkUpdateListener, ICropTile
{

	public short id;
	public byte size;
	public byte statGrowth;
	public byte statGain;
	public byte statResistance;
	public byte scanLevel;
	public NBTTagCompound customData;
	public int nutrientStorage;
	public int waterStorage;
	public int exStorage;
	public int growthPoints;
	public boolean upgraded;
	public char ticker;
	public boolean dirty;
	public static char tickRate = '\u0100';
	public byte humidity;
	public byte nutrients;
	public byte airQuality;

	public TileEntityCrop()
	{
		id = -1;
		size = 0;
		statGrowth = 0;
		statGain = 0;
		statResistance = 0;
		scanLevel = 0;
		customData = new NBTTagCompound();
		nutrientStorage = 0;
		waterStorage = 0;
		exStorage = 0;
		growthPoints = 0;
		upgraded = false;
		ticker = (char)IC2.random.nextInt(tickRate);
		dirty = true;
		humidity = -1;
		nutrients = -1;
		airQuality = -1;
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		id = nbttagcompound.getShort("cropid");
		size = nbttagcompound.getByte("size");
		statGrowth = nbttagcompound.getByte("statGrowth");
		statGain = nbttagcompound.getByte("statGain");
		statResistance = nbttagcompound.getByte("statResistance");
		if (nbttagcompound.hasKey("data0"))
		{
			for (int x = 0; x < 16; x++)
				customData.setShort((new StringBuilder()).append("legacy").append(x).toString(), nbttagcompound.getShort((new StringBuilder()).append("data").append(x).toString()));

		} else
		if (nbttagcompound.hasKey("customData"))
			customData = nbttagcompound.getCompoundTag("customData");
		growthPoints = nbttagcompound.getInteger("growthPoints");
		try
		{
			nutrientStorage = nbttagcompound.getInteger("nutrientStorage");
			waterStorage = nbttagcompound.getInteger("waterStorage");
		}
		catch (Throwable e)
		{
			nutrientStorage = nbttagcompound.getByte("nutrientStorage");
			waterStorage = nbttagcompound.getByte("waterStorage");
		}
		upgraded = nbttagcompound.getBoolean("upgraded");
		scanLevel = nbttagcompound.getByte("scanLevel");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setShort("cropid", id);
		nbttagcompound.setByte("size", size);
		nbttagcompound.setByte("statGrowth", statGrowth);
		nbttagcompound.setByte("statGain", statGain);
		nbttagcompound.setByte("statResistance", statResistance);
		nbttagcompound.setCompoundTag("customData", customData);
		nbttagcompound.setInteger("growthPoints", growthPoints);
		nbttagcompound.setInteger("nutrientStorage", nutrientStorage);
		nbttagcompound.setInteger("waterStorage", waterStorage);
		nbttagcompound.setBoolean("upgraded", upgraded);
		nbttagcompound.setByte("scanLevel", scanLevel);
	}

	public void updateEntity()
	{
		super.updateEntity();
		ticker++;
		if (ticker % tickRate == 0)
			tick();
		if (dirty)
		{
			dirty = false;
			super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
			super.worldObj.updateLightByType(EnumSkyBlock.Block, super.xCoord, super.yCoord, super.zCoord);
			if (IC2.platform.isSimulating())
			{
				IC2.network.announceBlockUpdate(super.worldObj, super.xCoord, super.yCoord, super.zCoord);
				if (!IC2.platform.isRendering())
				{
					String field;
					for (Iterator i$ = getNetworkedFields().iterator(); i$.hasNext(); IC2.network.updateTileEntityField(this, field))
						field = (String)i$.next();

				}
			}
		}
	}

	public List getNetworkedFields()
	{
		List ret = new Vector(4);
		ret.add("id");
		ret.add("size");
		ret.add("upgraded");
		ret.add("customData");
		return ret;
	}

	public void tick()
	{
		if (!IC2.platform.isSimulating())
			return;
		if (ticker % (tickRate << 2) == 0)
			humidity = updateHumidity();
		if ((ticker + tickRate) % (tickRate << 2) == 0)
			nutrients = updateNutrients();
		if ((ticker + tickRate * 2) % (tickRate << 2) == 0)
			airQuality = updateAirQuality();
		if (id < 0 && (!upgraded || !attemptCrossing()))
			if (IC2.random.nextInt(100) == 0 && !hasEx())
			{
				reset();
				id = (short)IC2Crops.weed.getId();
				size = 1;
			} else
			{
				if (exStorage > 0 && IC2.random.nextInt(10) == 0)
					exStorage--;
				return;
			}
		crop().tick(this);
		if (crop().canGrow(this))
		{
			growthPoints += calcGrowthRate();
			if (id > -1 && growthPoints >= crop().growthDuration(this))
			{
				growthPoints = 0;
				size++;
				dirty = true;
			}
		}
		if (nutrientStorage > 0)
			nutrientStorage--;
		if (waterStorage > 0)
			waterStorage--;
		if (crop().isWeed(this) && IC2.random.nextInt(50) - statGrowth <= 2)
			generateWeed();
	}

	public void generateWeed()
	{
		int x = super.xCoord;
		int y = super.yCoord;
		int z = super.zCoord;
		switch (IC2.random.nextInt(4))
		{
		case 0: // '\0'
			x++;
			// fall through

		case 1: // '\001'
			x--;
			// fall through

		case 2: // '\002'
			z++;
			// fall through

		case 3: // '\003'
			z--;
			break;
		}
		if (super.worldObj.getBlockTileEntity(x, y, z) instanceof TileEntityCrop)
		{
			TileEntityCrop crop = (TileEntityCrop)super.worldObj.getBlockTileEntity(x, y, z);
			if (crop.id == -1 || !crop.crop().isWeed(crop) && IC2.random.nextInt(32) >= crop.statResistance && !crop.hasEx())
			{
				byte newGrowth = statGrowth;
				if (crop.statGrowth > newGrowth)
					newGrowth = crop.statGrowth;
				if (newGrowth < 31 && IC2.random.nextBoolean())
					newGrowth++;
				crop.reset();
				crop.id = 0;
				crop.size = 1;
				crop.statGrowth = newGrowth;
			}
		} else
		if (super.worldObj.getBlockId(x, y, z) == 0)
		{
			int block = super.worldObj.getBlockId(x, y - 1, z);
			if (block == Block.dirt.blockID || block == ((Block) (Block.grass)).blockID || block == Block.tilledField.blockID)
			{
				super.worldObj.setBlock(x, y - 1, z, ((Block) (Block.grass)).blockID, 0, 7);
				super.worldObj.setBlock(x, y, z, ((Block) (Block.tallGrass)).blockID, 1, 7);
			}
		}
	}

	public boolean hasEx()
	{
		if (exStorage > 0)
		{
			exStorage -= 5;
			return true;
		} else
		{
			return false;
		}
	}

	public boolean attemptCrossing()
	{
		if (IC2.random.nextInt(3) != 0)
			return false;
		LinkedList crops = new LinkedList();
		askCropJoinCross(super.xCoord - 1, super.yCoord, super.zCoord, crops);
		askCropJoinCross(super.xCoord + 1, super.yCoord, super.zCoord, crops);
		askCropJoinCross(super.xCoord, super.yCoord, super.zCoord - 1, crops);
		askCropJoinCross(super.xCoord, super.yCoord, super.zCoord + 1, crops);
		if (crops.size() < 2)
			return false;
		int ratios[] = new int[256];
		for (int i = 1; i < ratios.length; i++)
		{
			CropCard crop = Crops.instance.getCropList()[i];
			if (crop == null || !crop.canGrow(this))
				continue;
			for (int j = 0; j < crops.size(); j++)
				ratios[i] += calculateRatioFor(crop, ((TileEntityCrop)crops.get(j)).crop());

		}

		int total = 0;
		int i;
		for (i = 0; i < ratios.length; i++)
			total += ratios[i];

		total = IC2.random.nextInt(total);
		i = 0;
		do
		{
			if (i >= ratios.length)
				break;
			if (ratios[i] > 0 && ratios[i] > total)
			{
				total = i;
				break;
			}
			total -= ratios[i];
			i++;
		} while (true);
		upgraded = false;
		id = (short)total;
		dirty = true;
		size = 1;
		statGrowth = 0;
		statResistance = 0;
		statGain = 0;
		for (i = 0; i < crops.size(); i++)
		{
			statGrowth += ((TileEntityCrop)crops.get(i)).statGrowth;
			statResistance += ((TileEntityCrop)crops.get(i)).statResistance;
			statGain += ((TileEntityCrop)crops.get(i)).statGain;
		}

		int count = crops.size();
		statGrowth /= count;
		statResistance /= count;
		statGain /= count;
		statGrowth += IC2.random.nextInt(1 + 2 * count) - count;
		if (statGrowth < 0)
			statGrowth = 0;
		if (statGrowth > 31)
			statGrowth = 31;
		statGain += IC2.random.nextInt(1 + 2 * count) - count;
		if (statGain < 0)
			statGain = 0;
		if (statGain > 31)
			statGain = 31;
		statResistance += IC2.random.nextInt(1 + 2 * count) - count;
		if (statResistance < 0)
			statResistance = 0;
		if (statResistance > 31)
			statResistance = 31;
		return true;
	}

	public int calculateRatioFor(CropCard a, CropCard b)
	{
		if (a == b)
			return 500;
		int value = 0;
		int i = 0;
		do
			if (i < 5)
			{
				int c = a.stat(i) - b.stat(i);
				if (c < 0)
					c *= -1;
				switch (c)
				{
				default:
					value--;
					// fall through

				case 0: // '\0'
					value += 2;
					// fall through

				case 1: // '\001'
					value++;
					// fall through

				case 2: // '\002'
					i++;
					break;
				}
			} else
			{
				for (i = 0; i < a.attributes().length; i++)
				{
					for (int j = 0; j < b.attributes().length; j++)
						if (a.attributes()[i].equalsIgnoreCase(b.attributes()[j]))
							value += 5;

				}

				if (b.tier() < a.tier() - 1)
					value -= 2 * (a.tier() - b.tier());
				if (b.tier() - 3 > a.tier())
					value -= b.tier() - a.tier();
				if (value < 0)
					value = 0;
				return value;
			}
		while (true);
	}

	public void askCropJoinCross(int x, int y, int z, LinkedList crops)
	{
		if (!(super.worldObj.getBlockTileEntity(x, y, z) instanceof TileEntityCrop))
			return;
		TileEntityCrop sidecrop = (TileEntityCrop)super.worldObj.getBlockTileEntity(x, y, z);
		if (sidecrop.id <= 0)
			return;
		if (!sidecrop.crop().canGrow(this) || !sidecrop.crop().canCross(sidecrop))
			return;
		int base = 4;
		if (sidecrop.statGrowth >= 16)
			base++;
		if (sidecrop.statGrowth >= 30)
			base++;
		if (sidecrop.statResistance >= 28)
			base += 27 - sidecrop.statResistance;
		if (base >= IC2.random.nextInt(20))
			crops.add(sidecrop);
	}

	public boolean leftclick(EntityPlayer player)
	{
		if (id < 0)
		{
			if (upgraded)
			{
				upgraded = false;
				dirty = true;
				if (IC2.platform.isSimulating())
					StackUtil.dropAsEntity(super.worldObj, super.xCoord, super.yCoord, super.zCoord, new ItemStack(Ic2Items.crop.getItem()));
				return true;
			} else
			{
				return false;
			}
		} else
		{
			return crop().leftclick(this, player);
		}
	}

	public boolean pick(boolean manual)
	{
		if (id < 0)
			return false;
		boolean bonus = harvest(false);
		float firstchance = crop().dropSeedChance(this);
		for (int i = 0; i < statResistance; i++)
			firstchance *= 1.1F;

		int drop = 0;
		if (bonus)
		{
			if (IC2.random.nextFloat() <= (firstchance + 1.0F) * 0.8F)
				drop++;
			float chance = crop().dropSeedChance(this) + (float)statGrowth / 100F;
			if (!manual)
				chance *= 0.8F;
			for (int i = 23; i < statGain; i++)
				chance *= 0.95F;

			if (IC2.random.nextFloat() <= chance)
				drop++;
		} else
		if (IC2.random.nextFloat() <= firstchance * 1.5F)
			drop++;
		ItemStack re[] = new ItemStack[drop];
		for (int i = 0; i < drop; i++)
			re[i] = crop().getSeeds(this);

		reset();
		if (IC2.platform.isSimulating() && re != null && re.length > 0)
		{
			for (int x = 0; x < re.length; x++)
			{
				if (re[x].itemID != Ic2Items.cropSeed.itemID)
					re[x].stackTagCompound = null;
				StackUtil.dropAsEntity(super.worldObj, super.xCoord, super.yCoord, super.zCoord, re[x]);
			}

		}
		return true;
	}

	public boolean rightclick(EntityPlayer player)
	{
		ItemStack current = player.getCurrentEquippedItem();
		if (current != null)
		{
			if (id < 0)
			{
				if (current.itemID == Ic2Items.crop.itemID && !upgraded)
				{
					if (!player.capabilities.isCreativeMode)
					{
						current.stackSize--;
						if (current.stackSize <= 0)
							player.inventory.mainInventory[player.inventory.currentItem] = null;
					}
					upgraded = true;
					dirty = true;
					return true;
				}
				if (applyBaseSeed(current, player))
					return true;
			} else
			if (current.itemID == Ic2Items.cropnalyzer.itemID)
			{
				if (IC2.platform.isSimulating())
				{
					String desc = getScanned();
					if (desc == null)
						desc = "Unknown Crop";
					IC2.platform.messagePlayer(player, desc, new Object[0]);
				}
				return true;
			}
			if (current.itemID == Item.bucketWater.itemID || current.itemID == Ic2Items.waterCell.getItem().itemID)
				if (waterStorage < 10)
				{
					waterStorage = 10;
					return true;
				} else
				{
					return current.itemID == Item.bucketWater.itemID;
				}
			if (current.itemID == Item.seeds.itemID)
				if (nutrientStorage <= 50)
				{
					nutrientStorage += 25;
					current.stackSize--;
					if (current.stackSize <= 0)
						player.inventory.mainInventory[player.inventory.currentItem] = null;
					return true;
				} else
				{
					return false;
				}
			if (current.itemID == Item.dyePowder.itemID && current.getItemDamage() == 15 || current.itemID == Ic2Items.fertilizer.itemID)
				if (applyFertilizer(true))
				{
					current.stackSize--;
					if (current.stackSize <= 0)
						player.inventory.mainInventory[player.inventory.currentItem] = null;
					return true;
				} else
				{
					return false;
				}
			if (current.itemID == Ic2Items.hydratingCell.itemID)
				if (applyHydration(true, current))
				{
					if (current.stackSize <= 0)
						player.inventory.mainInventory[player.inventory.currentItem] = null;
					return true;
				} else
				{
					return false;
				}
			if (current.itemID == Ic2Items.weedEx.itemID && applyWeedEx(true))
			{
				current.damageItem(1, player);
				if (current.stackSize <= 0)
					player.inventory.mainInventory[player.inventory.currentItem] = null;
				return true;
			}
		}
		if (id < 0)
			return false;
		else
			return crop().rightclick(this, player);
	}

	public boolean applyBaseSeed(ItemStack current, EntityPlayer player)
	{
		BaseSeed seed = Crops.instance.getBaseSeed(current);
		if (seed != null)
		{
			if (current.stackSize < seed.stackSize)
				return false;
			if (tryPlantIn(seed.id, seed.size, seed.statGrowth, seed.statGain, seed.statResistance, 1))
			{
				if (current.getItem().hasContainerItem())
				{
					current = current.getItem().getContainerItemStack(current);
				} else
				{
					current.stackSize -= seed.stackSize;
					if (current.stackSize <= 0)
						player.inventory.mainInventory[player.inventory.currentItem] = null;
				}
				return true;
			}
		}
		return false;
	}

	public boolean tryPlantIn(int i, int si, int statGr, int statGa, int statRe, int scan)
	{
		if (id > -1 || i <= 0 || upgraded)
			return false;
		if (!Crops.instance.getCropList()[i].canGrow(this))
		{
			return false;
		} else
		{
			reset();
			id = (short)i;
			size = (byte)si;
			statGrowth = (byte)statGr;
			statGain = (byte)statGa;
			statResistance = (byte)statRe;
			scanLevel = (byte)scan;
			return true;
		}
	}

	public boolean applyFertilizer(boolean manual)
	{
		if (nutrientStorage >= 100)
		{
			return false;
		} else
		{
			nutrientStorage += manual ? 100 : 90;
			return true;
		}
	}

	public boolean applyHydration(boolean manual, InvSlotConsumable hydrationSlot)
	{
		if (!manual && waterStorage >= 180 || waterStorage >= 200)
		{
			return false;
		} else
		{
			int apply = manual ? 200 - waterStorage : 180 - waterStorage;
			ItemStack affected = hydrationSlot.damage(apply, null);
			apply = affected.stackSize * affected.getMaxDamage() + affected.getItemDamage();
			waterStorage += apply;
			return true;
		}
	}

	public boolean applyHydration(boolean manual, ItemStack itemStack)
	{
		if (!manual && waterStorage >= 180 || waterStorage >= 200)
			return false;
		int apply = manual ? 200 - waterStorage : 180 - waterStorage;
		apply = Math.min(apply, itemStack.getMaxDamage() - itemStack.getItemDamage());
		if (itemStack.attemptDamageItem(apply, new Random()))
		{
			itemStack.stackSize--;
			itemStack.setItemDamage(0);
		}
		waterStorage += apply;
		return true;
	}

	public boolean applyWeedEx(boolean manual)
	{
		if (exStorage >= 100 && manual || exStorage >= 150)
			return false;
		exStorage += 50;
		boolean trigger = super.worldObj.rand.nextInt(3) == 0;
		if (manual)
			trigger = super.worldObj.rand.nextInt(5) == 0;
		if (id > 0 && exStorage >= 75 && trigger)
		{
			switch (super.worldObj.rand.nextInt(5))
			{
			case 0: // '\0'
				if (statGrowth > 0)
					statGrowth--;
				// fall through

			case 1: // '\001'
				if (statGain > 0)
					statGain--;
				break;
			}
			if (statResistance > 0)
				statResistance--;
		}
		return true;
	}

	public boolean harvest(boolean manual)
	{
		if (id < 0 || !crop().canBeHarvested(this))
			return false;
		float chance = crop().dropGainChance();
		for (int i = 0; i < statGain; i++)
			chance *= 1.03F;

		chance -= IC2.random.nextFloat();
		int drop = 0;
		for (; chance > 0.0F; chance -= IC2.random.nextFloat())
			drop++;

		ItemStack re[] = new ItemStack[drop];
		for (int i = 0; i < drop; i++)
		{
			re[i] = crop().getGain(this);
			if (re[i] != null && IC2.random.nextInt(100) <= statGain)
				re[i].stackSize++;
		}

		size = crop().getSizeAfterHarvest(this);
		dirty = true;
		if (IC2.platform.isSimulating() && re != null && re.length > 0)
		{
			for (int x = 0; x < re.length; x++)
				StackUtil.dropAsEntity(super.worldObj, super.xCoord, super.yCoord, super.zCoord, re[x]);

		}
		return true;
	}

	public void onNeighbourChange()
	{
		if (id < 0)
		{
			return;
		} else
		{
			crop().onNeighbourChange(this);
			return;
		}
	}

	public int emitRedstone()
	{
		if (id < 0)
			return 0;
		else
			return crop().emitRedstone(this);
	}

	public void onBlockDestroyed()
	{
		if (id < 0)
		{
			return;
		} else
		{
			crop().onBlockDestroyed(this);
			return;
		}
	}

	public int getEmittedLight()
	{
		if (id < 0)
			return 0;
		else
			return crop().getEmittedLight(this);
	}

	public byte getHumidity()
	{
		if (humidity == -1)
			humidity = updateHumidity();
		return humidity;
	}

	public byte getNutrients()
	{
		if (nutrients == -1)
			nutrients = updateNutrients();
		return nutrients;
	}

	public byte getAirQuality()
	{
		if (airQuality == -1)
			airQuality = updateAirQuality();
		return airQuality;
	}

	public byte updateHumidity()
	{
		int value = Crops.instance.getHumidityBiomeBonus(super.worldObj.getBiomeGenForCoords(super.xCoord, super.zCoord));
		if (super.worldObj.getBlockMetadata(super.xCoord, super.yCoord - 1, super.zCoord) >= 7)
			value += 2;
		if (waterStorage >= 5)
			value += 2;
		value += (waterStorage + 24) / 25;
		return (byte)value;
	}

	public byte updateNutrients()
	{
		int value = Crops.instance.getNutrientBiomeBonus(super.worldObj.getBiomeGenForCoords(super.xCoord, super.zCoord));
		for (int i = 2; i < 5 && super.worldObj.getBlockId(super.xCoord, super.yCoord - i, super.zCoord) == Block.dirt.blockID; i++)
			value++;

		value += (nutrientStorage + 19) / 20;
		return (byte)value;
	}

	public byte updateAirQuality()
	{
		int value = 0;
		int height = (super.yCoord - 64) / 15;
		if (height > 4)
			height = 4;
		if (height < 0)
			height = 0;
		value += height;
		int fresh = 9;
		for (int x = super.xCoord - 1; x < super.xCoord + 1 && fresh > 0; x++)
		{
			for (int z = super.zCoord - 1; z < super.zCoord + 1 && fresh > 0; z++)
				if (super.worldObj.isBlockOpaqueCube(x, super.yCoord, z) || (super.worldObj.getBlockTileEntity(x, super.yCoord, z) instanceof TileEntityCrop))
					fresh--;

		}

		value += fresh / 2;
		if (super.worldObj.canBlockSeeTheSky(super.xCoord, super.yCoord + 1, super.zCoord))
			value += 2;
		return (byte)value;
	}

	public byte updateMultiCulture()
	{
		LinkedList crops = new LinkedList();
		for (int x = -1; x < 1; x++)
		{
			for (int z = -1; z < 1; z++)
				if (super.worldObj.getBlockTileEntity(x + super.xCoord, super.yCoord, z + super.zCoord) instanceof TileEntityCrop)
					addIfNotPresent(((TileEntityCrop)super.worldObj.getBlockTileEntity(x + super.xCoord, super.yCoord, z + super.zCoord)).crop(), crops);

		}

		return (byte)(crops.size() - 1);
	}

	public void addIfNotPresent(CropCard crop, LinkedList crops)
	{
		for (int i = 0; i < crops.size(); i++)
			if (crop == crops.get(i))
				return;

		crops.add(crop);
	}

	public int calcGrowthRate()
	{
		int base = 3 + IC2.random.nextInt(7) + statGrowth;
		int need = (crop().tier() - 1) * 4 + statGrowth + statGain + statResistance;
		if (need < 0)
			need = 0;
		int have = crop().weightInfluences(this, getHumidity(), getNutrients(), getAirQuality()) * 5;
		if (have >= need)
		{
			base = (base * (100 + (have - need))) / 100;
		} else
		{
			int neg = (need - have) * 4;
			if (neg > 100 && IC2.random.nextInt(32) > statResistance)
			{
				reset();
				base = 0;
			} else
			{
				base = (base * (100 - neg)) / 100;
				if (base < 0)
					base = 0;
			}
		}
		return base;
	}

	public void calcTrampling()
	{
		if (!IC2.platform.isSimulating())
			return;
		if (IC2.random.nextInt(100) == 0 && IC2.random.nextInt(40) > statResistance)
		{
			reset();
			super.worldObj.setBlock(super.xCoord, super.yCoord - 1, super.zCoord, Block.dirt.blockID, 0, 7);
		}
	}

	public CropCard crop()
	{
		return Crops.instance.getCropList()[id];
	}

	public void onEntityCollision(Entity entity)
	{
		if (id < 0)
			return;
		if (crop().onEntityCollision(this, entity))
			calcTrampling();
	}

	public void reset()
	{
		id = -1;
		size = 0;
		customData = new NBTTagCompound();
		dirty = true;
		statGain = 0;
		statResistance = 0;
		statGrowth = 0;
		nutrients = -1;
		airQuality = -1;
		humidity = -1;
		growthPoints = 0;
		upgraded = false;
		scanLevel = 0;
	}

	public void updateState()
	{
		dirty = true;
	}

	public String getScanned()
	{
		if (scanLevel <= 0 || id < 0)
			return null;
		if (scanLevel >= 4)
			return (new StringBuilder()).append(crop().name()).append(" - Gr: ").append(statGrowth).append(" Ga: ").append(statGain).append(" Re: ").append(statResistance).toString();
		else
			return crop().name();
	}

	public boolean isBlockBelow(Block block)
	{
		for (int i = 1; i < 4; i++)
		{
			int id = super.worldObj.getBlockId(super.xCoord, super.yCoord - i, super.zCoord);
			if (id == 0)
				return false;
			if (Block.blocksList[id] == block)
				return true;
		}

		return false;
	}

	public ItemStack generateSeeds(short plant, byte growth, byte gain, byte resis, byte scan)
	{
		return ItemCropSeed.generateItemStackFromValues(plant, growth, gain, resis, scan);
	}

	public void onNetworkUpdate(String field)
	{
		dirty = true;
	}

	public short getID()
	{
		return id;
	}

	public byte getSize()
	{
		return size;
	}

	public byte getGrowth()
	{
		return statGrowth;
	}

	public byte getGain()
	{
		return statGain;
	}

	public byte getResistance()
	{
		return statResistance;
	}

	public byte getScanLevel()
	{
		return scanLevel;
	}

	public NBTTagCompound getCustomData()
	{
		return customData;
	}

	public int getNutrientStorage()
	{
		return nutrientStorage;
	}

	public int getHydrationStorage()
	{
		return waterStorage;
	}

	public int getWeedExStorage()
	{
		return exStorage;
	}

	public int getLightLevel()
	{
		return super.worldObj.getBlockLightValue(super.xCoord, super.yCoord, super.zCoord);
	}

	public void setID(short id)
	{
		this.id = id;
		dirty = true;
	}

	public void setSize(byte size)
	{
		this.size = size;
		dirty = true;
	}

	public void setGrowth(byte growth)
	{
		statGrowth = growth;
	}

	public void setGain(byte gain)
	{
		statGain = gain;
	}

	public void setResistance(byte resistance)
	{
		statResistance = resistance;
	}

	public void setScanLevel(byte scanLevel)
	{
		this.scanLevel = scanLevel;
	}

	public void setNutrientStorage(int nutrientStorage)
	{
		this.nutrientStorage = nutrientStorage;
	}

	public void setHydrationStorage(int hydrationStorage)
	{
		waterStorage = hydrationStorage;
	}

	public void setWeedExStorage(int weedExStorage)
	{
		exStorage = weedExStorage;
	}

	public World getWorld()
	{
		return super.worldObj;
	}

	public ChunkCoordinates getLocation()
	{
		return new ChunkCoordinates(super.xCoord, super.yCoord, super.zCoord);
	}

}
