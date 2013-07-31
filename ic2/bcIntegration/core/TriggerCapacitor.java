// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TriggerCapacitor.java

package ic2.bcIntegration.core;

import buildcraft.api.gates.ITriggerParameter;
import ic2.api.item.*;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.invslot.InvSlotCharge;
import ic2.core.block.invslot.InvSlotDischarge;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.block.wiring.TileEntityElectricBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

// Referenced classes of package ic2.bcIntegration.core:
//			Trigger

public class TriggerCapacitor extends Trigger
{

	int action;

	public TriggerCapacitor(int id, int action)
	{
		super(id);
		this.action = 0;
		this.action = action;
	}

	public int getIconIndex()
	{
		return action;
	}

	public String getDescription()
	{
		switch (action)
		{
		case 0: // '\0'
			return "Capacitor Empty";

		case 1: // '\001'
			return "Capacitor Has Energy";

		case 2: // '\002'
			return "Space For Energy";

		case 3: // '\003'
			return "Capacitor Full";

		case 4: // '\004'
			return "Charging Empty Item";

		case 5: // '\005'
			return "Charging Partially Charged Item";

		case 6: // '\006'
			return "Charging Fully Charged Item";

		case 7: // '\007'
			return "Discharging Empty Item";

		case 8: // '\b'
			return "Discharging Partially Charged Item";

		case 9: // '\t'
			return "Discharging Fully Charged Item";
		}
		return "";
	}

	public boolean isTriggerActive(ForgeDirection side, TileEntity tile, ITriggerParameter parameter)
	{
		if (tile == null)
			return false;
		if (tile instanceof TileEntityStandardMachine)
		{
			TileEntityStandardMachine teb = (TileEntityStandardMachine)tile;
			boolean hasEnergy = teb.energy >= teb.maxInput;
			boolean hasRoom = teb.energy <= teb.maxEnergy - teb.maxInput;
			boolean dischargeEnergy = canDischarge(teb.dischargeSlot.get());
			boolean dischargeRoom = canCharge(teb.dischargeSlot.get());
			switch (action)
			{
			case 0: // '\0'
				return !hasEnergy;

			case 1: // '\001'
				return hasEnergy;

			case 2: // '\002'
				return hasRoom;

			case 3: // '\003'
				return !hasRoom;

			case 7: // '\007'
				return !dischargeEnergy;

			case 8: // '\b'
				return dischargeEnergy && dischargeRoom;

			case 9: // '\t'
				return !dischargeRoom;
			}
		} else
		if (tile instanceof TileEntityBaseGenerator)
		{
			TileEntityBaseGenerator teb = (TileEntityBaseGenerator)tile;
			boolean hasEnergy = teb.storage > 0;
			boolean hasRoom = teb.storage < teb.maxStorage;
			boolean chargeEnergy = canDischarge(teb.chargeSlot.get());
			boolean chargeRoom = canCharge(teb.chargeSlot.get());
			switch (action)
			{
			case 0: // '\0'
				return !hasEnergy;

			case 1: // '\001'
				return hasEnergy;

			case 2: // '\002'
				return hasRoom;

			case 3: // '\003'
				return !hasRoom;

			case 4: // '\004'
				return !chargeEnergy;

			case 5: // '\005'
				return chargeEnergy && chargeRoom;

			case 6: // '\006'
				return !chargeRoom;
			}
		} else
		if (tile instanceof TileEntityElectricBlock)
		{
			TileEntityElectricBlock teb = (TileEntityElectricBlock)tile;
			boolean hasEnergy = teb.energy >= teb.output;
			boolean hasRoom = teb.energy < teb.maxStorage;
			boolean chargeEnergy = canDischarge(teb.chargeSlot.get());
			boolean chargeRoom = canCharge(teb.chargeSlot.get());
			boolean dischargeEnergy = canDischarge(teb.dischargeSlot.get());
			boolean dischargeRoom = canCharge(teb.dischargeSlot.get());
			switch (action)
			{
			case 0: // '\0'
				return !hasEnergy;

			case 1: // '\001'
				return hasEnergy;

			case 2: // '\002'
				return hasRoom;

			case 3: // '\003'
				return !hasRoom;

			case 4: // '\004'
				return !chargeEnergy;

			case 5: // '\005'
				return chargeEnergy && chargeRoom;

			case 6: // '\006'
				return !chargeRoom;

			case 7: // '\007'
				return !dischargeEnergy;

			case 8: // '\b'
				return dischargeEnergy && dischargeRoom;

			case 9: // '\t'
				return !dischargeRoom;
			}
		}
		return false;
	}

	private IElectricItem getElectricItem(ItemStack itemStack)
	{
		if (itemStack == null)
			return null;
		Item item = itemStack.getItem();
		if (item instanceof IElectricItem)
			return (IElectricItem)item;
		else
			return null;
	}

	private boolean canDischarge(ItemStack itemStack)
	{
		IElectricItem electricItem = getElectricItem(itemStack);
		if (electricItem == null)
			return false;
		else
			return ElectricItem.manager.getCharge(itemStack) > 0;
	}

	private boolean canCharge(ItemStack itemStack)
	{
		IElectricItem electricItem = getElectricItem(itemStack);
		if (electricItem == null)
			return false;
		else
			return ElectricItem.manager.charge(itemStack, 0x7fffffff, 0x7fffffff, true, true) > 0;
	}
}
