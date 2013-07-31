// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityStandardMachine.java

package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.*;
import ic2.core.audio.AudioManager;
import ic2.core.audio.AudioSource;
import ic2.core.block.invslot.*;
import ic2.core.block.machine.ContainerStandardMachine;
import ic2.core.item.IUpgradeItem;
import ic2.core.network.NetworkManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

// Referenced classes of package ic2.core.block.machine.tileentity:
//			TileEntityElectricMachine

public abstract class TileEntityStandardMachine extends TileEntityElectricMachine
	implements IHasGui, INetworkTileEntityEventListener
{

	public short progress;
	public final int defaultEnergyConsume;
	public final int defaultOperationLength;
	public final int defaultTier = 1;
	public final int defaultEnergyStorage;
	public int energyConsume;
	public int operationLength;
	public int operationsPerTick;
	public float serverChargeLevel;
	public float serverProgress;
	public AudioSource audioSource;
	private static final int EventStart = 0;
	private static final int EventInterrupt = 1;
	private static final int EventStop = 2;
	public InvSlot inputSlot;
	public final InvSlotOutput outputSlot = new InvSlotOutput(this, "output", 2, 1);
	public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade(this, "upgrade", 3, 4);
	static final boolean $assertionsDisabled = !ic2/core/block/machine/tileentity/TileEntityStandardMachine.desiredAssertionStatus();

	public TileEntityStandardMachine(int energyPerTick, int length)
	{
		super(energyPerTick * length, 1, 1);
		progress = 0;
		defaultEnergyConsume = energyConsume = energyPerTick;
		defaultOperationLength = operationLength = length;
		defaultEnergyStorage = energyPerTick * length;
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		progress = nbttagcompound.getShort("progress");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setShort("progress", progress);
	}

	public float getChargeLevel()
	{
		float ret;
		if (IC2.platform.isSimulating())
		{
			ret = (float)energy / (float)((maxEnergy - maxInput) + 1);
			if (ret > 1.0F)
				ret = 1.0F;
		} else
		{
			ret = serverChargeLevel;
		}
		return ret;
	}

	public float getProgress()
	{
		float ret;
		if (IC2.platform.isSimulating())
		{
			ret = (float)progress / (float)operationLength;
			if (ret > 1.0F)
				ret = 1.0F;
		} else
		{
			ret = serverProgress;
		}
		return ret;
	}

	public void setChargeLevel(float chargeLevel)
	{
		if (!$assertionsDisabled && IC2.platform.isSimulating())
		{
			throw new AssertionError();
		} else
		{
			serverChargeLevel = chargeLevel;
			return;
		}
	}

	public void setProgress(float progress)
	{
		if (!$assertionsDisabled && IC2.platform.isSimulating())
		{
			throw new AssertionError();
		} else
		{
			serverProgress = progress;
			return;
		}
	}

	public void onLoaded()
	{
		super.onLoaded();
		if (IC2.platform.isSimulating())
			setOverclockRates();
	}

	public void onUnloaded()
	{
		super.onUnloaded();
		if (IC2.platform.isRendering() && audioSource != null)
		{
			IC2.audioManager.removeSources(this);
			audioSource = null;
		}
	}

	public void onInventoryChanged()
	{
		super.onInventoryChanged();
		if (IC2.platform.isSimulating())
			setOverclockRates();
	}

	public void updateEntity()
	{
		super.updateEntity();
		boolean canOperate = canOperate();
		boolean needsInvUpdate = false;
		boolean newActive = getActive();
		if (progress >= operationLength)
		{
			operate();
			needsInvUpdate = true;
			progress = 0;
			newActive = false;
			IC2.network.initiateTileEntityEvent(this, 2, true);
		}
		canOperate = canOperate();
		if (!newActive || progress == 0)
		{
			if (canOperate)
			{
				if (energy >= energyConsume)
				{
					newActive = true;
					IC2.network.initiateTileEntityEvent(this, 0, true);
				}
			} else
			{
				progress = 0;
			}
		} else
		if (!canOperate || energy < energyConsume)
		{
			if (!canOperate)
				progress = 0;
			newActive = false;
			IC2.network.initiateTileEntityEvent(this, 1, true);
		}
		if (newActive)
		{
			progress++;
			energy -= energyConsume;
		}
		for (int i = 0; i < upgradeSlot.size(); i++)
		{
			ItemStack stack = upgradeSlot.get(i);
			if (stack != null && (stack.getItem() instanceof IUpgradeItem) && ((IUpgradeItem)stack.getItem()).onTick(stack, this))
				needsInvUpdate = true;
		}

		if (needsInvUpdate)
			super.onInventoryChanged();
		if (newActive != getActive())
			setActive(newActive);
	}

	public void setOverclockRates()
	{
		int extraProcessTime = 0;
		double processTimeMultiplier = 1.0D;
		int extraEnergyDemand = 0;
		double energyDemandMultiplier = 1.0D;
		int extraEnergyStorage = 0;
		double energyStorageMultiplier = 1.0D;
		int extraTier = 0;
		for (int i = 0; i < upgradeSlot.size(); i++)
		{
			ItemStack stack = upgradeSlot.get(i);
			if (stack != null && (stack.getItem() instanceof IUpgradeItem))
			{
				IUpgradeItem upgrade = (IUpgradeItem)stack.getItem();
				extraProcessTime += upgrade.getExtraProcessTime(stack, this) * stack.stackSize;
				processTimeMultiplier *= Math.pow(upgrade.getProcessTimeMultiplier(stack, this), stack.stackSize);
				extraEnergyDemand += upgrade.getExtraEnergyDemand(stack, this) * stack.stackSize;
				energyDemandMultiplier *= Math.pow(upgrade.getEnergyDemandMultiplier(stack, this), stack.stackSize);
				extraEnergyStorage += upgrade.getExtraEnergyStorage(stack, this) * stack.stackSize;
				energyStorageMultiplier *= Math.pow(upgrade.getEnergyStorageMultiplier(stack, this), stack.stackSize);
				extraTier += upgrade.getExtraTier(stack, this) * stack.stackSize;
			}
		}

		double previousProgress = (double)progress / (double)operationLength;
		double stackOpLen = ((double)defaultOperationLength + (double)extraProcessTime) * 64D * processTimeMultiplier;
		operationsPerTick = (int)Math.min(Math.ceil(64D / stackOpLen), 2147483647D);
		operationLength = (int)Math.round((stackOpLen * (double)operationsPerTick) / 64D);
		energyConsume = applyModifier(defaultEnergyConsume, extraEnergyDemand, energyDemandMultiplier);
		setTier(applyModifier(defaultTier, extraTier, 1.0D));
		maxEnergy = applyModifier(defaultEnergyStorage, (extraEnergyStorage + maxInput) - 1, energyStorageMultiplier);
		if (operationLength < 1)
			operationLength = 1;
		progress = (short)(int)Math.floor(previousProgress * (double)operationLength + 0.10000000000000001D);
	}

	public void operate()
	{
		for (int i = 0; i < operationsPerTick; i++)
		{
			if (!canOperate())
				return;
			ItemStack processResult;
			if (inputSlot.get().getItem().hasContainerItem())
			{
				processResult = getResultFor(inputSlot.get(), false).copy();
				inputSlot.put(inputSlot.get().getItem().getContainerItemStack(inputSlot.get()));
			} else
			{
				processResult = getResultFor(inputSlot.get(), true).copy();
			}
			if (inputSlot.get().stackSize <= 0)
				inputSlot.clear();
			for (int j = 0; j < upgradeSlot.size(); j++)
			{
				ItemStack stack = upgradeSlot.get(j);
				if (stack != null && (stack.getItem() instanceof IUpgradeItem))
					((IUpgradeItem)stack.getItem()).onProcessEnd(stack, this, processResult);
			}

			outputSlot.add(processResult);
		}

	}

	public boolean canOperate()
	{
		if (inputSlot.isEmpty())
			return false;
		ItemStack processResult = getResultFor(inputSlot.get(), false);
		if (processResult == null)
			return false;
		else
			return outputSlot.canAdd(processResult);
	}

	public abstract ItemStack getResultFor(ItemStack itemstack, boolean flag);

	public abstract String getInvName();

	public ContainerBase getGuiContainer(EntityPlayer entityPlayer)
	{
		return new ContainerStandardMachine(entityPlayer, this);
	}

	public void onGuiClosed(EntityPlayer entityplayer)
	{
	}

	public String getStartSoundFile()
	{
		return null;
	}

	public String getInterruptSoundFile()
	{
		return null;
	}

	public void onNetworkEvent(int event)
	{
		if (audioSource == null && getStartSoundFile() != null)
			audioSource = IC2.audioManager.createSource(this, getStartSoundFile());
		switch (event)
		{
		default:
			break;

		case 0: // '\0'
			if (audioSource != null)
				audioSource.play();
			break;

		case 1: // '\001'
			if (audioSource == null)
				break;
			audioSource.stop();
			if (getInterruptSoundFile() != null)
				IC2.audioManager.playOnce(this, getInterruptSoundFile());
			break;

		case 2: // '\002'
			if (audioSource != null)
				audioSource.stop();
			break;
		}
	}

	private int applyModifier(int base, int extra, double multiplier)
	{
		double ret = Math.round(((double)base + (double)extra) * multiplier);
		return ret <= 2147483647D ? (int)ret : 0x7fffffff;
	}

}
