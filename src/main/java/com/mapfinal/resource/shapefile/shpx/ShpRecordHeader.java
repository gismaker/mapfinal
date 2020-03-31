package com.mapfinal.resource.shapefile.shpx;

public class ShpRecordHeader {
	public ShpRecordHeader(byte[] bHeader) {
		if (bHeader == null) {
			throw new NullPointerException("ShpHeader is null.");
		}
		int offset = 0;
		byte[] bytes = new byte[4];
		System.arraycopy(bHeader, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		// 获取记录数
		iRecordNum = BigEndian.toInt(bytes);
		System.arraycopy(bHeader, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		iContentLength = BigEndian.toInt(bytes);	
	}

	/**
	 * 记录数,从1开始
	 */
	public int iRecordNum;/* byte 4,pos 0-3,endian big */
	/**
	 * 记录内容长度
	 */
	public int iContentLength;/* byte 4,pos 4-7,endian big */
	
	public static final int SIZE = 8;
}
