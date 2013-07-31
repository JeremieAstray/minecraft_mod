// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Crops.java

package ic2.api.crops;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

// Referenced classes of package ic2.api.crops:
//			CropCard, BaseSeed

public abstract class Crops
{

	public static Crops instance;

	public Crops()
	{
	}

	public abstract void addBiomeBonus(BiomeGenBase biomegenbase, int i, int j);

	public abstract int getHumidityBiomeBonus(BiomeGenBase biomegenbase);

	public abstract int getNutrientBiomeBonus(BiomeGenBase biomegenbase);

	public abstract CropCard[] getCropList();

	public abstract short registerCrop(CropCard cropcard);

	public abstract boolean registerCrop(CropCard cropcard, int i);

	public abstract boolean registerBaseSeed(ItemStack itemstack, int i, int j, int k, int l, int i1);

	public abstract BaseSeed getBaseSeed(ItemStack itemstack);

	public abstract void startSpriteRegistration(IconRegister iconregister);

	public abstract int getIdFor(CropCard cropcard);
}
