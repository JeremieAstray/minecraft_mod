// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GuiTradeOMatClosed.java

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
//			ContainerTradeOMatClosed, TileEntityTradeOMat

public class GuiTradeOMatClosed extends GuiContainer
{

	private final ContainerTradeOMatClosed container;
	private final String name = StatCollector.translateToLocal("ic2.blockPersonalTrader");
	private final String wantLabel = StatCollector.translateToLocal("ic2.container.personalTrader.want");
	private final String offerLabel = StatCollector.translateToLocal("ic2.container.personalTrader.offer");
	private final String stockLabel = StatCollector.translateToLocal("ic2.container.personalTrader.stock");
	private final String inv = StatCollector.translateToLocal("container.inventory");
	private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUITradeOMatClosed.png");

	public GuiTradeOMatClosed(ContainerTradeOMatClosed container)
	{
		super(container);
		this.container = container;
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		super.fontRenderer.drawString(name, (super.xSize - super.fontRenderer.getStringWidth(name)) / 2, 6, 0x404040);
		super.fontRenderer.drawString(inv, 8, (super.ySize - 96) + 2, 0x404040);
		super.fontRenderer.drawString(wantLabel, 12, 23, 0x404040);
		super.fontRenderer.drawString(offerLabel, 12, 42, 0x404040);
		super.fontRenderer.drawString(stockLabel, 12, 60, 0x404040);
		super.fontRenderer.drawString(container.tileEntity.stock >= 0 ? (new StringBuilder()).append("").append(container.tileEntity.stock).toString() : "¡Þ", 50, 60, container.tileEntity.stock == 0 ? 0xff5555 : 0x404040);
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
