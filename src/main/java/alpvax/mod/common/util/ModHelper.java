package alpvax.mod.common.util;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

public class ModHelper
{
	public static Object getMod(String modID)
	{
		for(ModContainer mc : Loader.instance().getActiveModList())
		{
			if(mc.getModId().equals(modID))
			{
				return mc.getMod();
			}
		}
		return null;
	}
}
