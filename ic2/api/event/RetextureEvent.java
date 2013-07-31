// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RetextureEvent.java

package ic2.api.event;

import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

public class RetextureEvent extends WorldEvent
{

	public final int x;
	public final int y;
	public final int z;
	public final int side;
	public final int referencedBlockId;
	public final int referencedMeta;
	public final int referencedSide;
	public boolean applied;

	public RetextureEvent(World world, int x, int y, int z, int side, int referencedBlockId, int referencedMeta, 
			int referencedSide)
	{
		super(world);
		applied = false;
		this.x = x;
		this.y = y;
		this.z = z;
		this.side = side;
		this.referencedBlockId = referencedBlockId;
		this.referencedMeta = referencedMeta;
		this.referencedSide = referencedSide;
	}
}
