// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ChargeTooltipHandler.java

package ic2.neiIntegration.core;

import codechicken.nei.forge.IContainerTooltipHandler;
import ic2.api.item.*;
import java.util.List;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ChargeTooltipHandler
	implements IContainerTooltipHandler
{

	public ChargeTooltipHandler()
	{
	}

	public List handleTooltipFirst(GuiContainer gui, int mousex, int mousey, List currentTip)
	{
		return currentTip;
	}

	public List handleItemTooltip(GuiContainer gui, ItemStack stack, List currentTip)
	{
		Item item = stack.getItem();
		if ((item instanceof IElectricItem) && (!(item instanceof ICustomElectricItem) || ((ICustomElectricItem)item).canShowChargeToolTip(stack)))
		{
			int maxCharge = ((IElectricItem)item).getMaxCharge(stack);
			if (maxCharge > 0)
			{
				int currentCharge = ElectricItem.discharge(stack, 0x7fffffff, 0x7fffffff, true, true);
				currentTip.add((new StringBuilder()).append(currentCharge).append("/").append(maxCharge).append(" EU").toString());
			}
		}
		return currentTip;
	}
}
