// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemScrapbox.java

package ic2.core.item;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.Recipes;
import ic2.core.*;
import ic2.core.init.InternalName;
import java.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.OreDictionary;

// Referenced classes of package ic2.core.item:
//			ItemIC2, BehaviorScrapboxDispense

public class ItemScrapbox extends ItemIC2
{
	static class ScrapboxRecipeManager
		implements IMachineRecipeManager
	{

		private final IMachineRecipeManager backend = new BasicMachineRecipeManager();

		public void addRecipe(ItemStack input, Float output)
		{
			backend.addRecipe(input, new Drop(output.floatValue()));
		}

		public Float getOutputFor(ItemStack input, boolean adjustInput)
		{
			Drop drop = (Drop)backend.getOutputFor(input, adjustInput);
			if (drop == null)
				return null;
			else
				return Float.valueOf(drop.originalChance);
		}

		public Map getRecipes()
		{
			Map recipes = new HashMap();
			java.util.Map.Entry entry;
			for (Iterator i$ = backend.getRecipes().entrySet().iterator(); i$.hasNext(); recipes.put(entry.getKey(), Float.valueOf(((Drop)entry.getValue()).originalChance / Drop.topChance)))
				entry = (java.util.Map.Entry)i$.next();

			return Collections.unmodifiableMap(recipes);
		}

		Map getDrops()
		{
			return backend.getRecipes();
		}

		public volatile Object getOutputFor(ItemStack x0, boolean x1)
		{
			return getOutputFor(x0, x1);
		}

		public volatile void addRecipe(ItemStack x0, Object x1)
		{
			addRecipe(x0, (Float)x1);
		}

		ScrapboxRecipeManager()
		{
		}
	}

	static class Drop
	{

		float originalChance;
		float upperChanceBound;
		static float topChance;

		Drop(float chance)
		{
			originalChance = chance;
			upperChanceBound = topChance += chance;
		}
	}


	public ItemScrapbox(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new BehaviorScrapboxDispense());
	}

	public static void init()
	{
		Recipes.scrapboxDrops = new ScrapboxRecipeManager();
		if (IC2.suddenlyHoes)
			addDrop(Item.hoeWood, 9001F);
		else
			addDrop(Item.hoeWood, 5.01F);
		addDrop(Block.dirt, 5F);
		addDrop(Item.stick, 4F);
		addDrop(Block.grass, 3F);
		addDrop(Block.gravel, 3F);
		addDrop(Block.netherrack, 2.0F);
		addDrop(Item.rottenFlesh, 2.0F);
		addDrop(Item.appleRed, 1.5F);
		addDrop(Item.bread, 1.5F);
		addDrop(Ic2Items.filledTinCan.getItem(), 1.5F);
		addDrop(Item.swordWood);
		addDrop(Item.shovelWood);
		addDrop(Item.pickaxeWood);
		addDrop(Block.slowSand);
		addDrop(Item.sign);
		addDrop(Item.leather);
		addDrop(Item.feather);
		addDrop(Item.bone);
		addDrop(Item.porkCooked, 0.9F);
		addDrop(Item.beefCooked, 0.9F);
		addDrop(Block.pumpkin, 0.9F);
		addDrop(Item.chickenCooked, 0.9F);
		addDrop(Item.minecartEmpty, 0.9F);
		addDrop(Item.redstone, 0.9F);
		addDrop(Ic2Items.rubber.getItem(), 0.8F);
		addDrop(Item.glowstone, 0.8F);
		addDrop(Ic2Items.coalDust.getItem(), 0.8F);
		addDrop(Ic2Items.copperDust.getItem(), 0.8F);
		addDrop(Ic2Items.tinDust.getItem(), 0.8F);
		addDrop(Ic2Items.plantBall.getItem(), 0.7F);
		addDrop(Ic2Items.suBattery.getItem(), 0.7F);
		addDrop(Ic2Items.ironDust.getItem(), 0.7F);
		addDrop(Ic2Items.goldDust.getItem(), 0.7F);
		addDrop(Item.slimeBall, 0.6F);
		addDrop(Block.oreIron, 0.5F);
		addDrop(Item.helmetGold, 0.5F);
		addDrop(Block.oreGold, 0.5F);
		addDrop(Item.cake, 0.5F);
		addDrop(Item.diamond, 0.1F);
		addDrop(Item.emerald, 0.05F);
		if (Ic2Items.copperOre != null)
		{
			addDrop(Ic2Items.copperOre.getItem(), 0.7F);
		} else
		{
			List ores = OreDictionary.getOres("oreCopper");
			if (!ores.isEmpty())
				addDrop(((ItemStack)ores.get(0)).copy(), 0.7F);
		}
		if (Ic2Items.tinOre != null)
		{
			addDrop(Ic2Items.tinOre.getItem(), 0.7F);
		} else
		{
			List ores = OreDictionary.getOres("oreTin");
			if (!ores.isEmpty())
				addDrop(((ItemStack)ores.get(0)).copy(), 0.7F);
		}
	}

	public static void addDrop(Item item)
	{
		addDrop(new ItemStack(item), 1.0F);
	}

	public static void addDrop(Item item, float chance)
	{
		addDrop(new ItemStack(item), chance);
	}

	public static void addDrop(Block block)
	{
		addDrop(new ItemStack(block), 1.0F);
	}

	public static void addDrop(Block block, float chance)
	{
		addDrop(new ItemStack(block), chance);
	}

	public static void addDrop(ItemStack item)
	{
		addDrop(item, 1.0F);
	}

	public static void addDrop(ItemStack item, float chance)
	{
		Recipes.scrapboxDrops.addRecipe(item, Float.valueOf(chance));
	}

	public static ItemStack getDrop(World world)
	{
label0:
		{
			Map dropList = ((ScrapboxRecipeManager)Recipes.scrapboxDrops).getDrops();
			if (dropList.isEmpty())
				break label0;
			float dropChance = world.rand.nextFloat() * Drop.topChance;
			Iterator i$ = dropList.entrySet().iterator();
			java.util.Map.Entry entry;
			Drop drop;
			do
			{
				if (!i$.hasNext())
					break label0;
				entry = (java.util.Map.Entry)i$.next();
				drop = (Drop)entry.getValue();
			} while (drop.upperChanceBound <= dropChance || drop.upperChanceBound - drop.originalChance > dropChance);
			return ((ItemStack)entry.getKey()).copy();
		}
		return null;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (!entityplayer.capabilities.isCreativeMode)
			itemstack.stackSize--;
		ItemStack itemStack = getDrop(world);
		if (itemStack != null)
			entityplayer.dropPlayerItem(itemStack);
		return itemstack;
	}
}
