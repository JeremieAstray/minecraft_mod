// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemElectricToolHoe.java

package ic2.core.item.tool;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItemManager;
import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.init.InternalName;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;
import net.minecraftforge.event.entity.player.UseHoeEvent;

// Referenced classes of package ic2.core.item.tool:
//			ItemElectricTool

public class ItemElectricToolHoe extends ItemElectricTool
{

	public ItemElectricToolHoe(Configuration config, InternalName internalName)
	{
		super(config, internalName, EnumToolMaterial.IRON, 50);
		maxCharge = 10000;
		transferLimit = 100;
		tier = 1;
		super.efficiencyOnProperMaterial = 16F;
	}

	public void init()
	{
		mineableBlocks.add(Block.dirt);
		mineableBlocks.add(Block.grass);
		mineableBlocks.add(Block.mycelium);
	}

	public boolean onBlockStartBreak(ItemStack itemStack, int x, int y, int z, EntityPlayer entityLiving)
	{
		ElectricItem.manager.use(itemStack, operationEnergyCost, entityLiving);
		return false;
	}

	public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, 
			float hitX, float hitY, float hitZ)
	{
		if (!entityPlayer.canPlayerEdit(x, y, z, side, itemStack))
			return false;
		if (!ElectricItem.manager.canUse(itemStack, operationEnergyCost))
			return false;
		if (MinecraftForge.EVENT_BUS.post(new UseHoeEvent(entityPlayer, itemStack, world, x, y, z)))
			return false;
		int blockId = world.getBlockId(x, y, z);
		if (side != 0 && world.isAirBlock(x, y + 1, z) && blockId == ((Block) (Block.grass)).blockID || blockId == Block.dirt.blockID)
		{
			Block block = Block.tilledField;
			world.playSoundEffect((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F, block.stepSound.getStepSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
			if (IC2.platform.isSimulating())
			{
				world.setBlock(x, y, z, block.blockID, 0, 3);
				ElectricItem.manager.use(itemStack, operationEnergyCost, entityPlayer);
			}
			return true;
		} else
		{
			return false;
		}
	}
}
