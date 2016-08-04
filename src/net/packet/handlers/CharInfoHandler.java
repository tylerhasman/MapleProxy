package net.packet.handlers;

import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import maplestory.MapleCharacter;
import net.Proxy;
import net.handler.HandlePacketResult;
import net.handler.MaplePacketHandler;

public class CharInfoHandler implements MaplePacketHandler {

	@Override
	public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
	}

	@Override
	public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		int channelId = buf.readInt();
		
		if(buf.capacity() == 25){
			
			buf.skipBytes(9);
			int mapId = buf.readInt();
			/*byte spawn = buf.readByte();
			short hp = buf.readShort();
			buf.skipBytes(1);
			long time = buf.readLong();*/
			
			proxy.getMaplestory().changeMap(mapId);
			
		}else{
			buf.skipBytes(1);
			buf.skipBytes(2);
			for(int i = 0; i < 3;i++){
				buf.skipBytes(4);
			}
			
			MapleCharacter chr = new MapleCharacter(proxy.getMaplestory(), buf);

			proxy.getMaplestory().changeMap(chr.getMapId());
			proxy.getMaplestory().setPlayer(chr);
			System.out.println("Maple Character selected (channel "+channelId+"): "+chr.getName()+" mapid "+chr.getMapId());
		}
		
	}

}
