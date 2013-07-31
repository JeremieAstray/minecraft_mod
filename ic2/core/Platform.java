// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Platform.java

package ic2.core;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import ic2.core.network.NetworkManager;
import java.io.*;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.network.NetServerHandler;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.IBlockAccess;

// Referenced classes of package ic2.core:
//			IC2, IHasGui

public class Platform
{

	public Platform()
	{
	}

	public boolean isSimulating()
	{
		return !FMLCommonHandler.instance().getEffectiveSide().isClient();
	}

	public boolean isRendering()
	{
		return !isSimulating();
	}

	public void displayError(String error)
	{
		throw new RuntimeException((new StringBuilder()).append("IndustrialCraft 2 Error\n\n=== IndustrialCraft 2 Error ===\n\n").append(error).append("\n\n===============================\n").toString().replace("\n", System.getProperty("line.separator")));
	}

	public void displayError(Exception e, String error)
	{
		displayError((new StringBuilder()).append("An unexpected Exception occured.\n\n").append(getStackTrace(e)).append("\n").append(error).toString());
	}

	public String getStackTrace(Exception e)
	{
		StringWriter writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		e.printStackTrace(printWriter);
		return writer.toString();
	}

	public EntityPlayer getPlayerInstance()
	{
		return null;
	}

	public transient void messagePlayer(EntityPlayer player, String message, Object args[])
	{
		if (player instanceof EntityPlayerMP)
		{
			ChatMessageComponent msg;
			if (args.length > 0)
				msg = ChatMessageComponent.func_111082_b(message, args);
			else
				msg = ChatMessageComponent.func_111077_e(message);
			((EntityPlayerMP)player).sendChatToPlayer(msg);
		}
	}

	public boolean launchGui(EntityPlayer player, IHasGui inventory)
	{
		if (player instanceof EntityPlayerMP)
		{
			EntityPlayerMP entityPlayerMp = (EntityPlayerMP)player;
			int windowId = entityPlayerMp.currentWindowId % 100 + 1;
			entityPlayerMp.currentWindowId = windowId;
			entityPlayerMp.closeContainer();
			IC2.network.initiateGuiDisplay(entityPlayerMp, inventory, windowId);
			player.openContainer = inventory.getGuiContainer(player);
			player.openContainer.windowId = windowId;
			player.openContainer.addCraftingToCrafters(entityPlayerMp);
			return true;
		} else
		{
			return false;
		}
	}

	public boolean launchGuiClient(EntityPlayer player, IHasGui inventory, boolean isAdmin)
	{
		return false;
	}

	public void profilerStartSection(String s)
	{
	}

	public void profilerEndSection()
	{
	}

	public void profilerEndStartSection(String s)
	{
	}

	public File getMinecraftDir()
	{
		return new File(".");
	}

	public void playSoundSp(String s, float f1, float f2)
	{
	}

	public void resetPlayerInAirTime(EntityPlayer player)
	{
		if (!(player instanceof EntityPlayerMP))
		{
			return;
		} else
		{
			((EntityPlayerMP)player).playerNetServerHandler.ticksForFloatKick = 0;
			return;
		}
	}

	public int getBlockTexture(Block block, IBlockAccess world, int x, int i, int j, int k)
	{
		return 0;
	}

	public int addArmor(String name)
	{
		return 0;
	}

	public void removePotion(EntityLivingBase entity, int potion)
	{
		entity.removePotionEffect(potion);
	}

	public int getRenderId(String name)
	{
		return -1;
	}

	public void onPostInit()
	{
	}
}
