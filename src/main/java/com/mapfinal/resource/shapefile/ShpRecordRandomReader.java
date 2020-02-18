package com.mapfinal.resource.shapefile;

import java.io.IOException;
import java.io.RandomAccessFile;

public class ShpRecordRandomReader {
	/**
	 * 32K
	 */
	public static final int MAX_BUFFER_SIZE = 32768;
	
	private byte[] buf;
	private int length = 0;
	private int currPointer = 0;
	private int id = 0;
	private int shpType = ShpType.NULL_SHAPE;
	
	public ShpRecordRandomReader(byte[] buf, int length, int id, int shpType) {
		// TODO Auto-generated constructor stub
		this.buf = buf;
		this.length = length;
		this.setId(id);
		this.setShpType(shpType);
		//System.out.println("[ShpRecordReader] length: "+length+", buf: "+buf.length);
	}
	
	public ShpRecordRandomReader(byte[] buf, int length) {
		// TODO Auto-generated constructor stub
		this.buf = buf;
		this.length = length;
		//System.out.println("[ShpRecordReader] length: "+length+", buf: "+buf.length);
	}
	
	public ShpRecordRandomReader(String filepath, int offset, int length) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(filepath, "r");
		this.length = length;
		buf = new byte[length];
		raf.seek(offset);
		raf.read(buf, 0, buf.length);
		raf.close();
	}
	
	public ShpRecordRandomReader(String filepath, ShxRecord shxRecord) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(filepath, "r");
		int offset = (shxRecord.iOffset) * 2;
		this.length = (shxRecord.iContentLength) * 2;
		buf = new byte[length];
		raf.seek(offset);
		raf.read(buf, 0, buf.length);
		raf.close();
	}
	
	public int read(byte[] buffer, int offset, int count) {
		if (this.currPointer > length) {
			//System.out.println("[ShpRecordReader] currPointer: "+ currPointer + ", count: " + count + ", length: " + length);
			return -1;
		}
		int available = length - this.currPointer;
		//System.out.println("[ShpRecordReader] length: "+ length + ", currPointer: " + currPointer +  ", count: " + count + ", available: " + available);
		if (count > available) {
			// System.arraycopy(buf, currPointer, buffer, offset, available);
			return -1;
		}
		System.arraycopy(buf, currPointer, buffer, offset, count);
		this.currPointer += count;
		return count;
	}

	/**
	 * 获得每条shp对象记录记录头的信息
	 * 
	 * @return
	 */
	public ShpRecordHeader getRecordHeader() {/* 已测试 */
		ShpRecordHeader recordHeader = null;
		byte[] buffer = new byte[ShpRecordHeader.SIZE];
		read(buffer, 0, buffer.length);
		recordHeader = new ShpRecordHeader(buffer);
		return recordHeader;
	}
	
	/**
	 * 获得每条shp对象描述信息
	 * 
	 * @param varInfo
	 * @return
	 * @throws IOException
	 */
	public ShpInfo getShpInfo() throws IOException {/* 已测试 */
		byte[] buffer = new byte[ShpInfo.SIZE];
		read(buffer, 0, buffer.length);
		ShpInfo shpInfo = new ShpInfo(buffer);
		return shpInfo;
	}
	
	/**
	 * 读入点对象数据
	 * 
	 * @param pBuffer
	 *            数据缓冲区，最大32K，如果超出需要分多次读取
	 * @param length
	 *            要读取的长度
	 * @param isEof
	 *            是否已经读取完成
	 * @return 读取数据的实际长度
	 * @throws IOException
	 */
	public BytePacket readPoint(int ilength) throws IOException {/* 已测试 */
		BytePacket bytePacket = new BytePacket();
		bytePacket.buffer = new byte[MAX_BUFFER_SIZE];
		if (ilength > MAX_BUFFER_SIZE) {
			ilength -= MAX_BUFFER_SIZE;
			int readCount = read(bytePacket.buffer, 0, MAX_BUFFER_SIZE);
			if (readCount != MAX_BUFFER_SIZE) {
				int available = length - this.currPointer;
				System.out.println("[ShpRecordReader] length: "+ length + ", currPointer: " + currPointer +  ", count: " + MAX_BUFFER_SIZE + ", available: " + available);
				return null;
			}
			bytePacket.isEof = false;
			bytePacket.actualLength = MAX_BUFFER_SIZE;
			bytePacket.length = ilength;
		} else {
			int readCount = read(bytePacket.buffer, 0, ilength);
			if (readCount != ilength) {
				int available = length - this.currPointer;
				System.out.println("[ShpRecordReader] length: "+ length + ", currPointer: " + currPointer +  ", count: " + MAX_BUFFER_SIZE + ", available: " + available);
				return null;
			}
			bytePacket.isEof = true;
			bytePacket.actualLength = ilength;
			bytePacket.length = ilength;
		}
		return bytePacket;
	}

	public int getShpType() {
		return shpType;
	}

	public void setShpType(int shpType) {
		this.shpType = shpType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLength() {
		return length;
	}

	public int getCurrPointer() {
		return currPointer;
	}
}
