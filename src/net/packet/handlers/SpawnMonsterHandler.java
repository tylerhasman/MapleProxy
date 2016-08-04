package net.packet.handlers;

import java.util.Collections;
import java.util.HashMap;

import io.netty.buffer.ByteBuf;
import maplestory.map.life.MapleMonster;
import net.ClientOpCode;
import net.Proxy;
import net.handler.HandlePacketResult;
import net.handler.MaplePacketHandler;
import net.packet.MaplePacket;
import net.packet.attack.AttackData;

public class SpawnMonsterHandler implements MaplePacketHandler {

	@Override
	public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result) {

	}

	@Override
	public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
		int objectId = buf.readInt();
		byte controller = buf.readByte();
		int monsterId = buf.readInt();
		
		MapleMonster monster = new MapleMonster(proxy.getMaplestory(), monsterId, objectId);
		
		proxy.getMaplestory().getCurrentMap().addObject(monster);
		
		if(proxy.isSpawnKillEnabled()){
			/*AttackData data = AttackData.generateBullshitData();
			data.allDamage.put(objectId, Collections.singletonList(50));
			
			proxy.getServer().sendPacket(new MaplePacket(ClientOpCode.CLOSE_RANGE_ATTACK.getCode(), data.toPacketPayload(), false));
			*/
			
			for(MapleMonster mon : proxy.getMaplestory().getCurrentMap().getObjects(MapleMonster.class)){
				AttackData data = AttackData.generateBullshitData();
				data.allDamage.put(mon.getObjectId(), Collections.singletonList(proxy.getSpawnKillDamage()));
				
				proxy.getServer().sendPacket(new MaplePacket(ClientOpCode.CLOSE_RANGE_ATTACK.getCode(), data.toPacketPayload(), false));
			}
		}
	
	}

}
