// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemBooze.java

package ic2.core.item;

import ic2.core.Ic2Items;
import ic2.core.init.InternalName;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.FoodStats;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item:
//			ItemIC2

public class ItemBooze extends ItemIC2
{

	public String solidRatio[] = {
		"Watery ", "Clear ", "Lite ", "", "Strong ", "Thick ", "Stodge ", "X"
	};
	public String hopsRatio[] = {
		"Soup ", "Alcfree ", "White ", "", "Dark ", "Full ", "Black ", "X"
	};
	public String timeRatioNames[] = {
		"Brew", "Youngster", "Beer", "Ale", "Dragonblood", "Black Stuff"
	};
	public int baseDuration[] = {
		300, 600, 900, 1200, 1600, 2000, 2400
	};
	public float baseIntensity[] = {
		0.4F, 0.75F, 1.0F, 1.5F, 2.0F
	};
	public static float rumStackability = 2.0F;
	public static int rumDuration = 600;

	public ItemBooze(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		setMaxStackSize(1);
		setCreativeTab(null);
	}

	public String getTextureName(int index)
	{
		if (index < timeRatioNames.length)
			return (new StringBuilder()).append(getUnlocalizedName()).append(".").append(InternalName.beer.name()).append(".").append(timeRatioNames[index]).toString();
		if (index == timeRatioNames.length)
			return (new StringBuilder()).append(getUnlocalizedName()).append(".").append(InternalName.rum.name()).toString();
		else
			return null;
	}

	public Icon getIconFromDamage(int meta)
	{
		int type = getTypeOfValue(meta);
		if (type == 1)
		{
			int timeRatio = Math.min(getTimeRatioOfBeerValue(meta), timeRatioNames.length - 1);
			return textures[timeRatio];
		}
		if (type == 2)
			return textures[timeRatioNames.length];
		else
			return null;
	}

	public String getItemDisplayName(ItemStack itemstack)
	{
		int meta = itemstack.getItemDamage();
		int type = getTypeOfValue(meta);
		if (type == 1)
		{
			int timeRatio = Math.min(getTimeRatioOfBeerValue(meta), timeRatioNames.length - 1);
			if (timeRatio == timeRatioNames.length - 1)
				return timeRatioNames[timeRatio];
			else
				return (new StringBuilder()).append(solidRatio[getSolidRatioOfBeerValue(meta)]).append(hopsRatio[getHopsRatioOfBeerValue(meta)]).append(timeRatioNames[timeRatio]).toString();
		}
		if (type == 2)
			return "Rum";
		else
			return "Zero";
	}

	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer player)
	{
		int meta = itemstack.getItemDamage();
		int type = getTypeOfValue(meta);
		if (type == 0)
			return new ItemStack(Ic2Items.mugEmpty.getItem());
		if (type == 1)
		{
			if (getTimeRatioOfBeerValue(meta) == 5)
				return drinkBlackStuff(player);
			int solidRatio = getSolidRatioOfBeerValue(meta);
			int alc = getHopsRatioOfBeerValue(meta);
			int duration = baseDuration[solidRatio];
			float intensity = baseIntensity[getTimeRatioOfBeerValue(meta)];
			player.getFoodStats().addStats(6 - alc, (float)solidRatio * 0.15F);
			int max = (int)(intensity * ((float)alc * 0.5F));
			PotionEffect slow = player.getActivePotionEffect(Potion.digSlowdown);
			int level = -1;
			if (slow != null)
				level = slow.getAmplifier();
			amplifyEffect(player, Potion.digSlowdown, max, intensity, duration);
			if (level > -1)
			{
				amplifyEffect(player, Potion.damageBoost, max, intensity, duration);
				if (level > 0)
				{
					amplifyEffect(player, Potion.moveSlowdown, max / 2, intensity, duration);
					if (level > 1)
					{
						amplifyEffect(player, Potion.resistance, max - 1, intensity, duration);
						if (level > 2)
						{
							amplifyEffect(player, Potion.confusion, 0, intensity, duration);
							if (level > 3)
								player.addPotionEffect(new PotionEffect(Potion.harm.id, 1, ((Entity) (player)).worldObj.rand.nextInt(3)));
						}
					}
				}
			}
		}
		if (type == 2)
			if (getProgressOfRumValue(meta) < 100)
			{
				drinkBlackStuff(player);
			} else
			{
				amplifyEffect(player, Potion.fireResistance, 0, rumStackability, rumDuration);
				PotionEffect def = player.getActivePotionEffect(Potion.resistance);
				int level = -1;
				if (def != null)
					level = def.getAmplifier();
				amplifyEffect(player, Potion.resistance, 2, rumStackability, rumDuration);
				if (level >= 0)
					amplifyEffect(player, Potion.blindness, 0, rumStackability, rumDuration);
				if (level >= 1)
					amplifyEffect(player, Potion.confusion, 0, rumStackability, rumDuration);
			}
		return new ItemStack(Ic2Items.mugEmpty.getItem());
	}

	public void amplifyEffect(EntityPlayer player, Potion potion, int max, float intensity, int duration)
	{
		PotionEffect eff = player.getActivePotionEffect(potion);
		if (eff == null)
		{
			player.addPotionEffect(new PotionEffect(potion.id, duration, 0));
		} else
		{
			int newdur = eff.getDuration();
			int maxnewdur = (int)((float)duration * (1.0F + intensity * 2.0F) - (float)newdur) / 2;
			if (maxnewdur < 0)
				maxnewdur = 0;
			if (maxnewdur < duration)
				duration = maxnewdur;
			newdur += duration;
			int newamp = eff.getAmplifier();
			if (newamp < max)
				newamp++;
			player.addPotionEffect(new PotionEffect(potion.id, newdur, newamp));
		}
	}

	public ItemStack drinkBlackStuff(EntityPlayer player)
	{
		switch (((Entity) (player)).worldObj.rand.nextInt(6))
		{
		case 1: // '\001'
			player.addPotionEffect(new PotionEffect(Potion.confusion.id, 1200, 0));
			break;

		case 2: // '\002'
			player.addPotionEffect(new PotionEffect(Potion.blindness.id, 2400, 0));
			break;

		case 3: // '\003'
			player.addPotionEffect(new PotionEffect(Potion.poison.id, 2400, 0));
			break;

		case 4: // '\004'
			player.addPotionEffect(new PotionEffect(Potion.poison.id, 200, 2));
			break;

		case 5: // '\005'
			player.addPotionEffect(new PotionEffect(Potion.harm.id, 1, ((Entity) (player)).worldObj.rand.nextInt(4)));
			break;
		}
		return new ItemStack(Ic2Items.mugEmpty.getItem());
	}

	public int getMaxItemUseDuration(ItemStack itemstack)
	{
		return 32;
	}

	public EnumAction getItemUseAction(ItemStack itemstack)
	{
		return EnumAction.drink;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
	{
		player.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}

	public static int getTypeOfValue(int value)
	{
		return skipGetOfValue(value, 0, 2);
	}

	public static int getAmountOfValue(int value)
	{
		if (getTypeOfValue(value) == 0)
			return 0;
		else
			return skipGetOfValue(value, 2, 5) + 1;
	}

	public static int getSolidRatioOfBeerValue(int value)
	{
		return skipGetOfValue(value, 7, 3);
	}

	public static int getHopsRatioOfBeerValue(int value)
	{
		return skipGetOfValue(value, 10, 3);
	}

	public static int getTimeRatioOfBeerValue(int value)
	{
		return skipGetOfValue(value, 13, 3);
	}

	public static int getProgressOfRumValue(int value)
	{
		return skipGetOfValue(value, 7, 7);
	}

	private static int skipGetOfValue(int value, int bitshift, int take)
	{
		value >>= bitshift;
		take = (int)Math.pow(2D, take) - 1;
		return value & take;
	}

}
