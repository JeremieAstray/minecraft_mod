// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockIC2Explosive.java

package ic2.core.block;

import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.init.InternalName;
import ic2.core.item.block.ItemBlockRare;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.block:
//			BlockSimple, EntityIC2Explosive

public abstract class BlockIC2Explosive extends BlockSimple
{

	public boolean canExplodeByHand;

	public BlockIC2Explosive(Configuration config, InternalName internalName, boolean manual)
	{
		super(config, internalName, Material.tnt, ic2/core/item/block/ItemBlockRare);
		canExplodeByHand = false;
		canExplodeByHand = manual;
	}

	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
		if (world.isBlockIndirectlyGettingPowered(x, y, z))
			removeBlockByPlayer(world, null, x, y, z);
	}

	public void onNeighborBlockChange(World world, int x, int y, int z, int srcBlockId)
	{
		if (world.isBlockIndirectlyGettingPowered(x, y, z))
			removeBlockByPlayer(world, null, x, y, z);
	}

	public int quantityDropped(Random random)
	{
		return 0;
	}

	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion)
	{
		EntityIC2Explosive entitytntprimed = getExplosionEntity(world, (float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F, explosion != null ? explosion.func_94613_c() : null);
		entitytntprimed.fuse = world.rand.nextInt(entitytntprimed.fuse / 4) + entitytntprimed.fuse / 8;
		world.spawnEntityInWorld(entitytntprimed);
	}

	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		if (!IC2.platform.isSimulating())
			return false;
		int l = world.getBlockMetadata(x, y, z);
		world.setBlockToAir(x, y, z);
		if (player != null && (l & 1) == 0 && !canExplodeByHand)
		{
			dropBlockAsItem_do(world, x, y, z, new ItemStack(super.blockID, 1, 0));
		} else
		{
			onIgnite(world, player, x, y, z);
			EntityIC2Explosive entitytntprimed = getExplosionEntity(world, (float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F, player != null ? ((EntityLivingBase) (player)) : null);
			world.spawnEntityInWorld(entitytntprimed);
			world.playSoundAtEntity(entitytntprimed, "random.fuse", 1.0F, 1.0F);
		}
		return false;
	}

	public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer player, int side, float xOffset, 
			float yOffset, float zOffset)
	{
		if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().itemID == Item.flintAndSteel.itemID)
		{
			par1World.setBlockMetadataWithNotify(x, y, z, 1, 7);
			removeBlockByPlayer(par1World, player, x, y, z);
			return true;
		} else
		{
			return super.onBlockActivated(par1World, x, y, z, player, side, xOffset, yOffset, zOffset);
		}
	}

	public abstract EntityIC2Explosive getExplosionEntity(World world, float f, float f1, float f2, EntityLivingBase entitylivingbase);

	public void onIgnite(World world1, EntityPlayer entityplayer, int i, int j, int k)
	{
	}
}
