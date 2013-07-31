// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GuiElectricBlock.java

package ic2.core.block.wiring;

import ic2.core.GuiIconButton;
import ic2.core.IC2;
import ic2.core.network.NetworkManager;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

// Referenced classes of package ic2.core.block.wiring:
//			ContainerElectricBlock, TileEntityElectricBlock

public class GuiElectricBlock extends GuiContainer
{

	private final ContainerElectricBlock container;
	private final String armorInv = I18n.func_135053_a("ic2.container.armor");
	private final String inv = I18n.func_135053_a("container.inventory");
	private final String level = I18n.func_135053_a("ic2.container.electricBlock.level");
	private final String name;
	private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIElectricBlock.png");

	public GuiElectricBlock(ContainerElectricBlock container)
	{
		super(container);
		super.ySize = 196;
		this.container = container;
		switch (container.tileEntity.tier)
		{
		case 1: // '\001'
			name = I18n.func_135053_a("ic2.blockBatBox");
			break;

		case 2: // '\002'
			name = I18n.func_135053_a("ic2.blockMFE");
			break;

		case 3: // '\003'
			name = I18n.func_135053_a("ic2.blockMFSU");
			break;

		default:
			name = null;
			break;
		}
	}

	public void initGui()
	{
		super.initGui();
		super.buttonList.add(new GuiIconButton(0, (super.width - super.xSize) / 2 + 152, (super.height - super.ySize) / 2 + 4, 20, 20, new ItemStack(Item.redstone), true));
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		super.fontRenderer.drawString(name, (super.xSize - super.fontRenderer.getStringWidth(name)) / 2, 6, 0x404040);
		super.fontRenderer.drawString(armorInv, 8, (super.ySize - 126) + 3, 0x404040);
		super.fontRenderer.drawString(inv, 8, (super.ySize - 96) + 3, 0x404040);
		super.fontRenderer.drawString(level, 79, 25, 0x404040);
		int e = Math.min(container.tileEntity.energy, container.tileEntity.maxStorage);
		super.fontRenderer.drawString((new StringBuilder()).append(" ").append(e).toString(), 110, 35, 0x404040);
		super.fontRenderer.drawString((new StringBuilder()).append("/").append(container.tileEntity.maxStorage).toString(), 110, 45, 0x404040);
		String output = I18n.func_135052_a("ic2.container.electricBlock.output", new Object[] {
			Integer.valueOf(container.tileEntity.output)
		});
		super.fontRenderer.drawString(output, 85, 60, 0x404040);
	}

	protected void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		super.mc.func_110434_K().func_110577_a(background);
		int j = (super.width - super.xSize) / 2;
		int k = (super.height - super.ySize) / 2;
		drawTexturedModalRect(j, k, 0, 0, super.xSize, super.ySize);
		if (container.tileEntity.energy > 0)
		{
			int i1 = (int)(24F * container.tileEntity.getChargeLevel());
			drawTexturedModalRect(j + 79, k + 34, 176, 14, i1 + 1, 16);
		}
	}

	protected void actionPerformed(GuiButton guibutton)
	{
		super.actionPerformed(guibutton);
		if (guibutton.id == 0)
			IC2.network.initiateClientTileEntityEvent(container.tileEntity, 0);
	}

}
