// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemArmorSolarHelmet.java

package ic2.core.item.armor;

import ic2.api.item.*;
import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;
import ic2.core.init.InternalName;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.armor:
//			ItemArmorUtility

public class ItemArmorSolarHelmet extends ItemArmorUtility
{

	public ItemArmorSolarHelmet(Configuration config, InternalName internalName)
	{
		super(config, internalName, InternalName.solar, 0);
		setMaxDamage(0);
	}

	public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack)
	{
		boolean ret = false;
		if (player.inventory.armorInventory[2] != null && (player.inventory.armorInventory[2].getItem() instanceof IElectricItem) && TileEntitySolarGenerator.isSunVisible(((Entity) (player)).worldObj, (int)((Entity) (player)).posX, (int)((Entity) (player)).posY + 1, (int)((Entity) (player)).posZ))
			ret = ElectricItem.manager.charge(player.inventory.armorInventory[2], 1, 0x7fffffff, true, false) > 0;
		if (ret)
			player.inventoryContainer.detectAndSendChanges();
	}

	public int getItemEnchantability()
	{
		return 0;
	}
}
