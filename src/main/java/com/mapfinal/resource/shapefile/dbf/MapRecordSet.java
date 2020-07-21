package com.mapfinal.resource.shapefile.dbf;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 对Shp对象的属性数据记录集(DBF数据)的管理
 * 
 * @author proxyme
 * 
 */

public class MapRecordSet {
	private static final int MAX_CACH_SIZE = 100;
	/**
	 * 记录集
	 */
	private ArrayList<MapFields> mFields;
	private MapTableDesc mTableDesc;
	private int mCacheSize = 0;
	/**
	 * 游标当前位置
	 */
	private long cursorPos = -1;
	private boolean isBof = false;
	private boolean isEof = false;
	/**
	 * DBF文件头
	 */
	private DbfHeader mDbfHeader;
	private boolean isDbfOpened = false;
	/**
	 * DBF文件输入流
	 */
//	private RandomAccessFile dbfFile;
	private RandomReader dbfFile;
	
	private String charsetName = "utf-8";

	public MapRecordSet() {
		this.mFields = new ArrayList<MapFields>();
		this.mTableDesc = new MapTableDesc();
		this.mCacheSize = 50;
	}

	public long getRecordCount() {
		return mDbfHeader.recordNum;
	}

	public MapFields getFields(int index) {
		if (index < 0 || index > mFields.size() - 1) {
			 throw new IndexOutOfBoundsException();
		}
		return mFields.get(index);
	}

	public MapTableDesc getTableDesc() {
		return this.mTableDesc;
	}

	public boolean isBOF() {
		return this.isBof;
	}

	public boolean isEOF() {
		return this.isEof;
	}

	public int getCacheSize() {
		return this.mCacheSize;
	}

	public boolean setCacheSize(int size) {
		if (size < 0 || size > MAX_CACH_SIZE) {
			return false;
		}
		this.mCacheSize = size;
		return true;
	}

	public boolean openDBF(String fileName) throws IOException {/* 已测试 */
		//dbfFile = new RandomAccessFile(fileName, "r");
		dbfFile = new RandomReader(fileName);
		if (dbfFile != null) {
			this.isDbfOpened = true;
		} else {
			return false;
		}
		//dbfFile.seek(0L);
		dbfFile.seek(0);
		byte[] buffer = new byte[DbfHeader.SIZE];
		int readCount = dbfFile.read(buffer, 0, buffer.length);
		if (readCount != buffer.length) {
			return false;
		}
		this.mDbfHeader = new DbfHeader(buffer);
		short headerLength = this.mDbfHeader.headerLength;
		// 计算字段个数
		short fieldCount = (short) ((headerLength - DbfHeader.SIZE - 1) / FieldElement.SIZE);
		int temp = fieldCount * FieldElement.SIZE + 1;
		byte[] szBuffer = new byte[temp];
		// 读入字段描述部分数据(表结构)
		readCount = dbfFile.read(szBuffer, 0, szBuffer.length);
		if (readCount != temp) {
			return false;
		}
		FieldElement oldField = null;
		byte[] bytes = new byte[FieldElement.SIZE];
		for (int i = 0; i < fieldCount; i++) {
			Arrays.fill(bytes, (byte) 0);
			System.arraycopy(szBuffer, i * FieldElement.SIZE, bytes, 0,
					FieldElement.SIZE);
			FieldElement fieldElement = new FieldElement(bytes);
			if (i == 0) {
				fieldElement.offset = 0;
			} else {
				fieldElement.offset = oldField.offset + oldField.fieldLength;
			}
			// 判断字段类型
			if (fieldElement.fieldType != 'N' && fieldElement.fieldType != 'F') {
				fieldElement.fieldLength += fieldElement.fieldDecimal * 256;
				fieldElement.fieldDecimal = 0;
			}
			oldField = fieldElement;
			this.mTableDesc.add(fieldElement);
		}
		// 读入记录到记录集缓冲区
		readRecord(0);
		return true;
	}

	/**
	 * 移动到记录集头部
	 * 
	 * @throws IOException
	 */
	public void moveFirst() throws IOException {
		if (!isDbfOpened) {
			return;
		}
		this.isBof = true;
		this.isEof = false;
		this.cursorPos = -1;
		readRecord(0);
	}

	/**
	 * 移动到记录集尾部
	 * 
	 * @throws IOException
	 */
	public void moveLast() throws IOException {
		if (!isDbfOpened) {
			return;
		}
		this.isEof = true;
		readRecord(this.mDbfHeader.recordNum - 1);
	}

	/**
	 * 移动到下一条记录
	 * 
	 * @throws IOException
	 */
	public void moveNext() throws IOException {
		if (!isDbfOpened) {
			return;
		}

		if (this.mDbfHeader.recordNum == 1) {
			isBof = true;
			isEof = true;
		} else if (cursorPos < this.mDbfHeader.recordNum - 1) {
			isBof = false;
			readRecord(cursorPos + 1);
		} else {
			isEof = true;
		}
	}

	/**
	 * 移动到上一条记录
	 * 
	 * @throws IOException
	 */
	public void movePrev() throws IOException {
		if (!isDbfOpened) {
			return;
		}
		if (this.mDbfHeader.recordNum == 1) {
			isBof = true;
			isEof = true;
		} else if (cursorPos > 0) {
			isEof = false;
			readRecord(cursorPos - 1);
		} else {
			isBof = true;
		}
	}

	/**
	 * 移动numRecords条记录
	 * 
	 * @param numRecords
	 * @param start
	 * @return
	 * @throws IOException
	 */
	public boolean move(int numRecords, RecordStart start) throws IOException {
		long pos = 0;
		switch (start) {
		case BookmarkCurrent:
			pos = cursorPos;
			break;
		case BookmarkLast:
			pos = this.mDbfHeader.recordNum - 1;
			break;
		case BookmarkFirst:
			pos = 0;
			break;
		}
		// 向后移动
		if (numRecords > 0) {
			if (this.mDbfHeader.recordNum <= (pos + numRecords)) {
				return false;
			} else {
				readRecord(pos + numRecords);
				return true;
			}
		} else {
			if ((pos + numRecords) < 0) {
				return false;
			} else {
				readRecord(pos + numRecords);
				return true;
			}
		}
	}

	private void clear() {
		for (MapFields fields : mFields) {
			fields.clear();
		}
		mFields.clear();
	}

	public void readRecord(long recordId) throws IOException {/* 已测试 */
		if (recordId < 0 || recordId >= this.mDbfHeader.recordNum) {
			// 无效索引
			 throw new IndexOutOfBoundsException();
		}
		long recordOffset = 0;
		byte[] buffer;
		// 要读取的记录未在缓冲区
		if (this.cursorPos != recordId) {
			// 计算记录相对文件头的偏移量
			recordOffset = recordId * this.mDbfHeader.recordLength
					+ this.mDbfHeader.headerLength;
			buffer = new byte[this.mDbfHeader.recordLength];
		//	dbfFile.seek(recordOffset);
			dbfFile.seek((int)recordOffset);
			int count = dbfFile.read(buffer, 0, this.mDbfHeader.recordLength);
			if (count != buffer.length) {
				return;
			}
			clear();
			MapFields mapFields = new MapFields();
			//MapField mapField;
			FieldElement fieldElement;
			byte[] szBuffer = new byte[255];
			double dbValue;
			Object varValue;
			short fieldCount = mTableDesc.getFieldCount();
			for (short j = 0; j < fieldCount; j++) {
				MapField mapField = new MapField();
				fieldElement = mTableDesc.getDesc(j);
				mapField.setName(new String(fieldElement.szFiledName).trim());
				mapField.setType(fieldElement.fieldType);
				Arrays.fill(szBuffer, (byte) 0);
				// 略过该记录是否删除标记字节buffer+1
				System.arraycopy(buffer, (int) (1 + fieldElement.offset),
						szBuffer, 0, fieldElement.fieldLength);
				if (fieldElement.fieldType == 'N'
						|| fieldElement.fieldType == 'F') {
					String strSequence = new String(szBuffer).trim();
					if (strSequence != null && strSequence.length() > 0) {
						dbValue = Float.parseFloat(strSequence);
					} else {
						dbValue = 0.0f;
					}
					varValue = dbValue;
					if (fieldElement.fieldDecimal == 0) {
						// 1表示整形
						mapField.setType(1);
					} else {
						// 2表示double
						mapField.setType(2);
					}
					mapField.setValue(varValue);
				} else if (fieldElement.fieldType == 'C') {
					mapField.setValue(new String(szBuffer, charsetName).trim());
					// 0表示string
					mapField.setType(0);
				} else {
					// 3表示Invaild
					mapField.setType(3);
				}
//				if(mapField.getStringValue().length() > 0) {
//					System.out.println(mapField.toString());
//				}
				mapFields.add(mapField);
			}
			// 清空缓冲区加入新记录
			clear();
			this.mFields.add(mapFields);
			this.cursorPos = recordId;
		}
	}

	public String getCharsetName() {
		return charsetName;
	}

	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}
}
