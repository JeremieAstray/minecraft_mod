// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockCrop.java

package ic2.core.block;

import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.core.*;
import ic2.core.init.InternalName;
import ic2.core.item.block.ItemBlockRare;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.*;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.block:
//			BlockMultiID, TileEntityCrop

public class BlockCrop extends BlockMultiID
{

	public static TileEntityCrop tempStore;
	private static final int textureIndexStick = 0;
	private static final int textureIndexStickUpgraded = 1;

	public BlockCrop(Configuration config, InternalName internalName)
	{
		super(config, internalName, Material.plants, ic2/core/item/block/ItemBlockRare);
		setHardness(0.8F);
		setResistance(0.2F);
		setStepSound(Block.soundGrassFootstep);
		Ic2Items.crop = new ItemStack(this, 1, 0);
	}

	public String getTextureFolder()
	{
		return "crop";
	}

	public int getTextureIndex(int meta)
	{
		if (meta == 0 || meta == 1)
			return meta;
		else
			return 0;
	}

	public String getTextureName(int index)
	{
		switch (index)
		{
		case 0: // '\0'
			return (new StringBuilder()).append(getUnlocalizedName()).append(".").append(InternalName.stick.name()).toString();

		case 1: // '\001'
			return (new StringBuilder()).append(getUnlocalizedName()).append(".").append(InternalName.stick.name()).append(".").append(InternalName.upgraded.name()).toString();
		}
		return null;
	}

	public void registerIcons(IconRegister iconRegister)
	{
		super.registerIcons(iconRegister);
		Crops.instance.startSpriteRegistration(iconRegister);
	}

	public TileEntity createTileEntity(World world, int metaData)
	{
		return new TileEntityCrop();
	}

	public Icon getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		TileEntity te = iBlockAccess.getBlockTileEntity(x, y, z);
		if (te instanceof TileEntityCrop)
		{
			TileEntityCrop tileEntityCrop = (TileEntityCrop)te;
			if (tileEntityCrop.id < 0)
			{
				if (!tileEntityCrop.upgraded)
					return getIcon(side, 0);
				else
					return getIcon(side, 1);
			} else
			{
				return tileEntityCrop.crop().getSprite(tileEntityCrop);
			}
		} else
		{
			return super.getBlockTexture(iBlockAccess, x, y, z, side);
		}
	}

	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		return world.getBlockId(i, j - 1, k) == Block.tilledField.blockID && super.canPlaceBlockAt(world, i, j, k);
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		super.onNeighborBlockChange(world, i, j, k, l);
		if (world.getBlockId(i, j - 1, k) != Block.tilledField.blockID)
		{
			world.setBlock(i, j, k, 0, 0, 7);
			dropBlockAsItem(world, i, j, k, 0, 0);
		} else
		{
			((TileEntityCrop)world.getBlockTileEntity(i, j, k)).onNeighbourChange();
		}
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		double d = 0.20000000000000001D;
		return AxisAlignedBB.getBoundingBox(d, 0.0D, d, 1.0D - d, 0.69999999999999996D, 1.0D - d);
	}

	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
	{
		((TileEntityCrop)world.getBlockTileEntity(i, j, k)).onEntityCollision(entity);
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
		return IC2.platform.getRenderId("crop");
	}

	public int isProvidingWeakPower(IBlockAccess iblockaccess, int i, int j, int k, int l)
	{
		return ((TileEntityCrop)iblockaccess.getBlockTileEntity(i, j, k)).emitRedstone();
	}

	public void breakBlock(World world, int i, int j, int k, int a, int b)
	{
		if (world != null)
			tempStore = (TileEntityCrop)world.getBlockTileEntity(i, j, k);
		super.breakBlock(world, i, j, k, a, b);
	}

	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion)
	{
		if (tempStore != null)
			tempStore.onBlockDestroyed();
	}

	public int getLightValue(IBlockAccess iblockaccess, int i, int j, int k)
	{
		return ((TileEntityCrop)iblockaccess.getBlockTileEntity(i, j, k)).getEmittedLight();
	}

	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		((TileEntityCrop)world.getBlockTileEntity(i, j, k)).leftclick(entityplayer);
	}

	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float a, 
			float b, float c)
	{
		return ((TileEntityCrop)world.getBlockTileEntity(i, j, k)).rightclick(entityplayer);
	}
}
