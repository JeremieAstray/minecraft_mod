// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemSprayer.java

package ic2.core.item.tool;

import ic2.core.*;
import ic2.core.block.wiring.TileEntityCable;
import ic2.core.init.InternalName;
import ic2.core.item.ItemGradual;
import ic2.core.item.armor.ItemArmorCFPack;
import java.util.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

public class ItemSprayer extends ItemGradual
{

	public ItemSprayer(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		setMaxDamage(1602);
	}

	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, 
			float a, float b, float c)
	{
		if (!IC2.platform.isSimulating())
			return true;
		ItemStack pack = entityplayer.inventory.armorInventory[2];
		boolean pulledFromCFPack = pack != null && pack.itemID == Ic2Items.cfPack.itemID && ((ItemArmorCFPack)pack.getItem()).getCFPellet(entityplayer, pack);
		if (!pulledFromCFPack && itemstack.getItemDamage() > 1501)
			return false;
		if (world.getBlockId(i, j, k) == Ic2Items.scaffold.itemID)
		{
			sprayFoam(world, i, j, k, calculateDirectionsFromPlayer(entityplayer), true);
			if (!pulledFromCFPack)
				itemstack.damageItem(100, entityplayer);
			return true;
		}
		if (l == 0)
			j--;
		if (l == 1)
			j++;
		if (l == 2)
			k--;
		if (l == 3)
			k++;
		if (l == 4)
			i--;
		if (l == 5)
			i++;
		world.getBlockId(i, j, k);
		if (sprayFoam(world, i, j, k, calculateDirectionsFromPlayer(entityplayer), false))
		{
			if (!pulledFromCFPack)
				itemstack.damageItem(100, entityplayer);
			return true;
		} else
		{
			return false;
		}
	}

	public static boolean[] calculateDirectionsFromPlayer(EntityPlayer player)
	{
		float yaw = ((Entity) (player)).rotationYaw % 360F;
		float pitch = ((Entity) (player)).rotationPitch;
		boolean r[] = {
			true, true, true, true, true, true
		};
		if (pitch >= -65F && pitch <= 65F)
		{
			if (yaw >= 300F && yaw <= 360F || yaw >= 0.0F && yaw <= 60F)
				r[2] = false;
			if (yaw >= 30F && yaw <= 150F)
				r[5] = false;
			if (yaw >= 120F && yaw <= 240F)
				r[3] = false;
			if (yaw >= 210F && yaw <= 330F)
				r[4] = false;
		}
		if (pitch <= -40F)
			r[0] = false;
		if (pitch >= 40F)
			r[1] = false;
		return r;
	}

	public boolean sprayFoam(World world, int i, int j, int k, boolean directions[], boolean scaffold)
	{
		int blockId = world.getBlockId(i, j, k);
		if (!scaffold && !Block.blocksList[Ic2Items.constructionFoam.itemID].canPlaceBlockAt(world, i, j, k) && (blockId != Ic2Items.copperCableBlock.itemID || world.getBlockMetadata(i, j, k) == 13) || scaffold && blockId != Ic2Items.scaffold.itemID)
			return false;
		ArrayList check = new ArrayList();
		ArrayList place = new ArrayList();
		int foamcount = getSprayMass();
		check.add(new ChunkPosition(i, j, k));
		for (int x = 0; x < check.size() && foamcount > 0; x++)
		{
			ChunkPosition set = (ChunkPosition)check.get(x);
			int targetBlockId = world.getBlockId(set.x, set.y, set.z);
			if (!scaffold && (Block.blocksList[Ic2Items.constructionFoam.itemID].canPlaceBlockAt(world, set.x, set.y, set.z) || targetBlockId == Ic2Items.copperCableBlock.itemID && world.getBlockMetadata(set.x, set.y, set.z) != 13) || scaffold && targetBlockId == Ic2Items.scaffold.itemID)
			{
				considerAddingCoord(set, place);
				addAdjacentSpacesOnList(set.x, set.y, set.z, check, directions, scaffold);
				foamcount--;
			}
		}

		Iterator i$ = place.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			ChunkPosition pos = (ChunkPosition)i$.next();
			int targetBlockId = world.getBlockId(pos.x, pos.y, pos.z);
			if (targetBlockId == Ic2Items.scaffold.itemID)
			{
				Block.blocksList[Ic2Items.scaffold.itemID].dropBlockAsItem(world, pos.x, pos.y, pos.z, world.getBlockMetadata(pos.x, pos.y, pos.z), 0);
				world.setBlock(pos.x, pos.y, pos.z, Ic2Items.constructionFoam.itemID, 0, 7);
			} else
			if (targetBlockId == Ic2Items.copperCableBlock.itemID)
			{
				net.minecraft.tileentity.TileEntity te = world.getBlockTileEntity(pos.x, pos.y, pos.z);
				if (te instanceof TileEntityCable)
					((TileEntityCable)te).changeFoam((byte)1);
			} else
			{
				world.setBlock(pos.x, pos.y, pos.z, Ic2Items.constructionFoam.itemID, 0, 7);
			}
		} while (true);
		return true;
	}

	public void addAdjacentSpacesOnList(int x, int y, int z, ArrayList foam, boolean directions[], boolean ignoreDirections)
	{
		int order[] = generateRngSpread(IC2.random);
		for (int i = 0; i < order.length; i++)
			if (ignoreDirections || directions[order[i]])
				switch (order[i])
				{
				case 0: // '\0'
					considerAddingCoord(new ChunkPosition(x, y - 1, z), foam);
					break;

				case 1: // '\001'
					considerAddingCoord(new ChunkPosition(x, y + 1, z), foam);
					break;

				case 2: // '\002'
					considerAddingCoord(new ChunkPosition(x, y, z - 1), foam);
					break;

				case 3: // '\003'
					considerAddingCoord(new ChunkPosition(x, y, z + 1), foam);
					break;

				case 4: // '\004'
					considerAddingCoord(new ChunkPosition(x - 1, y, z), foam);
					break;

				case 5: // '\005'
					considerAddingCoord(new ChunkPosition(x + 1, y, z), foam);
					break;
				}

	}

	public void considerAddingCoord(ChunkPosition coord, ArrayList list)
	{
		for (int i = 0; i < list.size(); i++)
			if (((ChunkPosition)list.get(i)).x == coord.x && ((ChunkPosition)list.get(i)).y == coord.y && ((ChunkPosition)list.get(i)).z == coord.z)
				return;

		list.add(coord);
	}

	public int[] generateRngSpread(Random random)
	{
		int re[] = {
			0, 1, 2, 3, 4, 5
		};
		for (int i = 0; i < 16; i++)
		{
			int first = random.nextInt(6);
			int second = random.nextInt(6);
			int save = re[first];
			re[first] = re[second];
			re[second] = save;
		}

		return re;
	}

	public static int getSprayMass()
	{
		return 13;
	}
}
