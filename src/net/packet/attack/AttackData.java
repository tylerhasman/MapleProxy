package net.packet.attack;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.ListSelectionEvent;

import lombok.Data;

@Data
public class AttackData {

	public int numAttacked;
	public int numDamage;
	public Map<Integer, List<Integer>> allDamage;
	public int skill;
	public byte speed;
	public byte stance;
	public byte display;
	public byte direction;
	
	public static AttackData generateBullshitData(){
		AttackData data = new AttackData();
		data.numAttacked = 1;
		data.numDamage = 1;
		data.allDamage = new HashMap<>();
		data.skill = 0;
		data.display = 0;
		data.direction = 6;
		data.stance = 0;
		data.speed = 5;
		
		return data;
	}
	
	public byte[] toPacketPayload(){
		
		ByteBuf buf = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);
		
		buf.writeByte(1);
		
		int numAttackedAndDamage = numAttacked << 4;
		numAttackedAndDamage += numDamage;
		
		buf.writeByte(numAttackedAndDamage);
		buf.writeInt(skill);
		
		buf.writeBytes(new byte[8]);
		
		buf.writeByte(display);
		buf.writeByte(direction);
		buf.writeByte(stance);
		
		buf.writeByte(0);
		
		buf.writeByte(speed);
		buf.writeBytes(new byte[4]);
		
		for(int oid : allDamage.keySet()){
			buf.writeInt(oid);
			
			buf.writeBytes(new byte[14]);
			
			for(int dmg : allDamage.get(oid)){
				buf.writeInt(dmg);
			}
		}
		
		buf.writeBytes(new byte[] {17, 9, 110, -72, 30, -3, 60, -2});
		
		buf = buf.capacity(buf.writerIndex());
		
		
		return buf.array();
		
	}
	
	

}
