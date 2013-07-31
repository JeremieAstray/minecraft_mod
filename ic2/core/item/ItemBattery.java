// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemBattery.java

package ic2.core.item;

import ic2.api.item.*;
import ic2.core.*;
import ic2.core.init.InternalName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item:
//			BaseElectricItem

public class ItemBattery extends BaseElectricItem
{

	public ItemBattery(Configuration config, InternalName internalName, int maxCharge, int transferLimit, int tier)
	{
		super(config, internalName);
		setNoRepair();
		this.maxCharge = maxCharge;
		this.transferLimit = transferLimit;
		this.tier = tier;
	}

	public String getTextureName(int index)
	{
		if (index < 5)
			return (new StringBuilder()).append(getUnlocalizedName()).append(".").append(index).toString();
		else
			return null;
	}

	public Icon getIconFromDamage(int meta)
	{
		if (meta <= 1)
			return textures[4];
		if (meta >= getMaxDamage() - 1)
			return textures[0];
		else
			return textures[3 - (3 * (meta - 2)) / ((getMaxDamage() - 4) + 1)];
	}

	public boolean canProvideEnergy(ItemStack itemStack)
	{
		return true;
	}

	public int getEmptyItemId(ItemStack itemStack)
	{
		if (super.itemID == Ic2Items.chargedReBattery.itemID)
			return Ic2Items.reBattery.itemID;
		else
			return super.getEmptyItemId(itemStack);
	}

	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityplayer)
	{
		if (IC2.platform.isSimulating() && itemStack.itemID == Ic2Items.chargedReBattery.itemID)
		{
			boolean transferred = false;
			for (int i = 0; i < 9; i++)
			{
				ItemStack stack = entityplayer.inventory.mainInventory[i];
				if (stack == null || !(Item.itemsList[stack.itemID] instanceof IElectricItem) || (Item.itemsList[stack.itemID] instanceof ItemBattery))
					continue;
				IElectricItem item = (IElectricItem)stack.getItem();
				int transfer = ElectricItem.manager.discharge(itemStack, 2 * transferLimit, item.getTier(stack), true, true);
				transfer = ElectricItem.manager.charge(stack, transfer, tier, true, false);
				ElectricItem.manager.discharge(itemStack, transfer, item.getTier(stack), true, false);
				if (transfer > 0)
					transferred = true;
			}

			if (transferred && !IC2.platform.isRendering())
				entityplayer.openContainer.detectAndSendChanges();
		}
		return itemStack;
	}
}
