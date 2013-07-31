// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockRubLeaves.java

package ic2.core.block;

import cpw.mods.fml.common.registry.GameRegistry;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import ic2.core.init.DefaultIds;
import ic2.core.init.InternalName;
import ic2.core.item.block.ItemRubLeaves;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.*;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.IShearable;

public class BlockRubLeaves extends BlockLeaves
	implements IShearable
{

	int field_72135_b[];

	public BlockRubLeaves(Configuration config, InternalName internalName)
	{
		super(IC2.getBlockIdFor(config, internalName, DefaultIds.get(internalName)));
		setTickRandomly(true);
		setHardness(0.2F);
		setLightOpacity(1);
		setStepSound(Block.soundGrassFootstep);
		disableStats();
		super.graphicsLevel = true;
		setUnlocalizedName(internalName.name());
		setCreativeTab(IC2.tabIC2);
		Ic2Items.rubberLeaves = new ItemStack(this);
		GameRegistry.registerBlock(this, ic2/core/item/block/ItemRubLeaves, internalName.name());
	}

	public void registerIcons(IconRegister iconRegister)
	{
		super.blockIcon = iconRegister.registerIcon((new StringBuilder()).append("ic2:").append(getUnlocalizedName().substring(5)).toString());
	}

	public Icon getIcon(int par1, int par2)
	{
		return super.blockIcon;
	}

	public int getRenderColor(int i)
	{
		return ColorizerFoliage.getFoliageColorBirch();
	}

	public int colorMultiplier(IBlockAccess iblockaccess, int i, int j, int k)
	{
		return ColorizerFoliage.getFoliageColorBirch();
	}

	public int quantityDropped(Random random)
	{
		return random.nextInt(35) == 0 ? 1 : 0;
	}

	public int idDropped(int i, Random random, int j)
	{
		return Ic2Items.rubberSapling.itemID;
	}

	public int damageDropped(int i)
	{
		return 0;
	}

	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return par1IBlockAccess.getBlockId(par2, par3, par4) != super.blockID ? !par1IBlockAccess.isBlockOpaqueCube(par2, par3, par4) : super.graphicsLevel;
	}

	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
	{
		if (!par1World.isRemote && par1World.rand.nextInt(35) == 0)
		{
			int var9 = idDropped(par5, par1World.rand, par7);
			dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(var9, 1, damageDropped(par5)));
		}
	}

	public boolean isShearable(ItemStack item, World world, int x, int i, int j)
	{
		return true;
	}

	public ArrayList onSheared(ItemStack item, World world, int x, int y, int z, int fortune)
	{
		ArrayList ret = new ArrayList();
		ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z) & 3));
		return ret;
	}

	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
	}
}
