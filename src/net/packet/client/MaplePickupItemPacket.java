package net.packet.client;

import java.nio.ByteOrder;
import java.nio.channels.SeekableByteChannel;
import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import maplestory.map.MapleDroppedItem;
import net.ClientOpCode;
import net.Proxy;
import net.Start;
import net.packet.MaplePacket;

public class MaplePickupItemPacket extends MaplePacket {

	public MaplePickupItemPacket(MapleDroppedItem item){
		super(ClientOpCode.ITEM_PICKUP.getCode());
		
		ByteBuf buf = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);
		
		buf.writeBytes(new byte[] {2, -44, -35, 51});
		buf.writeByte(0);
		
		buf.writeShort(item.getX());
		buf.writeShort(item.getY());
		
		buf.writeInt(item.getObjectId());
		
		buf.writeBytes(new byte[] {108, -87, -102, 73});
		
		setPayload(buf.capacity(buf.writerIndex()).array());
	}

}
