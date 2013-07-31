// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IReactor.java

package ic2.api.reactor;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public interface IReactor
{

	public abstract ChunkCoordinates getPosition();

	public abstract World getWorld();

	public abstract int getHeat();

	public abstract void setHeat(int i);

	public abstract int addHeat(int i);

	public abstract int getMaxHeat();

	public abstract void setMaxHeat(int i);

	public abstract float getHeatEffectModifier();

	public abstract void setHeatEffectModifier(float f);

	public abstract int getOutput();

	public abstract float addOutput(float f);

	/**
	 * @deprecated Method addOutput is deprecated
	 */

	public abstract int addOutput(int i);

	/**
	 * @deprecated Method getPulsePower is deprecated
	 */

	public abstract int getPulsePower();

	public abstract ItemStack getItemAt(int i, int j);

	public abstract void setItemAt(int i, int j, ItemStack itemstack);

	public abstract void explode();

	public abstract int getTickRate();

	public abstract boolean produceEnergy();
}
