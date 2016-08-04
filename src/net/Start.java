package net;

import java.io.IOException;
import java.net.InetAddress;
import net.socket.SocketConnectionFactory;

public class Start {
	//46.105.54.50
	//158.69.123.139 - MapleDestiny
	//Remote Server 149.56.15.132

	public static void main(String[] args) throws IOException {
		
		String localHost = args[0];
		
		System.out.println("Using local host "+localHost);
		
		String host = "localhost";
		
		SocketConnectionFactory scf = new SocketConnectionFactory();
		
		scf.startAcceptingConnectionsFrom(8480, InetAddress.getByName(host), 8484);
		
		while(true){
			Proxy proxy = new Proxy(scf, localHost);
			
			proxy.acceptClient();
			
			Thread thread = new Thread(proxy);
			thread.setDaemon(true);
			thread.start();
		}
		
		
	}
	

}
