package net.packet.server;

import java.nio.ByteOrder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import maplestory.MapleChannel;
import maplestory.world.MapleWorld;
import net.ServerOpCode;
import net.packet.MaplePacket;

public class MapleServerListPacket extends MaplePacket {

	public MapleServerListPacket(MapleWorld world) {
		super(ServerOpCode.SERVERLIST.getCode());
		
		ByteBuf buf = Unpooled.buffer(32).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.writeByte(world.getId());
		MaplePacket.writeString(buf, world.getName());
		buf.writeByte(world.getWorldInfo().getFlag());
		MaplePacket.writeString(buf, world.getWorldInfo().getEvent());
		buf.writeByte(100);
		buf.writeByte(0);
		buf.writeByte(100);
		buf.writeByte(0);
		buf.writeByte(0);
		buf.writeByte(world.getChannels().size());
		for(MapleChannel channel : world.getChannels()){
			MaplePacket.writeString(buf, channel.getName());
			buf.writeInt(channel.getPopulation());
			buf.writeByte(0);
			buf.writeShort(channel.getId());
		}
		buf.writeShort(0);
		
		buf = buf.capacity(buf.writerIndex());
		
		setPayload(buf);
	}

}
