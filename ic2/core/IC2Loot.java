// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IC2Loot.java

package ic2.core;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

// Referenced classes of package ic2.core:
//			Ic2Items

public class IC2Loot
{

	private static final WeightedRandomChestContent MINESHAFT_CORRIDOR[];
	private static final WeightedRandomChestContent STRONGHOLD_CORRIDOR[];
	private static final WeightedRandomChestContent STRONGHOLD_CROSSING[];
	private static final WeightedRandomChestContent VILLAGE_BLACKSMITH[];
	private static final WeightedRandomChestContent BONUS_CHEST[];
	private static final WeightedRandomChestContent DUNGEON_CHEST[];
	private static final WeightedRandomChestContent bronzeToolsArmor[];
	private static final WeightedRandomChestContent ingots[];
	private static WeightedRandomChestContent rubberSapling[] = new WeightedRandomChestContent[0];

	public IC2Loot()
	{
		if (Ic2Items.rubberSapling != null)
			rubberSapling = (new WeightedRandomChestContent[] {
				new WeightedRandomChestContent(Ic2Items.rubberSapling.copy(), 1, 4, 4)
			});
		addLoot("mineshaftCorridor", new WeightedRandomChestContent[][] {
			MINESHAFT_CORRIDOR, ingots
		});
		addLoot("pyramidDesertyChest", new WeightedRandomChestContent[][] {
			bronzeToolsArmor, ingots
		});
		addLoot("pyramidJungleChest", new WeightedRandomChestContent[][] {
			bronzeToolsArmor, ingots
		});
		addLoot("strongholdCorridor", new WeightedRandomChestContent[][] {
			STRONGHOLD_CORRIDOR, bronzeToolsArmor, ingots
		});
		addLoot("strongholdCrossing", new WeightedRandomChestContent[][] {
			STRONGHOLD_CROSSING, bronzeToolsArmor, ingots
		});
		addLoot("villageBlacksmith", new WeightedRandomChestContent[][] {
			VILLAGE_BLACKSMITH, bronzeToolsArmor, ingots, rubberSapling
		});
		addLoot("bonusChest", new WeightedRandomChestContent[][] {
			BONUS_CHEST
		});
		addLoot("dungeonChest", new WeightedRandomChestContent[][] {
			DUNGEON_CHEST
		});
	}

	private transient void addLoot(String category, WeightedRandomChestContent loot[][])
	{
		ChestGenHooks cgh = ChestGenHooks.getInfo(category);
		WeightedRandomChestContent arr$[][] = loot;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			WeightedRandomChestContent lootList[] = arr$[i$];
			WeightedRandomChestContent arr$[] = lootList;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				WeightedRandomChestContent lootEntry = arr$[i$];
				cgh.addItem(lootEntry);
			}

		}

	}

	static 
	{
		MINESHAFT_CORRIDOR = (new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(Ic2Items.uraniumDrop.copy(), 1, 2, 1), new WeightedRandomChestContent(Ic2Items.bronzePickaxe.copy(), 1, 1, 1), new WeightedRandomChestContent(Ic2Items.filledTinCan.copy(), 4, 16, 8)
		});
		STRONGHOLD_CORRIDOR = (new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(Ic2Items.matter.copy(), 1, 4, 1)
		});
		STRONGHOLD_CROSSING = (new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(Ic2Items.bronzePickaxe.copy(), 1, 1, 1)
		});
		VILLAGE_BLACKSMITH = (new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(Ic2Items.bronzeIngot.copy(), 2, 4, 5)
		});
		BONUS_CHEST = (new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(Ic2Items.treetap.copy(), 1, 1, 2)
		});
		DUNGEON_CHEST = (new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(Ic2Items.copperIngot.copy(), 2, 5, 100), new WeightedRandomChestContent(Ic2Items.tinIngot.copy(), 2, 5, 100), new WeightedRandomChestContent(new ItemStack(Item.recordBlocks), 1, 1, 5), new WeightedRandomChestContent(new ItemStack(Item.recordChirp), 1, 1, 5), new WeightedRandomChestContent(new ItemStack(Item.recordFar), 1, 1, 5), new WeightedRandomChestContent(new ItemStack(Item.recordMall), 1, 1, 5), new WeightedRandomChestContent(new ItemStack(Item.recordMellohi), 1, 1, 5), new WeightedRandomChestContent(new ItemStack(Item.recordStal), 1, 1, 5), new WeightedRandomChestContent(new ItemStack(Item.recordStrad), 1, 1, 5), new WeightedRandomChestContent(new ItemStack(Item.recordWard), 1, 1, 5), 
			new WeightedRandomChestContent(new ItemStack(Item.record11), 1, 1, 5), new WeightedRandomChestContent(new ItemStack(Item.recordWait), 1, 1, 5)
		});
		bronzeToolsArmor = (new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(Ic2Items.bronzePickaxe.copy(), 1, 1, 3), new WeightedRandomChestContent(Ic2Items.bronzeSword.copy(), 1, 1, 3), new WeightedRandomChestContent(Ic2Items.bronzeHelmet.copy(), 1, 1, 3), new WeightedRandomChestContent(Ic2Items.bronzeChestplate.copy(), 1, 1, 3), new WeightedRandomChestContent(Ic2Items.bronzeLeggings.copy(), 1, 1, 3), new WeightedRandomChestContent(Ic2Items.bronzeBoots.copy(), 1, 1, 3)
		});
		ingots = (new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(Ic2Items.copperIngot.copy(), 2, 6, 9), new WeightedRandomChestContent(Ic2Items.tinIngot.copy(), 1, 5, 8)
		});
	}
}
