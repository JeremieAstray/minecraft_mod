// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RenderBlock.java

package ic2.core.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

// Referenced classes of package ic2.core.block:
//			BlockMultiID

public abstract class RenderBlock
	implements ISimpleBlockRenderingHandler
{

	private final int renderId = RenderingRegistry.getNextAvailableRenderId();

	public RenderBlock()
	{
	}

	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderblocks)
	{
		((BlockMultiID)block).onRender(blockAccess, x, y, z);
		return false;
	}

	public abstract void renderInventoryBlock(Block block, int i, int j, RenderBlocks renderblocks);

	public boolean shouldRender3DInInventory()
	{
		return true;
	}

	public int getRenderId()
	{
		return renderId;
	}

	public static Icon getMissingIcon(ResourceLocation textureSheet)
	{
		return ((TextureMap)Minecraft.getMinecraft().func_110434_K().func_110581_b(textureSheet)).func_110572_b("missingno");
	}
}
