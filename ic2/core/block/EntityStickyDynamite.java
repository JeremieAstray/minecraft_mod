// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EntityStickyDynamite.java

package ic2.core.block;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

// Referenced classes of package ic2.core.block:
//			EntityDynamite

public class EntityStickyDynamite extends EntityDynamite
{

	public EntityStickyDynamite(World world)
	{
		super(world);
		sticky = true;
	}

	public EntityStickyDynamite(World world, EntityLivingBase entityliving)
	{
		super(world, entityliving);
		sticky = true;
	}
}
