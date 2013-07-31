// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockDynamite.java

package ic2.core.block;

import ic2.core.*;
import ic2.core.init.InternalName;
import ic2.core.item.block.ItemBlockRare;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.block:
//			BlockSimple, EntityDynamite

public class BlockDynamite extends BlockSimple
{

	public BlockDynamite(Configuration config, InternalName internalName)
	{
		super(config, internalName, Material.tnt, ic2/core/item/block/ItemBlockRare);
		setTickRandomly(true);
		setHardness(0.0F);
		setStepSound(Block.soundGrassFootstep);
		setCreativeTab(null);
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

	public int getRenderType()
	{
		return 2;
	}

	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		if (world.isBlockNormalCube(i - 1, j, k))
			return true;
		if (world.isBlockNormalCube(i + 1, j, k))
			return true;
		if (world.isBlockNormalCube(i, j, k - 1))
			return true;
		if (world.isBlockNormalCube(i, j, k + 1))
			return true;
		else
			return world.isBlockNormalCube(i, j - 1, k);
	}

	public void onPostBlockPlaced(World world, int i, int j, int k, int l)
	{
		int i1 = world.getBlockMetadata(i, j, k);
		if (l == 1 && world.isBlockNormalCube(i, j - 1, k))
			i1 = 5;
		if (l == 2 && world.isBlockNormalCube(i, j, k + 1))
			i1 = 4;
		if (l == 3 && world.isBlockNormalCube(i, j, k - 1))
			i1 = 3;
		if (l == 4 && world.isBlockNormalCube(i + 1, j, k))
			i1 = 2;
		if (l == 5 && world.isBlockNormalCube(i - 1, j, k))
			i1 = 1;
		world.setBlockMetadataWithNotify(i, j, k, i1, 3);
	}

	public void updateTick(World world, int i, int j, int k, Random random)
	{
		super.updateTick(world, i, j, k, random);
		if (world.getBlockMetadata(i, j, k) == 0)
			onBlockAdded(world, i, j, k);
	}

	public void onBlockAdded(World world, int i, int j, int k)
	{
		if (world.isBlockIndirectlyGettingPowered(i, j, k))
		{
			removeBlockByPlayer(world, null, i, j, k);
			return;
		}
		if (world.isBlockNormalCube(i, j - 1, k))
			world.setBlockMetadataWithNotify(i, j, k, 5, 3);
		else
		if (world.isBlockNormalCube(i - 1, j, k))
			world.setBlockMetadataWithNotify(i, j, k, 1, 3);
		else
		if (world.isBlockNormalCube(i + 1, j, k))
			world.setBlockMetadataWithNotify(i, j, k, 2, 3);
		else
		if (world.isBlockNormalCube(i, j, k - 1))
			world.setBlockMetadataWithNotify(i, j, k, 3, 3);
		else
		if (world.isBlockNormalCube(i, j, k + 1))
			world.setBlockMetadataWithNotify(i, j, k, 4, 3);
		dropBlockIfCantStay(world, i, j, k);
	}

	public int quantityDropped(Random random)
	{
		return 0;
	}

	public int idDropped(int i, Random random, int j)
	{
		return Ic2Items.dynamite.itemID;
	}

	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion)
	{
		EntityDynamite entitytntprimed = new EntityDynamite(world, (float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
		entitytntprimed.owner = explosion != null ? explosion.func_94613_c() : null;
		entitytntprimed.fuse = 5;
		world.spawnEntityInWorld(entitytntprimed);
	}

	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		if (!IC2.platform.isSimulating())
		{
			return false;
		} else
		{
			world.setBlock(x, y, z, 0, 0, 7);
			EntityDynamite entitytntprimed = new EntityDynamite(world, (float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
			entitytntprimed.owner = player;
			entitytntprimed.fuse = 40;
			world.spawnEntityInWorld(entitytntprimed);
			world.playSoundAtEntity(entitytntprimed, "random.fuse", 1.0F, 1.0F);
			return true;
		}
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		if (l > 0 && Block.blocksList[l].canProvidePower() && world.isBlockIndirectlyGettingPowered(i, j, k))
		{
			removeBlockByPlayer(world, null, i, j, k);
			return;
		}
		if (dropBlockIfCantStay(world, i, j, k))
		{
			int i1 = world.getBlockMetadata(i, j, k);
			boolean flag = false;
			if (!world.isBlockNormalCube(i - 1, j, k) && i1 == 1)
				flag = true;
			if (!world.isBlockNormalCube(i + 1, j, k) && i1 == 2)
				flag = true;
			if (!world.isBlockNormalCube(i, j, k - 1) && i1 == 3)
				flag = true;
			if (!world.isBlockNormalCube(i, j, k + 1) && i1 == 4)
				flag = true;
			if (!world.isBlockNormalCube(i, j - 1, k) && i1 == 5)
				flag = true;
			if (flag)
			{
				dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
				world.setBlock(i, j, k, 0, 0, 7);
			}
		}
	}

	public boolean dropBlockIfCantStay(World world, int i, int j, int k)
	{
		if (!canPlaceBlockAt(world, i, j, k))
		{
			onBlockDestroyedByExplosion(world, i, j, k, null);
			world.setBlock(i, j, k, 0, 0, 7);
			return false;
		} else
		{
			return true;
		}
	}

	public MovingObjectPosition collisionRayTrace(World world, int i, int j, int k, Vec3 Vec3, Vec3 Vec31)
	{
		int l = world.getBlockMetadata(i, j, k) & 7;
		float f = 0.15F;
		if (l == 1)
			setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
		else
		if (l == 2)
			setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
		else
		if (l == 3)
			setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
		else
		if (l == 4)
		{
			setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
		} else
		{
			float f1 = 0.1F;
			setBlockBounds(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, 0.6F, 0.5F + f1);
		}
		return super.collisionRayTrace(world, i, j, k, Vec3, Vec31);
	}
}
