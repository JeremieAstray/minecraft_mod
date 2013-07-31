// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemIC2Boat.java

package ic2.core.item;

import ic2.core.Ic2Items;
import ic2.core.init.InternalName;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.item:
//			ItemIC2, EntityIC2Boat, EntityBoatCarbon, EntityBoatRubber, 
//			EntityBoatElectric

public class ItemIC2Boat extends ItemIC2
{

	public ItemIC2Boat(Configuration config, InternalName internalName)
	{
		super(config, internalName);
		setHasSubtypes(true);
		Ic2Items.boatCarbon = new ItemStack(this, 1, 0);
		Ic2Items.boatRubber = new ItemStack(this, 1, 1);
		Ic2Items.boatRubberBroken = new ItemStack(this, 1, 2);
		Ic2Items.boatElectric = new ItemStack(this, 1, 3);
	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		EntityIC2Boat entityboat = makeBoat(par1ItemStack, par2World, par3EntityPlayer);
		if (entityboat == null)
			return par1ItemStack;
		float f = 1.0F;
		float f1 = ((Entity) (par3EntityPlayer)).prevRotationPitch + (((Entity) (par3EntityPlayer)).rotationPitch - ((Entity) (par3EntityPlayer)).prevRotationPitch) * f;
		float f2 = ((Entity) (par3EntityPlayer)).prevRotationYaw + (((Entity) (par3EntityPlayer)).rotationYaw - ((Entity) (par3EntityPlayer)).prevRotationYaw) * f;
		double d0 = ((Entity) (par3EntityPlayer)).prevPosX + (((Entity) (par3EntityPlayer)).posX - ((Entity) (par3EntityPlayer)).prevPosX) * (double)f;
		double d1 = (((Entity) (par3EntityPlayer)).prevPosY + (((Entity) (par3EntityPlayer)).posY - ((Entity) (par3EntityPlayer)).prevPosY) * (double)f + 1.6200000000000001D) - (double)((Entity) (par3EntityPlayer)).yOffset;
		double d2 = ((Entity) (par3EntityPlayer)).prevPosZ + (((Entity) (par3EntityPlayer)).posZ - ((Entity) (par3EntityPlayer)).prevPosZ) * (double)f;
		Vec3 vec3 = par2World.getWorldVec3Pool().getVecFromPool(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
		float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
		float f5 = -MathHelper.cos(-f1 * 0.01745329F);
		float f6 = MathHelper.sin(-f1 * 0.01745329F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		double d3 = 5D;
		Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
		MovingObjectPosition movingobjectposition = par2World.clip(vec3, vec31, true);
		if (movingobjectposition == null)
			return par1ItemStack;
		Vec3 vec32 = par3EntityPlayer.getLook(f);
		boolean flag = false;
		float f9 = 1.0F;
		List list = par2World.getEntitiesWithinAABBExcludingEntity(par3EntityPlayer, ((Entity) (par3EntityPlayer)).boundingBox.addCoord(vec32.xCoord * d3, vec32.yCoord * d3, vec32.zCoord * d3).expand(f9, f9, f9));
		Iterator i$ = list.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			Entity entity = (Entity)i$.next();
			if (entity.canBeCollidedWith())
			{
				float f10 = entity.getCollisionBorderSize();
				AxisAlignedBB axisalignedbb = entity.boundingBox.expand(f10, f10, f10);
				if (axisalignedbb.isVecInside(vec3))
					flag = true;
			}
		} while (true);
		if (flag)
			return par1ItemStack;
		if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE)
		{
			int i = movingobjectposition.blockX;
			int j = movingobjectposition.blockY;
			int k = movingobjectposition.blockZ;
			if (par2World.getBlockId(i, j, k) == Block.snow.blockID)
				j--;
			entityboat.setPosition((float)i + 0.5F, (float)j + 1.0F, (float)k + 0.5F);
			entityboat.rotationYaw = ((MathHelper.floor_double((double)((((Entity) (par3EntityPlayer)).rotationYaw * 4F) / 360F) + 0.5D) & 3) - 1) * 90;
			if (!par2World.getCollidingBoundingBoxes(entityboat, ((Entity) (entityboat)).boundingBox.expand(-0.10000000000000001D, -0.10000000000000001D, -0.10000000000000001D)).isEmpty())
				return par1ItemStack;
			if (!par2World.isRemote)
				par2World.spawnEntityInWorld(entityboat);
			if (!par3EntityPlayer.capabilities.isCreativeMode)
				par1ItemStack.stackSize--;
		}
		return par1ItemStack;
	}

	protected EntityIC2Boat makeBoat(ItemStack stack, World world, EntityPlayer player)
	{
		switch (stack.getItemDamage())
		{
		case 0: // '\0'
			return new EntityBoatCarbon(world);

		case 1: // '\001'
			return new EntityBoatRubber(world);

		case 3: // '\003'
			return new EntityBoatElectric(world);

		case 2: // '\002'
		default:
			return null;
		}
	}

	public String getUnlocalizedName(ItemStack itemStack)
	{
		InternalName ret;
		switch (itemStack.getItemDamage())
		{
		case 0: // '\0'
			ret = InternalName.boatCarbon;
			break;

		case 1: // '\001'
			ret = InternalName.boatRubber;
			break;

		case 2: // '\002'
			ret = InternalName.boatRubberBroken;
			break;

		case 3: // '\003'
			ret = InternalName.boatElectric;
			break;

		default:
			return null;
		}
		return ret.name();
	}

	public void getSubItems(int i, CreativeTabs tabs, List itemList)
	{
		int meta = 0;
		do
		{
			if (meta > 32767)
				break;
			ItemStack stack = new ItemStack(this, 1, meta);
			if (getUnlocalizedName(stack) == null)
				break;
			itemList.add(stack);
			meta++;
		} while (true);
	}
}
