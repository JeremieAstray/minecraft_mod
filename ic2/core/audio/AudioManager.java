// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AudioManager.java

package ic2.core.audio;

import net.minecraftforge.common.Configuration;

// Referenced classes of package ic2.core.audio:
//			PositionSpec, AudioSource

public class AudioManager
{

	public float defaultVolume;

	public AudioManager()
	{
	}

	public void initialize(Configuration configuration)
	{
	}

	public void playOnce(Object obj1, String s)
	{
	}

	public void playOnce(Object obj1, PositionSpec positionspec, String s, boolean flag, float f)
	{
	}

	public void removeSources(Object obj1)
	{
	}

	public AudioSource createSource(Object obj, String initialSoundFile)
	{
		return null;
	}

	public AudioSource createSource(Object obj, PositionSpec positionSpec, String initialSoundFile, boolean flag, boolean flag1, float f)
	{
		return null;
	}

	public void onTick()
	{
	}

	public float getMasterVolume()
	{
		return 0.0F;
	}
}
