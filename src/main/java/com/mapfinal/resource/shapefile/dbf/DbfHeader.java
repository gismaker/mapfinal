package com.mapfinal.resource.shapefile.dbf;

import com.mapfinal.resource.shapefile.BigEndian;

/**
 * DBF文件头
 * 
 * @author proxyme
 * 
 */
public class DbfHeader {
	public DbfHeader(byte[] bHeader) {
		int offset = 0;
		version = bHeader[offset++];
		date = new byte[3];
		date[0] = bHeader[offset++];
		date[1] = bHeader[offset++];
		date[2] = bHeader[offset++];
		byte[] bytes = new byte[4];
		//记录条数
		System.arraycopy(bHeader, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		recordNum = BigEndian.littleToInt(bytes);
		//头文件字节数
		bytes = new byte[2];
		System.arraycopy(bHeader, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		headerLength = BigEndian.littleToShort(bytes);
		//一条记录字节长度
		System.arraycopy(bHeader, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		recordLength = BigEndian.littleToShort(bytes);
	}
	public static final int SIZE = 32;
	/**
	 * 当前版本标识
	 */
	byte version;/* byte 1,pos 0 */
	/**
	 * 表示最近的更新日期，按照 YYMMDD 格式,每个占用一个字节,unsigned char
	 */
	byte[] date;/* byte 1,pos 1-3 */
	/**
	 * 文件中的记录条数,32位数
	 */
	int recordNum;/* byte 4 ,pos 4-7 */
	/**
	 * 文件头中的字节数。unsigned short
	 */
	short headerLength;/* byte 2 ,pos 8-9 */
	/**
	 * 一条记录中的字节长度。
	 */
	short recordLength;/* byte 2 ,pos 10-11 */
	/**
	 * 保留字节，用于以后添加新的说明性信息时使用，这里用 0 来填写。20个字节
	 */
	byte[] reserved;/* byte 2 ,pos 12-13 */
}
