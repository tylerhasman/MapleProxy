package net.packet.handlers;

import io.netty.buffer.ByteBuf;
import maplestory.MapleStat;
import net.Proxy;
import net.handler.HandlePacketResult;
import net.handler.MaplePacketHandler;

public class UpdateStatHandler implements MaplePacketHandler {
	
	@Override
	public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
	}

	@Override
	public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
		boolean itemReaction = buf.readBoolean();
		
		int mask = buf.readInt();
		
		for(MapleStat stat : MapleStat.values()){
			
			if((mask & stat.getValue()) == stat.getValue()){
				
				int value = 0;
				
				if (stat.getValue() == 0x1) {
	                value = buf.readShort();
	            } else if (stat.getValue() <= 0x4) {
	                value = buf.readInt();
	            } else if (stat.getValue() < 0x20) {
	                value = buf.readByte();
	            } else if (stat.getValue() < 0xFFFF) {
	               value = buf.readShort();
	            } else {
	            	value = buf.readInt();
	            }
				
				if(stat == MapleStat.MESO){
					proxy.getMaplestory().getPlayer().setMeso(value);
					System.out.println("Set player meso to "+value);
				}
				
			}

			
		}
		
	}

}
