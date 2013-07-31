// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemDynamite.java

package ic2.core.item.block;

import ic2.api.item.IBoxable;
import ic2.core.*;
import ic2.core.block.*;
import ic2.core.init.InternalName;
import ic2.core.item.ItemIC2;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

public class ItemDynamite extends ItemIC2
	implements IBoxable
{

	public boolean sticky;

	public ItemDynamite(Configuration config, InternalName internalName, boolean st)
	{
		super(config, internalName);
		sticky = st;
		setMaxStackSize(16);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new BehaviorDynamiteDispense(sticky));
	}

	public int getMetadata(int i)
	{
		return i;
	}

	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, 
			float a, float b, float c)
	{
		if (sticky)
			return false;
		if (l == 0)
			j--;
		if (l == 1)
			j++;
		if (l == 2)
			k--;
		if (l == 3)
			k++;
		if (l == 4)
			i--;
		if (l == 5)
			i++;
		int i1 = world.getBlockId(i, j, k);
		if (i1 == 0 && Block.blocksList[Ic2Items.dynamiteStick.itemID].canPlaceBlockAt(world, i, j, k))
		{
			world.setBlock(i, j, k, Ic2Items.dynamiteStick.itemID, 0, 3);
			itemstack.stackSize--;
			return true;
		} else
		{
			return true;
		}
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (!entityplayer.capabilities.isCreativeMode)
			itemstack.stackSize--;
		world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (Item.itemRand.nextFloat() * 0.4F + 0.8F));
		if (IC2.platform.isSimulating())
			if (sticky)
				world.spawnEntityInWorld(new EntityStickyDynamite(world, entityplayer));
			else
				world.spawnEntityInWorld(new EntityDynamite(world, entityplayer));
		return itemstack;
	}

	public boolean canBeStoredInToolbox(ItemStack itemstack)
	{
		return true;
	}
}
