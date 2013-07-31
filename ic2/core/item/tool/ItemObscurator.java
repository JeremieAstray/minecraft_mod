// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemObscurator.java

package ic2.core.item.tool;

import cpw.mods.fml.common.network.PacketDispatcher;
import ic2.api.event.RetextureEvent;
import ic2.api.item.*;
import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.init.InternalName;
import ic2.core.item.ItemIC2;
import ic2.core.network.IPlayerItemDataListener;
import ic2.core.network.NetworkManager;
import ic2.core.util.StackUtil;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet15Place;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;

// Referenced classes of package ic2.core.item.tool:
//			RenderObscurator

public class ItemObscurator extends ItemIC2
	implements IElectricItem, IPlayerItemDataListener
{

	private final int scanOperationCost = 20000;
	private final int printOperationCost = 5000;

	public ItemObscurator(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		setMaxDamage(27);
		setMaxStackSize(1);
		if (IC2.platform.isRendering())
			MinecraftForgeClient.registerItemRenderer(super.itemID, new RenderObscurator());
	}

	public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, 
			float hitX, float hitY, float hitZ)
	{
		int blockId;
		Block block;
		int meta;
		if (!entityPlayer.isSneaking() && ElectricItem.manager.canUse(itemStack, 5000))
		{
			NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
			int referencedBlockId = nbtData.getInteger("referencedBlockId");
			if (referencedBlockId <= 0 || referencedBlockId >= Block.blocksList.length || Block.blocksList[referencedBlockId] == null || !isBlockSuitable(Block.blocksList[referencedBlockId]))
				return false;
			if (IC2.platform.isSimulating())
			{
				RetextureEvent event = new RetextureEvent(world, x, y, z, side, nbtData.getInteger("referencedBlockId"), nbtData.getInteger("referencedMeta"), nbtData.getInteger("referencedSide"));
				MinecraftForge.EVENT_BUS.post(event);
				if (event.applied)
				{
					ElectricItem.manager.use(itemStack, 5000, entityPlayer);
					return true;
				} else
				{
					return false;
				}
			} else
			{
				PacketDispatcher.sendPacketToServer(new Packet15Place(x, y, z, side, entityPlayer.inventory.getCurrentItem(), hitX, hitY, hitZ));
				return true;
			}
		}
		if (!entityPlayer.isSneaking() || !IC2.platform.isRendering() || !ElectricItem.manager.canUse(itemStack, 20000))
			break MISSING_BLOCK_LABEL_417;
		blockId = world.getBlockId(x, y, z);
		block = Block.blocksList[blockId];
		if (block == null || block.isAirBlock(world, x, y, z) || !isBlockSuitable(block))
			break MISSING_BLOCK_LABEL_417;
		meta = world.getBlockMetadata(x, y, z);
		Icon texture;
		Icon textureWorld;
		texture = block.getIcon(side, meta);
		textureWorld = block.getBlockTexture(world, x, y, z, side);
		if (texture == null || texture != textureWorld)
			return false;
		break MISSING_BLOCK_LABEL_331;
		Exception e;
		e;
		return false;
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
		if (nbtData.getInteger("referencedBlockId") != blockId || nbtData.getInteger("referencedMeta") != meta || nbtData.getInteger("referencedSide") != side)
		{
			IC2.network.sendPlayerItemData(entityPlayer, entityPlayer.inventory.currentItem, new Object[] {
				Integer.valueOf(blockId), Integer.valueOf(meta), Integer.valueOf(side)
			});
			return true;
		}
		return false;
	}

	public transient void onPlayerItemNetworkData(EntityPlayer entityPlayer, int slot, Object data[])
	{
		ItemStack itemStack = entityPlayer.inventory.mainInventory[slot];
		if (ElectricItem.manager.use(itemStack, 20000, entityPlayer))
		{
			NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
			nbtData.setInteger("referencedBlockId", ((Integer)data[0]).intValue());
			nbtData.setInteger("referencedMeta", ((Integer)data[1]).intValue());
			nbtData.setInteger("referencedSide", ((Integer)data[2]).intValue());
		}
	}

	public void getSubItems(int i, CreativeTabs tabs, List itemList)
	{
		ItemStack charged = new ItemStack(this, 1);
		ElectricItem.manager.charge(charged, 0x7fffffff, 0x7fffffff, true, false);
		itemList.add(charged);
		itemList.add(new ItemStack(this, 1, getMaxDamage()));
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
		return 0x186a0;
	}

	public int getTier(ItemStack itemStack)
	{
		return 2;
	}

	public int getTransferLimit(ItemStack itemStack)
	{
		return 250;
	}

	private boolean isBlockSuitable(Block block)
	{
		return block.renderAsNormalBlock();
	}
}
