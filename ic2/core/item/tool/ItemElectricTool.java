// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemElectricTool.java

package ic2.core.item.tool;

import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.item.*;
import ic2.core.IC2;
import ic2.core.init.DefaultIds;
import ic2.core.init.InternalName;
import java.util.*;
import java.util.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.ForgeHooks;

public abstract class ItemElectricTool extends ItemTool
	implements IElectricItem
{

	public int operationEnergyCost;
	public int maxCharge;
	public int transferLimit;
	public int tier;
	public Set mineableBlocks;

	public ItemElectricTool(Configuration config, InternalName internalName, EnumToolMaterial toolmaterial, int operationEnergyCost)
	{
		super(IC2.getItemIdFor(config, internalName, DefaultIds.get(internalName)), 0.0F, toolmaterial, new Block[0]);
		mineableBlocks = new HashSet();
		this.operationEnergyCost = operationEnergyCost;
		setMaxDamage(27);
		setMaxStackSize(1);
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

	public float getStrVsBlock(ItemStack tool, Block block)
	{
		if (!ElectricItem.manager.canUse(tool, operationEnergyCost))
			return 1.0F;
		if (ForgeHooks.isToolEffective(tool, block, 0))
			return super.efficiencyOnProperMaterial;
		if (canHarvestBlock(block))
			return super.efficiencyOnProperMaterial;
		else
			return 1.0F;
	}

	public float getStrVsBlock(ItemStack tool, Block block, int md)
	{
		if (!ElectricItem.manager.canUse(tool, operationEnergyCost))
			return 1.0F;
		if (ForgeHooks.isToolEffective(tool, block, md))
			return super.efficiencyOnProperMaterial;
		if (canHarvestBlock(block))
			return super.efficiencyOnProperMaterial;
		else
			return 1.0F;
	}

	public boolean canHarvestBlock(Block block)
	{
		return mineableBlocks.contains(block);
	}

	public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase entityliving1)
	{
		return true;
	}

	public int getItemEnchantability()
	{
		return 0;
	}

	public boolean isRepairable()
	{
		return false;
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
		return maxCharge;
	}

	public int getTier(ItemStack itemStack)
	{
		return tier;
	}

	public int getTransferLimit(ItemStack itemStack)
	{
		return transferLimit;
	}

	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLiving)
	{
		if (Block.blocksList[par3] == null)
		{
			IC2.log.severe((new StringBuilder()).append("ItemElectricTool.onBlockDestroyed(): received invalid block id ").append(par3).toString());
			return false;
		}
		if ((double)Block.blocksList[par3].getBlockHardness(par2World, par4, par5, par6) != 0.0D)
			if (par7EntityLiving != null)
				ElectricItem.manager.use(par1ItemStack, operationEnergyCost, par7EntityLiving);
			else
				ElectricItem.manager.discharge(par1ItemStack, operationEnergyCost, tier, true, false);
		return true;
	}

	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return false;
	}

	public void getSubItems(int i, CreativeTabs tabs, List itemList)
	{
		ItemStack charged = new ItemStack(this, 1);
		ElectricItem.manager.charge(charged, 0x7fffffff, 0x7fffffff, true, false);
		itemList.add(charged);
		itemList.add(new ItemStack(this, 1, getMaxDamage()));
	}
}
