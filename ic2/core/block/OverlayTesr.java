// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OverlayTesr.java

package ic2.core.block;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

// Referenced classes of package ic2.core.block:
//			TileEntityBlock, BlockMultiID

public class OverlayTesr extends TileEntitySpecialRenderer
{

	private final RenderBlocks renderBlocks = new RenderBlocks();

	public OverlayTesr()
	{
	}

	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, 
			float partialTick)
	{
		TileEntityBlock te = (TileEntityBlock)tileEntity;
		int mask = te.getTesrMask();
		if (mask == 0)
			return;
		BlockMultiID block = (BlockMultiID)tileEntity.getBlockType();
		block.renderMask = mask;
		GL11.glPushAttrib(64);
		GL11.glPushMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glShadeModel(7425);
		func_110628_a(TextureMap.field_110575_b);
		float zScale = 1.001F;
		GL11.glTranslatef((float)(x + 0.5D), (float)(y + 0.5D), (float)(z + 0.5D));
		GL11.glScalef(zScale, zScale, zScale);
		GL11.glTranslatef((float)(-(x + 0.5D)), (float)(-(y + 0.5D)), (float)(-(z + 0.5D)));
		float f = 1.000001F;
		GL11.glTranslatef(-8F, -8F, -8F);
		GL11.glScalef(f, f, f);
		GL11.glTranslatef(8F, 8F, 8F);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setTranslation(x - (double)tileEntity.xCoord, y - (double)tileEntity.yCoord, z - (double)tileEntity.zCoord);
		renderBlocks.blockAccess = ((TileEntity) (te)).worldObj;
		renderBlocks.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		renderBlocks.renderStandardBlock(block, tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
		tessellator.draw();
		tessellator.setTranslation(0.0D, 0.0D, 0.0D);
		GL11.glPopMatrix();
		GL11.glPopAttrib();
		block.renderMask = 63;
		if (--te.tesrTtl == 0)
			tileEntity.worldObj.markBlockForUpdate(((TileEntity) (te)).xCoord, ((TileEntity) (te)).yCoord, ((TileEntity) (te)).zCoord);
	}
}
