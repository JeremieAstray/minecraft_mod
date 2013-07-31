// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockMultiID.java

package ic2.core.block;

import ic2.api.tile.IWrenchable;
import ic2.core.*;
import ic2.core.init.InternalName;
import ic2.core.util.StackUtil;
import java.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.ForgeDirection;

// Referenced classes of package ic2.core.block:
//			BlockContainerCommon, TileEntityBlock, BlockTextureStitched

public abstract class BlockMultiID extends BlockContainerCommon
{

	public static final int sideAndFacingToSpriteOffset[][] = {
		{
			3, 2, 0, 0, 0, 0
		}, {
			2, 3, 1, 1, 1, 1
		}, {
			1, 1, 3, 2, 5, 4
		}, {
			0, 0, 2, 3, 4, 5
		}, {
			4, 5, 4, 5, 3, 2
		}, {
			5, 4, 5, 4, 2, 3
		}
	};
	protected Icon textures[][];
	public int renderMask;

	public BlockMultiID(Configuration config, InternalName internalName, Material material)
	{
		super(config, internalName, material);
		renderMask = 63;
	}

	public BlockMultiID(Configuration config, InternalName internalName, Material material, Class itemClass)
	{
		super(config, internalName, material, itemClass);
		renderMask = 63;
	}

	public String getTextureFolder()
	{
		return null;
	}

	public String getTextureName(int index)
	{
		Item item = Item.itemsList[super.blockID];
		if (!item.getHasSubtypes())
			if (index == 0)
				return getUnlocalizedName();
			else
				return null;
		ItemStack itemStack = new ItemStack(this, 1, index);
		String ret = item.getUnlocalizedName(itemStack);
		if (ret == null)
			return null;
		else
			return ret.replace("item", "block");
	}

	public int getTextureIndex(int meta)
	{
		return meta;
	}

	public int getFacing(int meta)
	{
		return 3;
	}

	public int getFacing(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		TileEntity te = iBlockAccess.getBlockTileEntity(x, y, z);
		if (te instanceof TileEntityBlock)
		{
			return ((TileEntityBlock)te).getFacing();
		} else
		{
			int meta = iBlockAccess.getBlockMetadata(x, y, z);
			return getFacing(meta);
		}
	}

	public final int getTextureSubIndex(int side, int facing)
	{
		return getTextureSubIndex(side, facing, false);
	}

	public final int getTextureSubIndex(int side, int facing, boolean active)
	{
		int ret = sideAndFacingToSpriteOffset[side][facing];
		if (active)
			return ret + 6;
		else
			return ret;
	}

	public void registerIcons(IconRegister iconRegister)
	{
		int metaCount;
		for (metaCount = 0; getTextureName(metaCount) != null; metaCount++);
		textures = new Icon[metaCount][12];
		String textureFolder = getTextureFolder() != null ? (new StringBuilder()).append(getTextureFolder()).append("/").toString() : "";
		for (int index = 0; index < metaCount; index++)
		{
			String name = (new StringBuilder()).append("ic2:").append(textureFolder).append(getTextureName(index)).toString();
			for (int active = 0; active < 2; active++)
			{
				for (int side = 0; side < 6; side++)
				{
					int subIndex = active * 6 + side;
					String subName = (new StringBuilder()).append(name).append(":").append(subIndex).toString();
					net.minecraft.client.renderer.texture.TextureAtlasSprite texture = new BlockTextureStitched(subName, subIndex);
					textures[index][subIndex] = texture;
					((TextureMap)iconRegister).setTextureEntry(subName, texture);
				}

			}

		}

	}

	public Icon getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		int facing;
		boolean active;
		int meta;
		int index;
		int subIndex;
		facing = getFacing(iBlockAccess, x, y, z);
		active = isActive(iBlockAccess, x, y, z);
		meta = iBlockAccess.getBlockMetadata(x, y, z);
		index = getTextureIndex(meta);
		subIndex = getTextureSubIndex(side, facing, active);
		if (index >= textures.length)
			return null;
		return textures[index][subIndex];
		Exception e;
		e;
		IC2.platform.displayError(e, (new StringBuilder()).append("Coordinates: ").append(x).append("/").append(y).append("/").append(z).append("\n").append("Side: ").append(side).append("\n").append("Block: ").append(this).append("\n").append("Meta: ").append(meta).append("\n").append("Facing: ").append(facing).append("\n").append("Active: ").append(active).append("\n").append("Index: ").append(index).append("\n").append("SubIndex: ").append(subIndex).toString());
		return null;
	}

	public Icon getIcon(int side, int meta)
	{
		int facing;
		int index;
		int subIndex;
		facing = getFacing(meta);
		index = getTextureIndex(meta);
		subIndex = getTextureSubIndex(side, facing);
		if (index >= textures.length)
			return null;
		return textures[index][subIndex];
		Exception e;
		e;
		IC2.platform.displayError(e, (new StringBuilder()).append("Side: ").append(side).append("\n").append("Block: ").append(this).append("\n").append("Meta: ").append(meta).append("\n").append("Facing: ").append(facing).append("\n").append("Index: ").append(index).append("\n").append("SubIndex: ").append(subIndex).toString());
		return null;
	}

	public String getUnlocalizedName()
	{
		return super.getUnlocalizedName().substring(5);
	}

	public int getRenderType()
	{
		return IC2.platform.getRenderId("default");
	}

	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side)
	{
		if ((renderMask & 1 << side) != 0)
			return super.shouldSideBeRendered(blockAccess, x, y, z, side);
		else
			return false;
	}

	public void onRender(IBlockAccess blockAccess, int x, int y, int z)
	{
		TileEntity te = blockAccess.getBlockTileEntity(x, y, z);
		if (te instanceof TileEntityBlock)
			((TileEntityBlock)te).onRender();
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float a, 
			float b, float c)
	{
		if (entityPlayer.isSneaking())
			return false;
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof IHasGui)
		{
			if (IC2.platform.isSimulating())
				return IC2.platform.launchGui(entityPlayer, (IHasGui)te);
			else
				return true;
		} else
		{
			return false;
		}
	}

	public ArrayList getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList ret = super.getBlockDropped(world, x, y, z, metadata, fortune);
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof IInventory)
		{
			IInventory inv = (IInventory)te;
			for (int i = 0; i < inv.getSizeInventory(); i++)
			{
				ItemStack itemStack = inv.getStackInSlot(i);
				if (itemStack != null)
				{
					ret.add(itemStack);
					inv.setInventorySlotContents(i, null);
				}
			}

		}
		return ret;
	}

	public void breakBlock(World world, int x, int y, int z, int a, int b)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof TileEntityBlock)
			((TileEntityBlock)te).onBlockBreak(a, b);
		boolean firstItem = true;
		for (Iterator it = getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0).iterator(); it.hasNext();)
		{
			ItemStack itemStack = (ItemStack)it.next();
			if (firstItem)
				firstItem = false;
			else
				StackUtil.dropAsEntity(world, x, y, z, itemStack);
		}

		super.breakBlock(world, x, y, z, a, b);
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack itemStack)
	{
		if (!IC2.platform.isSimulating())
			return;
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity instanceof IWrenchable)
		{
			IWrenchable te = (IWrenchable)tileEntity;
			if (entityliving == null)
			{
				te.setFacing((short)2);
			} else
			{
				int l = MathHelper.floor_double((double)((((Entity) (entityliving)).rotationYaw * 4F) / 360F) + 0.5D) & 3;
				switch (l)
				{
				case 0: // '\0'
					te.setFacing((short)2);
					break;

				case 1: // '\001'
					te.setFacing((short)5);
					break;

				case 2: // '\002'
					te.setFacing((short)3);
					break;

				case 3: // '\003'
					te.setFacing((short)4);
					break;
				}
			}
		}
	}

	public void onSetBlockIDWithMetaData(World world, int x, int y, int z, int meta)
	{
		super.onSetBlockIDWithMetaData(world, x, y, z, meta);
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof TileEntityBlock)
		{
			TileEntityBlock teb = (TileEntityBlock)te;
			if (teb.loaded)
				teb.onUnloaded();
		}
	}

	public static boolean isActive(IBlockAccess iblockaccess, int i, int j, int k)
	{
		TileEntity te = iblockaccess.getBlockTileEntity(i, j, k);
		if (te instanceof TileEntityBlock)
			return ((TileEntityBlock)te).getActive();
		else
			return false;
	}

	public void getSubBlocks(int j, CreativeTabs tabs, List itemList)
	{
		Item item = Item.itemsList[super.blockID];
		if (!item.getHasSubtypes())
		{
			itemList.add(new ItemStack(this));
		} else
		{
			int i = 0;
			do
			{
				if (i >= 16)
					break;
				ItemStack is = new ItemStack(this, 1, i);
				if (Item.itemsList[super.blockID].getUnlocalizedName(is) == null)
					break;
				itemList.add(is);
				i++;
			} while (true);
		}
	}

	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return new ItemStack(this, 1, world.getBlockMetadata(x, y, z));
	}

	public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis)
	{
		if (axis == ForgeDirection.UNKNOWN)
			return false;
		TileEntity tileEntity = worldObj.getBlockTileEntity(x, y, z);
		if (tileEntity instanceof IWrenchable)
		{
			IWrenchable te = (IWrenchable)tileEntity;
			int newFacing = ForgeDirection.getOrientation(te.getFacing()).getRotation(axis).ordinal();
			if (te.wrenchCanSetFacing(null, newFacing))
				te.setFacing((short)newFacing);
		}
		return false;
	}

}
