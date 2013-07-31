// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockScaffold.java

package ic2.core.block;

import ic2.core.IC2;
import ic2.core.Ic2Items;
import ic2.core.init.InternalName;
import ic2.core.item.block.ItemBlockRare;
import ic2.core.util.Keyboard;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.ForgeDirection;

// Referenced classes of package ic2.core.block:
//			BlockSimple

public class BlockScaffold extends BlockSimple
{

	public static final int standardStrength = 2;
	public static final int standardIronStrength = 5;
	public static final int reinforcedStrength = 5;
	public static final int reinforcedIronStrength = 12;
	public static final int tickDelay = 1;
	private static final int textureIndexNormal = 0;
	private static final int textureIndexReinforced = 1;

	public BlockScaffold(Configuration config, InternalName internalName)
	{
		super(config, internalName, internalName != InternalName.blockIronScaffold ? Material.wood : Material.iron, ic2/core/item/block/ItemBlockRare);
		if (internalName == InternalName.blockIronScaffold)
		{
			setHardness(0.8F);
			setResistance(10F);
			setStepSound(Block.soundMetalFootstep);
			Ic2Items.ironScaffold = new ItemStack(this);
		} else
		{
			setHardness(0.5F);
			setResistance(0.2F);
			setStepSound(Block.soundWoodFootstep);
			Ic2Items.scaffold = new ItemStack(this);
		}
	}

	public String getTextureName(int index)
	{
		if (index == 0)
			return getUnlocalizedName();
		if (index == 1)
			return (new StringBuilder()).append(getUnlocalizedName()).append(".").append(InternalName.reinforced.name()).toString();
		else
			return null;
	}

	public int getTextureIndex(int meta)
	{
		return meta != getReinforcedStrength() ? 0 : 1;
	}

	public int getStandardStrength()
	{
		return super.blockMaterial != Material.iron ? 2 : 5;
	}

	public int getReinforcedStrength()
	{
		return super.blockMaterial != Material.iron ? 5 : 12;
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public boolean isBlockNormalCube(World world, int i, int j, int l)
	{
		return false;
	}

	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
	{
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;
			player.fallDistance = 0.0F;
			if (((Entity) (player)).motionY < -0.14999999999999999D)
				player.motionY = -0.14999999999999999D;
			if (IC2.keyboard.isForwardKeyDown(player) && ((Entity) (player)).motionY < 0.20000000000000001D)
				player.motionY = 0.20000000000000001D;
		}
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		float factor = 1.0F;
		float f = factor / 16F;
		return AxisAlignedBB.getBoundingBox((float)i + f, j, (float)k + f, ((float)i + factor) - f, (float)j + factor, ((float)k + factor) - f);
	}

	public boolean isBlockSolidOnSide(World world, int i, int j, int k, ForgeDirection side)
	{
		return side == ForgeDirection.DOWN || side == ForgeDirection.UP;
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
	{
		return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1);
	}

	public ArrayList getBlockDropped(World world, int i, int j, int k, int meta, int fortune)
	{
		ArrayList tr = new ArrayList();
		tr.add(new ItemStack(this, 1));
		if (meta == getReinforcedStrength())
		{
			if (super.blockMaterial == Material.iron)
				tr.add(new ItemStack(Ic2Items.ironFence.getItem(), 1));
			if (super.blockMaterial == Material.wood)
				tr.add(new ItemStack(Item.stick, 2));
		}
		return tr;
	}

	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float a, 
			float b, float c)
	{
		if (entityplayer.isSneaking())
			return false;
		ItemStack sticks = entityplayer.inventory.getCurrentItem();
		if (sticks == null || super.blockMaterial == Material.wood && (sticks.itemID != Item.stick.itemID || sticks.stackSize < 2) || super.blockMaterial == Material.iron && sticks.itemID != Ic2Items.ironFence.itemID)
			return false;
		if (world.getBlockMetadata(i, j, k) == getReinforcedStrength() || !isPillar(world, i, j, k))
			return false;
		if (super.blockMaterial == Material.wood)
			sticks.stackSize -= 2;
		else
			sticks.stackSize--;
		if (entityplayer.getCurrentEquippedItem().stackSize <= 0)
			entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
		world.setBlockMetadataWithNotify(i, j, k, getReinforcedStrength(), 7);
		return true;
	}

	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		if (entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().itemID == super.blockID)
		{
			for (; world.getBlockId(i, j, k) == super.blockID; j++);
			if (canPlaceBlockAt(world, i, j, k) && j < IC2.getWorldHeight(world))
			{
				world.setBlock(i, j, k, super.blockID, 0, 7);
				onPostBlockPlaced(world, i, j, k, 0);
				if (!entityplayer.capabilities.isCreativeMode)
				{
					entityplayer.getCurrentEquippedItem().stackSize--;
					if (entityplayer.getCurrentEquippedItem().stackSize <= 0)
						entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
				}
			}
		}
	}

	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		if (getStrengthFrom(world, i, j, k) <= -1)
			return false;
		else
			return super.canPlaceBlockAt(world, i, j, k);
	}

	public boolean isPillar(World world, int i, int j, int k)
	{
		for (; world.getBlockId(i, j, k) == super.blockID; j--);
		return world.isBlockNormalCube(i, j, k);
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		updateSupportStatus(world, i, j, k);
	}

	public void onPostBlockPlaced(World world, int i, int j, int k, int l)
	{
		updateTick(world, i, j, k, null);
	}

	public void updateTick(World world, int i, int j, int k, Random random)
	{
		int ownStrength = world.getBlockMetadata(i, j, k);
		if (ownStrength >= getReinforcedStrength())
		{
			if (!isPillar(world, i, j, k))
			{
				ownStrength = getStrengthFrom(world, i, j, k);
				ItemStack drop = new ItemStack(Item.stick, 2);
				if (super.blockMaterial == Material.iron)
					drop = new ItemStack(Ic2Items.ironFence.getItem());
				dropBlockAsItem_do(world, i, j, k, drop);
			}
		} else
		{
			ownStrength = getStrengthFrom(world, i, j, k);
		}
		if (ownStrength <= -1)
		{
			world.setBlock(i, j, k, 0, 0, 7);
			dropBlockAsItem_do(world, i, j, k, new ItemStack(this));
		} else
		if (ownStrength != world.getBlockMetadata(i, j, k))
		{
			world.setBlockMetadataWithNotify(i, j, k, ownStrength, 7);
			world.markBlockRangeForRenderUpdate(i, j, k, i, j, k);
		}
	}

	public int getStrengthFrom(World world, int i, int j, int k)
	{
		int strength = 0;
		if (isPillar(world, i, j - 1, k))
			strength = getStandardStrength() + 1;
		strength = compareStrengthTo(world, i, j - 1, k, strength);
		strength = compareStrengthTo(world, i + 1, j, k, strength);
		strength = compareStrengthTo(world, i - 1, j, k, strength);
		strength = compareStrengthTo(world, i, j, k + 1, strength);
		strength = compareStrengthTo(world, i, j, k - 1, strength);
		return strength - 1;
	}

	public int compareStrengthTo(World world, int i, int j, int k, int strength)
	{
		int s = 0;
		if (world.getBlockId(i, j, k) == super.blockID)
		{
			s = world.getBlockMetadata(i, j, k);
			if (s > getReinforcedStrength())
				s = getReinforcedStrength();
		}
		if (s > strength)
			return s;
		else
			return strength;
	}

	public void updateSupportStatus(World world, int i, int j, int k)
	{
		world.scheduleBlockUpdate(i, j, k, super.blockID, 1);
	}
}
