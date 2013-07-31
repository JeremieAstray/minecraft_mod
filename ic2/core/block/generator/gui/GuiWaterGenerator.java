// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GuiWaterGenerator.java

package ic2.core.block.generator.gui;

import ic2.core.block.generator.container.ContainerWaterGenerator;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GuiWaterGenerator extends GuiContainer
{

	public ContainerWaterGenerator container;
	public String name;
	public String inv;
	private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIWaterGenerator.png");

	public GuiWaterGenerator(ContainerWaterGenerator container)
	{
		super(container);
		this.container = container;
		name = StatCollector.translateToLocal("ic2.blockWaterGenerator");
		inv = StatCollector.translateToLocal("container.inventory");
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		super.fontRenderer.drawString(name, (super.xSize - super.fontRenderer.getStringWidth(name)) / 2, 6, 0x404040);
		super.fontRenderer.drawString(inv, 8, (super.ySize - 96) + 2, 0x404040);
	}

	protected void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		super.mc.func_110434_K().func_110577_a(background);
		int j = (super.width - super.xSize) / 2;
		int k = (super.height - super.ySize) / 2;
		drawTexturedModalRect(j, k, 0, 0, super.xSize, super.ySize);
		if (container.tileEntity.fuel > 0)
		{
			int l = container.tileEntity.gaugeFuelScaled(14);
			drawTexturedModalRect(j + 80, (k + 36 + 14) - l, 176, 14 - l, 14, l);
		}
	}

}
