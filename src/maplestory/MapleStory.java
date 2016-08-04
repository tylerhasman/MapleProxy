package maplestory;

import java.util.ArrayList;
import java.util.List;

import net.Proxy;
import net.packet.server.MapleServerMessagePacket;
import lombok.Getter;
import lombok.Setter;
import maplestory.map.MapleMap;
import maplestory.npc.MTSButtonNpcConversation;
import maplestory.npc.ProxyNpcConversation;
import maplestory.world.MapleWorld;

public class MapleStory {

	private List<MapleWorld> worlds;
	@Getter
	private Proxy proxy;
	
	@Getter
	private MapleMap currentMap;
	
	@Getter @Setter
	private MapleCharacter player;
	
	@Getter
	private ProxyNpcConversation currentProxyNpc;
	
	public MapleStory(Proxy proxy) {
		worlds = new ArrayList<>();
		currentMap = null;
		this.proxy = proxy;
	}
	
	public void addWorld(MapleWorld world){
		worlds.add(world);
		System.out.println("Added world "+world.getName()+" "+(world.getId()));
	}
	
	public void changeMap(int id){
		currentMap = new MapleMap(id, this);
	}
	
	public void sendMessage(MessageType type, String message){
		MapleServerMessagePacket packet = new MapleServerMessagePacket(type, message);
		
		proxy.getClient().sendPacket(packet);
	}

	public void startNpcConversation(ProxyNpcConversation conversation) {
		currentProxyNpc = conversation;
		
		currentProxyNpc.start();
	}

	public void closeNpc() {
		currentProxyNpc = null;
	}
	
}
