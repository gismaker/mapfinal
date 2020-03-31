package com.mapfinal.resource.shapefile.shpx;

/**
 * 
 * @author proxyme
 * 
 */
public class ShpInfo {
	public ShpInfo(byte[] b) {
		int offset = 0;
		byte[] bytes = new byte[4];
		// 获取几何类型
		System.arraycopy(b, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		this.iShpType = BigEndian.littleToInt(bytes);
		// 获取坐标范围
		this.ptBox = new ShpPoint[2];
		bytes = new byte[8];
		// ptBox[0]
		this.ptBox[0] = new ShpPoint();
		System.arraycopy(b, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		this.ptBox[0].x = BigEndian.littleToDouble(bytes);
		System.arraycopy(b, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		this.ptBox[0].y = BigEndian.littleToDouble(bytes);
		// ptBox[1]
		this.ptBox[1] = new ShpPoint();
		System.arraycopy(b, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		this.ptBox[1].x = BigEndian.littleToDouble(bytes);
		System.arraycopy(b, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		this.ptBox[1].y = BigEndian.littleToDouble(bytes);
		bytes = new byte[4];
		// 子线段个数
		System.arraycopy(b, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		this.iNumParts = BigEndian.littleToInt(bytes);
		// 坐标点数
		System.arraycopy(b, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		this.iNumPoints = BigEndian.littleToInt(bytes);
	}

	public static final int SIZE = 44;
	/**
	 * 几何类型
	 */
	public int iShpType; /* endian little */
	/**
	 * 坐标范围,2个大小,ptBox[0] lefttop,ptBox[1] rightBottom
	 */
	public ShpPoint ptBox[];/* endian little */
	/**
	 * 子线段个数
	 */
	public int iNumParts; /* endian little */
	/**
	 * 坐标点数
	 */
	public int iNumPoints;/* endian little */

}
