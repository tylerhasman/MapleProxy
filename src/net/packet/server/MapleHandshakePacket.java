package net.packet.server;

import java.nio.ByteOrder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.packet.MaplePacket;

public class MapleHandshakePacket extends MaplePacket {

	public MapleHandshakePacket(int version, byte[] send, byte[] recv, int locale) {
		super(0x0E);
		
		ByteBuf buf = Unpooled.buffer(14, 14).order(ByteOrder.LITTLE_ENDIAN);
		buf.writeShort(version);
		buf.writeByte(49);
		buf.writeBytes(recv);
		buf.writeBytes(send);
		buf.writeByte(locale);
		
		setPayload(buf);
	}

}
