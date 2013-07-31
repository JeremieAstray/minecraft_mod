// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockContainerCommon.java

package ic2.core.block;

import cpw.mods.fml.common.registry.GameRegistry;
import ic2.core.IC2;
import ic2.core.init.DefaultIds;
import ic2.core.init.InternalName;
import ic2.core.item.block.ItemBlockIC2;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.block:
//			IRareBlock

public abstract class BlockContainerCommon extends Block
	implements IRareBlock
{

	public final InternalName internalName;

	public BlockContainerCommon(Configuration config, InternalName internalName, Material material)
	{
		this(config, internalName, material, ic2/core/item/block/ItemBlockIC2);
	}

	public BlockContainerCommon(Configuration config, InternalName internalName, Material material, Class itemClass)
	{
		super(IC2.getBlockIdFor(config, internalName, DefaultIds.get(internalName)), material);
		setUnlocalizedName(internalName.name());
		setCreativeTab(IC2.tabIC2);
		this.internalName = internalName;
		GameRegistry.registerBlock(this, itemClass, internalName.name());
	}

	public boolean hasTileEntity(int metadata)
	{
		return true;
	}

	public abstract TileEntity createTileEntity(World world, int i);

	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.common;
	}
}
