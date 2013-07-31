// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IC2Crops.java

package ic2.core.block.crop;

import ic2.api.crops.*;
import ic2.core.*;
import ic2.core.init.Localization;
import java.io.PrintStream;
import java.util.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

// Referenced classes of package ic2.core.block.crop:
//			CropWeed, CropWheat, CropPumpkin, CropMelon, 
//			CropColorFlower, CropVenomilia, CropReed, CropStickReed, 
//			CropCocoa, CropFerru, CropAurelia, CropRedWheat, 
//			CropNetherWart, CropTerraWart, CropCoffee, CropHops, 
//			CropSeedFood, CropPotato

public class IC2Crops extends Crops
{

	private final Map humidityBiomeBonus = new HashMap();
	private final Map nutrientBiomeBonus = new HashMap();
	private final Map baseSeeds = new HashMap();
	private final CropCard crops[] = new CropCard[256];
	public static CropCard weed = new CropWeed();
	public static CropCard cropWheat = new CropWheat();
	public static CropCard cropPumpkin = new CropPumpkin();
	public static CropCard cropMelon = new CropMelon();
	public static CropCard cropYellowFlower = new CropColorFlower("Dandelion", new String[] {
		"Yellow", "Flower"
	}, 11);
	public static CropCard cropRedFlower = new CropColorFlower("Rose", new String[] {
		"Red", "Flower", "Rose"
	}, 1);
	public static CropCard cropBlackFlower = new CropColorFlower("Blackthorn", new String[] {
		"Black", "Flower", "Rose"
	}, 0);
	public static CropCard cropPurpleFlower = new CropColorFlower("Tulip", new String[] {
		"Purple", "Flower", "Tulip"
	}, 5);
	public static CropCard cropBlueFlower = new CropColorFlower("Cyazint", new String[] {
		"Blue", "Flower"
	}, 6);
	public static CropCard cropVenomilia = new CropVenomilia();
	public static CropCard cropReed = new CropReed();
	public static CropCard cropStickReed = new CropStickReed();
	public static CropCard cropCocoa = new CropCocoa();
	public static CropCard cropFerru = new CropFerru();
	public static CropCard cropAurelia = new CropAurelia();
	public static CropCard cropRedwheat = new CropRedWheat();
	public static CropCard cropNetherWart = new CropNetherWart();
	public static CropCard cropTerraWart = new CropTerraWart();
	public static CropCard cropCoffee = new CropCoffee();
	public static CropCard cropHops = new CropHops();
	public static CropCard cropCarrots;
	public static CropCard cropPotato = new CropPotato();

	public IC2Crops()
	{
	}

	public static void init()
	{
		Crops.instance = new IC2Crops();
		registerCrops();
		registerBaseSeeds();
	}

	public static void registerCrops()
	{
		if (!Crops.instance.registerCrop(weed, 0) || !Crops.instance.registerCrop(cropWheat, 1) || !Crops.instance.registerCrop(cropPumpkin, 2) || !Crops.instance.registerCrop(cropMelon, 3) || !Crops.instance.registerCrop(cropYellowFlower, 4) || !Crops.instance.registerCrop(cropRedFlower, 5) || !Crops.instance.registerCrop(cropBlackFlower, 6) || !Crops.instance.registerCrop(cropPurpleFlower, 7) || !Crops.instance.registerCrop(cropBlueFlower, 8) || !Crops.instance.registerCrop(cropVenomilia, 9) || !Crops.instance.registerCrop(cropReed, 10) || !Crops.instance.registerCrop(cropStickReed, 11) || !Crops.instance.registerCrop(cropCocoa, 12) || !Crops.instance.registerCrop(cropFerru, 13) || !Crops.instance.registerCrop(cropAurelia, 14) || !Crops.instance.registerCrop(cropRedwheat, 15) || !Crops.instance.registerCrop(cropNetherWart, 16) || !Crops.instance.registerCrop(cropTerraWart, 17) || !Crops.instance.registerCrop(cropCoffee, 18) || !Crops.instance.registerCrop(cropHops, 19) || !Crops.instance.registerCrop(cropCarrots, 20) || !Crops.instance.registerCrop(cropPotato, 21))
			IC2.platform.displayError("One or more crops have failed to initialize.\nThis could happen due to a crop addon using a crop ID already taken\nby a crop from IndustrialCraft 2.");
	}

	public static void registerBaseSeeds()
	{
		Crops.instance.registerBaseSeed(new ItemStack(Item.seeds.itemID, 1, 32767), cropWheat.getId(), 1, 1, 1, 1);
		Crops.instance.registerBaseSeed(new ItemStack(Item.pumpkinSeeds.itemID, 1, 32767), cropPumpkin.getId(), 1, 1, 1, 1);
		Crops.instance.registerBaseSeed(new ItemStack(Item.melonSeeds.itemID, 1, 32767), cropMelon.getId(), 1, 1, 1, 1);
		Crops.instance.registerBaseSeed(new ItemStack(Item.netherStalkSeeds.itemID, 1, 32767), cropNetherWart.getId(), 1, 1, 1, 1);
		Crops.instance.registerBaseSeed(new ItemStack(Ic2Items.terraWart.itemID, 1, 32767), cropTerraWart.getId(), 1, 1, 1, 1);
		Crops.instance.registerBaseSeed(new ItemStack(Ic2Items.coffeeBeans.itemID, 1, 32767), cropCoffee.getId(), 1, 1, 1, 1);
		Crops.instance.registerBaseSeed(new ItemStack(Item.reed.itemID, 1, 32767), cropReed.getId(), 1, 3, 0, 2);
		Crops.instance.registerBaseSeed(new ItemStack(Item.dyePowder.itemID, 1, 3), cropCocoa.getId(), 1, 0, 0, 0);
		Crops.instance.registerBaseSeed(new ItemStack(((Block) (Block.plantRed)).blockID, 4, 32767), cropRedFlower.getId(), 4, 1, 1, 1);
		Crops.instance.registerBaseSeed(new ItemStack(((Block) (Block.plantYellow)).blockID, 4, 32767), cropYellowFlower.getId(), 4, 1, 1, 1);
		Crops.instance.registerBaseSeed(new ItemStack(Item.carrot, 1, 32767), cropCarrots.getId(), 1, 1, 1, 1);
		Crops.instance.registerBaseSeed(new ItemStack(Item.potato, 1, 32767), cropPotato.getId(), 1, 1, 1, 1);
	}

	public void addBiomeBonus(BiomeGenBase biome, int humidityBonus, int nutrientsBonus)
	{
		humidityBiomeBonus.put(biome, Integer.valueOf(humidityBonus));
		nutrientBiomeBonus.put(biome, Integer.valueOf(nutrientsBonus));
	}

	public int getHumidityBiomeBonus(BiomeGenBase biome)
	{
		return humidityBiomeBonus.containsKey(biome) ? ((Integer)humidityBiomeBonus.get(biome)).intValue() : 0;
	}

	public int getNutrientBiomeBonus(BiomeGenBase biome)
	{
		return nutrientBiomeBonus.containsKey(biome) ? ((Integer)nutrientBiomeBonus.get(biome)).intValue() : 0;
	}

	public CropCard[] getCropList()
	{
		return crops;
	}

	public short registerCrop(CropCard crop)
	{
		for (short x = 0; x < crops.length; x++)
			if (crops[x] == null)
			{
				registerCrop(crop, x);
				return x;
			}

		return -1;
	}

	public boolean registerCrop(CropCard crop, int i)
	{
		if (i < 0 || i >= crops.length)
			return false;
		if (crops[i] == null)
		{
			crops[i] = crop;
			Localization.addLocalization((new StringBuilder()).append("ic2.cropSeed").append(i).toString(), (new StringBuilder()).append(crop.name()).append(" Seeds").toString());
			return true;
		} else
		{
			System.out.println((new StringBuilder()).append("[IndustrialCraft2] Cannot add crop:").append(crop.name()).append(" on ID #").append(i).append(", slot already occupied by crop:").append(crops[i].name()).toString());
			return false;
		}
	}

	public boolean registerBaseSeed(ItemStack stack, int id, int size, int growth, int gain, int resistance)
	{
		for (Iterator i$ = baseSeeds.keySet().iterator(); i$.hasNext();)
		{
			ItemStack key = (ItemStack)i$.next();
			if (key.itemID == stack.itemID && key.getItemDamage() == stack.getItemDamage())
				return false;
		}

		baseSeeds.put(stack, new BaseSeed(id, size, growth, gain, resistance, stack.stackSize));
		return true;
	}

	public BaseSeed getBaseSeed(ItemStack stack)
	{
		if (stack == null)
			return null;
		for (Iterator i$ = baseSeeds.entrySet().iterator(); i$.hasNext();)
		{
			java.util.Map.Entry entry = (java.util.Map.Entry)i$.next();
			ItemStack key = (ItemStack)entry.getKey();
			if (key.itemID == stack.itemID && (key.getItemDamage() == 32767 || key.getItemDamage() == stack.getItemDamage()))
				return (BaseSeed)baseSeeds.get(key);
		}

		return null;
	}

	public void startSpriteRegistration(IconRegister iconRegister)
	{
		CropCard arr$[] = crops;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			CropCard cropCard = arr$[i$];
			if (cropCard != null)
				cropCard.registerSprites(iconRegister);
		}

	}

	public int getIdFor(CropCard crop)
	{
		for (int i = 0; i < crops.length; i++)
			if (crops[i] == crop)
				return i;

		return -1;
	}

	static 
	{
		cropCarrots = new CropSeedFood("Carrots", "Orange", new ItemStack(Item.carrot));
	}
}
