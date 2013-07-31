// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityMiner.java

package ic2.core.block.machine.tileentity;

import cpw.mods.fml.relauncher.ReflectionHelper;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItemManager;
import ic2.core.*;
import ic2.core.audio.*;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableId;
import ic2.core.block.machine.ContainerMiner;
import ic2.core.block.machine.gui.GuiMiner;
import ic2.core.item.tool.ItemScanner;
import ic2.core.util.Liquid;
import ic2.core.util.StackUtil;
import java.io.Serializable;
import java.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

// Referenced classes of package ic2.core.block.machine.tileentity:
//			TileEntityElectricMachine, TileEntityPump

public class TileEntityMiner extends TileEntityElectricMachine
	implements IHasGui
{
	static final class MineResult extends Enum
	{

		public static final MineResult Working;
		public static final MineResult Done;
		public static final MineResult Failed_Temp;
		public static final MineResult Failed_Perm;
		private static final MineResult $VALUES[];

		public static MineResult[] values()
		{
			return (MineResult[])$VALUES.clone();
		}

		public static MineResult valueOf(String name)
		{
			return (MineResult)Enum.valueOf(ic2/core/block/machine/tileentity/TileEntityMiner$MineResult, name);
		}

		static 
		{
			Working = new MineResult("Working", 0);
			Done = new MineResult("Done", 1);
			Failed_Temp = new MineResult("Failed_Temp", 2);
			Failed_Perm = new MineResult("Failed_Perm", 3);
			$VALUES = (new MineResult[] {
				Working, Done, Failed_Temp, Failed_Perm
			});
		}

		private MineResult(String s, int i)
		{
			super(s, i);
		}
	}

	static final class Mode extends Enum
	{

		public static final Mode None;
		public static final Mode Withdraw;
		public static final Mode MineAir;
		public static final Mode MineDrill;
		public static final Mode MineDDrill;
		private static final Mode $VALUES[];

		public static Mode[] values()
		{
			return (Mode[])$VALUES.clone();
		}

		public static Mode valueOf(String name)
		{
			return (Mode)Enum.valueOf(ic2/core/block/machine/tileentity/TileEntityMiner$Mode, name);
		}

		static 
		{
			None = new Mode("None", 0);
			Withdraw = new Mode("Withdraw", 1);
			MineAir = new Mode("MineAir", 2);
			MineDrill = new Mode("MineDrill", 3);
			MineDDrill = new Mode("MineDDrill", 4);
			$VALUES = (new Mode[] {
				None, Withdraw, MineAir, MineDrill, MineDDrill
			});
		}

		private Mode(String s, int i)
		{
			super(s, i);
		}
	}


	private Mode lastMode;
	public int progress;
	private int scannedLevel;
	private int scanRange;
	private int lastX;
	private int lastZ;
	public boolean pumpMode;
	public boolean canProvideLiquid;
	public int liquidX;
	public int liquidY;
	public int liquidZ;
	private AudioSource audioSource;
	public final InvSlotConsumableId drillSlot;
	public final InvSlotConsumableBlock pipeSlot;
	public final InvSlotConsumableId scannerSlot;

	public TileEntityMiner()
	{
		super(1000, IC2.enableMinerLapotron ? 3 : 1, 0);
		lastMode = Mode.None;
		progress = 0;
		scannedLevel = -1;
		scanRange = 0;
		pumpMode = false;
		canProvideLiquid = false;
		drillSlot = new InvSlotConsumableId(this, "drill", 3, ic2.core.block.invslot.InvSlot.Access.IO, 1, ic2.core.block.invslot.InvSlot.InvSide.TOP, new int[] {
			Ic2Items.miningDrill.itemID, Ic2Items.diamondDrill.itemID
		});
		pipeSlot = new InvSlotConsumableBlock(this, "pipe", 2, ic2.core.block.invslot.InvSlot.Access.IO, 1, ic2.core.block.invslot.InvSlot.InvSide.SIDE);
		scannerSlot = new InvSlotConsumableId(this, "scanner", 1, ic2.core.block.invslot.InvSlot.Access.IO, 1, ic2.core.block.invslot.InvSlot.InvSide.BOTTOM, new int[] {
			Ic2Items.odScanner.itemID, Ic2Items.ovScanner.itemID
		});
	}

	public void onLoaded()
	{
		super.onLoaded();
		scannedLevel = -1;
		lastX = super.xCoord;
		lastZ = super.zCoord;
		canProvideLiquid = false;
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

	public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);
		lastMode = Mode.values()[nbtTagCompound.getInteger("lastMode")];
		progress = nbtTagCompound.getInteger("progress");
	}

	public void writeToNBT(NBTTagCompound nbtTagCompound)
	{
		super.writeToNBT(nbtTagCompound);
		nbtTagCompound.setInteger("lastMode", lastMode.ordinal());
		nbtTagCompound.setInteger("progress", progress);
	}

	public void updateEntity()
	{
		super.updateEntity();
		chargeTools();
		if (work())
		{
			onInventoryChanged();
			setActive(true);
		} else
		{
			setActive(false);
		}
	}

	private void chargeTools()
	{
		if (!scannerSlot.isEmpty())
			energy -= ElectricItem.manager.charge(scannerSlot.get(), energy, 2, false, false);
		if (!drillSlot.isEmpty())
			energy -= ElectricItem.manager.charge(drillSlot.get(), energy, 1, false, false);
	}

	private boolean work()
	{
		int operationHeight = getOperationHeight();
		if (drillSlot.isEmpty())
			return withDrawPipe(operationHeight);
		if (operationHeight >= 0)
		{
			int blockId = super.worldObj.getBlockId(super.xCoord, operationHeight, super.zCoord);
			if (blockId != Ic2Items.miningPipeTip.itemID)
				if (operationHeight > 0)
					return digDown(operationHeight, false);
				else
					return false;
			MineResult result = mineLevel(operationHeight);
			if (result == MineResult.Done)
				return digDown(operationHeight - 1, true);
			return result == MineResult.Working;
		} else
		{
			return false;
		}
	}

	private int getOperationHeight()
	{
		for (int y = super.yCoord - 1; y >= 0; y--)
		{
			int blockId = super.worldObj.getBlockId(super.xCoord, y, super.zCoord);
			if (blockId != Ic2Items.miningPipe.itemID)
				return y;
		}

		return -1;
	}

	private boolean withDrawPipe(int y)
	{
		if (lastMode != Mode.Withdraw)
		{
			lastMode = Mode.Withdraw;
			progress = 0;
		}
		if (y < 0 || super.worldObj.getBlockId(super.xCoord, y, super.zCoord) != Ic2Items.miningPipeTip.itemID)
			y++;
		if (y != super.yCoord && energy >= 3)
		{
			if (progress < 20)
			{
				energy -= 3;
				progress++;
			} else
			{
				progress = 0;
				removePipe(y);
			}
			return true;
		} else
		{
			return false;
		}
	}

	private void removePipe(int y)
	{
		super.worldObj.setBlockToAir(super.xCoord, y, super.zCoord);
		List drops = new ArrayList();
		drops.add(Ic2Items.miningPipe.copy());
		StackUtil.distributeDrop(this, drops);
		if (!pipeSlot.isEmpty() && pipeSlot.get().itemID != Ic2Items.miningPipe.itemID)
		{
			ItemStack filler = pipeSlot.consume(1);
			net.minecraft.item.Item fillerItem = filler.getItem();
			if (fillerItem instanceof ItemBlock)
				((ItemBlock)fillerItem).onItemUse(filler, new Ic2Player(super.worldObj), super.worldObj, super.xCoord, y + 1, super.zCoord, 0, 0.0F, 0.0F, 0.0F);
		}
	}

	private boolean digDown(int y, boolean removeTipAbove)
	{
		if (pipeSlot.isEmpty() || pipeSlot.get().itemID != Ic2Items.miningPipe.itemID)
			return false;
		if (y < 0)
		{
			if (removeTipAbove)
				super.worldObj.setBlock(super.xCoord, y + 1, super.zCoord, Ic2Items.miningPipe.itemID);
			return false;
		}
		MineResult result = mineBlock(super.xCoord, y, super.zCoord);
		if (result == MineResult.Failed_Temp || result == MineResult.Failed_Perm)
		{
			if (removeTipAbove)
				super.worldObj.setBlock(super.xCoord, y + 1, super.zCoord, Ic2Items.miningPipe.itemID);
			return false;
		}
		if (result == MineResult.Done)
		{
			if (removeTipAbove)
				super.worldObj.setBlock(super.xCoord, y + 1, super.zCoord, Ic2Items.miningPipe.itemID);
			pipeSlot.consume(1);
			super.worldObj.setBlock(super.xCoord, y, super.zCoord, Ic2Items.miningPipeTip.itemID);
		}
		return true;
	}

	private MineResult mineLevel(int y)
	{
		if (scannerSlot.isEmpty())
			return MineResult.Done;
		if (scannedLevel != y)
			scanRange = ((ItemScanner)scannerSlot.get().getItem()).startLayerScan(scannerSlot.get());
		if (scanRange > 0)
		{
			scannedLevel = y;
			for (int x = super.xCoord - scanRange; x <= super.xCoord + scanRange; x++)
			{
				for (int z = super.zCoord - scanRange; z <= super.zCoord + scanRange; z++)
				{
					int blockId = super.worldObj.getBlockId(x, y, z);
					int meta = super.worldObj.getBlockMetadata(x, y, z);
					boolean isValidTarget = false;
					if (ItemScanner.isValuable(blockId, meta) && canMine(x, y, z))
						isValidTarget = true;
					else
					if (pumpMode)
					{
						ic2.core.util.Liquid.LiquidData liquid = Liquid.getLiquid(super.worldObj, x, y, z);
						if (liquid != null && canPump(x, y, z))
							isValidTarget = true;
					}
					if (!isValidTarget)
						continue;
					MineResult result = mineTowards(x, y, z);
					if (result == MineResult.Done)
						return MineResult.Working;
					if (result != MineResult.Failed_Perm)
						return result;
				}

			}

			return MineResult.Done;
		} else
		{
			return MineResult.Failed_Temp;
		}
	}

	private MineResult mineTowards(int x, int y, int z)
	{
		int cx = super.xCoord;
		for (int cz = super.zCoord; cx != x || cz != z;)
		{
			boolean isCurrentPos = cx == lastX && cz == lastZ;
			if (Math.abs(x - cx) >= Math.abs(z - cz))
				cx += x <= cx ? -1 : 1;
			else
				cz += z <= cz ? -1 : 1;
			boolean isBlocking = false;
			if (isCurrentPos)
			{
				isBlocking = true;
			} else
			{
				int blockId = super.worldObj.getBlockId(cx, y, cz);
				if (blockId != 0 && !Block.blocksList[blockId].isAirBlock(super.worldObj, cx, y, cz))
				{
					ic2.core.util.Liquid.LiquidData liquid = Liquid.getLiquid(super.worldObj, cx, y, cz);
					if (liquid == null || liquid.isSource || pumpMode && canPump(x, y, z))
						isBlocking = true;
				}
			}
			if (isBlocking)
			{
				MineResult result = mineBlock(cx, y, cz);
				if (result == MineResult.Done)
				{
					lastX = cx;
					lastZ = cz;
				}
				return result;
			}
		}

		lastX = super.xCoord;
		lastZ = super.zCoord;
		return MineResult.Done;
	}

	private MineResult mineBlock(int x, int y, int z)
	{
		int blockId = super.worldObj.getBlockId(x, y, z);
		boolean isAirBlock = true;
		if (blockId != 0 && !Block.blocksList[blockId].isAirBlock(super.worldObj, x, y, z))
		{
			isAirBlock = false;
			ic2.core.util.Liquid.LiquidData liquidData = Liquid.getLiquid(super.worldObj, x, y, z);
			if (liquidData != null)
			{
				if (liquidData.isSource || pumpMode && canPump(x, y, z))
				{
					liquidX = x;
					liquidY = y;
					liquidZ = z;
					canProvideLiquid = true;
					return pumpMode ? MineResult.Failed_Temp : MineResult.Failed_Perm;
				}
			} else
			if (!canMine(x, y, z))
				return MineResult.Failed_Perm;
		}
		canProvideLiquid = false;
		Mode mode;
		int energyPerTick;
		int duration;
		if (isAirBlock)
		{
			mode = Mode.MineAir;
			energyPerTick = 3;
			duration = 20;
		} else
		if (drillSlot.get().itemID == Ic2Items.miningDrill.itemID)
		{
			mode = Mode.MineDrill;
			energyPerTick = 6;
			duration = 200;
		} else
		if (drillSlot.get().itemID == Ic2Items.diamondDrill.itemID)
		{
			mode = Mode.MineDDrill;
			energyPerTick = 20;
			duration = 50;
		} else
		{
			throw new IllegalStateException((new StringBuilder()).append("invalid drill: ").append(drillSlot.get()).toString());
		}
		if (lastMode != mode)
		{
			lastMode = mode;
			progress = 0;
		}
		if (progress < duration)
		{
			if (energy >= energyPerTick)
			{
				energy -= energyPerTick;
				progress++;
				return MineResult.Working;
			}
		} else
		if (isAirBlock || harvestBlock(x, y, z, blockId))
		{
			progress = 0;
			return MineResult.Done;
		}
		return MineResult.Failed_Temp;
	}

	private boolean harvestBlock(int x, int y, int z, int blockId)
	{
		if (drillSlot.get().itemID == Ic2Items.miningDrill.itemID)
		{
			if (!ElectricItem.manager.use(drillSlot.get(), 50, null))
				return false;
		} else
		if (drillSlot.get().itemID == Ic2Items.diamondDrill.itemID)
		{
			if (!ElectricItem.manager.use(drillSlot.get(), 80, null))
				return false;
		} else
		{
			throw new IllegalStateException((new StringBuilder()).append("invalid drill: ").append(drillSlot.get()).toString());
		}
		int energyCost = 2 * (super.yCoord - y);
		if (energy >= energyCost)
		{
			energy -= energyCost;
			StackUtil.distributeDrop(this, Block.blocksList[blockId].getBlockDropped(super.worldObj, x, y, z, super.worldObj.getBlockMetadata(x, y, z), 0));
			super.worldObj.setBlockToAir(x, y, z);
			return true;
		} else
		{
			return false;
		}
	}

	private boolean canPump(int x, int y, int z)
	{
		return false;
	}

	public boolean canMine(int x, int y, int z)
	{
		int meta;
		Block block;
		int id = super.worldObj.getBlockId(x, y, z);
		meta = super.worldObj.getBlockMetadata(x, y, z);
		if (id == 0)
			return true;
		if (id == Ic2Items.miningPipe.itemID || id == Ic2Items.miningPipeTip.itemID || id == ((Block) (Block.chest)).blockID)
			return false;
		if ((id == ((Block) (Block.waterMoving)).blockID || id == Block.waterStill.blockID || id == ((Block) (Block.lavaMoving)).blockID || id == Block.lavaStill.blockID) && isPumpConnected())
			return true;
		block = Block.blocksList[id];
		if (block.getBlockHardness(super.worldObj, x, y, z) < 0.0F)
			return false;
		if (block.canCollideCheck(meta, false) && block.blockMaterial.isToolNotRequired())
			return true;
		if (id == Block.web.blockID)
			return true;
		if (drillSlot.isEmpty())
			break MISSING_BLOCK_LABEL_396;
		List tc;
		HashMap toolClasses = (HashMap)ReflectionHelper.getPrivateValue(net/minecraftforge/common/ForgeHooks, null, new String[] {
			"toolClasses"
		});
		tc = (List)toolClasses.get(Integer.valueOf(drillSlot.get().itemID));
		if (tc == null)
			return drillSlot.get().canHarvestBlock(block);
		int hvl;
		Integer bhl;
		Object ta[] = tc.toArray();
		String cls = (String)ta[0];
		hvl = ((Integer)ta[1]).intValue();
		HashMap toolHarvestLevels = (HashMap)ReflectionHelper.getPrivateValue(net/minecraftforge/common/ForgeHooks, null, new String[] {
			"toolHarvestLevels"
		});
		bhl = (Integer)toolHarvestLevels.get(Arrays.asList(new Serializable[] {
			Integer.valueOf(block.blockID), Integer.valueOf(meta), cls
		}));
		if (bhl == null)
			return drillSlot.get().canHarvestBlock(block);
		if (bhl.intValue() > hvl)
			return false;
		return drillSlot.get().canHarvestBlock(block);
		Throwable e;
		e;
		return false;
		return false;
	}

	public boolean isPumpConnected()
	{
		if ((super.worldObj.getBlockTileEntity(super.xCoord, super.yCoord + 1, super.zCoord) instanceof TileEntityPump) && ((TileEntityPump)super.worldObj.getBlockTileEntity(super.xCoord, super.yCoord + 1, super.zCoord)).canHarvest())
			return true;
		if ((super.worldObj.getBlockTileEntity(super.xCoord, super.yCoord - 1, super.zCoord) instanceof TileEntityPump) && ((TileEntityPump)super.worldObj.getBlockTileEntity(super.xCoord, super.yCoord - 1, super.zCoord)).canHarvest())
			return true;
		if ((super.worldObj.getBlockTileEntity(super.xCoord + 1, super.yCoord, super.zCoord) instanceof TileEntityPump) && ((TileEntityPump)super.worldObj.getBlockTileEntity(super.xCoord + 1, super.yCoord, super.zCoord)).canHarvest())
			return true;
		if ((super.worldObj.getBlockTileEntity(super.xCoord - 1, super.yCoord, super.zCoord) instanceof TileEntityPump) && ((TileEntityPump)super.worldObj.getBlockTileEntity(super.xCoord - 1, super.yCoord, super.zCoord)).canHarvest())
			return true;
		if ((super.worldObj.getBlockTileEntity(super.xCoord, super.yCoord, super.zCoord + 1) instanceof TileEntityPump) && ((TileEntityPump)super.worldObj.getBlockTileEntity(super.xCoord, super.yCoord, super.zCoord + 1)).canHarvest())
			return true;
		return (super.worldObj.getBlockTileEntity(super.xCoord, super.yCoord, super.zCoord - 1) instanceof TileEntityPump) && ((TileEntityPump)super.worldObj.getBlockTileEntity(super.xCoord, super.yCoord, super.zCoord - 1)).canHarvest();
	}

	public boolean isAnyPumpConnected()
	{
		if (super.worldObj.getBlockTileEntity(super.xCoord, super.yCoord + 1, super.zCoord) instanceof TileEntityPump)
			return true;
		if (super.worldObj.getBlockTileEntity(super.xCoord, super.yCoord - 1, super.zCoord) instanceof TileEntityPump)
			return true;
		if (super.worldObj.getBlockTileEntity(super.xCoord + 1, super.yCoord, super.zCoord) instanceof TileEntityPump)
			return true;
		if (super.worldObj.getBlockTileEntity(super.xCoord - 1, super.yCoord, super.zCoord) instanceof TileEntityPump)
			return true;
		if (super.worldObj.getBlockTileEntity(super.xCoord, super.yCoord, super.zCoord + 1) instanceof TileEntityPump)
			return true;
		return super.worldObj.getBlockTileEntity(super.xCoord, super.yCoord, super.zCoord - 1) instanceof TileEntityPump;
	}

	public String getInvName()
	{
		return "Miner";
	}

	public int gaugeEnergyScaled(int i)
	{
		if (energy <= 0)
			return 0;
		int r = (energy * i) / 1000;
		if (r > i)
			r = i;
		return r;
	}

	public ContainerBase getGuiContainer(EntityPlayer entityPlayer)
	{
		return new ContainerMiner(entityPlayer, this);
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		return new GuiMiner(new ContainerMiner(entityPlayer, this));
	}

	public void onGuiClosed(EntityPlayer entityplayer)
	{
	}

	public void onNetworkUpdate(String field)
	{
		if (field.equals("active") && prevActive != getActive())
		{
			if (audioSource == null)
				audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Machines/MinerOp.ogg", true, false, IC2.audioManager.defaultVolume);
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
