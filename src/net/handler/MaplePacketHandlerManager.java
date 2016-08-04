package net.handler;

import io.netty.buffer.ByteBuf;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.ClientOpCode;
import net.OpCode;
import net.Proxy;
import net.ServerOpCode;
import net.packet.MaplePacket;

public class MaplePacketHandlerManager {
	
	private Map<OpCode, MaplePacketHandler> handlers;
	
	public MaplePacketHandlerManager() {
		handlers = new HashMap<>();
	}
	
	public void addHandler(OpCode code, MaplePacketHandler handler){
		handlers.put(code, handler);
	}
	
	public HandlePacketResult handlePacket(Proxy proxy, MaplePacket packet){
		MaplePacketHandler handler = handlers.getOrDefault(packet.getOpCode(), getDefaultHandler(packet));
		
		HandlePacketResult result = new HandlePacketResult();
		
		if(packet.isFromServer()){
			handler.onSendToClient(proxy, packet.toBuffer(), result);
		}else{
			handler.onSendToServer(proxy, packet.toBuffer(), result);
		}
		
		return result;
		
	}
	
	private MaplePacketHandler getDefaultHandler(MaplePacket packet){
		return new MaplePacketHandler() {
			
			@Override
			public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
				//System.out.println("Unhandled packet from client "+packet);
			}
			
			@Override
			public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
				//System.out.println("Unhandled packet from server "+packet);
			}
			
		};
	}

	public void reset() {
		handlers.clear();
	}
	
}
