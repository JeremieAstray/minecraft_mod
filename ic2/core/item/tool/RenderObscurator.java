// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RenderObscurator.java

package ic2.core.item.tool;

import ic2.core.block.RenderBlock;
import ic2.core.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RenderObscurator
	implements IItemRenderer
{

	public RenderObscurator()
	{
	}

	public boolean handleRenderType(ItemStack item, net.minecraftforge.client.IItemRenderer.ItemRenderType type)
	{
		return type == net.minecraftforge.client.IItemRenderer.ItemRenderType.INVENTORY || type == net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED;
	}

	public boolean shouldUseRenderHelper(net.minecraftforge.client.IItemRenderer.ItemRenderType type, ItemStack item, net.minecraftforge.client.IItemRenderer.ItemRendererHelper helper)
	{
		return false;
	}

	public transient void renderItem(net.minecraftforge.client.IItemRenderer.ItemRenderType type, ItemStack itemStack, Object data[])
	{
		Minecraft.getMinecraft().renderEngine.func_110577_a(TextureMap.field_110576_c);
		Icon overlayIcon = getOverlayIcon(itemStack);
		int overlayColor = getOverlayColor(itemStack);
		if (type == net.minecraftforge.client.IItemRenderer.ItemRenderType.INVENTORY)
		{
			renderIcon(itemStack.getIconIndex(), 0.0F, 0.0F, 16F, 16F, 0.0F);
			if (overlayIcon != null)
			{
				Minecraft.getMinecraft().renderEngine.func_110577_a(TextureMap.field_110575_b);
				GL11.glColor3f((float)(overlayColor >> 16 & 0xff) / 255F, (float)(overlayColor >> 8 & 0xff) / 255F, (float)(overlayColor & 0xff) / 255F);
				renderIcon(overlayIcon, 2.0F, 2.0F, 10F, 10F, 0.0F);
			}
		} else
		if (type == net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED)
		{
			Icon baseIcon = itemStack.getIconIndex();
			ItemRenderer.renderItemIn2D(Tessellator.instance, baseIcon.getMaxU(), baseIcon.getMinV(), baseIcon.getMinU(), baseIcon.getMaxV(), baseIcon.getOriginX(), baseIcon.getOriginY(), 0.0625F);
			if (overlayIcon != null)
			{
				Minecraft.getMinecraft().renderEngine.func_110577_a(TextureMap.field_110575_b);
				GL11.glColor3f((float)(overlayColor >> 16 & 0xff) / 255F, (float)(overlayColor >> 8 & 0xff) / 255F, (float)(overlayColor & 0xff) / 255F);
				renderIconWithNormal(overlayIcon, 0.875F, 0.875F, 0.375F, 0.375F, 0.001F, 0.0F, 0.0F, 1.0F);
				renderIconWithNormal(overlayIcon, 0.875F, 0.875F, 0.375F, 0.375F, -0.0635F, 0.0F, 0.0F, -1F);
			}
		}
	}

	private Icon getOverlayIcon(ItemStack itemStack)
	{
		NBTTagCompound nbtData;
		int referencedBlockId;
		nbtData = StackUtil.getOrCreateNbtData(itemStack);
		referencedBlockId = nbtData.getInteger("referencedBlockId");
		if (referencedBlockId == 0 || Block.blocksList[referencedBlockId] == null)
			break MISSING_BLOCK_LABEL_47;
		return Block.blocksList[referencedBlockId].getIcon(nbtData.getInteger("referencedSide"), nbtData.getInteger("referencedMeta"));
		Exception e;
		e;
		return null;
	}

	private int getOverlayColor(ItemStack itemStack)
	{
		int referencedBlockId;
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
		referencedBlockId = nbtData.getInteger("referencedBlockId");
		if (referencedBlockId == 0 || Block.blocksList[referencedBlockId] == null)
			break MISSING_BLOCK_LABEL_35;
		return Block.blocksList[referencedBlockId].getBlockColor();
		Exception e;
		e;
		return 0xffffff;
	}

	private void renderIcon(Icon icon, float xStart, float yStart, float xEnd, float yEnd, float z)
	{
		if (icon == null)
			icon = RenderBlock.getMissingIcon(TextureMap.field_110576_c);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(xStart, yEnd, z, icon.getMinU(), icon.getMaxV());
		tessellator.addVertexWithUV(xEnd, yEnd, z, icon.getMaxU(), icon.getMaxV());
		tessellator.addVertexWithUV(xEnd, yStart, z, icon.getMaxU(), icon.getMinV());
		tessellator.addVertexWithUV(xStart, yStart, z, icon.getMinU(), icon.getMinV());
		tessellator.draw();
	}

	private void renderIconWithNormal(Icon icon, float xStart, float yStart, float xEnd, float yEnd, float z, float nx, 
			float ny, float nz)
	{
		if (icon == null)
			icon = RenderBlock.getMissingIcon(TextureMap.field_110576_c);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setNormal(nx, ny, nz);
		tessellator.addVertexWithUV(xStart, yEnd, z, icon.getMinU(), icon.getMaxV());
		tessellator.addVertexWithUV(xEnd, yEnd, z, icon.getMaxU(), icon.getMaxV());
		tessellator.addVertexWithUV(xEnd, yStart, z, icon.getMaxU(), icon.getMinV());
		tessellator.addVertexWithUV(xStart, yStart, z, icon.getMinU(), icon.getMinV());
		tessellator.draw();
	}
}
