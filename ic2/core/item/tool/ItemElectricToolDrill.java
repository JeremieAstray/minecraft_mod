// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemElectricToolDrill.java

package ic2.core.item.tool;

import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.init.InternalName;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.*;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item.tool:
//			ItemElectricTool

public class ItemElectricToolDrill extends ItemElectricTool
{

	public int soundTicker;

	public ItemElectricToolDrill(Configuration config, InternalName internalName)
	{
		super(config, internalName, EnumToolMaterial.IRON, 50);
		soundTicker = 0;
		maxCharge = 10000;
		transferLimit = 100;
		tier = 1;
		super.efficiencyOnProperMaterial = 8F;
	}

	public void init()
	{
		mineableBlocks.add(Block.cobblestone);
		mineableBlocks.add(Block.stoneSingleSlab);
		mineableBlocks.add(Block.stoneDoubleSlab);
		mineableBlocks.add(Block.stairsCobblestone);
		mineableBlocks.add(Block.stone);
		mineableBlocks.add(Block.sandStone);
		mineableBlocks.add(Block.stairsSandStone);
		mineableBlocks.add(Block.cobblestoneMossy);
		mineableBlocks.add(Block.oreIron);
		mineableBlocks.add(Block.blockIron);
		mineableBlocks.add(Block.oreCoal);
		mineableBlocks.add(Block.blockGold);
		mineableBlocks.add(Block.oreGold);
		mineableBlocks.add(Block.oreDiamond);
		mineableBlocks.add(Block.blockDiamond);
		mineableBlocks.add(Block.ice);
		mineableBlocks.add(Block.netherrack);
		mineableBlocks.add(Block.oreLapis);
		mineableBlocks.add(Block.blockLapis);
		mineableBlocks.add(Block.oreRedstone);
		mineableBlocks.add(Block.oreRedstoneGlowing);
		mineableBlocks.add(Block.brick);
		mineableBlocks.add(Block.stairsBrick);
		mineableBlocks.add(Block.glowStone);
		mineableBlocks.add(Block.grass);
		mineableBlocks.add(Block.dirt);
		mineableBlocks.add(Block.mycelium);
		mineableBlocks.add(Block.sand);
		mineableBlocks.add(Block.gravel);
		mineableBlocks.add(Block.snow);
		mineableBlocks.add(Block.blockSnow);
		mineableBlocks.add(Block.blockClay);
		mineableBlocks.add(Block.tilledField);
		mineableBlocks.add(Block.stoneBrick);
		mineableBlocks.add(Block.stairsStoneBrick);
		mineableBlocks.add(Block.netherBrick);
		mineableBlocks.add(Block.stairsNetherBrick);
		mineableBlocks.add(Block.slowSand);
		mineableBlocks.add(Block.anvil);
	}

	public boolean canHarvestBlock(Block block)
	{
		if (block.blockMaterial == Material.rock || block.blockMaterial == Material.iron)
			return true;
		else
			return super.canHarvestBlock(block);
	}

	public float getStrVsBlock(ItemStack itemstack, Block block)
	{
		soundTicker++;
		if (soundTicker % 4 == 0)
			IC2.platform.playSoundSp(getRandomDrillSound(), 1.0F, 1.0F);
		return super.getStrVsBlock(itemstack, block);
	}

	public float getStrVsBlock(ItemStack itemstack, Block block, int md)
	{
		soundTicker++;
		if (soundTicker % 4 == 0)
			IC2.platform.playSoundSp(getRandomDrillSound(), 1.0F, 1.0F);
		return super.getStrVsBlock(itemstack, block, md);
	}

	public String getRandomDrillSound()
	{
		switch (IC2.random.nextInt(4))
		{
		default:
			return "drill";

		case 1: // '\001'
			return "drillOne";

		case 2: // '\002'
			return "drillTwo";

		case 3: // '\003'
			return "drillThree";
		}
	}
}
