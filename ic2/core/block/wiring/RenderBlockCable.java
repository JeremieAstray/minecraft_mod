// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RenderBlockCable.java

package ic2.core.block.wiring;

import ic2.core.block.RenderBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

// Referenced classes of package ic2.core.block.wiring:
//			TileEntityCable

public class RenderBlockCable extends RenderBlock
{

	public RenderBlockCable()
	{
	}

	public void renderInventoryBlock(Block block1, int i, int j, RenderBlocks renderblocks)
	{
	}

	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderblocks)
	{
		super.renderWorldBlock(blockAccess, x, y, z, block, modelId, renderblocks);
		TileEntity te = blockAccess.getBlockTileEntity(x, y, z);
		if (!(te instanceof TileEntityCable))
			return true;
		TileEntityCable cable = (TileEntityCable)te;
		if (cable.foamed > 0)
		{
			renderblocks.renderStandardBlock(block, x, y, z);
		} else
		{
			float th = cable.getCableThickness();
			float sp = (1.0F - th) / 2.0F;
			int connectivity = cable.connectivity;
			int renderSide = cable.renderSide;
			Icon textures[] = new Icon[6];
			for (int side = 0; side < 6; side++)
			{
				Icon icon = block.getBlockTexture(blockAccess, x, y, z, side);
				if (icon != null)
					textures[side] = icon;
				else
					textures[side] = getMissingIcon(TextureMap.field_110575_b);
			}

			Tessellator tessellator = Tessellator.instance;
			double xD = x;
			double yD = y;
			double zD = z;
			tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, x, y, z));
			if (connectivity == 0)
			{
				block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
				renderblocks.setRenderBoundsFromBlock(block);
				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderblocks.renderFaceYNeg(block, xD, yD, zD, textures[0]);
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderblocks.renderFaceYPos(block, xD, yD, zD, textures[1]);
				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderblocks.renderFaceZNeg(block, xD, yD, zD, textures[2]);
				renderblocks.renderFaceZPos(block, xD, y, zD, textures[3]);
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderblocks.renderFaceXNeg(block, xD, yD, zD, textures[4]);
				renderblocks.renderFaceXPos(block, xD, yD, zD, textures[5]);
			} else
			if (connectivity == 3)
			{
				block.setBlockBounds(0.0F, sp, sp, 1.0F, sp + th, sp + th);
				renderblocks.setRenderBoundsFromBlock(block);
				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderblocks.renderFaceYNeg(block, xD, yD, zD, textures[0]);
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderblocks.renderFaceYPos(block, xD, yD, zD, textures[1]);
				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderblocks.renderFaceZNeg(block, xD, yD, zD, textures[2]);
				renderblocks.renderFaceZPos(block, xD, y, zD, textures[3]);
				if ((renderSide & 1) != 0)
				{
					tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
					renderblocks.renderFaceXNeg(block, xD, yD, zD, textures[4]);
				}
				if ((renderSide & 2) != 0)
				{
					tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
					renderblocks.renderFaceXPos(block, xD, yD, zD, textures[5]);
				}
			} else
			if (connectivity == 12)
			{
				block.setBlockBounds(sp, 0.0F, sp, sp + th, 1.0F, sp + th);
				renderblocks.setRenderBoundsFromBlock(block);
				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderblocks.renderFaceZNeg(block, xD, yD, zD, textures[2]);
				renderblocks.renderFaceZPos(block, xD, y, zD, textures[3]);
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderblocks.renderFaceXNeg(block, xD, yD, zD, textures[4]);
				renderblocks.renderFaceXPos(block, xD, yD, zD, textures[5]);
				if ((renderSide & 4) != 0)
				{
					tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
					renderblocks.renderFaceYNeg(block, xD, yD, zD, textures[0]);
				}
				if ((renderSide & 8) != 0)
				{
					tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
					renderblocks.renderFaceYPos(block, xD, yD, zD, textures[1]);
				}
			} else
			if (connectivity == 48)
			{
				block.setBlockBounds(sp, sp, 0.0F, sp + th, sp + th, 1.0F);
				renderblocks.setRenderBoundsFromBlock(block);
				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderblocks.renderFaceYNeg(block, xD, yD, zD, textures[0]);
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderblocks.renderFaceYPos(block, xD, yD, zD, textures[1]);
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderblocks.renderFaceXNeg(block, xD, yD, zD, textures[4]);
				renderblocks.renderFaceXPos(block, xD, yD, zD, textures[5]);
				if ((renderSide & 0x10) != 0)
				{
					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					renderblocks.renderFaceZNeg(block, xD, y, zD, textures[2]);
				}
				if ((renderSide & 0x20) != 0)
				{
					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					renderblocks.renderFaceZPos(block, xD, yD, zD, textures[3]);
				}
			} else
			{
				if ((connectivity & 1) == 0)
				{
					block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
					renderblocks.setRenderBoundsFromBlock(block);
					tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
					renderblocks.renderFaceXNeg(block, xD, yD, zD, textures[4]);
				} else
				{
					block.setBlockBounds(0.0F, sp, sp, sp, sp + th, sp + th);
					renderblocks.setRenderBoundsFromBlock(block);
					tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
					renderblocks.renderFaceYNeg(block, xD, yD, zD, textures[0]);
					tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
					renderblocks.renderFaceYPos(block, xD, yD, zD, textures[1]);
					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					renderblocks.renderFaceZNeg(block, xD, yD, zD, textures[2]);
					renderblocks.renderFaceZPos(block, xD, y, zD, textures[3]);
					if ((renderSide & 1) != 0)
					{
						tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
						renderblocks.renderFaceXNeg(block, xD, yD, zD, textures[4]);
					}
				}
				if ((connectivity & 2) == 0)
				{
					block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
					renderblocks.setRenderBoundsFromBlock(block);
					tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
					renderblocks.renderFaceXPos(block, xD, yD, zD, textures[5]);
				} else
				{
					block.setBlockBounds(sp + th, sp, sp, 1.0F, sp + th, sp + th);
					renderblocks.setRenderBoundsFromBlock(block);
					tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
					renderblocks.renderFaceYNeg(block, xD, yD, zD, textures[0]);
					tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
					renderblocks.renderFaceYPos(block, xD, yD, zD, textures[1]);
					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					renderblocks.renderFaceZNeg(block, xD, yD, zD, textures[2]);
					renderblocks.renderFaceZPos(block, xD, y, zD, textures[3]);
					if ((renderSide & 2) != 0)
					{
						tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
						renderblocks.renderFaceXPos(block, xD, yD, zD, textures[5]);
					}
				}
				if ((connectivity & 4) == 0)
				{
					block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
					renderblocks.setRenderBoundsFromBlock(block);
					tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
					renderblocks.renderFaceYNeg(block, xD, yD, zD, textures[0]);
				} else
				{
					block.setBlockBounds(sp, 0.0F, sp, sp + th, sp, sp + th);
					renderblocks.setRenderBoundsFromBlock(block);
					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					renderblocks.renderFaceZNeg(block, xD, yD, zD, textures[2]);
					renderblocks.renderFaceZPos(block, xD, y, zD, textures[3]);
					tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
					renderblocks.renderFaceXNeg(block, xD, yD, zD, textures[4]);
					renderblocks.renderFaceXPos(block, xD, yD, zD, textures[5]);
					if ((renderSide & 4) != 0)
					{
						tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
						renderblocks.renderFaceYNeg(block, xD, yD, zD, textures[0]);
					}
				}
				if ((connectivity & 8) == 0)
				{
					block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
					renderblocks.setRenderBoundsFromBlock(block);
					tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
					renderblocks.renderFaceYPos(block, xD, yD, zD, textures[1]);
				} else
				{
					block.setBlockBounds(sp, sp + th, sp, sp + th, 1.0F, sp + th);
					renderblocks.setRenderBoundsFromBlock(block);
					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					renderblocks.renderFaceZNeg(block, xD, yD, zD, textures[2]);
					renderblocks.renderFaceZPos(block, xD, y, zD, textures[3]);
					tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
					renderblocks.renderFaceXNeg(block, xD, yD, zD, textures[4]);
					renderblocks.renderFaceXPos(block, xD, yD, zD, textures[5]);
					if ((renderSide & 8) != 0)
					{
						tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
						renderblocks.renderFaceYPos(block, xD, yD, zD, textures[1]);
					}
				}
				if ((connectivity & 0x10) == 0)
				{
					block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
					renderblocks.setRenderBoundsFromBlock(block);
					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					renderblocks.renderFaceZNeg(block, xD, y, zD, textures[2]);
				} else
				{
					block.setBlockBounds(sp, sp, 0.0F, sp + th, sp + th, sp);
					renderblocks.setRenderBoundsFromBlock(block);
					tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
					renderblocks.renderFaceYNeg(block, xD, yD, zD, textures[0]);
					tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
					renderblocks.renderFaceYPos(block, xD, yD, zD, textures[1]);
					tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
					renderblocks.renderFaceXNeg(block, xD, yD, zD, textures[4]);
					renderblocks.renderFaceXPos(block, xD, yD, zD, textures[5]);
					if ((renderSide & 0x10) != 0)
					{
						tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
						renderblocks.renderFaceZNeg(block, xD, y, zD, textures[2]);
					}
				}
				if ((connectivity & 0x20) == 0)
				{
					block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
					renderblocks.setRenderBoundsFromBlock(block);
					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					renderblocks.renderFaceZPos(block, xD, yD, zD, textures[3]);
				} else
				{
					block.setBlockBounds(sp, sp, sp + th, sp + th, sp + th, 1.0F);
					renderblocks.setRenderBoundsFromBlock(block);
					tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
					renderblocks.renderFaceYNeg(block, xD, yD, zD, textures[0]);
					tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
					renderblocks.renderFaceYPos(block, xD, yD, zD, textures[1]);
					tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
					renderblocks.renderFaceXNeg(block, xD, yD, zD, textures[4]);
					renderblocks.renderFaceXPos(block, xD, yD, zD, textures[5]);
					if ((renderSide & 0x20) != 0)
					{
						tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
						renderblocks.renderFaceZPos(block, xD, yD, zD, textures[3]);
					}
				}
			}
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderblocks.setRenderBoundsFromBlock(block);
		}
		return true;
	}

	public boolean shouldRender3DInInventory()
	{
		return false;
	}
}
