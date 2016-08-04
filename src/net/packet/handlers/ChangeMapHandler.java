package net.packet.handlers;

import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import maplestory.MapleByteBufWrapper;
import net.Proxy;
import net.handler.HandlePacketResult;
import net.handler.MaplePacketHandler;

public class ChangeMapHandler implements MaplePacketHandler {

	@Override
	public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
		if(buf.readableBytes() == 0){
			return;//Cash shop
		}
		
		buf.skipBytes(1);
		
		int target = buf.readInt();
		
		String startwp = new MapleByteBufWrapper(buf).readMapleString();
		buf.skipBytes(1);
		
		System.out.println("Using portal "+startwp);
		
		System.out.println(buf.readableBytes());
		
		System.out.println(Arrays.toString(buf.array()));
		
	}

	@Override
	public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result) {

	}

}
