// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InvSlot.java

package ic2.core.block.invslot;

import ic2.core.block.TileEntityInventory;
import ic2.core.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InvSlot
{
	public static final class InvSide extends Enum
	{

		public static final InvSide ANY;
		public static final InvSide TOP;
		public static final InvSide BOTTOM;
		public static final InvSide SIDE;
		private static final InvSide $VALUES[];

		public static InvSide[] values()
		{
			return (InvSide[])$VALUES.clone();
		}

		public static InvSide valueOf(String name)
		{
			return (InvSide)Enum.valueOf(ic2/core/block/invslot/InvSlot$InvSide, name);
		}

		public boolean matches(int side)
		{
			return this == ANY || side == 0 && this == BOTTOM || side == 1 && this == TOP || side >= 2 && side <= 5 && this == SIDE;
		}

		static 
		{
			ANY = new InvSide("ANY", 0);
			TOP = new InvSide("TOP", 1);
			BOTTOM = new InvSide("BOTTOM", 2);
			SIDE = new InvSide("SIDE", 3);
			$VALUES = (new InvSide[] {
				ANY, TOP, BOTTOM, SIDE
			});
		}

		private InvSide(String s, int i)
		{
			super(s, i);
		}
	}

	public static final class Access extends Enum
	{

		public static final Access NONE;
		public static final Access I;
		public static final Access O;
		public static final Access IO;
		private static final Access $VALUES[];

		public static Access[] values()
		{
			return (Access[])$VALUES.clone();
		}

		public static Access valueOf(String name)
		{
			return (Access)Enum.valueOf(ic2/core/block/invslot/InvSlot$Access, name);
		}

		static 
		{
			NONE = new Access("NONE", 0);
			I = new Access("I", 1);
			O = new Access("O", 2);
			IO = new Access("IO", 3);
			$VALUES = (new Access[] {
				NONE, I, O, IO
			});
		}

		private Access(String s, int i)
		{
			super(s, i);
		}
	}


	public final TileEntityInventory base;
	public final String name;
	public final int oldStartIndex;
	private final ItemStack contents[];
	protected final Access access;
	public final InvSide preferredSide;

	public InvSlot(TileEntityInventory base, String name, int oldStartIndex, Access access, int count)
	{
		this(base, name, oldStartIndex, access, count, InvSide.ANY);
	}

	public InvSlot(TileEntityInventory base, String name, int oldStartIndex, Access access, int count, InvSide preferredSide)
	{
		contents = new ItemStack[count];
		this.base = base;
		this.name = name;
		this.oldStartIndex = oldStartIndex;
		this.access = access;
		this.preferredSide = preferredSide;
		base.addInvSlot(this);
	}

	public void readFromNbt(NBTTagCompound nbtTagCompound)
	{
		NBTTagList contentsTag = nbtTagCompound.getTagList("Contents");
		for (int i = 0; i < contentsTag.tagCount(); i++)
		{
			NBTTagCompound contentTag = (NBTTagCompound)contentsTag.tagAt(i);
			int index = contentTag.getByte("Index") & 0xff;
			put(index, ItemStack.loadItemStackFromNBT(contentTag));
		}

	}

	public void writeToNbt(NBTTagCompound nbtTagCompound)
	{
		NBTTagList contentsTag = new NBTTagList();
		for (int i = 0; i < contents.length; i++)
			if (contents[i] != null)
			{
				NBTTagCompound contentTag = new NBTTagCompound();
				contentTag.setByte("Index", (byte)i);
				contents[i].writeToNBT(contentTag);
				contentsTag.appendTag(contentTag);
			}

		nbtTagCompound.setTag("Contents", contentsTag);
	}

	public int size()
	{
		return contents.length;
	}

	public ItemStack get()
	{
		return get(0);
	}

	public ItemStack get(int index)
	{
		return contents[index];
	}

	public void put(ItemStack content)
	{
		put(0, content);
	}

	public void put(int index, ItemStack content)
	{
		contents[index] = content;
	}

	public void clear()
	{
		for (int i = 0; i < contents.length; i++)
			contents[i] = null;

	}

	public boolean accepts(ItemStack itemStack)
	{
		return true;
	}

	public boolean canInput()
	{
		return access == Access.I || access == Access.IO;
	}

	public boolean canOutput()
	{
		return access == Access.O || access == Access.IO;
	}

	public boolean isEmpty()
	{
		ItemStack arr$[] = contents;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			ItemStack itemStack = arr$[i$];
			if (itemStack != null)
				return false;
		}

		return true;
	}

	public void organize()
	{
		for (int dstIndex = 0; dstIndex < contents.length - 1; dstIndex++)
		{
			ItemStack dst = contents[dstIndex];
			if (dst != null && dst.stackSize >= dst.getMaxStackSize())
				continue;
			for (int srcIndex = dstIndex + 1; srcIndex < contents.length; srcIndex++)
			{
				ItemStack src = contents[srcIndex];
				if (src == null)
					continue;
				if (dst == null)
				{
					contents[srcIndex] = null;
					contents[dstIndex] = dst = src;
					continue;
				}
				if (!StackUtil.isStackEqual(dst, src))
					continue;
				int space = dst.getMaxStackSize() - dst.stackSize;
				if (src.stackSize <= space)
				{
					contents[srcIndex] = null;
					dst.stackSize += src.stackSize;
					continue;
				}
				src.stackSize -= space;
				dst.stackSize += space;
				break;
			}

		}

	}

	public String toString()
	{
		String ret = (new StringBuilder()).append(name).append("[").append(contents.length).append("]: ").toString();
		for (int i = 0; i < contents.length; i++)
		{
			ret = (new StringBuilder()).append(ret).append(contents[i]).toString();
			if (i < contents.length - 1)
				ret = (new StringBuilder()).append(ret).append(", ").toString();
		}

		return ret;
	}
}
