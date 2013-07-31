// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemBatterySU.java

package ic2.core.item;

import ic2.api.item.*;
import ic2.core.init.InternalName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item:
//			ItemIC2

public class ItemBatterySU extends ItemIC2
	implements IBoxable
{

	public int capacity;
	public int tier;

	public ItemBatterySU(Configuration config, InternalName internalName, int capacity, int tier)
	{
		super(config, internalName);
		this.capacity = capacity;
		this.tier = tier;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (itemstack.itemID != super.itemID)
			return itemstack;
		int energy = capacity;
		for (int i = 0; i < 9 && energy > 0; i++)
		{
			ItemStack stack = entityplayer.inventory.mainInventory[i];
			if (stack != null && (Item.itemsList[stack.itemID] instanceof IElectricItem) && stack != itemstack)
				energy -= ElectricItem.manager.charge(stack, energy, tier, true, false);
		}

		if (energy != capacity)
			itemstack.stackSize--;
		return itemstack;
	}

	public boolean canBeStoredInToolbox(ItemStack itemstack)
	{
		return true;
	}
}
