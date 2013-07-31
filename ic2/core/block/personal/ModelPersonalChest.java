// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ModelPersonalChest.java

package ic2.core.block.personal;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelPersonalChest extends ModelBase
{

	ModelRenderer wallR;
	ModelRenderer wallL;
	ModelRenderer wallB;
	ModelRenderer wallU;
	ModelRenderer wallD;
	public ModelRenderer door;

	public ModelPersonalChest()
	{
		super.textureWidth = 64;
		super.textureHeight = 64;
		wallR = new ModelRenderer(this, 0, 0);
		wallR.addBox(0.0F, 0.0F, 0.0F, 14, 16, 1);
		wallR.setRotationPoint(1.0F, 0.0F, 15F);
		wallR.setTextureSize(64, 64);
		wallR.mirror = true;
		setRotation(wallR, 0.0F, 1.570796F, 0.0F);
		wallL = new ModelRenderer(this, 0, 0);
		wallL.addBox(0.0F, 0.0F, 0.0F, 14, 16, 1);
		wallL.setRotationPoint(15F, 0.0F, 1.0F);
		wallL.setTextureSize(64, 64);
		wallL.mirror = true;
		setRotation(wallL, 0.0F, -1.570796F, 0.0F);
		wallB = new ModelRenderer(this, 1, 1);
		wallB.addBox(1.0F, 1.0F, 0.0F, 12, 14, 1);
		wallB.setRotationPoint(15F, 0.0F, 15F);
		wallB.setTextureSize(64, 64);
		wallB.mirror = true;
		setRotation(wallB, 0.0F, 3.141593F, 0.0F);
		wallU = new ModelRenderer(this, 1, 17);
		wallU.addBox(1.0F, 0.0F, 0.0F, 12, 14, 1);
		wallU.setRotationPoint(1.0F, 0.0F, 15F);
		wallU.setTextureSize(64, 64);
		wallU.mirror = true;
		setRotation(wallU, -1.570796F, 0.0F, 0.0F);
		wallD = new ModelRenderer(this, 1, 17);
		wallD.addBox(1.0F, 0.0F, 0.0F, 12, 14, 1);
		wallD.setRotationPoint(15F, 15F, 1.0F);
		wallD.setTextureSize(64, 64);
		wallD.mirror = true;
		setRotation(wallD, -1.570796F, 3.141593F, 0.0F);
		door = new ModelRenderer(this, 30, 0);
		door.addBox(0.0F, 0.0F, 0.0F, 12, 14, 1);
		door.setRotationPoint(2.0F, 1.0F, 2.0F);
		door.setTextureSize(64, 64);
		door.mirror = true;
	}

	public void renderAll()
	{
		wallR.render(0.0625F);
		wallL.render(0.0625F);
		wallB.render(0.0625F);
		wallU.render(0.0625F);
		wallD.render(0.0625F);
		door.render(0.0625F);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
