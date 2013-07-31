// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IC2.java

package ic2.core;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.registry.*;
import cpw.mods.fml.relauncher.Side;
import ic2.api.crops.Crops;
import ic2.api.info.Info;
import ic2.api.item.ElectricItem;
import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.Recipes;
import ic2.api.tile.ExplosionWhitelist;
import ic2.core.audio.AudioManager;
import ic2.core.block.BlockBarrel;
import ic2.core.block.BlockCrop;
import ic2.core.block.BlockDynamite;
import ic2.core.block.BlockFoam;
import ic2.core.block.BlockIC2Door;
import ic2.core.block.BlockITNT;
import ic2.core.block.BlockMetal;
import ic2.core.block.BlockPoleFence;
import ic2.core.block.BlockResin;
import ic2.core.block.BlockRubLeaves;
import ic2.core.block.BlockRubSapling;
import ic2.core.block.BlockRubWood;
import ic2.core.block.BlockRubberSheet;
import ic2.core.block.BlockScaffold;
import ic2.core.block.BlockSimple;
import ic2.core.block.BlockTexGlass;
import ic2.core.block.BlockTextureStitched;
import ic2.core.block.BlockWall;
import ic2.core.block.EntityDynamite;
import ic2.core.block.EntityItnt;
import ic2.core.block.EntityNuke;
import ic2.core.block.EntityStickyDynamite;
import ic2.core.block.TileEntityBarrel;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.TileEntityCrop;
import ic2.core.block.TileEntityWall;
import ic2.core.block.WorldGenRubTree;
import ic2.core.block.crop.IC2Crops;
import ic2.core.block.generator.block.BlockGenerator;
import ic2.core.block.generator.block.BlockReactorChamber;
import ic2.core.block.generator.tileentity.TileEntityGenerator;
import ic2.core.block.generator.tileentity.TileEntityGeoGenerator;
import ic2.core.block.generator.tileentity.TileEntityNuclearReactorElectric;
import ic2.core.block.generator.tileentity.TileEntityReactorChamberElectric;
import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;
import ic2.core.block.generator.tileentity.TileEntityWaterGenerator;
import ic2.core.block.generator.tileentity.TileEntityWindGenerator;
import ic2.core.block.machine.BlockMachine;
import ic2.core.block.machine.BlockMachine2;
import ic2.core.block.machine.BlockMiningPipe;
import ic2.core.block.machine.BlockMiningTip;
import ic2.core.block.machine.tileentity.TileEntityCanner;
import ic2.core.block.machine.tileentity.TileEntityCompressor;
import ic2.core.block.machine.tileentity.TileEntityCropmatron;
import ic2.core.block.machine.tileentity.TileEntityElectricFurnace;
import ic2.core.block.machine.tileentity.TileEntityElectrolyzer;
import ic2.core.block.machine.tileentity.TileEntityExtractor;
import ic2.core.block.machine.tileentity.TileEntityInduction;
import ic2.core.block.machine.tileentity.TileEntityIronFurnace;
import ic2.core.block.machine.tileentity.TileEntityMacerator;
import ic2.core.block.machine.tileentity.TileEntityMagnetizer;
import ic2.core.block.machine.tileentity.TileEntityMatter;
import ic2.core.block.machine.tileentity.TileEntityMiner;
import ic2.core.block.machine.tileentity.TileEntityPump;
import ic2.core.block.machine.tileentity.TileEntityRecycler;
import ic2.core.block.machine.tileentity.TileEntityTeleporter;
import ic2.core.block.machine.tileentity.TileEntityTerra;
import ic2.core.block.machine.tileentity.TileEntityTesla;
import ic2.core.block.personal.BlockPersonal;
import ic2.core.block.personal.TileEntityEnergyOMat;
import ic2.core.block.personal.TileEntityPersonalChest;
import ic2.core.block.personal.TileEntityTradeOMat;
import ic2.core.block.wiring.BlockCable;
import ic2.core.block.wiring.BlockElectric;
import ic2.core.block.wiring.BlockLuminator;
import ic2.core.block.wiring.TileEntityCable;
import ic2.core.block.wiring.TileEntityCableDetector;
import ic2.core.block.wiring.TileEntityCableSplitter;
import ic2.core.block.wiring.TileEntityElectricBatBox;
import ic2.core.block.wiring.TileEntityElectricMFE;
import ic2.core.block.wiring.TileEntityElectricMFSU;
import ic2.core.block.wiring.TileEntityLuminator;
import ic2.core.block.wiring.TileEntityTransformerHV;
import ic2.core.block.wiring.TileEntityTransformerLV;
import ic2.core.block.wiring.TileEntityTransformerMV;
import ic2.core.init.InternalName;
import ic2.core.init.Localization;
import ic2.core.init.Recipies;
import ic2.core.item.ElectricItemManager;
import ic2.core.item.EntityBoatCarbon;
import ic2.core.item.EntityBoatElectric;
import ic2.core.item.EntityBoatRubber;
import ic2.core.item.GatewayElectricItemManager;
import ic2.core.item.ItemBattery;
import ic2.core.item.ItemBatteryDischarged;
import ic2.core.item.ItemBatterySU;
import ic2.core.item.ItemBooze;
import ic2.core.item.ItemCell;
import ic2.core.item.ItemCropSeed;
import ic2.core.item.ItemFertilizer;
import ic2.core.item.ItemFuelCanEmpty;
import ic2.core.item.ItemFuelCanFilled;
import ic2.core.item.ItemGradual;
import ic2.core.item.ItemIC2;
import ic2.core.item.ItemIC2Boat;
import ic2.core.item.ItemMigrate;
import ic2.core.item.ItemMug;
import ic2.core.item.ItemMugCoffee;
import ic2.core.item.ItemResin;
import ic2.core.item.ItemScrapbox;
import ic2.core.item.ItemTerraWart;
import ic2.core.item.ItemTinCan;
import ic2.core.item.ItemToolbox;
import ic2.core.item.ItemUpgradeModule;
import ic2.core.item.armor.ItemArmorBatpack;
import ic2.core.item.armor.ItemArmorCFPack;
import ic2.core.item.armor.ItemArmorHazmat;
import ic2.core.item.armor.ItemArmorIC2;
import ic2.core.item.armor.ItemArmorJetpack;
import ic2.core.item.armor.ItemArmorJetpackElectric;
import ic2.core.item.armor.ItemArmorLappack;
import ic2.core.item.armor.ItemArmorNanoSuit;
import ic2.core.item.armor.ItemArmorNightvisionGoggles;
import ic2.core.item.armor.ItemArmorQuantumSuit;
import ic2.core.item.armor.ItemArmorSolarHelmet;
import ic2.core.item.armor.ItemArmorStaticBoots;
import ic2.core.item.block.ItemBarrel;
import ic2.core.item.block.ItemCable;
import ic2.core.item.block.ItemDynamite;
import ic2.core.item.block.ItemIC2Door;
import ic2.core.item.reactor.ItemReactorCondensator;
import ic2.core.item.reactor.ItemReactorDepletedUranium;
import ic2.core.item.reactor.ItemReactorHeatStorage;
import ic2.core.item.reactor.ItemReactorHeatSwitch;
import ic2.core.item.reactor.ItemReactorHeatpack;
import ic2.core.item.reactor.ItemReactorPlating;
import ic2.core.item.reactor.ItemReactorReflector;
import ic2.core.item.reactor.ItemReactorUranium;
import ic2.core.item.reactor.ItemReactorVent;
import ic2.core.item.reactor.ItemReactorVentSpread;
import ic2.core.item.tfbp.ItemTFBPChilling;
import ic2.core.item.tfbp.ItemTFBPCultivation;
import ic2.core.item.tfbp.ItemTFBPDesertification;
import ic2.core.item.tfbp.ItemTFBPFlatification;
import ic2.core.item.tfbp.ItemTFBPIrrigation;
import ic2.core.item.tfbp.ItemTFBPMushroom;
import ic2.core.item.tool.EntityMiningLaser;
import ic2.core.item.tool.ItemCropnalyzer;
import ic2.core.item.tool.ItemDebug;
import ic2.core.item.tool.ItemElectricToolChainsaw;
import ic2.core.item.tool.ItemElectricToolDDrill;
import ic2.core.item.tool.ItemElectricToolDrill;
import ic2.core.item.tool.ItemElectricToolHoe;
import ic2.core.item.tool.ItemFrequencyTransmitter;
import ic2.core.item.tool.ItemIC2Axe;
import ic2.core.item.tool.ItemIC2Hoe;
import ic2.core.item.tool.ItemIC2Pickaxe;
import ic2.core.item.tool.ItemIC2Spade;
import ic2.core.item.tool.ItemIC2Sword;
import ic2.core.item.tool.ItemNanoSaber;
import ic2.core.item.tool.ItemObscurator;
import ic2.core.item.tool.ItemRemote;
import ic2.core.item.tool.ItemScanner;
import ic2.core.item.tool.ItemScannerAdv;
import ic2.core.item.tool.ItemSprayer;
import ic2.core.item.tool.ItemToolCutter;
import ic2.core.item.tool.ItemToolMeter;
import ic2.core.item.tool.ItemToolMiningLaser;
import ic2.core.item.tool.ItemToolPainter;
import ic2.core.item.tool.ItemToolWrench;
import ic2.core.item.tool.ItemToolWrenchElectric;
import ic2.core.item.tool.ItemTreetap;
import ic2.core.item.tool.ItemTreetapElectric;
import ic2.core.network.NetworkManager;
import ic2.core.util.ItemInfo;
import ic2.core.util.Keyboard;
import ic2.core.util.StackUtil;
import ic2.core.util.TextureIndex;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.*;
import net.minecraftforge.event.EventBus;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.*;
import net.minecraftforge.oredict.OreDictionary;

// Referenced classes of package ic2.core:
//			Platform, Ic2Items, BasicMachineRecipeManager, EnergyNet, 
//			IC2Potion, IC2Loot, IC2Achievements, WorldData, 
//			ITickCallback, ExplosionIC2, CreativeTabIC2

public class IC2
	implements ITickHandler, IWorldGenerator, IFuelHandler, IConnectionHandler, IPlayerTracker
{

	public static final String VERSION = "1.118.401-lf";
	private static IC2 instance = null;
	public static Platform platform;
	public static NetworkManager network;
	public static Keyboard keyboard;
	public static AudioManager audioManager;
	public static TextureIndex textureIndex;
	public static Logger log;
	public static IC2Achievements achievements;
	public static int cableRenderId;
	public static int fenceRenderId;
	public static int miningPipeRenderId;
	public static int luminatorRenderId;
	public static int cropRenderId;
	public static Random random = new Random();
	public static int windStrength;
	public static int windTicker;
	public static Map valuableOres = new TreeMap();
	public static boolean enableCraftingBucket = true;
	public static boolean enableCraftingCoin = true;
	public static boolean enableCraftingGlowstoneDust = true;
	public static boolean enableCraftingGunpowder = true;
	public static boolean enableCraftingITnt = true;
	public static boolean enableCraftingNuke = true;
	public static boolean enableCraftingRail = true;
	public static boolean enableDynamicIdAllocation = true;
	public static boolean enableLoggingWrench = true;
	public static boolean enableSecretRecipeHiding = true;
	public static boolean enableQuantumSpeedOnSprint = true;
	public static boolean enableMinerLapotron = false;
	public static boolean enableTeleporterInventory = true;
	public static boolean enableBurningScrap = true;
	public static boolean enableWorldGenTreeRubber = true;
	public static boolean enableWorldGenOreCopper = true;
	public static boolean enableWorldGenOreTin = true;
	public static boolean enableWorldGenOreUranium = true;
	public static float explosionPowerNuke = 35F;
	public static float explosionPowerReactorMax = 45F;
	public static int energyGeneratorBase = 10;
	public static int energyGeneratorGeo = 20;
	public static int energyGeneratorWater = 100;
	public static int energyGeneratorSolar = 100;
	public static int energyGeneratorWind = 100;
	public static int energyGeneratorNuclear = 5;
	public static boolean suddenlyHoes = false;
	public static boolean seasonal = false;
	public static boolean enableSteamReactor = false;
	public static float oreDensityFactor = 1.0F;
	public static boolean initialized = false;
	public static Properties runtimeIdProperties = new Properties();
	public static CreativeTabIC2 tabIC2 = new CreativeTabIC2();
	private static boolean silverDustSmeltingRegistered = false;
	private static Set blockedIds = new HashSet();
	public static final int networkProtocolVersion = 1;
	public static final String textureDomain = "ic2";
	public static final int setBlockNotify = 1;
	public static final int setBlockUpdate = 2;
	public static final int setBlockNoUpdateFromClient = 4;
	static final boolean $assertionsDisabled = !ic2/core/IC2.desiredAssertionStatus();

	public IC2()
	{
		instance = this;
	}

	public static IC2 getInstance()
	{
		return instance;
	}

	public boolean checkVersion(String version)
	{
		String partsLocal[] = "1.118.401-lf".split("\\.");
		String partsRemote[] = version.split("\\.");
		return partsLocal.length >= 2 && partsRemote.length >= 2 && partsLocal[0].equals(partsRemote[0]) && partsLocal[1].equals(partsRemote[1]);
	}

	public void load(FMLPreInitializationEvent event)
	{
		log = event.getModLog();
		int minForge = 759;
		int forge = ForgeVersion.getBuildVersion();
		if (forge > 0 && forge < minForge)
			platform.displayError((new StringBuilder()).append("The currently installed version of Minecraft Forge (").append(ForgeVersion.getMajorVersion()).append(".").append(ForgeVersion.getMinorVersion()).append(".").append(ForgeVersion.getRevisionVersion()).append(".").append(forge).append(") is too old.\n").append("Please update the Minecraft Forge.\n").append("\n").append("(Technical information: ").append(forge).append(" < ").append(minForge).append(")").toString());
		Configuration config;
		try
		{
			File configFile = new File(new File(platform.getMinecraftDir(), "config"), "IC2.cfg");
			config = new Configuration(configFile);
			config.load();
			log.info((new StringBuilder()).append("Config loaded from ").append(configFile.getAbsolutePath()).toString());
		}
		catch (Exception e)
		{
			log.warning((new StringBuilder()).append("Error while trying to access configuration! ").append(e).toString());
			config = null;
		}
		Property dynamicIdAllocationProp = null;
		if (config != null)
		{
			dynamicIdAllocationProp = config.get("general", "enableDynamicIdAllocation", enableDynamicIdAllocation);
			dynamicIdAllocationProp.comment = "Enable searching for free block ids, will get disabled after the next successful load";
			enableDynamicIdAllocation = dynamicIdAllocationProp.getBoolean(enableDynamicIdAllocation);
			Property prop = config.get("general", "enableCraftingBucket", enableCraftingBucket);
			prop.comment = "Enable crafting of buckets out of tin";
			enableCraftingBucket = prop.getBoolean(enableCraftingBucket);
			prop = config.get("general", "enableCraftingCoin", enableCraftingCoin);
			prop.comment = "Enable crafting of Industrial Credit coins";
			enableCraftingCoin = prop.getBoolean(enableCraftingCoin);
			prop = config.get("general", "enableCraftingGlowstoneDust", enableCraftingGlowstoneDust);
			prop.comment = "Enable crafting of glowstone dust out of dusts";
			enableCraftingGlowstoneDust = prop.getBoolean(enableCraftingGlowstoneDust);
			prop = config.get("general", "enableCraftingGunpowder", enableCraftingGunpowder);
			prop.comment = "Enable crafting of gunpowder out of dusts";
			enableCraftingGunpowder = prop.getBoolean(enableCraftingGunpowder);
			prop = config.get("general", "enableCraftingITnt", enableCraftingITnt);
			prop.comment = "Enable crafting of ITNT";
			enableCraftingITnt = prop.getBoolean(enableCraftingITnt);
			prop = config.get("general", "enableCraftingNuke", enableCraftingNuke);
			prop.comment = "Enable crafting of nukes";
			enableCraftingNuke = prop.getBoolean(enableCraftingNuke);
			prop = config.get("general", "enableCraftingRail", enableCraftingRail);
			prop.comment = "Enable crafting of rails out of bronze";
			enableCraftingRail = prop.getBoolean(enableCraftingRail);
			prop = config.get("general", "enableSecretRecipeHiding", enableSecretRecipeHiding);
			prop.comment = "Enable hiding of secret recipes in CraftGuide/NEI";
			enableSecretRecipeHiding = prop.getBoolean(enableSecretRecipeHiding);
			prop = config.get("general", "enableQuantumSpeedOnSprint", enableQuantumSpeedOnSprint);
			prop.comment = "Enable activation of the quantum leggings' speed boost when sprinting instead of holding the boost key";
			enableQuantumSpeedOnSprint = prop.getBoolean(enableQuantumSpeedOnSprint);
			prop = config.get("general", "enableMinerLapotron", enableMinerLapotron);
			prop.comment = "Enable usage of lapotron crystals on miners";
			enableMinerLapotron = prop.getBoolean(enableMinerLapotron);
			prop = config.get("general", "enableTeleporterInventory", enableTeleporterInventory);
			prop.comment = "Enable calculation of inventory weight when going through a teleporter";
			enableTeleporterInventory = prop.getBoolean(enableTeleporterInventory);
			prop = config.get("general", "enableBurningScrap", enableBurningScrap);
			prop.comment = "Enable burning of scrap in a generator";
			enableBurningScrap = prop.getBoolean(enableBurningScrap);
			prop = config.get("general", "enableLoggingWrench", enableLoggingWrench);
			prop.comment = "Enable logging of players when they remove a machine using a wrench";
			enableLoggingWrench = prop.getBoolean(enableLoggingWrench);
			prop = config.get("general", "enableWorldGenTreeRubber", enableWorldGenTreeRubber);
			prop.comment = "Enable generation of rubber trees in the world";
			enableWorldGenTreeRubber = prop.getBoolean(enableWorldGenTreeRubber);
			prop = config.get("general", "enableWorldGenOreCopper", enableWorldGenOreCopper);
			prop.comment = "Enable generation of copper in the world";
			enableWorldGenOreCopper = prop.getBoolean(enableWorldGenOreCopper);
			prop = config.get("general", "enableWorldGenOreTin", enableWorldGenOreTin);
			prop.comment = "Enable generation of tin in the world";
			enableWorldGenOreTin = prop.getBoolean(enableWorldGenOreTin);
			prop = config.get("general", "enableWorldGenOreUranium", enableWorldGenOreUranium);
			prop.comment = "Enable generation of uranium in the world";
			enableWorldGenOreUranium = prop.getBoolean(enableWorldGenOreUranium);
			prop = config.get("general", "enableSteamReactor", enableSteamReactor);
			prop.comment = "Enable steam-outputting reactors if Railcraft is installed";
			enableSteamReactor = prop.getBoolean(enableSteamReactor);
			prop = config.get("general", "explosionPowerNuke", Float.toString(explosionPowerNuke));
			prop.comment = "Explosion power of a nuke, where TNT is 4";
			explosionPowerNuke = (float)prop.getDouble(explosionPowerNuke);
			prop = config.get("general", "explosionPowerReactorMax", Float.toString(explosionPowerReactorMax));
			prop.comment = "Maximum explosion power of a nuclear reactor, where TNT is 4";
			explosionPowerReactorMax = (float)prop.getDouble(explosionPowerReactorMax);
			prop = config.get("general", "energyGeneratorBase", energyGeneratorBase);
			prop.comment = "Base energy generation values - increase those for higher energy yield";
			energyGeneratorBase = prop.getInt(energyGeneratorBase);
			energyGeneratorGeo = config.get("general", "energyGeneratorGeo", energyGeneratorGeo).getInt(energyGeneratorGeo);
			energyGeneratorWater = config.get("general", "energyGeneratorWater", energyGeneratorWater).getInt(energyGeneratorWater);
			energyGeneratorSolar = config.get("general", "energyGeneratorSolar", energyGeneratorSolar).getInt(energyGeneratorSolar);
			energyGeneratorWind = config.get("general", "energyGeneratorWind", energyGeneratorWind).getInt(energyGeneratorWind);
			energyGeneratorNuclear = config.get("general", "energyGeneratorNuclear", energyGeneratorNuclear).getInt(energyGeneratorNuclear);
			prop = config.get("general", "valuableOres", getValuableOreString());
			prop.comment = "List of valuable ores the miner should look for. Comma separated, format is id-metadata:value where value should be at least 1 to be considered by the miner";
			setValuableOreFromString(prop.getString());
			prop = config.get("general", "valuableOres", getValuableOreString());
			prop.comment = "List of valuable ores the miner should look for. Comma separated, format is id-metadata:value where value should be at least 1 to be considered by the miner";
			setValuableOreFromString(prop.getString());
			prop = config.get("general", "oreDensityFactor", Float.toString(oreDensityFactor));
			prop.comment = "Factor to adjust the ore generation rate";
			oreDensityFactor = (float)prop.getDouble(oreDensityFactor);
			if (config.hasChanged())
				config.save();
			Property property;
			for (Iterator i$ = config.getCategory("block").values().iterator(); i$.hasNext(); blockedIds.add(Integer.valueOf(property.getInt())))
				property = (Property)i$.next();

			Property property;
			for (Iterator i$ = config.getCategory("item").values().iterator(); i$.hasNext(); blockedIds.add(Integer.valueOf(property.getInt() + 256)))
				property = (Property)i$.next();

		}
		audioManager.initialize(config);
		runtimeIdProperties.put("initialVersion", "1.118.401-lf");
		EnumHelper.addToolMaterial("IC2_BRONZE", 2, 350, 6F, 2.0F, 13);
		EnumArmorMaterial bronzeArmorMaterial = EnumHelper.addArmorMaterial("IC2_BRONZE", 15, new int[] {
			3, 8, 6, 3
		}, 9);
		EnumArmorMaterial alloyArmorMaterial = EnumHelper.addArmorMaterial("IC2_ALLOY", 50, new int[] {
			4, 9, 7, 4
		}, 12);
		if (enableWorldGenOreCopper)
			Ic2Items.copperOre = new ItemStack((new BlockSimple(config, InternalName.blockOreCopper, Material.rock)).setHardness(3F).setResistance(5F));
		if (enableWorldGenOreTin)
			Ic2Items.tinOre = new ItemStack((new BlockSimple(config, InternalName.blockOreTin, Material.rock)).setHardness(3F).setResistance(5F));
		if (enableWorldGenOreUranium)
			Ic2Items.uraniumOre = new ItemStack((new BlockSimple(config, InternalName.blockOreUran, Material.rock)).setHardness(4F).setResistance(6F));
		if (enableWorldGenTreeRubber)
		{
			new BlockRubWood(config, InternalName.blockRubWood);
			new BlockRubLeaves(config, InternalName.blockRubLeaves);
			new BlockRubSapling(config, InternalName.blockRubSapling);
		}
		new BlockResin(config, InternalName.blockHarz);
		new BlockRubberSheet(config, InternalName.blockRubber);
		new BlockPoleFence(config, InternalName.blockFenceIron);
		Ic2Items.reinforcedStone = new ItemStack((new BlockSimple(config, InternalName.blockAlloy, Material.iron)).setHardness(80F).setResistance(150F).setStepSound(Block.soundMetalFootstep));
		Ic2Items.reinforcedGlass = new ItemStack(new BlockTexGlass(config, InternalName.blockAlloyGlass));
		Ic2Items.reinforcedDoorBlock = new ItemStack(new BlockIC2Door(config, InternalName.blockDoorAlloy));
		new BlockFoam(config, InternalName.blockFoam);
		new BlockWall(config, InternalName.blockWall);
		new BlockScaffold(config, InternalName.blockScaffold);
		new BlockScaffold(config, InternalName.blockIronScaffold);
		new BlockMetal(config, InternalName.blockMetal);
		new BlockCable(config, InternalName.blockCable);
		new BlockGenerator(config, InternalName.blockGenerator);
		new BlockReactorChamber(config, InternalName.blockReactorChamber);
		new BlockElectric(config, InternalName.blockElectric);
		new BlockMachine(config, InternalName.blockMachine);
		new BlockMachine2(config, InternalName.blockMachine2);
		Ic2Items.luminator = new ItemStack(new BlockLuminator(config, InternalName.blockLuminatorDark));
		Ic2Items.activeLuminator = new ItemStack(new BlockLuminator(config, InternalName.blockLuminator));
		new BlockMiningPipe(config, InternalName.blockMiningPipe);
		new BlockMiningTip(config, InternalName.blockMiningTip);
		new BlockPersonal(config, InternalName.blockPersonal);
		Ic2Items.industrialTnt = new ItemStack(new BlockITNT(config, InternalName.blockITNT));
		Ic2Items.nuke = new ItemStack(new BlockITNT(config, InternalName.blockNuke));
		Ic2Items.dynamiteStick = new ItemStack(new BlockDynamite(config, InternalName.blockDynamite));
		Ic2Items.dynamiteStickWithRemote = new ItemStack(new BlockDynamite(config, InternalName.blockDynamiteRemote));
		new BlockCrop(config, InternalName.blockCrop);
		new BlockBarrel(config, InternalName.blockBarrel);
		Ic2Items.resin = new ItemStack(new ItemResin(config, InternalName.itemHarz));
		Ic2Items.rubber = new ItemStack(new ItemIC2(config, InternalName.itemRubber));
		Ic2Items.uraniumDrop = new ItemStack(new ItemIC2(config, InternalName.itemOreUran));
		Ic2Items.bronzeDust = new ItemStack(new ItemIC2(config, InternalName.itemDustBronze));
		Ic2Items.clayDust = new ItemStack(new ItemIC2(config, InternalName.itemDustClay));
		Ic2Items.coalDust = new ItemStack(new ItemIC2(config, InternalName.itemDustCoal));
		Ic2Items.copperDust = new ItemStack(new ItemIC2(config, InternalName.itemDustCopper));
		Ic2Items.goldDust = new ItemStack(new ItemIC2(config, InternalName.itemDustGold));
		Ic2Items.ironDust = new ItemStack(new ItemIC2(config, InternalName.itemDustIron));
		Ic2Items.silverDust = new ItemStack(new ItemIC2(config, InternalName.itemDustSilver));
		Ic2Items.smallIronDust = new ItemStack(new ItemIC2(config, InternalName.itemDustIronSmall));
		Ic2Items.tinDust = new ItemStack(new ItemIC2(config, InternalName.itemDustTin));
		Ic2Items.hydratedCoalDust = new ItemStack(new ItemIC2(config, InternalName.itemFuelCoalDust));
		Ic2Items.refinedIronIngot = new ItemStack(new ItemIC2(config, InternalName.itemIngotAdvIron));
		Ic2Items.copperIngot = new ItemStack(new ItemIC2(config, InternalName.itemIngotCopper));
		Ic2Items.tinIngot = new ItemStack(new ItemIC2(config, InternalName.itemIngotTin));
		Ic2Items.bronzeIngot = new ItemStack(new ItemIC2(config, InternalName.itemIngotBronze));
		Ic2Items.mixedMetalIngot = new ItemStack(new ItemIC2(config, InternalName.itemIngotAlloy));
		Ic2Items.uraniumIngot = new ItemStack(new ItemIC2(config, InternalName.itemIngotUran));
		Ic2Items.electronicCircuit = new ItemStack(new ItemIC2(config, InternalName.itemPartCircuit));
		Ic2Items.advancedCircuit = new ItemStack((new ItemIC2(config, InternalName.itemPartCircuitAdv)).setRarity(1).setUnlocalizedName("itemPartCircuitAdv").setCreativeTab(tabIC2));
		Ic2Items.advancedAlloy = new ItemStack(new ItemIC2(config, InternalName.itemPartAlloy));
		Ic2Items.carbonFiber = new ItemStack(new ItemIC2(config, InternalName.itemPartCarbonFibre));
		Ic2Items.carbonMesh = new ItemStack(new ItemIC2(config, InternalName.itemPartCarbonMesh));
		Ic2Items.carbonPlate = new ItemStack(new ItemIC2(config, InternalName.itemPartCarbonPlate));
		Ic2Items.matter = new ItemStack((new ItemIC2(config, InternalName.itemMatter)).setRarity(2).setUnlocalizedName("itemMatter").setCreativeTab(tabIC2));
		Ic2Items.iridiumOre = new ItemStack((new ItemIC2(config, InternalName.itemOreIridium)).setRarity(2).setUnlocalizedName("itemOreIridium").setCreativeTab(tabIC2));
		Ic2Items.iridiumPlate = new ItemStack((new ItemIC2(config, InternalName.itemPartIridium)).setRarity(2).setUnlocalizedName("itemPartIridium").setCreativeTab(tabIC2));
		Ic2Items.denseCopperPlate = new ItemStack(new ItemIC2(config, InternalName.itemPartDCP));
		Ic2Items.treetap = new ItemStack(new ItemTreetap(config, InternalName.itemTreetap));
		Ic2Items.bronzePickaxe = new ItemStack(new ItemIC2Pickaxe(config, InternalName.itemToolBronzePickaxe, EnumToolMaterial.IRON, 5F, "ingotBronze"));
		Ic2Items.bronzeAxe = new ItemStack(new ItemIC2Axe(config, InternalName.itemToolBronzeAxe, EnumToolMaterial.IRON, 5F, "ingotBronze"));
		Ic2Items.bronzeSword = new ItemStack(new ItemIC2Sword(config, InternalName.itemToolBronzeSword, EnumToolMaterial.IRON, 7, "ingotBronze"));
		Ic2Items.bronzeShovel = new ItemStack(new ItemIC2Spade(config, InternalName.itemToolBronzeSpade, EnumToolMaterial.IRON, 5F, "ingotBronze"));
		Ic2Items.bronzeHoe = new ItemStack(new ItemIC2Hoe(config, InternalName.itemToolBronzeHoe, EnumToolMaterial.IRON, "ingotBronze"));
		Ic2Items.wrench = new ItemStack(new ItemToolWrench(config, InternalName.itemToolWrench));
		Ic2Items.cutter = new ItemStack(new ItemToolCutter(config, InternalName.itemToolCutter));
		Ic2Items.constructionFoamSprayer = new ItemStack(new ItemSprayer(config, InternalName.itemFoamSprayer));
		Ic2Items.toolbox = new ItemStack(new ItemToolbox(config, InternalName.itemToolbox));
		Ic2Items.miningDrill = new ItemStack(new ItemElectricToolDrill(config, InternalName.itemToolDrill));
		Ic2Items.diamondDrill = new ItemStack(new ItemElectricToolDDrill(config, InternalName.itemToolDDrill));
		Ic2Items.chainsaw = new ItemStack(new ItemElectricToolChainsaw(config, InternalName.itemToolChainsaw));
		Ic2Items.electricWrench = new ItemStack(new ItemToolWrenchElectric(config, InternalName.itemToolWrenchElectric));
		Ic2Items.electricTreetap = new ItemStack(new ItemTreetapElectric(config, InternalName.itemTreetapElectric));
		Ic2Items.miningLaser = new ItemStack(new ItemToolMiningLaser(config, InternalName.itemToolMiningLaser));
		Ic2Items.ecMeter = new ItemStack(new ItemToolMeter(config, InternalName.itemToolMEter));
		Ic2Items.odScanner = new ItemStack(new ItemScanner(config, InternalName.itemScanner, 1));
		Ic2Items.ovScanner = new ItemStack(new ItemScannerAdv(config, InternalName.itemScannerAdv, 2));
		Ic2Items.obscurator = new ItemStack(new ItemObscurator(config, InternalName.obscurator));
		Ic2Items.frequencyTransmitter = new ItemStack(new ItemFrequencyTransmitter(config, InternalName.itemFreq));
		Ic2Items.nanoSaber = new ItemStack(new ItemNanoSaber(config, InternalName.itemNanoSaber));
		Ic2Items.hazmatHelmet = new ItemStack(new ItemArmorHazmat(config, InternalName.itemArmorHazmatHelmet, 0));
		Ic2Items.hazmatChestplate = new ItemStack(new ItemArmorHazmat(config, InternalName.itemArmorHazmatChestplate, 1));
		Ic2Items.hazmatLeggings = new ItemStack(new ItemArmorHazmat(config, InternalName.itemArmorHazmatLeggings, 2));
		Ic2Items.hazmatBoots = new ItemStack(new ItemArmorHazmat(config, InternalName.itemArmorRubBoots, 3));
		Ic2Items.bronzeHelmet = new ItemStack(new ItemArmorIC2(config, InternalName.itemArmorBronzeHelmet, bronzeArmorMaterial, InternalName.bronze, 0, "ingotBronze"));
		Ic2Items.bronzeChestplate = new ItemStack(new ItemArmorIC2(config, InternalName.itemArmorBronzeChestplate, bronzeArmorMaterial, InternalName.bronze, 1, "ingotBronze"));
		Ic2Items.bronzeLeggings = new ItemStack(new ItemArmorIC2(config, InternalName.itemArmorBronzeLegs, bronzeArmorMaterial, InternalName.bronze, 2, "ingotBronze"));
		Ic2Items.bronzeBoots = new ItemStack(new ItemArmorIC2(config, InternalName.itemArmorBronzeBoots, bronzeArmorMaterial, InternalName.bronze, 3, "ingotBronze"));
		Ic2Items.compositeArmor = new ItemStack(new ItemArmorIC2(config, InternalName.itemArmorAlloyChestplate, alloyArmorMaterial, InternalName.alloy, 1, Ic2Items.advancedAlloy));
		Ic2Items.nanoHelmet = new ItemStack(new ItemArmorNanoSuit(config, InternalName.itemArmorNanoHelmet, 0));
		Ic2Items.nanoBodyarmor = new ItemStack(new ItemArmorNanoSuit(config, InternalName.itemArmorNanoChestplate, 1));
		Ic2Items.nanoLeggings = new ItemStack(new ItemArmorNanoSuit(config, InternalName.itemArmorNanoLegs, 2));
		Ic2Items.nanoBoots = new ItemStack(new ItemArmorNanoSuit(config, InternalName.itemArmorNanoBoots, 3));
		Ic2Items.quantumHelmet = new ItemStack(new ItemArmorQuantumSuit(config, InternalName.itemArmorQuantumHelmet, 0));
		Ic2Items.quantumBodyarmor = new ItemStack(new ItemArmorQuantumSuit(config, InternalName.itemArmorQuantumChestplate, 1));
		Ic2Items.quantumLeggings = new ItemStack(new ItemArmorQuantumSuit(config, InternalName.itemArmorQuantumLegs, 2));
		Ic2Items.quantumBoots = new ItemStack(new ItemArmorQuantumSuit(config, InternalName.itemArmorQuantumBoots, 3));
		Ic2Items.jetpack = new ItemStack(new ItemArmorJetpack(config, InternalName.itemArmorJetpack));
		Ic2Items.electricJetpack = new ItemStack(new ItemArmorJetpackElectric(config, InternalName.itemArmorJetpackElectric));
		Ic2Items.batPack = new ItemStack(new ItemArmorBatpack(config, InternalName.itemArmorBatpack));
		Ic2Items.lapPack = new ItemStack(new ItemArmorLappack(config, InternalName.itemArmorLappack));
		Ic2Items.cfPack = new ItemStack(new ItemArmorCFPack(config, InternalName.itemArmorCFPack));
		Ic2Items.solarHelmet = new ItemStack(new ItemArmorSolarHelmet(config, InternalName.itemSolarHelmet));
		Ic2Items.staticBoots = new ItemStack(new ItemArmorStaticBoots(config, InternalName.itemStaticBoots));
		Ic2Items.nightvisionGoggles = new ItemStack(new ItemArmorNightvisionGoggles(config, InternalName.itemNightvisionGoggles));
		Ic2Items.reBattery = new ItemStack(new ItemBatteryDischarged(config, InternalName.itemBatREDischarged, 10000, 100, 1));
		Ic2Items.chargedReBattery = new ItemStack(new ItemBattery(config, InternalName.itemBatRE, 10000, 100, 1));
		Ic2Items.energyCrystal = new ItemStack(new ItemBattery(config, InternalName.itemBatCrystal, 0x186a0, 250, 2));
		Ic2Items.lapotronCrystal = new ItemStack((new ItemBattery(config, InternalName.itemBatLamaCrystal, 0xf4240, 600, 3)).setRarity(1));
		Ic2Items.suBattery = new ItemStack(new ItemBatterySU(config, InternalName.itemBatSU, 1000, 1));
		new ItemCable(config, InternalName.itemCable);
		Ic2Items.cell = new ItemStack(new ItemCell(config, InternalName.itemCellEmpty));
		Ic2Items.lavaCell = new ItemStack(new ItemIC2(config, InternalName.itemCellLava));
		Ic2Items.hydratedCoalCell = new ItemStack(new ItemIC2(config, InternalName.itemCellCoal));
		Ic2Items.bioCell = new ItemStack(new ItemIC2(config, InternalName.itemCellBio));
		Ic2Items.coalfuelCell = new ItemStack(new ItemIC2(config, InternalName.itemCellCoalRef));
		Ic2Items.biofuelCell = new ItemStack(new ItemIC2(config, InternalName.itemCellBioRef));
		Ic2Items.waterCell = new ItemStack(new ItemIC2(config, InternalName.itemCellWater));
		Ic2Items.electrolyzedWaterCell = new ItemStack(new ItemIC2(config, InternalName.itemCellWaterElectro));
		Ic2Items.fuelCan = new ItemStack(new ItemFuelCanEmpty(config, InternalName.itemFuelCanEmpty));
		Ic2Items.filledFuelCan = new ItemStack(new ItemFuelCanFilled(config, InternalName.itemFuelCan));
		Ic2Items.tinCan = new ItemStack(new ItemIC2(config, InternalName.itemTinCan));
		Ic2Items.filledTinCan = new ItemStack(new ItemTinCan(config, InternalName.itemTinCanFilled));
		Ic2Items.airCell = new ItemStack(new ItemIC2(config, InternalName.itemCellAir));
		Ic2Items.reactorUraniumSimple = new ItemStack(new ItemReactorUranium(config, InternalName.reactorUraniumSimple, 1));
		Ic2Items.reactorUraniumDual = new ItemStack(new ItemReactorUranium(config, InternalName.reactorUraniumDual, 2));
		Ic2Items.reactorUraniumQuad = new ItemStack(new ItemReactorUranium(config, InternalName.reactorUraniumQuad, 4));
		Ic2Items.reactorCoolantSimple = new ItemStack(new ItemReactorHeatStorage(config, InternalName.reactorCoolantSimple, 10000));
		Ic2Items.reactorCoolantTriple = new ItemStack(new ItemReactorHeatStorage(config, InternalName.reactorCoolantTriple, 30000));
		Ic2Items.reactorCoolantSix = new ItemStack(new ItemReactorHeatStorage(config, InternalName.reactorCoolantSix, 60000));
		Ic2Items.reactorPlating = new ItemStack(new ItemReactorPlating(config, InternalName.reactorPlating, 1000, 0.95F));
		Ic2Items.reactorPlatingHeat = new ItemStack(new ItemReactorPlating(config, InternalName.reactorPlatingHeat, 2000, 0.99F));
		Ic2Items.reactorPlatingExplosive = new ItemStack(new ItemReactorPlating(config, InternalName.reactorPlatingExplosive, 500, 0.9F));
		Ic2Items.reactorHeatSwitch = new ItemStack(new ItemReactorHeatSwitch(config, InternalName.reactorHeatSwitch, 2500, 12, 4));
		Ic2Items.reactorHeatSwitchCore = new ItemStack(new ItemReactorHeatSwitch(config, InternalName.reactorHeatSwitchCore, 5000, 0, 72));
		Ic2Items.reactorHeatSwitchSpread = new ItemStack(new ItemReactorHeatSwitch(config, InternalName.reactorHeatSwitchSpread, 5000, 36, 0));
		Ic2Items.reactorHeatSwitchDiamond = new ItemStack(new ItemReactorHeatSwitch(config, InternalName.reactorHeatSwitchDiamond, 10000, 24, 8));
		Ic2Items.reactorVent = new ItemStack(new ItemReactorVent(config, InternalName.reactorVent, 1000, 6, 0));
		Ic2Items.reactorVentCore = new ItemStack(new ItemReactorVent(config, InternalName.reactorVentCore, 1000, 5, 5));
		Ic2Items.reactorVentGold = new ItemStack(new ItemReactorVent(config, InternalName.reactorVentGold, 1000, 20, 36));
		Ic2Items.reactorVentSpread = new ItemStack(new ItemReactorVentSpread(config, InternalName.reactorVentSpread, 4));
		Ic2Items.reactorVentDiamond = new ItemStack(new ItemReactorVent(config, InternalName.reactorVentDiamond, 1000, 12, 0));
		Ic2Items.reactorIsotopeCell = new ItemStack(new ItemReactorDepletedUranium(config, InternalName.reactorIsotopeCell));
		Ic2Items.reEnrichedUraniumCell = new ItemStack(new ItemIC2(config, InternalName.itemCellUranEnriched));
		Ic2Items.nearDepletedUraniumCell = new ItemStack(new ItemIC2(config, InternalName.itemCellUranEmpty));
		Ic2Items.reactorHeatpack = new ItemStack(new ItemReactorHeatpack(config, InternalName.reactorHeatpack, 1000, 1));
		Ic2Items.reactorReflector = new ItemStack(new ItemReactorReflector(config, InternalName.reactorReflector, 10000));
		Ic2Items.reactorReflectorThick = new ItemStack(new ItemReactorReflector(config, InternalName.reactorReflectorThick, 40000));
		Ic2Items.reactorCondensator = new ItemStack(new ItemReactorCondensator(config, InternalName.reactorCondensator, 20000));
		Ic2Items.reactorCondensatorLap = new ItemStack(new ItemReactorCondensator(config, InternalName.reactorCondensatorLap, 0x186a0));
		Ic2Items.terraformerBlueprint = new ItemStack(new ItemIC2(config, InternalName.itemTFBP));
		Ic2Items.cultivationTerraformerBlueprint = new ItemStack(new ItemTFBPCultivation(config, InternalName.itemTFBPCultivation));
		Ic2Items.irrigationTerraformerBlueprint = new ItemStack(new ItemTFBPIrrigation(config, InternalName.itemTFBPIrrigation));
		Ic2Items.chillingTerraformerBlueprint = new ItemStack(new ItemTFBPChilling(config, InternalName.itemTFBPChilling));
		Ic2Items.desertificationTerraformerBlueprint = new ItemStack(new ItemTFBPDesertification(config, InternalName.itemTFBPDesertification));
		Ic2Items.flatificatorTerraformerBlueprint = new ItemStack(new ItemTFBPFlatification(config, InternalName.itemTFBPFlatification));
		Ic2Items.mushroomTerraformerBlueprint = new ItemStack(new ItemTFBPMushroom(config, InternalName.itemTFBPMushroom));
		Ic2Items.coalBall = new ItemStack(new ItemIC2(config, InternalName.itemPartCoalBall));
		Ic2Items.compressedCoalBall = new ItemStack(new ItemIC2(config, InternalName.itemPartCoalBlock));
		Ic2Items.coalChunk = new ItemStack(new ItemIC2(config, InternalName.itemPartCoalChunk));
		Ic2Items.industrialDiamond = new ItemStack((new ItemIC2(config, InternalName.itemPartIndustrialDiamond)).setUnlocalizedName("itemPartIndustrialDiamond"));
		Ic2Items.scrap = new ItemStack(new ItemIC2(config, InternalName.itemScrap));
		Ic2Items.scrapBox = new ItemStack(new ItemScrapbox(config, InternalName.itemScrapbox));
		Ic2Items.hydratedCoalClump = new ItemStack(new ItemIC2(config, InternalName.itemFuelCoalCmpr));
		Ic2Items.plantBall = new ItemStack(new ItemIC2(config, InternalName.itemFuelPlantBall));
		Ic2Items.compressedPlantBall = new ItemStack(new ItemIC2(config, InternalName.itemFuelPlantCmpr));
		Ic2Items.painter = new ItemStack(new ItemIC2(config, InternalName.itemToolPainter));
		Ic2Items.blackPainter = new ItemStack(new ItemToolPainter(config, InternalName.itemToolPainterBlack, 0));
		Ic2Items.redPainter = new ItemStack(new ItemToolPainter(config, InternalName.itemToolPainterRed, 1));
		Ic2Items.greenPainter = new ItemStack(new ItemToolPainter(config, InternalName.itemToolPainterGreen, 2));
		Ic2Items.brownPainter = new ItemStack(new ItemToolPainter(config, InternalName.itemToolPainterBrown, 3));
		Ic2Items.bluePainter = new ItemStack(new ItemToolPainter(config, InternalName.itemToolPainterBlue, 4));
		Ic2Items.purplePainter = new ItemStack(new ItemToolPainter(config, InternalName.itemToolPainterPurple, 5));
		Ic2Items.cyanPainter = new ItemStack(new ItemToolPainter(config, InternalName.itemToolPainterCyan, 6));
		Ic2Items.lightGreyPainter = new ItemStack(new ItemToolPainter(config, InternalName.itemToolPainterLightGrey, 7));
		Ic2Items.darkGreyPainter = new ItemStack(new ItemToolPainter(config, InternalName.itemToolPainterDarkGrey, 8));
		Ic2Items.pinkPainter = new ItemStack(new ItemToolPainter(config, InternalName.itemToolPainterPink, 9));
		Ic2Items.limePainter = new ItemStack(new ItemToolPainter(config, InternalName.itemToolPainterLime, 10));
		Ic2Items.yellowPainter = new ItemStack(new ItemToolPainter(config, InternalName.itemToolPainterYellow, 11));
		Ic2Items.cloudPainter = new ItemStack(new ItemToolPainter(config, InternalName.itemToolPainterCloud, 12));
		Ic2Items.magentaPainter = new ItemStack(new ItemToolPainter(config, InternalName.itemToolPainterMagenta, 13));
		Ic2Items.orangePainter = new ItemStack(new ItemToolPainter(config, InternalName.itemToolPainterOrange, 14));
		Ic2Items.whitePainter = new ItemStack(new ItemToolPainter(config, InternalName.itemToolPainterWhite, 15));
		Ic2Items.dynamite = new ItemStack(new ItemDynamite(config, InternalName.itemDynamite, false));
		Ic2Items.stickyDynamite = new ItemStack(new ItemDynamite(config, InternalName.itemDynamiteSticky, true));
		Ic2Items.remote = new ItemStack(new ItemRemote(config, InternalName.itemRemote));
		new ItemUpgradeModule(config, InternalName.upgradeModule);
		Ic2Items.coin = new ItemStack(new ItemIC2(config, InternalName.itemCoin));
		Ic2Items.reinforcedDoor = new ItemStack(new ItemIC2Door(config, InternalName.itemDoorAlloy, Block.blocksList[Ic2Items.reinforcedDoorBlock.itemID]));
		Ic2Items.constructionFoamPellet = new ItemStack(new ItemIC2(config, InternalName.itemPartPellet));
		Ic2Items.grinPowder = new ItemStack(new ItemIC2(config, InternalName.itemGrinPowder));
		Ic2Items.debug = new ItemStack(new ItemDebug(config, InternalName.itemDebug));
		Ic2Items.coolant = new ItemStack(new ItemIC2(config, InternalName.itemCoolant));
		new ItemIC2Boat(config, InternalName.itemBoat);
		Ic2Items.cropSeed = new ItemStack(new ItemCropSeed(config, InternalName.itemCropSeed));
		Ic2Items.cropnalyzer = new ItemStack(new ItemCropnalyzer(config, InternalName.itemCropnalyzer));
		Ic2Items.fertilizer = new ItemStack(new ItemFertilizer(config, InternalName.itemFertilizer));
		Ic2Items.hydratingCell = new ItemStack(new ItemGradual(config, InternalName.itemCellHydrant));
		Ic2Items.electricHoe = new ItemStack(new ItemElectricToolHoe(config, InternalName.itemToolHoe));
		Ic2Items.terraWart = new ItemStack(new ItemTerraWart(config, InternalName.itemTerraWart));
		Ic2Items.weedEx = new ItemStack((new ItemIC2(config, InternalName.itemWeedEx)).setMaxStackSize(1).setMaxDamage(64));
		Ic2Items.mugEmpty = new ItemStack(new ItemMug(config, InternalName.itemMugEmpty));
		Ic2Items.coffeeBeans = new ItemStack(new ItemIC2(config, InternalName.itemCofeeBeans));
		Ic2Items.coffeePowder = new ItemStack(new ItemIC2(config, InternalName.itemCofeePowder));
		Ic2Items.mugCoffee = new ItemStack(new ItemMugCoffee(config, InternalName.itemMugCoffee));
		Ic2Items.hops = new ItemStack(new ItemIC2(config, InternalName.itemHops));
		Ic2Items.barrel = new ItemStack(new ItemBarrel(config, InternalName.itemBarrel));
		Ic2Items.mugBooze = new ItemStack(new ItemBooze(config, InternalName.itemMugBooze));
		new ItemMigrate(config, InternalName.itemNanoSaberOff, Ic2Items.nanoSaber.getItem());
		Block.obsidian.setResistance(60F);
		Block.enchantmentTable.setResistance(60F);
		Block.enderChest.setResistance(60F);
		Block.anvil.setResistance(60F);
		Block.waterMoving.setResistance(30F);
		Block.waterStill.setResistance(30F);
		Block.lavaStill.setResistance(30F);
		((BlockIC2Door)Block.blocksList[Ic2Items.reinforcedDoorBlock.itemID]).setItemDropped(Ic2Items.reinforcedDoor.itemID);
		ElectricItem.manager = new GatewayElectricItemManager();
		ElectricItem.rawManager = new ElectricItemManager();
		ItemInfo itemInfo = new ItemInfo();
		Info.itemEnergy = itemInfo;
		Info.itemFuel = itemInfo;
		ExplosionWhitelist.addWhitelistedBlock(Block.bedrock);
		Recipes.matterAmplifier = new BasicMachineRecipeManager();
		Recipes.matterAmplifier.addRecipe(Ic2Items.scrap, Integer.valueOf(5000));
		Recipes.matterAmplifier.addRecipe(Ic2Items.scrapBox, Integer.valueOf(45000));
		FurnaceRecipes furnaceRecipes = FurnaceRecipes.smelting();
		if (Ic2Items.rubberWood != null)
			furnaceRecipes.addSmelting(Ic2Items.rubberWood.itemID, Ic2Items.rubberWood.getItemDamage(), new ItemStack(Block.wood, 1, 3), 0.1F);
		if (Ic2Items.tinOre != null)
			furnaceRecipes.addSmelting(Ic2Items.tinOre.itemID, Ic2Items.tinOre.getItemDamage(), Ic2Items.tinIngot, 0.5F);
		if (Ic2Items.copperOre != null)
			furnaceRecipes.addSmelting(Ic2Items.copperOre.itemID, Ic2Items.copperOre.getItemDamage(), Ic2Items.copperIngot, 0.5F);
		furnaceRecipes.addSmelting(Item.ingotIron.itemID, Ic2Items.refinedIronIngot, 0.2F);
		furnaceRecipes.addSmelting(Ic2Items.ironDust.itemID, Ic2Items.ironDust.getItemDamage(), new ItemStack(Item.ingotIron, 1), 0.0F);
		furnaceRecipes.addSmelting(Ic2Items.goldDust.itemID, Ic2Items.goldDust.getItemDamage(), new ItemStack(Item.ingotGold, 1), 0.0F);
		furnaceRecipes.addSmelting(Ic2Items.tinDust.itemID, Ic2Items.tinDust.getItemDamage(), Ic2Items.tinIngot.copy(), 0.0F);
		furnaceRecipes.addSmelting(Ic2Items.copperDust.itemID, Ic2Items.copperDust.getItemDamage(), Ic2Items.copperIngot.copy(), 0.0F);
		furnaceRecipes.addSmelting(Ic2Items.hydratedCoalDust.itemID, Ic2Items.hydratedCoalDust.getItemDamage(), Ic2Items.coalDust.copy(), 0.0F);
		furnaceRecipes.addSmelting(Ic2Items.bronzeDust.itemID, Ic2Items.bronzeDust.getItemDamage(), Ic2Items.bronzeIngot.copy(), 0.0F);
		furnaceRecipes.addSmelting(Ic2Items.resin.itemID, Ic2Items.resin.getItemDamage(), Ic2Items.rubber.copy(), 0.3F);
		furnaceRecipes.addSmelting(Ic2Items.mugCoffee.itemID, new ItemStack(Ic2Items.mugCoffee.getItem(), 1, 1), 0.1F);
		((ItemElectricToolChainsaw)Ic2Items.chainsaw.getItem()).init();
		((ItemElectricToolDrill)Ic2Items.miningDrill.getItem()).init();
		((ItemElectricToolDDrill)Ic2Items.diamondDrill.getItem()).init();
		ItemScrapbox.init();
		ItemTFBPCultivation.init();
		ItemTFBPFlatification.init();
		TileEntityCompressor.init();
		TileEntityExtractor.init();
		TileEntityMacerator.init();
		TileEntityRecycler.init(config);
		MinecraftForge.setToolClass(Ic2Items.bronzePickaxe.getItem(), "pickaxe", 2);
		MinecraftForge.setToolClass(Ic2Items.bronzeAxe.getItem(), "axe", 2);
		MinecraftForge.setToolClass(Ic2Items.bronzeShovel.getItem(), "shovel", 2);
		MinecraftForge.setToolClass(Ic2Items.chainsaw.getItem(), "axe", 2);
		MinecraftForge.setToolClass(Ic2Items.miningDrill.getItem(), "pickaxe", 2);
		MinecraftForge.setToolClass(Ic2Items.diamondDrill.getItem(), "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(Block.blocksList[Ic2Items.reinforcedStone.itemID], "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(Block.blocksList[Ic2Items.reinforcedDoorBlock.itemID], "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(Block.blocksList[Ic2Items.insulatedCopperCableBlock.itemID], "axe", 0);
		MinecraftForge.setBlockHarvestLevel(Block.blocksList[Ic2Items.constructionFoamWall.itemID], "pickaxe", 1);
		if (Ic2Items.copperOre != null)
			MinecraftForge.setBlockHarvestLevel(Block.blocksList[Ic2Items.copperOre.itemID], "pickaxe", 1);
		if (Ic2Items.tinOre != null)
			MinecraftForge.setBlockHarvestLevel(Block.blocksList[Ic2Items.tinOre.itemID], "pickaxe", 1);
		if (Ic2Items.uraniumOre != null)
			MinecraftForge.setBlockHarvestLevel(Block.blocksList[Ic2Items.uraniumOre.itemID], "pickaxe", 2);
		if (Ic2Items.rubberWood != null)
			MinecraftForge.setBlockHarvestLevel(Block.blocksList[Ic2Items.rubberWood.itemID], "axe", 0);
		windStrength = 10 + random.nextInt(10);
		windTicker = 0;
		Block.setBurnProperties(Ic2Items.scaffold.itemID, 8, 20);
		if (Ic2Items.rubberLeaves != null)
			Block.setBurnProperties(Ic2Items.rubberLeaves.itemID, 30, 20);
		if (Ic2Items.rubberWood != null)
			Block.setBurnProperties(Ic2Items.rubberWood.itemID, 4, 20);
		MinecraftForge.EVENT_BUS.register(this);
		Recipies.registerCraftingRecipes();
		String arr$[] = OreDictionary.getOreNames();
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			String oreName = arr$[i$];
			ItemStack ore;
			for (Iterator i$ = OreDictionary.getOres(oreName).iterator(); i$.hasNext(); registerOre(new net.minecraftforge.oredict.OreDictionary.OreRegisterEvent(oreName, ore)))
				ore = (ItemStack)i$.next();

		}

		if (!$assertionsDisabled && Ic2Items.uraniumDrop == null)
			throw new AssertionError();
		if (!$assertionsDisabled && Ic2Items.bronzeIngot == null)
			throw new AssertionError();
		if (!$assertionsDisabled && Ic2Items.copperIngot == null)
			throw new AssertionError();
		if (!$assertionsDisabled && Ic2Items.refinedIronIngot == null)
			throw new AssertionError();
		if (!$assertionsDisabled && Ic2Items.tinIngot == null)
			throw new AssertionError();
		if (!$assertionsDisabled && Ic2Items.uraniumIngot == null)
			throw new AssertionError();
		if (!$assertionsDisabled && Ic2Items.rubber == null)
			throw new AssertionError();
		if (Ic2Items.copperOre != null)
			OreDictionary.registerOre("oreCopper", Ic2Items.copperOre);
		if (Ic2Items.tinOre != null)
			OreDictionary.registerOre("oreTin", Ic2Items.tinOre);
		if (Ic2Items.uraniumOre != null)
			OreDictionary.registerOre("oreUranium", Ic2Items.uraniumOre);
		OreDictionary.registerOre("dropUranium", Ic2Items.uraniumDrop);
		OreDictionary.registerOre("dustBronze", Ic2Items.bronzeDust);
		OreDictionary.registerOre("dustClay", Ic2Items.clayDust);
		OreDictionary.registerOre("dustCoal", Ic2Items.coalDust);
		OreDictionary.registerOre("dustCopper", Ic2Items.copperDust);
		OreDictionary.registerOre("dustGold", Ic2Items.goldDust);
		OreDictionary.registerOre("dustIron", Ic2Items.ironDust);
		OreDictionary.registerOre("dustSilver", Ic2Items.silverDust);
		OreDictionary.registerOre("dustTin", Ic2Items.tinDust);
		OreDictionary.registerOre("ingotBronze", Ic2Items.bronzeIngot);
		OreDictionary.registerOre("ingotCopper", Ic2Items.copperIngot);
		OreDictionary.registerOre("ingotRefinedIron", Ic2Items.refinedIronIngot);
		OreDictionary.registerOre("ingotTin", Ic2Items.tinIngot);
		OreDictionary.registerOre("ingotUranium", Ic2Items.uraniumIngot);
		OreDictionary.registerOre("itemRubber", Ic2Items.rubber);
		OreDictionary.registerOre("blockBronze", Ic2Items.bronzeBlock);
		OreDictionary.registerOre("blockCopper", Ic2Items.copperBlock);
		OreDictionary.registerOre("blockTin", Ic2Items.tinBlock);
		OreDictionary.registerOre("blockUranium", Ic2Items.uraniumBlock);
		if (Ic2Items.rubberWood != null)
			OreDictionary.registerOre("woodRubber", Ic2Items.rubberWood);
		EnergyNet.initialize();
		IC2Crops.init();
		Crops.instance.addBiomeBonus(BiomeGenBase.river, 2, 0);
		Crops.instance.addBiomeBonus(BiomeGenBase.swampland, 2, 2);
		Crops.instance.addBiomeBonus(BiomeGenBase.forest, 1, 1);
		Crops.instance.addBiomeBonus(BiomeGenBase.forestHills, 1, 1);
		Crops.instance.addBiomeBonus(BiomeGenBase.jungle, 1, 2);
		Crops.instance.addBiomeBonus(BiomeGenBase.jungleHills, 1, 2);
		Crops.instance.addBiomeBonus(BiomeGenBase.desert, -1, 0);
		Crops.instance.addBiomeBonus(BiomeGenBase.desertHills, -1, 0);
		Crops.instance.addBiomeBonus(BiomeGenBase.mushroomIsland, 0, 2);
		Crops.instance.addBiomeBonus(BiomeGenBase.mushroomIslandShore, 0, 2);
		IC2Potion.init();
		new IC2Loot();
		achievements = new IC2Achievements();
		enableDynamicIdAllocation = false;
		if (dynamicIdAllocationProp != null)
			dynamicIdAllocationProp.set(false);
		if (config != null && config.hasChanged())
			config.save();
		EntityRegistry.registerModEntity(ic2/core/item/tool/EntityMiningLaser, "MiningLaser", 0, this, 160, 5, true);
		EntityRegistry.registerModEntity(ic2/core/block/EntityDynamite, "Dynamite", 1, this, 160, 5, true);
		EntityRegistry.registerModEntity(ic2/core/block/EntityStickyDynamite, "StickyDynamite", 2, this, 160, 5, true);
		EntityRegistry.registerModEntity(ic2/core/block/EntityItnt, "Itnt", 3, this, 160, 5, true);
		EntityRegistry.registerModEntity(ic2/core/block/EntityNuke, "Nuke", 4, this, 160, 5, true);
		EntityRegistry.registerModEntity(ic2/core/item/EntityBoatCarbon, "BoatCarbon", 5, this, 80, 3, true);
		EntityRegistry.registerModEntity(ic2/core/item/EntityBoatRubber, "BoatRubber", 6, this, 80, 3, true);
		EntityRegistry.registerModEntity(ic2/core/item/EntityBoatElectric, "BoatElectric", 7, this, 80, 3, true);
		int d = Integer.parseInt((new SimpleDateFormat("Mdd")).format(new Date()));
		suddenlyHoes = (double)d > Math.cbrt(64000000D) && (double)d < Math.cbrt(65939264D);
		seasonal = (double)d > Math.cbrt(1089547389D) && (double)d < Math.cbrt(1338273208D);
		TickRegistry.registerTickHandler(this, Side.CLIENT);
		TickRegistry.registerTickHandler(this, Side.SERVER);
		GameRegistry.registerWorldGenerator(this);
		GameRegistry.registerFuelHandler(this);
		NetworkRegistry.instance().registerConnectionHandler(this);
		GameRegistry.registerPlayerTracker(this);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER, Ic2Items.waterCell.copy(), Ic2Items.cell.copy());
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.LAVA, Ic2Items.lavaCell.copy(), Ic2Items.cell.copy());
		Fluid coolant = new Fluid("coolant");
		FluidRegistry.registerFluid(coolant);
		FluidContainerRegistry.registerFluidContainer(coolant, Ic2Items.reactorCoolantSimple.copy(), Ic2Items.cell.copy());
		Localization.init(event.getSourceFile());
		initialized = true;
	}

	public void modsLoaded(FMLPostInitializationEvent event)
	{
		if (!initialized)
			platform.displayError("IndustrialCraft 2 has failed to initialize properly.");
		if (loadSubModule("bcIntegration"))
			log.info("BuildCraft integration module loaded");
		String noGrab = (new StringBuilder()).append("").append(Ic2Items.miningPipe.itemID).append(", ").append(Ic2Items.miningPipeTip.itemID).append(", ").append(Ic2Items.scaffold.itemID).append(", ").append(Ic2Items.rubberTrampoline.itemID).toString();
		if (Ic2Items.rubberWood != null)
			noGrab = (new StringBuilder()).append(noGrab).append(", ").append(Ic2Items.rubberWood.itemID).append(": 2: 3: 4: 5: 6: 7: 8: 9: 10: 11").toString();
		FMLInterModComms.sendMessage("PortalGun", "addBlockIDToGrabList", noGrab);
		FMLInterModComms.sendMessage("GraviGun", "addBlockIDToGrabList", noGrab);
		try
		{
			Method addCustomItem = Class.forName("mod_Gibbing").getMethod("addCustomItem", new Class[] {
				Integer.TYPE, Double.TYPE
			});
			addCustomItem.invoke(null, new Object[] {
				Integer.valueOf(Ic2Items.nanoSaber.itemID), Double.valueOf(0.5D)
			});
			addCustomItem.invoke(null, new Object[] {
				Integer.valueOf(Ic2Items.chainsaw.itemID), Double.valueOf(0.5D)
			});
			addCustomItem.invoke(null, new Object[] {
				Integer.valueOf(Ic2Items.miningDrill.itemID), Double.valueOf(0.33300000000000002D)
			});
			addCustomItem.invoke(null, new Object[] {
				Integer.valueOf(Ic2Items.diamondDrill.itemID), Double.valueOf(0.33300000000000002D)
			});
		}
		catch (Throwable e) { }
		try
		{
			Field axes = Class.forName("mod_Timber").getDeclaredField("axes");
			axes.set(null, (new StringBuilder()).append(axes.get(null)).append(", ").append(Ic2Items.bronzeAxe.itemID).append(", ").append(Ic2Items.chainsaw.itemID).toString());
		}
		catch (Throwable e) { }
		GameRegistry.registerTileEntity(ic2/core/block/TileEntityBlock, "Empty Management TileEntity");
		GameRegistry.registerTileEntity(ic2/core/block/machine/tileentity/TileEntityIronFurnace, "Iron Furnace");
		GameRegistry.registerTileEntity(ic2/core/block/machine/tileentity/TileEntityElectricFurnace, "Electric Furnace");
		GameRegistry.registerTileEntity(ic2/core/block/machine/tileentity/TileEntityMacerator, "Macerator");
		GameRegistry.registerTileEntity(ic2/core/block/machine/tileentity/TileEntityExtractor, "Extractor");
		GameRegistry.registerTileEntity(ic2/core/block/machine/tileentity/TileEntityCompressor, "Compressor");
		GameRegistry.registerTileEntity(ic2/core/block/generator/tileentity/TileEntityGenerator, "Generator");
		GameRegistry.registerTileEntity(ic2/core/block/generator/tileentity/TileEntityGeoGenerator, "Geothermal Generator");
		GameRegistry.registerTileEntity(ic2/core/block/generator/tileentity/TileEntityWaterGenerator, "Water Mill");
		GameRegistry.registerTileEntity(ic2/core/block/generator/tileentity/TileEntitySolarGenerator, "Solar Panel");
		GameRegistry.registerTileEntity(ic2/core/block/generator/tileentity/TileEntityWindGenerator, "Wind Mill");
		GameRegistry.registerTileEntity(ic2/core/block/machine/tileentity/TileEntityCanner, "Canning Machine");
		GameRegistry.registerTileEntity(ic2/core/block/machine/tileentity/TileEntityMiner, "Miner");
		GameRegistry.registerTileEntity(ic2/core/block/machine/tileentity/TileEntityPump, "Pump");
		if (BlockGenerator.tileEntityNuclearReactorClass == ic2/core/block/generator/tileentity/TileEntityNuclearReactorElectric)
			GameRegistry.registerTileEntity(ic2/core/block/generator/tileentity/TileEntityNuclearReactorElectric, "Nuclear Reactor");
		if (BlockReactorChamber.tileEntityReactorChamberClass == ic2/core/block/generator/tileentity/TileEntityReactorChamberElectric)
			GameRegistry.registerTileEntity(ic2/core/block/generator/tileentity/TileEntityReactorChamberElectric, "Reactor Chamber");
		GameRegistry.registerTileEntity(ic2/core/block/machine/tileentity/TileEntityMagnetizer, "Magnetizer");
		GameRegistry.registerTileEntity(ic2/core/block/wiring/TileEntityCable, "Cable");
		GameRegistry.registerTileEntity(ic2/core/block/wiring/TileEntityElectricBatBox, "BatBox");
		GameRegistry.registerTileEntity(ic2/core/block/wiring/TileEntityElectricMFE, "MFE");
		GameRegistry.registerTileEntity(ic2/core/block/wiring/TileEntityElectricMFSU, "MFSU");
		GameRegistry.registerTileEntity(ic2/core/block/wiring/TileEntityTransformerLV, "LV-Transformer");
		GameRegistry.registerTileEntity(ic2/core/block/wiring/TileEntityTransformerMV, "MV-Transformer");
		GameRegistry.registerTileEntity(ic2/core/block/wiring/TileEntityTransformerHV, "HV-Transformer");
		GameRegistry.registerTileEntity(ic2/core/block/wiring/TileEntityLuminator, "Luminator");
		GameRegistry.registerTileEntity(ic2/core/block/machine/tileentity/TileEntityElectrolyzer, "Electrolyzer");
		if (BlockPersonal.tileEntityPersonalChestClass == ic2/core/block/personal/TileEntityPersonalChest)
			GameRegistry.registerTileEntity(ic2/core/block/personal/TileEntityPersonalChest, "Personal Safe");
		GameRegistry.registerTileEntity(ic2/core/block/personal/TileEntityTradeOMat, "Trade-O-Mat");
		GameRegistry.registerTileEntity(ic2/core/block/personal/TileEntityEnergyOMat, "Energy-O-Mat");
		GameRegistry.registerTileEntity(ic2/core/block/machine/tileentity/TileEntityRecycler, "Recycler");
		GameRegistry.registerTileEntity(ic2/core/block/machine/tileentity/TileEntityInduction, "Induction Furnace");
		GameRegistry.registerTileEntity(ic2/core/block/machine/tileentity/TileEntityMatter, "Mass Fabricator");
		GameRegistry.registerTileEntity(ic2/core/block/machine/tileentity/TileEntityTerra, "Terraformer");
		GameRegistry.registerTileEntity(ic2/core/block/machine/tileentity/TileEntityTeleporter, "Teleporter");
		GameRegistry.registerTileEntity(ic2/core/block/machine/tileentity/TileEntityTesla, "Tesla Coil");
		GameRegistry.registerTileEntity(ic2/core/block/wiring/TileEntityCableDetector, "Detector Cable");
		GameRegistry.registerTileEntity(ic2/core/block/wiring/TileEntityCableSplitter, "SplitterCable");
		GameRegistry.registerTileEntity(ic2/core/block/TileEntityCrop, "TECrop");
		GameRegistry.registerTileEntity(ic2/core/block/TileEntityBarrel, "TEBarrel");
		GameRegistry.registerTileEntity(ic2/core/block/machine/tileentity/TileEntityCropmatron, "Crop-Matron");
		GameRegistry.registerTileEntity(ic2/core/block/TileEntityWall, "CF-Wall");
		platform.onPostInit();
	}

	private static boolean loadSubModule(String name)
	{
		log.info((new StringBuilder()).append("Loading IC2 submodule: ").append(name).toString());
		Class subModuleClass = ic2/core/IC2.getClassLoader().loadClass((new StringBuilder()).append("ic2.").append(name).append(".SubModule").toString());
		return ((Boolean)subModuleClass.getMethod("init", new Class[0]).invoke(null, new Object[0])).booleanValue();
		Throwable t;
		t;
		log.info((new StringBuilder()).append("Submodule ").append(name).append(" not loaded").toString());
		return false;
	}

	public int getBurnTime(ItemStack stack)
	{
		if (Ic2Items.rubberSapling != null && stack.equals(Ic2Items.rubberSapling))
			return 80;
		if (stack.itemID == Item.reed.itemID)
			return 50;
		if (stack.itemID == Block.cactus.blockID)
			return 50;
		if (stack.itemID == Ic2Items.scrap.itemID)
			return 350;
		if (stack.itemID == Ic2Items.scrapBox.itemID)
			return 3150;
		if (stack.itemID == Ic2Items.lavaCell.itemID)
			return TileEntityFurnace.getItemBurnTime(new ItemStack(Item.bucketLava));
		else
			return 0;
	}

	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		if (enableWorldGenTreeRubber)
		{
			BiomeGenBase biomegenbase = world.getWorldChunkManager().getBiomeGenAt(chunkX * 16 + 16, chunkZ * 16 + 16);
			if (biomegenbase != null && biomegenbase.biomeName != null)
			{
				int rubbertrees = 0;
				if (biomegenbase.biomeName.toLowerCase().contains("taiga"))
					rubbertrees += random.nextInt(3);
				if (biomegenbase.biomeName.toLowerCase().contains("forest") || biomegenbase.biomeName.toLowerCase().contains("jungle"))
					rubbertrees += random.nextInt(5) + 1;
				if (biomegenbase.biomeName.toLowerCase().contains("swamp"))
					rubbertrees += random.nextInt(10) + 5;
				if (random.nextInt(100) + 1 <= rubbertrees * 2)
					(new WorldGenRubTree()).generate(world, random, chunkX * 16 + random.nextInt(16), rubbertrees, chunkZ * 16 + random.nextInt(16));
			}
		}
		int baseHeight = getSeaLevel(world) + 1;
		int baseScale = Math.round((float)baseHeight * oreDensityFactor);
		if (enableWorldGenOreCopper && Ic2Items.copperOre != null)
		{
			int baseCount = (15 * baseScale) / 64;
			int count = (int)Math.round(random.nextGaussian() * Math.sqrt(baseCount) + (double)baseCount);
			for (int n = 0; n < count; n++)
			{
				int x = chunkX * 16 + random.nextInt(16);
				int y = random.nextInt((40 * baseHeight) / 64) + random.nextInt((20 * baseHeight) / 64) + (10 * baseHeight) / 64;
				int z = chunkZ * 16 + random.nextInt(16);
				(new WorldGenMinable(Ic2Items.copperOre.itemID, 10)).generate(world, random, x, y, z);
			}

		}
		if (enableWorldGenOreTin && Ic2Items.tinOre != null)
		{
			int baseCount = (25 * baseScale) / 64;
			int count = (int)Math.round(random.nextGaussian() * Math.sqrt(baseCount) + (double)baseCount);
			for (int n = 0; n < count; n++)
			{
				int x = chunkX * 16 + random.nextInt(16);
				int y = random.nextInt((40 * baseHeight) / 64);
				int z = chunkZ * 16 + random.nextInt(16);
				(new WorldGenMinable(Ic2Items.tinOre.itemID, 6)).generate(world, random, x, y, z);
			}

		}
		if (enableWorldGenOreUranium && Ic2Items.uraniumOre != null)
		{
			int baseCount = (20 * baseScale) / 64;
			int count = (int)Math.round(random.nextGaussian() * Math.sqrt(baseCount) + (double)baseCount);
			for (int n = 0; n < count; n++)
			{
				int x = chunkX * 16 + random.nextInt(16);
				int y = random.nextInt((64 * baseHeight) / 64);
				int z = chunkZ * 16 + random.nextInt(16);
				(new WorldGenMinable(Ic2Items.uraniumOre.itemID, 3)).generate(world, random, x, y, z);
			}

		}
	}

	public transient void tickStart(EnumSet type, Object tickData[])
	{
		if (type.contains(TickType.WORLD))
		{
			platform.profilerStartSection("Init");
			World world = (World)tickData[0];
			platform.profilerEndStartSection("Wind");
			if (windTicker % 128 == 0)
				updateWind(world);
			windTicker++;
			textureIndex.t++;
			ItemNanoSaber.ticker++;
			platform.profilerEndStartSection("EnergyNet");
			EnergyNet.onTick(world);
			platform.profilerEndStartSection("Networking");
			network.onTick(world);
			platform.profilerEndStartSection("TickCallbacks");
			processTickCallbacks(world);
			platform.profilerEndSection();
		}
		if (type.contains(TickType.WORLDLOAD) && platform.isSimulating())
		{
			Integer arr$[] = DimensionManager.getIDs();
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				int worldId = arr$[i$].intValue();
				World world = DimensionManager.getProvider(worldId).worldObj;
				if (world == null || !(world.getSaveHandler() instanceof SaveHandler))
					continue;
				SaveHandler saveHandler = (SaveHandler)world.getSaveHandler();
				File saveFolder = null;
				Field arr$[] = net/minecraft/world/storage/SaveHandler.getDeclaredFields();
				int len$ = arr$.length;
				for (int i$ = 0; i$ < len$; i$++)
				{
					Field field = arr$[i$];
					if (field.getType() != java/io/File)
						continue;
					field.setAccessible(true);
					try
					{
						File file = (File)field.get(saveHandler);
						if (saveFolder == null || saveFolder.getParentFile() == file)
							saveFolder = file;
					}
					catch (Exception e) { }
				}

				if (saveFolder == null)
					continue;
				try
				{
					Properties mapIdProperties = new Properties() {

						final IC2 this$0;

						public Set keySet()
						{
							return Collections.unmodifiableSet(new TreeSet(super.keySet()));
						}

						public synchronized Enumeration keys()
						{
							return Collections.enumeration(new TreeSet(super.keySet()));
						}

			
			{
				this$0 = IC2.this;
				super();
			}
					};
					mapIdProperties.putAll(runtimeIdProperties);
					File mapIdPropertiesFile = new File(saveFolder, "ic2_map.cfg");
					if (mapIdPropertiesFile.exists())
					{
						FileInputStream fileInputStream = new FileInputStream(mapIdPropertiesFile);
						Properties properties = new Properties();
						properties.load(fileInputStream);
						fileInputStream.close();
						List outdatedProperties = new Vector();
						Iterator i$ = properties.entrySet().iterator();
						do
						{
							if (!i$.hasNext())
								break;
							java.util.Map.Entry mapEntry = (java.util.Map.Entry)i$.next();
							String key = (String)mapEntry.getKey();
							String value = (String)mapEntry.getValue();
							if (!runtimeIdProperties.containsKey(key))
							{
								outdatedProperties.add(key);
							} else
							{
								int separatorPos = key.indexOf('.');
								if (separatorPos != -1)
								{
									String section = key.substring(0, separatorPos);
									String entry = key.substring(separatorPos + 1);
									if ((section.equals("block") || section.equals("item")) && !value.equals(runtimeIdProperties.get(key)))
										platform.displayError((new StringBuilder()).append("IC2 detected an ID conflict between your IC2.cfg and the map you are\ntrying to load.\n\nMap: ").append(saveFolder.getName()).append("\n").append("\n").append("Config section: ").append(section).append("\n").append("Config entry: ").append(entry).append("\n").append("Config value: ").append(runtimeIdProperties.get(key)).append("\n").append("Map value: ").append(value).append("\n").append("\n").append("Adjust your config to match the IDs used by the map or convert your\n").append("map to use the IDs specified in the config.\n").append("\n").append("See also: config/IC2.cfg ").append(platform.isRendering() ? "saves/" : "").append(saveFolder.getName()).append("/ic2_map.cfg").toString());
								}
							}
						} while (true);
						String key;
						for (i$ = outdatedProperties.iterator(); i$.hasNext(); properties.remove(key))
							key = (String)i$.next();

						mapIdProperties.putAll(properties);
					}
					FileOutputStream fileOutputStream = new FileOutputStream(mapIdPropertiesFile);
					mapIdProperties.store(fileOutputStream, "ic2 map related configuration data");
					fileOutputStream.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				break;
			}

		}
	}

	public void processTickCallbacks(World world)
	{
		WorldData worldData = WorldData.get(world);
		platform.profilerStartSection("SingleTickCallback");
		for (ITickCallback tickCallback = (ITickCallback)worldData.singleTickCallbacks.poll(); tickCallback != null; tickCallback = (ITickCallback)worldData.singleTickCallbacks.poll())
		{
			platform.profilerStartSection(tickCallback.getClass().getName());
			tickCallback.tickCallback(world);
			platform.profilerEndSection();
		}

		platform.profilerEndStartSection("ContTickCallback");
		worldData.continuousTickCallbacksInUse = true;
		for (Iterator i$ = worldData.continuousTickCallbacks.iterator(); i$.hasNext(); platform.profilerEndSection())
		{
			ITickCallback tickCallback = (ITickCallback)i$.next();
			platform.profilerStartSection(tickCallback.getClass().getName());
			tickCallback.tickCallback(world);
		}

		worldData.continuousTickCallbacksInUse = false;
		worldData.continuousTickCallbacks.addAll(worldData.continuousTickCallbacksToAdd);
		worldData.continuousTickCallbacksToAdd.clear();
		worldData.continuousTickCallbacks.removeAll(worldData.continuousTickCallbacksToRemove);
		worldData.continuousTickCallbacksToRemove.clear();
		platform.profilerEndSection();
	}

	public transient void tickEnd(EnumSet enumset, Object aobj[])
	{
	}

	public EnumSet ticks()
	{
		return EnumSet.of(TickType.WORLD, TickType.WORLDLOAD);
	}

	public String getLabel()
	{
		return "IC2";
	}

	public void playerLoggedIn(Player player1, NetHandler nethandler, INetworkManager inetworkmanager)
	{
	}

	public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager)
	{
		return null;
	}

	public void connectionOpened(NetHandler nethandler, String s, int i, INetworkManager inetworkmanager)
	{
	}

	public void connectionOpened(NetHandler nethandler, MinecraftServer minecraftserver, INetworkManager inetworkmanager)
	{
	}

	public void connectionClosed(INetworkManager inetworkmanager)
	{
	}

	public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login)
	{
		network.sendLoginData();
	}

	public void onPlayerLogin(EntityPlayer entityplayer)
	{
	}

	public void onPlayerLogout(EntityPlayer player)
	{
		if (platform.isSimulating())
		{
			ItemArmorQuantumSuit.removePlayerReferences(player);
			keyboard.removePlayerReferences(player);
		}
	}

	public void onPlayerChangedDimension(EntityPlayer entityplayer)
	{
	}

	public void onPlayerRespawn(EntityPlayer entityplayer)
	{
	}

	public void onWorldLoad(net.minecraftforge.event.world.WorldEvent.Load event)
	{
		textureIndex.reset();
	}

	public void onWorldUnload(net.minecraftforge.event.world.WorldEvent.Unload event)
	{
		WorldData.onWorldUnload(event.world);
	}

	public void onTextureStitchPost(net.minecraftforge.client.event.TextureStitchEvent.Post event)
	{
		BlockTextureStitched.onPostStitch();
	}

	public void onChunkWatchEvent(net.minecraftforge.event.world.ChunkWatchEvent.Watch event)
	{
		Chunk chunk = ((Entity) (event.player)).worldObj.getChunkFromChunkCoords(event.chunk.chunkXPos, event.chunk.chunkZPos);
		TileEntity tileEntity;
		for (Iterator i$ = chunk.chunkTileEntityMap.values().iterator(); i$.hasNext(); network.sendInitialData(tileEntity, event.player))
			tileEntity = (TileEntity)i$.next();

	}

	public static void addSingleTickCallback(World world, ITickCallback tickCallback)
	{
		WorldData worldData = WorldData.get(world);
		worldData.singleTickCallbacks.add(tickCallback);
	}

	public static void addContinuousTickCallback(World world, ITickCallback tickCallback)
	{
		WorldData worldData = WorldData.get(world);
		if (!worldData.continuousTickCallbacksInUse)
		{
			worldData.continuousTickCallbacks.add(tickCallback);
		} else
		{
			worldData.continuousTickCallbacksToRemove.remove(tickCallback);
			worldData.continuousTickCallbacksToAdd.add(tickCallback);
		}
	}

	public static void removeContinuousTickCallback(World world, ITickCallback tickCallback)
	{
		WorldData worldData = WorldData.get(world);
		if (!worldData.continuousTickCallbacksInUse)
		{
			worldData.continuousTickCallbacks.remove(tickCallback);
		} else
		{
			worldData.continuousTickCallbacksToAdd.remove(tickCallback);
			worldData.continuousTickCallbacksToRemove.add(tickCallback);
		}
	}

	public static void updateWind(World world)
	{
		if (world.provider.dimensionId != 0)
			return;
		int upChance = 10;
		int downChance = 10;
		if (windStrength > 20)
			upChance -= windStrength - 20;
		if (windStrength < 10)
			downChance -= 10 - windStrength;
		if (random.nextInt(100) <= upChance)
		{
			windStrength++;
			return;
		}
		if (random.nextInt(100) <= downChance)
		{
			windStrength--;
			return;
		} else
		{
			return;
		}
	}

	public static int getBlockIdFor(Configuration config, InternalName internalName, int standardId)
	{
		String name = internalName.name();
		Property prop = null;
		Integer ret;
		if (config == null)
		{
			ret = Integer.valueOf(standardId);
		} else
		{
			prop = config.get("block", name, standardId);
			ret = Integer.valueOf(prop.getInt(standardId));
		}
		if (ret.intValue() <= 0 || ret.intValue() > Block.blocksList.length)
			platform.displayError((new StringBuilder()).append("An invalid block ID has been detected on your IndustrialCraft 2\nconfiguration file. Block IDs cannot be higher than ").append(Block.blocksList.length - 1).append(".\n").append("\n").append("Block with invalid ID: ").append(name).append("\n").append("Invalid ID: ").append(ret).toString());
		if (Block.blocksList[ret.intValue()] != null || Item.itemsList[ret.intValue()] != null)
			if (enableDynamicIdAllocation)
			{
				boolean conflictSolved = false;
				int blockId = Block.blocksList.length - 1;
				do
				{
					if (blockId <= 0)
						break;
					if (!blockedIds.contains(Integer.valueOf(blockId)) && Block.blocksList[blockId] == null && Item.itemsList[blockId] == null)
					{
						if (prop != null)
							prop.set(blockId);
						ret = Integer.valueOf(blockId);
						conflictSolved = true;
						break;
					}
					blockId--;
				} while (true);
				if (!conflictSolved)
					platform.displayError((new StringBuilder()).append("IC2 ran out of block IDs while trying to allocate a new ID.\nTry relocating item IDs to the range above 3840 or remove\n+some mods too free up more IDs.\nBlock with invalid ID: ").append(name).toString());
			} else
			{
				String occupiedBy;
				if (Block.blocksList[ret.intValue()] != null)
					occupiedBy = (new StringBuilder()).append("block ").append(Block.blocksList[ret.intValue()]).append(" (").append(Block.blocksList[ret.intValue()].getUnlocalizedName()).append(")").toString();
				else
					occupiedBy = (new StringBuilder()).append("item ").append(Item.itemsList[ret.intValue()]).append(" (").append(Item.itemsList[ret.intValue()].getUnlocalizedName()).append(")").toString();
				platform.displayError((new StringBuilder()).append("A conflicting block ID has been detected on your IndustrialCraft 2\nconfiguration file. Block IDs cannot be used more than once.\n\nBlock with invalid ID: ").append(name).append("\n").append("Invalid ID: ").append(ret).append("\n").append("Already occupied by: ").append(occupiedBy).toString());
			}
		runtimeIdProperties.setProperty((new StringBuilder()).append("block.").append(name).toString(), ret.toString());
		return ret.intValue();
	}

	public static int getItemIdFor(Configuration config, InternalName internalName, int standardId)
	{
		String name = internalName.name();
		Property prop = null;
		Integer ret;
		if (config == null)
		{
			ret = Integer.valueOf(standardId);
		} else
		{
			prop = config.get("item", name, standardId);
			ret = Integer.valueOf(prop.getInt(standardId));
		}
		if (ret.intValue() < 256 || ret.intValue() > Item.itemsList.length - 256)
			platform.displayError((new StringBuilder()).append("An invalid item ID has been detected on your IndustrialCraft 2\nconfiguration file. Item IDs cannot be lower than 256 or\nhigher than ").append(Item.itemsList.length - 1 - 256).append(".\n").append("\n").append("Item with invalid ID: ").append(name).append("\n").append("Invalid ID: ").append(ret).append(" (").append(ret.intValue() + 256).append(" shifted)").toString());
		if (ret.intValue() + 256 < Block.blocksList.length && Block.blocksList[ret.intValue() + 256] != null || Item.itemsList[ret.intValue() + 256] != null)
			if (enableDynamicIdAllocation)
			{
				boolean conflictSolved = false;
				int itemId = Item.itemsList.length - 1 - 256;
				do
				{
					if (itemId < 256)
						break;
					if (!blockedIds.contains(Integer.valueOf(itemId + 256)) && (itemId >= Block.blocksList.length - 256 || Block.blocksList[itemId + 256] == null) && Item.itemsList[itemId + 256] == null)
					{
						if (prop != null)
							prop.set(itemId);
						ret = Integer.valueOf(itemId);
						conflictSolved = true;
						break;
					}
					itemId--;
				} while (true);
				if (!conflictSolved)
					platform.displayError((new StringBuilder()).append("IC2 ran out of item IDs while trying to allocate a new ID.\nTry removing some mods too free up more IDs.\nItem with invalid ID: ").append(name).toString());
			} else
			{
				String occupiedBy;
				if (ret.intValue() + 256 < Block.blocksList.length && Block.blocksList[ret.intValue() + 256] != null)
					occupiedBy = (new StringBuilder()).append("block ").append(Block.blocksList[ret.intValue() + 256]).append(" (").append(Block.blocksList[ret.intValue() + 256].getUnlocalizedName()).append(")").toString();
				else
					occupiedBy = (new StringBuilder()).append("item ").append(Item.itemsList[ret.intValue() + 256]).append(" (").append(Item.itemsList[ret.intValue() + 256].getUnlocalizedName()).append(")").toString();
				platform.displayError((new StringBuilder()).append("A conflicting item ID has been detected on your IndustrialCraft 2\nconfiguration file. Item IDs cannot be used for more than once.\n\nItem with invalid ID: ").append(name).append("\n").append("Invalid ID: ").append(ret).append(" (").append(ret.intValue() + 256).append(" shifted)\n").append("Already occupied by: ").append(occupiedBy).toString());
			}
		runtimeIdProperties.setProperty((new StringBuilder()).append("item.").append(name).toString(), ret.toString());
		return ret.intValue();
	}

	public static void explodeMachineAt(World world, int x, int y, int z)
	{
		world.setBlock(x, y, z, 0, 0, 7);
		ExplosionIC2 explosion = new ExplosionIC2(world, null, 0.5D + (double)x, 0.5D + (double)y, 0.5D + (double)z, 2.5F, 0.75F, 0.75F);
		explosion.doExplosion();
	}

	public static int getSeaLevel(World world)
	{
		return world.provider.getAverageGroundLevel();
	}

	public static int getWorldHeight(World world)
	{
		return world.getHeight();
	}

	public static void addValuableOre(int blockId, int value)
	{
		addValuableOre(blockId, 32767, value);
	}

	public static void addValuableOre(int blockId, int metaData, int value)
	{
		if (valuableOres.containsKey(Integer.valueOf(blockId)))
		{
			Map metaMap = (Map)valuableOres.get(Integer.valueOf(blockId));
			if (metaMap.containsKey(Integer.valueOf(32767)))
				return;
			if (metaData == 32767)
			{
				metaMap.clear();
				metaMap.put(Integer.valueOf(32767), Integer.valueOf(value));
			} else
			if (!metaMap.containsKey(Integer.valueOf(metaData)))
				metaMap.put(Integer.valueOf(metaData), Integer.valueOf(value));
		} else
		{
			Map metaMap = new TreeMap();
			metaMap.put(Integer.valueOf(metaData), Integer.valueOf(value));
			valuableOres.put(Integer.valueOf(blockId), metaMap);
		}
	}

	private static String getValuableOreString()
	{
		StringBuilder ret = new StringBuilder();
		boolean first = true;
		for (Iterator i$ = valuableOres.entrySet().iterator(); i$.hasNext();)
		{
			java.util.Map.Entry entry = (java.util.Map.Entry)i$.next();
			Iterator i$ = ((Map)entry.getValue()).entrySet().iterator();
			while (i$.hasNext()) 
			{
				java.util.Map.Entry entry2 = (java.util.Map.Entry)i$.next();
				if (first)
					first = false;
				else
					ret.append(", ");
				ret.append(entry.getKey());
				if (((Integer)entry2.getKey()).intValue() != 32767)
				{
					ret.append("-");
					ret.append(entry2.getKey());
				}
				ret.append(":");
				ret.append(entry2.getValue());
			}
		}

		return ret.toString();
	}

	private static void setValuableOreFromString(String str)
	{
		valuableOres.clear();
		String strParts[] = str.trim().split("\\s*,\\s*");
		String arr$[] = strParts;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			String strPart = arr$[i$];
			String idMetaValue[] = strPart.split("\\s*:\\s*");
			String idMeta[] = idMetaValue[0].split("\\s*-\\s*");
			if (idMeta[0].length() == 0)
				continue;
			int blockId = Integer.parseInt(idMeta[0]);
			int metaData = 32767;
			int value = 1;
			if (idMeta.length == 2)
				metaData = Integer.parseInt(idMeta[1]);
			if (idMetaValue.length == 2)
				value = Integer.parseInt(idMetaValue[1]);
			addValuableOre(blockId, metaData, value);
		}

	}

	public void registerOre(net.minecraftforge.oredict.OreDictionary.OreRegisterEvent event)
	{
		String oreClass = event.Name;
		ItemStack ore = event.Ore;
		if (oreClass.equals("ingotCopper"))
		{
			Recipes.macerator.addRecipe(ore, Ic2Items.copperDust);
			Recipes.compressor.addRecipe(StackUtil.copyWithSize(ore, 8), Ic2Items.denseCopperPlate);
		} else
		if (oreClass.equals("ingotRefinedIron"))
			Recipes.macerator.addRecipe(ore, Ic2Items.ironDust);
		else
		if (oreClass.equals("ingotSilver"))
		{
			Recipes.macerator.addRecipe(ore, Ic2Items.silverDust);
			if (!silverDustSmeltingRegistered)
			{
				FurnaceRecipes.smelting().addSmelting(Ic2Items.silverDust.itemID, Ic2Items.silverDust.getItemDamage(), ore, 0.8F);
				silverDustSmeltingRegistered = true;
			}
		} else
		if (oreClass.equals("ingotTin"))
			Recipes.macerator.addRecipe(ore, Ic2Items.tinDust);
		else
		if (oreClass.equals("dropUranium"))
			Recipes.compressor.addRecipe(ore, Ic2Items.uraniumIngot);
		else
		if (oreClass.equals("oreCopper"))
		{
			Recipes.macerator.addRecipe(ore, StackUtil.copyWithSize(Ic2Items.copperDust, 2));
			addValuableOre(ore.itemID, ore.getItemDamage(), 2);
		} else
		if (oreClass.equals("oreGemRuby") || oreClass.equals("oreGemGreenSapphire") || oreClass.equals("oreGemSapphire"))
			addValuableOre(ore.itemID, ore.getItemDamage(), 4);
		else
		if (oreClass.equals("oreSilver"))
		{
			Recipes.macerator.addRecipe(ore, StackUtil.copyWithSize(Ic2Items.silverDust, 2));
			addValuableOre(ore.itemID, ore.getItemDamage(), 3);
		} else
		if (oreClass.equals("oreTin"))
		{
			Recipes.macerator.addRecipe(ore, StackUtil.copyWithSize(Ic2Items.tinDust, 2));
			addValuableOre(ore.itemID, ore.getItemDamage(), 2);
		} else
		if (oreClass.equals("oreUranium"))
		{
			Recipes.compressor.addRecipe(ore, Ic2Items.uraniumIngot);
			addValuableOre(ore.itemID, ore.getItemDamage(), 4);
		} else
		if (oreClass.equals("oreTungsten"))
			addValuableOre(ore.itemID, ore.getItemDamage(), 5);
		else
		if (oreClass.equals("woodRubber"))
			Recipes.extractor.addRecipe(ore, Ic2Items.rubber);
		else
		if (oreClass.startsWith("ore"))
			addValuableOre(ore.itemID, ore.getItemDamage(), 1);
	}

	public void onLivingSpecialSpawn(net.minecraftforge.event.entity.living.LivingSpawnEvent.SpecialSpawn event)
	{
		if (seasonal && ((event.entityLiving instanceof EntityZombie) || (event.entityLiving instanceof EntitySkeleton)) && ((Entity) (event.entityLiving)).worldObj.rand.nextFloat() < 0.1F)
		{
			EntityLiving entity = (EntityLiving)event.entityLiving;
			for (int i = 0; i <= 4; i++)
				entity.setEquipmentDropChance(i, (-1.0F / 0.0F));

			if (entity instanceof EntityZombie)
				entity.setCurrentItemOrArmor(0, Ic2Items.nanoSaber.copy());
			if (((Entity) (event.entityLiving)).worldObj.rand.nextFloat() < 0.1F)
			{
				entity.setCurrentItemOrArmor(1, Ic2Items.quantumHelmet.copy());
				entity.setCurrentItemOrArmor(2, Ic2Items.quantumBodyarmor.copy());
				entity.setCurrentItemOrArmor(3, Ic2Items.quantumLeggings.copy());
				entity.setCurrentItemOrArmor(4, Ic2Items.quantumBoots.copy());
			} else
			{
				entity.setCurrentItemOrArmor(1, Ic2Items.nanoHelmet.copy());
				entity.setCurrentItemOrArmor(2, Ic2Items.nanoBodyarmor.copy());
				entity.setCurrentItemOrArmor(3, Ic2Items.nanoLeggings.copy());
				entity.setCurrentItemOrArmor(4, Ic2Items.nanoBoots.copy());
			}
		}
	}

	static 
	{
		addValuableOre(Block.oreCoal.blockID, 1);
		addValuableOre(Block.oreGold.blockID, 3);
		addValuableOre(Block.oreRedstone.blockID, 3);
		addValuableOre(Block.oreLapis.blockID, 3);
		addValuableOre(Block.oreIron.blockID, 4);
		addValuableOre(Block.oreDiamond.blockID, 5);
		addValuableOre(Block.oreEmerald.blockID, 5);
	}
}
