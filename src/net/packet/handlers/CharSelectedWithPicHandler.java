package net.packet.handlers;

import io.netty.buffer.ByteBuf;
import net.Proxy;
import net.handler.HandlePacketResult;
import net.handler.MaplePacketHandler;
import net.packet.MaplePacket;

public class CharSelectedWithPicHandler implements MaplePacketHandler {

	@Override
	public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		System.out.println("PIC "+MaplePacket.readString(buf));
		buf.skipBytes(4);
		String macs = MaplePacket.readString(buf);
		System.out.println("macs "+macs);
	}

	@Override
	public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
	}

}
