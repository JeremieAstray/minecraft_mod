// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EntityItnt.java

package ic2.core.block;

import ic2.core.Ic2Items;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

// Referenced classes of package ic2.core.block:
//			EntityIC2Explosive

public class EntityItnt extends EntityIC2Explosive
{

	public EntityItnt(World world, double x, double y, double z)
	{
		super(world, x, y, z, 60, 5.5F, 0.9F, 0.3F, Block.blocksList[Ic2Items.industrialTnt.itemID]);
	}

	public EntityItnt(World world)
	{
		this(world, 0.0D, 0.0D, 0.0D);
	}
}
