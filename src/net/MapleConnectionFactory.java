package net;

import java.net.InetAddress;

public interface MapleConnectionFactory {

	public MapleConnection createConnection(InetAddress host, int port);
	
	public MapleConnection acceptConnection();
	
	public void startAcceptingConnectionsFrom(int port, InetAddress target, int targetPort);
	
	public InetAddress getTargetIp(int port);
	
	public int getTargetPort(int port);
	
}
