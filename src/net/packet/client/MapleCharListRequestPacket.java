package net.packet.client;

import java.nio.ByteOrder;
import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.ClientOpCode;
import net.packet.MaplePacket;

public class MapleCharListRequestPacket extends MaplePacket {

	public MapleCharListRequestPacket(int worldId, int channel) {
		super(ClientOpCode.CHARLIST_REQUEST.getCode());
		
		ByteBuf buf = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);
		
		buf.writeByte(0);
		buf.writeByte(worldId);
		buf.writeByte(channel);
		
		buf = buf.capacity(buf.writerIndex());
		
		setPayload(buf);
	}

}
