// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EntityBoatCarbon.java

package ic2.core.item;

import ic2.core.Ic2Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

// Referenced classes of package ic2.core.item:
//			EntityIC2Boat

public class EntityBoatCarbon extends EntityIC2Boat
{

	public EntityBoatCarbon(World par1World)
	{
		super(par1World);
	}

	protected ItemStack getItem()
	{
		return Ic2Items.boatCarbon.copy();
	}

	protected double getBreakMotion()
	{
		return 0.40000000000000002D;
	}

	protected void breakBoat(double motion)
	{
		entityDropItem(getItem(), 0.0F);
	}

	public String getTexture()
	{
		return "textures/models/boatCarbon.png";
	}
}
