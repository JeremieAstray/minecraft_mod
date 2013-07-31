// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GuiPersonalChest.java

package ic2.core.block.personal;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

// Referenced classes of package ic2.core.block.personal:
//			ContainerPersonalChest

public class GuiPersonalChest extends GuiContainer
{

	public ContainerPersonalChest container;
	public String name;
	public String inv;
	private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIPersonalChest.png");

	public GuiPersonalChest(ContainerPersonalChest container)
	{
		super(container);
		this.container = container;
		name = StatCollector.translateToLocal("ic2.blockPersonalChest");
		inv = StatCollector.translateToLocal("container.inventory");
		super.ySize = 222;
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		super.fontRenderer.drawString(name, 8, 6, 0x404040);
		super.fontRenderer.drawString(inv, 8, (super.ySize - 96) + 2, 0x404040);
	}

	protected void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		super.mc.func_110434_K().func_110577_a(background);
		int xOffset = (super.width - super.xSize) / 2;
		int yOffset = (super.height - super.ySize) / 2;
		drawTexturedModalRect(xOffset, yOffset, 0, 0, super.xSize, super.ySize);
	}

}
