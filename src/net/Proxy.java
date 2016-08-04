package net;

import java.net.InetAddress;
import java.net.UnknownHostException;

import lombok.Getter;
import lombok.Setter;
import maplestory.MapleStory;
import net.handler.HandlePacketResult;
import net.handler.MaplePacketHandlerManager;
import net.packet.MaplePacket;
import net.packet.handlers.ChangeChannelHandler;
import net.packet.handlers.ChangeMapHandler;
import net.packet.handlers.CharInfoHandler;
import net.packet.handlers.CharSelectedHandler;
import net.packet.handlers.CharSelectedWithPicHandler;
import net.packet.handlers.CloseRangeDamageHandler;
import net.packet.handlers.DeleteItemHandler;
import net.packet.handlers.DropItemHandler;
import net.packet.handlers.DropMesoHandler;
import net.packet.handlers.GeneralChatHandler;
import net.packet.handlers.HandshakeHandler;
import net.packet.handlers.KillMonsterHandler;
import net.packet.handlers.LoginResultHandler;
import net.packet.handlers.NoOpHandler;
import net.packet.handlers.NpcMoreTalkHandler;
import net.packet.handlers.OpenMTSHandler;
import net.packet.handlers.PickCharHandler;
import net.packet.handlers.PlayerMoveHandler;
import net.packet.handlers.RegisterPicHandler;
import net.packet.handlers.ServerIpHandler;
import net.packet.handlers.ServerListHandler;
import net.packet.handlers.SpawnMonsterHandler;
import net.packet.handlers.SpawnNpcHandler;
import net.packet.handlers.UpdateStatHandler;

public class Proxy implements Runnable{
	
	private static int nextId = 0;
	
	@Getter
	private MapleConnection client, server;
	@Getter
	private MapleConnectionFactory connectionFactory;
	
	@Getter
	private MaplePacketHandlerManager packetHandlers;
	
	@Getter
	private MapleStory maplestory;
	
	@Getter
	private InetAddress localWAN;
	
	@Getter @Setter
	private boolean hitCheatEnabled = false;
	
	@Getter @Setter
	private boolean spawnKillEnabled = false;
	@Getter @Setter
	private int spawnKillDamage = 10;
	
	@Getter @Setter
	private boolean vacEnabled = false;
	
	@Getter
	private final int id;
	
	public Proxy(MapleConnectionFactory factory, String localWAN) throws UnknownHostException{
		this.connectionFactory = factory;
		maplestory = new MapleStory(this);
		packetHandlers = new MaplePacketHandlerManager();
		initializeHandlers();
		this.localWAN = InetAddress.getByName(localWAN);
		id = nextId++;
	}
	
	public void initializeHandlers(){
		packetHandlers.reset();
		packetHandlers.addHandler(ServerOpCode.HANDSHAKE, new HandshakeHandler());
		packetHandlers.addHandler(ServerOpCode.LOGIN_STATUS, new LoginResultHandler());
		packetHandlers.addHandler(ServerOpCode.PING, new NoOpHandler());
		packetHandlers.addHandler(ClientOpCode.PONG, new NoOpHandler());
		packetHandlers.addHandler(ServerOpCode.SERVERLIST, new ServerListHandler());
		packetHandlers.addHandler(ServerOpCode.SERVER_IP, new ServerIpHandler());
		packetHandlers.addHandler(ServerOpCode.CHANGE_CHANNEL, new ChangeChannelHandler());
		packetHandlers.addHandler(ClientOpCode.NPC_ACTION, new NoOpHandler());
		packetHandlers.addHandler(ServerOpCode.NPC_ACTION, new NoOpHandler());
		packetHandlers.addHandler(ClientOpCode.GENERAL_CHAT, new GeneralChatHandler());
		packetHandlers.addHandler(ClientOpCode.MOVE_PLAYER, new PlayerMoveHandler());
		packetHandlers.addHandler(ServerOpCode.MOVE_PLAYER, new PlayerMoveHandler());
		packetHandlers.addHandler(ServerOpCode.SET_FIELD, new CharInfoHandler());
		packetHandlers.addHandler(ServerOpCode.SPAWN_NPC, new SpawnNpcHandler());
		packetHandlers.addHandler(ClientOpCode.CHAR_SELECT, new CharSelectedHandler());
		packetHandlers.addHandler(ClientOpCode.CHAR_SELECT_WITH_PIC, new CharSelectedWithPicHandler());
		packetHandlers.addHandler(ClientOpCode.PICK_ALL_CHAR, new PickCharHandler());
		packetHandlers.addHandler(ClientOpCode.REGISTER_PIC, new RegisterPicHandler());
		packetHandlers.addHandler(ClientOpCode.MOVE_SUMMON, new NoOpHandler());
		packetHandlers.addHandler(ClientOpCode.MOVE_LIFE, new NoOpHandler());
		packetHandlers.addHandler(ServerOpCode.MOVE_MONSTER_RESPONSE, new NoOpHandler());
		packetHandlers.addHandler(ServerOpCode.SPAWN_MONSTER, new SpawnMonsterHandler());
		packetHandlers.addHandler(ClientOpCode.CLOSE_RANGE_ATTACK, new CloseRangeDamageHandler());
		packetHandlers.addHandler(ServerOpCode.KILL_MONSTER, new KillMonsterHandler());
		packetHandlers.addHandler(ServerOpCode.DROP_ITEM_FROM_MAPOBJECT, new DropItemHandler());
		packetHandlers.addHandler(ServerOpCode.REMOVE_ITEM_FROM_MAP, new DeleteItemHandler());
		packetHandlers.addHandler(ClientOpCode.CHANGE_MAP, new ChangeMapHandler());
		packetHandlers.addHandler(ServerOpCode.STAT_CHANGED, new UpdateStatHandler());
		packetHandlers.addHandler(ClientOpCode.MESO_DROP, new DropMesoHandler());
		packetHandlers.addHandler(ClientOpCode.ENTER_MTS, new OpenMTSHandler());
		packetHandlers.addHandler(ClientOpCode.NPC_TALK_MORE, new NpcMoreTalkHandler());
	}

	public void acceptClient(){
		client = connectionFactory.acceptConnection();
	}
	
	public void connectToServer(){
		if(server == null)
			server = connectionFactory.createConnection(connectionFactory.getTargetIp(client.getPort()), connectionFactory.getTargetPort(client.getPort()));
	}
	
	@Override
	public void run() {
		if(client == null){
			throw new IllegalStateException("Please call acceptClient() first");
		}
		
		connectToServer();
		
		if(server == null){
			System.err.println("Could not connect to server!");
			return;
		}
		
		long lastRead = System.currentTimeMillis();
		
		while(client.isReady() && server.isReady()){
			try{
				MaplePacket serverPacket = server.readPacket();
				MaplePacket clientPacket = client.readPacket();
				
				if(serverPacket != null){
					HandlePacketResult result = packetHandlers.handlePacket(this, serverPacket);
					
					if(result.isNewPayload()){
						serverPacket.setPayload(result.getResultPayload());
					}
					
					if(!result.isCancelled()){
						client.sendPacket(serverPacket);
					}
					lastRead = System.currentTimeMillis();
					
				}
				
				if(clientPacket != null){
					
					HandlePacketResult result = packetHandlers.handlePacket(this, clientPacket);
					
					if(result.isNewPayload()){
						clientPacket.setPayload(result.getResultPayload());
					}
					
					if(!result.isCancelled()){
						server.sendPacket(clientPacket);
					}
					lastRead = System.currentTimeMillis();
				}
				
				if(System.currentTimeMillis() - lastRead > 100){
					server.sendPacket(new MaplePacket(ClientOpCode.PONG.getCode(), new byte[0], false));
					client.sendPacket(new MaplePacket(ServerOpCode.PING.getCode(), new byte[0], true));
					lastRead = System.currentTimeMillis();
				}
				
			}catch(Exception e){
				e.printStackTrace();
				break;
			}
			
		}
		
		server.closeConnection();
		client.closeConnection();
	
	}
	
}
