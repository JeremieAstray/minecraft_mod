// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockWall.java

package ic2.core.block;

import ic2.api.event.PaintEvent;
import ic2.api.event.RetextureEvent;
import ic2.core.Ic2Items;
import ic2.core.init.InternalName;
import ic2.core.item.block.ItemBlockRare;
import ic2.core.util.Util;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.*;
import net.minecraftforge.event.EventBus;

// Referenced classes of package ic2.core.block:
//			BlockMultiID, TileEntityWall

public class BlockWall extends BlockMultiID
{

	public BlockWall(Configuration config, InternalName internalName)
	{
		super(config, internalName, Material.rock, ic2/core/item/block/ItemBlockRare);
		setHardness(3F);
		setResistance(30F);
		setStepSound(Block.soundStoneFootstep);
		Ic2Items.constructionFoamWall = new ItemStack(this);
		setCreativeTab(null);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public String getTextureFolder()
	{
		return "cf";
	}

	public String getTextureName(int index)
	{
		if (index >= 0 && index <= 15)
			return (new StringBuilder()).append(InternalName.blockWall.name()).append(".").append(Util.getColorName(index).name()).toString();
		else
			return null;
	}

	public Icon getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		TileEntityWall wall;
		Block referencedBlock;
		TileEntity te = iBlockAccess.getBlockTileEntity(x, y, z);
		if (!(te instanceof TileEntityWall))
			break MISSING_BLOCK_LABEL_105;
		wall = (TileEntityWall)te;
		if (wall.retextureRefId == null || wall.retextureRefId[side] == 0 || wall.retextureRefMeta == null || wall.retextureRefSide == null)
			break MISSING_BLOCK_LABEL_105;
		referencedBlock = Block.blocksList[wall.retextureRefId[side]];
		if (referencedBlock == null)
			break MISSING_BLOCK_LABEL_105;
		return referencedBlock.getIcon(wall.retextureRefSide[side], wall.retextureRefMeta[side]);
		Exception e;
		e;
		return super.getBlockTexture(iBlockAccess, x, y, z, side);
	}

	public int quantityDropped(Random r)
	{
		return 0;
	}

	public TileEntity createTileEntity(World world, int metadata)
	{
		return new TileEntityWall();
	}

	public boolean recolourBlock(World world, int x, int y, int z, ForgeDirection side, int color)
	{
		color = BlockColored.getDyeFromBlock(color);
		if (color != world.getBlockMetadata(x, y, z))
		{
			world.setBlockMetadataWithNotify(x, y, z, color, 3);
			return true;
		} else
		{
			return false;
		}
	}

	public void onPaint(PaintEvent event)
	{
		if (event.world.getBlockId(event.x, event.y, event.z) != super.blockID)
			return;
		if (event.color != event.world.getBlockMetadata(event.x, event.y, event.z))
		{
			event.world.setBlockMetadataWithNotify(event.x, event.y, event.z, event.color, 3);
			event.painted = true;
		}
	}

	public ItemStack createStackedBlock(int i)
	{
		return null;
	}

	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return Ic2Items.constructionFoam.copy();
	}

	public void onRetexture(RetextureEvent event)
	{
		TileEntity te = event.world.getBlockTileEntity(event.x, event.y, event.z);
		if ((te instanceof TileEntityWall) && ((TileEntityWall)te).retexture(event.side, event.referencedBlockId, event.referencedMeta, event.referencedSide))
			event.applied = true;
	}
}
