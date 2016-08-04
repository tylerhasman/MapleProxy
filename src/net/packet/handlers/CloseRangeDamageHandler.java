package net.packet.handlers;

import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.netty.buffer.ByteBuf;
import maplestory.map.life.MapleMonster;
import net.Proxy;
import net.handler.HandlePacketResult;
import net.handler.MaplePacketHandler;
import net.packet.attack.AttackData;
import net.packet.attack.AttackParser;

public class CloseRangeDamageHandler implements MaplePacketHandler {

	@Override
	public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		if(proxy.isHitCheatEnabled()){
			AttackData data = AttackParser.readAttackData(buf);
			
			List<MapleMonster> monsters = proxy.getMaplestory().getCurrentMap().getObjects(MapleMonster.class);
			
			data.numAttacked = 1;
			data.numDamage = 1;
			
			int damage = 50;
			
			int i = 0;
			for(MapleMonster monster : monsters){
				if(i > data.numAttacked){
					continue;
				}
				data.allDamage.put(monster.getObjectId(), Collections.singletonList(damage));
				i++;
			}
			
			result.setResultPayload(data.toPacketPayload());	
		}
		
		//result.setCancelled(true);
	}

	@Override
	public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		

		
	}

}
