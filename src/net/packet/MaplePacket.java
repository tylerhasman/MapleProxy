package net.packet;

import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Arrays;

import lombok.Getter;
import net.ClientOpCode;
import net.OpCode;
import net.ServerOpCode;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class MaplePacket {

	@Getter
	private short type;
	@Getter
	private byte[] payload;
	private boolean fromServer;
	
	public MaplePacket(int type, byte[] payload, boolean fromServer) {
		this.type = (short) type;
		this.payload = payload;
		this.fromServer = fromServer;
	}
	
	public boolean isFromServer() {
		return fromServer;
	}
	
	public OpCode getOpCode(){
		if(fromServer){
			return ServerOpCode.getById(type);
		}else{
			return ClientOpCode.getById(type);
		}
	}
	
	protected MaplePacket(int type){
		this.type = (short) type;
	}
	
	public void setPayload(byte[] buffer){
		payload = buffer;
	}
	
	protected void setPayload(ByteBuf buf){
		payload = buf.array();
	}
	
	@Override
	public String toString() {
		Object obj = getOpCode();
		if(obj == null){
			obj = type;
		}
		return "{"+obj+"} "+Arrays.toString(getPayload())+" ("+new String(getPayload())+")";
	}

	public ByteBuf toBuffer() {
		return Unpooled.copiedBuffer(payload).order(ByteOrder.LITTLE_ENDIAN);
	}
	
	public static String readString(ByteBuf buf, int length){
		byte[] strB = new byte[length];
		buf.readBytes(strB);
		
		return new String(strB, Charset.forName("US-ASCII"));
	}
	
	public static String readString(ByteBuf buf){
		short length = buf.readShort();
		
		return readString(buf, length);
	}
	
	public static void writeString(ByteBuf buf, String str){
		byte[] bytes = str.getBytes(Charset.forName("US-ASCII"));
		
		short length = (short) str.length();
		
		buf.writeShort(length);
		buf.writeBytes(bytes);
	}
	
}
