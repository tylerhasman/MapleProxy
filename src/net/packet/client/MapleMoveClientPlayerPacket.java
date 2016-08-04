package net.packet.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteOrder;
import java.util.List;
import net.ClientOpCode;
import net.packet.MaplePacket;
import net.packet.movement.LifeMovementFragment;

public class MapleMoveClientPlayerPacket extends MaplePacket {

	public MapleMoveClientPlayerPacket(List<LifeMovementFragment> movements) {
		super(ClientOpCode.MOVE_PLAYER.getCode());
		
		ByteBuf buf = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);
		
		buf.writeBytes(new byte[] {1, -70, -33, 19, 70, -61, -3, 86, 0});
		buf.writeByte(movements.size());
		
		for(LifeMovementFragment move : movements){
			move.serialize(buf);
		}
		
		buf.writeBytes(new byte[] {17, -120, -120, -120, -120, -120, -120, -120, -120, 8, 17, -2, 17, -1, 81, -2, 94, -1});
		
		buf = buf.capacity(buf.writerIndex());
		
		setPayload(buf);
		
	}

}
