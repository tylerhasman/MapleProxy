package net.packet.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.net.InetAddress;
import java.nio.ByteOrder;

import net.ServerOpCode;
import net.packet.MaplePacket;

public class MapleChangeChannelPacket extends MaplePacket {

	public MapleChangeChannelPacket(InetAddress address, short port) {
		super(ServerOpCode.CHANGE_CHANNEL.getCode());
	
		ByteBuf buf = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);
		
		buf.writeByte(1);
		buf.writeBytes(address.getAddress());
		buf.writeShort(port);
	
		buf = buf.capacity(buf.writerIndex());
		
		setPayload(buf);
	}

}
