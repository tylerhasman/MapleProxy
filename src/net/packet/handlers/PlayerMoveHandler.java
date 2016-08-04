package net.packet.handlers;

import io.netty.buffer.ByteBuf;

import java.util.List;

import maplestory.MapleCharacter;
import net.Proxy;
import net.handler.HandlePacketResult;
import net.handler.MaplePacketHandler;
import net.packet.movement.LifeMovementFragment;
import net.packet.movement.MovementUtil;

public class PlayerMoveHandler implements MaplePacketHandler {
	
	@Override
	public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		MapleCharacter chr = proxy.getMaplestory().getPlayer();
		if(chr == null){
			return;
		}
		buf.skipBytes(9);
		List<LifeMovementFragment> movement = MovementUtil.parseMovement(buf);
		if(movement != null){
			MovementUtil.updatePosition(movement, chr, 0);
		}
	}

	@Override
	public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
	}

}
