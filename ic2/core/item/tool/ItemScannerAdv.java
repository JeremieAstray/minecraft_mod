// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemScannerAdv.java

package ic2.core.item.tool;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItemManager;
import ic2.core.init.InternalName;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.tool:
//			ItemScanner

public class ItemScannerAdv extends ItemScanner
{

	public ItemScannerAdv(Configuration config, InternalName internalName, int t)
	{
		super(config, internalName, t);
	}

	public int startLayerScan(ItemStack itemStack)
	{
		return ElectricItem.manager.use(itemStack, 250, null) ? 6 : 0;
	}
}
