package net.packet.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.nio.ByteOrder;

import net.ServerOpCode;
import net.packet.MaplePacket;

public class MapleServerIpPacket extends MaplePacket {

	public MapleServerIpPacket(InetAddress address, short port, int charid) {
		super(ServerOpCode.SERVER_IP.getCode());
		
		ByteBuf buf = Unpooled.buffer(17, 17).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.writeShort(0);
		buf.writeBytes(address.getAddress());
		buf.writeShort(port);
		buf.writeInt(charid);
		buf.writeBytes(new byte[5]);
		
		setPayload(buf);
	}

}
