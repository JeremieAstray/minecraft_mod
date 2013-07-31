// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RenderIC2Boat.java

package ic2.core.item;

import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.ResourceLocation;

// Referenced classes of package ic2.core.item:
//			EntityIC2Boat

public class RenderIC2Boat extends RenderBoat
{

	public RenderIC2Boat()
	{
	}

	protected ResourceLocation func_110781_a(EntityBoat entity)
	{
		return new ResourceLocation("ic2", ((EntityIC2Boat)entity).getTexture());
	}
}
