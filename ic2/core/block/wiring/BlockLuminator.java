// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockLuminator.java

package ic2.core.block.wiring;

import ic2.api.Direction;
import ic2.api.item.*;
import ic2.core.*;
import ic2.core.block.BlockMultiID;
import ic2.core.block.BlockPoleFence;
import ic2.core.init.InternalName;
import ic2.core.item.block.ItemLuminator;
import ic2.core.util.AabbUtil;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.block.wiring:
//			BlockCable, TileEntityLuminator

public class BlockLuminator extends BlockMultiID
{

	boolean light;

	public BlockLuminator(Configuration config, InternalName internalName)
	{
		super(config, internalName, Material.glass, ic2/core/item/block/ItemLuminator);
		setStepSound(Block.soundGlassFootstep);
		setHardness(0.3F);
		setResistance(0.5F);
		if (internalName == InternalName.blockLuminator)
		{
			light = true;
			setLightValue(1.0F);
			setCreativeTab(null);
		} else
		{
			light = false;
		}
	}

	public String getTextureFolder()
	{
		return "wiring";
	}

	public int getTextureIndex(int meta)
	{
		return 0;
	}

	public int quantityDropped(Random random)
	{
		return 0;
	}

	public boolean canPlaceBlockOnSide(World world, int i, int j, int k, int direction)
	{
		if (world.getBlockId(i, j, k) != 0)
			return false;
		switch (direction)
		{
		case 0: // '\0'
			j++;
			break;

		case 1: // '\001'
			j--;
			break;

		case 2: // '\002'
			k++;
			break;

		case 3: // '\003'
			k--;
			break;

		case 4: // '\004'
			i++;
			break;

		case 5: // '\005'
			i--;
			break;
		}
		return isSupportingBlock(world, i, j, k);
	}

	public static boolean isSupportingBlock(World world, int i, int j, int k)
	{
		if (world.getBlockId(i, j, k) == 0)
			return false;
		if (world.isBlockOpaqueCube(i, j, k))
			return true;
		else
			return isSpecialSupporter(world, i, j, k);
	}

	public static boolean isSpecialSupporter(IBlockAccess world, int i, int j, int k)
	{
		Block block = Block.blocksList[world.getBlockId(i, j, k)];
		if (block == null)
			return false;
		if ((block instanceof BlockFence) || (block instanceof BlockPoleFence) || (block instanceof BlockCable))
			return true;
		return block.blockID == Ic2Items.reinforcedGlass.itemID || block == Block.glass;
	}

	public boolean canBlockStay(World world, int i, int j, int k)
	{
		TileEntity te = world.getBlockTileEntity(i, j, k);
		if (te != null && ((TileEntityLuminator)te).ignoreBlockStay)
			return true;
		int facing = world.getBlockMetadata(i, j, k);
		switch (facing)
		{
		case 0: // '\0'
			j++;
			break;

		case 1: // '\001'
			j--;
			break;

		case 2: // '\002'
			k++;
			break;

		case 3: // '\003'
			k--;
			break;

		case 4: // '\004'
			i++;
			break;

		case 5: // '\005'
			i--;
			break;
		}
		return isSupportingBlock(world, i, j, k);
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		if (!canBlockStay(world, i, j, k))
			world.setBlock(i, j, k, 0, 0, 7);
		super.onNeighborBlockChange(world, i, j, k, l);
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public int getRenderType()
	{
		return IC2.platform.getRenderId("luminator");
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z, int meta)
	{
		return getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		float box[] = getBoxOfLuminator(world, x, y, z);
		return AxisAlignedBB.getAABBPool().getAABB(box[0] + (float)x, box[1] + (float)y, box[2] + (float)z, box[3] + (float)x, box[4] + (float)y, box[5] + (float)z);
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 origin, Vec3 absDirection)
	{
		Vec3 direction = Vec3.createVectorHelper(absDirection.xCoord - origin.xCoord, absDirection.yCoord - origin.yCoord, absDirection.zCoord - origin.zCoord);
		double maxLength = direction.lengthVector();
		Vec3 intersection = Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
		Direction intersectingDirection = AabbUtil.getIntersection(origin, direction, getCollisionBoundingBoxFromPool(world, x, y, z), intersection);
		if (intersectingDirection != null && intersection.distanceTo(origin) <= maxLength)
			return new MovingObjectPosition(x, y, z, intersectingDirection.toSideValue(), intersection);
		else
			return null;
	}

	public static float[] getBoxOfLuminator(IBlockAccess world, int x, int y, int z)
	{
		int facing = world.getBlockMetadata(x, y, z);
		float px = 0.0625F;
		switch (facing)
		{
		case 0: // '\0'
			y++;
			break;

		case 1: // '\001'
			y--;
			break;

		case 2: // '\002'
			z++;
			break;

		case 3: // '\003'
			z--;
			break;

		case 4: // '\004'
			x++;
			break;

		case 5: // '\005'
			x--;
			break;
		}
		boolean fullCover = isSpecialSupporter(world, x, y, z);
		switch (facing)
		{
		case 1: // '\001'
			return (new float[] {
				0.0F, 0.0F, 0.0F, 1.0F, 1.0F * px, 1.0F
			});

		case 2: // '\002'
			if (fullCover)
				return (new float[] {
					0.0F, 0.0F, 15F * px, 1.0F, 1.0F, 1.0F
				});
			else
				return (new float[] {
					6F * px, 3F * px, 14F * px, 1.0F - 6F * px, 1.0F - 3F * px, 1.0F
				});

		case 3: // '\003'
			if (fullCover)
				return (new float[] {
					0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F * px
				});
			else
				return (new float[] {
					6F * px, 3F * px, 0.0F, 1.0F - 6F * px, 1.0F - 3F * px, 2.0F * px
				});

		case 4: // '\004'
			if (fullCover)
				return (new float[] {
					15F * px, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F
				});
			else
				return (new float[] {
					14F * px, 3F * px, 6F * px, 1.0F, 1.0F - 3F * px, 1.0F - 6F * px
				});

		case 5: // '\005'
			if (fullCover)
				return (new float[] {
					0.0F, 0.0F, 0.0F, 1.0F * px, 1.0F, 1.0F
				});
			else
				return (new float[] {
					0.0F, 3F * px, 6F * px, 2.0F * px, 1.0F - 3F * px, 1.0F - 6F * px
				});
		}
		if (fullCover)
			return (new float[] {
				0.0F, 15F * px, 0.0F, 1.0F, 1.0F, 1.0F
			});
		else
			return (new float[] {
				4F * px, 13F * px, 4F * px, 1.0F - 4F * px, 1.0F, 1.0F - 4F * px
			});
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public boolean isBlockNormalCube(World world, int i, int j, int l)
	{
		return false;
	}

	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float a, 
			float b, float c)
	{
		ItemStack itemStack = entityplayer.getCurrentEquippedItem();
		if (itemStack == null || !(itemStack.getItem() instanceof IElectricItem))
			return false;
		itemStack.getItem();
		TileEntityLuminator lumi = (TileEntityLuminator)world.getBlockTileEntity(i, j, k);
		int transfer = lumi.getMaxEnergy() - lumi.energy;
		if (transfer <= 0)
			return false;
		transfer = ElectricItem.manager.discharge(itemStack, transfer, 2, true, false);
		if (!light)
		{
			world.setBlock(i, j, k, Ic2Items.activeLuminator.itemID, world.getBlockMetadata(i, j, k), 7);
			lumi = (TileEntityLuminator)world.getBlockTileEntity(i, j, k);
		}
		lumi.energy += transfer;
		return true;
	}

	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
	{
		if (light && (entity instanceof EntityMob))
			entity.setFire(!(entity instanceof EntityLivingBase) || ((EntityLivingBase)entity).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD ? 10 : 20);
	}

	public TileEntity createTileEntity(World world, int meta)
	{
		return new TileEntityLuminator();
	}

	public void getSubBlocks(int i, CreativeTabs tabs, List itemList)
	{
		if (!light)
			super.getSubBlocks(i, tabs, itemList);
	}

	public boolean hasComparatorInputOverride()
	{
		return true;
	}

	public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
	{
		TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
		if (te != null && te.getClass() == ic2/core/block/wiring/TileEntityLuminator)
		{
			TileEntityLuminator tel = (TileEntityLuminator)te;
			return (int)Math.floor(((float)tel.energy / (float)tel.getMaxEnergy()) * 15F);
		} else
		{
			return 0;
		}
	}
}
