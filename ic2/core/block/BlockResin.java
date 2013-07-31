// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockResin.java

package ic2.core.block;

import ic2.core.Ic2Items;
import ic2.core.init.InternalName;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.block:
//			BlockSimple

public class BlockResin extends BlockSimple
{

	public BlockResin(Configuration config, InternalName internalName)
	{
		super(config, internalName, Material.circuits);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		setHardness(1.6F);
		setResistance(0.5F);
		setStepSound(Block.soundSandFootstep);
		setCreativeTab(null);
		Ic2Items.resinSheet = new ItemStack(this);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int l)
	{
		return null;
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public int idDropped(int i, Random random, int j)
	{
		return Ic2Items.resin.itemID;
	}

	public int quantityDropped(Random random)
	{
		return random.nextInt(5) != 0 ? 1 : 0;
	}

	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		int l = world.getBlockId(i, j - 1, k);
		if (l == 0 || !Block.blocksList[l].isOpaqueCube())
			return false;
		else
			return world.getBlockMaterial(i, j - 1, k).isSolid();
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		if (!canPlaceBlockAt(world, i, j, k))
		{
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlock(i, j, k, 0, 0, 7);
		}
	}

	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
	{
		entity.fallDistance *= 0.75F;
		entity.motionX *= 0.60000002384185791D;
		entity.motionY *= 0.85000002384185791D;
		entity.motionZ *= 0.60000002384185791D;
	}
}
