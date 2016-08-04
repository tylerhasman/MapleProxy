package net.handler;

import net.Proxy;
import io.netty.buffer.ByteBuf;

public interface MaplePacketHandler {

	public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result);
	
	public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result);
	
}
