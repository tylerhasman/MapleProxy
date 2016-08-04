package net.packet.handlers;

import io.netty.buffer.ByteBuf;
import maplestory.npc.ProxyNpcConversation;
import net.Proxy;
import net.handler.HandlePacketResult;
import net.handler.MaplePacketHandler;

public class NpcMoreTalkHandler implements MaplePacketHandler {

	@Override
	public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
		ProxyNpcConversation convo = proxy.getMaplestory().getCurrentProxyNpc();
		
		if(convo == null){
			return;
		}
		
		byte lastMsg = buf.readByte();
		byte action = buf.readByte();
		
		if(lastMsg == 2){
			//GetText
		}else{
			int selection = -1;
			if(buf.readableBytes() >= 4){
				selection = buf.readInt();
			}else if(buf.readableBytes() > 0){
				selection = buf.readByte();
			}
			
			convo.action(action, lastMsg, selection);
		}
		
	}

	@Override
	public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
	}

}
