// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemResin.java

package ic2.core.item;

import ic2.core.Ic2Items;
import ic2.core.init.InternalName;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item:
//			ItemIC2

public class ItemResin extends ItemIC2
{

	public ItemResin(Configuration config, InternalName internalName)
	{
		super(config, internalName);
	}

	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, 
			float a, float b, float c)
	{
		if (world.getBlockId(i, j, k) == ((Block) (Block.pistonBase)).blockID && world.getBlockMetadata(i, j, k) == side)
		{
			world.setBlock(i, j, k, ((Block) (Block.pistonStickyBase)).blockID, side, 3);
			if (!entityplayer.capabilities.isCreativeMode)
				itemstack.stackSize--;
			return true;
		}
		if (side != 1)
			return false;
		j++;
		if (world.getBlockId(i, j, k) != 0 || !Block.blocksList[Ic2Items.resinSheet.itemID].canPlaceBlockAt(world, i, j, k))
			return false;
		world.setBlock(i, j, k, Ic2Items.resinSheet.itemID, 0, 3);
		if (!entityplayer.capabilities.isCreativeMode)
			itemstack.stackSize--;
		return true;
	}
}
