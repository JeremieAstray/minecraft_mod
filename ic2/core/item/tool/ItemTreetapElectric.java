// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemTreetapElectric.java

package ic2.core.item.tool;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItemManager;
import ic2.core.Ic2Items;
import ic2.core.init.InternalName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.tool:
//			ItemElectricTool, ItemTreetap

public class ItemTreetapElectric extends ItemElectricTool
{

	public ItemTreetapElectric(Configuration config, InternalName internalName)
	{
		super(config, internalName, EnumToolMaterial.IRON, 50);
		maxCharge = 10000;
		transferLimit = 100;
		tier = 1;
	}

	public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, 
			float hitX, float hitY, float hitZ)
	{
		if (world.getBlockId(x, y, z) != Ic2Items.rubberWood.itemID || !ElectricItem.manager.canUse(itemStack, operationEnergyCost))
			return false;
		if (ItemTreetap.attemptExtract(entityPlayer, world, x, y, z, side, null))
		{
			ElectricItem.manager.use(itemStack, operationEnergyCost, entityPlayer);
			return true;
		} else
		{
			return false;
		}
	}
}
