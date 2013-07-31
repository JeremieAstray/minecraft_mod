// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemDebug.java

package ic2.core.item.tool;

import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;
import ic2.api.reactor.IReactor;
import ic2.api.tile.IEnergyStorage;
import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.TileEntityCrop;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.block.personal.IPersonalBlock;
import ic2.core.init.InternalName;
import ic2.core.item.InfiniteElectricItemManager;
import ic2.core.item.ItemIC2;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

public class ItemDebug extends ItemIC2
	implements ISpecialElectricItem
{

	private static final String modes[] = {
		"Interfaces and Fields", "Tile Data"
	};
	private static IElectricItemManager manager = null;

	public ItemDebug(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		setHasSubtypes(false);
		if (!Util.inDev())
			setCreativeTab(null);
	}

	public String getUnlocalizedName(ItemStack stack)
	{
		return "debugItem";
	}

	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, 
			float hitX, float hitY, float hitZ)
	{
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
		int mode = nbtData.getInteger("mode");
		if (player.isSneaking())
		{
			if (IC2.platform.isSimulating())
			{
				mode = (mode + 1) % modes.length;
				nbtData.setInteger("mode", mode);
				IC2.platform.messagePlayer(player, (new StringBuilder()).append("Debug Item Mode: ").append(modes[mode]).toString(), new Object[0]);
			}
			return false;
		}
		switch (mode)
		{
		default:
			break;

		case 0: // '\0'
		{
			PrintStream ps = new PrintStream(new FileOutputStream(FileDescriptor.out));
			int blockId = world.getBlockId(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			String plat = IC2.platform.isRendering() ? IC2.platform.isSimulating() ? "sp" : "client" : "server";
			String message;
			if (blockId < Block.blocksList.length && Block.blocksList[blockId] != null)
				message = (new StringBuilder()).append("[").append(plat).append("] id: ").append(blockId).append(" meta: ").append(meta).append(" name: ").append(Block.blocksList[blockId].getUnlocalizedName()).append(" te: ").append(tileEntity).toString();
			else
				message = (new StringBuilder()).append("[").append(plat).append("] id: ").append(blockId).append(" meta: ").append(meta).append(" name: null te: ").append(tileEntity).toString();
			IC2.platform.messagePlayer(player, message, new Object[0]);
			ps.println(message);
			if (tileEntity != null)
			{
				message = (new StringBuilder()).append("[").append(plat).append("] interfaces:").toString();
				Class c = tileEntity.getClass();
				do
				{
					Class arr$[] = c.getInterfaces();
					int len$ = arr$.length;
					for (int i$ = 0; i$ < len$; i$++)
					{
						Class i = arr$[i$];
						message = (new StringBuilder()).append(message).append(" ").append(i.getName()).toString();
					}

					c = c.getSuperclass();
				} while (c != null);
				IC2.platform.messagePlayer(player, message, new Object[0]);
				ps.println(message);
			}
			if (blockId < Block.blocksList.length && Block.blocksList[blockId] != null && Block.blocksList[blockId].blockID != 0)
			{
				ps.println("block fields:");
				dumpObjectFields(ps, Block.blocksList[blockId]);
			}
			if (tileEntity != null)
			{
				ps.println("tile entity fields:");
				dumpObjectFields(ps, tileEntity);
			}
			ps.flush();
			break;
		}

		case 1: // '\001'
		{
			if (!IC2.platform.isSimulating())
				return false;
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if (tileEntity instanceof TileEntityBlock)
			{
				TileEntityBlock te = (TileEntityBlock)tileEntity;
				IC2.platform.messagePlayer(player, (new StringBuilder()).append("Block: Active=").append(te.getActive()).append(" Facing=").append(te.getFacing()).toString(), new Object[0]);
			}
			if (tileEntity instanceof TileEntityBaseGenerator)
			{
				TileEntityBaseGenerator te = (TileEntityBaseGenerator)tileEntity;
				IC2.platform.messagePlayer(player, (new StringBuilder()).append("BaseGen: Fuel=").append(te.fuel).append(" Storage=").append(te.storage).toString(), new Object[0]);
			}
			if (tileEntity instanceof TileEntityElectricMachine)
			{
				TileEntityElectricMachine te = (TileEntityElectricMachine)tileEntity;
				IC2.platform.messagePlayer(player, (new StringBuilder()).append("ElecMachine: Energy=").append(te.energy).toString(), new Object[0]);
			}
			if (tileEntity instanceof IEnergyStorage)
			{
				IEnergyStorage te = (IEnergyStorage)tileEntity;
				IC2.platform.messagePlayer(player, (new StringBuilder()).append("EnergyStorage: Stored=").append(te.getStored()).toString(), new Object[0]);
			}
			if (tileEntity instanceof IReactor)
			{
				IReactor te = (IReactor)tileEntity;
				IC2.platform.messagePlayer(player, (new StringBuilder()).append("Reactor: Heat=").append(te.getHeat()).append(" MaxHeat=").append(te.getMaxHeat()).append(" HEM=").append(te.getHeatEffectModifier()).append(" Output=").append(te.getOutput()).toString(), new Object[0]);
			}
			if (tileEntity instanceof IPersonalBlock)
			{
				IPersonalBlock te = (IPersonalBlock)tileEntity;
				IC2.platform.messagePlayer(player, (new StringBuilder()).append("PersonalBlock: CanAccess=").append(te.permitsAccess(player)).toString(), new Object[0]);
			}
			if (tileEntity instanceof TileEntityCrop)
			{
				TileEntityCrop te = (TileEntityCrop)tileEntity;
				IC2.platform.messagePlayer(player, (new StringBuilder()).append("PersonalBlock: Crop=").append(te.id).append(" Size=").append(te.size).append(" Growth=").append(te.statGrowth).append(" Gain=").append(te.statGain).append(" Resistance=").append(te.statResistance).append(" Nutrients=").append(te.nutrientStorage).append(" Water=").append(te.waterStorage).append(" GrowthPoints=").append(te.growthPoints).toString(), new Object[0]);
			}
			break;
		}
		}
		return IC2.platform.isSimulating();
	}

	private static void dumpObjectFields(PrintStream ps, Object o)
	{
		Class fieldDeclaringClass = o.getClass();
		do
		{
			Field fields[] = fieldDeclaringClass.getDeclaredFields();
			Field arr$[] = fields;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Field field = arr$[i$];
				if ((field.getModifiers() & 8) != 0 && (fieldDeclaringClass == net/minecraft/block/Block || fieldDeclaringClass == net/minecraft/tileentity/TileEntity))
					continue;
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				try
				{
					Object value = field.get(o);
					ps.println((new StringBuilder()).append(field.getName()).append(" class: ").append(fieldDeclaringClass.getName()).append(" type: ").append(field.getType()).toString());
					ps.println((new StringBuilder()).append("    identity hash: ").append(System.identityHashCode(o)).append(" hash: ").append(o.hashCode()).append(" modifiers: ").append(field.getModifiers()).toString());
					if (field.getType().isArray())
					{
						List array = new ArrayList();
						for (int i = 0; i < Array.getLength(value); i++)
							array.add(Array.get(value, i));

						value = array;
					}
					if (value instanceof Iterable)
					{
						ps.println("    values:");
						int i = 0;
						for (Iterator it = ((Iterable)value).iterator(); it.hasNext();)
						{
							ps.println((new StringBuilder()).append("      [").append(i).append("] ").append(getValueString(it.next())).toString());
							i++;
						}

					} else
					{
						ps.println((new StringBuilder()).append("    value: ").append(getValueString(value)).toString());
					}
				}
				catch (IllegalAccessException e)
				{
					ps.println((new StringBuilder()).append("name: ").append(fieldDeclaringClass.getName()).append(".").append(field.getName()).append(" type: ").append(field.getType()).append(" value: <can't access>").toString());
				}
				catch (NullPointerException e)
				{
					ps.println((new StringBuilder()).append("name: ").append(fieldDeclaringClass.getName()).append(".").append(field.getName()).append(" type: ").append(field.getType()).append(" value: <null>").toString());
				}
				field.setAccessible(accessible);
			}

			fieldDeclaringClass = fieldDeclaringClass.getSuperclass();
		} while (fieldDeclaringClass != null);
	}

	private static String getValueString(Object o)
	{
		if (o == null)
			return "<null>";
		String ret = o.toString();
		if (o.getClass().isArray())
		{
			for (int i = 0; i < Array.getLength(o); i++)
				ret = (new StringBuilder()).append(ret).append(" [").append(i).append("] ").append(Array.get(o, i)).toString();

		}
		if (ret.length() > 100)
			ret = (new StringBuilder()).append(ret.substring(0, 90)).append("... (").append(ret.length()).append(" more)").toString();
		return ret;
	}

	public boolean canProvideEnergy(ItemStack itemStack)
	{
		return true;
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
		return 0x7fffffff;
	}

	public int getTier(ItemStack itemStack)
	{
		return 1;
	}

	public int getTransferLimit(ItemStack itemStack)
	{
		return 0x7fffffff;
	}

	public IElectricItemManager getManager(ItemStack itemStack)
	{
		if (manager == null)
			manager = new InfiniteElectricItemManager();
		return manager;
	}

}
