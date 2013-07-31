// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemFuelCanFilled.java

package ic2.core.item;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.core.Ic2Items;
import ic2.core.init.InternalName;
import ic2.core.util.StackUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item:
//			ItemFuelCan

public class ItemFuelCanFilled extends ItemFuelCan
	implements IFuelHandler
{

	public ItemFuelCanFilled(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		setMaxStackSize(1);
		setContainerItem(Ic2Items.fuelCan.getItem());
		GameRegistry.registerFuelHandler(this);
	}

	public int getBurnTime(ItemStack stack)
	{
		if (stack.itemID != super.itemID)
			return 0;
		NBTTagCompound data = StackUtil.getOrCreateNbtData(stack);
		if (stack.getItemDamage() > 0)
			data.setInteger("value", stack.getItemDamage());
		int fv = data.getInteger("value") * 2;
		return fv <= 32767 ? fv : 32767;
	}
}
