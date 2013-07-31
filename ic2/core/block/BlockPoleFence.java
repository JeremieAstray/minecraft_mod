// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockPoleFence.java

package ic2.core.block;

import ic2.api.Direction;
import ic2.api.item.ItemWrapper;
import ic2.core.*;
import ic2.core.init.InternalName;
import ic2.core.item.block.ItemBlockRare;
import ic2.core.util.AabbUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.block:
//			BlockSimple

public class BlockPoleFence extends BlockSimple
{

	private static final double halfThickness = 0.125D;
	private static final double halfBarThickness = 0.0625D;
	private static final double heightBarBottom = 0.375D;
	private static final double heightBar = 0.1875D;
	private static final double heightBarDistance = 0.375D;
	private static final Direction directions[];

	public BlockPoleFence(Configuration config, InternalName internalName)
	{
		super(config, internalName, Material.iron, ic2/core/item/block/ItemBlockRare);
		setHardness(1.5F);
		setResistance(5F);
		setStepSound(Block.soundMetalFootstep);
		Ic2Items.ironFence = new ItemStack(this);
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public boolean isBlockNormalCube(World world, int i, int j, int l)
	{
		return false;
	}

	public int getRenderType()
	{
		return IC2.platform.getRenderId("fence");
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z, int meta)
	{
		return getCommonBoundingBoxFromPool(world, x, y, z, false);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return getCommonBoundingBoxFromPool(world, x, y, z, false);
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return getCommonBoundingBoxFromPool(world, x, y, z, true);
	}

	private AxisAlignedBB getCommonBoundingBoxFromPool(World world, int x, int y, int z, boolean selectionBoundingBox)
	{
		double minX = ((double)x + 0.5D) - 0.125D;
		double minY = y;
		double minZ = ((double)z + 0.5D) - 0.125D;
		double maxX = (double)x + 0.5D + 0.125D;
		double maxY = selectionBoundingBox ? y + 1 : (double)y + 1.5D;
		double maxZ = (double)z + 0.5D + 0.125D;
		if (world.getBlockId(x - 1, y, z) == super.blockID)
			minX = x;
		if (world.getBlockId(x + 1, y, z) == super.blockID)
			maxX = x + 1;
		if (world.getBlockId(x, y, z - 1) == super.blockID)
			minZ = z;
		if (world.getBlockId(x, y, z + 1) == super.blockID)
			maxZ = z + 1;
		return AxisAlignedBB.getAABBPool().getAABB(minX, minY, minZ, maxX, maxY, maxZ);
	}

	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 origin, Vec3 absDirection)
	{
		Vec3 direction = Vec3.createVectorHelper(absDirection.xCoord - origin.xCoord, absDirection.yCoord - origin.yCoord, absDirection.zCoord - origin.zCoord);
		double maxLength = direction.lengthVector();
		boolean hit = false;
		Vec3 intersection = Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
		Direction intersectingDirection = AabbUtil.getIntersection(origin, direction, AxisAlignedBB.getAABBPool().getAABB(((double)x + 0.5D) - 0.125D, y, ((double)z + 0.5D) - 0.125D, (double)x + 0.5D + 0.125D, y + 1, (double)z + 0.5D + 0.125D), intersection);
		if (intersectingDirection != null && intersection.distanceTo(origin) <= maxLength)
		{
			hit = true;
		} else
		{
			Direction arr$[] = directions;
			int len$ = arr$.length;
			int i$ = 0;
label0:
			do
			{
label1:
				{
					if (i$ >= len$)
						break label0;
					Direction dir = arr$[i$];
					static class 1
					{

						static final int $SwitchMap$ic2$api$Direction[];

						static 
						{
							$SwitchMap$ic2$api$Direction = new int[Direction.values().length];
							try
							{
								$SwitchMap$ic2$api$Direction[Direction.XN.ordinal()] = 1;
							}
							catch (NoSuchFieldError ex) { }
							try
							{
								$SwitchMap$ic2$api$Direction[Direction.XP.ordinal()] = 2;
							}
							catch (NoSuchFieldError ex) { }
							try
							{
								$SwitchMap$ic2$api$Direction[Direction.ZN.ordinal()] = 3;
							}
							catch (NoSuchFieldError ex) { }
							try
							{
								$SwitchMap$ic2$api$Direction[Direction.ZP.ordinal()] = 4;
							}
							catch (NoSuchFieldError ex) { }
						}
					}

					switch (1..SwitchMap.ic2.api.Direction[dir.ordinal()])
					{
					default:
						break label1;

					case 1: // '\001'
						if (world.getBlockId(x - 1, y, z) == super.blockID)
							break;
						break label1;

					case 2: // '\002'
						if (world.getBlockId(x + 1, y, z) == super.blockID)
							break;
						break label1;

					case 3: // '\003'
						if (world.getBlockId(x, y, z - 1) == super.blockID)
							break;
						break label1;

					case 4: // '\004'
						if (world.getBlockId(x, y, z + 1) != super.blockID)
							break label1;
						break;
					}
					int pieceToCheck = 0;
label2:
					do
					{
label3:
						{
							if (pieceToCheck >= 2)
								break label2;
							AxisAlignedBB bbox = null;
							double yMin = (double)y + 0.375D + (double)pieceToCheck * 0.375D;
							double yMax = yMin + 0.1875D;
							switch (1..SwitchMap.ic2.api.Direction[dir.ordinal()])
							{
							default:
								break label3;

							case 1: // '\001'
								bbox = AxisAlignedBB.getAABBPool().getAABB(x, yMin, ((double)z + 0.5D) - 0.0625D, (double)x + 0.5D, yMax, (double)z + 0.5D + 0.0625D);
								break;

							case 2: // '\002'
								bbox = AxisAlignedBB.getAABBPool().getAABB((double)x + 0.5D, yMin, ((double)z + 0.5D) - 0.0625D, (double)x + 1.0D, yMax, (double)z + 0.5D + 0.0625D);
								break;

							case 3: // '\003'
								bbox = AxisAlignedBB.getAABBPool().getAABB(((double)x + 0.5D) - 0.0625D, yMin, z, (double)x + 0.5D + 0.0625D, yMax, (double)z + 0.5D);
								break;

							case 4: // '\004'
								bbox = AxisAlignedBB.getAABBPool().getAABB(((double)x + 0.5D) - 0.0625D, yMin, (double)z + 0.5D, (double)x + 0.5D + 0.0625D, yMax, (double)z + 1.0D);
								break;
							}
							intersectingDirection = AabbUtil.getIntersection(origin, direction, bbox, intersection);
							if (intersectingDirection != null && intersection.distanceTo(origin) <= maxLength)
							{
								hit = true;
								break label2;
							}
						}
						pieceToCheck++;
					} while (true);
					if (hit)
						break label0;
				}
				i$++;
			} while (true);
		}
		if (hit)
			return new MovingObjectPosition(x, y, z, intersectingDirection.toSideValue(), intersection);
		else
			return null;
	}

	public boolean isPole(World world, int i, int j, int k)
	{
		return world.getBlockId(i - 1, j, k) != super.blockID && world.getBlockId(i + 1, j, k) != super.blockID && world.getBlockId(i, j, k - 1) != super.blockID && world.getBlockId(i, j, k + 1) != super.blockID;
	}

	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
	{
		if (super.blockMaterial != Material.iron || !isPole(world, i, j, k) || !(entity instanceof EntityPlayer))
			return;
		boolean powered = world.getBlockMetadata(i, j, k) > 0;
		boolean metalShoes = false;
		EntityPlayer player = (EntityPlayer)entity;
		ItemStack shoes = player.inventory.armorInventory[0];
		if (shoes != null)
		{
			int id = shoes.itemID;
			if (id == ((Item) (Item.bootsIron)).itemID || id == ((Item) (Item.bootsGold)).itemID || id == ((Item) (Item.bootsChain)).itemID || ItemWrapper.isMetalArmor(shoes, player))
				metalShoes = true;
		}
		if (!powered || !metalShoes)
		{
			if (player.isSneaking())
				if (((Entity) (player)).motionY < -0.25D)
					player.motionY *= 0.89999997615814209D;
				else
					player.fallDistance = 0.0F;
		} else
		{
			world.setBlockMetadataWithNotify(i, j, k, world.getBlockMetadata(i, j, k) - 1, 7);
			player.motionY += 0.075000002980232239D;
			if (((Entity) (player)).motionY > 0.0D)
			{
				player.motionY *= 1.0299999713897705D;
				player.fallDistance = 0.0F;
			}
			if (player.isSneaking())
			{
				if (((Entity) (player)).motionY > 0.30000001192092896D)
					player.motionY = 0.30000001192092896D;
			} else
			if (((Entity) (player)).motionY > 1.5D)
				player.motionY = 1.5D;
		}
	}

	static 
	{
		directions = (new Direction[] {
			Direction.XN, Direction.XP, Direction.ZN, Direction.ZP
		});
	}
}
