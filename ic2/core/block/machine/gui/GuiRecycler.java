// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GuiRecycler.java

package ic2.core.block.machine.gui;

import ic2.core.block.machine.ContainerStandardMachine;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GuiRecycler extends GuiContainer
{

	public ContainerStandardMachine container;
	public String name;
	public String inv;
	private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIRecycler.png");

	public GuiRecycler(ContainerStandardMachine container)
	{
		super(container);
		this.container = container;
		name = StatCollector.translateToLocal("ic2.blockRecycler");
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
		int chargeLevel = (int)(14F * container.tileEntity.getChargeLevel());
		int progress = (int)(24F * container.tileEntity.getProgress());
		if (chargeLevel > 0)
			drawTexturedModalRect(j + 56, (k + 36 + 14) - chargeLevel, 176, 14 - chargeLevel, 14, chargeLevel);
		if (progress > 0)
			drawTexturedModalRect(j + 79, k + 34, 176, 14, progress + 1, 16);
	}

}
