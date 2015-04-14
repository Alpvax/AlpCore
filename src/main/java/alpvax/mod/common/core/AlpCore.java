package alpvax.mod.common.core;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import alpvax.mod.common.mods.ModData;
import alpvax.mod.common.util.GlobalConstants;

import com.google.common.eventbus.Subscribe;


// import cpw.mods.fml.common.event.FMLInitializationEvent;

public class AlpCore extends DummyModContainer
{
	public static File location;
	public static Map<String, String> classTransformMap = new HashMap<String, String>();

	public AlpCore()
	{
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = ModData.coreModID;
		meta.name = ModData.coreModName;
		meta.version = ModData.coreModVersion;
		// meta.credits = "";
		meta.authorList = Arrays.asList("Alpvax");
		meta.description = "Core library for all Alpvax's mods";
		meta.url = "";
		// meta.updateUrl = "";
		// meta.screenshots = new String[0];
		// meta.logoFile = "";
	}/*
		*
		* @Override public boolean registerBus(EventBus bus, LoadController controller) { bus.register(this); return true; }
		*/

	@Subscribe
	public void preInit(FMLPreInitializationEvent e)
	{
		GlobalConstants.DEF_CONFIG_DIR = e.getModConfigurationDirectory();
	}
}
