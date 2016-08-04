package net.packet.server;

import java.nio.ByteOrder;
import java.util.Random;

import io.netty.buffer.Unpooled;
import maplestory.MapleByteBufWrapper;
import maplestory.MapleCharacter;
import net.ServerOpCode;
import net.packet.MaplePacket;

public class MapleSpawnPlayerPacket extends MaplePacket {

	public MapleSpawnPlayerPacket(MapleCharacter chr) {
		super(ServerOpCode.SPAWN_PLAYER.getCode());
		
		MapleByteBufWrapper buf = new MapleByteBufWrapper(Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN));
		
		int random = new Random().nextInt();
		
		buf.writeInt(chr.getObjectId());
		buf.writeByte(chr.getLevel());
		buf.writeMapleString(chr.getName());
		buf.writeMapleString("FakePlayer");//Guild Name
		buf.writeBytes(new byte[6]);
		buf.writeInt(0);
		buf.writeShort(0);
		buf.writeByte(0xFC);
		buf.writeByte(1);
		buf.writeInt(0);
		buf.writeLong(0);
		buf.writeZero(6);
		buf.writeInt(random);
		buf.writeZero(11);
		buf.writeInt(random);
		buf.writeZero(11);
		buf.writeInt(random);
		buf.writeShort(0);
		buf.writeByte(0);
		
		buf.writeLong(0);
		
        buf.writeInt(random);
        buf.writeZero(9);
        buf.writeInt(random);
        buf.writeShort(0);
        buf.writeInt(0); // actually not 0, why is it 0 then?
        buf.writeZero(10);
        buf.writeInt(random);
        buf.writeZero(13);
        buf.writeInt(random);
        buf.writeShort(0);
        buf.writeByte(0);
        
        buf.writeShort(chr.getJob());
        
        buf.writeByte(chr.isMale() ? 0 : 1);
        buf.writeByte(chr.getSkin());
        buf.writeInt(chr.getFace());
        buf.writeByte(0);
		buf.writeInt(chr.getHair());
		
		//Equips
		buf.writeByte(0xFF);
		buf.writeByte(0xFF);
		buf.writeInt(0);
		
		buf.writeInt(0);
		buf.writeInt(0);
		buf.writeInt(0);
		
		//End of Equips
		
		buf.writeInt(0);
		buf.writeInt(0);
		buf.writeInt(0);
		buf.writeShort(chr.getX());
		buf.writeShort(chr.getY());
		buf.writeByte(0);//Stance
		buf.writeShort(0);//Fh
		buf.writeByte(0);
		buf.writeByte(0);//Pets
		
		//Mount
		buf.writeInt(1);
		buf.writeLong(0);//Exp + Tiredness
		
		//End of mount
		
		buf.writeByte(0);
		
		buf.writeByte(0);
		
		buf.writeByte(0);
		buf.writeByte(0);
		
		buf.writeBoolean(false);
		
		buf.writeZero(3);
		buf.writeByte(0);
		
		setPayload(buf.capacity(buf.writerIndex()).array());
	}

}
