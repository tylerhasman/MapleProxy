package maplestory.npc;

import lombok.AccessLevel;
import lombok.Getter;
import net.Proxy;
import net.packet.server.MapleNpcPacket;
import net.packet.server.MapleNpcPacket.NpcTalkType;

public abstract class ProxyNpcConversation {

	@Getter(value=AccessLevel.PACKAGE)
	private Proxy proxy;
	
	private int npcId;
	
	public ProxyNpcConversation(Proxy proxy, int npcId) {
		this.proxy = proxy;
		this.npcId = npcId;
	}
	
	public void sendOk(String message){
		proxy.getClient().sendPacket(new MapleNpcPacket(npcId, message, NpcTalkType.OK));
	}
	
	public void sendSimple(String message){
		proxy.getClient().sendPacket(new MapleNpcPacket(npcId, message, NpcTalkType.SIMPLE));
	}
	
	public void dispose(){
		proxy.getMaplestory().closeNpc();
	}
	
	public abstract void start();
	
	public void action(int mode, int type, int selection){
		dispose();
	}
	
}
