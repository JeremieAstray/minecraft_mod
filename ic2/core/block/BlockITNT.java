// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockITNT.java

package ic2.core.block;

import ic2.core.IC2;
import ic2.core.init.InternalName;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.block:
//			BlockIC2Explosive, EntityItnt, EntityNuke, EntityIC2Explosive

public class BlockITNT extends BlockIC2Explosive
{

	public boolean isITNT;

	public BlockITNT(Configuration config, InternalName internalName)
	{
		super(config, internalName, internalName == InternalName.blockITNT);
		if (internalName == InternalName.blockITNT)
			isITNT = true;
		else
			isITNT = false;
		setHardness(0.0F);
		setStepSound(Block.soundGrassFootstep);
	}

	public EntityIC2Explosive getExplosionEntity(World world, float x, float y, float z, EntityLivingBase igniter)
	{
		EntityIC2Explosive ret;
		if (isITNT)
			ret = new EntityItnt(world, x, y, z);
		else
			ret = new EntityNuke(world, x, y, z);
		ret.setIgniter(igniter);
		return ret;
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack itemStack)
	{
		if (!isITNT && (entityliving instanceof EntityPlayer))
			IC2.log.log(Level.INFO, (new StringBuilder()).append("Player ").append(((EntityPlayer)entityliving).username).append(" placed a nuke at ").append(world.provider.dimensionId).append(":(").append(x).append(",").append(y).append(",").append(z).append(")").toString());
	}

	public void onIgnite(World world, EntityPlayer player, int x, int y, int z)
	{
		if (!isITNT)
			IC2.log.log(Level.INFO, (new StringBuilder()).append("Nuke at ").append(world.provider.dimensionId).append(":(").append(x).append(",").append(y).append(",").append(z).append(") was ignited ").append(player != null ? (new StringBuilder()).append("by ").append(player.username).toString() : "indirectly").toString());
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return isITNT ? EnumRarity.common : EnumRarity.uncommon;
	}

	public void getSubBlocks(int i, CreativeTabs tabs, List itemList)
	{
		if (!isITNT && !IC2.enableCraftingNuke)
		{
			return;
		} else
		{
			super.getSubBlocks(i, tabs, itemList);
			return;
		}
	}
}
