package net.packet.server;

import java.nio.ByteOrder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import maplestory.map.life.MapleNpc;
import net.ServerOpCode;
import net.packet.MaplePacket;

public class MapleSpawnNpcPacket extends MaplePacket {

	public MapleSpawnNpcPacket(MapleNpc npc) {
		super(ServerOpCode.SPAWN_NPC.getCode());
		
		ByteBuf buf = Unpooled.buffer(19).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.writeInt(npc.getObjectId());
		buf.writeInt(npc.getRefId());
		buf.writeShort(npc.getX());
		buf.writeShort(npc.getY());
		buf.writeBoolean(!npc.isF());
		buf.writeShort(npc.getFh());
		buf.writeShort(npc.getRx0());
		buf.writeShort(npc.getRx1());
		buf.writeByte(1);
		
		setPayload(buf);
	}

}
