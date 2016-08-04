package maplestory.map.life;

import java.util.Collections;

import maplestory.MapleStory;
import maplestory.map.MapleMapObject;
import net.ClientOpCode;
import net.Proxy;
import net.packet.MaplePacket;
import net.packet.attack.AttackData;
import lombok.Getter;

public class MapleMonster extends MapleMapObject {

	@Getter
	private int refId;
	
	public MapleMonster(MapleStory ms, int refId, int objectId) {
		super(ms, objectId);
		this.refId = refId;
	}
	
	@Override
	public String toString() {
		return "Monster "+String.valueOf(refId);
	}

	public void damage(Proxy proxy, int amount) {
		AttackData data = AttackData.generateBullshitData();
		data.allDamage.put(getObjectId(), Collections.singletonList(amount));
		
		MaplePacket pack = new MaplePacket(ClientOpCode.CLOSE_RANGE_ATTACK.getCode(), data.toPacketPayload(), false);
		
		proxy.getServer().sendPacket(pack);
	}

}
