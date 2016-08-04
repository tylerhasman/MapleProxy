package net.packet.server;

import java.nio.ByteOrder;

import lombok.AllArgsConstructor;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.ServerOpCode;
import net.packet.MaplePacket;

public class MapleNpcPacket extends MaplePacket {

	@AllArgsConstructor
	public static enum NpcTalkType {
		
		OK(0, new byte[2]),
		SIMPLE(4, new byte[0]);
		
		private final int code;
		private final byte[] extra;
		
	}

	public MapleNpcPacket(int npcId, String text, NpcTalkType type) {
		super(ServerOpCode.NPC_TALK.getCode());
		
		ByteBuf buf = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);
		
		buf.writeByte(4);
		buf.writeInt(npcId);
		buf.writeByte(type.code);//OK
		buf.writeByte(0);
		writeString(buf, text);
		buf.writeBytes(type.extra);

		buf = buf.capacity(buf.writerIndex());
		
		setPayload(buf);
	}

}
