// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemNanoSaber.java

package ic2.core.item.tool;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItemManager;
import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.init.InternalName;
import ic2.core.item.armor.ItemArmorNanoSuit;
import ic2.core.item.armor.ItemArmorQuantumSuit;
import ic2.core.util.StackUtil;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.tool:
//			ItemElectricTool

public class ItemNanoSaber extends ItemElectricTool
{

	public static int ticker = 0;
	private Icon textures[];
	private int soundTicker;
	private final Random shinyrand = new Random();

	public ItemNanoSaber(Configuration config, InternalName internalName)
	{
		super(config, internalName, EnumToolMaterial.IRON, 10);
		soundTicker = 0;
		maxCharge = 40000;
		transferLimit = 128;
		tier = 2;
		mineableBlocks.add(Block.web);
	}

	public void registerIcons(IconRegister iconRegister)
	{
		textures = new Icon[3];
		textures[0] = iconRegister.registerIcon((new StringBuilder()).append("ic2:").append(getUnlocalizedName()).append(".").append(InternalName.off.name()).toString());
		textures[1] = iconRegister.registerIcon((new StringBuilder()).append("ic2:").append(getUnlocalizedName()).append(".").append(InternalName.active.name()).toString());
		textures[2] = iconRegister.registerIcon((new StringBuilder()).append("ic2:").append(getUnlocalizedName()).append(".").append(InternalName.activeAlternate.name()).toString());
	}

	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	public Icon getIcon(ItemStack itemStack, int pass)
	{
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
		if (nbtData.getBoolean("active"))
		{
			if (shinyrand.nextBoolean())
				return textures[1];
			else
				return textures[2];
		} else
		{
			return textures[0];
		}
	}

	public float getStrVsBlock(ItemStack itemStack, Block block)
	{
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
		if (nbtData.getBoolean("active"))
		{
			soundTicker++;
			if (soundTicker % 4 == 0)
				IC2.platform.playSoundSp(getRandomSwingSound(), 1.0F, 1.0F);
			return 4F;
		} else
		{
			return 1.0F;
		}
	}

	public boolean hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase source)
	{
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
		if (!nbtData.getBoolean("active"))
			return true;
		if (IC2.platform.isSimulating() && (!(source instanceof EntityPlayer) || !MinecraftServer.getServer().isPVPEnabled()))
		{
			for (int i = 0; i < 4; i++)
			{
				ItemStack armor = target.getCurrentItemOrArmor(i + 1);
				if (armor == null)
					continue;
				int amount = 0;
				if (armor.getItem() instanceof ItemArmorNanoSuit)
					amount = 4800;
				else
				if (armor.getItem() instanceof ItemArmorQuantumSuit)
					amount = 30000;
				if (amount <= 0)
					continue;
				ElectricItem.manager.discharge(armor, amount, tier, true, false);
				if (!ElectricItem.manager.canUse(armor, 1))
					target.setCurrentItemOrArmor(i + 1, null);
				drainSaber(itemStack, 2, source);
			}

			drainSaber(itemStack, 5, source);
		}
		if (IC2.platform.isRendering())
			IC2.platform.playSoundSp(getRandomSwingSound(), 1.0F, 1.0F);
		return true;
	}

	public String getRandomSwingSound()
	{
		switch (IC2.random.nextInt(3))
		{
		default:
			return "nanosabreSwing";

		case 1: // '\001'
			return "nanosabreSwingOne";

		case 2: // '\002'
			return "nanosabreSwingTwo";
		}
	}

	public boolean onBlockStartBreak(ItemStack itemStack, int i, int j, int k, EntityPlayer player)
	{
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
		if (nbtData.getBoolean("active"))
			drainSaber(itemStack, 10, player);
		return false;
	}

	public float getDamageVsEntity(Entity entity, ItemStack itemStack)
	{
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
		return !nbtData.getBoolean("active") ? 4F : 20F;
	}

	public boolean isFull3D()
	{
		return true;
	}

	public boolean canHarvestBlock(Block block)
	{
		return block.blockID == Block.web.blockID;
	}

	public static void drainSaber(ItemStack itemStack, int damage, EntityLivingBase entity)
	{
		if (!ElectricItem.manager.use(itemStack, damage * 8, entity))
		{
			NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
			nbtData.setBoolean("active", false);
		}
	}

	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityplayer)
	{
		if (!IC2.platform.isSimulating())
			return itemStack;
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
		if (nbtData.getBoolean("active"))
			nbtData.setBoolean("active", false);
		else
		if (ElectricItem.manager.canUse(itemStack, 16))
		{
			nbtData.setBoolean("active", true);
			world.playSoundAtEntity(entityplayer, "nanosabrePower", 1.0F, 1.0F);
		}
		return itemStack;
	}

	public void onUpdate(ItemStack itemStack, World world, Entity entity, int slot, boolean par5)
	{
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
		if (!nbtData.getBoolean("active"))
			return;
		if (ticker % 16 == 0 && (entity instanceof EntityPlayerMP))
			if (slot < 9)
				drainSaber(itemStack, 64, (EntityPlayer)entity);
			else
			if (ticker % 64 == 0)
				drainSaber(itemStack, 16, (EntityPlayer)entity);
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.uncommon;
	}

}
