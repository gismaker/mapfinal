package com.mapfinal.resource.shapefile;

public class ShxRecord {
	public ShxRecord(byte[] b) {
		int offset = 0;
		byte[] bytes = new byte[4];
		// 获得偏移量
		System.arraycopy(b, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		iOffset = BigEndian.toInt(bytes);
		// 获得记录长度
		System.arraycopy(b, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		iContentLength = BigEndian.toInt(bytes);
	}
	
	public int length() {
		return iContentLength * 2  + ShpRecordHeader.SIZE;
	}
	
	public int offset() {
		return iOffset * 2 ;
	}

	/**
	 * 偏移量,表示坐标文件中的对应记录的起始位置相对于坐标文件起始位置的位移量。因为ShpHeader中读取的长度为实际长度的一半,
	 * 所以这里offset和contentLength也都为实际值的一半
	 */
	public int iOffset;/* endian big */
	/**
	 * 记录长度,表示坐标文件中的对应记录的长度。
	 */
	public int iContentLength;/* endian big */
	/**
	 * ShxRecord占用字节数
	 */
	public static final int SIZE = 8;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Shx[offset:" + offset() + ", length:" + length() + "]";
	}
	
}
