// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Direction.java

package ic2.api;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public final class Direction extends Enum
{

	public static final Direction XN;
	public static final Direction XP;
	public static final Direction YN;
	public static final Direction YP;
	public static final Direction ZN;
	public static final Direction ZP;
	private int dir;
	public static final Direction directions[] = values();
	private static final Direction $VALUES[];

	public static Direction[] values()
	{
		return (Direction[])$VALUES.clone();
	}

	public static Direction valueOf(String name)
	{
		return (Direction)Enum.valueOf(ic2/api/Direction, name);
	}

	private Direction(String s, int i, int dir)
	{
		super(s, i);
		this.dir = dir;
	}

	public TileEntity applyToTileEntity(TileEntity tileEntity)
	{
		int coords[] = {
			tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord
		};
		coords[dir / 2] += getSign();
		if (tileEntity.worldObj != null && tileEntity.worldObj.blockExists(coords[0], coords[1], coords[2]))
			return tileEntity.worldObj.getBlockTileEntity(coords[0], coords[1], coords[2]);
		else
			return null;
	}

	public Direction getInverse()
	{
		int inverseDir = dir - getSign();
		Direction arr$[] = directions;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Direction direction = arr$[i$];
			if (direction.dir == inverseDir)
				return direction;
		}

		return this;
	}

	public int toSideValue()
	{
		return (dir + 4) % 6;
	}

	private int getSign()
	{
		return (dir % 2) * 2 - 1;
	}

	public ForgeDirection toForgeDirection()
	{
		return ForgeDirection.getOrientation(toSideValue());
	}

	static 
	{
		XN = new Direction("XN", 0, 0);
		XP = new Direction("XP", 1, 1);
		YN = new Direction("YN", 2, 2);
		YP = new Direction("YP", 3, 3);
		ZN = new Direction("ZN", 4, 4);
		ZP = new Direction("ZP", 5, 5);
		$VALUES = (new Direction[] {
			XN, XP, YN, YP, ZN, ZP
		});
	}
}
