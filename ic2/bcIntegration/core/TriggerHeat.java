// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TriggerHeat.java

package ic2.bcIntegration.core;

import buildcraft.api.gates.ITriggerParameter;
import ic2.core.block.machine.tileentity.TileEntityInduction;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

// Referenced classes of package ic2.bcIntegration.core:
//			Trigger

public class TriggerHeat extends Trigger
{

	private final boolean heated;

	public TriggerHeat(int id, boolean heated)
	{
		super(id);
		this.heated = heated;
	}

	public int getIconIndex()
	{
		return heated ? 3 : 0;
	}

	public String getDescription()
	{
		return heated ? "Fully Heated Up" : "Not Fully Heated Up";
	}

	public boolean isTriggerActive(ForgeDirection side, TileEntity tile, ITriggerParameter parameter)
	{
		if (tile instanceof TileEntityInduction)
		{
			TileEntityInduction tei = (TileEntityInduction)tile;
			if ((tei.heat >= TileEntityInduction.maxHeat) == heated)
				return true;
		}
		return false;
	}
}
