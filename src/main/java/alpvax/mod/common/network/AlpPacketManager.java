package alpvax.mod.common.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;


/**
 * @author Alpvax
 *
 */
public class AlpPacketManager extends SimpleNetworkWrapper
{
	private int nextID = 0;

	public AlpPacketManager(String channelName)
	{
		super(channelName);
	}

	/**
	 * Registers a message and message handler
	 */
	public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> handlerClass, Class<REQ> messageClass, Side side)
	{
		if(nextID++ > 255)
		{
			throw new IndexOutOfBoundsException("Maximum number of Packets registered on this channel, unable to register packet " + messageClass);
		}
		registerMessage(handlerClass, messageClass, nextID, side);
	}

	/**
	 * Registers a message and message handler on both sides
	 * @param handlerClass Must extend {@link AbstractBiMessageHandler} 
	 * @param messageClass must implement {@link IMessage}
	 */
	public <REQ extends IMessage, REPLY extends IMessage> void register2WayMessage(Class<? extends IMessageHandler<REQ, REPLY>> clientHandlerClass, Class<? extends IMessageHandler<REQ, REPLY>> serverHandlerClass, Class<REQ> messageClass)
	{
		registerMessage(clientHandlerClass, messageClass, Side.CLIENT);
		registerMessage(serverHandlerClass, messageClass, Side.SERVER);
	}
}
