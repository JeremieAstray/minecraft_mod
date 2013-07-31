// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContainerSolarGenerator.java

package ic2.core.block.generator.container;

import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

// Referenced classes of package ic2.core.block.generator.container:
//			ContainerBaseGenerator

public class ContainerSolarGenerator extends ContainerBaseGenerator
{

	public final TileEntitySolarGenerator tileEntity;
	public boolean sunIsVisible;
	public boolean initialized;

	public ContainerSolarGenerator(EntityPlayer entityPlayer, TileEntitySolarGenerator tileEntity)
	{
		super(entityPlayer, tileEntity, 166, 80, 26);
		sunIsVisible = false;
		initialized = false;
		this.tileEntity = tileEntity;
	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0; i < super.crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)super.crafters.get(i);
			if (sunIsVisible != tileEntity.sunIsVisible || !initialized)
			{
				icrafting.sendProgressBarUpdate(this, 3, tileEntity.sunIsVisible ? 1 : 0);
				initialized = true;
			}
		}

		sunIsVisible = tileEntity.sunIsVisible;
	}

	public void updateProgressBar(int index, int value)
	{
		super.updateProgressBar(index, value);
		switch (index)
		{
		case 3: // '\003'
			tileEntity.sunIsVisible = value != 0;
			break;
		}
	}
}
