// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GuiMatter.java

package ic2.core.block.machine.gui;

import ic2.core.block.machine.ContainerMatter;
import ic2.core.block.machine.tileentity.TileEntityMatter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GuiMatter extends GuiContainer
{

	public ContainerMatter container;
	public String name;
	public String progressLabel;
	public String amplifierLabel;
	public String inv;
	private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIMatter.png");

	public GuiMatter(ContainerMatter container)
	{
		super(container);
		this.container = container;
		name = StatCollector.translateToLocal("ic2.blockMatter");
		progressLabel = StatCollector.translateToLocal("ic2.container.matter.progress");
		amplifierLabel = StatCollector.translateToLocal("ic2.container.matter.amplifier");
		inv = StatCollector.translateToLocal("container.inventory");
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		super.fontRenderer.drawString(name, (super.xSize - super.fontRenderer.getStringWidth(name)) / 2, 6, 0x404040);
		super.fontRenderer.drawString(inv, 8, (super.ySize - 96) + 2, 0x404040);
		super.fontRenderer.drawString(progressLabel, 16, 20, 0x404040);
		super.fontRenderer.drawString(container.tileEntity.getProgressAsString(), 16, 28, 0x404040);
		if (container.tileEntity.scrap > 0)
		{
			super.fontRenderer.drawString(amplifierLabel, 16, 44, 0x404040);
			super.fontRenderer.drawString((new StringBuilder()).append("").append(container.tileEntity.scrap).toString(), 16, 56, 0x404040);
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
