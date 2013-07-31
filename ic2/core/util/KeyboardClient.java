// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   KeyboardClient.java

package ic2.core.util;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.TickType;
import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.network.NetworkManager;
import java.util.EnumSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

// Referenced classes of package ic2.core.util:
//			Keyboard

public class KeyboardClient extends Keyboard
{

	private Minecraft mc;
	private KeyBinding altKey;
	private KeyBinding boostKey;
	private KeyBinding modeSwitchKey;
	private KeyBinding sideinventoryKey;
	private int lastKeyState;

	public KeyboardClient()
	{
		mc = Minecraft.getMinecraft();
		altKey = new KeyBinding("ALT Key", 56);
		boostKey = new KeyBinding("Boost Key", 29);
		modeSwitchKey = new KeyBinding("Mode Switch Key", 50);
		sideinventoryKey = new KeyBinding("Side Inventory Key", 46);
		lastKeyState = 0;
		KeyBindingRegistry.registerKeyBinding(new cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler(new KeyBinding[] {
			altKey, boostKey, modeSwitchKey, sideinventoryKey
		}) {

			final KeyboardClient this$0;

			public String getLabel()
			{
				return "IC2Keyboard";
			}

			public EnumSet ticks()
			{
				return EnumSet.of(TickType.CLIENT);
			}

			public void keyUp(EnumSet enumset, KeyBinding keybinding, boolean flag)
			{
			}

			public void keyDown(EnumSet enumset, KeyBinding keybinding, boolean flag, boolean flag1)
			{
			}

			
			{
				this$0 = KeyboardClient.this;
				super(x0);
			}
		});
	}

	public void sendKeyUpdate()
	{
		int currentKeyState = (altKey.pressed ? 1 : 0) << 0 | (boostKey.pressed ? 1 : 0) << 1 | (mc.gameSettings.keyBindForward.pressed ? 1 : 0) << 2 | (modeSwitchKey.pressed ? 1 : 0) << 3 | (mc.gameSettings.keyBindJump.pressed ? 1 : 0) << 4 | (sideinventoryKey.pressed ? 1 : 0) << 5;
		if (currentKeyState != lastKeyState)
		{
			IC2.network.initiateKeyUpdate(currentKeyState);
			super.processKeyUpdate(IC2.platform.getPlayerInstance(), currentKeyState);
			lastKeyState = currentKeyState;
		}
	}
}
