package maplestory.npc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteOrder;
import java.util.List;

import maplestory.map.life.MapleNpc;
import net.ClientOpCode;
import net.Proxy;
import net.packet.MaplePacket;

public class MTSButtonNpcConversation extends ProxyNpcConversation {
	
	int status = -1;
	int lastSelection = -1;
	List<MapleNpc> npcs = null;
	
	public MTSButtonNpcConversation(Proxy proxy, int npcId) {
		super(proxy, npcId);
	}

	@Override
	public void start() {
		sendSimple("What would you like to do?\r\n#b#L0#Open Cheat Menu#l\r\n#L1#Open MTS#l");
	}
	
	@Override
	public void action(int mode, int type, int selection) {
		if(mode == 0){
			dispose();
			return;
		}
		status++;
		if(status == 0){
			if(selection == 0){
				sendSimple("Current Cheats (Click to Toggle)\r\n"
						+ "#L0#All Monster Hit "+format(getProxy().isHitCheatEnabled())+"#l\r\n\r\n#k"
								+ "#L1#Spawn Kill is "+format(getProxy().isSpawnKillEnabled())+"#l\r\n\r\n#k"
										+ "#L2#Safe Map Vac is "+format(getProxy().isVacEnabled())+"#l\r\n"
												+ "#k#L3#Open NPC Menu#l");
			}else{
				dispose();
				getProxy().getServer().sendPacket(new MaplePacket(ClientOpCode.ENTER_MTS.getCode(), new byte[0], false));
			}
		}else if(status == 1){
			if(selection == 0){
				getProxy().setHitCheatEnabled(!getProxy().isHitCheatEnabled());
			}else if(selection == 1){
				getProxy().setSpawnKillEnabled(!getProxy().isSpawnKillEnabled());
			}else if(selection == 2){
				getProxy().setVacEnabled(!getProxy().isVacEnabled());
			}else if(selection == 3){
				String s = "Select an NPC on the map to talk to\r\n\r\n#b";
				
				npcs = getProxy().getMaplestory().getCurrentMap().getObjects(MapleNpc.class);
				
				int i = 0;
				
				for(MapleNpc npc : npcs){
					
					s += "#L"+i+"#"+npc.getRefId()+"#l\r\n";
					i++;
				}
				
				sendSimple(s);
				lastSelection = selection;
				return;
			}
			status = -1;
			action(1, 0, 0);
		}else if(status == 2){
			if(lastSelection == 3){

				dispose();
				
				if(selection >= 0){
					ByteBuf buf = Unpooled.buffer(4, 4).order(ByteOrder.LITTLE_ENDIAN);
					
					buf.writeInt(npcs.get(selection).getObjectId());
					
					getProxy().getServer().sendPacket(new MaplePacket(ClientOpCode.NPC_TALK.getCode(), buf.array(), false));
				}
				
			}
		}
		
	}
	
	private String format(boolean b){
		return b ? "#bENABLED" : "#rDISABLED";
	}
	
}
