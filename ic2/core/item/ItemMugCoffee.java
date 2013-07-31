// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemMugCoffee.java

package ic2.core.item;

import ic2.core.Ic2Items;
import ic2.core.init.InternalName;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item:
//			ItemIC2

public class ItemMugCoffee extends ItemIC2
{

	public ItemMugCoffee(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		setHasSubtypes(true);
		setMaxStackSize(1);
	}

	public String getUnlocalizedName(ItemStack itemstack)
	{
		int meta = itemstack.getItemDamage();
		if (meta < 3)
			return (new StringBuilder()).append("itemMugCoffee_").append(meta).toString();
		else
			return null;
	}

	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer player)
	{
		int meta = itemstack.getItemDamage();
		int highest = 0;
		int x = amplifyEffect(player, Potion.moveSpeed, meta);
		if (x > highest)
			highest = x;
		x = amplifyEffect(player, Potion.digSpeed, meta);
		if (x > highest)
			highest = x;
		if (meta == 2)
			highest -= 2;
		if (highest >= 3)
		{
			player.addPotionEffect(new PotionEffect(Potion.confusion.id, (highest - 2) * 200, 0));
			if (highest >= 4)
				player.addPotionEffect(new PotionEffect(Potion.harm.id, 1, highest - 3));
		}
		return new ItemStack(Ic2Items.mugEmpty.getItem());
	}

	public int amplifyEffect(EntityPlayer player, Potion potion, int meta)
	{
		PotionEffect eff = player.getActivePotionEffect(potion);
		if (eff != null)
		{
			int max = 1;
			if (meta == 1)
				max = 5;
			if (meta == 2)
				max = 6;
			int newAmp = eff.getAmplifier();
			int newDur = eff.getDuration();
			if (newAmp < max)
				newAmp++;
			if (meta == 0)
				newDur += 600;
			else
				newDur += 1200;
			eff.combine(new PotionEffect(eff.getPotionID(), newDur, newAmp));
			return newAmp;
		} else
		{
			player.addPotionEffect(new PotionEffect(potion.id, 300, 0));
			return 1;
		}
	}

	public int getMaxItemUseDuration(ItemStack itemstack)
	{
		return 32;
	}

	public EnumAction getItemUseAction(ItemStack itemstack)
	{
		return EnumAction.drink;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
	{
		player.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}

	public void getSubItems(int i, CreativeTabs tabs, List itemList)
	{
		for (int meta = 0; meta < 3; meta++)
			itemList.add(new ItemStack(this, 1, meta));

	}
}
