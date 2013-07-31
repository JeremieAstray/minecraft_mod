// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TriggerEnergyFlow.java

package ic2.bcIntegration.core;

import buildcraft.api.gates.ITriggerParameter;
import ic2.core.block.wiring.TileEntityCableDetector;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

// Referenced classes of package ic2.bcIntegration.core:
//			Trigger

public class TriggerEnergyFlow extends Trigger
{

	private final boolean flowing;

	public TriggerEnergyFlow(int id, boolean flowing)
	{
		super(id);
		this.flowing = flowing;
	}

	public int getIconIndex()
	{
		return flowing ? 3 : 0;
	}

	public String getDescription()
	{
		return flowing ? "Energy flowing" : "Energy not flowing";
	}

	public boolean isTriggerActive(ForgeDirection side, TileEntity tile, ITriggerParameter parameter)
	{
		if (tile instanceof TileEntityCableDetector)
		{
			TileEntityCableDetector teb = (TileEntityCableDetector)tile;
			if (teb.getActive() == flowing)
				return true;
		}
		return false;
	}
}
