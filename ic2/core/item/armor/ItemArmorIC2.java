// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemArmorIC2.java

package ic2.core.item.armor;

import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.item.IMetalArmor;
import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.init.DefaultIds;
import ic2.core.init.InternalName;
import ic2.core.util.Util;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.Configuration;

public class ItemArmorIC2 extends ItemArmor
	implements IMetalArmor
{

	private final Object repairMaterial;
	private final String armorName;

	public ItemArmorIC2(Configuration config, InternalName internalName, EnumArmorMaterial enumArmorMaterial, InternalName armorName, int armorType, Object repairMaterial)
	{
		super(IC2.getItemIdFor(config, internalName, DefaultIds.get(internalName)), enumArmorMaterial, IC2.platform.addArmor(armorName.name()), armorType);
		this.repairMaterial = repairMaterial;
		this.armorName = armorName.name();
		setMaxDamage(enumArmorMaterial.getDurability(armorType));
		setUnlocalizedName(internalName.name());
		setCreativeTab(IC2.tabIC2);
		GameRegistry.registerItem(this, internalName.name());
	}

	public void registerIcons(IconRegister iconRegister)
	{
		super.itemIcon = iconRegister.registerIcon((new StringBuilder()).append("ic2:").append(getUnlocalizedName()).toString());
	}

	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
	{
		int suffix = super.armorType != 2 ? 1 : 2;
		return (new StringBuilder()).append("/armor/ic2/textures/armor/").append(armorName).append("_").append(suffix).append(".png").toString();
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

	public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player)
	{
		return true;
	}

	public boolean getIsRepairable(ItemStack stack1, ItemStack stack2)
	{
		return stack2 != null && Util.matchesOD(stack2, repairMaterial);
	}
}
