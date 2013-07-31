// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Recipies.java

package ic2.core.init;

import ic2.api.recipe.ICraftingRecipeManager;
import ic2.api.recipe.Recipes;
import ic2.core.*;
import ic2.core.item.ItemGradual;
import ic2.core.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Recipies
{

	public Recipies()
	{
	}

	public static void registerCraftingRecipes()
	{
		ICraftingRecipeManager advRecipes = Recipes.advRecipes = new AdvCraftingRecipeManager();
		advRecipes.addRecipe(Ic2Items.copperBlock, new Object[] {
			"MMM", "MMM", "MMM", Character.valueOf('M'), "ingotCopper"
		});
		advRecipes.addRecipe(Ic2Items.bronzeBlock, new Object[] {
			"MMM", "MMM", "MMM", Character.valueOf('M'), "ingotBronze"
		});
		advRecipes.addRecipe(Ic2Items.tinBlock, new Object[] {
			"MMM", "MMM", "MMM", Character.valueOf('M'), "ingotTin"
		});
		advRecipes.addRecipe(Ic2Items.uraniumBlock, new Object[] {
			"MMM", "MMM", "MMM", Character.valueOf('M'), "ingotUranium"
		});
		advRecipes.addRecipe(Ic2Items.ironFurnace, new Object[] {
			"III", "I I", "III", Character.valueOf('I'), Item.ingotIron
		});
		advRecipes.addRecipe(Ic2Items.ironFurnace, new Object[] {
			" I ", "I I", "IFI", Character.valueOf('I'), Item.ingotIron, Character.valueOf('F'), Block.furnaceIdle
		});
		advRecipes.addRecipe(Ic2Items.electroFurnace, new Object[] {
			" C ", "RFR", Character.valueOf('C'), Ic2Items.electronicCircuit, Character.valueOf('R'), Item.redstone, Character.valueOf('F'), Ic2Items.ironFurnace
		});
		advRecipes.addRecipe(Ic2Items.macerator, new Object[] {
			"FFF", "SMS", " C ", Character.valueOf('F'), Item.flint, Character.valueOf('S'), Block.cobblestone, Character.valueOf('M'), Ic2Items.machine, Character.valueOf('C'), 
			Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.extractor, new Object[] {
			"TMT", "TCT", Character.valueOf('T'), Ic2Items.treetap, Character.valueOf('M'), Ic2Items.machine, Character.valueOf('C'), Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.compressor, new Object[] {
			"S S", "SMS", "SCS", Character.valueOf('S'), Block.stone, Character.valueOf('M'), Ic2Items.machine, Character.valueOf('C'), Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.miner, new Object[] {
			"CMC", " P ", " P ", Character.valueOf('P'), Ic2Items.miningPipe, Character.valueOf('M'), Ic2Items.machine, Character.valueOf('C'), Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.pump, new Object[] {
			"cCc", "cMc", "PTP", Character.valueOf('c'), Ic2Items.cell, Character.valueOf('T'), Ic2Items.treetap, Character.valueOf('P'), Ic2Items.miningPipe, Character.valueOf('M'), 
			Ic2Items.machine, Character.valueOf('C'), Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.magnetizer, new Object[] {
			"RFR", "RMR", "RFR", Character.valueOf('R'), Item.redstone, Character.valueOf('F'), Ic2Items.ironFence, Character.valueOf('M'), Ic2Items.machine
		});
		advRecipes.addRecipe(Ic2Items.electrolyzer, new Object[] {
			"c c", "cCc", "EME", Character.valueOf('E'), Ic2Items.cell, Character.valueOf('c'), Ic2Items.insulatedCopperCableItem, Character.valueOf('M'), Ic2Items.machine, Character.valueOf('C'), 
			Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.advancedMachine, new Object[] {
			" A ", "CMC", " A ", Character.valueOf('A'), Ic2Items.advancedAlloy, Character.valueOf('C'), Ic2Items.carbonPlate, Character.valueOf('M'), Ic2Items.machine
		});
		advRecipes.addRecipe(Ic2Items.advancedMachine, new Object[] {
			" C ", "AMA", " C ", Character.valueOf('A'), Ic2Items.advancedAlloy, Character.valueOf('C'), Ic2Items.carbonPlate, Character.valueOf('M'), Ic2Items.machine
		});
		advRecipes.addRecipe(Ic2Items.personalSafe, new Object[] {
			"c", "M", "C", Character.valueOf('c'), Ic2Items.electronicCircuit, Character.valueOf('C'), Block.chest, Character.valueOf('M'), Ic2Items.machine
		});
		advRecipes.addRecipe(Ic2Items.tradeOMat, new Object[] {
			"RRR", "CMC", Character.valueOf('R'), Item.redstone, Character.valueOf('C'), Block.chest, Character.valueOf('M'), Ic2Items.machine
		});
		advRecipes.addRecipe(Ic2Items.energyOMat, new Object[] {
			"RBR", "CMC", Character.valueOf('R'), Item.redstone, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem, Character.valueOf('M'), Ic2Items.machine, Character.valueOf('B'), Ic2Items.reBattery
		});
		advRecipes.addRecipe(Ic2Items.massFabricator, new Object[] {
			"GCG", "ALA", "GCG", Character.valueOf('A'), Ic2Items.advancedMachine, Character.valueOf('L'), Ic2Items.lapotronCrystal, Character.valueOf('G'), Item.glowstone, Character.valueOf('C'), 
			Ic2Items.advancedCircuit
		});
		advRecipes.addRecipe(Ic2Items.terraformer, new Object[] {
			"GTG", "DMD", "GDG", Character.valueOf('T'), Ic2Items.terraformerBlueprint, Character.valueOf('G'), Item.glowstone, Character.valueOf('D'), Block.dirt, Character.valueOf('M'), 
			Ic2Items.advancedMachine
		});
		advRecipes.addRecipe(Ic2Items.teleporter, new Object[] {
			"GFG", "CMC", "GDG", Character.valueOf('M'), Ic2Items.advancedMachine, Character.valueOf('C'), Ic2Items.glassFiberCableItem, Character.valueOf('F'), Ic2Items.frequencyTransmitter, Character.valueOf('G'), 
			Ic2Items.advancedCircuit, Character.valueOf('D'), Ic2Items.industrialDiamond
		});
		advRecipes.addRecipe(Ic2Items.teleporter, new Object[] {
			"GFG", "CMC", "GDG", Character.valueOf('M'), Ic2Items.advancedMachine, Character.valueOf('C'), Ic2Items.glassFiberCableItem, Character.valueOf('F'), Ic2Items.frequencyTransmitter, Character.valueOf('G'), 
			Ic2Items.advancedCircuit, Character.valueOf('D'), Item.diamond
		});
		advRecipes.addRecipe(Ic2Items.inductionFurnace, new Object[] {
			"CCC", "CFC", "CMC", Character.valueOf('C'), "ingotCopper", Character.valueOf('F'), Ic2Items.electroFurnace, Character.valueOf('M'), Ic2Items.advancedMachine
		});
		advRecipes.addRecipe(Ic2Items.machine, new Object[] {
			"III", "I I", "III", Character.valueOf('I'), "ingotRefinedIron"
		});
		advRecipes.addRecipe(Ic2Items.recycler, new Object[] {
			" G ", "DMD", "IDI", Character.valueOf('D'), Block.dirt, Character.valueOf('G'), Item.glowstone, Character.valueOf('M'), Ic2Items.compressor, Character.valueOf('I'), 
			"ingotRefinedIron"
		});
		advRecipes.addRecipe(Ic2Items.canner, new Object[] {
			"TCT", "TMT", "TTT", Character.valueOf('T'), "ingotTin", Character.valueOf('M'), Ic2Items.machine, Character.valueOf('C'), Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.teslaCoil, new Object[] {
			"RRR", "RMR", "ICI", Character.valueOf('M'), Ic2Items.mvTransformer, Character.valueOf('R'), Item.redstone, Character.valueOf('C'), Ic2Items.electronicCircuit, Character.valueOf('I'), 
			"ingotRefinedIron"
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.luminator, 8), new Object[] {
			"ICI", "GTG", "GGG", Character.valueOf('G'), Block.glass, Character.valueOf('I'), "ingotRefinedIron", Character.valueOf('T'), Ic2Items.tinCableItem, Character.valueOf('C'), 
			Ic2Items.insulatedCopperCableItem
		});
		advRecipes.addRecipe(Ic2Items.generator, new Object[] {
			" B ", "III", " F ", Character.valueOf('B'), Ic2Items.reBattery, Character.valueOf('F'), Ic2Items.ironFurnace, Character.valueOf('I'), "ingotRefinedIron"
		});
		advRecipes.addRecipe(Ic2Items.generator, new Object[] {
			" B ", "III", " F ", Character.valueOf('B'), Ic2Items.chargedReBattery, Character.valueOf('F'), Ic2Items.ironFurnace, Character.valueOf('I'), "ingotRefinedIron"
		});
		advRecipes.addRecipe(Ic2Items.generator, new Object[] {
			"B", "M", "F", Character.valueOf('B'), Ic2Items.reBattery, Character.valueOf('F'), Block.furnaceIdle, Character.valueOf('M'), Ic2Items.machine
		});
		advRecipes.addRecipe(Ic2Items.generator, new Object[] {
			"B", "M", "F", Character.valueOf('B'), Ic2Items.chargedReBattery, Character.valueOf('F'), Block.furnaceIdle, Character.valueOf('M'), Ic2Items.machine
		});
		advRecipes.addRecipe(Ic2Items.reactorChamber, new Object[] {
			" C ", "CMC", " C ", Character.valueOf('C'), Ic2Items.denseCopperPlate, Character.valueOf('M'), Ic2Items.machine
		});
		if (IC2.energyGeneratorWater > 0)
			advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.waterMill, 2), new Object[] {
				"SPS", "PGP", "SPS", Character.valueOf('S'), "stickWood", Character.valueOf('P'), "plankWood", Character.valueOf('G'), Ic2Items.generator
			});
		if (IC2.energyGeneratorSolar > 0)
			advRecipes.addRecipe(Ic2Items.solarPanel, new Object[] {
				"CgC", "gCg", "cGc", Character.valueOf('G'), Ic2Items.generator, Character.valueOf('C'), "dustCoal", Character.valueOf('g'), Block.glass, Character.valueOf('c'), 
				Ic2Items.electronicCircuit
			});
		if (IC2.energyGeneratorWind > 0)
			advRecipes.addRecipe(Ic2Items.windMill, new Object[] {
				"I I", " G ", "I I", Character.valueOf('I'), Item.ingotIron, Character.valueOf('G'), Ic2Items.generator
			});
		if (IC2.energyGeneratorNuclear > 0)
			advRecipes.addRecipe(Ic2Items.nuclearReactor, new Object[] {
				" c ", "CCC", " G ", Character.valueOf('C'), Ic2Items.reactorChamber, Character.valueOf('c'), Ic2Items.advancedCircuit, Character.valueOf('G'), Ic2Items.generator
			});
		if (IC2.energyGeneratorGeo > 0)
			advRecipes.addRecipe(Ic2Items.geothermalGenerator, new Object[] {
				"gCg", "gCg", "IGI", Character.valueOf('G'), Ic2Items.generator, Character.valueOf('C'), Ic2Items.cell, Character.valueOf('g'), Block.glass, Character.valueOf('I'), 
				"ingotRefinedIron"
			});
		advRecipes.addShapelessRecipe(Ic2Items.reactorUraniumSimple, new Object[] {
			Ic2Items.reEnrichedUraniumCell, "dustCoal"
		});
		advRecipes.addShapelessRecipe(new ItemStack(Ic2Items.reactorIsotopeCell.itemID, 1, 9999), new Object[] {
			Ic2Items.nearDepletedUraniumCell, "dustCoal"
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.nearDepletedUraniumCell, 8), new Object[] {
			"CCC", "CUC", "CCC", Character.valueOf('C'), Ic2Items.cell, Character.valueOf('U'), "ingotUranium"
		});
		advRecipes.addShapelessRecipe(Ic2Items.reactorUraniumSimple, new Object[] {
			Ic2Items.cell, "ingotUranium"
		});
		advRecipes.addRecipe(Ic2Items.reactorUraniumDual, new Object[] {
			"UCU", Character.valueOf('U'), Ic2Items.reactorUraniumSimple, Character.valueOf('C'), Ic2Items.denseCopperPlate
		});
		advRecipes.addRecipe(Ic2Items.reactorUraniumQuad, new Object[] {
			" U ", "CCC", " U ", Character.valueOf('U'), Ic2Items.reactorUraniumDual, Character.valueOf('C'), Ic2Items.denseCopperPlate
		});
		advRecipes.addRecipe(Ic2Items.reactorCoolantSimple, new Object[] {
			" T ", "TWT", " T ", Character.valueOf('W'), "liquid$water", Character.valueOf('T'), "ingotTin"
		});
		advRecipes.addRecipe(Ic2Items.reactorCoolantTriple, new Object[] {
			"TTT", "CCC", "TTT", Character.valueOf('C'), Ic2Items.reactorCoolantSimple, Character.valueOf('T'), "ingotTin"
		});
		advRecipes.addRecipe(Ic2Items.reactorCoolantSix, new Object[] {
			"TCT", "TcT", "TCT", Character.valueOf('C'), Ic2Items.reactorCoolantTriple, Character.valueOf('T'), "ingotTin", Character.valueOf('c'), Ic2Items.denseCopperPlate
		});
		advRecipes.addShapelessRecipe(Ic2Items.reactorPlating, new Object[] {
			Ic2Items.denseCopperPlate, Ic2Items.advancedAlloy
		});
		advRecipes.addShapelessRecipe(Ic2Items.reactorPlatingHeat, new Object[] {
			Ic2Items.reactorPlating, Ic2Items.denseCopperPlate, Ic2Items.denseCopperPlate
		});
		advRecipes.addShapelessRecipe(Ic2Items.reactorPlatingExplosive, new Object[] {
			Ic2Items.reactorPlating, Ic2Items.advancedAlloy, Ic2Items.advancedAlloy
		});
		advRecipes.addRecipe(Ic2Items.reactorHeatSwitch, new Object[] {
			" c ", "TCT", " T ", Character.valueOf('c'), Ic2Items.electronicCircuit, Character.valueOf('T'), "ingotTin", Character.valueOf('C'), Ic2Items.denseCopperPlate
		});
		advRecipes.addRecipe(Ic2Items.reactorHeatSwitchCore, new Object[] {
			"C", "S", "C", Character.valueOf('S'), Ic2Items.reactorHeatSwitch, Character.valueOf('C'), Ic2Items.denseCopperPlate
		});
		advRecipes.addRecipe(Ic2Items.reactorHeatSwitchSpread, new Object[] {
			" G ", "GSG", " G ", Character.valueOf('S'), Ic2Items.reactorHeatSwitch, Character.valueOf('G'), Item.ingotGold
		});
		advRecipes.addRecipe(Ic2Items.reactorHeatSwitchDiamond, new Object[] {
			"GcG", "SCS", "GcG", Character.valueOf('S'), Ic2Items.reactorHeatSwitch, Character.valueOf('C'), Ic2Items.denseCopperPlate, Character.valueOf('G'), Ic2Items.glassFiberCableItem, Character.valueOf('c'), 
			Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.reactorVent, new Object[] {
			"I#I", "# #", "I#I", Character.valueOf('I'), Ic2Items.refinedIronIngot, Character.valueOf('#'), Block.fenceIron
		});
		advRecipes.addRecipe(Ic2Items.reactorVentCore, new Object[] {
			"C", "V", "C", Character.valueOf('V'), Ic2Items.reactorVent, Character.valueOf('C'), Ic2Items.denseCopperPlate
		});
		advRecipes.addRecipe(Ic2Items.reactorVentGold, new Object[] {
			"G", "V", "G", Character.valueOf('V'), Ic2Items.reactorVentCore, Character.valueOf('G'), Item.ingotGold
		});
		advRecipes.addRecipe(Ic2Items.reactorVentSpread, new Object[] {
			"#T#", "TVT", "#T#", Character.valueOf('V'), Ic2Items.reactorVent, Character.valueOf('#'), Block.fenceIron, Character.valueOf('T'), "ingotTin"
		});
		advRecipes.addRecipe(Ic2Items.reactorVentDiamond, new Object[] {
			"#V#", "#D#", "#V#", Character.valueOf('V'), Ic2Items.reactorVent, Character.valueOf('#'), Block.fenceIron, Character.valueOf('D'), Item.diamond
		});
		advRecipes.addRecipe(Ic2Items.reactorHeatpack, new Object[] {
			"c", "L", "C", Character.valueOf('c'), Ic2Items.electronicCircuit, Character.valueOf('C'), Ic2Items.denseCopperPlate, Character.valueOf('L'), Ic2Items.lavaCell
		});
		advRecipes.addRecipe(Ic2Items.reactorReflector, new Object[] {
			"TcT", "cCc", "TcT", Character.valueOf('c'), "dustCoal", Character.valueOf('C'), Ic2Items.denseCopperPlate, Character.valueOf('T'), "dustTin"
		});
		advRecipes.addRecipe(Ic2Items.reactorReflectorThick, new Object[] {
			" R ", "RCR", " R ", Character.valueOf('C'), Ic2Items.denseCopperPlate, Character.valueOf('R'), Ic2Items.reactorReflector
		});
		advRecipes.addRecipe(Ic2Items.reactorCondensator, new Object[] {
			"RRR", "RVR", "RSR", Character.valueOf('R'), Item.redstone, Character.valueOf('V'), Ic2Items.reactorVent, Character.valueOf('S'), Ic2Items.reactorHeatSwitch
		});
		new RecipeGradual((ItemGradual)Ic2Items.reactorCondensator.getItem(), new ItemStack(Item.redstone), 10000);
		advRecipes.addRecipe(Ic2Items.reactorCondensatorLap, new Object[] {
			"RVR", "CLC", "RSR", Character.valueOf('R'), Item.redstone, Character.valueOf('V'), Ic2Items.reactorVentCore, Character.valueOf('S'), Ic2Items.reactorHeatSwitchCore, Character.valueOf('C'), 
			Ic2Items.reactorCondensator, Character.valueOf('L'), Block.blockLapis
		});
		new RecipeGradual((ItemGradual)Ic2Items.reactorCondensatorLap.getItem(), new ItemStack(Item.redstone), 5000);
		new RecipeGradual((ItemGradual)Ic2Items.reactorCondensatorLap.getItem(), new ItemStack(Item.dyePowder, 1, 4), 40000);
		advRecipes.addRecipe(Ic2Items.batBox, new Object[] {
			"PCP", "BBB", "PPP", Character.valueOf('P'), "plankWood", Character.valueOf('C'), Ic2Items.insulatedCopperCableItem, Character.valueOf('B'), Ic2Items.reBattery
		});
		advRecipes.addRecipe(Ic2Items.batBox, new Object[] {
			"PCP", "BBB", "PPP", Character.valueOf('P'), "plankWood", Character.valueOf('C'), Ic2Items.insulatedCopperCableItem, Character.valueOf('B'), Ic2Items.chargedReBattery
		});
		advRecipes.addRecipe(Ic2Items.mfeUnit, new Object[] {
			"cCc", "CMC", "cCc", Character.valueOf('M'), Ic2Items.machine, Character.valueOf('c'), Ic2Items.doubleInsulatedGoldCableItem, Character.valueOf('C'), Ic2Items.energyCrystal
		});
		advRecipes.addRecipe(Ic2Items.mfsUnit, new Object[] {
			"LCL", "LML", "LAL", Character.valueOf('M'), Ic2Items.mfeUnit, Character.valueOf('A'), Ic2Items.advancedMachine, Character.valueOf('C'), Ic2Items.advancedCircuit, Character.valueOf('L'), 
			Ic2Items.lapotronCrystal
		});
		advRecipes.addRecipe(Ic2Items.lvTransformer, new Object[] {
			"PCP", "ccc", "PCP", Character.valueOf('P'), "plankWood", Character.valueOf('C'), Ic2Items.insulatedCopperCableItem, Character.valueOf('c'), "ingotCopper"
		});
		advRecipes.addRecipe(Ic2Items.mvTransformer, new Object[] {
			" C ", " M ", " C ", Character.valueOf('M'), Ic2Items.machine, Character.valueOf('C'), Ic2Items.doubleInsulatedGoldCableItem
		});
		advRecipes.addRecipe(Ic2Items.hvTransformer, new Object[] {
			" c ", "CED", " c ", Character.valueOf('E'), Ic2Items.mvTransformer, Character.valueOf('c'), Ic2Items.trippleInsulatedIronCableItem, Character.valueOf('D'), Ic2Items.energyCrystal, Character.valueOf('C'), 
			Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.reinforcedStone, 8), new Object[] {
			"SSS", "SAS", "SSS", Character.valueOf('S'), Block.stone, Character.valueOf('A'), Ic2Items.advancedAlloy
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.reinforcedGlass, 7), new Object[] {
			"GAG", "GGG", "GAG", Character.valueOf('G'), Block.glass, Character.valueOf('A'), Ic2Items.advancedAlloy
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.reinforcedGlass, 7), new Object[] {
			"GGG", "AGA", "GGG", Character.valueOf('G'), Block.glass, Character.valueOf('A'), Ic2Items.advancedAlloy
		});
		advRecipes.addRecipe(Ic2Items.remote, new Object[] {
			" c ", "GCG", "TTT", Character.valueOf('c'), Ic2Items.insulatedCopperCableItem, Character.valueOf('G'), Item.glowstone, Character.valueOf('C'), Ic2Items.electronicCircuit, Character.valueOf('T'), 
			Block.tnt
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.rubberTrampoline, 3), new Object[] {
			"RRR", "RRR", Character.valueOf('R'), "itemRubber"
		});
		advRecipes.addRecipe(new ItemStack(Block.torchWood, 4), new Object[] {
			"R", "I", Character.valueOf('I'), "stickWood", Character.valueOf('R'), Ic2Items.resin, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.scaffold, 16), new Object[] {
			"PPP", " s ", "s s", Character.valueOf('P'), "plankWood", Character.valueOf('s'), "stickWood"
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.ironScaffold, 16), new Object[] {
			"PPP", " s ", "s s", Character.valueOf('P'), "ingotRefinedIron", Character.valueOf('s'), Ic2Items.ironFence.getItem()
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.ironFence, 12), new Object[] {
			"III", "III", Character.valueOf('I'), "ingotRefinedIron"
		});
		if (IC2.enableCraftingITnt)
		{
			advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.industrialTnt, 4), new Object[] {
				"FFF", "TTT", "FFF", Character.valueOf('F'), Item.flint, Character.valueOf('T'), Block.tnt
			});
			advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.industrialTnt, 4), new Object[] {
				"FTF", "FTF", "FTF", Character.valueOf('F'), Item.flint, Character.valueOf('T'), Block.tnt
			});
		}
		if (IC2.enableCraftingNuke)
			advRecipes.addRecipe(Ic2Items.nuke, new Object[] {
				"RCR", "UMU", "RCR", Character.valueOf('R'), Ic2Items.reEnrichedUraniumCell, Character.valueOf('C'), Ic2Items.advancedCircuit, Character.valueOf('U'), "blockUranium", Character.valueOf('M'), 
				Ic2Items.advancedMachine
			});
		advRecipes.addRecipe(new ItemStack(Block.stone, 16), new Object[] {
			"   ", " M ", "   ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Block.glass, 32), new Object[] {
			" M ", "M M", " M ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Block.grass, 16), new Object[] {
			"   ", "M  ", "M  ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Block.cobblestoneMossy, 16), new Object[] {
			"   ", " M ", "M M", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Block.sandStone, 16), new Object[] {
			"   ", "  M", " M ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Block.blockSnow, 4), new Object[] {
			"M M", "   ", "   ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Block.waterStill, 1), new Object[] {
			"   ", " M ", " M ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Block.lavaStill, 1), new Object[] {
			" M ", " M ", " M ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Block.oreIron, 2), new Object[] {
			"M M", " M ", "M M", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Block.oreGold, 2), new Object[] {
			" M ", "MMM", " M ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Block.obsidian, 12), new Object[] {
			"M M", "M M", "   ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Block.netherrack, 16), new Object[] {
			"  M", " M ", "M  ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Block.glowStone, 8), new Object[] {
			" M ", "M M", "MMM", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Block.wood, 8), new Object[] {
			" M ", "   ", "   ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Block.cactus, 48), new Object[] {
			" M ", "MMM", "M M", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Block.vine, 24), new Object[] {
			"M  ", "M  ", "M  ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Block.cloth, 12), new Object[] {
			"M M", "   ", " M ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Item.coal, 20), new Object[] {
			"  M", "M  ", "  M", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Item.diamond, 1), new Object[] {
			"MMM", "MMM", "MMM", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Item.redstone, 24), new Object[] {
			"   ", " M ", "MMM", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Item.dyePowder, 9, 4), new Object[] {
			" M ", " M ", " MM", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Item.feather, 32), new Object[] {
			" M ", " M ", "M M", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Item.snowball, 16), new Object[] {
			"   ", "   ", "MMM", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Item.gunpowder, 15), new Object[] {
			"MMM", "M  ", "MMM", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Item.clay, 48), new Object[] {
			"MM ", "M  ", "MM ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Item.dyePowder, 32, 3), new Object[] {
			"MM ", "  M", "MM ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Item.dyePowder, 48, 0), new Object[] {
			" MM", " MM", " M ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Item.reed, 48), new Object[] {
			"M M", "M M", "M M", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Item.flint, 32), new Object[] {
			" M ", "MM ", "MM ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Item.bone, 32), new Object[] {
			"M  ", "MM ", "M  ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.resin, 21), new Object[] {
			"M M", "   ", "M M", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.iridiumOre, 1), new Object[] {
			"MMM", " M ", "MMM", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Block.mycelium, 24), new Object[] {
			"   ", "M M", "MMM", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Block.stoneBrick, 48, 3), new Object[] {
			"MM ", "MM ", "M  ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
		});
		if (Ic2Items.copperOre != null)
			advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.copperOre, 5), new Object[] {
				"  M", "M M", "   ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
			});
		else
			advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.copperDust, 10), new Object[] {
				"  M", "M M", "   ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
			});
		if (Ic2Items.tinOre != null)
			advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.tinOre, 5), new Object[] {
				"   ", "M M", "  M", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
			});
		else
			advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.tinDust, 10), new Object[] {
				"   ", "M M", "  M", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true)
			});
		if (Ic2Items.rubberWood != null)
			advRecipes.addRecipe(new ItemStack(Block.planks, 3, 3), new Object[] {
				"W", Character.valueOf('W'), Ic2Items.rubberWood
			});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.insulatedCopperCableItem, 6), new Object[] {
			"RRR", "CCC", "RRR", Character.valueOf('C'), "ingotCopper", Character.valueOf('R'), "itemRubber"
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.insulatedCopperCableItem, 6), new Object[] {
			"RCR", "RCR", "RCR", Character.valueOf('C'), "ingotCopper", Character.valueOf('R'), "itemRubber"
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.copperCableItem, 6), new Object[] {
			"CCC", Character.valueOf('C'), "ingotCopper"
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.goldCableItem, 12), new Object[] {
			"GGG", Character.valueOf('G'), Item.ingotGold
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.insulatedGoldCableItem, 4), new Object[] {
			" R ", "RGR", " R ", Character.valueOf('G'), Item.ingotGold, Character.valueOf('R'), "itemRubber"
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.glassFiberCableItem, 4), new Object[] {
			"GGG", "RDR", "GGG", Character.valueOf('G'), Block.glass, Character.valueOf('R'), Item.redstone, Character.valueOf('D'), Item.diamond
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.glassFiberCableItem, 4), new Object[] {
			"GGG", "RDR", "GGG", Character.valueOf('G'), Block.glass, Character.valueOf('R'), Item.redstone, Character.valueOf('D'), Ic2Items.industrialDiamond
		});
		advRecipes.addRecipe(Ic2Items.detectorCableItem, new Object[] {
			" C ", "RIR", " R ", Character.valueOf('R'), Item.redstone, Character.valueOf('I'), Ic2Items.trippleInsulatedIronCableItem, Character.valueOf('C'), Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.splitterCableItem, new Object[] {
			" R ", "ILI", " R ", Character.valueOf('R'), Item.redstone, Character.valueOf('I'), Ic2Items.trippleInsulatedIronCableItem, Character.valueOf('L'), Block.lever
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.ironCableItem, 12), new Object[] {
			"III", Character.valueOf('I'), "ingotRefinedIron"
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.insulatedIronCableItem, 4), new Object[] {
			" R ", "RIR", " R ", Character.valueOf('I'), "ingotRefinedIron", Character.valueOf('R'), "itemRubber"
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.glassFiberCableItem, 6), new Object[] {
			"GGG", "SDS", "GGG", Character.valueOf('G'), Block.glass, Character.valueOf('S'), "ingotSilver", Character.valueOf('R'), Item.redstone, Character.valueOf('D'), 
			Item.diamond
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.glassFiberCableItem, 6), new Object[] {
			"GGG", "SDS", "GGG", Character.valueOf('G'), Block.glass, Character.valueOf('S'), "ingotSilver", Character.valueOf('R'), Item.redstone, Character.valueOf('D'), 
			Ic2Items.industrialDiamond
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.tinCableItem, 9), new Object[] {
			"TTT", Character.valueOf('T'), "ingotTin"
		});
		advRecipes.addShapelessRecipe(Ic2Items.insulatedCopperCableItem, new Object[] {
			"itemRubber", Ic2Items.copperCableItem
		});
		advRecipes.addShapelessRecipe(Ic2Items.insulatedGoldCableItem, new Object[] {
			"itemRubber", Ic2Items.goldCableItem
		});
		advRecipes.addShapelessRecipe(Ic2Items.doubleInsulatedGoldCableItem, new Object[] {
			"itemRubber", Ic2Items.insulatedGoldCableItem
		});
		advRecipes.addShapelessRecipe(Ic2Items.doubleInsulatedGoldCableItem, new Object[] {
			"itemRubber", "itemRubber", Ic2Items.goldCableItem
		});
		advRecipes.addShapelessRecipe(Ic2Items.insulatedIronCableItem, new Object[] {
			"itemRubber", Ic2Items.ironCableItem
		});
		advRecipes.addShapelessRecipe(Ic2Items.doubleInsulatedIronCableItem, new Object[] {
			"itemRubber", Ic2Items.insulatedIronCableItem
		});
		advRecipes.addShapelessRecipe(Ic2Items.trippleInsulatedIronCableItem, new Object[] {
			"itemRubber", Ic2Items.doubleInsulatedIronCableItem
		});
		advRecipes.addShapelessRecipe(Ic2Items.trippleInsulatedIronCableItem, new Object[] {
			"itemRubber", "itemRubber", Ic2Items.insulatedIronCableItem
		});
		advRecipes.addShapelessRecipe(Ic2Items.doubleInsulatedIronCableItem, new Object[] {
			"itemRubber", "itemRubber", Ic2Items.ironCableItem
		});
		advRecipes.addShapelessRecipe(Ic2Items.trippleInsulatedIronCableItem, new Object[] {
			"itemRubber", "itemRubber", "itemRubber", Ic2Items.ironCableItem
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.suBattery, 5), new Object[] {
			"C", "R", "D", Character.valueOf('D'), "dustCoal", Character.valueOf('R'), Item.redstone, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.suBattery, 5), new Object[] {
			"C", "D", "R", Character.valueOf('D'), "dustCoal", Character.valueOf('R'), Item.redstone, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.suBattery, 8), new Object[] {
			"c", "C", "R", Character.valueOf('R'), Item.redstone, Character.valueOf('C'), Ic2Items.hydratedCoalDust, Character.valueOf('c'), Ic2Items.insulatedCopperCableItem
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.suBattery, 8), new Object[] {
			"c", "R", "C", Character.valueOf('R'), Item.redstone, Character.valueOf('C'), Ic2Items.hydratedCoalDust, Character.valueOf('c'), Ic2Items.insulatedCopperCableItem
		});
		advRecipes.addRecipe(Ic2Items.reBattery, new Object[] {
			" C ", "TRT", "TRT", Character.valueOf('T'), "ingotTin", Character.valueOf('R'), Item.redstone, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem
		});
		advRecipes.addRecipe(Ic2Items.energyCrystal, new Object[] {
			"RRR", "RDR", "RRR", Character.valueOf('D'), Item.diamond, Character.valueOf('R'), Item.redstone
		});
		advRecipes.addRecipe(Ic2Items.energyCrystal, new Object[] {
			"RRR", "RDR", "RRR", Character.valueOf('D'), Ic2Items.industrialDiamond, Character.valueOf('R'), Item.redstone
		});
		advRecipes.addRecipe(Ic2Items.lapotronCrystal, new Object[] {
			"LCL", "LDL", "LCL", Character.valueOf('D'), Ic2Items.energyCrystal, Character.valueOf('C'), Ic2Items.electronicCircuit, Character.valueOf('L'), new ItemStack(Item.dyePowder, 1, 4)
		});
		advRecipes.addRecipe(Ic2Items.treetap, new Object[] {
			" P ", "PPP", "P  ", Character.valueOf('P'), "plankWood"
		});
		advRecipes.addRecipe(Ic2Items.painter, new Object[] {
			" CC", " IC", "I  ", Character.valueOf('C'), Block.cloth, Character.valueOf('I'), Item.ingotIron
		});
		advRecipes.addRecipe(new ItemStack(Item.pickaxeDiamond, 1), new Object[] {
			"DDD", " S ", " S ", Character.valueOf('S'), "stickWood", Character.valueOf('D'), Ic2Items.industrialDiamond
		});
		advRecipes.addRecipe(new ItemStack(Item.hoeDiamond, 1), new Object[] {
			"DD ", " S ", " S ", Character.valueOf('S'), "stickWood", Character.valueOf('D'), Ic2Items.industrialDiamond
		});
		advRecipes.addRecipe(new ItemStack(Item.shovelDiamond, 1), new Object[] {
			"D", "S", "S", Character.valueOf('S'), "stickWood", Character.valueOf('D'), Ic2Items.industrialDiamond
		});
		advRecipes.addRecipe(new ItemStack(Item.axeDiamond, 1), new Object[] {
			"DD ", "DS ", " S ", Character.valueOf('S'), "stickWood", Character.valueOf('D'), Ic2Items.industrialDiamond
		});
		advRecipes.addRecipe(new ItemStack(Item.swordDiamond, 1), new Object[] {
			"D", "D", "S", Character.valueOf('S'), "stickWood", Character.valueOf('D'), Ic2Items.industrialDiamond
		});
		advRecipes.addRecipe(new ItemStack(Ic2Items.constructionFoamSprayer.itemID, 1, 1601), new Object[] {
			"SS ", "Ss ", "  S", Character.valueOf('S'), Block.cobblestone, Character.valueOf('s'), "stickWood"
		});
		new RecipeGradual((ItemGradual)Ic2Items.constructionFoamSprayer.getItem(), Ic2Items.constructionFoamPellet, 100);
		advRecipes.addRecipe(Ic2Items.bronzePickaxe, new Object[] {
			"BBB", " S ", " S ", Character.valueOf('B'), "ingotBronze", Character.valueOf('S'), "stickWood"
		});
		advRecipes.addRecipe(Ic2Items.bronzeAxe, new Object[] {
			"BB", "SB", "S ", Character.valueOf('B'), "ingotBronze", Character.valueOf('S'), "stickWood"
		});
		advRecipes.addRecipe(Ic2Items.bronzeHoe, new Object[] {
			"BB", "S ", "S ", Character.valueOf('B'), "ingotBronze", Character.valueOf('S'), "stickWood"
		});
		advRecipes.addRecipe(Ic2Items.bronzeSword, new Object[] {
			"B", "B", "S", Character.valueOf('B'), "ingotBronze", Character.valueOf('S'), "stickWood"
		});
		advRecipes.addRecipe(Ic2Items.bronzeShovel, new Object[] {
			" B ", " S ", " S ", Character.valueOf('B'), "ingotBronze", Character.valueOf('S'), "stickWood"
		});
		advRecipes.addRecipe(Ic2Items.wrench, new Object[] {
			"B B", "BBB", " B ", Character.valueOf('B'), "ingotBronze"
		});
		advRecipes.addRecipe(Ic2Items.cutter, new Object[] {
			"A A", " A ", "I I", Character.valueOf('A'), "ingotRefinedIron", Character.valueOf('I'), Item.ingotIron
		});
		advRecipes.addRecipe(Ic2Items.toolbox, new Object[] {
			"ICI", "III", Character.valueOf('C'), Block.chest, Character.valueOf('I'), "ingotRefinedIron"
		});
		advRecipes.addRecipe(Ic2Items.miningDrill, new Object[] {
			" I ", "ICI", "IBI", Character.valueOf('I'), "ingotRefinedIron", Character.valueOf('B'), Ic2Items.reBattery, Character.valueOf('C'), Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.miningDrill, new Object[] {
			" I ", "ICI", "IBI", Character.valueOf('I'), "ingotRefinedIron", Character.valueOf('B'), Ic2Items.chargedReBattery, Character.valueOf('C'), Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.chainsaw, new Object[] {
			" II", "ICI", "BI ", Character.valueOf('I'), "ingotRefinedIron", Character.valueOf('B'), Ic2Items.reBattery, Character.valueOf('C'), Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.chainsaw, new Object[] {
			" II", "ICI", "BI ", Character.valueOf('I'), "ingotRefinedIron", Character.valueOf('B'), Ic2Items.chargedReBattery, Character.valueOf('C'), Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.diamondDrill, new Object[] {
			" D ", "DdD", Character.valueOf('D'), Item.diamond, Character.valueOf('d'), Ic2Items.miningDrill
		});
		advRecipes.addRecipe(Ic2Items.diamondDrill, new Object[] {
			" D ", "DdD", Character.valueOf('D'), Item.diamond, Character.valueOf('d'), Ic2Items.miningDrill
		});
		advRecipes.addRecipe(Ic2Items.odScanner, new Object[] {
			" G ", "CBC", "ccc", Character.valueOf('B'), Ic2Items.reBattery, Character.valueOf('c'), Ic2Items.insulatedCopperCableItem, Character.valueOf('G'), Item.glowstone, Character.valueOf('C'), 
			Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.odScanner, new Object[] {
			" G ", "CBC", "ccc", Character.valueOf('B'), Ic2Items.chargedReBattery, Character.valueOf('c'), Ic2Items.insulatedCopperCableItem, Character.valueOf('G'), Item.glowstone, Character.valueOf('C'), 
			Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.ovScanner, new Object[] {
			" G ", "GCG", "cSc", Character.valueOf('S'), Ic2Items.odScanner, Character.valueOf('c'), Ic2Items.doubleInsulatedGoldCableItem, Character.valueOf('G'), Item.glowstone, Character.valueOf('C'), 
			Ic2Items.advancedCircuit
		});
		advRecipes.addRecipe(Ic2Items.ovScanner, new Object[] {
			" G ", "GCG", "cSc", Character.valueOf('S'), Ic2Items.chargedReBattery, Character.valueOf('c'), Ic2Items.doubleInsulatedGoldCableItem, Character.valueOf('G'), Item.glowstone, Character.valueOf('C'), 
			Ic2Items.advancedCircuit
		});
		advRecipes.addRecipe(Ic2Items.obscurator, new Object[] {
			"rEr", "CAC", "rrr", Character.valueOf('r'), Item.redstone, Character.valueOf('E'), Ic2Items.energyCrystal, Character.valueOf('C'), Ic2Items.doubleInsulatedGoldCableItem, Character.valueOf('A'), 
			Ic2Items.advancedCircuit
		});
		advRecipes.addRecipe(Ic2Items.electricWrench, new Object[] {
			"  W", " C ", "B  ", Character.valueOf('W'), Ic2Items.wrench, Character.valueOf('B'), Ic2Items.reBattery, Character.valueOf('C'), Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.electricWrench, new Object[] {
			"  W", " C ", "B  ", Character.valueOf('W'), Ic2Items.wrench, Character.valueOf('B'), Ic2Items.chargedReBattery, Character.valueOf('C'), Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.electricTreetap, new Object[] {
			"  W", " C ", "B  ", Character.valueOf('W'), Ic2Items.treetap, Character.valueOf('B'), Ic2Items.reBattery, Character.valueOf('C'), Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.electricTreetap, new Object[] {
			"  W", " C ", "B  ", Character.valueOf('W'), Ic2Items.treetap, Character.valueOf('B'), Ic2Items.chargedReBattery, Character.valueOf('C'), Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.ecMeter, new Object[] {
			" G ", "cCc", "c c", Character.valueOf('G'), Item.glowstone, Character.valueOf('c'), Ic2Items.insulatedCopperCableItem, Character.valueOf('C'), Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.miningLaser, new Object[] {
			"Rcc", "AAC", " AA", Character.valueOf('A'), Ic2Items.advancedAlloy, Character.valueOf('C'), Ic2Items.advancedCircuit, Character.valueOf('c'), Ic2Items.energyCrystal, Character.valueOf('R'), 
			Item.redstone
		});
		advRecipes.addRecipe(Ic2Items.nanoSaber, new Object[] {
			"GA ", "GA ", "CcC", Character.valueOf('C'), Ic2Items.carbonPlate, Character.valueOf('c'), Ic2Items.energyCrystal, Character.valueOf('G'), Item.glowstone, Character.valueOf('A'), 
			Ic2Items.advancedAlloy
		});
		advRecipes.addRecipe(Ic2Items.electricHoe, new Object[] {
			"II ", " C ", " B ", Character.valueOf('I'), "ingotRefinedIron", Character.valueOf('B'), Ic2Items.reBattery, Character.valueOf('C'), Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.electricHoe, new Object[] {
			"II ", " C ", " B ", Character.valueOf('I'), "ingotRefinedIron", Character.valueOf('B'), Ic2Items.chargedReBattery, Character.valueOf('C'), Ic2Items.electronicCircuit
		});
		advRecipes.addShapelessRecipe(Ic2Items.frequencyTransmitter, new Object[] {
			Ic2Items.electronicCircuit, Ic2Items.insulatedCopperCableItem
		});
		advRecipes.addRecipe(Ic2Items.advancedCircuit, new Object[] {
			"RGR", "LCL", "RGR", Character.valueOf('L'), new ItemStack(Item.dyePowder, 1, 4), Character.valueOf('G'), Item.glowstone, Character.valueOf('R'), Item.redstone, Character.valueOf('C'), 
			Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.advancedCircuit, new Object[] {
			"RLR", "GCG", "RLR", Character.valueOf('L'), new ItemStack(Item.dyePowder, 1, 4), Character.valueOf('G'), Item.glowstone, Character.valueOf('R'), Item.redstone, Character.valueOf('C'), 
			Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.plantBall, new Object[] {
			"PPP", "P P", "PPP", Character.valueOf('P'), Item.wheat
		});
		advRecipes.addRecipe(Ic2Items.plantBall, new Object[] {
			"PPP", "P P", "PPP", Character.valueOf('P'), Item.reed
		});
		advRecipes.addRecipe(Ic2Items.plantBall, new Object[] {
			"PPP", "P P", "PPP", Character.valueOf('P'), Item.seeds
		});
		advRecipes.addRecipe(Ic2Items.plantBall, new Object[] {
			"PPP", "P P", "PPP", Character.valueOf('P'), "treeLeaves"
		});
		if (Ic2Items.rubberLeaves != null)
			advRecipes.addRecipe(Ic2Items.plantBall, new Object[] {
				"PPP", "P P", "PPP", Character.valueOf('P'), Ic2Items.rubberLeaves
			});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.plantBall, 2), new Object[] {
			"PPP", "P P", "PPP", Character.valueOf('P'), "treeSapling"
		});
		if (Ic2Items.rubberSapling != null)
			advRecipes.addRecipe(Ic2Items.plantBall, new Object[] {
				"PPP", "P P", "PPP", Character.valueOf('P'), Ic2Items.rubberSapling
			});
		advRecipes.addRecipe(Ic2Items.plantBall, new Object[] {
			"PPP", "P P", "PPP", Character.valueOf('P'), Block.tallGrass
		});
		advRecipes.addRecipe(Ic2Items.carbonFiber, new Object[] {
			"CC", "CC", Character.valueOf('C'), "dustCoal"
		});
		advRecipes.addRecipe(Ic2Items.iridiumPlate, new Object[] {
			"IAI", "ADA", "IAI", Character.valueOf('I'), Ic2Items.iridiumOre, Character.valueOf('A'), Ic2Items.advancedAlloy, Character.valueOf('D'), Item.diamond
		});
		advRecipes.addRecipe(Ic2Items.iridiumPlate, new Object[] {
			"IAI", "ADA", "IAI", Character.valueOf('I'), Ic2Items.iridiumOre, Character.valueOf('A'), Ic2Items.advancedAlloy, Character.valueOf('D'), Ic2Items.industrialDiamond
		});
		advRecipes.addRecipe(Ic2Items.coalBall, new Object[] {
			"CCC", "CFC", "CCC", Character.valueOf('C'), "dustCoal", Character.valueOf('F'), Item.flint
		});
		advRecipes.addRecipe(Ic2Items.coalChunk, new Object[] {
			"###", "#O#", "###", Character.valueOf('#'), Ic2Items.compressedCoalBall, Character.valueOf('O'), Block.obsidian
		});
		advRecipes.addRecipe(Ic2Items.coalChunk, new Object[] {
			"###", "#O#", "###", Character.valueOf('#'), Ic2Items.compressedCoalBall, Character.valueOf('O'), Block.blockIron, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(Ic2Items.coalChunk, new Object[] {
			"###", "#O#", "###", Character.valueOf('#'), Ic2Items.compressedCoalBall, Character.valueOf('O'), Block.brick, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(Ic2Items.smallIronDust, new Object[] {
			"CTC", "TCT", "CTC", Character.valueOf('C'), "dustCopper", Character.valueOf('T'), "dustTin", Boolean.valueOf(true)
		});
		advRecipes.addRecipe(Ic2Items.smallIronDust, new Object[] {
			"TCT", "CTC", "TCT", Character.valueOf('C'), "dustCopper", Character.valueOf('T'), "dustTin", Boolean.valueOf(true)
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.hydratedCoalDust, 8), new Object[] {
			"CCC", "CWC", "CCC", Character.valueOf('C'), "dustCoal", Character.valueOf('W'), "liquid$water"
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.refinedIronIngot, 8), new Object[] {
			"M", Character.valueOf('M'), Ic2Items.machine
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.copperIngot, 9), new Object[] {
			"B", Character.valueOf('B'), Ic2Items.copperBlock
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.tinIngot, 9), new Object[] {
			"B", Character.valueOf('B'), Ic2Items.tinBlock
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.bronzeIngot, 9), new Object[] {
			"B", Character.valueOf('B'), Ic2Items.bronzeBlock
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.uraniumIngot, 9), new Object[] {
			"B", Character.valueOf('B'), Ic2Items.uraniumBlock
		});
		advRecipes.addRecipe(Ic2Items.electronicCircuit, new Object[] {
			"CCC", "RIR", "CCC", Character.valueOf('I'), "ingotRefinedIron", Character.valueOf('R'), Item.redstone, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem
		});
		advRecipes.addRecipe(Ic2Items.electronicCircuit, new Object[] {
			"CRC", "CIC", "CRC", Character.valueOf('I'), "ingotRefinedIron", Character.valueOf('R'), Item.redstone, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem
		});
		advRecipes.addRecipe(Ic2Items.compositeArmor, new Object[] {
			"A A", "ALA", "AIA", Character.valueOf('L'), Item.plateLeather, Character.valueOf('I'), Item.plateIron, Character.valueOf('A'), Ic2Items.advancedAlloy
		});
		advRecipes.addRecipe(Ic2Items.compositeArmor, new Object[] {
			"A A", "AIA", "ALA", Character.valueOf('L'), Item.plateLeather, Character.valueOf('I'), Item.plateIron, Character.valueOf('A'), Ic2Items.advancedAlloy
		});
		advRecipes.addRecipe(Ic2Items.nanoHelmet, new Object[] {
			"CcC", "CGC", Character.valueOf('C'), Ic2Items.carbonPlate, Character.valueOf('c'), Ic2Items.energyCrystal, Character.valueOf('G'), Block.glass
		});
		advRecipes.addRecipe(Ic2Items.nanoBodyarmor, new Object[] {
			"C C", "CcC", "CCC", Character.valueOf('C'), Ic2Items.carbonPlate, Character.valueOf('c'), Ic2Items.energyCrystal
		});
		advRecipes.addRecipe(Ic2Items.nanoLeggings, new Object[] {
			"CcC", "C C", "C C", Character.valueOf('C'), Ic2Items.carbonPlate, Character.valueOf('c'), Ic2Items.energyCrystal
		});
		advRecipes.addRecipe(Ic2Items.nanoBoots, new Object[] {
			"C C", "CcC", Character.valueOf('C'), Ic2Items.carbonPlate, Character.valueOf('c'), Ic2Items.energyCrystal
		});
		advRecipes.addRecipe(Ic2Items.quantumHelmet, new Object[] {
			" n ", "ILI", "CGC", Character.valueOf('n'), Ic2Items.nanoHelmet, Character.valueOf('I'), Ic2Items.iridiumPlate, Character.valueOf('L'), Ic2Items.lapotronCrystal, Character.valueOf('G'), 
			Ic2Items.reinforcedGlass, Character.valueOf('C'), Ic2Items.advancedCircuit
		});
		advRecipes.addRecipe(Ic2Items.quantumBodyarmor, new Object[] {
			"AnA", "ILI", "IAI", Character.valueOf('n'), Ic2Items.nanoBodyarmor, Character.valueOf('I'), Ic2Items.iridiumPlate, Character.valueOf('L'), Ic2Items.lapotronCrystal, Character.valueOf('A'), 
			Ic2Items.advancedAlloy
		});
		advRecipes.addRecipe(Ic2Items.quantumLeggings, new Object[] {
			"MLM", "InI", "G G", Character.valueOf('n'), Ic2Items.nanoLeggings, Character.valueOf('I'), Ic2Items.iridiumPlate, Character.valueOf('L'), Ic2Items.lapotronCrystal, Character.valueOf('G'), 
			Item.glowstone, Character.valueOf('M'), Ic2Items.machine
		});
		advRecipes.addRecipe(Ic2Items.quantumBoots, new Object[] {
			"InI", "RLR", Character.valueOf('n'), Ic2Items.nanoBoots, Character.valueOf('I'), Ic2Items.iridiumPlate, Character.valueOf('L'), Ic2Items.lapotronCrystal, Character.valueOf('R'), Ic2Items.hazmatBoots
		});
		advRecipes.addRecipe(Ic2Items.hazmatHelmet, new Object[] {
			" O ", "RGR", "R#R", Character.valueOf('R'), "itemRubber", Character.valueOf('G'), Block.glass, Character.valueOf('#'), Block.fenceIron, Character.valueOf('O'), 
			new ItemStack(Item.dyePowder, 1, 14)
		});
		advRecipes.addRecipe(Ic2Items.hazmatChestplate, new Object[] {
			"R R", "ROR", "ROR", Character.valueOf('R'), "itemRubber", Character.valueOf('O'), new ItemStack(Item.dyePowder, 1, 14)
		});
		advRecipes.addRecipe(Ic2Items.hazmatLeggings, new Object[] {
			"ROR", "R R", "R R", Character.valueOf('R'), "itemRubber", Character.valueOf('O'), new ItemStack(Item.dyePowder, 1, 14)
		});
		advRecipes.addRecipe(Ic2Items.hazmatBoots, new Object[] {
			"R R", "R R", "RWR", Character.valueOf('R'), "itemRubber", Character.valueOf('W'), Block.cloth
		});
		advRecipes.addRecipe(Ic2Items.batPack, new Object[] {
			"BCB", "BTB", "B B", Character.valueOf('T'), "ingotTin", Character.valueOf('C'), Ic2Items.electronicCircuit, Character.valueOf('B'), Ic2Items.chargedReBattery
		});
		advRecipes.addRecipe(Ic2Items.batPack, new Object[] {
			"BCB", "BTB", "B B", Character.valueOf('T'), "ingotTin", Character.valueOf('C'), Ic2Items.electronicCircuit, Character.valueOf('B'), Ic2Items.reBattery
		});
		advRecipes.addRecipe(Ic2Items.lapPack, new Object[] {
			"LAL", "LBL", "L L", Character.valueOf('L'), Block.blockLapis, Character.valueOf('A'), Ic2Items.advancedCircuit, Character.valueOf('B'), Ic2Items.batPack
		});
		advRecipes.addRecipe(Ic2Items.solarHelmet, new Object[] {
			"III", "ISI", "CCC", Character.valueOf('I'), Item.ingotIron, Character.valueOf('S'), Ic2Items.solarPanel, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem
		});
		advRecipes.addRecipe(Ic2Items.solarHelmet, new Object[] {
			" H ", " S ", "CCC", Character.valueOf('H'), Item.helmetIron, Character.valueOf('S'), Ic2Items.solarPanel, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem
		});
		advRecipes.addRecipe(Ic2Items.staticBoots, new Object[] {
			"I I", "ISI", "CCC", Character.valueOf('I'), Item.ingotIron, Character.valueOf('S'), Block.cloth, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem
		});
		advRecipes.addRecipe(Ic2Items.staticBoots, new Object[] {
			" H ", " S ", "CCC", Character.valueOf('H'), Item.bootsIron, Character.valueOf('S'), Block.cloth, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem
		});
		advRecipes.addRecipe(Ic2Items.nightvisionGoggles, new Object[] {
			"X@X", "LGL", "RCR", Character.valueOf('X'), Ic2Items.reactorHeatSwitchDiamond, Character.valueOf('@'), Ic2Items.nanoHelmet, Character.valueOf('L'), Ic2Items.luminator, Character.valueOf('G'), 
			Ic2Items.reinforcedGlass, Character.valueOf('R'), "itemRubber", Character.valueOf('C'), Ic2Items.advancedCircuit
		});
		advRecipes.addRecipe(Ic2Items.bronzeHelmet, new Object[] {
			"BBB", "B B", Character.valueOf('B'), "ingotBronze"
		});
		advRecipes.addRecipe(Ic2Items.bronzeChestplate, new Object[] {
			"B B", "BBB", "BBB", Character.valueOf('B'), "ingotBronze"
		});
		advRecipes.addRecipe(Ic2Items.bronzeLeggings, new Object[] {
			"BBB", "B B", "B B", Character.valueOf('B'), "ingotBronze"
		});
		advRecipes.addRecipe(Ic2Items.bronzeBoots, new Object[] {
			"B B", "B B", Character.valueOf('B'), "ingotBronze"
		});
		advRecipes.addRecipe(new ItemStack(Ic2Items.jetpack.itemID, 1, 18001), new Object[] {
			"ICI", "IFI", "R R", Character.valueOf('I'), "ingotRefinedIron", Character.valueOf('C'), Ic2Items.electronicCircuit, Character.valueOf('F'), Ic2Items.fuelCan, Character.valueOf('R'), 
			Item.redstone
		});
		advRecipes.addRecipe(Ic2Items.electricJetpack, new Object[] {
			"ICI", "IBI", "G G", Character.valueOf('I'), "ingotRefinedIron", Character.valueOf('C'), Ic2Items.advancedCircuit, Character.valueOf('B'), Ic2Items.batBox, Character.valueOf('G'), 
			Item.glowstone
		});
		advRecipes.addRecipe(Ic2Items.terraformerBlueprint, new Object[] {
			" C ", " A ", "R R", Character.valueOf('C'), Ic2Items.electronicCircuit, Character.valueOf('A'), Ic2Items.advancedCircuit, Character.valueOf('R'), Item.redstone
		});
		advRecipes.addRecipe(Ic2Items.cultivationTerraformerBlueprint, new Object[] {
			" S ", "S#S", " S ", Character.valueOf('#'), Ic2Items.terraformerBlueprint, Character.valueOf('S'), Item.seeds
		});
		advRecipes.addRecipe(Ic2Items.desertificationTerraformerBlueprint, new Object[] {
			" S ", "S#S", " S ", Character.valueOf('#'), Ic2Items.terraformerBlueprint, Character.valueOf('S'), Block.sand
		});
		advRecipes.addRecipe(Ic2Items.irrigationTerraformerBlueprint, new Object[] {
			" W ", "W#W", " W ", Character.valueOf('#'), Ic2Items.terraformerBlueprint, Character.valueOf('W'), Item.bucketWater
		});
		advRecipes.addRecipe(Ic2Items.chillingTerraformerBlueprint, new Object[] {
			" S ", "S#S", " S ", Character.valueOf('#'), Ic2Items.terraformerBlueprint, Character.valueOf('S'), Item.snowball
		});
		advRecipes.addRecipe(Ic2Items.flatificatorTerraformerBlueprint, new Object[] {
			" D ", "D#D", " D ", Character.valueOf('#'), Ic2Items.terraformerBlueprint, Character.valueOf('D'), Block.dirt
		});
		advRecipes.addRecipe(Ic2Items.mushroomTerraformerBlueprint, new Object[] {
			"mMm", "M#M", "mMm", Character.valueOf('#'), Ic2Items.terraformerBlueprint, Character.valueOf('M'), Block.mushroomBrown, Character.valueOf('m'), Block.mycelium
		});
		advRecipes.addShapelessRecipe(Ic2Items.terraformerBlueprint, new Object[] {
			Ic2Items.cultivationTerraformerBlueprint
		});
		advRecipes.addShapelessRecipe(Ic2Items.terraformerBlueprint, new Object[] {
			Ic2Items.irrigationTerraformerBlueprint
		});
		advRecipes.addShapelessRecipe(Ic2Items.terraformerBlueprint, new Object[] {
			Ic2Items.chillingTerraformerBlueprint
		});
		advRecipes.addShapelessRecipe(Ic2Items.terraformerBlueprint, new Object[] {
			Ic2Items.desertificationTerraformerBlueprint
		});
		advRecipes.addShapelessRecipe(Ic2Items.terraformerBlueprint, new Object[] {
			Ic2Items.flatificatorTerraformerBlueprint
		});
		advRecipes.addRecipe(Ic2Items.overclockerUpgrade, new Object[] {
			"CCC", "WEW", Character.valueOf('C'), Ic2Items.reactorCoolantSimple, Character.valueOf('W'), Ic2Items.insulatedCopperCableItem, Character.valueOf('E'), Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.transformerUpgrade, new Object[] {
			"GGG", "WTW", "GEG", Character.valueOf('G'), Block.glass, Character.valueOf('W'), Ic2Items.doubleInsulatedGoldCableItem, Character.valueOf('T'), Ic2Items.mvTransformer, Character.valueOf('E'), 
			Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.energyStorageUpgrade, new Object[] {
			"www", "WBW", "wEw", Character.valueOf('w'), Block.planks, Character.valueOf('W'), Ic2Items.insulatedCopperCableItem, Character.valueOf('B'), Ic2Items.reBattery, Character.valueOf('E'), 
			Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.energyStorageUpgrade, new Object[] {
			"www", "WBW", "wEw", Character.valueOf('w'), Block.planks, Character.valueOf('W'), Ic2Items.insulatedCopperCableItem, Character.valueOf('B'), Ic2Items.chargedReBattery, Character.valueOf('E'), 
			Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.ejectorUpgrade, new Object[] {
			"PHP", "WEW", Character.valueOf('P'), Block.pistonBase, Character.valueOf('H'), Block.hopperBlock, Character.valueOf('W'), Ic2Items.insulatedCopperCableItem, Character.valueOf('E'), Ic2Items.electronicCircuit
		});
		advRecipes.addRecipe(Ic2Items.boatCarbon, new Object[] {
			"X X", "XXX", Character.valueOf('X'), Ic2Items.carbonPlate
		});
		advRecipes.addRecipe(Ic2Items.boatRubber, new Object[] {
			"X X", "XXX", Character.valueOf('X'), "itemRubber"
		});
		advRecipes.addShapelessRecipe(Ic2Items.boatRubber, new Object[] {
			Ic2Items.boatRubberBroken, "itemRubber"
		});
		advRecipes.addRecipe(Ic2Items.boatElectric, new Object[] {
			"CCC", "X@X", "XXX", Character.valueOf('X'), "ingotRefinedIron", Character.valueOf('C'), Ic2Items.insulatedCopperCableItem, Character.valueOf('@'), Ic2Items.waterMill
		});
		advRecipes.addRecipe(Ic2Items.boatElectric, new Object[] {
			"CCC", "X@X", "XXX", Character.valueOf('X'), "ingotRefinedIron", Character.valueOf('C'), Ic2Items.insulatedCopperCableItem, Character.valueOf('@'), Ic2Items.waterMill
		});
		advRecipes.addRecipe(Ic2Items.reinforcedDoor, new Object[] {
			"SS", "SS", "SS", Character.valueOf('S'), Ic2Items.reinforcedStone
		});
		advRecipes.addRecipe(Ic2Items.scrapBox, new Object[] {
			"SSS", "SSS", "SSS", Character.valueOf('S'), Ic2Items.scrap
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.stickyDynamite, 8), new Object[] {
			"DDD", "DRD", "DDD", Character.valueOf('D'), Ic2Items.dynamite, Character.valueOf('R'), Ic2Items.resin
		});
		advRecipes.addShapelessRecipe(StackUtil.copyWithSize(Ic2Items.dynamite, 8), new Object[] {
			Ic2Items.industrialTnt, Item.silk
		});
		advRecipes.addShapelessRecipe(StackUtil.copyWithSize(Ic2Items.bronzeDust, 2), new Object[] {
			"dustTin", "dustCopper", "dustCopper", "dustCopper"
		});
		advRecipes.addShapelessRecipe(Ic2Items.ironDust, new Object[] {
			Ic2Items.smallIronDust, Ic2Items.smallIronDust
		});
		advRecipes.addShapelessRecipe(Ic2Items.carbonMesh, new Object[] {
			Ic2Items.carbonFiber, Ic2Items.carbonFiber
		});
		advRecipes.addShapelessRecipe(new ItemStack(Block.pistonStickyBase, 1), new Object[] {
			Block.pistonBase, Ic2Items.resin, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(new ItemStack(Block.pistonBase, 1), new Object[] {
			"TTT", "#X#", "#R#", Character.valueOf('#'), Block.cobblestone, Character.valueOf('X'), "ingotBronze", Character.valueOf('R'), Item.redstone, Character.valueOf('T'), 
			Block.planks, Boolean.valueOf(true)
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.miningPipe, 8), new Object[] {
			"I I", "I I", "ITI", Character.valueOf('I'), "ingotRefinedIron", Character.valueOf('T'), Ic2Items.treetap
		});
		if (Ic2Items.rubberSapling != null)
			advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.plantBall, 2), new Object[] {
				"PPP", "P P", "PPP", Character.valueOf('P'), Ic2Items.rubberSapling
			});
		if (IC2.enableCraftingGlowstoneDust)
			advRecipes.addRecipe(new ItemStack(Item.glowstone, 1), new Object[] {
				"RGR", "GRG", "RGR", Character.valueOf('R'), Item.redstone, Character.valueOf('G'), "dustGold", Boolean.valueOf(true)
			});
		if (IC2.enableCraftingGunpowder)
			advRecipes.addRecipe(new ItemStack(Item.gunpowder, 3), new Object[] {
				"RCR", "CRC", "RCR", Character.valueOf('R'), Item.redstone, Character.valueOf('C'), "dustCoal", Boolean.valueOf(true)
			});
		if (IC2.enableCraftingBucket)
			advRecipes.addRecipe(new ItemStack(Item.bucketEmpty, 1), new Object[] {
				"T T", " T ", Character.valueOf('T'), "ingotTin", Boolean.valueOf(true)
			});
		if (IC2.enableCraftingCoin)
			advRecipes.addRecipe(Ic2Items.refinedIronIngot, new Object[] {
				"III", "III", "III", Character.valueOf('I'), Ic2Items.coin
			});
		if (IC2.enableCraftingCoin)
			advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.coin, 16), new Object[] {
				"II", "II", Character.valueOf('I'), "ingotRefinedIron"
			});
		if (IC2.enableCraftingRail)
			advRecipes.addRecipe(new ItemStack(Block.rail, 8), new Object[] {
				"B B", "BsB", "B B", Character.valueOf('B'), "ingotBronze", Character.valueOf('s'), "stickWood", Boolean.valueOf(true)
			});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.crop, 2), new Object[] {
			"S S", "S S", Character.valueOf('S'), "stickWood"
		});
		advRecipes.addRecipe(new ItemStack(Ic2Items.cropnalyzer.getItem()), new Object[] {
			"cc ", "RGR", "RCR", Character.valueOf('G'), Block.glass, Character.valueOf('c'), Ic2Items.insulatedCopperCableItem, Character.valueOf('R'), Item.redstone, Character.valueOf('C'), 
			Ic2Items.electronicCircuit
		});
		advRecipes.addShapelessRecipe(StackUtil.copyWithSize(Ic2Items.fertilizer, 2), new Object[] {
			Ic2Items.scrap, new ItemStack(Item.dyePowder, 1, 15)
		});
		advRecipes.addShapelessRecipe(StackUtil.copyWithSize(Ic2Items.fertilizer, 2), new Object[] {
			Ic2Items.scrap, Ic2Items.scrap, Ic2Items.fertilizer
		});
		advRecipes.addRecipe(Ic2Items.weedEx, new Object[] {
			"R", "G", "C", Character.valueOf('R'), Item.redstone, Character.valueOf('G'), Ic2Items.grinPowder, Character.valueOf('C'), Ic2Items.cell
		});
		advRecipes.addRecipe(Ic2Items.cropmatron, new Object[] {
			"cBc", "CMC", "CCC", Character.valueOf('M'), Ic2Items.machine, Character.valueOf('C'), Ic2Items.crop, Character.valueOf('c'), Ic2Items.electronicCircuit, Character.valueOf('B'), 
			Block.chest
		});
		advRecipes.addRecipe(new ItemStack(Ic2Items.mugEmpty.getItem()), new Object[] {
			"SS ", "SSS", "SS ", Character.valueOf('S'), Block.stone
		});
		advRecipes.addShapelessRecipe(new ItemStack(Ic2Items.coffeePowder.getItem()), new Object[] {
			Ic2Items.coffeeBeans
		});
		advRecipes.addShapelessRecipe(new ItemStack(Ic2Items.mugCoffee.getItem()), new Object[] {
			Ic2Items.mugEmpty, Ic2Items.coffeePowder, "liquid$water"
		});
		advRecipes.addShapelessRecipe(new ItemStack(Ic2Items.mugCoffee.getItem(), 1, 2), new Object[] {
			new ItemStack(Ic2Items.mugCoffee.getItem(), 1, 1), Item.sugar, Item.bucketMilk
		});
		if (Ic2Items.rubberWood != null)
			advRecipes.addRecipe(new ItemStack(Ic2Items.barrel.getItem()), new Object[] {
				"P", "W", "P", Character.valueOf('P'), Block.planks, Character.valueOf('W'), Ic2Items.rubberWood
			});
		advRecipes.addRecipe(new ItemStack(Ic2Items.mugEmpty.getItem()), new Object[] {
			"#", Character.valueOf('#'), new ItemStack(Ic2Items.mugBooze.getItem(), 1, -1)
		});
		advRecipes.addRecipe(new ItemStack(Ic2Items.barrel.getItem()), new Object[] {
			"#", Character.valueOf('#'), new ItemStack(Ic2Items.barrel.getItem(), 1, -1)
		});
		advRecipes.addShapelessRecipe(Ic2Items.blackPainter, new Object[] {
			Ic2Items.painter, "dyeBlack"
		});
		advRecipes.addShapelessRecipe(Ic2Items.redPainter, new Object[] {
			Ic2Items.painter, "dyeRed"
		});
		advRecipes.addShapelessRecipe(Ic2Items.greenPainter, new Object[] {
			Ic2Items.painter, "dyeGreen"
		});
		advRecipes.addShapelessRecipe(Ic2Items.brownPainter, new Object[] {
			Ic2Items.painter, "dyeBrown"
		});
		advRecipes.addShapelessRecipe(Ic2Items.bluePainter, new Object[] {
			Ic2Items.painter, "dyeBlue"
		});
		advRecipes.addShapelessRecipe(Ic2Items.purplePainter, new Object[] {
			Ic2Items.painter, "dyePurple"
		});
		advRecipes.addShapelessRecipe(Ic2Items.cyanPainter, new Object[] {
			Ic2Items.painter, "dyeCyan"
		});
		advRecipes.addShapelessRecipe(Ic2Items.lightGreyPainter, new Object[] {
			Ic2Items.painter, "dyeLightGray"
		});
		advRecipes.addShapelessRecipe(Ic2Items.darkGreyPainter, new Object[] {
			Ic2Items.painter, "dyeGray"
		});
		advRecipes.addShapelessRecipe(Ic2Items.pinkPainter, new Object[] {
			Ic2Items.painter, "dyePink"
		});
		advRecipes.addShapelessRecipe(Ic2Items.limePainter, new Object[] {
			Ic2Items.painter, "dyeLime"
		});
		advRecipes.addShapelessRecipe(Ic2Items.yellowPainter, new Object[] {
			Ic2Items.painter, "dyeYellow"
		});
		advRecipes.addShapelessRecipe(Ic2Items.cloudPainter, new Object[] {
			Ic2Items.painter, "dyeLightBlue"
		});
		advRecipes.addShapelessRecipe(Ic2Items.magentaPainter, new Object[] {
			Ic2Items.painter, "dyeMagenta"
		});
		advRecipes.addShapelessRecipe(Ic2Items.orangePainter, new Object[] {
			Ic2Items.painter, "dyeOrange"
		});
		advRecipes.addShapelessRecipe(Ic2Items.whitePainter, new Object[] {
			Ic2Items.painter, "dyeWhite"
		});
		advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] {
			new ItemStack(Ic2Items.blackPainter.itemID, 1, -1)
		});
		advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] {
			new ItemStack(Ic2Items.redPainter.itemID, 1, -1)
		});
		advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] {
			new ItemStack(Ic2Items.greenPainter.itemID, 1, -1)
		});
		advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] {
			new ItemStack(Ic2Items.brownPainter.itemID, 1, -1)
		});
		advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] {
			new ItemStack(Ic2Items.bluePainter.itemID, 1, -1)
		});
		advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] {
			new ItemStack(Ic2Items.purplePainter.itemID, 1, -1)
		});
		advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] {
			new ItemStack(Ic2Items.cyanPainter.itemID, 1, -1)
		});
		advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] {
			new ItemStack(Ic2Items.lightGreyPainter.itemID, 1, -1)
		});
		advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] {
			new ItemStack(Ic2Items.darkGreyPainter.itemID, 1, -1)
		});
		advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] {
			new ItemStack(Ic2Items.pinkPainter.itemID, 1, -1)
		});
		advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] {
			new ItemStack(Ic2Items.limePainter.itemID, 1, -1)
		});
		advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] {
			new ItemStack(Ic2Items.yellowPainter.itemID, 1, -1)
		});
		advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] {
			new ItemStack(Ic2Items.cloudPainter.itemID, 1, -1)
		});
		advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] {
			new ItemStack(Ic2Items.magentaPainter.itemID, 1, -1)
		});
		advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] {
			new ItemStack(Ic2Items.orangePainter.itemID, 1, -1)
		});
		advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] {
			new ItemStack(Ic2Items.whitePainter.itemID, 1, -1)
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.cell, 16), new Object[] {
			" T ", "T T", " T ", Character.valueOf('T'), "ingotTin"
		});
		advRecipes.addRecipe(Ic2Items.fuelCan, new Object[] {
			" TT", "T T", "TTT", Character.valueOf('T'), "ingotTin"
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.tinCan, 4), new Object[] {
			"T T", "TTT", Character.valueOf('T'), "ingotTin"
		});
		advRecipes.addShapelessRecipe(Ic2Items.waterCell, new Object[] {
			Ic2Items.cell, Item.bucketWater
		});
		advRecipes.addShapelessRecipe(Ic2Items.lavaCell, new Object[] {
			Ic2Items.cell, Item.bucketLava
		});
		advRecipes.addShapelessRecipe(new ItemStack(Block.obsidian, 1), new Object[] {
			Ic2Items.waterCell, Ic2Items.waterCell, Ic2Items.lavaCell, Ic2Items.lavaCell
		});
		advRecipes.addShapelessRecipe(Ic2Items.hydratedCoalDust, new Object[] {
			"dustCoal", "liquid$water"
		});
		advRecipes.addShapelessRecipe(Ic2Items.hydratedCoalCell, new Object[] {
			Ic2Items.cell, Ic2Items.hydratedCoalClump
		});
		advRecipes.addShapelessRecipe(Ic2Items.bioCell, new Object[] {
			Ic2Items.cell, Ic2Items.compressedPlantBall
		});
		advRecipes.addRecipe(new ItemStack(Ic2Items.cfPack.itemID, 1, 259), new Object[] {
			"SCS", "FTF", "F F", Character.valueOf('T'), "ingotTin", Character.valueOf('C'), Ic2Items.electronicCircuit, Character.valueOf('F'), Ic2Items.fuelCan, Character.valueOf('S'), 
			new ItemStack(Ic2Items.constructionFoamSprayer.itemID, 1, 1601)
		});
		advRecipes.addShapelessRecipe(StackUtil.copyWithSize(Ic2Items.constructionFoam, 3), new Object[] {
			"dustClay", "liquid$water", Item.redstone, "dustCoal"
		});
		advRecipes.addShapelessRecipe(new ItemStack(Item.diamond), new Object[] {
			Ic2Items.industrialDiamond
		});
		advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.mixedMetalIngot, 2), new Object[] {
			"III", "BBB", "TTT", Character.valueOf('I'), "ingotRefinedIron", Character.valueOf('B'), "ingotBronze", Character.valueOf('T'), "ingotTin"
		});
		advRecipes.addRecipe(Ic2Items.remote, new Object[] {
			" C ", "TLT", " F ", Character.valueOf('C'), Ic2Items.insulatedCopperCableItem, Character.valueOf('F'), Ic2Items.frequencyTransmitter, Character.valueOf('L'), new ItemStack(Item.dyePowder, 1, 4), Character.valueOf('T'), 
			"ingotTin"
		});
	}
}
