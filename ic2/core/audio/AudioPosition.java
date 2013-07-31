// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AudioPosition.java

package ic2.core.audio;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

// Referenced classes of package ic2.core.audio:
//			PositionSpec

public class AudioPosition
{

	public World world;
	public float x;
	public float y;
	public float z;

	public static AudioPosition getFrom(Object obj, PositionSpec positionSpec)
	{
		if (obj instanceof AudioPosition)
			return (AudioPosition)obj;
		if (obj instanceof Entity)
		{
			Entity e = (Entity)obj;
			return new AudioPosition(e.worldObj, (float)e.posX, (float)e.posY, (float)e.posZ);
		}
		if (obj instanceof TileEntity)
		{
			TileEntity te = (TileEntity)obj;
			return new AudioPosition(te.worldObj, (float)te.xCoord + 0.5F, (float)te.yCoord + 0.5F, (float)te.zCoord + 0.5F);
		} else
		{
			return null;
		}
	}

	public AudioPosition(World world, float x, float y, float z)
	{
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
