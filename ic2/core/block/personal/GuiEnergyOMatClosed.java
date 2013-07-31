// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GuiEnergyOMatClosed.java

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
//			ContainerEnergyOMatClosed, TileEntityEnergyOMat

public class GuiEnergyOMatClosed extends GuiContainer
{

	public ContainerEnergyOMatClosed container;
	public String name;
	public String wantLabel;
	public String offerLabel;
	public String paidForLabel;
	public String inv;
	private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIEnergyOMatClosed.png");

	public GuiEnergyOMatClosed(ContainerEnergyOMatClosed container)
	{
		super(container);
		this.container = container;
		name = StatCollector.translateToLocal("ic2.blockPersonalTraderEnergy");
		wantLabel = StatCollector.translateToLocal("ic2.container.personalTrader.want");
		offerLabel = StatCollector.translateToLocal("ic2.container.personalTrader.offer");
		paidForLabel = StatCollector.translateToLocal("ic2.container.personalTraderEnergy.paidFor");
		inv = StatCollector.translateToLocal("container.inventory");
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		super.fontRenderer.drawString(name, (super.xSize - super.fontRenderer.getStringWidth(name)) / 2, 6, 0x404040);
		super.fontRenderer.drawString(inv, 8, (super.ySize - 96) + 2, 0x404040);
		super.fontRenderer.drawString(wantLabel, 12, 21, 0x404040);
		super.fontRenderer.drawString(offerLabel, 12, 39, 0x404040);
		super.fontRenderer.drawString((new StringBuilder()).append(container.tileEntity.euOffer).append(" EU").toString(), 50, 39, 0x404040);
		super.fontRenderer.drawString(StatCollector.translateToLocalFormatted("ic2.container.personalTraderEnergy.paidFor", new Object[] {
			Integer.valueOf(container.tileEntity.paidFor)
		}), 12, 57, 0x404040);
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
