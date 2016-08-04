package maplestory.map.life;

import lombok.Setter;
import maplestory.MapleCharacter;
import maplestory.MapleStory;

public class MapleFakeCharacter extends MapleCharacter {

	public MapleFakeCharacter(MapleStory ms, int objectId) {
		super(ms, objectId);
		setHair(30022);
		setFace(20000);
		
	}

}
