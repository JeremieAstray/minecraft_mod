// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemToolbox.java

package ic2.core.item;

import ic2.api.item.IBoxable;
import ic2.api.item.ItemWrapper;
import ic2.core.*;
import ic2.core.init.InternalName;
import ic2.core.util.StackUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item:
//			ItemIC2

public class ItemToolbox extends ItemIC2
	implements IBoxable
{

	public ItemToolbox(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		setMaxStackSize(1);
	}

	public String getTextureName(int index)
	{
		switch (index)
		{
		case 0: // '\0'
			return getUnlocalizedName();

		case 1: // '\001'
			return (new StringBuilder()).append(getUnlocalizedName()).append(".").append(InternalName.open.name()).toString();
		}
		return null;
	}

	public Icon getIconFromDamage(int meta)
	{
		if (meta == 0)
			return textures[0];
		else
			return textures[1];
	}

	public String getUnlocalizedName(ItemStack itemstack)
	{
		if (itemstack == null)
			return "DAMN TMI CAUSING NPE's!";
		if (itemstack.getItemDamage() == 0)
			return "itemToolbox";
		ItemStack inventory[] = getInventoryFromNBT(itemstack);
		if (inventory[0] == null)
			return "itemToolbox";
		else
			return inventory[0].getItem().getUnlocalizedName(inventory[0]);
	}

	public static ItemStack[] getInventoryFromNBT(ItemStack is)
	{
		ItemStack re[] = new ItemStack[8];
		if (is.getTagCompound() == null)
			return re;
		NBTTagCompound tag = is.getTagCompound();
		for (int i = 0; i < 8; i++)
		{
			NBTTagCompound item = tag.getCompoundTag((new StringBuilder()).append("box").append(i).toString());
			if (item != null)
				re[i] = ItemStack.loadItemStackFromNBT(item);
		}

		return re;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (!IC2.platform.isSimulating())
			return itemstack;
		if (itemstack.getItemDamage() == 0)
			pack(itemstack, entityplayer);
		else
			unpack(itemstack, entityplayer);
		if (!IC2.platform.isRendering())
			entityplayer.openContainer.detectAndSendChanges();
		return itemstack;
	}

	public boolean canBeStoredInToolbox(ItemStack wayne)
	{
		return false;
	}

	public void pack(ItemStack toolbox, EntityPlayer player)
	{
		ItemStack hotbar[] = player.inventory.mainInventory;
		NBTTagCompound mainbox = new NBTTagCompound();
		int boxcount = 0;
		for (int i = 0; i < 9; i++)
			if (hotbar[i] != null && hotbar[i] != toolbox && ItemWrapper.canBeStoredInToolbox(hotbar[i]) && (hotbar[i].getMaxStackSize() <= 1 || hotbar[i].itemID == Ic2Items.scaffold.itemID || hotbar[i].itemID == Ic2Items.miningPipe.itemID))
			{
				NBTTagCompound myBox = new NBTTagCompound();
				hotbar[i].writeToNBT(myBox);
				hotbar[i] = null;
				mainbox.setCompoundTag((new StringBuilder()).append("box").append(boxcount).toString(), myBox);
				boxcount++;
			}

		if (boxcount == 0)
		{
			return;
		} else
		{
			toolbox.setTagCompound(mainbox);
			toolbox.setItemDamage(1);
			return;
		}
	}

	public void unpack(ItemStack toolbox, EntityPlayer player)
	{
		NBTTagCompound box = toolbox.getTagCompound();
		if (box == null)
			return;
		ItemStack inventory[] = getInventoryFromNBT(toolbox);
		ItemStack hotbar[] = player.inventory.mainInventory;
		int inv = 0;
		for (int i = 0; i < inventory.length && inventory[inv] != null; i++)
			if (hotbar[i] == null)
			{
				hotbar[i] = inventory[inv];
				inv++;
			}

		for (; inv < 8 && inventory[inv] != null; inv++)
			StackUtil.dropAsEntity(((Entity) (player)).worldObj, (int)((Entity) (player)).posX, (int)((Entity) (player)).posY, (int)((Entity) (player)).posZ, inventory[inv]);

		toolbox.setTagCompound(null);
		toolbox.setItemDamage(0);
	}
}
