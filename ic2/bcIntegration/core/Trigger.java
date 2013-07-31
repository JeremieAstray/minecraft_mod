// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Trigger.java

package ic2.bcIntegration.core;

import buildcraft.api.core.IIconProvider;
import buildcraft.api.gates.*;
import ic2.bcIntegration.SubModule;
import ic2.core.IC2;
import java.util.logging.Logger;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public abstract class Trigger
	implements ITrigger
{

	private final int id;

	public Trigger(int id)
	{
		this.id = id;
		if (ActionManager.triggers[id] == null)
			ActionManager.triggers[id] = this;
		else
			IC2.log.warning((new StringBuilder()).append("can't add trigger ").append(this).append(", id ").append(id).append(" already occupied by someone else.").toString());
	}

	public int getId()
	{
		return id;
	}

	public abstract int getIconIndex();

	public IIconProvider getIconProvider()
	{
		return SubModule.iconProvider;
	}

	public boolean hasParameter()
	{
		return false;
	}

	public abstract String getDescription();

	public abstract boolean isTriggerActive(ForgeDirection forgedirection, TileEntity tileentity, ITriggerParameter itriggerparameter);

	public ITriggerParameter createParameter()
	{
		return null;
	}
}
