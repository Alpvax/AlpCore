package alpvax.common.core;

import java.io.File;
import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

public class AlpFMLLoadingPlugin implements IFMLLoadingPlugin
{
	@Override
	public String[] getASMTransformerClass()
	{
		return new String[]{ECClassTransformer.class.getName()};
	}

	@Override
	public String getModContainerClass()
	{
		return AlpCore.class.getName();
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

	/* (non-Javadoc)
	 * @see net.minecraftforge.fml.relauncher.IFMLLoadingPlugin#getAccessTransformerClass()
	 */
	@Override
	public String getAccessTransformerClass()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
