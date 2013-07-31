// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EntityNuke.java

package ic2.core.block;

import ic2.core.*;
import ic2.core.item.tool.ItemToolWrench;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

// Referenced classes of package ic2.core.block:
//			EntityIC2Explosive

public class EntityNuke extends EntityIC2Explosive
{

	public EntityNuke(World world, double x, double y, double z)
	{
		super(world, x, y, z, 300, IC2.explosionPowerNuke, 0.05F, 1.5F, Block.blocksList[Ic2Items.nuke.itemID]);
	}

	public EntityNuke(World world)
	{
		this(world, 0.0D, 0.0D, 0.0D);
	}

	public boolean func_130002_c(EntityPlayer player)
	{
		if (IC2.platform.isSimulating() && player.inventory.mainInventory[player.inventory.currentItem] != null && (player.inventory.mainInventory[player.inventory.currentItem].getItem() instanceof ItemToolWrench))
		{
			ItemToolWrench wrench = (ItemToolWrench)player.inventory.mainInventory[player.inventory.currentItem].getItem();
			if (wrench.canTakeDamage(player.inventory.mainInventory[player.inventory.currentItem], 1))
			{
				wrench.damage(player.inventory.mainInventory[player.inventory.currentItem], 1, player);
				setDead();
			}
		}
		return false;
	}
}
