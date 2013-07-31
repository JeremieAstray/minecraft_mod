// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemTerraWart.java

package ic2.core.item;

import cpw.mods.fml.common.registry.GameRegistry;
import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.init.DefaultIds;
import ic2.core.init.InternalName;
import java.util.Random;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

public class ItemTerraWart extends ItemFood
{

	public ItemTerraWart(Configuration config, InternalName internalName)
	{
		super(IC2.getItemIdFor(config, internalName, DefaultIds.get(internalName)), 0, 1.0F, false);
		setAlwaysEdible();
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

	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer player)
	{
		itemstack.stackSize--;
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		IC2.platform.removePotion(player, Potion.confusion.id);
		IC2.platform.removePotion(player, Potion.digSlowdown.id);
		IC2.platform.removePotion(player, Potion.hunger.id);
		IC2.platform.removePotion(player, Potion.moveSlowdown.id);
		IC2.platform.removePotion(player, Potion.weakness.id);
		return itemstack;
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.rare;
	}
}
