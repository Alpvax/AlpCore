package alpvax.common.util;

import java.io.File;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;


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

	/*public static File getCurrentConfigDir()
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
	/*public static Configuration[] getConfigs(String subDir, String configName)
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
	}*/

	public static Configuration getCurrentConfig(String subDir, String configName)
	{
		String path = (subDir == null || subDir.length() < 1 ? "" : subDir + "/") + configName;
		SaveHandler s = getWorldSaveHandler();
		if(s != null)
		{
			return new Configuration(new File(s.getWorldDirectory() + "/config", path));
		}
		return new Configuration(new File(GlobalConstants.DEF_CONFIG_DIR, path));
	}

	/**
	 * Retrieve a property from the appropriate {@link Configuration}.<br>
	 * Returns {@code null} if the property is not found
	 * @param subDir The subpath (beneath /config/) the config file is located in
	 * @param configName The name of the config file
	 * @param category The category the key is saved under
	 * @param key The key to retrieve
	 */
	public static Property getProperty(String subDir, String configName, String category, String key)
	{
		return getProperty(subDir, configName, category, key, null, null, null, false);
	}

	/**
	 * Retrieve a property from the appropriate {@link Configuration}.<br>
	 * Saves {@code defaultValue} if the property is not found
	 * @param subDir The subpath (beneath /config/) the config file is located in
	 * @param configName The name of the config file
	 * @param category The category the key is saved under
	 * @param key The key to save/retrieve
	 * @param defaultValue The value to use if the key does not already exist
	 * @param comment A comment for the key
	 * @param type The {@link Property.Type} to save/retrieve
	 */
	public static Property getProperty(String subDir, String configName, String category, String key, String defaultValue, String comment, Property.Type type)
	{
		return getProperty(subDir, configName, category, key, new String[]{defaultValue}, comment, type, false);
	}

	/**
	 * Retrieve an array property from the appropriate {@link Configuration}.<br>
	 * Saves {@code defaultValues} if the property is not found
	 * @param subDir The subpath (beneath /config/) the config file is located in
	 * @param configName The name of the config file
	 * @param category The category the key is saved under
	 * @param key The key to save/retrieve
	 * @param defaultValues The values to use if the key does not already exist
	 * @param comment A comment for the key
	 * @param type The {@link Property.Type} to save/retrieve
	 */
	public static Property getProperty(String subDir, String configName, String category, String key, String[] defaultValues, String comment, Property.Type type)
	{
		return getProperty(subDir, configName, category, key, defaultValues, comment, type, true);
	}

	private static Property getProperty(String subDir, String configName, String category, String key, String[] defaultValues, String comment, Property.Type type, boolean isList)
	{
		if(key == null || key.equals(""))
		{
			throw new NullPointerException("Config key cannot be null");
		}
		SaveHandler s = getWorldSaveHandler();
		Configuration conf = null;
		if(s != null)
		{
			conf = new Configuration(new File(s.getWorldDirectory() + "/config/" + subDir, configName));
		}
		if(conf == null || !conf.hasKey(category, key))
		{
			Configuration def = new Configuration(new File(GlobalConstants.DEF_CONFIG_DIR + "/" + subDir, configName));
			if(defaultValues == null && !def.hasKey(category, key))
			{
				return null;
			}
			Property p = getPropertyAndSave(def, category, key, defaultValues, defaultValues[0], comment, type, isList);
			if(conf != null)
			{
				return getPropertyAndSave(conf, category, key, p.getStringList(), p.getString(), comment, type, isList);
			}
			return p;
		}
		return getPropertyAndSave(conf, category, key, defaultValues, defaultValues[0], comment, type, isList);
	}

	private static Property getPropertyAndSave(Configuration config, String category, String key, String[] defaultValues, String defaultValue, String comment, Property.Type type, boolean isList)
	{
		Property p = isList ? config.get(category, key, defaultValues, comment, type) : config.get(category, key, defaultValue, comment, type);
		config.save();
		return p;
	}

	/*public static String getConfigOption(String subDir, String configName, String category, String key, String _default)
	{
		SaveHandler s = getWorldSaveHandler();
		Configuration conf = null;
		if(s != null)
		{
			conf = new Configuration(new File(s.getWorldDirectory() + "config/" + subDir, configName));
			if(conf.hasKey(category, key))
			{
				return conf.get(category, key, _default).getString();
			}
		}
		Configuration def = new Configuration(new File(GlobalConstants.DEF_CONFIG_DIR + "/" + subDir, configName));
		Property p = null;
		if(def.hasKey(category, key))
		{
			p = def.getCategory(category).get(key);
			if(conf != null)
			{
				conf.getCategory(category).put(key, p);
			}
			return p;
		}
		return null;
	}*/
}
