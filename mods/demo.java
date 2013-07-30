package mods;

import net.minecraft.src.ModLoader;
import items.demoitem;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid="demo",name="MOD",version="1.0.0")


public class demo {
	public demoitem ini;
	@PreInit
	public void pre_init(FMLPreInitializationEvent event)
	{	
		ini = new demoitem(50000);
	}
	@Init
	public void init(FMLInitializationEvent evt)
	{
		ModLoader.addName(ini,"supersward");

	}
}
