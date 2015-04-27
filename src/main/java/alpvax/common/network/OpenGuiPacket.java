package alpvax.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import alpvax.common.util.ModHelper;


public class OpenGuiPacket implements IMessage
{
	private String modID;
	private int guiID;

	public OpenGuiPacket()
	{
	}

	public OpenGuiPacket(String modID, int guiID)
	{
		this.modID = modID;
		this.guiID = guiID;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		modID = ByteBufUtils.readUTF8String(buf);
		guiID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, modID);
		buf.writeInt(guiID);
	}

	public static class Handler implements IMessageHandler<OpenGuiPacket, IMessage>
	{
		@Override
		public IMessage onMessage(OpenGuiPacket message, MessageContext ctx)
		{
			Minecraft.getMinecraft().addScheduledTask(new OpenGuiScheduledTask(message));
			return null;
		}
	}

	private static class OpenGuiScheduledTask implements Runnable
	{
		private OpenGuiPacket message;

		public OpenGuiScheduledTask(OpenGuiPacket message)
		{
			this.message = message;
		}

		@Override
		public void run()
		{
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			player.openGui(ModHelper.getMod(message.modID), message.guiID, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
		}
	}
}
