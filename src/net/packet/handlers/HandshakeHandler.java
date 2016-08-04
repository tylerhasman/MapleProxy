package net.packet.handlers;

import io.netty.buffer.ByteBuf;
import net.Proxy;
import net.handler.HandlePacketResult;
import net.handler.MaplePacketHandler;

public class HandshakeHandler implements MaplePacketHandler {

	@Override
	public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result) {

	}

	@Override
	public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		short version = buf.readShort();
		buf.skipBytes(3);
		byte[] recv = new byte[4];
		byte[] send = new byte[4];
		buf.readBytes(recv);
		buf.readBytes(send);
		//byte locale = buf.readByte();
		
		/*System.out.println("Server version is "+version);
		System.out.println("Crypt keys are "+Arrays.toString(recv)+" "+Arrays.toString(send));
		System.out.println("Locale is "+locale);*/
		
		/*client.setCryptKeys(recv, send);
		server.setCryptKeys(send, recv);*/
		proxy.getClient().setCryptKeys(send, recv, version);
		proxy.getServer().setCryptKeys(recv, send, version);
	}

}
