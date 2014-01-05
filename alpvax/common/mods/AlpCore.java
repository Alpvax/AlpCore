package alpvax.common.mods;

import alpvax.common.network.AlpModPacket;
import alpvax.common.network.PacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = ModData.coreModID, name = ModData.coreModName, version = ModData.coreModVersion)//, acceptedMinecraftVersions = "[1.4]")
@NetworkMod(clientSideRequired=true,serverSideRequired=false, channels = {AlpModPacket.channel}, packetHandler = PacketHandler.class)
public class AlpCore
{
	@Instance(ModData.coreModID)
	public static AlpCore instance;
	

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		instance = this;
	}
}
