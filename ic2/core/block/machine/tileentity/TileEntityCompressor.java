// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityCompressor.java

package ic2.core.block.machine.tileentity;

import ic2.api.Direction;
import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.Recipes;
import ic2.core.BasicMachineRecipeManager;
import ic2.core.Ic2Items;
import ic2.core.block.invslot.*;
import ic2.core.block.machine.ContainerStandardMachine;
import ic2.core.block.machine.gui.GuiCompressor;
import java.util.*;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

// Referenced classes of package ic2.core.block.machine.tileentity:
//			TileEntityStandardMachine, TileEntityPump

public class TileEntityCompressor extends TileEntityStandardMachine
{

	public TileEntityPump validPump;
	public static List recipes = new Vector();

	public TileEntityCompressor()
	{
		super(2, 400);
		inputSlot = new InvSlotProcessableGeneric(this, "input", 0, 1, Recipes.compressor);
	}

	public static void init()
	{
		Recipes.compressor = new BasicMachineRecipeManager();
		Recipes.compressor.addRecipe(Ic2Items.plantBall, Ic2Items.compressedPlantBall);
		Recipes.compressor.addRecipe(Ic2Items.hydratedCoalDust, Ic2Items.hydratedCoalClump);
		Recipes.compressor.addRecipe(new ItemStack(Block.netherrack, 3), new ItemStack(Block.netherBrick));
		Recipes.compressor.addRecipe(new ItemStack(Block.sand), new ItemStack(Block.sandStone));
		Recipes.compressor.addRecipe(new ItemStack(Item.snowball), new ItemStack(Block.ice));
		Recipes.compressor.addRecipe(Ic2Items.waterCell, new ItemStack(Item.snowball));
		Recipes.compressor.addRecipe(Ic2Items.mixedMetalIngot, Ic2Items.advancedAlloy);
		Recipes.compressor.addRecipe(Ic2Items.carbonMesh, Ic2Items.carbonPlate);
		Recipes.compressor.addRecipe(Ic2Items.coalBall, Ic2Items.compressedCoalBall);
		Recipes.compressor.addRecipe(Ic2Items.coalChunk, new ItemStack(Item.diamond));
		Recipes.compressor.addRecipe(Ic2Items.constructionFoam, Ic2Items.constructionFoamPellet);
		Recipes.compressor.addRecipe(Ic2Items.cell, Ic2Items.airCell);
	}

	public ItemStack getResultFor(ItemStack itemStack, boolean adjustInput)
	{
		return (ItemStack)Recipes.compressor.getOutputFor(itemStack, adjustInput);
	}

	public boolean canOperate()
	{
		return super.canOperate() || getValidPump() != null && outputSlot.canAdd(new ItemStack(Item.snowball));
	}

	public void operate()
	{
		if (!canOperate())
			return;
		if (inputSlot.isEmpty() || getResultFor(inputSlot.get(), false) == null)
		{
			TileEntityPump pump = getValidPump();
			if (pump == null)
				return;
			pump.pumpCharge = 0;
			pump.clearLastBlock();
			outputSlot.add(new ItemStack(Item.snowball));
		} else
		{
			super.operate();
		}
	}

	public TileEntityPump getValidPump()
	{
		if (validPump != null && !validPump.isInvalid())
		{
			FluidStack liquid = validPump.pump(((TileEntity) (validPump)).xCoord, ((TileEntity) (validPump)).yCoord, ((TileEntity) (validPump)).zCoord);
			if (liquid != null && liquid.getFluid() == FluidRegistry.WATER)
				return validPump;
		}
		validPump = null;
		Direction arr$[] = Direction.values();
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Direction dir = arr$[i$];
			TileEntity te = dir.applyToTileEntity(this);
			if (!(te instanceof TileEntityPump))
				continue;
			FluidStack liquid = ((TileEntityPump)te).pump(((TileEntity) (validPump)).xCoord, ((TileEntity) (validPump)).yCoord, ((TileEntity) (validPump)).zCoord);
			if (liquid != null && liquid.getFluid() == FluidRegistry.WATER)
			{
				validPump = (TileEntityPump)te;
				return validPump;
			}
		}

		return null;
	}

	public String getInvName()
	{
		return "Compressor";
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		return new GuiCompressor(new ContainerStandardMachine(entityPlayer, this));
	}

	public String getStartSoundFile()
	{
		return "Machines/CompressorOp.ogg";
	}

	public String getInterruptSoundFile()
	{
		return "Machines/InterruptOne.ogg";
	}

	public float getWrenchDropRate()
	{
		return 0.85F;
	}

}
