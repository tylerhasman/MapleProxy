package net.packet.handlers;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import maplestory.MapleChannel;
import maplestory.world.MapleWorld;
import maplestory.world.MapleWorldInfo;
import net.Proxy;
import net.handler.HandlePacketResult;
import net.handler.MaplePacketHandler;
import net.packet.MaplePacket;
import net.packet.server.MapleServerListPacket;

public class ServerListHandler implements MaplePacketHandler {

	@Override
	public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
	}

	@Override
	public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
		MapleWorld world = null;
		List<MapleChannel> channels = new ArrayList<>();
		MapleWorldInfo info = null;
		
		byte serverId = buf.readByte();
		
		if(serverId == -1){
			return;
		}
		String name   = MaplePacket.readString(buf);
		byte flag     = buf.readByte();
		String event  = MaplePacket.readString(buf);
		event += "\r\nPacket Editor is working!";
		flag = 1;
		/*byte rateMod  = buf.readByte();
		byte expEvent = buf.readByte();
		byte rateMod2 = buf.readByte();
		byte dropRate = buf.readByte();*/
		buf.skipBytes(4);//Skip the 4 bytes above I guess...
		buf.skipBytes(1);
		byte chanSize = buf.readByte();
		for(int i = 0; i < chanSize;i++){
			String chanName = MaplePacket.readString(buf);
			int population = buf.readInt();
			buf.skipBytes(1);
			short id = buf.readShort();
			MapleChannel channel = new MapleChannel(chanName, (byte) id, population);
			channels.add(channel);
		}
		buf.skipBytes(2);
		
		info = new MapleWorldInfo(flag, event);
		
		world = MapleWorld.create(name, serverId, channels, info);
		
		proxy.getMaplestory().addWorld(world);
		
		MapleServerListPacket packet = new MapleServerListPacket(world);
		
		result.setResultPayload(packet.getPayload());
	}

}
