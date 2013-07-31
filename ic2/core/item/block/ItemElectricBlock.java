// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemElectricBlock.java

package ic2.core.item.block;

import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.core.item.block:
//			ItemBlockRare

public class ItemElectricBlock extends ItemBlockRare
{

	public ItemElectricBlock(int i)
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	public int getMetadata(int i)
	{
		return i;
	}

	public String getUnlocalizedName(ItemStack itemstack)
	{
		int meta = itemstack.getItemDamage();
		switch (meta)
		{
		case 0: // '\0'
			return "blockBatBox";

		case 1: // '\001'
			return "blockMFE";

		case 2: // '\002'
			return "blockMFSU";

		case 3: // '\003'
			return "blockTransformerLV";

		case 4: // '\004'
			return "blockTransformerMV";

		case 5: // '\005'
			return "blockTransformerHV";
		}
		return null;
	}
}
