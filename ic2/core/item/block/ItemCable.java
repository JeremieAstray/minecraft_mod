// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemCable.java

package ic2.core.item.block;

import ic2.api.item.IBoxable;
import ic2.core.Ic2Items;
import ic2.core.block.wiring.BlockCable;
import ic2.core.init.InternalName;
import ic2.core.item.ItemIC2;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

public class ItemCable extends ItemIC2
	implements IBoxable
{

	public ItemCable(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		setHasSubtypes(true);
		Ic2Items.copperCableItem = new ItemStack(this, 1, 1);
		Ic2Items.insulatedCopperCableItem = new ItemStack(this, 1, 0);
		Ic2Items.goldCableItem = new ItemStack(this, 1, 2);
		Ic2Items.insulatedGoldCableItem = new ItemStack(this, 1, 3);
		Ic2Items.doubleInsulatedGoldCableItem = new ItemStack(this, 1, 4);
		Ic2Items.ironCableItem = new ItemStack(this, 1, 5);
		Ic2Items.insulatedIronCableItem = new ItemStack(this, 1, 6);
		Ic2Items.doubleInsulatedIronCableItem = new ItemStack(this, 1, 7);
		Ic2Items.trippleInsulatedIronCableItem = new ItemStack(this, 1, 8);
		Ic2Items.glassFiberCableItem = new ItemStack(this, 1, 9);
		Ic2Items.tinCableItem = new ItemStack(this, 1, 10);
		Ic2Items.detectorCableItem = new ItemStack(this, 1, 11);
		Ic2Items.splitterCableItem = new ItemStack(this, 1, 12);
	}

	public String getUnlocalizedName(ItemStack itemstack)
	{
		int meta = itemstack.getItemDamage();
		InternalName ret;
		switch (meta)
		{
		case 0: // '\0'
			ret = InternalName.itemCable;
			break;

		case 1: // '\001'
			ret = InternalName.itemCableO;
			break;

		case 2: // '\002'
			ret = InternalName.itemGoldCable;
			break;

		case 3: // '\003'
			ret = InternalName.itemGoldCableI;
			break;

		case 4: // '\004'
			ret = InternalName.itemGoldCableII;
			break;

		case 5: // '\005'
			ret = InternalName.itemIronCable;
			break;

		case 6: // '\006'
			ret = InternalName.itemIronCableI;
			break;

		case 7: // '\007'
			ret = InternalName.itemIronCableII;
			break;

		case 8: // '\b'
			ret = InternalName.itemIronCableIIII;
			break;

		case 9: // '\t'
			ret = InternalName.itemGlassCable;
			break;

		case 10: // '\n'
			ret = InternalName.itemTinCable;
			break;

		case 11: // '\013'
			ret = InternalName.itemDetectorCable;
			break;

		case 12: // '\f'
			ret = InternalName.itemSplitterCable;
			break;

		default:
			return null;
		}
		return ret.name();
	}

	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, 
			float a, float b, float c)
	{
		int blockId = world.getBlockId(x, y, z);
		if (blockId > 0)
			if (blockId == Block.snow.blockID)
				side = 1;
			else
			if (blockId != Block.vine.blockID && blockId != ((Block) (Block.tallGrass)).blockID && blockId != ((Block) (Block.deadBush)).blockID && (Block.blocksList[blockId] == null || !Block.blocksList[blockId].isBlockReplaceable(world, x, y, z)))
				switch (side)
				{
				case 0: // '\0'
					y--;
					break;

				case 1: // '\001'
					y++;
					break;

				case 2: // '\002'
					z--;
					break;

				case 3: // '\003'
					z++;
					break;

				case 4: // '\004'
					x--;
					break;

				case 5: // '\005'
					x++;
					break;
				}
		BlockCable block = (BlockCable)Block.blocksList[Ic2Items.insulatedCopperCableBlock.itemID];
		if ((blockId == 0 || world.canPlaceEntityOnSide(Ic2Items.insulatedCopperCableBlock.itemID, x, y, z, false, side, entityplayer, itemstack)) && world.checkNoEntityCollision(block.getCollisionBoundingBoxFromPool(world, x, y, z, itemstack.getItemDamage())) && world.setBlock(x, y, z, ((Block) (block)).blockID, itemstack.getItemDamage(), 3))
		{
			block.onPostBlockPlaced(world, x, y, z, side);
			block.onBlockPlacedBy(world, x, y, z, entityplayer, itemstack);
			if (!entityplayer.capabilities.isCreativeMode)
				itemstack.stackSize--;
			return true;
		} else
		{
			return false;
		}
	}

	public void getSubItems(int i, CreativeTabs tabs, List itemList)
	{
		int meta = 0;
		do
		{
			if (meta >= 32767)
				break;
			ItemStack stack = new ItemStack(this, 1, meta);
			if (getUnlocalizedName(stack) == null)
				break;
			itemList.add(stack);
			meta++;
		} while (true);
	}

	public boolean canBeStoredInToolbox(ItemStack itemstack)
	{
		return true;
	}
}
