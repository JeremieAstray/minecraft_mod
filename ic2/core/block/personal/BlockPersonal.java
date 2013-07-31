// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockPersonal.java

package ic2.core.block.personal;

import ic2.core.*;
import ic2.core.block.BlockMultiID;
import ic2.core.block.TileEntityBlock;
import ic2.core.init.InternalName;
import ic2.core.item.block.ItemPersonalBlock;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.block.personal:
//			TileEntityTradeOMat, TileEntityEnergyOMat, IPersonalBlock, TileEntityPersonalChest

public class BlockPersonal extends BlockMultiID
{

	public static Class tileEntityPersonalChestClass = ic2/core/block/personal/TileEntityPersonalChest;

	public BlockPersonal(Configuration config, InternalName internalName)
	{
		super(config, internalName, Material.iron, ic2/core/item/block/ItemPersonalBlock);
		setBlockUnbreakable();
		setResistance(6000000F);
		setStepSound(Block.soundMetalFootstep);
		Block.canBlockGrass[super.blockID] = false;
		Ic2Items.personalSafe = new ItemStack(this, 1, 0);
		Ic2Items.tradeOMat = new ItemStack(this, 1, 1);
		Ic2Items.energyOMat = new ItemStack(this, 1, 2);
	}

	public String getTextureFolder()
	{
		return "personal";
	}

	public int idDropped(int meta, Random random, int j)
	{
		return super.blockID;
	}

	public int damageDropped(int meta)
	{
		return meta;
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public int getRenderType()
	{
		return IC2.platform.getRenderId("personal");
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public ArrayList getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
	{
		if (IC2.platform.isSimulating() && IC2.platform.isRendering())
		{
			return super.getBlockDropped(world, x, y, z, metadata, fortune);
		} else
		{
			ArrayList ret = new ArrayList();
			ret.add(new ItemStack(super.blockID, 1, metadata));
			return ret;
		}
	}

	public TileEntityBlock createTileEntity(World world, int meta)
	{
		meta;
		JVM INSTR tableswitch 0 2: default 54
	//	               0 28
	//	               1 38
	//	               2 46;
		   goto _L1 _L2 _L3 _L4
_L2:
		return (TileEntityBlock)tileEntityPersonalChestClass.newInstance();
_L3:
		return new TileEntityTradeOMat();
_L4:
		return new TileEntityEnergyOMat();
_L1:
		return null;
		Exception e;
		e;
		throw new RuntimeException(e);
	}

	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float a, 
			float b, float c)
	{
		if (entityplayer.isSneaking())
			return false;
		int meta = world.getBlockMetadata(i, j, k);
		TileEntity te = world.getBlockTileEntity(i, j, k);
		if (IC2.platform.isSimulating() && meta != 1 && meta != 2 && (te instanceof IPersonalBlock) && !((IPersonalBlock)te).permitsAccess(entityplayer))
			return false;
		else
			return super.onBlockActivated(world, i, j, k, entityplayer, side, a, b, c);
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return stack.getItemDamage() != 0 ? EnumRarity.common : EnumRarity.uncommon;
	}

	public boolean canDragonDestroy(World world, int x, int y, int i)
	{
		return false;
	}

	public volatile TileEntity createTileEntity(World x0, int x1)
	{
		return createTileEntity(x0, x1);
	}

}
