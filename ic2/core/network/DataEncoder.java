// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DataEncoder.java

package ic2.core.network;

import ic2.core.IC2;
import ic2.core.Platform;
import java.io.*;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.potion.Potion;
import net.minecraft.stats.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.*;
import net.minecraftforge.common.DimensionManager;

public class DataEncoder
{

	public DataEncoder()
	{
	}

	public static Object decode(DataInputStream is)
		throws IOException
	{
		byte type = is.readByte();
		switch (type)
		{
		case 0: // '\0'
		{
			return Integer.valueOf(is.readInt());
		}

		case 1: // '\001'
		{
			short length = is.readShort();
			int data[] = new int[length];
			for (int i = 0; i < length; i++)
				data[i] = is.readInt();

			return data;
		}

		case 2: // '\002'
		{
			return Short.valueOf(is.readShort());
		}

		case 3: // '\003'
		{
			short length = is.readShort();
			short data[] = new short[length];
			for (int i = 0; i < length; i++)
				data[i] = is.readShort();

			return data;
		}

		case 4: // '\004'
		{
			return Byte.valueOf(is.readByte());
		}

		case 5: // '\005'
		{
			short length = is.readShort();
			byte data[] = new byte[length];
			for (int i = 0; i < length; i++)
				data[i] = is.readByte();

			return data;
		}

		case 6: // '\006'
		{
			return Long.valueOf(is.readLong());
		}

		case 7: // '\007'
		{
			short length = is.readShort();
			long data[] = new long[length];
			for (int i = 0; i < length; i++)
				data[i] = is.readLong();

			return data;
		}

		case 8: // '\b'
		{
			return Boolean.valueOf(is.readBoolean());
		}

		case 9: // '\t'
		{
			short length = is.readShort();
			boolean data[] = new boolean[length];
			byte b = 0;
			for (int i = 0; i < length; i++)
			{
				if (i % 8 == 0)
					b = is.readByte();
				data[i] = (b & 1 << i % 8) != 0;
			}

			return data;
		}

		case 10: // '\n'
		{
			short length = is.readShort();
			char data[] = new char[length];
			for (int i = 0; i < length; i++)
				data[i] = is.readChar();

			return new String(data);
		}

		case 11: // '\013'
		{
			short length = is.readShort();
			String data[] = new String[length];
			for (int i = 0; i < length; i++)
			{
				short slength = is.readShort();
				char sdata[] = new char[slength];
				for (int j = 0; j < slength; j++)
					sdata[j] = is.readChar();

				data[i] = new String(sdata);
			}

			return data;
		}

		case 12: // '\f'
		{
			short id = is.readShort();
			if (id == 0)
				return null;
			byte stackSize = is.readByte();
			short meta = is.readShort();
			ItemStack stack = new ItemStack(id, stackSize, meta);
			if (is.readBoolean())
				stack.stackTagCompound = CompressedStreamTools.read(is);
			return stack;
		}

		case 13: // '\r'
		{
			return NBTBase.readNamedTag(is);
		}

		case 14: // '\016'
		{
			byte kind = is.readByte();
			int id = is.readInt();
			switch (kind)
			{
			case 0: // '\0'
				return Block.blocksList[id];

			case 1: // '\001'
				return Item.itemsList[id];

			case 2: // '\002'
				return AchievementList.achievementList.get(id);

			case 3: // '\003'
				return Potion.potionTypes[id];

			case 4: // '\004'
				return Enchantment.enchantmentsList[id];
			}
			// fall through
		}

		case 15: // '\017'
		{
			byte kind = is.readByte();
			int x = is.readInt();
			int y = 0;
			if (kind == 1 || kind == 2)
				y = is.readInt();
			int z = is.readInt();
			switch (kind)
			{
			case 0: // '\0'
				return new ChunkCoordIntPair(x, z);

			case 1: // '\001'
				return new ChunkCoordinates(x, y, z);

			case 2: // '\002'
				return new ChunkPosition(x, y, z);
			}
			// fall through
		}

		case 16: // '\020'
		{
			int dimension = is.readInt();
			int x = is.readInt();
			int y = is.readInt();
			int z = is.readInt();
			return DimensionManager.getWorld(dimension).getBlockTileEntity(x, y, z);
		}

		case 17: // '\021'
		{
			int dimension = is.readInt();
			return DimensionManager.getWorld(dimension);
		}

		case 18: // '\022'
		{
			return Float.valueOf(is.readFloat());
		}

		case 19: // '\023'
		{
			short length = is.readShort();
			float data[] = new float[length];
			for (int i = 0; i < length; i++)
				data[i] = is.readFloat();

			return data;
		}

		case 20: // '\024'
		{
			return Double.valueOf(is.readDouble());
		}

		case 21: // '\025'
		{
			short length = is.readShort();
			double data[] = new double[length];
			for (int i = 0; i < length; i++)
				data[i] = is.readDouble();

			return data;
		}

		case 127: // '\177'
		{
			return null;
		}

		default:
		{
			IC2.platform.displayError((new StringBuilder()).append("An unknown data type was received over multiplayer to be decoded.\nThis could happen due to corrupted data or a bug.\n\n(Technical information: type ID ").append(type).append(")").toString());
			return null;
		}
		}
	}

	public static void encode(DataOutputStream os, Object o)
		throws IOException
	{
		if (o instanceof Integer)
		{
			os.writeByte(0);
			os.writeInt(((Integer)o).intValue());
		} else
		if (o instanceof int[])
		{
			os.writeByte(1);
			int oa[] = (int[])(int[])o;
			os.writeShort(oa.length);
			for (int i = 0; i < oa.length; i++)
				os.writeInt(oa[i]);

		} else
		if (o instanceof Short)
		{
			os.writeByte(2);
			os.writeShort(((Short)o).shortValue());
		} else
		if (o instanceof short[])
		{
			os.writeByte(3);
			short oa[] = (short[])(short[])o;
			os.writeShort(oa.length);
			for (int i = 0; i < oa.length; i++)
				os.writeShort(oa[i]);

		} else
		if (o instanceof Byte)
		{
			os.writeByte(4);
			os.writeByte(((Byte)o).byteValue());
		} else
		if (o instanceof byte[])
		{
			os.writeByte(5);
			byte oa[] = (byte[])(byte[])o;
			os.writeShort(oa.length);
			for (int i = 0; i < oa.length; i++)
				os.writeByte(oa[i]);

		} else
		if (o instanceof Long)
		{
			os.writeByte(6);
			os.writeLong(((Long)o).longValue());
		} else
		if (o instanceof long[])
		{
			os.writeByte(7);
			long oa[] = (long[])(long[])o;
			os.writeShort(oa.length);
			for (int i = 0; i < oa.length; i++)
				os.writeLong(oa[i]);

		} else
		if (o instanceof Boolean)
		{
			os.writeByte(8);
			os.writeBoolean(((Boolean)o).booleanValue());
		} else
		if (o instanceof boolean[])
		{
			os.writeByte(9);
			boolean oa[] = (boolean[])(boolean[])o;
			os.writeShort(oa.length);
			byte b = 0;
			for (int i = 0; i < oa.length; i++)
			{
				if (i % 8 == 0 && i > 0)
				{
					os.writeByte(b);
					b = 0;
				}
				b |= (oa[i] ? 1 : 0) << i % 8;
			}

			os.writeByte(b);
		} else
		if (o instanceof String)
		{
			os.writeByte(10);
			String oa = (String)o;
			os.writeShort(oa.length());
			os.writeChars(oa);
		} else
		if (o instanceof String[])
		{
			os.writeByte(11);
			String oa[] = (String[])(String[])o;
			os.writeShort(oa.length);
			for (int i = 0; i < oa.length; i++)
			{
				os.writeShort(oa[i].length());
				os.writeChars(oa[i]);
			}

		} else
		if (o instanceof ItemStack)
		{
			os.writeByte(12);
			ItemStack oa = (ItemStack)o;
			os.writeShort(oa.itemID);
			if (oa.itemID == 0)
				return;
			os.writeByte(oa.stackSize);
			os.writeShort(oa.getItemDamage());
			if ((Item.itemsList[oa.itemID].isDamageable() || Item.itemsList[oa.itemID].getShareTag()) && oa.stackTagCompound != null)
			{
				os.writeBoolean(true);
				CompressedStreamTools.write(oa.stackTagCompound, os);
			} else
			{
				os.writeBoolean(false);
			}
		} else
		if (o instanceof NBTBase)
		{
			os.writeByte(13);
			NBTBase.writeNamedTag((NBTBase)o, os);
		} else
		if (o instanceof Block)
		{
			os.writeByte(14);
			os.writeByte(0);
			os.writeInt(((Block)o).blockID);
		} else
		if (o instanceof Item)
		{
			os.writeByte(14);
			os.writeByte(1);
			os.writeInt(((Item)o).itemID);
		} else
		if (o instanceof Achievement)
		{
			os.writeByte(14);
			os.writeByte(2);
			os.writeInt(((StatBase) ((Achievement)o)).statId);
		} else
		if (o instanceof Potion)
		{
			os.writeByte(14);
			os.writeByte(3);
			os.writeInt(((Potion)o).id);
		} else
		if (o instanceof Enchantment)
		{
			os.writeByte(14);
			os.writeByte(4);
			os.writeInt(((Enchantment)o).effectId);
		} else
		if (o instanceof ChunkCoordinates)
		{
			os.writeByte(15);
			os.writeByte(0);
			ChunkCoordinates oa = (ChunkCoordinates)o;
			os.writeInt(oa.posX);
			os.writeInt(oa.posY);
			os.writeInt(oa.posZ);
		} else
		if (o instanceof ChunkCoordIntPair)
		{
			os.writeByte(15);
			os.writeByte(1);
			ChunkCoordIntPair oa = (ChunkCoordIntPair)o;
			os.writeInt(oa.chunkXPos);
			os.writeInt(oa.getCenterZPosition() - 8 >> 4);
		} else
		if (o instanceof ChunkPosition)
		{
			os.writeByte(15);
			os.writeByte(1);
			ChunkPosition oa = (ChunkPosition)o;
			os.writeInt(oa.x);
			os.writeInt(oa.y);
			os.writeInt(oa.z);
		} else
		if (o instanceof TileEntity)
		{
			os.writeByte(16);
			TileEntity oa = (TileEntity)o;
			os.writeInt(oa.worldObj.provider.dimensionId);
			os.writeInt(oa.xCoord);
			os.writeInt(oa.yCoord);
			os.writeInt(oa.zCoord);
		} else
		if (o instanceof World)
		{
			os.writeByte(17);
			os.writeInt(((World)o).provider.dimensionId);
		} else
		if (o instanceof Float)
		{
			os.writeByte(18);
			os.writeFloat(((Float)o).floatValue());
		} else
		if (o instanceof float[])
		{
			os.writeByte(19);
			float oa[] = (float[])(float[])o;
			os.writeShort(oa.length);
			for (int i = 0; i < oa.length; i++)
				os.writeFloat(oa[i]);

		} else
		if (o instanceof Double)
		{
			os.writeByte(20);
			os.writeDouble(((Double)o).doubleValue());
		} else
		if (o instanceof double[])
		{
			os.writeByte(21);
			double oa[] = (double[])(double[])o;
			os.writeShort(oa.length);
			for (int i = 0; i < oa.length; i++)
				os.writeDouble(oa[i]);

		} else
		if (o == null)
			os.writeByte(127);
		else
			IC2.platform.displayError((new StringBuilder()).append("An unknown data type was attempted to be encoded for sending through\nmultiplayer.\nThis could happen due to a bug.\n\n(Technical information: ").append(o.getClass().getName()).append(")").toString());
	}
}
