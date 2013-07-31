// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityPersonalChestRenderer.java

package ic2.core.block.personal;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

// Referenced classes of package ic2.core.block.personal:
//			ModelPersonalChest, TileEntityPersonalChest

public class TileEntityPersonalChestRenderer extends TileEntitySpecialRenderer
{

	private final ModelPersonalChest model = new ModelPersonalChest();
	private static final ResourceLocation texture = new ResourceLocation("ic2", "textures/models/newsafe.png");

	public TileEntityPersonalChestRenderer()
	{
	}

	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, 
			float partialTick)
	{
		if (!(tile instanceof TileEntityPersonalChest))
			return;
		TileEntityPersonalChest safe = (TileEntityPersonalChest)tile;
		func_110628_a(texture);
		GL11.glPushMatrix();
		GL11.glEnable(32826);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)x, (float)y + 1.0F, (float)z + 1.0F);
		GL11.glScalef(1.0F, -1F, -1F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		int angle;
		switch (safe.getFacing())
		{
		case 2: // '\002'
			angle = 180;
			break;

		case 4: // '\004'
			angle = 90;
			break;

		case 5: // '\005'
			angle = -90;
			break;

		case 3: // '\003'
		default:
			angle = 0;
			break;
		}
		GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		float lidAngle = safe.prevLidAngle + (safe.lidAngle - safe.prevLidAngle) * partialTick;
		lidAngle = 1.0F - lidAngle;
		lidAngle = 1.0F - lidAngle * lidAngle * lidAngle;
		model.door.rotateAngleY = (lidAngle * 3.141593F) / 2.0F;
		model.renderAll();
		GL11.glDisable(32826);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
