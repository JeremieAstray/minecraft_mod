// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntitySolarGenerator.java

package ic2.core.block.generator.tileentity;

import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.block.generator.container.ContainerSolarGenerator;
import ic2.core.block.generator.gui.GuiSolarGenerator;
import java.util.Random;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenDesert;
import net.minecraft.world.biome.WorldChunkManager;

// Referenced classes of package ic2.core.block.generator.tileentity:
//			TileEntityBaseGenerator

public class TileEntitySolarGenerator extends TileEntityBaseGenerator
{

	public static Random randomizer = new Random();
	public int ticker;
	public boolean sunIsVisible;

	public TileEntitySolarGenerator()
	{
		super(1, 1);
		sunIsVisible = false;
		ticker = randomizer.nextInt(tickRate());
	}

	public void onLoaded()
	{
		super.onLoaded();
		updateSunVisibility();
	}

	public int gaugeFuelScaled(int i)
	{
		return i;
	}

	public boolean gainEnergy()
	{
		if (ticker++ % tickRate() == 0)
			updateSunVisibility();
		if (sunIsVisible)
		{
			double gen = (double)IC2.energyGeneratorSolar / 100D;
			if (gen >= 1.0D || ticker % (100 - IC2.energyGeneratorSolar) == 0)
				storage += (int)Math.ceil(gen);
			return true;
		} else
		{
			return false;
		}
	}

	public boolean gainFuel()
	{
		return false;
	}

	public void updateSunVisibility()
	{
		sunIsVisible = isSunVisible(super.worldObj, super.xCoord, super.yCoord + 1, super.zCoord);
	}

	public static boolean isSunVisible(World world, int x, int y, int z)
	{
		return world.isDaytime() && !world.provider.hasNoSky && world.canBlockSeeTheSky(x, y, z) && ((world.getWorldChunkManager().getBiomeGenAt(x, z) instanceof BiomeGenDesert) || !world.isRaining() && !world.isThundering());
	}

	public boolean needsFuel()
	{
		return true;
	}

	public String getInvName()
	{
		return "Solar Panel";
	}

	public ContainerBase getGuiContainer(EntityPlayer entityPlayer)
	{
		return new ContainerSolarGenerator(entityPlayer, this);
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		return new GuiSolarGenerator(new ContainerSolarGenerator(entityPlayer, this));
	}

	public int tickRate()
	{
		return 128;
	}

	public boolean delayActiveUpdate()
	{
		return true;
	}

}
