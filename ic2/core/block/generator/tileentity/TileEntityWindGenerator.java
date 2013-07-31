// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityWindGenerator.java

package ic2.core.block.generator.tileentity;

import ic2.core.*;
import ic2.core.block.generator.container.ContainerWindGenerator;
import ic2.core.block.generator.gui.GuiWindGenerator;
import ic2.core.util.StackUtil;
import java.util.Random;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

// Referenced classes of package ic2.core.block.generator.tileentity:
//			TileEntityBaseGenerator

public class TileEntityWindGenerator extends TileEntityBaseGenerator
{

	public static Random randomizer = new Random();
	public double subproduction;
	public double substorage;
	public int ticker;
	public int obscuratedBlockCount;

	public TileEntityWindGenerator()
	{
		super(4, 4);
		subproduction = 0.0D;
		substorage = 0.0D;
		ticker = randomizer.nextInt(tickRate());
	}

	public int gaugeFuelScaled(int i)
	{
		double prod = subproduction / 3D;
		int re = (int)(prod * (double)i);
		if (re < 0)
			return 0;
		if (re > i)
			return i;
		else
			return re;
	}

	public int getOverheatScaled(int i)
	{
		double prod = (subproduction - 5D) / 5D;
		if (subproduction <= 5D)
			return 0;
		if (subproduction >= 10D)
			return i;
		else
			return (int)(prod * (double)i);
	}

	public void onLoaded()
	{
		super.onLoaded();
		updateObscuratedBlockCount();
	}

	public boolean gainEnergy()
	{
		ticker++;
		if (ticker % tickRate() == 0)
		{
			if (ticker % (8 * tickRate()) == 0)
				updateObscuratedBlockCount();
			subproduction = (double)(IC2.windStrength * (super.yCoord - 64 - obscuratedBlockCount)) / 750D;
			if (subproduction <= 0.0D)
				return false;
			if (super.worldObj.isThundering())
				subproduction *= 1.5D;
			else
			if (super.worldObj.isRaining())
				subproduction *= 1.2D;
			if (subproduction > 5D && (double)super.worldObj.rand.nextInt(5000) <= subproduction - 5D)
			{
				subproduction = 0.0D;
				super.worldObj.setBlock(super.xCoord, super.yCoord, super.zCoord, Ic2Items.generator.itemID, Ic2Items.generator.getItemDamage(), 7);
				for (int i = super.worldObj.rand.nextInt(5); i > 0; i--)
					StackUtil.dropAsEntity(super.worldObj, super.xCoord, super.yCoord, super.zCoord, new ItemStack(Item.ingotIron));

				return false;
			}
			subproduction *= IC2.energyGeneratorWind;
			subproduction /= 100D;
		}
		substorage += subproduction;
		production = (short)(int)substorage;
		if (storage + production >= maxStorage)
		{
			substorage = 0.0D;
			return false;
		} else
		{
			storage += production;
			substorage -= production;
			return true;
		}
	}

	public boolean gainFuel()
	{
		return false;
	}

	public void updateObscuratedBlockCount()
	{
		obscuratedBlockCount = -1;
		for (int x = -4; x < 5; x++)
		{
			for (int y = -2; y < 5; y++)
			{
				for (int z = -4; z < 5; z++)
					if (super.worldObj.getBlockId(x + super.xCoord, y + super.yCoord, z + super.zCoord) != 0)
						obscuratedBlockCount++;

			}

		}

	}

	public boolean needsFuel()
	{
		return true;
	}

	public int getMaxEnergyOutput()
	{
		return 10;
	}

	public String getInvName()
	{
		return "Wind Mill";
	}

	public int tickRate()
	{
		return 128;
	}

	public String getOperationSoundFile()
	{
		return "Generators/WindGenLoop.ogg";
	}

	public boolean delayActiveUpdate()
	{
		return true;
	}

	public ContainerBase getGuiContainer(EntityPlayer entityPlayer)
	{
		return new ContainerWindGenerator(entityPlayer, this);
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		return new GuiWindGenerator(new ContainerWindGenerator(entityPlayer, this));
	}

}
