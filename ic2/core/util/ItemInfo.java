// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemInfo.java

package ic2.core.util;

import ic2.api.info.IEnergyValueProvider;
import ic2.api.info.IFuelValueProvider;
import ic2.api.item.*;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fluids.*;

// Referenced classes of package ic2.core.util:
//			StackUtil

public class ItemInfo
	implements IEnergyValueProvider, IFuelValueProvider
{

	public ItemInfo()
	{
	}

	public int getEnergyValue(ItemStack itemStack)
	{
		Item item = itemStack.getItem();
		if (item instanceof IElectricItem)
			return ElectricItem.manager.getCharge(itemStack);
		if (item.itemID == Ic2Items.suBattery.itemID || item.itemID == Item.redstone.itemID)
			return item.itemID != Ic2Items.suBattery.itemID ? 800 : 1200;
		else
			return 0;
	}

	public int getFuelValue(ItemStack itemStack, boolean allowLava)
	{
		int itemId = itemStack.itemID;
		FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(itemStack);
		if (liquid != null && liquid.getFluid() == FluidRegistry.LAVA)
			return !allowLava ? 0 : 2000;
		if (itemId == Ic2Items.filledFuelCan.itemID)
		{
			NBTTagCompound data = StackUtil.getOrCreateNbtData(itemStack);
			if (itemStack.getItemDamage() > 0)
				data.setInteger("value", itemStack.getItemDamage());
			return data.getInteger("value") * 2;
		}
		if (itemId == Ic2Items.scrap.itemID && !IC2.enableBurningScrap)
			return 0;
		else
			return TileEntityFurnace.getItemBurnTime(itemStack);
	}
}
