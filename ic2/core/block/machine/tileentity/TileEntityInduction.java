// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityInduction.java

package ic2.core.block.machine.tileentity;

import ic2.core.*;
import ic2.core.block.invslot.*;
import ic2.core.block.machine.ContainerInduction;
import ic2.core.block.machine.gui.GuiInduction;
import java.util.Random;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

// Referenced classes of package ic2.core.block.machine.tileentity:
//			TileEntityElectricMachine

public class TileEntityInduction extends TileEntityElectricMachine
	implements IHasGui
{

	public int soundTicker;
	public static short maxHeat = 10000;
	public short heat;
	public short progress;
	public final InvSlotProcessable inputSlotA = new InvSlotProcessableSmelting(this, "inputA", 0, 1);
	public final InvSlotProcessable inputSlotB = new InvSlotProcessableSmelting(this, "inputB", 1, 1);
	public final InvSlotOutput outputSlotA = new InvSlotOutput(this, "outputA", 3, 1);
	public final InvSlotOutput outputSlotB = new InvSlotOutput(this, "outputB", 4, 1);

	public TileEntityInduction()
	{
		super(maxHeat, 2, 2);
		heat = 0;
		progress = 0;
		soundTicker = IC2.random.nextInt(64);
	}

	public String getInvName()
	{
		if (IC2.platform.isRendering())
			return "Induction Furnace";
		else
			return "InductionFurnace";
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		heat = nbttagcompound.getShort("heat");
		progress = nbttagcompound.getShort("progress");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setShort("heat", heat);
		nbttagcompound.setShort("progress", progress);
	}

	public String getHeat()
	{
		return (new StringBuilder()).append("").append((heat * 100) / maxHeat).append("%").toString();
	}

	public int gaugeProgressScaled(int i)
	{
		return (i * progress) / 4000;
	}

	public int gaugeFuelScaled(int i)
	{
		return (i * Math.min(energy, maxEnergy)) / maxEnergy;
	}

	public void updateEntity()
	{
		super.updateEntity();
		boolean needsInvUpdate = false;
		boolean newActive = getActive();
		if (heat == 0)
			newActive = false;
		if (progress >= 4000)
		{
			operate();
			needsInvUpdate = true;
			progress = 0;
			newActive = false;
		}
		boolean canOperate = canOperate();
		if (energy > 0 && (canOperate || isRedstonePowered()))
		{
			energy--;
			if (heat < maxHeat)
				heat++;
			newActive = true;
		} else
		{
			heat -= Math.min(heat, 4);
		}
		if (!newActive || progress == 0)
		{
			if (canOperate)
			{
				if (energy >= 15)
					newActive = true;
			} else
			{
				progress = 0;
			}
		} else
		if (!canOperate || energy < 15)
		{
			if (!canOperate)
				progress = 0;
			newActive = false;
		}
		if (newActive && canOperate)
		{
			progress += heat / 30;
			energy -= 15;
		}
		if (needsInvUpdate)
			onInventoryChanged();
		if (newActive != getActive())
			setActive(newActive);
	}

	public void operate()
	{
		operate(inputSlotA, outputSlotA);
		operate(inputSlotB, outputSlotB);
	}

	public void operate(InvSlotProcessable inputSlot, InvSlotOutput outputSlot)
	{
		if (!canOperate(inputSlot, outputSlot))
		{
			return;
		} else
		{
			outputSlot.add(inputSlot.process(false));
			return;
		}
	}

	public boolean canOperate()
	{
		return canOperate(inputSlotA, outputSlotA) || canOperate(inputSlotB, outputSlotB);
	}

	public boolean canOperate(InvSlotProcessable inputSlot, InvSlotOutput outputSlot)
	{
		if (inputSlot.isEmpty())
			return false;
		ItemStack processResult = inputSlot.process(true);
		if (processResult == null)
			return false;
		else
			return outputSlot.canAdd(processResult);
	}

	public ContainerBase getGuiContainer(EntityPlayer entityPlayer)
	{
		return new ContainerInduction(entityPlayer, this);
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		return new GuiInduction(new ContainerInduction(entityPlayer, this));
	}

	public void onGuiClosed(EntityPlayer entityplayer)
	{
	}

	public float getWrenchDropRate()
	{
		return 0.8F;
	}

}
