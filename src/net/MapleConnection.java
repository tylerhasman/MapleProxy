package net;

import java.net.SocketAddress;

import net.packet.MaplePacket;
import security.MapleAESOFB;

public interface MapleConnection {

	public void sendPacket(MaplePacket packet);
	
	public MaplePacket readPacket();

	public void closeConnection();
	
	public MapleAESOFB getSendCrypto();
	
	public MapleAESOFB getRecvCrypto();
	
	public SocketAddress getRemoteAddress();
	
	public int getPort();
	
	public boolean isReady();
	
	public void setCryptKeys(byte[] send, byte[] recv, int version);
	
}
