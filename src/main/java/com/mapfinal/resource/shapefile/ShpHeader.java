package com.mapfinal.resource.shapefile;

/**
 * 坐标文件的文件头是一个长度固定 (100 bytes) 的记录段，一共有 9 个 int 型和 7 个 double 型数据 <br/>
 * 最后 4 个加星号特别标示的四个数据只有当这个 Shapefile 文件包含 Z 方向 坐标或者具有 Measure 值时才有值，否则为 0.0 。所谓
 * Measure 值，是用于存储需要的 附加数据，可以用来记录各种数据，例如权值、道路长度等信息。
 * 
 * @author proxyme
 * 
 */
public class ShpHeader {
	public static final int SHAPE_FILE_CODE = 9994;
	public static final int SHAPE_FILE_VERSION = 1000;
	public static final int SIZE = 100;
	/**
	 * 文件标识
	 */
	public int iFileCode; /* byte 4, pos 0-3, value = 9994,endian big */
	/**
	 * 保留字节,长度为5个int
	 * 
	 */
	public int[] iReserved;/* byte 4,pos 4-23, endian big */
	/**
	 * 文件的实际长度,大小等于文件长度的一半,因为用2个字节表示,最大值为65535,节省空间
	 */
	public int iFileLength;/* byte 4,pos 24-27,endian big */
	/**
	 * 版本号
	 */
	public int iVersion;/* byte 4, pos 28-31 ,value = 1000,endian little */
	/**
	 * 几何类型,表示这个 Shapefile 文件所记录的空间数据的几何类型
	 */
	public int iShpType;/* byte 4, pos 32-35,endian little */
	/**
	 * 空间数据所占空间范围的X方向最小值
	 */
	public double dbXMin; /* byte 8,pos 36-43,endian little */
	/**
	 * 空间数据所占空间范围的Y方向最小值
	 */
	public double dbYMin;/* byte 8,pos 44-51,endian little */
	/**
	 * 空间数据所占空间范围的X方向最大值
	 */
	public double dbXMax;/* byte 8,pos 53-59,endian little */
	/**
	 * 空间数据所占空间范围的Y方向最大值
	 */
	public double dbYMax;/* byte 8,pos 60-67,endian little */
	/**
	 * 空间数据所占空间范围的Z方向最小值
	 */
	/* reserved */public double dbZMin;/* byte 8,pos 68-75,endian little */
	/**
	 * 空间数据所占空间范围的Z方向最大值
	 */
	/* reserved */public double dbZMax;/* byte 8,pos 76-83,endian little */
	/**
	 * 最小Measure值
	 */
	/* reserved */public double dbMMin;/* byte 8,pos 84-91,endian little */
	/**
	 * 最大Measure值
	 */
	/* reserved */public double dbMMax;/* byte 8,pos 92-100,endian little */

	public ShpHeader(byte[] bHeader) {
		readHeader(bHeader);
	}

	/**
	 * 读取文件标识,Big Endian为高位在前,低位在后
	 * 
	 * @param header
	 */
	public void readHeader(byte[] bHeader) {
		if (bHeader == null) {
			throw new NullPointerException("ShpHeader is null.");
		}
		if (bHeader.length < 100 - 32) {
			throw new IllegalArgumentException(
					"The length of ShpHeader should not less than 68.");
		}
		int offset = 0;
		byte[] bytes = new byte[4];
		System.arraycopy(bHeader, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		// 获取文件标识
		iFileCode = BigEndian.toInt(bytes);
		// 保留字
		iReserved = new int[5];
		for (int i = 0; i < iReserved.length; i++) {
			System.arraycopy(bHeader, offset, bytes, 0, bytes.length);
			offset += bytes.length;
			iReserved[i] = BigEndian.toInt(bytes);
		}
		// 获取文件实际长度
		System.arraycopy(bHeader, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		iFileLength = BigEndian.toInt(bytes);
		// 获取版本号
		System.arraycopy(bHeader, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		// 版本号
		iVersion = BigEndian.littleToInt(bytes);
		// 获取几何类型
		System.arraycopy(bHeader, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		iShpType = BigEndian.littleToInt(bytes);
		bytes = new byte[8];
		// x方向最小值
		System.arraycopy(bHeader, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		dbXMin = BigEndian.littleToDouble(bytes);
		// y方向最小值
		System.arraycopy(bHeader, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		dbYMin = BigEndian.littleToDouble(bytes);
		// x方向最大值
		System.arraycopy(bHeader, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		dbXMax = BigEndian.littleToDouble(bytes);
		// Y方向最大值
		System.arraycopy(bHeader, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		dbYMax = BigEndian.littleToDouble(bytes);
	}

}
