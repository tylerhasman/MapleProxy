package net;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

import net.socket.SocketConnectionFactory;

public class Start {
	public static void main(String[] args) throws IOException {
		
		try(Scanner scanner = new Scanner(System.in)){
			
			
			String localHost = "localhost";
			
			System.out.println("Enter the ip to forward to: ");
			
			String host = scanner.nextLine();
			
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
	

}
