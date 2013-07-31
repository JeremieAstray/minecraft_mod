// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PointExplosion.java

package ic2.core;

import ic2.core.util.Util;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class PointExplosion extends Explosion
{

	private final World world;
	private final float dropRate;
	private final int entityDamage;

	public PointExplosion(World world, Entity entity, double x, double y, double z, float power, float dropRate, int entityDamage)
	{
		super(world, entity, x, y, z, power);
		this.world = world;
		this.dropRate = dropRate;
		this.entityDamage = entityDamage;
	}

	public void doExplosionA()
	{
		for (int x = Util.roundToNegInf(super.explosionX) - 1; x <= Util.roundToNegInf(super.explosionX) + 1; x++)
		{
			for (int y = Util.roundToNegInf(super.explosionY) - 1; y <= Util.roundToNegInf(super.explosionY) + 1; y++)
			{
				for (int z = Util.roundToNegInf(super.explosionZ) - 1; z <= Util.roundToNegInf(super.explosionZ) + 1; z++)
				{
					int blockId = world.getBlockId(x, y, z);
					if (blockId > 0 && Block.blocksList[blockId].getExplosionResistance(super.exploder, world, x, y, z, super.explosionX, super.explosionY, super.explosionZ) < super.explosionSize * 10F)
						super.affectedBlockPositions.add(new ChunkPosition(x, y, z));
				}

			}

		}

		List entitiesInRange = world.getEntitiesWithinAABBExcludingEntity(super.exploder, AxisAlignedBB.getAABBPool().getAABB(super.explosionX - 2D, super.explosionY - 2D, super.explosionZ - 2D, super.explosionX + 2D, super.explosionY + 2D, super.explosionZ + 2D));
		Entity entity;
		for (Iterator i$ = entitiesInRange.iterator(); i$.hasNext(); entity.attackEntityFrom(DamageSource.setExplosionSource(this), entityDamage))
			entity = (Entity)i$.next();

		super.explosionSize = 1.0F / dropRate;
	}
}
