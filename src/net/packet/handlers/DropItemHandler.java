package net.packet.handlers;

import java.awt.Point;

import io.netty.buffer.ByteBuf;
import maplestory.map.MapleDroppedItem;
import net.Proxy;
import net.handler.HandlePacketResult;
import net.handler.MaplePacketHandler;

public class DropItemHandler implements MaplePacketHandler {

	@Override
	public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
	}

	@Override
	public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
		byte mod = buf.readByte();
		int oid = buf.readInt();
		boolean mesoDrop = buf.readBoolean();
		int itemID = buf.readInt();
		int owner = buf.readInt();
		byte dropType = buf.readByte();
		short x = buf.readShort();
		short y = buf.readShort();
		
		
		MapleDroppedItem item = new MapleDroppedItem(proxy.getMaplestory(), oid);
		item.setItemId(itemID);
		item.setOwner(owner);
		item.setX(x);
		item.setY(y);
		
		proxy.getMaplestory().getCurrentMap().addObject(item);
	
		if(proxy.isVacEnabled()){
			for(MapleDroppedItem drops : proxy.getMaplestory().getCurrentMap().getObjects(MapleDroppedItem.class)){
				drops.pickup(proxy);
			}
		}
		
		
	}

}
