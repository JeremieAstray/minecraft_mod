// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemTinCan.java

package ic2.core.item;

import cpw.mods.fml.common.registry.GameRegistry;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import ic2.core.init.DefaultIds;
import ic2.core.init.InternalName;
import java.util.List;
import java.util.Random;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

public class ItemTinCan extends ItemFood
{

	public ItemTinCan(Configuration config, InternalName internalName)
	{
		super(IC2.getItemIdFor(config, internalName, DefaultIds.get(internalName)), 2, 0.95F, false);
		setHasSubtypes(true);
		setUnlocalizedName(internalName.name());
		setCreativeTab(IC2.tabIC2);
		GameRegistry.registerItem(this, internalName.name());
	}

	public void registerIcons(IconRegister iconRegister)
	{
		super.itemIcon = iconRegister.registerIcon((new StringBuilder()).append("ic2:").append(getUnlocalizedName()).toString());
	}

	public String getUnlocalizedName()
	{
		return super.getUnlocalizedName().substring(5);
	}

	public String getUnlocalizedName(ItemStack itemStack)
	{
		return getUnlocalizedName();
	}

	public String getItemDisplayName(ItemStack itemStack)
	{
		return StatCollector.translateToLocal((new StringBuilder()).append("ic2.").append(getUnlocalizedName(itemStack)).toString());
	}

	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		super.onEaten(itemstack, world, entityplayer);
		onEaten(entityplayer);
		return itemstack;
	}

	public void onEaten(EntityPlayer entityplayer)
	{
		entityplayer.heal(2.0F);
		ItemStack is = Ic2Items.tinCan.copy();
		if (!entityplayer.inventory.addItemStackToInventory(is))
			entityplayer.dropPlayerItem(is);
	}

	public void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		switch (par1ItemStack.getItemDamage())
		{
		default:
			break;

		case 1: // '\001'
			if (par3EntityPlayer.getRNG().nextFloat() < 0.8F)
				par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 600 / (((ItemFood)Item.rottenFlesh).getHealAmount() / 2), 0));
			break;

		case 2: // '\002'
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.poison.id, 100 / (((ItemFood)Item.spiderEye).getHealAmount() / 2), 0));
			break;

		case 3: // '\003'
			if (par3EntityPlayer.getRNG().nextFloat() < 0.3F)
				par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 600 / (((ItemFood)Item.chickenRaw).getHealAmount() / 2), 0));
			break;

		case 4: // '\004'
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 100 / (((ItemFood)Item.appleGold).getHealAmount() / 2), 0));
			break;

		case 5: // '\005'
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600 / (((ItemFood)Item.appleGold).getHealAmount() / 2), 3));
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.resistance.id, 6000 / (((ItemFood)Item.appleGold).getHealAmount() / 2), 0));
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000 / (((ItemFood)Item.appleGold).getHealAmount() / 2), 0));
			break;
		}
	}

	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean debugTooltips)
	{
		int meta = stack.getItemDamage();
		if (meta == 1 || meta == 2 || meta == 3)
			info.add("This looks bad...");
	}

	public int getMaxItemUseDuration(ItemStack itemstack)
	{
		return 20;
	}
}
