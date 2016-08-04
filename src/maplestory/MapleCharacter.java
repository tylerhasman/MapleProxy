package maplestory;

import java.awt.Point;

import net.Proxy;
import net.packet.MaplePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import maplestory.map.MapleDroppedItem;
import maplestory.map.MapleMapObject;
import maplestory.map.life.MapleFakeCharacter;

public class MapleCharacter extends MapleMapObject {

	@Getter @Setter
	private String name;
	
	@Getter
	private boolean male;
	
	@Getter @Setter(value=AccessLevel.PROTECTED)
	private int skin, face, hair;
	
	@Getter
	private int level, job;
	@Getter
	private int strength, dexterity, luck, intelligence;
	
	@Getter
	private int hp, mp, maxHp, maxMp;
	
	@Getter
	private int ap, sp;
	
	@Getter
	private int exp;

	@Getter
	private int fame;
	
	@Getter
	private int mapId;
	
	@Getter @Setter
	private int meso;
	
	public MapleCharacter(MapleStory ms, int id) {
		super(ms, id);
	}
	
	public MapleCharacter(MapleStory ms, ByteBuf buf) {
		this(ms, buf, true);
	}
	
	public MapleCharacter(MapleStory ms, ByteBuf buf, boolean skipBytes){
		super(ms, -1);
		loadFromBuffer(buf, skipBytes);
	}
	
	@Override
	public void positionUpdate(Point position) {
		super.positionUpdate(position);
		for(MapleDroppedItem item : getMaplestory().getCurrentMap().getObjects(MapleDroppedItem.class)){
			if(item.distance(this) <= 20){
				item.pickup(getMaplestory().getProxy());
			}
		}

	}
	
	public void loadFromBuffer(ByteBuf buf, boolean skipBytes){
		
		if(skipBytes){
			buf.skipBytes(8);
			buf.skipBytes(1);	
		}

		loadStatsFromBuffer(buf);
	}
	
	public void loadStatsFromBuffer(ByteBuf buf){
		
		setObjectId(buf.readInt());
		name = MaplePacket.readString(buf, 13).trim();
		male = (buf.readByte() == 0);
		skin = buf.readByte();
		face = buf.readInt();
		hair = buf.readInt();
		
		for(int i = 0; i < 3;i++){
			buf.skipBytes(8);//Pets
		}
		
		level = buf.readByte();
		job = buf.readShort();
		strength = buf.readShort();
		dexterity = buf.readShort();
		intelligence = buf.readShort();
		luck = buf.readShort();
		
		hp = buf.readShort();
		maxHp = buf.readShort();
		mp = buf.readShort();
		maxMp = buf.readShort();
		
		ap = buf.readShort();
		sp = buf.readShort();
		
		exp = buf.readInt();
		
		fame = buf.readShort();
		
		buf.skipBytes(4);
		mapId = buf.readInt();
	}

}
