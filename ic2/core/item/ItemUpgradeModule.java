// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemUpgradeModule.java

package ic2.core.item;

import ic2.api.Direction;
import ic2.core.Ic2Items;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.init.InternalName;
import ic2.core.util.StackUtil;
import java.text.DecimalFormat;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item:
//			ItemIC2, IUpgradeItem

public class ItemUpgradeModule extends ItemIC2
	implements IUpgradeItem
{

	private static DecimalFormat decimalformat = new DecimalFormat("0.##");
	private final int upgradeCount = 4;

	public ItemUpgradeModule(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		setHasSubtypes(true);
		Ic2Items.overclockerUpgrade = new ItemStack(this, 1, 0);
		Ic2Items.transformerUpgrade = new ItemStack(this, 1, 1);
		Ic2Items.energyStorageUpgrade = new ItemStack(this, 1, 2);
		Ic2Items.ejectorUpgrade = new ItemStack(this, 1, 3);
	}

	public String getTextureFolder()
	{
		return "upgrade";
	}

	public String getTextureName(int index)
	{
		String ret = super.getTextureName(index);
		if (ret != null)
			return ret;
		if (index - upgradeCount < 6)
			return (new StringBuilder()).append(InternalName.ejectorUpgrade.name()).append(".").append(index - upgradeCount).toString();
		else
			return null;
	}

	public Icon getIcon(ItemStack stack, int pass)
	{
		if (stack.getItemDamage() == 3)
		{
			int dir = StackUtil.getOrCreateNbtData(stack).getByte("dir");
			if (dir >= 1 && dir <= 6)
				return super.getIconFromDamage((upgradeCount + dir) - 1);
		}
		return super.getIcon(stack, pass);
	}

	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	public String getUnlocalizedName(ItemStack itemStack)
	{
		InternalName ret;
		switch (itemStack.getItemDamage())
		{
		case 0: // '\0'
			ret = InternalName.overclockerUpgrade;
			break;

		case 1: // '\001'
			ret = InternalName.transformerUpgrade;
			break;

		case 2: // '\002'
			ret = InternalName.energyStorageUpgrade;
			break;

		case 3: // '\003'
			ret = InternalName.ejectorUpgrade;
			break;

		default:
			return null;
		}
		return ret.name();
	}

	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		super.addInformation(stack, player, list, par4);
		switch (stack.getItemDamage())
		{
		default:
			break;

		case 0: // '\0'
			list.add(StatCollector.translateToLocalFormatted("ic2.tooltip.upgrade.overclocker.time", new Object[] {
				decimalformat.format(100D * Math.pow(0.69999999999999996D, stack.stackSize))
			}));
			list.add(StatCollector.translateToLocalFormatted("ic2.tooltip.upgrade.overclocker.power", new Object[] {
				decimalformat.format(100D * Math.pow(1.6000000000000001D, stack.stackSize))
			}));
			break;

		case 1: // '\001'
			list.add(StatCollector.translateToLocalFormatted("ic2.tooltip.upgrade.transformer", new Object[] {
				Integer.valueOf(stack.stackSize)
			}));
			break;

		case 2: // '\002'
			list.add(StatCollector.translateToLocalFormatted("ic2.tooltip.upgrade.storage", new Object[] {
				Integer.valueOf(10000 * stack.stackSize)
			}));
			break;

		case 3: // '\003'
			String side;
			switch (StackUtil.getOrCreateNbtData(stack).getByte("dir") - 1)
			{
			case 0: // '\0'
				side = "ic2.dir.west";
				break;

			case 1: // '\001'
				side = "ic2.dir.east";
				break;

			case 2: // '\002'
				side = "ic2.dir.bottom";
				break;

			case 3: // '\003'
				side = "ic2.dir.top";
				break;

			case 4: // '\004'
				side = "ic2.dir.north";
				break;

			case 5: // '\005'
				side = "ic2.dir.south";
				break;

			default:
				side = "ic2.tooltip.upgrade.ejector.anyside";
				break;
			}
			list.add(StatCollector.translateToLocalFormatted("ic2.tooltip.upgrade.ejector", new Object[] {
				StatCollector.translateToLocal(side)
			}));
			break;
		}
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, 
			float xOffset, float yOffset, float zOffset)
	{
		if (stack.getItemDamage() == 3)
		{
			int dir = 1 + (side + 2) % 6;
			NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
			if (nbtData.getByte("dir") != dir)
			{
				nbtData.setByte("dir", (byte)dir);
				return true;
			}
		}
		return false;
	}

	public void getSubItems(int i, CreativeTabs tabs, List itemList)
	{
		int meta = 0;
		do
		{
			if (meta > 32767)
				break;
			ItemStack stack = new ItemStack(this, 1, meta);
			if (getUnlocalizedName(stack) == null)
				break;
			itemList.add(stack);
			meta++;
		} while (true);
	}

	public int getExtraProcessTime(ItemStack stack, TileEntityStandardMachine parent)
	{
		return 0;
	}

	public double getProcessTimeMultiplier(ItemStack stack, TileEntityStandardMachine parent)
	{
		return stack.getItemDamage() != 0 ? 1.0D : 0.69999999999999996D;
	}

	public int getExtraEnergyDemand(ItemStack stack, TileEntityStandardMachine parent)
	{
		return 0;
	}

	public double getEnergyDemandMultiplier(ItemStack stack, TileEntityStandardMachine parent)
	{
		return stack.getItemDamage() != 0 ? 1.0D : 1.6000000000000001D;
	}

	public int getExtraEnergyStorage(ItemStack stack, TileEntityStandardMachine parent)
	{
		return stack.getItemDamage() != 2 ? 0 : 10000;
	}

	public double getEnergyStorageMultiplier(ItemStack stack, TileEntityStandardMachine parent)
	{
		return 1.0D;
	}

	public int getExtraTier(ItemStack stack, TileEntityStandardMachine parent)
	{
		return stack.getItemDamage() != 1 ? 0 : 1;
	}

	public boolean onTick(ItemStack stack, TileEntityStandardMachine parent)
	{
		if (stack.getItemDamage() == 3)
		{
			ItemStack output = parent.outputSlot.get();
			if (output != null && parent.energy >= 20)
			{
				int amount = Math.min(output.stackSize, parent.energy / 20);
				int dir = StackUtil.getOrCreateNbtData(stack).getByte("dir");
				if (dir < 1 || dir > 6)
				{
					amount = StackUtil.distribute(parent, StackUtil.copyWithSize(output, amount), false);
				} else
				{
					TileEntity te = Direction.values()[dir - 1].applyToTileEntity(parent);
					if (te instanceof IInventory)
						amount = StackUtil.putInInventory((IInventory)te, StackUtil.copyWithSize(output, amount), false);
					else
						return false;
				}
				output.stackSize -= amount;
				if (output.stackSize <= 0)
					parent.outputSlot.clear();
				parent.energy -= 20 * amount;
				return true;
			}
		}
		return false;
	}

	public void onProcessEnd(ItemStack itemstack, TileEntityStandardMachine tileentitystandardmachine, ItemStack itemstack1)
	{
	}

}
