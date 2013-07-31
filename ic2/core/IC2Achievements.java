// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IC2Achievements.java

package ic2.core;

import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.core.block.machine.tileentity.TileEntityCompressor;
import ic2.core.block.machine.tileentity.TileEntityMatter;
import java.util.Collection;
import java.util.HashMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.*;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

// Referenced classes of package ic2.core:
//			Ic2Items

public class IC2Achievements
	implements ICraftingHandler
{

	public HashMap achievementList;
	private int achievementBaseX;
	private int achievementBaseY;

	public IC2Achievements()
	{
		achievementBaseX = -4;
		achievementBaseY = -5;
		achievementList = new HashMap();
		registerAchievement(0xb3dee, "acquireResin", 2, 0, Ic2Items.resin, AchievementList.mineWood, false);
		if (Ic2Items.copperOre != null || Ic2Items.tinOre != null || Ic2Items.uraniumOre != null)
			registerAchievement(0xb3def, "mineOre", 4, 0, Ic2Items.copperOre != null ? Ic2Items.copperOre : Ic2Items.tinOre != null ? Ic2Items.tinOre : Ic2Items.uraniumOre, AchievementList.buildBetterPickaxe, false);
		registerAchievement(0xb3df0, "acquireRefinedIron", 0, 0, Ic2Items.refinedIronIngot, AchievementList.acquireIron, false);
		registerAchievement(0xb3df1, "buildCable", 0, 2, Ic2Items.insulatedCopperCableItem, "acquireRefinedIron", false);
		registerAchievement(0xb3df2, "buildGenerator", 6, 2, Ic2Items.generator, "buildCable", false);
		registerAchievement(0xb3df3, "buildMacerator", 6, 0, Ic2Items.macerator, "buildGenerator", false);
		registerAchievement(0xb3df5, "buildCoalDiamond", 8, 0, Ic2Items.industrialDiamond, "buildMacerator", false);
		registerAchievement(0xb3df6, "buildElecFurnace", 8, 2, Ic2Items.electroFurnace, "buildGenerator", false);
		registerAchievement(0xb3df7, "buildIndFurnace", 10, 2, Ic2Items.inductionFurnace, "buildElecFurnace", false);
		registerAchievement(0xb3df9, "buildCompressor", 4, 4, Ic2Items.compressor, "buildGenerator", false);
		registerAchievement(0xb3dfa, "compressUranium", 2, 4, Ic2Items.uraniumIngot, "buildCompressor", false);
		registerAchievement(0xb3dfb, "dieFromOwnNuke", 0, 4, Ic2Items.nuke, "compressUranium", true);
		registerAchievement(0xb3dfc, "buildExtractor", 8, 4, Ic2Items.extractor, "buildGenerator", false);
		registerAchievement(0xb3df8, "buildBatBox", 6, 6, Ic2Items.batBox, "buildGenerator", false);
		registerAchievement(0xb3dfd, "buildDrill", 8, 6, Ic2Items.miningDrill, "buildBatBox", false);
		registerAchievement(0xb3dfe, "buildDDrill", 10, 6, Ic2Items.diamondDrill, "buildDrill", false);
		registerAchievement(0xb3dff, "buildChainsaw", 4, 6, Ic2Items.chainsaw, "buildBatBox", false);
		registerAchievement(0xb3e00, "killCreeperChainsaw", 2, 6, Ic2Items.chainsaw, "buildChainsaw", true);
		registerAchievement(0xb3e01, "buildMFE", 6, 8, Ic2Items.mfeUnit, "buildBatBox", false);
		registerAchievement(0xb3e02, "buildMassFab", 8, 8, Ic2Items.massFabricator, "buildBatBox", false);
		registerAchievement(0xb3e03, "acquireMatter", 10, 8, Ic2Items.matter, "buildMassFab", false);
		registerAchievement(0xb3e04, "buildQArmor", 12, 8, Ic2Items.quantumBodyarmor, "acquireMatter", false);
		registerAchievement(0xb3e05, "starveWithQHelmet", 14, 8, Ic2Items.filledTinCan, "buildQArmor", true);
		registerAchievement(0xb3e06, "buildMiningLaser", 4, 8, Ic2Items.miningLaser, "buildMFE", false);
		registerAchievement(0xb3e07, "killDragonMiningLaser", 2, 8, Ic2Items.miningLaser, "buildMiningLaser", true);
		registerAchievement(0xb3e08, "buildMFS", 6, 10, Ic2Items.mfsUnit, "buildMFE", false);
		registerAchievement(0xb3e09, "buildTeleporter", 4, 10, Ic2Items.teleporter, "buildMFS", false);
		registerAchievement(0xb3e0a, "teleportFarAway", 2, 10, Ic2Items.teleporter, "buildTeleporter", true);
		registerAchievement(0xb3e0b, "buildTerraformer", 8, 10, Ic2Items.terraformer, "buildMFS", false);
		registerAchievement(0xb3e0c, "terraformEndCultivation", 10, 10, Ic2Items.cultivationTerraformerBlueprint, "buildTerraformer", true);
		AchievementPage.registerAchievementPage(new AchievementPage("IndustrialCraft 2", (Achievement[])achievementList.values().toArray(new Achievement[achievementList.size()])));
		MinecraftForge.EVENT_BUS.register(this);
		GameRegistry.registerCraftingHandler(this);
	}

	public Achievement registerAchievement(int id, String textId, int x, int y, ItemStack icon, Achievement requirement, boolean special)
	{
		Achievement achievement = new Achievement(id, textId, achievementBaseX + x, achievementBaseY + y, icon, requirement);
		if (special)
			achievement.setSpecial();
		achievement.registerAchievement();
		achievementList.put(textId, achievement);
		return achievement;
	}

	public Achievement registerAchievement(int id, String textId, int x, int y, ItemStack icon, String requirement, boolean special)
	{
		Achievement achievement = new Achievement(id, textId, achievementBaseX + x, achievementBaseY + y, icon, getAchievement(requirement));
		if (special)
			achievement.setSpecial();
		achievement.registerAchievement();
		achievementList.put(textId, achievement);
		return achievement;
	}

	public void issueAchievement(EntityPlayer entityplayer, String textId)
	{
		if (achievementList.containsKey(textId))
			entityplayer.triggerAchievement((StatBase)achievementList.get(textId));
	}

	public Achievement getAchievement(String textId)
	{
		if (achievementList.containsKey(textId))
			return (Achievement)achievementList.get(textId);
		else
			return null;
	}

	public void onCrafting(EntityPlayer entityplayer, ItemStack itemstack, IInventory iinventory)
	{
		if (itemstack.isItemEqual(Ic2Items.generator))
			issueAchievement(entityplayer, "buildGenerator");
		else
		if (itemstack.itemID == Ic2Items.insulatedCopperCableItem.itemID)
			issueAchievement(entityplayer, "buildCable");
		else
		if (itemstack.isItemEqual(Ic2Items.macerator))
			issueAchievement(entityplayer, "buildMacerator");
		else
		if (itemstack.isItemEqual(Ic2Items.electroFurnace))
			issueAchievement(entityplayer, "buildElecFurnace");
		else
		if (itemstack.isItemEqual(Ic2Items.compressor))
			issueAchievement(entityplayer, "buildCompressor");
		else
		if (itemstack.isItemEqual(Ic2Items.batBox))
			issueAchievement(entityplayer, "buildBatBox");
		else
		if (itemstack.isItemEqual(Ic2Items.mfeUnit))
			issueAchievement(entityplayer, "buildMFE");
		else
		if (itemstack.isItemEqual(Ic2Items.teleporter))
			issueAchievement(entityplayer, "buildTeleporter");
		else
		if (itemstack.isItemEqual(Ic2Items.massFabricator))
			issueAchievement(entityplayer, "buildMassFab");
		else
		if (itemstack.itemID == Ic2Items.quantumBodyarmor.itemID || itemstack.itemID == Ic2Items.quantumBoots.itemID || itemstack.itemID == Ic2Items.quantumHelmet.itemID || itemstack.itemID == Ic2Items.quantumLeggings.itemID)
			issueAchievement(entityplayer, "buildQArmor");
		else
		if (itemstack.isItemEqual(Ic2Items.extractor))
			issueAchievement(entityplayer, "buildExtractor");
		else
		if (itemstack.itemID == Ic2Items.miningDrill.itemID)
			issueAchievement(entityplayer, "buildDrill");
		else
		if (itemstack.itemID == Ic2Items.diamondDrill.itemID)
			issueAchievement(entityplayer, "buildDDrill");
		else
		if (itemstack.itemID == Ic2Items.chainsaw.itemID)
			issueAchievement(entityplayer, "buildChainsaw");
		else
		if (itemstack.itemID == Ic2Items.miningLaser.itemID)
			issueAchievement(entityplayer, "buildMiningLaser");
		else
		if (itemstack.isItemEqual(Ic2Items.mfsUnit))
			issueAchievement(entityplayer, "buildMFS");
		else
		if (itemstack.isItemEqual(Ic2Items.terraformer))
			issueAchievement(entityplayer, "buildTerraformer");
		else
		if (itemstack.isItemEqual(Ic2Items.coalChunk))
			issueAchievement(entityplayer, "buildCoalDiamond");
		else
		if (itemstack.isItemEqual(Ic2Items.inductionFurnace))
			issueAchievement(entityplayer, "buildIndFurnace");
	}

	public void onSmelting(EntityPlayer entityplayer, ItemStack itemstack)
	{
		if (itemstack.isItemEqual(Ic2Items.refinedIronIngot))
			issueAchievement(entityplayer, "acquireRefinedIron");
	}

	public void onMachineOp(EntityPlayer entityplayer, ItemStack itemstack, IInventory inventory)
	{
		if ((inventory instanceof TileEntityCompressor) && itemstack.itemID == Ic2Items.uraniumIngot.itemID)
			issueAchievement(entityplayer, "compressUranium");
		else
		if ((inventory instanceof TileEntityMatter) && itemstack.itemID == Ic2Items.matter.itemID)
			issueAchievement(entityplayer, "acquireMatter");
	}

	public void onItemPickup(EntityItemPickupEvent event)
	{
		if (Ic2Items.copperOre != null && event.item.getEntityItem().isItemEqual(Ic2Items.copperOre) || Ic2Items.tinOre != null && event.item.getEntityItem().isItemEqual(Ic2Items.tinOre) || Ic2Items.uraniumDrop != null && event.item.getEntityItem().isItemEqual(Ic2Items.uraniumDrop))
			issueAchievement(event.entityPlayer, "mineOre");
	}
}
