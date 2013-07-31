// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemIC2.java

package ic2.core.item;

import cpw.mods.fml.common.registry.GameRegistry;
import ic2.core.IC2;
import ic2.core.init.DefaultIds;
import ic2.core.init.InternalName;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.*;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.Configuration;

public class ItemIC2 extends Item
{

	private int rarity;
	protected Icon textures[];

	public ItemIC2(Configuration config, InternalName internalName)
	{
		super(IC2.getItemIdFor(config, internalName, DefaultIds.get(internalName)));
		rarity = 0;
		setUnlocalizedName(internalName.name());
		setCreativeTab(IC2.tabIC2);
		GameRegistry.registerItem(this, internalName.name());
	}

	public String getTextureFolder()
	{
		return null;
	}

	public String getTextureName(int index)
	{
		if (super.hasSubtypes)
			return getUnlocalizedName(new ItemStack(super.itemID, 1, index));
		if (index == 0)
			return getUnlocalizedName();
		else
			return null;
	}

	public void registerIcons(IconRegister iconRegister)
	{
		int indexCount;
		for (indexCount = 0; getTextureName(indexCount) != null; indexCount++);
		textures = new Icon[indexCount];
		String textureFolder = getTextureFolder() != null ? (new StringBuilder()).append(getTextureFolder()).append("/").toString() : "";
		for (int index = 0; index < indexCount; index++)
			textures[index] = iconRegister.registerIcon((new StringBuilder()).append("ic2:").append(textureFolder).append(getTextureName(index)).toString());

	}

	public Icon getIconFromDamage(int meta)
	{
		if (meta < textures.length)
			return textures[meta];
		else
			return textures.length >= 1 ? textures[0] : null;
	}

	public String getUnlocalizedName()
	{
		return super.getUnlocalizedName().substring(5);
	}

	public String getUnlocalizedName(ItemStack itemStack)
	{
		return getUnlocalizedName();
	}

	public String getItemDisplayName(ItemStack itemStack)
	{
		return StatCollector.translateToLocal((new StringBuilder()).append("ic2.").append(getUnlocalizedName(itemStack)).toString());
	}

	public ItemIC2 setRarity(int rarity)
	{
		this.rarity = rarity;
		return this;
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.values()[rarity];
	}
}
