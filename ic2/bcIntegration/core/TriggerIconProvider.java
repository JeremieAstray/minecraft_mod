// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TriggerIconProvider.java

package ic2.bcIntegration.core;

import buildcraft.api.core.IIconProvider;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class TriggerIconProvider
	implements IIconProvider
{

	private Icon icons[];
	private boolean registered;

	public TriggerIconProvider()
	{
		registered = false;
	}

	public Icon getIcon(int iconIndex)
	{
		return icons[iconIndex];
	}

	public void registerIcons(IconRegister iconRegister)
	{
		if (registered)
		{
			return;
		} else
		{
			registered = true;
			icons = new Icon[12];
			icons[0] = iconRegister.registerIcon("ic2:bcTrigger.capacitorEmpty");
			icons[1] = iconRegister.registerIcon("ic2:bcTrigger.capacitorPartial");
			icons[2] = iconRegister.registerIcon("ic2:bcTrigger.capacitorRoom");
			icons[3] = iconRegister.registerIcon("ic2:bcTrigger.capacitorFull");
			icons[4] = iconRegister.registerIcon("ic2:bcTrigger.chargingEmpty");
			icons[5] = iconRegister.registerIcon("ic2:bcTrigger.chargingPartial");
			icons[6] = iconRegister.registerIcon("ic2:bcTrigger.chargingFull");
			icons[7] = iconRegister.registerIcon("ic2:bcTrigger.dischargingEmpty");
			icons[8] = iconRegister.registerIcon("ic2:bcTrigger.dischargingPartial");
			icons[9] = iconRegister.registerIcon("ic2:bcTrigger.dischargingFull");
			icons[10] = iconRegister.registerIcon("buildcraft:triggers/trigger_machine_active");
			icons[11] = iconRegister.registerIcon("buildcraft:triggers/trigger_machine_inactive");
			return;
		}
	}
}
