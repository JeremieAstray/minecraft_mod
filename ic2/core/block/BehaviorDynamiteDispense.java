// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BehaviorDynamiteDispense.java

package ic2.core.block;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

// Referenced classes of package ic2.core.block:
//			EntityDynamite

public class BehaviorDynamiteDispense extends BehaviorProjectileDispense
{

	private boolean sticky;

	public BehaviorDynamiteDispense(boolean sticky)
	{
		this.sticky = false;
		this.sticky = sticky;
	}

	protected IProjectile getProjectileEntity(World var1, IPosition var2)
	{
		return new EntityDynamite(var1, var2.getX(), var2.getY(), var2.getZ(), sticky);
	}
}
