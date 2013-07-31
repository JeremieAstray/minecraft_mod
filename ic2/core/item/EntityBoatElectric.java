// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EntityBoatElectric.java

package ic2.core.item;

import ic2.api.item.*;
import ic2.core.Ic2Items;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

// Referenced classes of package ic2.core.item:
//			EntityIC2Boat

public class EntityBoatElectric extends EntityIC2Boat
{

	private static final int euConsume = 4;
	private boolean accelerated;

	public EntityBoatElectric(World par1World)
	{
		super(par1World);
		accelerated = false;
	}

	protected ItemStack getItem()
	{
		return Ic2Items.boatElectric.copy();
	}

	protected double getBreakMotion()
	{
		return 0.5D;
	}

	protected void breakBoat(double motion)
	{
		entityDropItem(getItem(), 0.0F);
	}

	protected double getAccelerationFactor()
	{
		return accelerated ? 1.5D : 0.25D;
	}

	protected double getTopSpeed()
	{
		return 0.69999999999999996D;
	}

	protected boolean isOnWater(AxisAlignedBB aabb)
	{
		return super.worldObj.isAABBInMaterial(aabb, Material.water) || super.worldObj.isAABBInMaterial(aabb, Material.lava);
	}

	public String getTexture()
	{
		return "textures/models/boatElectric.png";
	}

	public void onUpdate()
	{
		super.isImmuneToFire = true;
		extinguish();
		if (super.ridingEntity != null)
			super.ridingEntity.extinguish();
		accelerated = false;
		if ((Math.abs(super.motionX) > 0.10000000000000001D || Math.abs(super.motionZ) > 0.10000000000000001D) && (super.riddenByEntity instanceof EntityPlayer))
		{
			EntityPlayer player = (EntityPlayer)super.riddenByEntity;
			int i = 0;
			do
			{
				if (i >= 4)
					break;
				if (player.inventory.armorInventory[i] != null && (player.inventory.armorInventory[i].getItem() instanceof IElectricItem) && ElectricItem.manager.discharge(player.inventory.armorInventory[i], 4, 0x7fffffff, true, true) == 4)
				{
					ElectricItem.manager.discharge(player.inventory.armorInventory[i], 4, 0x7fffffff, true, false);
					accelerated = true;
					break;
				}
				i++;
			} while (true);
		}
		super.onUpdate();
	}
}
