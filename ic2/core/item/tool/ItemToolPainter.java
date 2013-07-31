// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemToolPainter.java

package ic2.core.item.tool;

import ic2.api.event.PaintEvent;
import ic2.core.*;
import ic2.core.audio.AudioManager;
import ic2.core.audio.PositionSpec;
import ic2.core.init.InternalName;
import ic2.core.item.ItemIC2;
import ic2.core.util.Keyboard;
import ic2.core.util.StackUtil;
import java.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.*;
import net.minecraftforge.event.EventBus;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.oredict.OreDictionary;

public class ItemToolPainter extends ItemIC2
{

	private static final String dyes[] = {
		"dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", 
		"dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite"
	};
	public int color;

	public ItemToolPainter(Configuration config, InternalName internalName, int col)
	{
		super(config, internalName);
		setMaxDamage(32);
		setMaxStackSize(1);
		color = col;
		MinecraftForge.EVENT_BUS.register(this);
	}

	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, 
			float a, float b, float c)
	{
		PaintEvent event = new PaintEvent(world, i, j, k, side, color);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.painted)
		{
			if (IC2.platform.isSimulating())
				damagePainter(entityplayer);
			if (IC2.platform.isRendering())
				IC2.audioManager.playOnce(entityplayer, PositionSpec.Hand, "Tools/Painter.ogg", true, IC2.audioManager.defaultVolume);
			return true;
		}
		int blockId = world.getBlockId(i, j, k);
		if (Block.blocksList[blockId] != null && Block.blocksList[blockId].recolourBlock(world, i, j, k, ForgeDirection.VALID_DIRECTIONS[side], BlockColored.getBlockFromDye(color)))
		{
			damagePainter(entityplayer);
			if (IC2.platform.isRendering())
				IC2.audioManager.playOnce(entityplayer, PositionSpec.Hand, "Tools/Painter.ogg", true, IC2.audioManager.defaultVolume);
			return true;
		} else
		{
			return false;
		}
	}

	public boolean onEntityInteract(EntityInteractEvent event)
	{
		EntityPlayer player = event.entityPlayer;
		Entity entity = event.entity;
		if (entity.worldObj.isRemote || player.getCurrentEquippedItem() == null || player.getCurrentEquippedItem().itemID != super.itemID)
			return true;
		boolean ret = true;
		if (entity instanceof EntitySheep)
		{
			EntitySheep sheep = (EntitySheep)entity;
			int clr = BlockColored.getBlockFromDye(color);
			if (sheep.getFleeceColor() != clr)
			{
				ret = false;
				((EntitySheep)entity).setFleeceColor(clr);
				damagePainter(player);
			}
		}
		return ret;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (IC2.platform.isSimulating() && IC2.keyboard.isModeSwitchKeyDown(entityplayer))
		{
			NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemstack);
			boolean newValue = !nbtData.getBoolean("autoRefill");
			nbtData.setBoolean("autoRefill", newValue);
			if (newValue)
				IC2.platform.messagePlayer(entityplayer, "Painter automatic refill mode enabled", new Object[0]);
			else
				IC2.platform.messagePlayer(entityplayer, "Painter automatic refill mode disabled", new Object[0]);
		}
		return itemstack;
	}

	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean debugTooltips)
	{
		info.add(StatCollector.translateToLocal((new StringBuilder()).append(Item.dyePowder.getUnlocalizedName(new ItemStack(Item.dyePowder, 1, color))).append(".name").toString()));
	}

	private void damagePainter(EntityPlayer player)
	{
		if (player.inventory.mainInventory[player.inventory.currentItem].getItemDamage() >= player.inventory.mainInventory[player.inventory.currentItem].getMaxDamage() - 1)
		{
			int dyeIS = -1;
			NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(player.inventory.mainInventory[player.inventory.currentItem]);
			if (nbtData.getBoolean("autoRefill"))
			{
label0:
				for (int l = 0; l < player.inventory.mainInventory.length; l++)
				{
					if (player.inventory.mainInventory[l] == null)
						continue;
					Iterator i$ = OreDictionary.getOres(dyes[color]).iterator();
					ItemStack ore;
					do
					{
						if (!i$.hasNext())
							continue label0;
						ore = (ItemStack)i$.next();
					} while (!ore.isItemEqual(player.inventory.mainInventory[l]));
					dyeIS = l;
				}

			}
			if (dyeIS == -1)
			{
				player.inventory.mainInventory[player.inventory.currentItem] = Ic2Items.painter.copy();
			} else
			{
				if (--player.inventory.mainInventory[dyeIS].stackSize <= 0)
					player.inventory.mainInventory[dyeIS] = null;
				player.inventory.mainInventory[player.inventory.currentItem].setItemDamage(0);
			}
		} else
		{
			player.inventory.mainInventory[player.inventory.currentItem].damageItem(1, player);
		}
		player.openContainer.detectAndSendChanges();
	}

}
