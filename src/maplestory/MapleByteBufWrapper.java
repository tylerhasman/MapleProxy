package maplestory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufProcessor;

public class MapleByteBufWrapper extends ByteBuf {

	private static Charset ASCII = Charset.forName("US-ASCII");
	
	private ByteBuf buf;
	
	public MapleByteBufWrapper(ByteBuf buf) {
		this.buf = buf;
	}

	public ByteBuf writeMapleString(String str){
		writeShort(str.length());
		writeBytes(str.getBytes(ASCII));
		
		return this;
	}
	
	public String readMapleString(){
		int length = readUnsignedShort();
		
		byte[] buffer = new byte[length];
		
		readBytes(buffer);
		
		return new String(buffer);
	}
	
	public ByteBufAllocator alloc() {
		return buf.alloc();
	}

	public byte[] array() {
		return buf.array();
	}

	public int arrayOffset() {
		return buf.arrayOffset();
	}

	public int bytesBefore(byte value) {
		return buf.bytesBefore(value);
	}

	public int bytesBefore(int length, byte value) {
		return buf.bytesBefore(length, value);
	}

	public int bytesBefore(int index, int length, byte value) {
		return buf.bytesBefore(index, length, value);
	}

	public int capacity() {
		return buf.capacity();
	}

	public ByteBuf capacity(int newCapacity) {
		return buf.capacity(newCapacity);
	}

	public ByteBuf clear() {
		return buf.clear();
	}

	public int compareTo(ByteBuf buffer) {
		return buf.compareTo(buffer);
	}

	public ByteBuf copy() {
		return buf.copy();
	}

	public ByteBuf copy(int index, int length) {
		return buf.copy(index, length);
	}

	public ByteBuf discardReadBytes() {
		return buf.discardReadBytes();
	}

	public ByteBuf discardSomeReadBytes() {
		return buf.discardSomeReadBytes();
	}

	public ByteBuf duplicate() {
		return buf.duplicate();
	}

	public int ensureWritable(int minWritableBytes, boolean force) {
		return buf.ensureWritable(minWritableBytes, force);
	}

	public ByteBuf ensureWritable(int minWritableBytes) {
		return buf.ensureWritable(minWritableBytes);
	}

	public boolean equals(Object obj) {
		return buf.equals(obj);
	}

	public int forEachByte(ByteBufProcessor processor) {
		return buf.forEachByte(processor);
	}

	public int forEachByte(int index, int length, ByteBufProcessor processor) {
		return buf.forEachByte(index, length, processor);
	}

	public int forEachByteDesc(ByteBufProcessor processor) {
		return buf.forEachByteDesc(processor);
	}

	public int forEachByteDesc(int index, int length, ByteBufProcessor processor) {
		return buf.forEachByteDesc(index, length, processor);
	}

	public boolean getBoolean(int index) {
		return buf.getBoolean(index);
	}

	public byte getByte(int index) {
		return buf.getByte(index);
	}

	public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
		return buf.getBytes(index, dst, dstIndex, length);
	}

	public ByteBuf getBytes(int index, byte[] dst) {
		return buf.getBytes(index, dst);
	}

	public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
		return buf.getBytes(index, dst, dstIndex, length);
	}

	public ByteBuf getBytes(int index, ByteBuf dst, int length) {
		return buf.getBytes(index, dst, length);
	}

	public ByteBuf getBytes(int index, ByteBuf dst) {
		return buf.getBytes(index, dst);
	}

	public ByteBuf getBytes(int index, ByteBuffer dst) {
		return buf.getBytes(index, dst);
	}

	public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
		return buf.getBytes(index, out, length);
	}

	public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
		return buf.getBytes(index, out, length);
	}

	public char getChar(int index) {
		return buf.getChar(index);
	}

	public double getDouble(int index) {
		return buf.getDouble(index);
	}

	public float getFloat(int index) {
		return buf.getFloat(index);
	}

	public int getInt(int index) {
		return buf.getInt(index);
	}

	public long getLong(int index) {
		return buf.getLong(index);
	}

	public int getMedium(int index) {
		return buf.getMedium(index);
	}

	public short getShort(int index) {
		return buf.getShort(index);
	}

	public short getUnsignedByte(int index) {
		return buf.getUnsignedByte(index);
	}

	public long getUnsignedInt(int index) {
		return buf.getUnsignedInt(index);
	}

	public int getUnsignedMedium(int index) {
		return buf.getUnsignedMedium(index);
	}

	public int getUnsignedShort(int index) {
		return buf.getUnsignedShort(index);
	}

	public boolean hasArray() {
		return buf.hasArray();
	}

	public boolean hasMemoryAddress() {
		return buf.hasMemoryAddress();
	}

	public int hashCode() {
		return buf.hashCode();
	}

	public int indexOf(int fromIndex, int toIndex, byte value) {
		return buf.indexOf(fromIndex, toIndex, value);
	}

	public ByteBuffer internalNioBuffer(int index, int length) {
		return buf.internalNioBuffer(index, length);
	}

	public boolean isDirect() {
		return buf.isDirect();
	}

	public boolean isReadable() {
		return buf.isReadable();
	}

	public boolean isReadable(int size) {
		return buf.isReadable(size);
	}

	public boolean isWritable() {
		return buf.isWritable();
	}

	public boolean isWritable(int size) {
		return buf.isWritable(size);
	}

	public ByteBuf markReaderIndex() {
		return buf.markReaderIndex();
	}

	public ByteBuf markWriterIndex() {
		return buf.markWriterIndex();
	}

	public int maxCapacity() {
		return buf.maxCapacity();
	}

	public int maxWritableBytes() {
		return buf.maxWritableBytes();
	}

	public long memoryAddress() {
		return buf.memoryAddress();
	}

	public ByteBuffer nioBuffer() {
		return buf.nioBuffer();
	}

	public ByteBuffer nioBuffer(int index, int length) {
		return buf.nioBuffer(index, length);
	}

	public int nioBufferCount() {
		return buf.nioBufferCount();
	}

	public ByteBuffer[] nioBuffers() {
		return buf.nioBuffers();
	}

	public ByteBuffer[] nioBuffers(int index, int length) {
		return buf.nioBuffers(index, length);
	}

	public ByteOrder order() {
		return buf.order();
	}

	public ByteBuf order(ByteOrder endianness) {
		return buf.order(endianness);
	}

	public boolean readBoolean() {
		return buf.readBoolean();
	}

	public byte readByte() {
		return buf.readByte();
	}

	public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
		return buf.readBytes(dst, dstIndex, length);
	}

	public ByteBuf readBytes(byte[] dst) {
		return buf.readBytes(dst);
	}

	public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
		return buf.readBytes(dst, dstIndex, length);
	}

	public ByteBuf readBytes(ByteBuf dst, int length) {
		return buf.readBytes(dst, length);
	}

	public ByteBuf readBytes(ByteBuf dst) {
		return buf.readBytes(dst);
	}

	public ByteBuf readBytes(ByteBuffer dst) {
		return buf.readBytes(dst);
	}

	public int readBytes(GatheringByteChannel out, int length) throws IOException {
		return buf.readBytes(out, length);
	}

	public ByteBuf readBytes(int length) {
		return buf.readBytes(length);
	}

	public ByteBuf readBytes(OutputStream out, int length) throws IOException {
		return buf.readBytes(out, length);
	}

	public char readChar() {
		return buf.readChar();
	}

	public double readDouble() {
		return buf.readDouble();
	}

	public float readFloat() {
		return buf.readFloat();
	}

	public int readInt() {
		return buf.readInt();
	}

	public long readLong() {
		return buf.readLong();
	}

	public int readMedium() {
		return buf.readMedium();
	}

	public short readShort() {
		return buf.readShort();
	}

	public ByteBuf readSlice(int length) {
		return buf.readSlice(length);
	}

	public short readUnsignedByte() {
		return buf.readUnsignedByte();
	}

	public long readUnsignedInt() {
		return buf.readUnsignedInt();
	}

	public int readUnsignedMedium() {
		return buf.readUnsignedMedium();
	}

	public int readUnsignedShort() {
		return buf.readUnsignedShort();
	}

	public int readableBytes() {
		return buf.readableBytes();
	}

	public int readerIndex() {
		return buf.readerIndex();
	}

	public ByteBuf readerIndex(int readerIndex) {
		return buf.readerIndex(readerIndex);
	}

	public int refCnt() {
		return buf.refCnt();
	}

	public boolean release() {
		return buf.release();
	}

	public boolean release(int arg0) {
		return buf.release(arg0);
	}

	public ByteBuf resetReaderIndex() {
		return buf.resetReaderIndex();
	}

	public ByteBuf resetWriterIndex() {
		return buf.resetWriterIndex();
	}

	public ByteBuf retain() {
		return buf.retain();
	}

	public ByteBuf retain(int increment) {
		return buf.retain(increment);
	}

	public ByteBuf setBoolean(int index, boolean value) {
		return buf.setBoolean(index, value);
	}

	public ByteBuf setByte(int index, int value) {
		return buf.setByte(index, value);
	}

	public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
		return buf.setBytes(index, src, srcIndex, length);
	}

	public ByteBuf setBytes(int index, byte[] src) {
		return buf.setBytes(index, src);
	}

	public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
		return buf.setBytes(index, src, srcIndex, length);
	}

	public ByteBuf setBytes(int index, ByteBuf src, int length) {
		return buf.setBytes(index, src, length);
	}

	public ByteBuf setBytes(int index, ByteBuf src) {
		return buf.setBytes(index, src);
	}

	public ByteBuf setBytes(int index, ByteBuffer src) {
		return buf.setBytes(index, src);
	}

	public int setBytes(int index, InputStream in, int length) throws IOException {
		return buf.setBytes(index, in, length);
	}

	public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
		return buf.setBytes(index, in, length);
	}

	public ByteBuf setChar(int index, int value) {
		return buf.setChar(index, value);
	}

	public ByteBuf setDouble(int index, double value) {
		return buf.setDouble(index, value);
	}

	public ByteBuf setFloat(int index, float value) {
		return buf.setFloat(index, value);
	}

	public ByteBuf setIndex(int readerIndex, int writerIndex) {
		return buf.setIndex(readerIndex, writerIndex);
	}

	public ByteBuf setInt(int index, int value) {
		return buf.setInt(index, value);
	}

	public ByteBuf setLong(int index, long value) {
		return buf.setLong(index, value);
	}

	public ByteBuf setMedium(int index, int value) {
		return buf.setMedium(index, value);
	}

	public ByteBuf setShort(int index, int value) {
		return buf.setShort(index, value);
	}

	public ByteBuf setZero(int index, int length) {
		return buf.setZero(index, length);
	}

	public ByteBuf skipBytes(int length) {
		return buf.skipBytes(length);
	}

	public ByteBuf slice() {
		return buf.slice();
	}

	public ByteBuf slice(int index, int length) {
		return buf.slice(index, length);
	}

	public String toString() {
		return buf.toString();
	}

	public String toString(Charset charset) {
		return buf.toString(charset);
	}

	public String toString(int index, int length, Charset charset) {
		return buf.toString(index, length, charset);
	}

	public ByteBuf unwrap() {
		return buf.unwrap();
	}

	public int writableBytes() {
		return buf.writableBytes();
	}

	public ByteBuf writeBoolean(boolean value) {
		return buf.writeBoolean(value);
	}

	public ByteBuf writeByte(int value) {
		return buf.writeByte(value);
	}

	public ByteBuf writeBytes(byte[] src, int srcIndex, int length) {
		return buf.writeBytes(src, srcIndex, length);
	}

	public ByteBuf writeBytes(byte[] src) {
		return buf.writeBytes(src);
	}

	public ByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
		return buf.writeBytes(src, srcIndex, length);
	}

	public ByteBuf writeBytes(ByteBuf src, int length) {
		return buf.writeBytes(src, length);
	}

	public ByteBuf writeBytes(ByteBuf src) {
		return buf.writeBytes(src);
	}

	public ByteBuf writeBytes(ByteBuffer src) {
		return buf.writeBytes(src);
	}

	public int writeBytes(InputStream in, int length) throws IOException {
		return buf.writeBytes(in, length);
	}

	public int writeBytes(ScatteringByteChannel in, int length) throws IOException {
		return buf.writeBytes(in, length);
	}

	public ByteBuf writeChar(int value) {
		return buf.writeChar(value);
	}

	public ByteBuf writeDouble(double value) {
		return buf.writeDouble(value);
	}

	public ByteBuf writeFloat(float value) {
		return buf.writeFloat(value);
	}

	public ByteBuf writeInt(int value) {
		return buf.writeInt(value);
	}

	public ByteBuf writeLong(long value) {
		return buf.writeLong(value);
	}

	public ByteBuf writeMedium(int value) {
		return buf.writeMedium(value);
	}

	public ByteBuf writeShort(int value) {
		return buf.writeShort(value);
	}

	public ByteBuf writeZero(int length) {
		return buf.writeZero(length);
	}

	public int writerIndex() {
		return buf.writerIndex();
	}

	public ByteBuf writerIndex(int writerIndex) {
		return buf.writerIndex(writerIndex);
	}

}
