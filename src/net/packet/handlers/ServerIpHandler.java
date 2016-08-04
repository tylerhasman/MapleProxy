package net.packet.handlers;

import io.netty.buffer.ByteBuf;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import net.Proxy;
import net.handler.HandlePacketResult;
import net.handler.MaplePacketHandler;
import net.packet.server.MapleServerIpPacket;

public class ServerIpHandler implements MaplePacketHandler {

	@Override
	public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
	}

	@Override
	public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		buf.skipBytes(2);
		try {
			byte[] hostBytes = new byte[4];
			buf.readBytes(hostBytes);
			InetAddress address = InetAddress.getByAddress(hostBytes);
			
			short port = buf.readShort();
			int charid = buf.readInt();
			buf.skipBytes(4);
			
			int maskedPort = 100 + port;
			
			MapleServerIpPacket packet = new MapleServerIpPacket(InetAddress.getLocalHost(), (short) (maskedPort), charid);
			
			result.setResultPayload(packet.getPayload());
			
			proxy.getConnectionFactory().startAcceptingConnectionsFrom(maskedPort, address, port);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
