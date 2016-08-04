package net.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.util.Pair;
import net.MapleConnection;
import net.MapleConnectionFactory;

public class SocketConnectionFactory implements MapleConnectionFactory {
	
	private Map<Integer, ServerSocket> serverSockets;
	private Map<Integer, Pair<InetAddress, Integer>> targets;
	
	public SocketConnectionFactory() {
		serverSockets = new HashMap<>();
		targets = new HashMap<>();
	}
	
	@Override
	public MapleConnection createConnection(InetAddress host, int port) {
		try {
			if(host == null){
				throw new IllegalArgumentException("host cannot be null");
			}
			Socket socket = new Socket(host, port);
			
			socket.setSoTimeout(10000);
			
			return new SocketMapleConnection(socket, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public MapleConnection acceptConnection() {
		
		MapleConnection con = null;
		
		while(con == null){
			for(ServerSocket socket : new ArrayList<>(serverSockets.values())){
				con = acceptConnection(socket);
				if(con != null){
					System.out.println("Accepted connection from port "+socket.getLocalPort());
					return con;
				}
			}	
		}
		
		return null;
	}
	
	private MapleConnection acceptConnection(ServerSocket serverSocket){
		try {
			Socket con = serverSocket.accept();
			
			con.setSoTimeout(10000);
			
			return new SocketMapleConnection(con, true);
		} catch(SocketTimeoutException e){
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void startAcceptingConnectionsFrom(int port, InetAddress target, int targetPort) {
		if(target == null){
			throw new IllegalArgumentException("target cannot be null!");
		}
		if(!serverSockets.containsKey(port)){
			try {
				ServerSocket server = new ServerSocket(port);
				server.setSoTimeout(50);
				serverSockets.put(port, server);
				targets.put(port, new Pair<>(target, targetPort));
				System.out.println("now accepting connections on port "+port+" forwarding to "+target+":"+targetPort);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public InetAddress getTargetIp(int port) {
		return targets.get(port).getKey();
	}
	
	@Override
	public int getTargetPort(int port) {
		return targets.get(port).getValue();
	}
	
}
