package maplestory.map;

import java.awt.Point;
import java.util.Collections;

import maplestory.MapleStory;
import net.Proxy;
import net.packet.client.MapleMoveClientPlayerPacket;
import net.packet.client.MaplePickupItemPacket;
import net.packet.movement.AbsoluteLifeMovement;
import lombok.Getter;
import lombok.Setter;


public class MapleDroppedItem extends MapleMapObject {

	@Getter @Setter
	private int itemId;
	
	@Getter @Setter
	private int owner;
	
	public MapleDroppedItem(MapleStory ms, int objectId) {
		super(ms, objectId);
	}
	
	public void pickup(Proxy proxy){
		if(distance(proxy.getMaplestory().getPlayer()) > 50){
			proxy.getServer().sendPacket(new MapleMoveClientPlayerPacket(Collections.singletonList(new AbsoluteLifeMovement((byte)0, new Point(getX(), getY()), 0, (byte) 2))));
		}
		proxy.getServer().sendPacket(new MaplePickupItemPacket(this));
	}
	
	@Override
	public String toString() {
		return "MapleDroppedItem "+itemId+" "+getObjectId();
	}

}
