// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockBarrel.java

package ic2.core.block;

import ic2.core.*;
import ic2.core.init.InternalName;
import ic2.core.item.block.ItemBlockRare;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.block:
//			BlockMultiID, TileEntityBarrel

public class BlockBarrel extends BlockMultiID
{

	private static final int textureIndexNormal = 0;
	private static final int textureIndexTap = 1;

	public BlockBarrel(Configuration config, InternalName internalName)
	{
		super(config, internalName, Material.wood, ic2/core/item/block/ItemBlockRare);
		setHardness(1.0F);
		setStepSound(Block.soundWoodFootstep);
		setCreativeTab(null);
		Ic2Items.blockBarrel = new ItemStack(this);
	}

	public String getTextureName(int index)
	{
		if (index == 0)
			return getUnlocalizedName();
		if (index == 1)
			return (new StringBuilder()).append(getUnlocalizedName()).append(".").append(InternalName.tap.name()).toString();
		else
			return null;
	}

	public int getTextureIndex(int meta)
	{
		return 0;
	}

	public Icon getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		TileEntity tileEntity = iBlockAccess.getBlockTileEntity(x, y, z);
		int subIndex = getTextureSubIndex(side, 3);
		if (side > 1 && (tileEntity instanceof TileEntityBarrel) && ((TileEntityBarrel)tileEntity).treetapSide == side)
			return textures[1][subIndex];
		else
			return textures[0][subIndex];
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float a, 
			float b, float c)
	{
		return ((TileEntityBarrel)world.getBlockTileEntity(x, y, z)).rightclick(entityPlayer);
	}

	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
	{
		TileEntityBarrel barrel = (TileEntityBarrel)world.getBlockTileEntity(x, y, z);
		if (barrel.treetapSide > 1)
		{
			if (IC2.platform.isSimulating())
				StackUtil.dropAsEntity(world, x, y, z, new ItemStack(Ic2Items.treetap.getItem()));
			barrel.treetapSide = 0;
			barrel.update();
			barrel.drainLiquid(1);
			return;
		}
		if (IC2.platform.isSimulating())
			StackUtil.dropAsEntity(world, x, y, z, new ItemStack(Ic2Items.barrel.getItem(), 1, barrel.calculateMetaValue()));
		world.setBlock(x, y, z, Ic2Items.scaffold.itemID, 0, 3);
	}

	public TileEntity createTileEntity(World world, int metaData)
	{
		return new TileEntityBarrel();
	}

	public ArrayList getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList re = new ArrayList();
		re.add(new ItemStack(Ic2Items.scaffold.getItem()));
		re.add(new ItemStack(Ic2Items.barrel.getItem(), 1, 0));
		return re;
	}
}
