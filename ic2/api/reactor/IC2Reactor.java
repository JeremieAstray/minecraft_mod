// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IC2Reactor.java

package ic2.api.reactor;

import java.lang.reflect.Field;

public class IC2Reactor
{

	private static Field energyGeneratorNuclear;

	public IC2Reactor()
	{
	}

	public static int getEUOutput()
	{
		if (energyGeneratorNuclear == null)
			energyGeneratorNuclear = Class.forName((new StringBuilder()).append(getPackage()).append(".core.IC2").toString()).getDeclaredField("energyGeneratorNuclear");
		return energyGeneratorNuclear.getInt(null);
		Throwable e;
		e;
		throw new RuntimeException(e);
	}

	private static String getPackage()
	{
		Package pkg = ic2/api/reactor/IC2Reactor.getPackage();
		if (pkg != null)
		{
			String packageName = pkg.getName();
			return packageName.substring(0, packageName.length() - ".api.reactor".length());
		} else
		{
			return "ic2";
		}
	}
}
