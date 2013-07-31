// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TextureIndexClient.java

package ic2.core.util;

import java.util.HashMap;
import java.util.Map;

// Referenced classes of package ic2.core.util:
//			TextureIndex

public class TextureIndexClient extends TextureIndex
{

	private final Map textureIndexes = new HashMap();

	public TextureIndexClient()
	{
	}

	public int get(int blockId, int index)
	{
		return -1;
	}

	public void reset()
	{
		textureIndexes.clear();
	}
}
