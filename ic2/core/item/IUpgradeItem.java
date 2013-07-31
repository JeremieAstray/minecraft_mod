// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IUpgradeItem.java

package ic2.core.item;

import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import net.minecraft.item.ItemStack;

public interface IUpgradeItem
{

	public abstract int getExtraProcessTime(ItemStack itemstack, TileEntityStandardMachine tileentitystandardmachine);

	public abstract double getProcessTimeMultiplier(ItemStack itemstack, TileEntityStandardMachine tileentitystandardmachine);

	public abstract int getExtraEnergyDemand(ItemStack itemstack, TileEntityStandardMachine tileentitystandardmachine);

	public abstract double getEnergyDemandMultiplier(ItemStack itemstack, TileEntityStandardMachine tileentitystandardmachine);

	public abstract int getExtraEnergyStorage(ItemStack itemstack, TileEntityStandardMachine tileentitystandardmachine);

	public abstract double getEnergyStorageMultiplier(ItemStack itemstack, TileEntityStandardMachine tileentitystandardmachine);

	public abstract int getExtraTier(ItemStack itemstack, TileEntityStandardMachine tileentitystandardmachine);

	public abstract boolean onTick(ItemStack itemstack, TileEntityStandardMachine tileentitystandardmachine);

	public abstract void onProcessEnd(ItemStack itemstack, TileEntityStandardMachine tileentitystandardmachine, ItemStack itemstack1);
}
