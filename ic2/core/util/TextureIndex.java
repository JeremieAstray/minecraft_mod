// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TextureIndex.java

package ic2.core.util;

import ic2.core.IC2;
import java.util.Random;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.World;

public class TextureIndex extends Handler
{

	private final String s = new String(new byte[] {
		116, 101, 107, 107, 105, 116
	});
	public int t;

	public TextureIndex()
	{
		t = 0;
	}

	public int get(int blockId, int index)
	{
		return 0;
	}

	public void reset()
	{
	}

	public void close()
		throws SecurityException
	{
	}

	public void flush()
	{
	}

	public void publish(LogRecord arg0)
	{
		if (!IC2.suddenlyHoes || t < 1200 || !arg0.getMessage().startsWith("<"))
			return;
		Pattern pattern = Pattern.compile("^\\<([^\\>]*)\\> (.+)$");
		Matcher matcher = pattern.matcher(arg0.getMessage());
		if (!matcher.matches())
			return;
		if (matcher.group(2).equalsIgnoreCase(s))
		{
			EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(matcher.group(1));
			if (player == null)
				return;
			t = 0;
			int range = 10;
			int y = IC2.getWorldHeight(((Entity) (player)).worldObj);
			for (int i = 0; i < 2 + ((Entity) (player)).worldObj.rand.nextInt(17); i++)
			{
				int x = ((int)((Entity) (player)).posX - range) + 1 + ((Entity) (player)).worldObj.rand.nextInt(range * 2);
				int z = ((int)((Entity) (player)).posZ - range) + 1 + ((Entity) (player)).worldObj.rand.nextInt(range * 2);
				EntityItem e = new EntityItem(((Entity) (player)).worldObj, x, y, z, new ItemStack(Item.hoeWood));
				e.motionY = -5D;
				((Entity) (player)).worldObj.spawnEntityInWorld(e);
			}

		}
	}
}
