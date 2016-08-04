package net.packet.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteOrder;
import java.util.List;

import net.ServerOpCode;
import net.packet.MaplePacket;
import net.packet.movement.LifeMovementFragment;

public class MapleMovePlayerPacket extends MaplePacket {

	public MapleMovePlayerPacket(int oid, List<LifeMovementFragment> movements) {
		super(ServerOpCode.MOVE_PLAYER.getCode());
		
		ByteBuf buf = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);
		
		buf.writeInt(oid);
		buf.writeInt(0);
		buf.writeByte(movements.size());
		for(LifeMovementFragment frag : movements){
			frag.serialize(buf);
		}
		
		setPayload(buf.capacity(buf.writerIndex()));
	}

}
