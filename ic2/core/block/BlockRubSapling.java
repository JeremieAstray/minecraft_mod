// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockRubSapling.java

package ic2.core.block;

import cpw.mods.fml.common.registry.GameRegistry;
import ic2.core.*;
import ic2.core.init.DefaultIds;
import ic2.core.init.InternalName;
import ic2.core.item.block.ItemBlockRare;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumPlantType;

// Referenced classes of package ic2.core.block:
//			WorldGenRubTree

public class BlockRubSapling extends BlockSapling
{

	public BlockRubSapling(Configuration config, InternalName internalName)
	{
		super(IC2.getBlockIdFor(config, internalName, DefaultIds.get(internalName)));
		setHardness(0.0F);
		setStepSound(Block.soundGrassFootstep);
		setUnlocalizedName(internalName.name());
		setCreativeTab(IC2.tabIC2);
		Ic2Items.rubberSapling = new ItemStack(this);
		GameRegistry.registerBlock(this, ic2/core/item/block/ItemBlockRare, internalName.name());
	}

	public void registerIcons(IconRegister iconRegister)
	{
		super.blockIcon = iconRegister.registerIcon((new StringBuilder()).append("ic2:").append(getUnlocalizedName()).toString());
	}

	public Icon getIcon(int par1, int par2)
	{
		return super.blockIcon;
	}

	public String getUnlocalizedName()
	{
		return super.getUnlocalizedName().substring(5);
	}

	public void updateTick(World world, int i, int j, int k, Random random)
	{
		if (!IC2.platform.isSimulating())
			return;
		if (!canBlockStay(world, i, j, k))
		{
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlock(i, j, k, 0, 0, 7);
			return;
		}
		if (world.getBlockLightValue(i, j + 1, k) >= 9 && random.nextInt(30) == 0)
			growTree(world, i, j, k, random);
	}

	public void growTree(World world, int i, int j, int k, Random random)
	{
		(new WorldGenRubTree()).grow(world, i, j, k, random);
	}

	public int damageDropped(int i)
	{
		return 0;
	}

	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float a, 
			float b, float c)
	{
		if (!IC2.platform.isSimulating())
			return false;
		ItemStack equipped = entityplayer.getCurrentEquippedItem();
		if (equipped == null)
			return false;
		if (equipped.getItem() == Item.dyePowder && equipped.getItemDamage() == 15)
		{
			growTree(world, i, j, k, world.rand);
			if (!entityplayer.capabilities.isCreativeMode)
				equipped.stackSize--;
			entityplayer.swingItem();
		}
		return false;
	}

	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
	}

	public EnumPlantType getPlantType(World world, int x, int y, int z)
	{
		return EnumPlantType.Plains;
	}
}
