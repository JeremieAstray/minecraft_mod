// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IC2DamageSource.java

package ic2.core;

import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.Explosion;

public class IC2DamageSource extends DamageSource
{

	public static IC2DamageSource electricity = new IC2DamageSource("electricity");
	public static IC2DamageSource nuke = (IC2DamageSource)(new IC2DamageSource("nuke")).setExplosion();
	public static IC2DamageSource radiation = (IC2DamageSource)(new IC2DamageSource("radiation")).setDamageBypassesArmor();

	public IC2DamageSource(String s)
	{
		super(s);
	}

	public static DamageSource setNukeSource(Explosion explosion)
	{
		return ((DamageSource) (explosion == null || explosion.func_94613_c() == null ? nuke : (new EntityDamageSource("nuke.player", explosion.func_94613_c())).setExplosion()));
	}

}
