// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IC2Potion.java

package ic2.core;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

// Referenced classes of package ic2.core:
//			IC2DamageSource

public class IC2Potion extends Potion
{

	public static final IC2Potion radiation = new IC2Potion(24, true, 0x4e9331);

	public IC2Potion(int id, boolean badEffect, int liquidColor)
	{
		super(id, badEffect, liquidColor);
	}

	public void performEffect(EntityLivingBase entity, int amplifier)
	{
		if (super.id == ((Potion) (radiation)).id)
			entity.attackEntityFrom(IC2DamageSource.radiation, amplifier + 1);
	}

	public boolean isReady(int duration, int amplifier)
	{
		if (super.id == ((Potion) (radiation)).id)
		{
			int rate = 25 >> amplifier;
			return rate <= 0 ? true : duration % rate == 0;
		} else
		{
			return false;
		}
	}

	public static void init()
	{
		radiation.setPotionName("ic2.potion.radiation");
		radiation.setIconIndex(6, 0);
		radiation.setEffectiveness(0.25D);
	}

}
