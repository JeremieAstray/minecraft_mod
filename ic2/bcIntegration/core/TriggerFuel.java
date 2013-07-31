// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TriggerFuel.java

package ic2.bcIntegration.core;

import buildcraft.api.core.IIconProvider;
import buildcraft.api.gates.ITriggerParameter;
import ic2.core.Ic2Items;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

// Referenced classes of package ic2.bcIntegration.core:
//			Trigger, MappedIconProvider

public class TriggerFuel extends Trigger
{

	private final boolean has;
	private final MappedIconProvider iconProvider;

	public TriggerFuel(int id, boolean has)
	{
		super(id);
		this.has = has;
		iconProvider = new MappedIconProvider(new ItemStack[] {
			Ic2Items.filledFuelCan, Ic2Items.fuelCan
		});
	}

	public int getIconIndex()
	{
		return has ? 0 : 1;
	}

	public IIconProvider getIconProvider()
	{
		return iconProvider;
	}

	public String getDescription()
	{
		return has ? "Has Fuel" : "No Fuel";
	}

	public boolean isTriggerActive(ForgeDirection side, TileEntity tile, ITriggerParameter parameter)
	{
		if (tile instanceof TileEntityBaseGenerator)
		{
			TileEntityBaseGenerator teb = (TileEntityBaseGenerator)tile;
			if ((teb.fuel > 0) == has)
				return true;
		}
		return false;
	}
}
