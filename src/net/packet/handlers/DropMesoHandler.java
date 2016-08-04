package net.packet.handlers;

import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import net.Proxy;
import net.handler.HandlePacketResult;
import net.handler.MaplePacketHandler;

public class DropMesoHandler implements MaplePacketHandler {

	@Override
	public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
	}

	@Override
	public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result) {

	}

}
