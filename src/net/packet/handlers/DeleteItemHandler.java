package net.packet.handlers;

import io.netty.buffer.ByteBuf;
import net.Proxy;
import net.handler.HandlePacketResult;
import net.handler.MaplePacketHandler;

public class DeleteItemHandler implements MaplePacketHandler {

	@Override
	public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
	}

	@Override
	public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
		byte animation = buf.readByte();
		int oid = buf.readInt();
		
		proxy.getMaplestory().getCurrentMap().removeObject(oid);
		
	}

}
