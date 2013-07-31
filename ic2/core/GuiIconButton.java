// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GuiIconButton.java

package ic2.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiSmallButton;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiIconButton extends GuiSmallButton
{

	private ResourceLocation texture;
	private int textureX;
	private int textureY;
	private ItemStack itemStack;
	private boolean drawQuantity;
	private RenderItem renderItem;

	public GuiIconButton(int id, int x, int y, int w, int h, ResourceLocation texture, int textureX, 
			int textureY)
	{
		super(id, x, y, w, h, "");
		itemStack = null;
		this.texture = texture;
		this.textureX = textureX;
		this.textureY = textureY;
	}

	public GuiIconButton(int id, int x, int y, int w, int h, ItemStack icon, boolean drawQuantity)
	{
		super(id, x, y, w, h, "");
		itemStack = null;
		itemStack = icon;
		this.drawQuantity = drawQuantity;
	}

	public void drawButton(Minecraft minecraft, int i, int j)
	{
		super.drawButton(minecraft, i, j);
		if (itemStack == null)
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			minecraft.func_110434_K().func_110577_a(texture);
			drawTexturedModalRect(super.xPosition + 2, super.yPosition + 1, textureX, textureY, super.width - 4, super.height - 4);
		} else
		{
			if (renderItem == null)
				renderItem = new RenderItem();
			renderItem.renderItemIntoGUI(minecraft.fontRenderer, minecraft.renderEngine, itemStack, super.xPosition + 2, super.yPosition + 1);
			if (drawQuantity)
				renderItem.renderItemOverlayIntoGUI(minecraft.fontRenderer, minecraft.renderEngine, itemStack, super.xPosition + 2, super.xPosition + 1);
		}
	}
}
