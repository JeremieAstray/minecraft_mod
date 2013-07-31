// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InvSlotConsumable.java

package ic2.core.block.invslot;

import ic2.core.block.TileEntityInventory;
import ic2.core.util.StackUtil;
import java.util.Random;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.core.block.invslot:
//			InvSlot

public abstract class InvSlotConsumable extends InvSlot
{

	private static final Random random = new Random();

	public InvSlotConsumable(TileEntityInventory base, String name, int oldStartIndex, int count)
	{
		super(base, name, oldStartIndex, InvSlot.Access.I, count, InvSlot.InvSide.TOP);
	}

	public InvSlotConsumable(TileEntityInventory base, String name, int oldStartIndex, InvSlot.Access access, int count, InvSlot.InvSide preferredSide)
	{
		super(base, name, oldStartIndex, access, count, preferredSide);
	}

	public abstract boolean accepts(ItemStack itemstack);

	public boolean canOutput()
	{
		return super.canOutput() || access != InvSlot.Access.NONE && get() != null && !accepts(get());
	}

	public ItemStack consume(int amount)
	{
		return consume(amount, false, false);
	}

	public ItemStack consume(int amount, boolean simulate, boolean consumeContainers)
	{
		ItemStack ret = null;
		for (int i = 0; i < size(); i++)
		{
			ItemStack itemStack = get(i);
			if (itemStack == null || !accepts(itemStack) || ret != null && !StackUtil.isStackEqual(itemStack, ret))
				continue;
			int currentAmount = Math.min(amount, itemStack.stackSize);
			amount -= currentAmount;
			if (!simulate)
				if (itemStack.stackSize == currentAmount)
				{
					if (!consumeContainers && itemStack.getItem().hasContainerItem())
						put(i, itemStack.getItem().getContainerItemStack(itemStack));
					else
						put(i, null);
				} else
				{
					itemStack.stackSize -= currentAmount;
				}
			if (ret == null)
				ret = StackUtil.copyWithSize(itemStack, currentAmount);
			else
				ret.stackSize += currentAmount;
			if (amount == 0)
				break;
		}

		return ret;
	}

	public ItemStack damage(int amount)
	{
		return damage(amount, null);
	}

	public ItemStack damage(int amount, EntityLivingBase src)
	{
		ItemStack ret = null;
		int damageApplied = 0;
		for (int i = 0; i < size(); i++)
		{
			ItemStack itemStack = get(i);
			if (itemStack == null || !accepts(itemStack) || !itemStack.getItem().isDamageable() || ret != null && (itemStack.itemID != ret.itemID || !ItemStack.areItemStackTagsEqual(itemStack, ret)))
				continue;
			int currentAmount = Math.min(amount, itemStack.getMaxDamage() - itemStack.getItemDamage());
			damageApplied += currentAmount;
			amount -= currentAmount;
			if (src != null)
				itemStack.damageItem(currentAmount, src);
			else
				itemStack.attemptDamageItem(currentAmount, random);
			if (itemStack.getItemDamage() >= itemStack.getMaxDamage())
			{
				itemStack.stackSize--;
				itemStack.setItemDamage(0);
			}
			if (itemStack.stackSize == 0)
				put(i, null);
			else
				i--;
			if (ret == null)
				ret = itemStack.copy();
			if (amount == 0)
				break;
		}

		ret.stackSize = damageApplied / ret.getMaxDamage();
		ret.setItemDamage(damageApplied % ret.getMaxDamage());
		return ret;
	}

}
