// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CropCard.java

package ic2.api.crops;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

// Referenced classes of package ic2.api.crops:
//			ICropTile, Crops

public abstract class CropCard
{

	protected Icon textures[];

	public CropCard()
	{
	}

	public abstract String name();

	public String discoveredBy()
	{
		return "Alblaka";
	}

	public String desc(int i)
	{
		String att[] = attributes();
		if (att == null || att.length == 0)
			return "";
		String s;
		if (i == 0)
		{
			s = att[0];
			if (att.length >= 2)
			{
				s = (new StringBuilder()).append(s).append(", ").append(att[1]).toString();
				if (att.length >= 3)
					s = (new StringBuilder()).append(s).append(",").toString();
			}
			return s;
		}
		if (att.length < 3)
			return "";
		s = att[2];
		if (att.length >= 4)
			s = (new StringBuilder()).append(s).append(", ").append(att[3]).toString();
		return s;
	}

	public abstract int tier();

	public abstract int stat(int i);

	public abstract String[] attributes();

	public abstract int maxSize();

	public void registerSprites(IconRegister iconRegister)
	{
		textures = new Icon[maxSize()];
		for (int i = 1; i <= textures.length; i++)
			textures[i - 1] = iconRegister.registerIcon((new StringBuilder()).append("ic2:crop/blockCrop.").append(name()).append(".").append(i).toString());

	}

	public Icon getSprite(ICropTile crop)
	{
		if (crop.getSize() <= 0 || crop.getSize() > textures.length)
			return null;
		else
			return textures[crop.getSize() - 1];
	}

	public String getTextureFile()
	{
		return "/ic2/sprites/crops_0.png";
	}

	public int growthDuration(ICropTile crop)
	{
		return tier() * 200;
	}

	public abstract boolean canGrow(ICropTile icroptile);

	public int weightInfluences(ICropTile crop, float humidity, float nutrients, float air)
	{
		return (int)(humidity + nutrients + air);
	}

	public boolean canCross(ICropTile crop)
	{
		return crop.getSize() >= 3;
	}

	public boolean rightclick(ICropTile crop, EntityPlayer player)
	{
		return crop.harvest(true);
	}

	public abstract boolean canBeHarvested(ICropTile icroptile);

	public float dropGainChance()
	{
		float base = 1.0F;
		for (int i = 0; i < tier(); i++)
			base = (float)((double)base * 0.94999999999999996D);

		return base;
	}

	public abstract ItemStack getGain(ICropTile icroptile);

	public byte getSizeAfterHarvest(ICropTile crop)
	{
		return 1;
	}

	public boolean leftclick(ICropTile crop, EntityPlayer player)
	{
		return crop.pick(true);
	}

	public float dropSeedChance(ICropTile crop)
	{
		if (crop.getSize() == 1)
			return 0.0F;
		float base = 0.5F;
		if (crop.getSize() == 2)
			base /= 2.0F;
		for (int i = 0; i < tier(); i++)
			base = (float)((double)base * 0.80000000000000004D);

		return base;
	}

	public ItemStack getSeeds(ICropTile crop)
	{
		return crop.generateSeeds(crop.getID(), crop.getGrowth(), crop.getGain(), crop.getResistance(), crop.getScanLevel());
	}

	public void onNeighbourChange(ICropTile icroptile)
	{
	}

	public int emitRedstone(ICropTile crop)
	{
		return 0;
	}

	public void onBlockDestroyed(ICropTile icroptile)
	{
	}

	public int getEmittedLight(ICropTile crop)
	{
		return 0;
	}

	public boolean onEntityCollision(ICropTile crop, Entity entity)
	{
		if (entity instanceof EntityLivingBase)
			return ((EntityLivingBase)entity).isSprinting();
		else
			return false;
	}

	public void tick(ICropTile icroptile)
	{
	}

	public boolean isWeed(ICropTile crop)
	{
		return crop.getSize() >= 2 && (crop.getID() == 0 || crop.getGrowth() >= 24);
	}

	public final int getId()
	{
		return Crops.instance.getIdFor(this);
	}
}
