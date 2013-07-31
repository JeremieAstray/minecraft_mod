// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemCropSeed.java

package ic2.core.item;

import ic2.api.crops.Crops;
import ic2.core.Ic2Items;
import ic2.core.block.TileEntityCrop;
import ic2.core.init.InternalName;
import ic2.core.util.Util;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item:
//			ItemIC2

public class ItemCropSeed extends ItemIC2
{

	public ItemCropSeed(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		setMaxStackSize(1);
		if (!Util.inDev())
			setCreativeTab(null);
	}

	public String getUnlocalizedName(ItemStack itemstack)
	{
		if (itemstack == null)
			return "cropSeedUn";
		int level = getScannedFromStack(itemstack);
		if (level == 0)
			return "cropSeedUn";
		if (level < 0)
			return "cropSeedInvalid";
		else
			return (new StringBuilder()).append("cropSeed").append(getIdFromStack(itemstack)).toString();
	}

	public boolean isDamageable()
	{
		return true;
	}

	public boolean isRepairable()
	{
		return false;
	}

	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean debugTooltips)
	{
		if (getScannedFromStack(stack) >= 4)
		{
			info.add((new StringBuilder()).append("\2472Gr\2477 ").append(getGrowthFromStack(stack)).toString());
			info.add((new StringBuilder()).append("\2476Ga\2477 ").append(getGainFromStack(stack)).toString());
			info.add((new StringBuilder()).append("\2473Re\2477 ").append(getResistanceFromStack(stack)).toString());
		}
	}

	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, 
			float a, float b, float c)
	{
		if (world.getBlockTileEntity(x, y, z) instanceof TileEntityCrop)
		{
			TileEntityCrop crop = (TileEntityCrop)world.getBlockTileEntity(x, y, z);
			if (crop.tryPlantIn(getIdFromStack(itemstack), 1, getGrowthFromStack(itemstack), getGainFromStack(itemstack), getResistanceFromStack(itemstack), getScannedFromStack(itemstack)))
			{
				entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
				return true;
			} else
			{
				return false;
			}
		} else
		{
			return false;
		}
	}

	public void getSubItems(int id, CreativeTabs tabs, List items)
	{
		ic2.api.crops.CropCard crops[] = Crops.instance.getCropList();
		for (int i = 0; i < crops.length; i++)
			if (crops[i] != null)
				items.add(generateItemStackFromValues((short)i, (byte)1, (byte)1, (byte)1, (byte)4));

	}

	public static ItemStack generateItemStackFromValues(short id, byte statGrowth, byte statGain, byte statResistance, byte scan)
	{
		ItemStack is = new ItemStack(Ic2Items.cropSeed.getItem());
		NBTTagCompound tag = new NBTTagCompound();
		tag.setShort("id", id);
		tag.setByte("growth", statGrowth);
		tag.setByte("gain", statGain);
		tag.setByte("resistance", statResistance);
		tag.setByte("scan", scan);
		is.setTagCompound(tag);
		return is;
	}

	public static short getIdFromStack(ItemStack is)
	{
		if (is.getTagCompound() == null)
			return -1;
		else
			return is.getTagCompound().getShort("id");
	}

	public static byte getGrowthFromStack(ItemStack is)
	{
		if (is.getTagCompound() == null)
			return -1;
		else
			return is.getTagCompound().getByte("growth");
	}

	public static byte getGainFromStack(ItemStack is)
	{
		if (is.getTagCompound() == null)
			return -1;
		else
			return is.getTagCompound().getByte("gain");
	}

	public static byte getResistanceFromStack(ItemStack is)
	{
		if (is.getTagCompound() == null)
			return -1;
		else
			return is.getTagCompound().getByte("resistance");
	}

	public static byte getScannedFromStack(ItemStack is)
	{
		if (is.getTagCompound() == null)
			return -1;
		else
			return is.getTagCompound().getByte("scan");
	}

	public static void incrementScannedOfStack(ItemStack is)
	{
		if (is.getTagCompound() == null)
		{
			return;
		} else
		{
			is.getTagCompound().setByte("scan", (byte)(getScannedFromStack(is) + 1));
			return;
		}
	}
}
