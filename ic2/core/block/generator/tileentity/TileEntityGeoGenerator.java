// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityGeoGenerator.java

package ic2.core.block.generator.tileentity;

import ic2.core.*;
import ic2.core.block.generator.container.ContainerGeoGenerator;
import ic2.core.block.generator.gui.GuiGeoGenerator;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.*;

// Referenced classes of package ic2.core.block.generator.tileentity:
//			TileEntityBaseGenerator

public class TileEntityGeoGenerator extends TileEntityBaseGenerator
	implements IFluidTank
{

	public int maxLava;
	public final InvSlotConsumableLiquid fuelSlot;

	public TileEntityGeoGenerator()
	{
		super(IC2.energyGeneratorGeo, Math.max(IC2.energyGeneratorGeo, 32));
		maxLava = 24000;
		fuelSlot = new InvSlotConsumableLiquid(this, "fuel", 1, 1, FluidRegistry.LAVA);
	}

	public int gaugeFuelScaled(int i)
	{
		if (fuel <= 0)
			return 0;
		else
			return (fuel * i) / maxLava;
	}

	public boolean gainFuel()
	{
		if (fuel + 1000 > maxLava)
			return false;
		ItemStack liquid = fuelSlot.consume(1);
		if (liquid == null)
		{
			return false;
		} else
		{
			fuel += 1000;
			return true;
		}
	}

	public boolean gainFuelSub(ItemStack stack)
	{
		return false;
	}

	public boolean needsFuel()
	{
		return fuel <= maxLava;
	}

	public int distributeLava(int amount)
	{
		int need = maxLava - fuel;
		if (need > amount)
			need = amount;
		amount -= need;
		fuel += need / 2;
		return amount;
	}

	public String getInvName()
	{
		if (IC2.platform.isRendering())
			return "Geothermal Generator";
		else
			return "Geoth. Generator";
	}

	public String getOperationSoundFile()
	{
		return "Generators/GeothermalLoop.ogg";
	}

	public ContainerBase getGuiContainer(EntityPlayer entityPlayer)
	{
		return new ContainerGeoGenerator(entityPlayer, this);
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		return new GuiGeoGenerator(new ContainerGeoGenerator(entityPlayer, this));
	}

	public void onBlockBreak(int a, int b)
	{
		FluidEvent.fireEvent(new net.minecraftforge.fluids.FluidEvent.FluidSpilledEvent(new FluidStack(FluidRegistry.LAVA, 1000), super.worldObj, super.xCoord, super.yCoord, super.zCoord));
	}

	public FluidStack getFluid()
	{
		return fuel <= 0 ? null : new FluidStack(FluidRegistry.LAVA, fuel);
	}

	public int getFluidAmount()
	{
		return fuel;
	}

	public int getCapacity()
	{
		return maxLava;
	}

	public FluidTankInfo getInfo()
	{
		return new FluidTankInfo(this);
	}

	public int fill(FluidStack resource, boolean doFill)
	{
		if (resource.getFluid() != FluidRegistry.LAVA)
			return 0;
		int toAdd = Math.min(resource.amount, maxLava - fuel);
		if (doFill)
		{
			fuel += toAdd;
			FluidStack fluid = new FluidStack(resource, toAdd);
			FluidEvent.fireEvent(new net.minecraftforge.fluids.FluidEvent.FluidFillingEvent(fluid, super.worldObj, super.xCoord, super.yCoord, super.zCoord, this));
		}
		return toAdd;
	}

	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		return null;
	}
}
