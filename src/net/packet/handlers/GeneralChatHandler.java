package net.packet.handlers;

import java.awt.Point;
import java.util.Collections;
import java.util.Random;

import com.sun.javafx.scene.control.skin.ComboBoxPopupControl.FakeFocusTextField;

import io.netty.buffer.ByteBuf;
import maplestory.MapleCharacter;
import maplestory.MessageType;
import maplestory.map.MapleMapObject;
import maplestory.map.life.MapleFakeCharacter;
import maplestory.map.life.MapleNpc;
import net.Proxy;
import net.handler.HandlePacketResult;
import net.handler.MaplePacketHandler;
import net.packet.MaplePacket;
import net.packet.client.MapleChangeMapPacket;
import net.packet.client.MapleDropMesoPacket;
import net.packet.client.MapleMoveClientPlayerPacket;
import net.packet.movement.AbsoluteLifeMovement;
import net.packet.server.MapleServerMessagePacket;

public class GeneralChatHandler implements MaplePacketHandler {

	@Override
	public void onSendToServer(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		String text = MaplePacket.readString(buf);
		MapleCharacter chr = proxy.getMaplestory().getPlayer();
		Random r = new Random();
		
		if(text.startsWith("~")){
			
			String[] args = text.substring(1).split(" ");
			if(args.length == 0){
				return;
			}
			
			if(args[0].equalsIgnoreCase("refresh")){
				proxy.initializeHandlers();
				proxy.getMaplestory().sendMessage(MessageType.LIGHT_BLUE_TEXT, "Handlers reinitialized");
			}else if(args[0].equalsIgnoreCase("spawnnpc")){
				int id = Integer.parseInt(args[1]);
				
				MapleNpc npc = new MapleNpc(proxy.getMaplestory(), id, proxy.getMaplestory().getCurrentMap().getNextObjectId());
				npc.setX(chr.getX());
				npc.setY(chr.getY());
				
				proxy.getMaplestory().getCurrentMap().addObject(npc);
				
				proxy.getMaplestory().sendMessage(MessageType.LIGHT_BLUE_TEXT, "Spawned npc "+id);
			}else if(args[0].equalsIgnoreCase("objects")){
				
				for(MapleMapObject life : proxy.getMaplestory().getCurrentMap().getObjects()){
					double distance = life.distance(proxy.getMaplestory().getPlayer());
					proxy.getMaplestory().sendMessage(MessageType.NOTICE, life.getObjectId()+" "+life.toString()+" DFP: "+distance);
				}
			}else if(args[0].equalsIgnoreCase("portal")){
				String portal = args[1];
				
				proxy.getServer().sendPacket(new MapleChangeMapPacket(portal));
			}else if(args[0].equalsIgnoreCase("spawnfakeplayer")){
				MapleFakeCharacter fake = new MapleFakeCharacter(proxy.getMaplestory(), chr.getMaplestory().getCurrentMap().getNextObjectId());
				
				fake.setName("Sunnorca");
				fake.setX(chr.getX());
				fake.setY(chr.getY());
				
				proxy.getMaplestory().getCurrentMap().addObject(fake);
			}else if(args[0].equalsIgnoreCase("drop")){
				proxy.getServer().sendPacket(new MapleDropMesoPacket(50));
			}else if(args[0].equalsIgnoreCase("pos")){
				proxy.getClient().sendPacket(new MapleServerMessagePacket(MessageType.NOTICE, "Your position is "+chr.getX()+" "+chr.getY()));
			}else if(args[0].equalsIgnoreCase("hitcheat")){
				proxy.setHitCheatEnabled(!proxy.isHitCheatEnabled());
				proxy.getMaplestory().sendMessage(MessageType.POPUP, "Hit Cheat "+(proxy.isHitCheatEnabled() ? "Enabled" : "Disabled"));
			}else if(args[0].equalsIgnoreCase("spawnkill")){
				
				int damage = proxy.getSpawnKillDamage();
				
				if(args.length == 2){
					try{
						damage = Integer.parseInt(args[1]);
					}catch(NumberFormatException e){
					}
				}
				
				proxy.setSpawnKillDamage(damage);
				
				proxy.setSpawnKillEnabled(!proxy.isSpawnKillEnabled());
				proxy.getMaplestory().sendMessage(MessageType.POPUP, "Spawn Kill Cheat "+(proxy.isSpawnKillEnabled() ? "Enabled" : "Disabled"));
			}else if(args[0].equalsIgnoreCase("vac")){
				proxy.setVacEnabled(!proxy.isVacEnabled());
				proxy.getMaplestory().sendMessage(MessageType.POPUP, "Vac "+(proxy.isVacEnabled() ? "Enabled" : "Disabled"));
			}else if(args[0].equalsIgnoreCase("quit")){
				proxy.getClient().closeConnection();
			}else{
				proxy.getMaplestory().sendMessage(MessageType.PINK_TEXT, "Unknown proxy command "+args[0]);
			}
			
			result.setCancelled(true);
		}
	}

	@Override
	public void onSendToClient(Proxy proxy, ByteBuf buf, HandlePacketResult result) {
		
	}

}
