// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RenderBlockCrop.java

package ic2.core.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

// Referenced classes of package ic2.core.block:
//			RenderBlock

public class RenderBlockCrop extends RenderBlock
{

	public RenderBlockCrop()
	{
	}

	public static void renderBlockCropsImpl(Icon icon, int x, int y, int z)
	{
		Tessellator tessellator = Tessellator.instance;
		double yBase = (double)y - 0.0625D;
		double uStart = icon.getInterpolatedU(0.0D);
		double uEnd = icon.getInterpolatedU(16D);
		double vStart = icon.getInterpolatedV(0.0D);
		double vEnd = icon.getInterpolatedV(16D);
		double x1 = ((double)x + 0.5D) - 0.25D;
		double x2 = (double)x + 0.5D + 0.25D;
		double z1 = ((double)z + 0.5D) - 0.5D;
		double z2 = (double)z + 0.5D + 0.5D;
		tessellator.addVertexWithUV(x1, yBase + 1.0D, z1, uStart, vStart);
		tessellator.addVertexWithUV(x1, yBase + 0.0D, z1, uStart, vEnd);
		tessellator.addVertexWithUV(x1, yBase + 0.0D, z2, uEnd, vEnd);
		tessellator.addVertexWithUV(x1, yBase + 1.0D, z2, uEnd, vStart);
		tessellator.addVertexWithUV(x1, yBase + 1.0D, z2, uStart, vStart);
		tessellator.addVertexWithUV(x1, yBase + 0.0D, z2, uStart, vEnd);
		tessellator.addVertexWithUV(x1, yBase + 0.0D, z1, uEnd, vEnd);
		tessellator.addVertexWithUV(x1, yBase + 1.0D, z1, uEnd, vStart);
		tessellator.addVertexWithUV(x2, yBase + 1.0D, z2, uStart, vStart);
		tessellator.addVertexWithUV(x2, yBase + 0.0D, z2, uStart, vEnd);
		tessellator.addVertexWithUV(x2, yBase + 0.0D, z1, uEnd, vEnd);
		tessellator.addVertexWithUV(x2, yBase + 1.0D, z1, uEnd, vStart);
		tessellator.addVertexWithUV(x2, yBase + 1.0D, z1, uStart, vStart);
		tessellator.addVertexWithUV(x2, yBase + 0.0D, z1, uStart, vEnd);
		tessellator.addVertexWithUV(x2, yBase + 0.0D, z2, uEnd, vEnd);
		tessellator.addVertexWithUV(x2, yBase + 1.0D, z2, uEnd, vStart);
		x1 = ((double)x + 0.5D) - 0.5D;
		x2 = (double)x + 0.5D + 0.5D;
		z1 = ((double)z + 0.5D) - 0.25D;
		z2 = (double)z + 0.5D + 0.25D;
		tessellator.addVertexWithUV(x1, yBase + 1.0D, z1, uStart, vStart);
		tessellator.addVertexWithUV(x1, yBase + 0.0D, z1, uStart, vEnd);
		tessellator.addVertexWithUV(x2, yBase + 0.0D, z1, uEnd, vEnd);
		tessellator.addVertexWithUV(x2, yBase + 1.0D, z1, uEnd, vStart);
		tessellator.addVertexWithUV(x2, yBase + 1.0D, z1, uStart, vStart);
		tessellator.addVertexWithUV(x2, yBase + 0.0D, z1, uStart, vEnd);
		tessellator.addVertexWithUV(x1, yBase + 0.0D, z1, uEnd, vEnd);
		tessellator.addVertexWithUV(x1, yBase + 1.0D, z1, uEnd, vStart);
		tessellator.addVertexWithUV(x2, yBase + 1.0D, z2, uStart, vStart);
		tessellator.addVertexWithUV(x2, yBase + 0.0D, z2, uStart, vEnd);
		tessellator.addVertexWithUV(x1, yBase + 0.0D, z2, uEnd, vEnd);
		tessellator.addVertexWithUV(x1, yBase + 1.0D, z2, uEnd, vStart);
		tessellator.addVertexWithUV(x1, yBase + 1.0D, z2, uStart, vStart);
		tessellator.addVertexWithUV(x1, yBase + 0.0D, z2, uStart, vEnd);
		tessellator.addVertexWithUV(x2, yBase + 0.0D, z2, uEnd, vEnd);
		tessellator.addVertexWithUV(x2, yBase + 1.0D, z2, uEnd, vStart);
	}

	public void renderInventoryBlock(Block block1, int i, int j, RenderBlocks renderblocks)
	{
	}

	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		super.renderWorldBlock(blockAccess, x, y, z, block, modelId, renderer);
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, x, y, z));
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderBlockCropsImpl(block.getBlockTexture(blockAccess, x, y, z, 0), x, y, z);
		return true;
	}

	public boolean shouldRender3DInInventory()
	{
		return false;
	}
}
