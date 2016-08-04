package net.packet.handlers;

import io.netty.buffer.ByteBuf;
import maplestory.npc.MTSButtonNpcConversation;
import net.Proxy;
import net.handler.HandlePacketResult;
import net.handler.MaplePacketHandler;
import net.packet.server.MapleNpcPacket;
import net.packet.server.MapleReAllowActionPacket;

public class OpenMTSHandler implements MaplePacketHandler {

	@Override
	public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
		result.setCancelled(true);
		
		proxy.getMaplestory().startNpcConversation(new MTSButtonNpcConversation(proxy, 2080005));
		
		proxy.getClient().sendPacket(new MapleReAllowActionPacket());
		
	}

	@Override
	public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
	}

}
