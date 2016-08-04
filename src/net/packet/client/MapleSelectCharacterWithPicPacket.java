package net.packet.client;

import java.nio.ByteOrder;

import security.MapleSecurity;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.ClientOpCode;
import net.packet.MaplePacket;

public class MapleSelectCharacterWithPicPacket extends MaplePacket {

	public MapleSelectCharacterWithPicPacket(String pic, int charId) {
		super(ClientOpCode.CHAR_SELECT_WITH_PIC.getCode());
		
		ByteBuf buf = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);
		
		MaplePacket.writeString(buf, pic);
		buf.writeInt(charId);
		MaplePacket.writeString(buf, MapleSecurity.getRandomMAC());
		MaplePacket.writeString(buf, MapleSecurity.getRandomHWID());
		
		setPayload(buf);
	}

}
