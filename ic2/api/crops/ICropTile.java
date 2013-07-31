// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ICropTile.java

package ic2.api.crops;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public interface ICropTile
{

	public abstract short getID();

	public abstract void setID(short word0);

	public abstract byte getSize();

	public abstract void setSize(byte byte0);

	public abstract byte getGrowth();

	public abstract void setGrowth(byte byte0);

	public abstract byte getGain();

	public abstract void setGain(byte byte0);

	public abstract byte getResistance();

	public abstract void setResistance(byte byte0);

	public abstract byte getScanLevel();

	public abstract void setScanLevel(byte byte0);

	public abstract NBTTagCompound getCustomData();

	public abstract int getNutrientStorage();

	public abstract void setNutrientStorage(int i);

	public abstract int getHydrationStorage();

	public abstract void setHydrationStorage(int i);

	public abstract int getWeedExStorage();

	public abstract void setWeedExStorage(int i);

	public abstract byte getHumidity();

	public abstract byte getNutrients();

	public abstract byte getAirQuality();

	public abstract World getWorld();

	public abstract ChunkCoordinates getLocation();

	public abstract int getLightLevel();

	public abstract boolean pick(boolean flag);

	public abstract boolean harvest(boolean flag);

	public abstract void reset();

	public abstract void updateState();

	public abstract boolean isBlockBelow(Block block);

	public abstract ItemStack generateSeeds(short word0, byte byte0, byte byte1, byte byte2, byte byte3);
}
