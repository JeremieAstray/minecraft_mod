// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemRubLeaves.java

package ic2.core.item.block;

import ic2.core.Ic2Items;
import net.minecraft.block.Block;
import net.minecraft.item.ItemLeaves;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.ColorizerFoliage;

public class ItemRubLeaves extends ItemLeaves
{

	public ItemRubLeaves(int par1)
	{
		super(par1);
		setHasSubtypes(false);
	}

	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		return ColorizerFoliage.getFoliageColorBirch();
	}

	public String getUnlocalizedName()
	{
		return super.getUnlocalizedName().substring(5);
	}

	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		return getUnlocalizedName();
	}

	public String getItemDisplayName(ItemStack itemStack)
	{
		return StatCollector.translateToLocal((new StringBuilder()).append("ic2.").append(getUnlocalizedName(itemStack)).toString());
	}

	public Icon getIconFromDamage(int par1)
	{
		return Block.blocksList[Ic2Items.rubberLeaves.itemID].getIcon(0, par1);
	}
}
