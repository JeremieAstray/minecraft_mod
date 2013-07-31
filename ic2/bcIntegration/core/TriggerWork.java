// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TriggerWork.java

package ic2.bcIntegration.core;

import buildcraft.api.gates.ITriggerParameter;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

// Referenced classes of package ic2.bcIntegration.core:
//			Trigger

public class TriggerWork extends Trigger
{

	private final boolean working;

	public TriggerWork(int id, boolean working)
	{
		super(id);
		this.working = working;
	}

	public int getIconIndex()
	{
		return working ? 10 : 11;
	}

	public String getDescription()
	{
		return working ? "Machine On" : "Machine Off";
	}

	public boolean isTriggerActive(ForgeDirection side, TileEntity tile, ITriggerParameter parameter)
	{
		if (tile instanceof TileEntityStandardMachine)
		{
			TileEntityStandardMachine teb = (TileEntityStandardMachine)tile;
			if (teb.getActive() == working)
				return true;
		}
		return false;
	}
}
