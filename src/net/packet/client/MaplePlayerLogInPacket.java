package net.packet.client;

import java.nio.ByteOrder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.ClientOpCode;
import net.packet.MaplePacket;

public class MaplePlayerLogInPacket extends MaplePacket {

	public MaplePlayerLogInPacket(int charId) {
		super(ClientOpCode.PLAYER_LOGGEDIN.getCode());
		
		ByteBuf buf = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);
		
		buf.writeInt(charId);
		
		setPayload(buf);
	}

}
