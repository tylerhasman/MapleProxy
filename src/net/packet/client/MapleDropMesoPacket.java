
package net.packet.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteOrder;

import net.ClientOpCode;
import net.packet.MaplePacket;

public class MapleDropMesoPacket extends MaplePacket {

	public static int unk = 57140058;
	
	public MapleDropMesoPacket(int amount) {
		super(ClientOpCode.MESO_DROP.getCode());
		
		ByteBuf buf = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);
		
		buf.writeInt(unk += 5000);
		buf.writeInt(amount);
		
		buf = buf.capacity(buf.writerIndex());
		
		setPayload(buf);
	}

}
