package maplestory.map;

import java.util.ArrayList;
import java.util.List;

import net.packet.server.MapleSpawnNpcPacket;
import net.packet.server.MapleSpawnPlayerPacket;
import lombok.Getter;
import maplestory.MapleStory;
import maplestory.map.life.MapleFakeCharacter;
import maplestory.map.life.MapleNpc;

public class MapleMap {

	@Getter
	private List<MapleMapObject> objects;
	@Getter
	private final int id;
	private MapleStory ms;
	private int nextObjectId;
	
	public MapleMap(int id, MapleStory ms) {
		objects = new ArrayList<>();
		this.id = id;
		this.ms = ms;
		nextObjectId = Integer.MAX_VALUE;
	}

	@SuppressWarnings("unchecked")
	public <T extends MapleMapObject> List<T> getObjects(Class<T> lifeClass){
		
		List<T> life = new ArrayList<>();
		
		for(MapleMapObject l : objects){
			if(lifeClass.isInstance(l)){
				life.add((T) l);
			}
		}
		
		return life;
		
	}
	
	public void addObject(MapleMapObject life){
		removeObject(life.getObjectId());
		if(life instanceof MapleNpc){
			addNpc((MapleNpc) life);
		}else if(life instanceof MapleFakeCharacter){
			addFakeCharacter((MapleFakeCharacter) life);
		}
		objects.add(life);
	}
	
	private void addFakeCharacter(MapleFakeCharacter fake) {
		ms.getProxy().getClient().sendPacket(new MapleSpawnPlayerPacket(fake));
	}

	private void addNpc(MapleNpc npc){
		ms.getProxy().getClient().sendPacket(new MapleSpawnNpcPacket(npc));
	}

	public int getNextObjectId() {
		return nextObjectId--;
	}

	public void removeObject(int oid) {
		objects.removeIf(life -> life.getObjectId() == oid);
	}
}
