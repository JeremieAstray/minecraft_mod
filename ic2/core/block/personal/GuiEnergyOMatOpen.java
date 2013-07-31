// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GuiEnergyOMatOpen.java

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
//			ContainerEnergyOMatOpen, TileEntityEnergyOMat

public class GuiEnergyOMatOpen extends GuiContainer
{

	public ContainerEnergyOMatOpen container;
	public String name;
	public String offerLabel;
	public String inv;
	private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIEnergyOMatOpen.png");

	public GuiEnergyOMatOpen(ContainerEnergyOMatOpen container)
	{
		super(container);
		this.container = container;
		name = StatCollector.translateToLocal("ic2.blockPersonalTraderEnergy");
		offerLabel = StatCollector.translateToLocal("ic2.container.personalTrader.offer");
		inv = StatCollector.translateToLocal("container.inventory");
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		super.fontRenderer.drawString(name, (super.xSize - super.fontRenderer.getStringWidth(name)) / 2, 6, 0x404040);
		super.fontRenderer.drawString(inv, 8, (super.ySize - 96) + 2, 0x404040);
		super.fontRenderer.drawString(offerLabel, 100, 60, 0x404040);
		super.fontRenderer.drawString((new StringBuilder()).append(container.tileEntity.euOffer).append(" EU").toString(), 100, 68, 0x404040);
	}

	protected void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		super.mc.func_110434_K().func_110577_a(background);
		int j = (super.width - super.xSize) / 2;
		int k = (super.height - super.ySize) / 2;
		drawTexturedModalRect(j, k, 0, 0, super.xSize, super.ySize);
	}

	public void initGui()
	{
		super.initGui();
		super.buttonList.add(new GuiSmallButton(0, super.guiLeft + 102, super.guiTop + 16, 32, 10, "-100k"));
		super.buttonList.add(new GuiSmallButton(1, super.guiLeft + 102, super.guiTop + 26, 32, 10, "-10k"));
		super.buttonList.add(new GuiSmallButton(2, super.guiLeft + 102, super.guiTop + 36, 32, 10, "-1k"));
		super.buttonList.add(new GuiSmallButton(3, super.guiLeft + 102, super.guiTop + 46, 32, 10, "-100"));
		super.buttonList.add(new GuiSmallButton(4, super.guiLeft + 134, super.guiTop + 16, 32, 10, "+100k"));
		super.buttonList.add(new GuiSmallButton(5, super.guiLeft + 134, super.guiTop + 26, 32, 10, "+10k"));
		super.buttonList.add(new GuiSmallButton(6, super.guiLeft + 134, super.guiTop + 36, 32, 10, "+1k"));
		super.buttonList.add(new GuiSmallButton(7, super.guiLeft + 134, super.guiTop + 46, 32, 10, "+100"));
	}

	protected void actionPerformed(GuiButton guibutton)
	{
		super.actionPerformed(guibutton);
		IC2.network.initiateClientTileEntityEvent(container.tileEntity, guibutton.id);
	}

}
