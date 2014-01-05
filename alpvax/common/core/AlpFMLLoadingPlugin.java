package alpvax.common.core;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class AlpFMLLoadingPlugin implements IFMLLoadingPlugin
{
	@Override
	public String[] getLibraryRequestClass()
	{
		return null;
	}

	@Override
	public String[] getASMTransformerClass()
	{
		return new String[]{ECClassTransformer.class.getName()};
	}

	@Override
	public String getModContainerClass()
	{
		return AlpMod.class.getName();
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
		AlpCore.location = (File)data.get("coremodLocation");
	}
}
