// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemToolWrenchElectric.java

package ic2.core.item.tool;

import ic2.api.item.*;
import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.init.InternalName;
import ic2.core.util.Keyboard;
import ic2.core.util.StackUtil;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.tool:
//			ItemToolWrench

public class ItemToolWrenchElectric extends ItemToolWrench
	implements IElectricItem
{

	public ItemToolWrenchElectric(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		setMaxDamage(27);
		setMaxStackSize(1);
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (IC2.platform.isSimulating() && IC2.keyboard.isModeSwitchKeyDown(entityplayer))
		{
			NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemstack);
			boolean newValue = !nbtData.getBoolean("losslessMode");
			nbtData.setBoolean("losslessMode", newValue);
			if (newValue)
				IC2.platform.messagePlayer(entityplayer, "Lossless wrench mode enabled", new Object[0]);
			else
				IC2.platform.messagePlayer(entityplayer, "Lossless wrench mode disabled", new Object[0]);
		}
		return itemstack;
	}

	public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, 
			float hitX, float hitY, float hitZ)
	{
		if (IC2.keyboard.isModeSwitchKeyDown(entityPlayer))
			return false;
		else
			return super.onItemUseFirst(itemstack, entityPlayer, world, x, y, z, side, hitX, hitY, hitZ);
	}

	public boolean canTakeDamage(ItemStack stack, int amount)
	{
		amount *= 50;
		return ElectricItem.manager.discharge(stack, amount, 0x7fffffff, true, true) == amount;
	}

	public void damage(ItemStack itemStack, int amount, EntityPlayer player)
	{
		ElectricItem.manager.use(itemStack, 50 * amount, player);
	}

	public boolean canProvideEnergy(ItemStack itemStack)
	{
		return false;
	}

	public int getChargedItemId(ItemStack itemStack)
	{
		return super.itemID;
	}

	public int getEmptyItemId(ItemStack itemStack)
	{
		return super.itemID;
	}

	public int getMaxCharge(ItemStack itemStack)
	{
		return 12000;
	}

	public int getTier(ItemStack itemStack)
	{
		return 1;
	}

	public int getTransferLimit(ItemStack itemStack)
	{
		return 250;
	}

	public void getSubItems(int i, CreativeTabs tabs, List itemList)
	{
		ItemStack charged = new ItemStack(this, 1);
		ElectricItem.manager.charge(charged, 0x7fffffff, 0x7fffffff, true, false);
		itemList.add(charged);
		itemList.add(new ItemStack(this, 1, getMaxDamage()));
	}

	public boolean overrideWrenchSuccessRate(ItemStack itemStack)
	{
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
		return nbtData.getBoolean("losslessMode");
	}

	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return false;
	}
}
