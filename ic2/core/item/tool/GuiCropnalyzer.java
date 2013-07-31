// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GuiCropnalyzer.java

package ic2.core.item.tool;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

// Referenced classes of package ic2.core.item.tool:
//			ContainerCropnalyzer, HandHeldCropnalyzer

public class GuiCropnalyzer extends GuiContainer
{

	public ContainerCropnalyzer container;
	public String name;
	private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUICropnalyzer.png");

	public GuiCropnalyzer(ContainerCropnalyzer container)
	{
		super(container);
		this.container = container;
		name = StatCollector.translateToLocal("ic2.itemCropnalyzer");
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		super.fontRenderer.drawString(name, 74, 11, 0);
		int level = container.cropnalyzer.getScannedLevel();
		if (level <= -1)
			return;
		if (level == 0)
		{
			super.fontRenderer.drawString("UNKNOWN", 8, 37, 0xffffff);
			return;
		}
		super.fontRenderer.drawString(container.cropnalyzer.getSeedName(), 8, 37, 0xffffff);
		if (level >= 2)
		{
			super.fontRenderer.drawString((new StringBuilder()).append("Tier: ").append(container.cropnalyzer.getSeedTier()).toString(), 8, 50, 0xffffff);
			super.fontRenderer.drawString("Discovered by:", 8, 73, 0xffffff);
			super.fontRenderer.drawString(container.cropnalyzer.getSeedDiscovered(), 8, 86, 0xffffff);
		}
		if (level >= 3)
		{
			super.fontRenderer.drawString(container.cropnalyzer.getSeedDesc(0), 8, 109, 0xffffff);
			super.fontRenderer.drawString(container.cropnalyzer.getSeedDesc(1), 8, 122, 0xffffff);
		}
		if (level >= 4)
		{
			super.fontRenderer.drawString("Growth:", 118, 37, 0xadff2f);
			super.fontRenderer.drawString((new StringBuilder()).append("").append(container.cropnalyzer.getSeedGrowth()).toString(), 118, 50, 0xadff2f);
			super.fontRenderer.drawString("Gain:", 118, 73, 0xeec900);
			super.fontRenderer.drawString((new StringBuilder()).append("").append(container.cropnalyzer.getSeedGain()).toString(), 118, 86, 0xeec900);
			super.fontRenderer.drawString("Resis.:", 118, 109, 52945);
			super.fontRenderer.drawString((new StringBuilder()).append("").append(container.cropnalyzer.getSeedResistence()).toString(), 118, 122, 52945);
		}
	}

	protected void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		super.mc.func_110434_K().func_110577_a(background);
		int j = (super.width - super.xSize) / 2;
		int k = (super.height - super.ySize) / 2;
		drawTexturedModalRect(j, k, 0, 0, super.xSize, super.ySize);
	}

}
