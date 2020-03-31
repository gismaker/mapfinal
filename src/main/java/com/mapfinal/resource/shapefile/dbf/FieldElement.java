package com.mapfinal.resource.shapefile.dbf;

import java.util.Arrays;

import com.mapfinal.resource.shapefile.shpx.BigEndian;

/**
 * 字段描述结构
 * 
 * @author proxyme
 * 
 */
public class FieldElement {
	public FieldElement() {

	}

	public FieldElement(byte[] buffer) {
		int offset = 0;
		// 字段名称
		this.szFiledName = new byte[11];
		System.arraycopy(buffer, offset, this.szFiledName, 0,
				this.szFiledName.length);
		offset += this.szFiledName.length;
		byte[] bytes = new byte[1];
		// 字段类型
		System.arraycopy(buffer, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		this.fieldType = BigEndian.littleToChar(BigEndian.toByte(bytes));
		// 偏移量
		bytes = new byte[8];
		Arrays.fill(bytes, (byte) 0);
		System.arraycopy(buffer, offset, bytes, 0, 4);
		this.offset = BigEndian.toLong(bytes);
		offset += 4;
		// 字段长度
		bytes = new byte[1];
		System.arraycopy(buffer, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		// 将byte转换为short,这里一定要与0xff按位与,将高8位清空
		this.fieldLength = BigEndian.unsignedToShort(BigEndian.toByte(bytes));
		// 浮点数整数部分长度
		System.arraycopy(buffer, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		this.fieldDecimal = BigEndian.toByte(bytes);
		// 保留字,2个字节
		this.reserved1 = new byte[2];
		System.arraycopy(buffer, offset, this.reserved1, 0,
				this.reserved1.length);
		offset += this.reserved1.length;
		// dBASE
		bytes = new byte[1];
		System.arraycopy(buffer, offset, bytes, 0, bytes.length);
		offset += bytes.length;
		this.dbaseiv_id = BigEndian.toByte(bytes);
		// 保留字,10个字节
		this.reserved2 = new byte[10];
		System.arraycopy(buffer, offset, this.reserved2, 0,
				this.reserved2.length);
		offset += this.reserved2.length;
		// productionIndex
		this.productionIndex = buffer[buffer.length - 1];
	}

	/**
	 * DBF头长度为32
	 */
	public static final int SIZE = 32;
	/**
	 * 字段名称,11个字节,字符串c/c++格式一样都是little endian
	 */
	byte[] szFiledName;
	/**
	 * 字段类型,1个字节
	 */
	// byte fieldType;
	char fieldType;
	/**
	 * 偏移量unsigned long,c++中为4个字节,这里需要用java 8个字节的long来表示
	 */
	long offset;
	/**
	 * 字段长度,unsigned char,所以这里需要2个字节表示
	 */
	// byte fieldLength;
	short fieldLength;
	/**
	 * 浮点数整数部分长度,unsigned char,故这里需要用short来表示
	 */
	// byte fieldDecimal;
	short fieldDecimal;
	/**
	 * 保留,2个字节
	 */
	byte[] reserved1;
	/**
	 * dBASE IV work area id
	 */
	byte dbaseiv_id;
	/**
	 * 保留,10个字节
	 */
	byte[] reserved2;
	byte productionIndex;
}
