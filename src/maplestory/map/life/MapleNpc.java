package maplestory.map.life;

import lombok.Getter;
import lombok.Setter;
import maplestory.MapleStory;
import maplestory.map.MapleMapObject;

public class MapleNpc extends MapleMapObject {
	
	@Getter @Setter
	private int fh, rx0, rx1;
	@Getter @Setter
	private boolean f;
	@Getter
	private int refId;
	
	public MapleNpc(MapleStory ms, int refId, int objectId) {
		super(ms, objectId);
		this.refId = refId;
	}
	
	@Override
	public String toString() {
		return "NPC "+String.valueOf(refId);
	}

}
