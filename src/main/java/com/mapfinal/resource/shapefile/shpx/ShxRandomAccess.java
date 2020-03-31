package com.mapfinal.resource.shapefile.shpx;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.locationtech.jts.geom.Envelope;

public class ShxRandomAccess {

	/**
	 * Shx文件输入流
	 */
	private RandomAccessFile shxFile;
	/**
	 * 当前对象范围
	 */
	private Envelope mExtent;
	/**
	 * SHX文件实际长度
	 */
	private int shxFileLength;
	/**
	 * 主文件记录个数
	 */
	private int recordCount;
	/**
	 * shp对象类型
	 */
	private int shpType = ShpType.NULL_SHAPE;
	
	public ShxRandomAccess(File fShx) throws IOException {
		// TODO Auto-generated constructor stub
		//long start = System.currentTimeMillis();
		// 读取索引文件头 长100字节
		shxFile = new RandomAccessFile(fShx, "r");
		byte[] shxBuffer = new byte[100];
		shxFile.read(shxBuffer, 0, shxBuffer.length);
		ShpHeader shpHeader = new ShpHeader(shxBuffer);
		// 是否是shx文件
		if (shpHeader.iFileCode != 9994) {
			shxFile.close();
			shxFile = null;
			return;
		}
		// 文件版本是否正确
		if (shpHeader.iVersion != 1000) {
			shxFile.close();
			shxFile = null;
			return;
		}
		// shp类型
		this.shpType = shpHeader.iShpType;
		//System.out.println("shpType: " + shpType);
		this.shxFileLength = shpHeader.iFileLength;
		//System.out.println("shxFileLength: " + shxFileLength);
		// 保存数据最大矩形范围
		this.mExtent = new Envelope(shpHeader.dbXMin, shpHeader.dbXMax, shpHeader.dbYMin, shpHeader.dbYMax);
		//System.out.println("mExtent: " + mExtent.toString());
		// 通过索引文件计算主文件记录个数,文件长度数值以16位计
		this.recordCount = ((this.shxFileLength - 50) * 2) / ShxRecord.SIZE;
		//System.out.println("[ShxRandomAccess] recordCount: " + recordCount);
		//logger.info("Read header cost total time is :" + (System.currentTimeMillis() - start));
		//System.out.println("read shp head times: " + (System.currentTimeMillis() - start));
		//long start1 = System.currentTimeMillis();
		//System.out.println("read dbf times: " + (System.currentTimeMillis() - start1));
	}

	public void close() throws IOException {
		// TODO Auto-generated method stub
		if(shxFile!=null) {
			shxFile.close();
		}
	}

	/**
	 * 计算每条shp对象相对文件头的偏移量
	 * 
	 * @param recordPos
	 *            记录索引值(从0开始)
	 * @return 该shp对象数据在文件中的位置
	 * @throws IOException
	 */
	public ShxRecord getRecordPosition(int numberOfRecord) throws IOException {/* 已测试 */
		if(shxFile==null) return null;
		int offset;
		if (numberOfRecord < 0) {
			return null;
		}
		// 获得索引文件记录偏移量相对文件头
		if (numberOfRecord == 1) {
			offset = ShpHeader.SIZE;
		} else {
			offset = ShpHeader.SIZE + (numberOfRecord - 1) * ShxRecord.SIZE;
		}
		if (offset > this.shxFileLength * 2 - ShxRecord.SIZE) {
			return null;
		}
		shxFile.seek(offset);
		byte[] buffer = new byte[ShxRecord.SIZE];
		shxFile.read(buffer, 0, buffer.length);
		ShxRecord shxRecord = new ShxRecord(buffer);
		return shxRecord;
		//int temp = shxRecord.iOffset;
		//shpFile.seek(temp * 2);
		//temp = shxRecord.iContentLength;
		//return temp * 2;
	}

	/**
	 * 获得当前地图文件最大矩形范围
	 * 
	 * @return 地图矩形范围
	 */
	public Envelope getExtent() {
		return this.mExtent;
	}

	public int getShxFileLength() {
		return shxFileLength;
	}
	
	public int getRecordCount() {
		return recordCount;
	}
	
	public int getShpType() {
		return shpType;
	}
}
