// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockTexGlass.java

package ic2.core.block;

import ic2.core.init.InternalName;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.block:
//			BlockSimple

public class BlockTexGlass extends BlockSimple
{

	public BlockTexGlass(Configuration config, InternalName internalName)
	{
		super(config, internalName, Material.glass);
		setHardness(5F);
		setResistance(150F);
		setStepSound(Block.soundGlassFootstep);
	}

	public int quantityDropped(Random random)
	{
		return 0;
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		if (iBlockAccess.getBlockId(x, y, z) == super.blockID)
			return false;
		else
			return super.shouldSideBeRendered(iBlockAccess, x, y, z, side);
	}
}
