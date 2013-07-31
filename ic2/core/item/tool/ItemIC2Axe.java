// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemIC2Axe.java

package ic2.core.item.tool;

import cpw.mods.fml.common.registry.GameRegistry;
import ic2.core.IC2;
import ic2.core.init.DefaultIds;
import ic2.core.init.InternalName;
import ic2.core.util.Util;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.*;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.Configuration;

public class ItemIC2Axe extends ItemAxe
{

	private final Object repairMaterial;
	public float field_77864_a;

	public ItemIC2Axe(Configuration config, InternalName internalName, EnumToolMaterial enumtoolmaterial, float efficiency, Object repairMaterial)
	{
		super(IC2.getItemIdFor(config, internalName, DefaultIds.get(internalName)), enumtoolmaterial);
		super.efficiencyOnProperMaterial = efficiency;
		this.repairMaterial = repairMaterial;
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

	public int getItemEnchantability()
	{
		return 13;
	}

	public boolean getIsRepairable(ItemStack stack1, ItemStack stack2)
	{
		return stack2 != null && Util.matchesOD(stack2, repairMaterial);
	}
}
