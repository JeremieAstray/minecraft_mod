// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemMug.java

package ic2.core.item;

import ic2.core.*;
import ic2.core.block.TileEntityBarrel;
import ic2.core.init.InternalName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item:
//			ItemIC2

public class ItemMug extends ItemIC2
{

	public ItemMug(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		setMaxStackSize(1);
	}

	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, 
			float a, float b, float c)
	{
		if (world.getBlockId(i, j, k) == Ic2Items.blockBarrel.itemID)
		{
			TileEntityBarrel barrel = (TileEntityBarrel)world.getBlockTileEntity(i, j, k);
			if (barrel.treetapSide < 2 || barrel.treetapSide != side)
				return false;
			int value = barrel.calculateMetaValue();
			if (barrel.drainLiquid(1) && IC2.platform.isSimulating())
			{
				ItemStack is = new ItemStack(Ic2Items.mugBooze.itemID, 1, value);
				if (entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem].stackSize > 1)
				{
					entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem].stackSize--;
					if (!entityplayer.inventory.addItemStackToInventory(is))
						entityplayer.dropPlayerItem(is);
				} else
				{
					entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = is;
				}
				return true;
			} else
			{
				return false;
			}
		} else
		{
			return false;
		}
	}
}
