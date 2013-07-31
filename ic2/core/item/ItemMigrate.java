// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemMigrate.java

package ic2.core.item;

import ic2.core.init.InternalName;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item:
//			ItemIC2

public class ItemMigrate extends ItemIC2
{

	private final Item targetItem;

	public ItemMigrate(Configuration config, InternalName oldName, Item targetItem)
	{
		super(config, oldName);
		this.targetItem = targetItem;
		setCreativeTab(null);
	}

	public void registerIcons(IconRegister iconregister)
	{
	}

	public Icon getIconFromDamage(int meta)
	{
		if (targetItem != null)
			return targetItem.getIconFromDamage(meta);
		else
			return super.itemIcon;
	}

	public void onUpdate(ItemStack itemStack, World world, Entity entity, int slot, boolean isHeld)
	{
		if (targetItem != null)
			itemStack.itemID = targetItem.itemID;
	}
}
