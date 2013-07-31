// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GuiTradeOMatOpen.java

package ic2.core.block.personal;

import ic2.core.IC2;
import ic2.core.network.NetworkManager;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

// Referenced classes of package ic2.core.block.personal:
//			ContainerTradeOMatOpen, TileEntityTradeOMat

public class GuiTradeOMatOpen extends GuiContainer
{

	private final ContainerTradeOMatOpen container;
	private final String name = StatCollector.translateToLocal("ic2.blockPersonalTrader");
	private final String wantLabel = StatCollector.translateToLocal("ic2.container.personalTrader.want");
	private final String offerLabel = StatCollector.translateToLocal("ic2.container.personalTrader.offer");
	private final String totalTradesLabel0 = StatCollector.translateToLocal("ic2.container.personalTrader.totalTrades0");
	private final String totalTradesLabel1 = StatCollector.translateToLocal("ic2.container.personalTrader.totalTrades1");
	private final String stockLabel = StatCollector.translateToLocal("ic2.container.personalTrader.stock");
	private final String inv = StatCollector.translateToLocal("container.inventory");
	private final boolean isAdmin;
	private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUITradeOMatOpen.png");

	public GuiTradeOMatOpen(ContainerTradeOMatOpen container, boolean isAdmin)
	{
		super(container);
		this.container = container;
		this.isAdmin = isAdmin;
	}

	public void initGui()
	{
		super.initGui();
		if (isAdmin)
			super.buttonList.add(new GuiSmallButton(0, (super.width - super.xSize) / 2 + 152, (super.height - super.ySize) / 2 + 4, 20, 20, "¡Þ"));
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		super.fontRenderer.drawString(name, (super.xSize - super.fontRenderer.getStringWidth(name)) / 2, 6, 0x404040);
		super.fontRenderer.drawString(inv, 8, (super.ySize - 96) + 2, 0x404040);
		super.fontRenderer.drawString(wantLabel, 12, 23, 0x404040);
		super.fontRenderer.drawString(offerLabel, 12, 57, 0x404040);
		super.fontRenderer.drawString(totalTradesLabel0, 108, 28, 0x404040);
		super.fontRenderer.drawString(totalTradesLabel1, 108, 36, 0x404040);
		super.fontRenderer.drawString((new StringBuilder()).append("").append(container.tileEntity.totalTradeCount).toString(), 112, 44, 0x404040);
		super.fontRenderer.drawString((new StringBuilder()).append(stockLabel).append(" ").append(container.tileEntity.stock >= 0 ? (new StringBuilder()).append("").append(container.tileEntity.stock).toString() : "¡Þ").toString(), 108, 60, 0x404040);
	}

	protected void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		super.mc.func_110434_K().func_110577_a(background);
		int j = (super.width - super.xSize) / 2;
		int k = (super.height - super.ySize) / 2;
		drawTexturedModalRect(j, k, 0, 0, super.xSize, super.ySize);
	}

	protected void actionPerformed(GuiButton guibutton)
	{
		super.actionPerformed(guibutton);
		if (guibutton.id == 0)
			IC2.network.initiateClientTileEntityEvent(container.tileEntity, 0);
	}

}
