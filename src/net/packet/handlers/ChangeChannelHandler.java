package net.packet.handlers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import net.Proxy;
import net.handler.HandlePacketResult;
import net.handler.MaplePacketHandler;
import net.packet.server.MapleChangeChannelPacket;
import net.packet.server.MapleServerIpPacket;

public class ChangeChannelHandler implements MaplePacketHandler {

	@Override
	public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
	}

	@Override
	public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		buf.skipBytes(1);
		byte[] address = new byte[4];
		buf.readBytes(address);
		
		short port = buf.readShort();
		
		try {
			proxy.getConnectionFactory().startAcceptingConnectionsFrom(port, InetAddress.getByAddress(address), port);
			
			MapleChangeChannelPacket packet = new MapleChangeChannelPacket(proxy.getLocalWAN(), port);
			
			result.setResultPayload(packet.getPayload());
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
