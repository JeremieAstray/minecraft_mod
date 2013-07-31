// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SubModule.java

package ic2.bcIntegration;

import buildcraft.api.fuels.IronEngineCoolant;
import buildcraft.api.gates.ActionManager;
import buildcraft.api.gates.ITriggerProvider;
import buildcraft.api.transport.IPipe;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.bcIntegration.core.TileEntityNuclearReactorSteam;
import ic2.bcIntegration.core.TileEntityPersonalChestBc32x;
import ic2.bcIntegration.core.TileEntityReactorChamberSteam;
import ic2.bcIntegration.core.TriggerCapacitor;
import ic2.bcIntegration.core.TriggerEnergyFlow;
import ic2.bcIntegration.core.TriggerFuel;
import ic2.bcIntegration.core.TriggerHeat;
import ic2.bcIntegration.core.TriggerIconProvider;
import ic2.bcIntegration.core.TriggerScrap;
import ic2.bcIntegration.core.TriggerWork;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import ic2.core.block.generator.block.BlockGenerator;
import ic2.core.block.generator.block.BlockReactorChamber;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.machine.tileentity.*;
import ic2.core.block.personal.BlockPersonal;
import ic2.core.block.wiring.TileEntityCableDetector;
import ic2.core.block.wiring.TileEntityElectricBlock;
import java.util.LinkedList;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

public class SubModule
	implements ITriggerProvider
{

	public static TriggerCapacitor triggerCapacitorEmpty;
	public static TriggerCapacitor triggerCapacitorHasEnergy;
	public static TriggerCapacitor triggerCapacitorHasRoom;
	public static TriggerCapacitor triggerCapacitorFull;
	public static TriggerCapacitor triggerChargeEmpty;
	public static TriggerCapacitor triggerChargePartial;
	public static TriggerCapacitor triggerChargeFull;
	public static TriggerCapacitor triggerDischargeEmpty;
	public static TriggerCapacitor triggerDischargePartial;
	public static TriggerCapacitor triggerDischargeFull;
	public static TriggerWork triggerWorking;
	public static TriggerWork triggerNotWorking;
	public static TriggerEnergyFlow triggerEnergyFlowing;
	public static TriggerEnergyFlow triggerEnergyNotFlowing;
	public static TriggerFuel triggerHasFuel;
	public static TriggerFuel triggerNoFuel;
	public static TriggerScrap triggerHasScrap;
	public static TriggerScrap triggerNoScrap;
	public static TriggerHeat triggerFullHeat;
	public static TriggerHeat triggerNoFullHeat;
	public static TriggerIconProvider iconProvider;

	public SubModule()
	{
	}

	public static boolean init()
	{
		BlockPersonal.tileEntityPersonalChestClass = ic2/bcIntegration/core/TileEntityPersonalChestBc32x;
		ModLoader.registerTileEntity(ic2/bcIntegration/core/TileEntityPersonalChestBc32x, "Personal Safe");
		IronEngineCoolant.coolants.add(new IronEngineCoolant(new FluidStack(Ic2Items.coolant.itemID, 1000), 5F));
		iconProvider = new TriggerIconProvider();
		triggerCapacitorEmpty = new TriggerCapacitor(71, 0);
		triggerCapacitorHasEnergy = new TriggerCapacitor(72, 1);
		triggerCapacitorHasRoom = new TriggerCapacitor(73, 2);
		triggerCapacitorFull = new TriggerCapacitor(74, 3);
		triggerChargeEmpty = new TriggerCapacitor(68, 4);
		triggerChargePartial = new TriggerCapacitor(70, 5);
		triggerChargeFull = new TriggerCapacitor(69, 6);
		triggerDischargeEmpty = new TriggerCapacitor(65, 7);
		triggerDischargePartial = new TriggerCapacitor(67, 8);
		triggerDischargeFull = new TriggerCapacitor(66, 9);
		triggerWorking = new TriggerWork(75, true);
		triggerNotWorking = new TriggerWork(76, false);
		triggerEnergyFlowing = new TriggerEnergyFlow(112, true);
		triggerEnergyNotFlowing = new TriggerEnergyFlow(113, false);
		triggerHasFuel = new TriggerFuel(114, true);
		triggerNoFuel = new TriggerFuel(115, false);
		triggerHasScrap = new TriggerScrap(116, false);
		triggerNoScrap = new TriggerScrap(117, true);
		triggerFullHeat = new TriggerHeat(118, true);
		triggerNoFullHeat = new TriggerHeat(119, false);
		ActionManager.registerTriggerProvider(new SubModule());
		FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", (new StringBuilder()).append(Ic2Items.bronzeBlock.itemID).append("@").append(Ic2Items.bronzeBlock.getItemDamage()).toString());
		FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", (new StringBuilder()).append(Ic2Items.copperBlock.itemID).append("@").append(Ic2Items.copperBlock.getItemDamage()).toString());
		FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", (new StringBuilder()).append(Ic2Items.tinBlock.itemID).append("@").append(Ic2Items.tinBlock.getItemDamage()).toString());
		FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", (new StringBuilder()).append(Ic2Items.uraniumBlock.itemID).append("@").append(Ic2Items.uraniumBlock.getItemDamage()).toString());
		if (IC2.enableSteamReactor)
		{
			BlockGenerator.tileEntityNuclearReactorClass = ic2/bcIntegration/core/TileEntityNuclearReactorSteam;
			BlockReactorChamber.tileEntityReactorChamberClass = ic2/bcIntegration/core/TileEntityReactorChamberSteam;
			GameRegistry.registerTileEntity(ic2/bcIntegration/core/TileEntityNuclearReactorSteam, "Nuclear Reactor");
			GameRegistry.registerTileEntity(ic2/bcIntegration/core/TileEntityReactorChamberSteam, "Reactor Chamber");
		}
		return true;
	}

	public LinkedList getPipeTriggers(IPipe pipe)
	{
		return null;
	}

	public LinkedList getNeighborTriggers(Block block, TileEntity tile)
	{
		LinkedList temp = new LinkedList();
		if ((tile instanceof TileEntityStandardMachine) || (tile instanceof TileEntityBaseGenerator) || (tile instanceof TileEntityElectricBlock))
		{
			temp.add(triggerCapacitorEmpty);
			temp.add(triggerCapacitorHasEnergy);
			temp.add(triggerCapacitorHasRoom);
			temp.add(triggerCapacitorFull);
		}
		if ((tile instanceof TileEntityBaseGenerator) || (tile instanceof TileEntityElectricBlock))
		{
			temp.add(triggerChargeEmpty);
			temp.add(triggerChargePartial);
			temp.add(triggerChargeFull);
		}
		if ((tile instanceof TileEntityStandardMachine) || (tile instanceof TileEntityElectricBlock))
		{
			temp.add(triggerDischargeEmpty);
			temp.add(triggerDischargePartial);
			temp.add(triggerDischargeFull);
		}
		if ((tile instanceof TileEntityStandardMachine) || (tile instanceof TileEntityBaseGenerator))
		{
			temp.add(triggerWorking);
			temp.add(triggerNotWorking);
		}
		if (tile instanceof TileEntityBaseGenerator)
		{
			temp.add(triggerHasFuel);
			temp.add(triggerNoFuel);
		}
		if (tile instanceof TileEntityCableDetector)
		{
			temp.add(triggerEnergyFlowing);
			temp.add(triggerEnergyNotFlowing);
		}
		if (tile instanceof TileEntityMatter)
		{
			temp.add(triggerHasScrap);
			temp.add(triggerNoScrap);
		}
		if (tile instanceof TileEntityInduction)
		{
			temp.add(triggerFullHeat);
			temp.add(triggerNoFullHeat);
		}
		return temp;
	}
}
