// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockIC2Door.java

package ic2.core.block;

import cpw.mods.fml.common.registry.GameRegistry;
import ic2.core.IC2;
import ic2.core.init.DefaultIds;
import ic2.core.init.InternalName;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.Configuration;

public class BlockIC2Door extends BlockDoor
{

	public int itemDropped;
	private Icon textures[];

	public BlockIC2Door(Configuration config, InternalName internalName)
	{
		super(IC2.getBlockIdFor(config, internalName, DefaultIds.get(internalName)), Material.iron);
		setHardness(50F);
		setResistance(150F);
		setStepSound(Block.soundMetalFootstep);
		disableStats();
		setUnlocalizedName(internalName.name());
		setCreativeTab(null);
		GameRegistry.registerBlock(this, internalName.name());
	}

	public void registerIcons(IconRegister iconRegister)
	{
		textures = new Icon[2];
		textures[0] = iconRegister.registerIcon((new StringBuilder()).append("ic2:").append(getUnlocalizedName().substring(5)).append(".").append(InternalName.top).toString());
		textures[1] = iconRegister.registerIcon((new StringBuilder()).append("ic2:").append(getUnlocalizedName().substring(5)).append(".").append(InternalName.bottom).toString());
	}

	public Icon getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		return getIcon(side, iBlockAccess.getBlockMetadata(x, y, z));
	}

	public Icon getIcon(int side, int meta)
	{
		if ((meta & 8) == 8)
			return textures[0];
		else
			return textures[1];
	}

	public BlockIC2Door setItemDropped(int itemid)
	{
		itemDropped = itemid;
		return this;
	}

	public int idDropped(int meta, Random random, int j)
	{
		if ((meta & 8) == 8)
			return 0;
		else
			return itemDropped;
	}
}
