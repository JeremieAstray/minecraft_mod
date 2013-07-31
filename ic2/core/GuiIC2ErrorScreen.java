// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GuiIC2ErrorScreen.java

package ic2.core;

import net.minecraft.client.gui.GuiScreen;

public class GuiIC2ErrorScreen extends GuiScreen
{

	private final String title;
	private final String error;

	public GuiIC2ErrorScreen(String title, String error)
	{
		this.title = title;
		this.error = error;
	}

	public void drawScreen(int par1, int par2, float par3)
	{
		drawBackground(0);
		drawCenteredString(super.fontRenderer, title, super.width / 2, (super.height / 4 - 60) + 20, 0xffffff);
		int add = 0;
		String split[] = error.split("\n");
		String arr$[] = split;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			String s = arr$[i$];
			drawString(super.fontRenderer, s, super.width / 2 - 180, (((super.height / 4 - 60) + 60) - 10) + add, 0xa0a0a0);
			add += 10;
		}

	}
}
