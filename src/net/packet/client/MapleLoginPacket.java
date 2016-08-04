package net.packet.client;

import java.nio.ByteOrder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.ClientOpCode;
import net.packet.MaplePacket;

public class MapleLoginPacket extends MaplePacket {

	public MapleLoginPacket(String username, String pw) {
		super(ClientOpCode.LOGIN_PASSWORD.getCode());
		
		ByteBuf buf = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);
		
		MaplePacket.writeString(buf, username);
		MaplePacket.writeString(buf, pw);
		
		byte[] unknown = {8, 0, 39, -35, -118, 36, -87, 86, 10, -64, 0, 0, 0, 0, -94, 115, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0};
		
		buf.writeBytes(unknown);
		
		buf = buf.capacity(buf.writerIndex());
		
		setPayload(buf);
	}


}
