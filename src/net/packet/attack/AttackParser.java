package net.packet.attack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.netty.buffer.ByteBuf;

public class AttackParser {

	public static AttackData readAttackData(ByteBuf buf){
		
		AttackData data = new AttackData();
		
		buf.skipBytes(1);
		int numAttackedAndDamage = buf.readByte();
		data.numAttacked = (numAttackedAndDamage >>> 4) & 0xF;
		data.numDamage = numAttackedAndDamage & 0xF;
		data.allDamage = new HashMap<>();
		data.skill = buf.readInt();
		
		buf.skipBytes(8);
		
		data.display = buf.readByte();
		data.direction = buf.readByte();
		data.stance = buf.readByte();
		
		buf.skipBytes(1);
		data.speed = buf.readByte();
		buf.skipBytes(4);
		
		for(int i = 0; i < data.numAttacked;i++){
			int oid = buf.readInt();
			buf.skipBytes(14);
			List<Integer> damageNumbers = new ArrayList<>();
			for(int j = 0; j < data.numDamage;j++){
				int damage = buf.readInt();
				damageNumbers.add(damage);
			}
			data.allDamage.put(oid, damageNumbers);
		}
		
		return data;
		
	}
	
}
