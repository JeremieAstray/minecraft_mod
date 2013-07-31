// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RenderExplosiveBlock.java

package ic2.core.block;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

// Referenced classes of package ic2.core.block:
//			EntityIC2Explosive

public class RenderExplosiveBlock extends Render
{

	public RenderBlocks blockRenderer;

	public RenderExplosiveBlock()
	{
		blockRenderer = new RenderBlocks();
		super.shadowSize = 0.5F;
	}

	public void render(EntityIC2Explosive entitytntprimed, double x, double y, double z, 
			float f, float f1)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		if (((float)entitytntprimed.fuse - f1) + 1.0F < 10F)
		{
			float scale = 1.0F - (((float)entitytntprimed.fuse - f1) + 1.0F) / 10F;
			if (scale < 0.0F)
				scale = 0.0F;
			if (scale > 1.0F)
				scale = 1.0F;
			scale *= scale;
			scale *= scale;
			scale = 1.0F + scale * 0.3F;
			GL11.glScalef(scale, scale, scale);
		}
		float f3 = (1.0F - (((float)entitytntprimed.fuse - f1) + 1.0F) / 100F) * 0.8F;
		func_110776_a(TextureMap.field_110575_b);
		blockRenderer.renderBlockAsItem(entitytntprimed.renderBlock, 0, entitytntprimed.getBrightness(f1));
		if ((entitytntprimed.fuse / 5) % 2 == 0)
		{
			GL11.glDisable(3553);
			GL11.glDisable(2896);
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 772);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, f3);
			blockRenderer.renderBlockAsItem(entitytntprimed.renderBlock, 0, 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(3042);
			GL11.glEnable(2896);
			GL11.glEnable(3553);
		}
		GL11.glPopMatrix();
	}

	public void doRender(Entity entity, double x, double y, double z, 
			float f, float f1)
	{
		render((EntityIC2Explosive)entity, x, y, z, f, f1);
	}

	protected ResourceLocation func_110775_a(Entity entity)
	{
		return null;
	}
}
