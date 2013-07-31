// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityElectricBatBox.java

package ic2.core.block.wiring;


// Referenced classes of package ic2.core.block.wiring:
//			TileEntityElectricBlock

public class TileEntityElectricBatBox extends TileEntityElectricBlock
{

	public TileEntityElectricBatBox()
	{
		super(1, 32, 40000);
	}

	public String getInvName()
	{
		return "BatBox";
	}
}
