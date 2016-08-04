package net.packet.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import maplestory.MapleByteBufWrapper;
import net.ClientOpCode;
import net.packet.MaplePacket;

public class MapleChangeMapPacket extends MaplePacket {

	public MapleChangeMapPacket(String portalId) {
		super(ClientOpCode.CHANGE_MAP.getCode());
		
		ByteBuf buf = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);
		
		buf.writeByte(2);
		
		buf.writeBytes(new byte[] {-1, -1, -1, -1});
		
		buf = new MapleByteBufWrapper(buf).writeMapleString(portalId);
		
		buf.writeByte(56);
		
		buf.writeBytes(new byte[] {0, -58, 1, 0, 0, 0});
		
		setPayload(buf.capacity(buf.writerIndex()).array());
		
	}

}
