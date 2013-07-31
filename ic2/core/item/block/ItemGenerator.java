// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemGenerator.java

package ic2.core.item.block;

import net.minecraft.item.ItemStack;

// Referenced classes of package ic2.core.item.block:
//			ItemBlockRare

public class ItemGenerator extends ItemBlockRare
{

	public ItemGenerator(int i)
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
			return "blockGenerator";

		case 1: // '\001'
			return "blockGeoGenerator";

		case 2: // '\002'
			return "blockWaterGenerator";

		case 3: // '\003'
			return "blockSolarGenerator";

		case 4: // '\004'
			return "blockWindGenerator";

		case 5: // '\005'
			return "blockNuclearReactor";
		}
		return null;
	}
}
