// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemArmorNightvisionGoggles.java

package ic2.core.item.armor;

import ic2.api.item.*;
import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.init.InternalName;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.armor:
//			ItemArmorUtility

public class ItemArmorNightvisionGoggles extends ItemArmorUtility
	implements IElectricItem
{

	public ItemArmorNightvisionGoggles(Configuration config, InternalName internalName)
	{
		super(config, internalName, InternalName.nightvision, 0);
		setMaxDamage(27);
	}

	public boolean canProvideEnergy(ItemStack itemStack)
	{
		return false;
	}

	public int getChargedItemId(ItemStack itemStack)
	{
		return super.itemID;
	}

	public int getEmptyItemId(ItemStack itemStack)
	{
		return super.itemID;
	}

	public int getMaxCharge(ItemStack itemStack)
	{
		return 20000;
	}

	public int getTier(ItemStack itemStack)
	{
		return 1;
	}

	public int getTransferLimit(ItemStack itemStack)
	{
		return 200;
	}

	public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack)
	{
		if (((Entity) (player)).worldObj.isRemote)
			return;
		boolean ret = false;
		if (ElectricItem.manager.use(itemStack, 1, player))
		{
			if (((Entity) (player)).worldObj.isDaytime())
			{
				int x = MathHelper.floor_double(((Entity) (player)).posX);
				int z = MathHelper.floor_double(((Entity) (player)).posZ);
				double fixedY = ((Entity) (player)).posY;
				if (fixedY < 0.0D)
					fixedY = 0.0D;
				else
				if (fixedY > (double)((Entity) (player)).worldObj.getHeight())
					fixedY = ((Entity) (player)).worldObj.getHeight();
				int skylight = ((Entity) (player)).worldObj.getChunkFromBlockCoords(x, z).getSavedLightValue(EnumSkyBlock.Sky, x & 0xf, MathHelper.floor_double(fixedY), z & 0xf);
				if (skylight > 12)
				{
					IC2.platform.removePotion(player, Potion.nightVision.id);
					player.addPotionEffect(new PotionEffect(Potion.blindness.id, 100 + (skylight - 13) * 50, 0, true));
				}
			} else
			{
				IC2.platform.removePotion(player, Potion.blindness.id);
				player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 250, 0, true));
			}
			ret = true;
		}
		if (ret)
			player.inventoryContainer.detectAndSendChanges();
	}

	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List itemList)
	{
		ItemStack itemStack = new ItemStack(this, 1);
		if (getChargedItemId(itemStack) == super.itemID)
		{
			ItemStack charged = new ItemStack(this, 1);
			ElectricItem.manager.charge(charged, 0x7fffffff, 0x7fffffff, true, false);
			itemList.add(charged);
		}
		if (getEmptyItemId(itemStack) == super.itemID)
			itemList.add(new ItemStack(this, 1, getMaxDamage()));
	}

	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return false;
	}
}
