// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TriggerScrap.java

package ic2.bcIntegration.core;

import buildcraft.api.core.IIconProvider;
import buildcraft.api.gates.ITriggerParameter;
import ic2.core.Ic2Items;
import ic2.core.block.machine.tileentity.TileEntityMatter;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

// Referenced classes of package ic2.bcIntegration.core:
//			Trigger, MappedIconProvider

public class TriggerScrap extends Trigger
{

	private final boolean inverse;
	private final MappedIconProvider iconProvider;

	public TriggerScrap(int id, boolean inverse)
	{
		super(id);
		this.inverse = inverse;
		iconProvider = new MappedIconProvider(new ItemStack[] {
			Ic2Items.smallIronDust, Ic2Items.scrap
		});
	}

	public int getIconIndex()
	{
		return inverse ? 0 : 1;
	}

	public IIconProvider getIconProvider()
	{
		return iconProvider;
	}

	public String getDescription()
	{
		return inverse ? "No Amplifier" : "Has Amplifier";
	}

	public boolean isTriggerActive(ForgeDirection side, TileEntity tile, ITriggerParameter parameter)
	{
		if (tile instanceof TileEntityMatter)
		{
			TileEntityMatter tem = (TileEntityMatter)tile;
			if (tem.amplificationIsAvailable() != inverse)
				return true;
		}
		return false;
	}
}
