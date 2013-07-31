// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockFoam.java

package ic2.core.block;

import ic2.core.*;
import ic2.core.init.InternalName;
import ic2.core.item.block.ItemBlockRare;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.block:
//			BlockSimple

public class BlockFoam extends BlockSimple
{

	public BlockFoam(Configuration config, InternalName internalName)
	{
		super(config, internalName, Material.cloth, ic2/core/item/block/ItemBlockRare);
		setTickRandomly(true);
		setHardness(0.01F);
		setResistance(10F);
		setStepSound(Block.soundClothFootstep);
		Ic2Items.constructionFoam = new ItemStack(this);
	}

	public String getTextureFolder()
	{
		return "cf";
	}

	public int tickRate(World world)
	{
		return 500;
	}

	public int quantityDropped(Random r)
	{
		return 0;
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public boolean isBlockNormalCube(World world, int i, int j, int l)
	{
		return true;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int l)
	{
		return null;
	}

	public boolean isBlockSolid(IBlockAccess world, int x, int y, int i, int j)
	{
		return false;
	}

	public void updateTick(World world, int i, int j, int k, Random random)
	{
		if (!IC2.platform.isSimulating())
			return;
		if (world.getBlockLightValue(i, j, k) * 6 >= world.rand.nextInt(1000))
			world.setBlock(i, j, k, Ic2Items.constructionFoamWall.itemID, 7, 7);
		else
			world.scheduleBlockUpdate(i, j, k, super.blockID, tickRate(world));
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float xOffset, 
			float yOffset, float zOffset)
	{
		ItemStack itemStack = entityPlayer.getCurrentEquippedItem();
		if (itemStack != null && itemStack.itemID == Block.sand.blockID)
		{
			world.setBlock(x, y, z, Ic2Items.constructionFoamWall.itemID, 7, 7);
			if (!entityPlayer.capabilities.isCreativeMode)
			{
				itemStack.stackSize--;
				if (itemStack.stackSize <= 0)
					entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = null;
			}
			return true;
		} else
		{
			return false;
		}
	}

	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		if (world.isAirBlock(x, y, z))
			return true;
		int blockId = world.getBlockId(x, y, z);
		return blockId == ((Block) (Block.fire)).blockID || world.getBlockMaterial(x, y, z).isLiquid();
	}

	public ItemStack createStackedBlock(int i)
	{
		return null;
	}
}
