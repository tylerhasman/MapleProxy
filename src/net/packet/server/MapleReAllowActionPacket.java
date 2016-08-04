package net.packet.server;

import net.ServerOpCode;
import net.packet.MaplePacket;

public class MapleReAllowActionPacket extends MaplePacket {

	public MapleReAllowActionPacket() {
		super(ServerOpCode.STAT_CHANGED.getCode());
		
		setPayload(new byte[] {1, 0, 0, 0, 0});
	}

}
