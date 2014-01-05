package alpvax.common.core;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import alpvax.common.mods.ModData;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
//import cpw.mods.fml.common.event.FMLInitializationEvent;

public class AlpCore extends DummyModContainer
{
	public static File location;
	public static Map<String, String> classTransformMap = new HashMap<String, String>();
	
	public EnchantColoursMod()
	{
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = ModData.coreModID;
		meta.name = ModData.coreModName;
		meta.version = ModData.coreModVersion;
		//meta.credits = "";
		meta.authorList = Arrays.asList("Alpvax");
		meta.description = "Core library for all Alpvax's mods";
		meta.url = "";
		//meta.updateUrl = "";
		//meta.screenshots = new String[0];
		//meta.logoFile = "";
	}/*

	@Override
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		bus.register(this);
		return true;
	}
	
	@Subscribe
	public void init(FMLInitializationEvent evt)
	{
	}*/
}
