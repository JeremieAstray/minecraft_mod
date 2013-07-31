// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemMachine.java

package ic2.core.item.block;

import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.core.item.block:
//			ItemBlockRare

public class ItemMachine extends ItemBlockRare
{

	public ItemMachine(int i)
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
			return "blockMachine";

		case 1: // '\001'
			return "blockIronFurnace";

		case 2: // '\002'
			return "blockElecFurnace";

		case 3: // '\003'
			return "blockMacerator";

		case 4: // '\004'
			return "blockExtractor";

		case 5: // '\005'
			return "blockCompressor";

		case 6: // '\006'
			return "blockCanner";

		case 7: // '\007'
			return "blockMiner";

		case 8: // '\b'
			return "blockPump";

		case 9: // '\t'
			return "blockMagnetizer";

		case 10: // '\n'
			return "blockElectrolyzer";

		case 11: // '\013'
			return "blockRecycler";

		case 12: // '\f'
			return "blockAdvMachine";

		case 13: // '\r'
			return "blockInduction";

		case 14: // '\016'
			return "blockMatter";

		case 15: // '\017'
			return "blockTerra";
		}
		return null;
	}
}
