// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityElectricFurnace.java

package ic2.core.block.machine.tileentity;

import ic2.core.block.invslot.InvSlotProcessableSmelting;
import ic2.core.block.machine.ContainerStandardMachine;
import ic2.core.block.machine.gui.GuiElecFurnace;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

// Referenced classes of package ic2.core.block.machine.tileentity:
//			TileEntityStandardMachine

public class TileEntityElectricFurnace extends TileEntityStandardMachine
{

	public TileEntityElectricFurnace()
	{
		super(3, 130);
		inputSlot = new InvSlotProcessableSmelting(this, "input", 0, 1);
	}

	public ItemStack getResultFor(ItemStack input, boolean adjustInput)
	{
		ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(input);
		if (adjustInput && result != null)
			input.stackSize--;
		return result;
	}

	public String getInvName()
	{
		return "Electric Furnace";
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		return new GuiElecFurnace(new ContainerStandardMachine(entityPlayer, this));
	}

	public String getStartSoundFile()
	{
		return "Machines/Electro Furnace/ElectroFurnaceLoop.ogg";
	}

	public String getInterruptSoundFile()
	{
		return null;
	}
}
