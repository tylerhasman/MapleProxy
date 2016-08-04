package net.packet.handlers;

import io.netty.buffer.ByteBuf;
import maplestory.map.life.MapleNpc;
import net.Proxy;
import net.handler.HandlePacketResult;
import net.handler.MaplePacketHandler;

public class SpawnNpcHandler implements MaplePacketHandler {

	@Override
	public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
	}

	@Override
	public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
		int objectId = buf.readInt();
		int refId    = buf.readInt();
		short x      = buf.readShort();
		short cy     = buf.readShort();
		boolean f    = !buf.readBoolean();
		short fh     = buf.readShort();
		short rx0    = buf.readShort();
		short rx1    = buf.readShort();
		
		MapleNpc npc = new MapleNpc(proxy.getMaplestory(), refId, objectId);
		npc.setX(x);
		npc.setY(cy);
		npc.setF(f);
		npc.setFh(fh);
		npc.setF(f);
		npc.setRx0(rx0);
		npc.setRx1(rx1);
		
		proxy.getMaplestory().getCurrentMap().addObject(npc);
		
		result.setCancelled(true);
	}

}
