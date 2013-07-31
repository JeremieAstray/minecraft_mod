// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ExplosionWhitelist.java

package ic2.api.tile;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.block.Block;

public final class ExplosionWhitelist
{

	private static Set whitelist = new HashSet();

	public ExplosionWhitelist()
	{
	}

	public static void addWhitelistedBlock(Block block)
	{
		whitelist.add(block);
	}

	public static void removeWhitelistedBlock(Block block)
	{
		whitelist.remove(block);
	}

	public static boolean isBlockWhitelisted(Block block)
	{
		return whitelist.contains(block);
	}

}
