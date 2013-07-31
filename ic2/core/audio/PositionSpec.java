// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PositionSpec.java

package ic2.core.audio;


public final class PositionSpec extends Enum
{

	public static final PositionSpec Center;
	public static final PositionSpec Backpack;
	public static final PositionSpec Hand;
	private static final PositionSpec $VALUES[];

	public static PositionSpec[] values()
	{
		return (PositionSpec[])$VALUES.clone();
	}

	public static PositionSpec valueOf(String name)
	{
		return (PositionSpec)Enum.valueOf(ic2/core/audio/PositionSpec, name);
	}

	private PositionSpec(String s, int i)
	{
		super(s, i);
	}

	static 
	{
		Center = new PositionSpec("Center", 0);
		Backpack = new PositionSpec("Backpack", 1);
		Hand = new PositionSpec("Hand", 2);
		$VALUES = (new PositionSpec[] {
			Center, Backpack, Hand
		});
	}
}
