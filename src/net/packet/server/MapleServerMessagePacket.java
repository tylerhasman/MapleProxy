package net.packet.server;

import java.nio.ByteOrder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import maplestory.MessageType;
import net.ServerOpCode;
import net.packet.MaplePacket;

public class MapleServerMessagePacket extends MaplePacket {

	public MapleServerMessagePacket(MessageType type, String msg){
		super(ServerOpCode.SERVERMESSAGE.getCode());
		
		ByteBuf buf = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);
		buf.writeByte(type.getId());
		if(type == MessageType.SCROLLING_TEXT){
			buf.writeByte(1);
		}
		MaplePacket.writeString(buf, msg);
		if(type == MessageType.SUPER_MEGAPHONE){
			throw new RuntimeException("Not implemented");
		}else if(type == MessageType.LIGHT_BLUE_TEXT){
			buf.writeInt(0);
		}
		
		setPayload(buf);
	}
	
}
