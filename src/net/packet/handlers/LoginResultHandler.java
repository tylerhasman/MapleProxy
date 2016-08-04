package net.packet.handlers;

import java.util.Calendar;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import net.Proxy;
import net.handler.HandlePacketResult;
import net.handler.MaplePacketHandler;
import net.packet.MaplePacket;

public class LoginResultHandler implements MaplePacketHandler {

	@Override
	public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result) {

	}

	@Override
	public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		if(buf.readableBytes() > 6){
			buf.skipBytes(6);
			int id = buf.readInt();
			buf.skipBytes(4);
			String username = MaplePacket.readString(buf);
			buf.skipBytes(1);
			byte quietbanned = buf.readByte();
			long quiettime = buf.readLong();
			long creation = buf.readLong();
			buf.skipBytes(4);
			short pin = buf.readShort();
			if(quietbanned == 1){
				System.out.println("Quiet ban detected! "+quiettime);
			}
			System.out.println("ID "+id+" UN: "+username+" CR"+creation+" PIN "+pin);
		}else{
			byte reason = buf.readByte();
			System.out.println("Login failed reason is "+reason);
		}
	}

}
