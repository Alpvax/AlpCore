package alpvax.mod.common.util;

import java.io.File;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;

public class SaveHelper
{
	public static String getWorldName()
	{
		SaveHandler s = getWorldSaveHandler();
		return s != null ? s.loadWorldInfo().getWorldName() : null;
	}

	public static SaveHandler getWorldSaveHandler()
	{
		if(DimensionManager.getWorld(0) != null)
		{
			return (SaveHandler)DimensionManager.getWorld(0).getSaveHandler();
		}
		MinecraftServer srv = MinecraftServer.getServer();
		if(srv != null)
		{
			ISaveFormat s = srv.getActiveAnvilConverter();
			return s != null ? (SaveHandler)s.getSaveLoader(srv.getFolderName(), false) : null;
		}
		return null;
	}
	
	public static File getCurrentConfigDir()
	{
		return getCurrentConfigDir("");
	}
	
	public static File getCurrentConfigDir(String subDir)
	{
		SaveHandler s = getWorldSaveHandler();
		File f;
		if(s == null)
		{
			f = GlobalConstants.DEF_CONFIG_DIR;
		}
		else
		{
			f = new File(s.getWorldDirectory(), "config");
		}
		if(subDir != null && subDir.length() > 0)
		{
			f = new File(f, subDir);
		}
		f.mkdirs();
		return f;
	}
	
	/**
	 * @param subDir Optional subpath to look in
	 * @param configName
	 * @return
	 */
	public static Configuration[] getConfigs(String subDir, String configName)
	{
		File f = new File(GlobalConstants.DEF_CONFIG_DIR, subDir);
		f.mkdirs();
		Configuration[] res = {new Configuration(new File(f, configName)), null};
		SaveHandler s = getWorldSaveHandler();
		if(s != null)
		{
			f = new File(s.getWorldDirectory(), "config/" + subDir);
			f.mkdirs();
			res[1] = new Configuration(new File(f, configName));
		}
		return res;
	}
	
	public static Configuration getCurrentConfig(String subDir, String configName)
	{
		Configuration[] configs = SaveHelper.getConfigs(subDir, configName);
		return configs[1] != null ? configs[1] : configs[0];
	}
}
