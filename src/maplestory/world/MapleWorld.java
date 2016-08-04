package maplestory.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import maplestory.MapleChannel;

public class MapleWorld {

	@Getter
	private final String name;
	@Getter
	private final byte id;
	private List<MapleChannel> channels;
	
	@Getter
	private MapleWorldInfo worldInfo;
	
	MapleWorld(String name, byte id) {
		this.name = name;
		this.id = id;
		channels = new ArrayList<>();
	}
	
	public List<MapleChannel> getChannels() {
		return Collections.unmodifiableList(channels);
	}
	
	public static MapleWorld create(String name, byte id, List<MapleChannel> channels, MapleWorldInfo info){
		MapleWorld world = new MapleWorld(name, id);
		world.worldInfo = info;
		world.channels = channels;
		
		return world;
	}
	
}
