package maplestory.map;

import java.awt.Point;

import lombok.Getter;
import lombok.Setter;
import maplestory.MapleStory;

public class MapleMapObject {

	@Getter @Setter
	private int objectId;
	
	@Getter
	private final MapleStory maplestory;
	
	public MapleMapObject(MapleStory ms, int objectId) {
		this.objectId = objectId;
		maplestory = ms;
	}
	
	@Getter @Setter
	private int x, y;
	
	public double distance(MapleMapObject obj){
		return distance(obj.x, obj.y);
	}
	
	public double distance(int x2, int y2){
		double distance = Math.sqrt(Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2));
		
		return distance;
	}

	public void positionUpdate(Point position) {
		x = position.x;
		y = position.y;
	}
	
}
