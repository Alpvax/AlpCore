package alpvax.common.network;

import ibxm.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class PacketHandler implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		try
		{
			EntityPlayer sender = (EntityPlayer) player;
			ByteArrayDataInput in = ByteStreams.newDataInput(packet.data);
			int packetId = in.readUnsignedByte();
			AlpModPacket modPacket = AlpModPacket.constructPacket(packetId);
			modPacket.read(in);
			modPacket.execute(sender, sender.worldObj.isRemote ? Side.CLIENT : Side.SERVER);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}