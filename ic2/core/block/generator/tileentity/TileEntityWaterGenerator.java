// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityWaterGenerator.java

package ic2.core.block.generator.tileentity;

import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.block.generator.container.ContainerWaterGenerator;
import ic2.core.block.generator.gui.GuiWaterGenerator;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;

// Referenced classes of package ic2.core.block.generator.tileentity:
//			TileEntityBaseGenerator

public class TileEntityWaterGenerator extends TileEntityBaseGenerator
{

	public static Random randomizer = new Random();
	public int ticker;
	public int water;
	public int microStorage;
	public int maxWater;
	public final InvSlotConsumableLiquid fuelSlot;

	public TileEntityWaterGenerator()
	{
		super(2, 2);
		water = 0;
		microStorage = 0;
		maxWater = 2000;
		production = 2;
		ticker = randomizer.nextInt(tickRate());
		fuelSlot = new InvSlotConsumableLiquid(this, "fuel", 1, ic2.core.block.invslot.InvSlot.Access.NONE, 1, ic2.core.block.invslot.InvSlot.InvSide.TOP, FluidRegistry.WATER);
	}

	public void onLoaded()
	{
		super.onLoaded();
		updateWaterCount();
	}

	public int gaugeFuelScaled(int i)
	{
		if (fuel <= 0)
			return 0;
		else
			return (fuel * i) / maxWater;
	}

	public boolean gainFuel()
	{
		if (fuel + 500 > maxWater)
			return false;
		if (!fuelSlot.isEmpty())
		{
			ItemStack liquid = fuelSlot.consume(1);
			if (liquid == null)
				return false;
			fuel += 500;
			if (liquid.getItem().hasContainerItem())
				production = 1;
			else
				production = 2;
			return true;
		}
		if (fuel <= 0)
		{
			flowPower();
			production = microStorage / 100;
			microStorage -= production * 100;
			if (production > 0)
			{
				fuel++;
				return true;
			} else
			{
				return false;
			}
		} else
		{
			return false;
		}
	}

	public boolean gainFuelSub(ItemStack stack)
	{
		return false;
	}

	public boolean needsFuel()
	{
		return fuel <= maxWater;
	}

	public void flowPower()
	{
		if (ticker++ % tickRate() == 0)
			updateWaterCount();
		water = (water * IC2.energyGeneratorWater) / 100;
		if (water > 0)
			microStorage += water;
	}

	public void updateWaterCount()
	{
		int count = 0;
		for (int x = super.xCoord - 1; x < super.xCoord + 2; x++)
		{
			for (int y = super.yCoord - 1; y < super.yCoord + 2; y++)
			{
				for (int z = super.zCoord - 1; z < super.zCoord + 2; z++)
					if (super.worldObj.getBlockId(x, y, z) == ((Block) (Block.waterMoving)).blockID || super.worldObj.getBlockId(x, y, z) == Block.waterStill.blockID)
						count++;

			}

		}

		water = count;
	}

	public String getInvName()
	{
		return "Water Mill";
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		return new GuiWaterGenerator(new ContainerWaterGenerator(entityPlayer, this));
	}

	public int tickRate()
	{
		return 128;
	}

	public String getOperationSoundFile()
	{
		return "Generators/WatermillLoop.ogg";
	}

	public boolean delayActiveUpdate()
	{
		return true;
	}

	public ContainerBase getGuiContainer(EntityPlayer entityPlayer)
	{
		return new ContainerWaterGenerator(entityPlayer, this);
	}

}
