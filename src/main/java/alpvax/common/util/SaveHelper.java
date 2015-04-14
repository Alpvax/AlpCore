package alpvax.common.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.common.DimensionManager;

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
		else if(MinecraftServer.getServer() != null)
		{
			MinecraftServer srv = MinecraftServer.getServer();
			return (SaveHandler)srv.getActiveAnvilConverter().getSaveLoader(srv.getFolderName(), false);
		}
		return null;
	}
}
