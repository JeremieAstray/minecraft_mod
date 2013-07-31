// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NEIIC2Config.java

package ic2.neiIntegration.core;

import codechicken.nei.MultiItemRange;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.forge.GuiContainerManager;
import ic2.core.Ic2Items;
import ic2.core.block.machine.gui.*;
import java.io.PrintStream;
import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.neiIntegration.core:
//			AdvRecipeHandler, AdvShapelessRecipeHandler, GradualRecipeHandler, MaceratorRecipeHandler, 
//			ExtractorRecipeHandler, CompressorRecipeHandler, ScrapboxRecipeHandler, ChargeTooltipHandler

public class NEIIC2Config
	implements IConfigureNEI
{

	public NEIIC2Config()
	{
	}

	public void loadConfig()
	{
		System.out.println("IC2 NEI Submodule initialized");
		API.registerRecipeHandler(new AdvRecipeHandler());
		API.registerUsageHandler(new AdvRecipeHandler());
		API.registerRecipeHandler(new AdvShapelessRecipeHandler());
		API.registerUsageHandler(new AdvShapelessRecipeHandler());
		API.registerRecipeHandler(new GradualRecipeHandler());
		API.registerUsageHandler(new GradualRecipeHandler());
		API.registerRecipeHandler(new MaceratorRecipeHandler());
		API.registerUsageHandler(new MaceratorRecipeHandler());
		API.registerRecipeHandler(new ExtractorRecipeHandler());
		API.registerUsageHandler(new ExtractorRecipeHandler());
		API.registerRecipeHandler(new CompressorRecipeHandler());
		API.registerUsageHandler(new CompressorRecipeHandler());
		API.registerUsageHandler(new ScrapboxRecipeHandler());
		API.registerRecipeHandler(new ScrapboxRecipeHandler());
		API.registerGuiOverlay(ic2/core/block/machine/gui/GuiMacerator, "macerator", 5, 11);
		API.registerGuiOverlay(ic2/core/block/machine/gui/GuiExtractor, "extractor", 5, 11);
		API.registerGuiOverlay(ic2/core/block/machine/gui/GuiCompressor, "compressor", 5, 11);
		API.registerGuiOverlay(ic2/core/block/machine/gui/GuiIronFurnace, "smelting", 5, 11);
		API.registerGuiOverlay(ic2/core/block/machine/gui/GuiElecFurnace, "smelting", 5, 11);
		API.registerGuiOverlay(ic2/core/block/machine/gui/GuiInduction, "smelting", -4, 11);
		GuiContainerManager.addTooltipHandler(new ChargeTooltipHandler());
		addSubsets();
	}

	public void addSubsets()
	{
		addSubset("IC2.Armor.Bronze", new Object[] {
			Integer.valueOf(Ic2Items.bronzeHelmet.itemID), Integer.valueOf(Ic2Items.bronzeChestplate.itemID), Integer.valueOf(Ic2Items.bronzeLeggings.itemID), Integer.valueOf(Ic2Items.bronzeBoots.itemID)
		});
		addSubset("IC2.Armor.Nano", new Object[] {
			Integer.valueOf(Ic2Items.nanoHelmet.itemID), Integer.valueOf(Ic2Items.nanoBodyarmor.itemID), Integer.valueOf(Ic2Items.nanoLeggings.itemID), Integer.valueOf(Ic2Items.nanoBoots.itemID)
		});
		addSubset("IC2.Armor.Quantum", new Object[] {
			Integer.valueOf(Ic2Items.quantumHelmet.itemID), Integer.valueOf(Ic2Items.quantumBodyarmor.itemID), Integer.valueOf(Ic2Items.quantumLeggings.itemID), Integer.valueOf(Ic2Items.quantumBoots.itemID)
		});
		addSubset("IC2.Armor.Hazmat", new Object[] {
			Integer.valueOf(Ic2Items.hazmatHelmet.itemID), Integer.valueOf(Ic2Items.hazmatChestplate.itemID), Integer.valueOf(Ic2Items.hazmatLeggings.itemID), Integer.valueOf(Ic2Items.hazmatBoots.itemID)
		});
		addSubset("IC2.Armor.Utility", new Object[] {
			Integer.valueOf(Ic2Items.batPack.itemID), Integer.valueOf(Ic2Items.cfPack.itemID), Integer.valueOf(Ic2Items.compositeArmor.itemID), Integer.valueOf(Ic2Items.electricJetpack.itemID), Integer.valueOf(Ic2Items.jetpack.itemID), Integer.valueOf(Ic2Items.lapPack.itemID), Integer.valueOf(Ic2Items.solarHelmet.itemID), Integer.valueOf(Ic2Items.staticBoots.itemID)
		});
		addSubset("IC2.Wiring", new Object[] {
			Ic2Items.copperCableItem, Ic2Items.insulatedCopperCableItem, Ic2Items.goldCableItem, Ic2Items.insulatedGoldCableItem, Ic2Items.doubleInsulatedGoldCableItem, Ic2Items.ironCableItem, Ic2Items.insulatedIronCableItem, Ic2Items.doubleInsulatedIronCableItem, Ic2Items.trippleInsulatedIronCableItem, Ic2Items.glassFiberCableItem, 
			Ic2Items.tinCableItem, Ic2Items.detectorCableItem, Ic2Items.splitterCableItem, Ic2Items.lvTransformer, Ic2Items.mvTransformer, Ic2Items.hvTransformer
		});
		addSubset("IC2.EU Storage", new Object[] {
			Ic2Items.batBox, Ic2Items.mfeUnit, Ic2Items.mfsUnit, Integer.valueOf(Ic2Items.electrolyzedWaterCell.itemID), Integer.valueOf(Ic2Items.energyCrystal.itemID), Integer.valueOf(Ic2Items.lapotronCrystal.itemID), Integer.valueOf(Ic2Items.suBattery.itemID), Integer.valueOf(Ic2Items.reBattery.itemID), Integer.valueOf(Ic2Items.chargedReBattery.itemID)
		});
		addSubset("IC2.Machines.Generators", new Object[] {
			Ic2Items.generator, Ic2Items.geothermalGenerator, Ic2Items.nuclearReactor, Ic2Items.solarPanel, Ic2Items.windMill, Ic2Items.waterMill
		});
		addSubset("IC2.Machines.Gatherers", new Object[] {
			Ic2Items.miner, Ic2Items.pump
		});
		addSubset("IC2.Machines.Processors", new Object[] {
			Ic2Items.canner, Ic2Items.compressor, Ic2Items.electroFurnace, Ic2Items.extractor, Ic2Items.inductionFurnace, Ic2Items.ironFurnace, Ic2Items.macerator, Ic2Items.massFabricator, Ic2Items.recycler
		});
		addSubset("IC2.Machines.Utility", new Object[] {
			Ic2Items.electrolyzer, Ic2Items.magnetizer, Ic2Items.personalSafe, Ic2Items.teleporter, Ic2Items.terraformer, Ic2Items.teslaCoil, Ic2Items.tradeOMat, Ic2Items.energyOMat
		});
		addSubset("IC2.Tools.Electric", new Object[] {
			Integer.valueOf(Ic2Items.chainsaw.itemID), Integer.valueOf(Ic2Items.electricTreetap.itemID), Integer.valueOf(Ic2Items.electricWrench.itemID), Integer.valueOf(Ic2Items.electricHoe.itemID), Integer.valueOf(Ic2Items.diamondDrill.itemID), Integer.valueOf(Ic2Items.miningDrill.itemID), Integer.valueOf(Ic2Items.miningLaser.itemID), Integer.valueOf(Ic2Items.odScanner.itemID), Integer.valueOf(Ic2Items.ovScanner.itemID)
		});
		addSubset("IC2.Tools.Bronze", new Object[] {
			Integer.valueOf(Ic2Items.bronzeAxe.itemID), Integer.valueOf(Ic2Items.bronzeHoe.itemID), Integer.valueOf(Ic2Items.bronzeShovel.itemID), Integer.valueOf(Ic2Items.bronzePickaxe.itemID)
		});
		addSubset("IC2.Tools.Utility", new Object[] {
			Integer.valueOf(Ic2Items.ecMeter.itemID), Integer.valueOf(Ic2Items.wrench.itemID), Integer.valueOf(Ic2Items.treetap.itemID), Integer.valueOf(Ic2Items.cutter.itemID), Integer.valueOf(Ic2Items.constructionFoamSprayer.itemID)
		});
	}

	private transient void addSubset(String name, Object items[])
	{
		MultiItemRange multiitemrange = new MultiItemRange();
		Object arr$[] = items;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Object item = arr$[i$];
			if (item instanceof ItemStack)
				multiitemrange.add((ItemStack)item);
			else
				multiitemrange.add(((Integer)item).intValue());
		}

		API.addSetRange(name, multiitemrange);
	}

	public String getName()
	{
		return "IndustrialCraft 2";
	}

	public String getVersion()
	{
		return "1.118.401-lf";
	}
}
