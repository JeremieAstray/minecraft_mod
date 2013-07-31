// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemToolCutter.java

package ic2.core.item.tool;

import ic2.core.*;
import ic2.core.audio.*;
import ic2.core.block.wiring.TileEntityCable;
import ic2.core.init.InternalName;
import ic2.core.item.ItemIC2;
import java.util.Random;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

public class ItemToolCutter extends ItemIC2
{

	public ItemToolCutter(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		setMaxDamage(512);
		setMaxStackSize(1);
	}

	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, 
			float a, float b, float c)
	{
		TileEntity te = world.getBlockTileEntity(i, j, k);
		if (te instanceof TileEntityCable)
		{
			TileEntityCable cable = (TileEntityCable)te;
			if (cable.tryAddInsulation())
			{
				if (entityplayer.inventory.consumeInventoryItem(Ic2Items.rubber.itemID))
				{
					if (IC2.platform.isSimulating())
						damageCutter(itemstack, 1);
					return true;
				}
				cable.tryRemoveInsulation();
			}
		}
		return false;
	}

	public static void cutInsulationFrom(ItemStack itemstack, World world, int i, int j, int k)
	{
		TileEntity te = world.getBlockTileEntity(i, j, k);
		if (te instanceof TileEntityCable)
		{
			TileEntityCable cable = (TileEntityCable)te;
			if (cable.tryRemoveInsulation())
			{
				if (IC2.platform.isSimulating())
				{
					double d = (double)world.rand.nextFloat() * 0.69999999999999996D + 0.14999999999999999D;
					double d1 = (double)world.rand.nextFloat() * 0.69999999999999996D + 0.14999999999999999D;
					double d2 = (double)world.rand.nextFloat() * 0.69999999999999996D + 0.14999999999999999D;
					EntityItem entityitem = new EntityItem(world, (double)i + d, (double)j + d1, (double)k + d2, Ic2Items.rubber.copy());
					entityitem.delayBeforeCanPickup = 10;
					world.spawnEntityInWorld(entityitem);
					damageCutter(itemstack, 3);
				}
				if (IC2.platform.isRendering())
					IC2.audioManager.playOnce(new AudioPosition(world, (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F), PositionSpec.Center, "Tools/InsulationCutters.ogg", true, IC2.audioManager.defaultVolume);
			}
		}
	}

	public static void damageCutter(ItemStack itemStack, int damage)
	{
		if (!itemStack.isItemStackDamageable())
			return;
		itemStack.setItemDamage(itemStack.getItemDamage() + damage);
		if (itemStack.getItemDamage() > itemStack.getMaxDamage())
		{
			itemStack.stackSize--;
			if (itemStack.stackSize < 0)
				itemStack.stackSize = 0;
			itemStack.setItemDamage(0);
		}
	}
}
