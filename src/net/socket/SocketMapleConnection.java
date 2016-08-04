package net.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteOrder;
import java.util.Arrays;

import security.MapleAESOFB;
import security.MapleCustomEncryption;
import security.MapleSecurity;
import lombok.Getter;
import net.MapleConnection;
import net.ServerOpCode;
import net.packet.MaplePacket;

public class SocketMapleConnection implements MapleConnection {

	private Socket socket;
	@Getter
	private MapleAESOFB sendCrypto, recvCrypto;
	
	private boolean closed;
	private int length = -1;
	
	private boolean isClient;
	
	private boolean handshakeDone;
	
	public SocketMapleConnection(Socket con, boolean isClient) {
		socket = con;
		closed = false;
		this.isClient = isClient;
		handshakeDone = isClient;
	}

	public boolean isClient() {
		return isClient;
	}
	
	@Override
	public int getPort() {
		return socket.getLocalPort();
	}
	
	@Override
	public void sendPacket(MaplePacket packet) {
		if(!isReady()){
			return;
		}
		try {
			OutputStream os = socket.getOutputStream();
			
			if(packet.getOpCode() != ServerOpCode.HANDSHAKE){
				int length = packet.getPayload().length + 2;
				ByteBuf buf = Unpooled.buffer(length, length).order(ByteOrder.LITTLE_ENDIAN);
				byte[] header = sendCrypto.getPacketHeader(length);
				
				buf.writeShort(packet.getType());
				buf.writeBytes(packet.getPayload());
				
				os.write(header);
				
				byte[] payload = buf.array();
				
				MapleCustomEncryption.encryptData(payload);
				
				sendCrypto.crypt(payload);
				
				os.write(payload);
				
			}else{
				ByteBuf buf = Unpooled.buffer(16, 16).order(ByteOrder.LITTLE_ENDIAN);
				buf.writeShort(14);
				buf.writeBytes(packet.getPayload());
				os.write(buf.array());
			}
		} catch (Exception e) {
			handleException(e);
		}
		
	}
	
	public MaplePacket readHandshakePacket(){
		if(!isReady()){
			return null;
		}
		try {
			InputStream is = socket.getInputStream();
			
			if(length <= 0){
				if(is.available() < 2){
					return null;
				}
				byte[] lengthBuf = new byte[2];
				is.read(lengthBuf);
				length = Unpooled.wrappedBuffer(lengthBuf).readShort();
				length = MapleAESOFB.getPacketLength(length);
			}
			
			if(is.available() < length){
				return null;
			}
			
			byte[] data = new byte[length];
			is.read(data);
			
			length = -1;
			handshakeDone = true;
			return new MaplePacket(-1, data, true);
		}catch(IOException e){
			handleException(e);
		}
		return null;
	}

	@Override
	public MaplePacket readPacket() {
		if(!isReady()){
			return null;
		}
		if(!handshakeDone){
			return readHandshakePacket();
		}
		try {
			InputStream is = socket.getInputStream();
			
			byte[] lengthBuf = new byte[4];
			if(length <= 0){
				if(is.available() < 4){
					return null;
				}
				
				is.read(lengthBuf);
				length = Unpooled.wrappedBuffer(lengthBuf).readInt();
				
				length = MapleAESOFB.getPacketLength(length);
			}
			
			if(is.available() < length){
				return null;
			}
			
			byte[] decryptedPacket = new byte[length];
			is.read(decryptedPacket);
			
			recvCrypto.crypt(decryptedPacket);
			MapleCustomEncryption.decryptData(decryptedPacket);
			
			ByteBuf buf = Unpooled.wrappedBuffer(decryptedPacket).order(ByteOrder.LITTLE_ENDIAN);
			short type = buf.readShort();
			byte[] payload = new byte[length - 2];
			
			buf.readBytes(payload);
			
			length = -1;
			
			return new MaplePacket(type, payload, !isClient);
		} catch (IOException e) {
			handleException(e);
		}
		return null;
	}

	@Override
	public void closeConnection() {
		try{
			if(closed){
				return;
			}
			System.out.println("Closing connection "+socket.getRemoteSocketAddress());
			closed = true;
			socket.close();
		}catch(Exception e){
			handleException(e);
		}
	}

	@Override
	public SocketAddress getRemoteAddress() {
		return socket.getRemoteSocketAddress();
	}

	@Override
	public boolean isReady() {
		return !closed;
	}
	
	public void setCryptKeys(byte[] send, byte[] recv, int version) {
		if(isClient){
			sendCrypto = new MapleAESOFB(MapleSecurity.getAESKey(), send, (short) (0xFFFF - version));
			recvCrypto = new MapleAESOFB(MapleSecurity.getAESKey(), recv, (short) version);
		}else{
			sendCrypto = new MapleAESOFB(MapleSecurity.getAESKey(), send, (short) version);
			recvCrypto = new MapleAESOFB(MapleSecurity.getAESKey(), recv, (short) version);
		}
	}
	
	private void handleException(Exception e){
		System.out.println("Error "+e.getMessage());
		if(e instanceof IOException){
			closed = true;
		}
	}

}
