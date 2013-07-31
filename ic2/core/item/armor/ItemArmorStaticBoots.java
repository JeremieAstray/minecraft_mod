// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemArmorStaticBoots.java

package ic2.core.item.armor;

import ic2.api.item.*;
import ic2.core.init.InternalName;
import ic2.core.util.StackUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.armor:
//			ItemArmorUtility

public class ItemArmorStaticBoots extends ItemArmorUtility
{

	public ItemArmorStaticBoots(Configuration config, InternalName internalName)
	{
		super(config, internalName, InternalName.rubber, 3);
	}

	public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack)
	{
		if (player.inventory.armorInventory[2] == null || !(player.inventory.armorInventory[2].getItem() instanceof IElectricItem))
			return;
		boolean ret = false;
		NBTTagCompound compound = StackUtil.getOrCreateNbtData(itemStack);
		boolean isNotWalking = ((Entity) (player)).ridingEntity != null || player.isInWater();
		if (!compound.hasKey("x") || isNotWalking)
			compound.setInteger("x", (int)((Entity) (player)).posX);
		if (!compound.hasKey("z") || isNotWalking)
			compound.setInteger("z", (int)((Entity) (player)).posZ);
		double distance = Math.sqrt((compound.getInteger("x") - (int)((Entity) (player)).posX) * (compound.getInteger("x") - (int)((Entity) (player)).posX) + (compound.getInteger("z") - (int)((Entity) (player)).posZ) * (compound.getInteger("z") - (int)((Entity) (player)).posZ));
		if (distance >= 5D)
		{
			compound.setInteger("x", (int)((Entity) (player)).posX);
			compound.setInteger("z", (int)((Entity) (player)).posZ);
			ret = ElectricItem.manager.charge(player.inventory.armorInventory[2], Math.min(3, (int)distance / 5), 0x7fffffff, true, false) > 0;
		}
		if (ret)
			player.inventoryContainer.detectAndSendChanges();
	}
}
