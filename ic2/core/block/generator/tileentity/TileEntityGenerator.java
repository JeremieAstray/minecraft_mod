// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityGenerator.java

package ic2.core.block.generator.tileentity;

import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.block.generator.container.ContainerGenerator;
import ic2.core.block.generator.gui.GuiGenerator;
import ic2.core.block.invslot.InvSlotConsumableFuel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

// Referenced classes of package ic2.core.block.generator.tileentity:
//			TileEntityBaseGenerator

public class TileEntityGenerator extends TileEntityBaseGenerator
{

	public int itemFuelTime;
	public final InvSlotConsumableFuel fuelSlot = new InvSlotConsumableFuel(this, "fuel", 1, 1, false);

	public TileEntityGenerator()
	{
		super(IC2.energyGeneratorBase, 4000);
		itemFuelTime = 0;
	}

	public int gaugeFuelScaled(int i)
	{
		if (fuel <= 0)
			return 0;
		if (itemFuelTime <= 0)
			itemFuelTime = fuel;
		return Math.min((fuel * i) / itemFuelTime, i);
	}

	public boolean gainFuel()
	{
		int fuelValue = fuelSlot.consumeFuel() / 4;
		if (fuelValue == 0)
		{
			return false;
		} else
		{
			fuel += fuelValue;
			itemFuelTime = fuelValue;
			return true;
		}
	}

	public String getInvName()
	{
		return "Generator";
	}

	public boolean isConverting()
	{
		return fuel > 0;
	}

	public String getOperationSoundFile()
	{
		return "Generators/GeneratorLoop.ogg";
	}

	public ContainerBase getGuiContainer(EntityPlayer entityPlayer)
	{
		return new ContainerGenerator(entityPlayer, this);
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		return new GuiGenerator(new ContainerGenerator(entityPlayer, this));
	}
}
