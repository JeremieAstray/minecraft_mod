// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EntityBoatRubber.java

package ic2.core.item;

import ic2.core.Ic2Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

// Referenced classes of package ic2.core.item:
//			EntityIC2Boat

public class EntityBoatRubber extends EntityIC2Boat
{

	public EntityBoatRubber(World par1World)
	{
		super(par1World);
	}

	protected ItemStack getItem()
	{
		return Ic2Items.boatRubber.copy();
	}

	protected double getBreakMotion()
	{
		return 0.23000000000000001D;
	}

	protected void breakBoat(double motion)
	{
		playSound("random.pop", 16F, 8F);
		entityDropItem((motion <= 0.26000000000000001D ? Ic2Items.boatRubber : Ic2Items.boatRubberBroken).copy(), 0.0F);
	}

	public String getTexture()
	{
		return "textures/models/boatRubber.png";
	}
}
