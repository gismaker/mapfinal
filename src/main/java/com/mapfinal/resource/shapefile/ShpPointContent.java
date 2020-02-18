package com.mapfinal.resource.shapefile;

public class ShpPointContent {
	public ShpPointContent(byte[] b) {
		int offset = 0;
		byte[] bytes = new byte[4];
		// 读取类型
		System.arraycopy(b, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		iShpType = BigEndian.littleToInt(bytes);
		// 读取x坐标
		bytes = new byte[8];
		System.arraycopy(b, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		x = BigEndian.littleToDouble(bytes);
		// 读取Y坐标
		System.arraycopy(b, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		y = BigEndian.littleToDouble(bytes);
	}

	/**
	 * 内存字节数
	 */
	public static final int SIZE = 20;
	/**
	 * 类型
	 */
	public int iShpType;/* endian little */
	public double x;/* endian little */
	public double y;/* endian little */
}
