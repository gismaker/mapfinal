package com.mapfinal.resource.shapefile.shpx;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import com.mapfinal.dispatcher.Dispatcher;
import com.mapfinal.dispatcher.FeatureDispatcher;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.dispatcher.SpatialIndexer;
import com.mapfinal.dispatcher.indexer.jts.KdTreeSpatialIndexer;
import com.mapfinal.dispatcher.indexer.jts.QuadtreeSpatialIndexer;
import com.mapfinal.dispatcher.indexer.jts.STRTreeSpatialIndexer;
import com.mapfinal.geometry.GeoKit;
import com.mapfinal.resource.FeatureCollection;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

/**
 * 通过RandomAccessFile方式读取文件指定位置和长度的数据
 * @author yangyong
 *
 */
public class ShpRandomAccess {

	/**
	 * Shp文件输入流
	 */
	private RandomAccessFile shpRandomAccessFile = null;
	
	/**
	 * 当前对象范围
	 */
	private Envelope mExtent;
	/**
	 * SHP文件实际长度
	 */
	private int shpFileLength;
	/**
	 * 主文件记录个数
	 */
	private int recordCount;
	/**
	 * shp对象类型
	 */
	private int shpType = ShpType.NULL_SHAPE;
	
	public ShpRandomAccess(File fShp, ShxRandomAccess shxRandomAccess) throws IOException {/* 已测试 */
		//long start = System.currentTimeMillis();
		shpRandomAccessFile = new RandomAccessFile(fShp, "r");
		// 读主文件头,长100字节
		byte[] shpBuffer = new byte[100];
		shpRandomAccessFile.read(shpBuffer, 0, shpBuffer.length);
		ShpHeader shpHeader = new ShpHeader(shpBuffer);
		if (shpHeader.iFileCode != 9994) {
			// 不是shp文件
			shpRandomAccessFile.close();
			shpRandomAccessFile = null;
			return;
		}
		if (shpHeader.iVersion != 1000) {
			// 文件版本是否正确
			shpRandomAccessFile.close();
			shpRandomAccessFile = null;
			return;
		}
		// shp类型
		this.shpType = shpHeader.iShpType;
		this.shpFileLength = shpHeader.iFileLength;
		// 保存数据最大矩形范围
		this.mExtent = new Envelope(shpHeader.dbXMin, shpHeader.dbXMax, shpHeader.dbYMin, shpHeader.dbYMax);
		// 通过索引文件计算主文件记录个数,文件长度数值以16位计
		this.recordCount = shxRandomAccess.getRecordCount();
		//System.out.println("[ShpRandomAccess] recordCount: " + recordCount);
		//System.out.println("[ShpRandomAccess] newClass times: " + (System.currentTimeMillis() - start));
	}

	public Dispatcher buildDispatcher(FeatureCollection resource, ShxRandomAccess shxRandomAccess) throws IOException {
		// TODO Auto-generated method stub
		Dispatcher dispatchar = null;
		SpatialIndexer indexer = null;
		switch (this.shpType) {
		case ShpType.NULL_SHAPE:
			break;
		case ShpType.POINT:
			indexer = new KdTreeSpatialIndexer();
			dispatchar = new FeatureDispatcher(indexer, resource);
			buildKdTreeSpatialIndex(indexer, shpType, shxRandomAccess);
			break;
		case ShpType.POLYLINE:
			indexer = new STRTreeSpatialIndexer();
			dispatchar = new FeatureDispatcher(indexer, resource);
			buildSpatialIndex(indexer, shpType, shxRandomAccess);
			break;
		case ShpType.POLYGON:
			indexer = new QuadtreeSpatialIndexer();
			buildSpatialIndex(indexer, shpType, shxRandomAccess);
			dispatchar = new FeatureDispatcher(indexer, resource);
			break;
		case ShpType.MULTIPOINT:
			dispatchar = new FeatureDispatcher(new KdTreeSpatialIndexer(), resource);
			break;
		case ShpType.POINT_Z:
			dispatchar = new FeatureDispatcher(new KdTreeSpatialIndexer(), resource);
			break;
		case ShpType.POLYLINE_Z:
			indexer = new QuadtreeSpatialIndexer();
			dispatchar = new FeatureDispatcher(indexer, resource);
			buildSpatialIndex(indexer, shpType, shxRandomAccess);
			break;
		case ShpType.POLYGONZ:
			indexer = new QuadtreeSpatialIndexer();
			dispatchar = new FeatureDispatcher(indexer, resource);
			buildSpatialIndex(indexer, shpType, shxRandomAccess);
			break;
		case ShpType.MULTIPOINT_Z:
			indexer = new STRTreeSpatialIndexer();
			dispatchar = new FeatureDispatcher(indexer, resource);
			buildSpatialIndex(indexer, shpType, shxRandomAccess);
			break;
		case ShpType.POINT_M:
			dispatchar = new FeatureDispatcher(new KdTreeSpatialIndexer(), resource);
			break;
		case ShpType.POLYLINE_M:
			indexer = new STRTreeSpatialIndexer();
			dispatchar = new FeatureDispatcher(indexer, resource);
			buildSpatialIndex(indexer, shpType, shxRandomAccess);
			break;
		case ShpType.POLYGON_M:
			indexer = new QuadtreeSpatialIndexer();
			dispatchar = new FeatureDispatcher(indexer, resource);
			buildSpatialIndex(indexer, shpType, shxRandomAccess);
			break;
		case ShpType.MULTIPOINT_M:
			dispatchar = new FeatureDispatcher(new KdTreeSpatialIndexer(), resource);
			break;
		case ShpType.MULTIPATCH:
			indexer = new QuadtreeSpatialIndexer();
			dispatchar = new FeatureDispatcher(indexer, resource);
			buildSpatialIndex(indexer, shpType, shxRandomAccess);
			break;
		}
		return dispatchar;
	}
	
	public void buildKdTreeSpatialIndex(SpatialIndexer indexer, int shpType, ShxRandomAccess shxRandomAccess) throws IOException {
		ShpPointContent pointContent;
		int length = 0;
		// 读入点记录,记录从1开始
		for (int i = 1; i <= recordCount; i++) {
			ShxRecord shx = shxRandomAccess.getRecordPosition(i);
			length = shx.length();
			if (length <= 0) {
				continue;
			}
			int offset = shx.offset();
			byte[] buf = new byte[length];
			readRecordBuf(buf, offset, length);
			ShpRecordRandomReader shpRecord = new ShpRecordRandomReader(buf, length);
			// 获得记录头信息
			ShpRecordHeader shpRecordHeader = shpRecord.getRecordHeader();
			if (shpRecordHeader == null) {
				break;
			}
			// 记录内容长度是否与shp实体大小一致,索引记录长度是否与记录内容长度一致
			if (shpRecordHeader.iContentLength * 2 != ShpPointContent.SIZE
					|| shpRecordHeader.iContentLength * 2 != length) {
				break;
			}
			byte[] ptBytesContent = new byte[ShpPointContent.SIZE];
			shpRecord.read(ptBytesContent, 0, ptBytesContent.length);
			pointContent = new ShpPointContent(ptBytesContent);
			// 类型是否匹配
			if (pointContent.iShpType != this.shpType) {
				break;
			}
			Coordinate p =new Coordinate(pointContent.x, pointContent.y);
			String id = String.valueOf(i);
			String dataType = "shp";
			String geometryType = "POINT";
			Envelope env = new Envelope(p);
			SpatialIndexObject sio = new SpatialIndexObject(id, dataType, geometryType, env);
			((KdTreeSpatialIndexer)indexer).insert(p, sio);
		}
	}
	
	/**
	 * 构建索引
	 * @param indexer
	 * @param shpType
	 * @param shxRandomAccess
	 * @throws IOException
	 */
	public void buildSpatialIndex(SpatialIndexer indexer, int shpType, ShxRandomAccess shxRandomAccess) throws IOException {
		System.out.println("[ShpRandomAccess] buildSpatialIndex");
		int length = 0;
		for (int i = 1; i <= recordCount; i++) {
			ShxRecord shx = shxRandomAccess.getRecordPosition(i);
			//System.out.println("ShxRecord: " + shx.toString());
			length = shx.length();
			if (length <= 0) {
				continue;
			}
			int offset = shx.offset();
			byte[] buf = new byte[length];
			readRecordBuf(buf, offset, length);
			ShpRecordRandomReader shpRecord = new ShpRecordRandomReader(buf, length);
			// 获得记录头
			ShpRecordHeader shpRecordHeader = shpRecord.getRecordHeader();
			if (shpRecordHeader == null) {
				continue;
			}
			ShpInfo shpInfo = null;
			try {
				shpInfo = shpRecord.getShpInfo();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (shpInfo == null) {
				continue;
			}
			// 类型不匹配
			if (shpInfo.iShpType != shpType) {
				continue;
			}
			if (shpInfo.iNumParts * 4 > ShpRecordRandomReader.MAX_BUFFER_SIZE) {
				continue;
			}
			// 计算对象内容实际长度
			int actualLength = ShpInfo.SIZE + shpInfo.iNumParts * 4;
			actualLength += shpInfo.iNumPoints * ShpPoint.SIZE;
			// 判断实际长度是否与索引文件中记录一致
			if (shpRecordHeader.iContentLength * 2 != actualLength) {
				continue;
			}
			// 设置shp矩形范围
			String id = String.valueOf(i);
			String dataType = "shp";
			String geometryType = ShpType.shpTypeName(shpType);
			Envelope env = new Envelope(shpInfo.ptBox[0].x, shpInfo.ptBox[1].x, shpInfo.ptBox[0].y, shpInfo.ptBox[1].y);
			//System.out.println("sio env: " + env.toString());
			SpatialIndexObject sio = new SpatialIndexObject(id, dataType, geometryType, env);
			if(indexer instanceof QuadtreeSpatialIndexer) {
				QuadtreeSpatialIndexer qsi = (QuadtreeSpatialIndexer) indexer;
				qsi.insert(sio.getEnvelope(), sio);
			} else if(indexer instanceof STRTreeSpatialIndexer) {
				STRTreeSpatialIndexer ssi = (STRTreeSpatialIndexer) indexer;
				ssi.insert(sio.getEnvelope(), sio);
			} else {
				continue;
			}
		}
	}

	/**
	 * 关闭
	 * @throws IOException
	 */
	public void close() throws IOException {
		// TODO Auto-generated method stub
		if(shpRandomAccessFile!=null) {
			shpRandomAccessFile.close();
		}
	}
	
	public List<Object> readRecordAll(ShxRandomAccess shxRandomAccess) throws IOException {
		List<Object> points = new ArrayList<Object>();
		for (int i = 1; i <= recordCount; i++) {
			ShxRecord shx = shxRandomAccess.getRecordPosition(i);
			switch (this.shpType) {
			case ShpType.NULL_SHAPE:
				break;
			case ShpType.POINT: /* 已测试 */
				Point pt = readRecordPoint(shx);
				pt.setUserData(i-1);
				points.add(pt);
				break;
			case ShpType.POLYLINE: /* 已测试 */
				LineString line = readRecordPolyline(shx);
				line.setUserData(i-1);
				points.add(line);
				break;
			case ShpType.POLYGON:/* 已测试 */
				MultiPolygon polygon = readRecordPolygon(shx);
				polygon.setUserData(i-1);
				points.add(polygon);
				break;
			case ShpType.MULTIPOINT:
				break;
			case ShpType.POINT_Z:
				break;
			case ShpType.POLYLINE_Z:
				break;
			case ShpType.POLYGONZ:
				break;
			case ShpType.MULTIPOINT_Z:
				break;
			case ShpType.POINT_M:
				break;
			case ShpType.POLYLINE_M:
				break;
			case ShpType.POLYGON_M:
				break;
			case ShpType.MULTIPOINT_M:
				break;
			case ShpType.MULTIPATCH:
				break;
			}
		}
		return points;
	}
	
	/**
	 * 读取一条Point记录
	 * @param shx
	 * @return
	 * @throws IOException
	 */
	public Point readRecordPoint(ShxRecord shx) throws IOException {
		int length = shx.length();
		if (length <= 0) {
			return null;
		}
		int offset = shx.offset();
		byte[] buf = new byte[length];
		readRecordBuf(buf, offset, length);
		ShpRecordRandomReader shpRecord = new ShpRecordRandomReader(buf, length);
		// 获得记录头信息
		ShpRecordHeader shpRecordHeader = shpRecord.getRecordHeader();
		if (shpRecordHeader == null) {
			return null;
		}
		// 记录内容长度是否与shp实体大小一致,索引记录长度是否与记录内容长度一致
		if (shpRecordHeader.iContentLength * 2 != ShpPointContent.SIZE
				|| shpRecordHeader.iContentLength * 2 != length) {
			return null;
		}
		byte[] ptBytesContent = new byte[ShpPointContent.SIZE];
		shpRecord.read(ptBytesContent, 0, ptBytesContent.length);
		ShpPointContent pointContent = new ShpPointContent(ptBytesContent);
		// 类型是否匹配
		if (pointContent.iShpType !=this.shpType) {
			return null;
		}
		Point point = GeoKit.getGeometryFactory().createPoint(new Coordinate(pointContent.x, pointContent.y));
		return point;
	}
	
	/**
	 * 读取一条ShpType.POLYLINE记录
	 * @param shx
	 * @return
	 * @throws IOException
	 */
	public LineString readRecordPolyline(ShxRecord shx) throws IOException {
		int length = shx.length();
		if (length <= 0) {
			return null;
		}
		int offset = shx.offset();
		byte[] buf = new byte[length];
		readRecordBuf(buf, offset, length);
		ShpRecordRandomReader shpRecord = new ShpRecordRandomReader(buf, length);
		// 获得记录头
		ShpRecordHeader shpRecordHeader = shpRecord.getRecordHeader();
		if (shpRecordHeader == null) {
			return null;
		}
		ShpInfo shpInfo = shpRecord.getShpInfo();
		if (shpInfo == null) {
			return null;
		}
		// 类型不匹配
		if (shpInfo.iShpType != ShpType.POLYLINE) {
			return null;
		}
		if (shpInfo.iNumParts * 4 > ShpRecordRandomReader.MAX_BUFFER_SIZE) {
			return null;
		}
		// 计算对象内容实际长度
		int actualLength = ShpInfo.SIZE + shpInfo.iNumParts * 4;
		actualLength += shpInfo.iNumPoints * ShpPoint.SIZE;
		// 判断实际长度是否与索引文件中记录一致
		if (shpRecordHeader.iContentLength * 2 != actualLength) {
			return null;
		}
		// 设置shp矩形范围
		//line.setExtent(shpInfo.ptBox[0].x, shpInfo.ptBox[0].y, shpInfo.ptBox[1].x, shpInfo.ptBox[1].y);
		// 读入多义线段索引
		byte[] partBuffer = new byte[shpInfo.iNumParts * 4];
		int readCount = shpRecord.read(partBuffer, 0, partBuffer.length);
		if (readCount != partBuffer.length) {
			return null;
		}
		// 读出的字节数组转换为整数数组
		int[] partsIndex = BigEndian.littleToIntArray(partBuffer);
		// 点坐标存储所占字节数
		length = shpInfo.iNumPoints * ShpPoint.SIZE;
		// 初始化缓冲区数据
		BytePacket data = shpRecord.readPoint(length);
		int dataLength = data.actualLength;
		boolean isEof = data.isEof;
		length = data.length;
		if (data.actualLength <= 0) {
			return null;
		}
		int firstIndex = 0, nextFirstIndex = 0;
		int posPointer = 0;
		CoordinateList coordList = new CoordinateList();
		for (int j = 0; j < shpInfo.iNumParts; j++) {
			if (j == shpInfo.iNumParts - 1) {
				// 本段第一个顶点索引
				firstIndex = partsIndex[j];
				// 下一个段第一个顶点索引
				nextFirstIndex = shpInfo.iNumPoints;
			} else {
				firstIndex = partsIndex[j];
				nextFirstIndex = partsIndex[j + 1];
			}
			// 处理第i段的顶点
			for (; firstIndex < nextFirstIndex; firstIndex++) {
				// 需要读入数据更新缓冲区
				if (posPointer == dataLength && !isEof) {
					data = shpRecord.readPoint(length);
					dataLength = data.actualLength;
					isEof = data.isEof;
					length = data.length;
					if (dataLength <= 0) {
						return null;
					}
					posPointer = 0;
				}
				byte[] bTemp = new byte[8];
				System.arraycopy(data.buffer, posPointer, bTemp, 0,
						bTemp.length);
				double x = BigEndian.littleToDouble(bTemp);
				posPointer += 8;
				// 需要读入数据更新缓冲区
				if (posPointer == dataLength && !isEof) {
					data = shpRecord.readPoint(length);
					dataLength = data.actualLength;
					isEof = data.isEof;
					length = data.length;
					if (dataLength <= 0) {
						return null;
					}
					posPointer = 0;
				}
				System.arraycopy(data.buffer, posPointer, bTemp, 0,
						bTemp.length);
				posPointer += 8;
				double y = BigEndian.littleToDouble(bTemp);
				coordList.add(new Coordinate(x, y), true);
			}
		}
		LineString line = GeoKit.getGeometryFactory().createLineString(coordList.toCoordinateArray());
		return line;
	}
	
	/**
	 * 读取一条ShpType.POLYGON记录
	 * @param shx
	 * @return
	 * @throws IOException
	 */
	public MultiPolygon readRecordPolygon(ShxRecord shx) throws IOException {
		//long start = System.currentTimeMillis();
		// 读入多边形i记录
		int length = shx.length();
		if (length <= 0) {
			System.out.print("polygon_01 & ");
			return null;
		}
		int offset = shx.offset();
		byte[] buf = new byte[length];
		readRecordBuf(buf, offset, length);
		ShpRecordRandomReader shpRecord = new ShpRecordRandomReader(buf, length);
		// 获得记录头
		ShpRecordHeader shpRecordHeader = shpRecord.getRecordHeader();
		if (shpRecordHeader == null) {
			System.out.print("polygon_02 & ");
			return null;
		}
		ShpInfo shpInfo = shpRecord.getShpInfo();
		if (shpInfo == null) {
			System.out.print("polygon_03 & ");
			return null;
		}
		// 判断类型是否匹配
		if (shpInfo.iShpType != ShpType.POLYGON) {
			System.out.print("polygon_04 & ShpType: " + shpInfo.iShpType);
			return null;
		}
		if (shpInfo.iNumParts * 4 > ShpRecordRandomReader.MAX_BUFFER_SIZE) {
			// 复合多边形中的多边形个数大于最大缓冲区长度，忽略该对象
			System.out.print("polygon_05 & ");
			return null;
		}
		// 计算对象内容实际长度
		int actualLength = ShpInfo.SIZE + shpInfo.iNumParts * 4;
		actualLength += shpInfo.iNumPoints * ShpPoint.SIZE;
		// 判断实际长度是否与索引文件中记录的一致
		if (shpRecordHeader.iContentLength * 2 != actualLength) {
			System.out.print("polygon_06 & ");
			return null;
		}
		// 设置shp矩形范围
		//polygon.setExtent(shpInfo.ptBox[0].x, shpInfo.ptBox[0].y, shpInfo.ptBox[1].x, shpInfo.ptBox[1].y);
		byte[] partBuffer = new byte[shpInfo.iNumParts * 4];
		// 读入复合多边形段索引,因为转换成了整形
		int readCount = shpRecord.read(partBuffer, 0, partBuffer.length);
		if (readCount != shpInfo.iNumParts * 4) {
			System.out.print("polygon_07 & ");
			return null;
		}
		// 读出的字节数组转换为整数数组
		int[] partsIndex = BigEndian.littleToIntArray(partBuffer);
		// 点坐标存储所占字节数
		length = shpInfo.iNumPoints * ShpPoint.SIZE;
		// 初始化缓冲区数据
		BytePacket data = shpRecord.readPoint(length);
		int dataLength = data.actualLength;
		boolean isEof = data.isEof;
		length = data.length;
		if (data.actualLength <= 0) {
			System.out.print("polygon_08 & ");
			return null;
		}
		int firstIndex = 0, nextFirstIndex = 0;
		int posPointer = 0;
		Polygon[] polygons = new Polygon[shpInfo.iNumParts];
		for (int j = 0; j < shpInfo.iNumParts; j++) {
			CoordinateList coordList = new CoordinateList();
			if (j == shpInfo.iNumParts - 1) {
				// 本段第一个顶点索引
				firstIndex = partsIndex[j];
				// 下一个段第一个顶点索引
				nextFirstIndex = shpInfo.iNumPoints;
			} else {
				firstIndex = partsIndex[j];
				nextFirstIndex = partsIndex[j + 1];
			}
			// 处理第i段的顶点
			for (; firstIndex < nextFirstIndex; firstIndex++) {
				// 需要读入数据更新缓冲区
				if (posPointer == dataLength && !isEof) {
					data = shpRecord.readPoint(length);
					dataLength = data.actualLength;
					isEof = data.isEof;
					length = data.length;
					if (dataLength <= 0) {
						return null;
					}
					posPointer = 0;
				}
				byte[] bTemp = new byte[8];
				System.arraycopy(data.buffer, posPointer, bTemp, 0,
						bTemp.length);
				double x = BigEndian.littleToDouble(bTemp);
				posPointer += 8;
				// 需要读入数据更新缓冲区
				if (posPointer == dataLength && !isEof) {
					data = shpRecord.readPoint(length);
					dataLength = data.actualLength;
					isEof = data.isEof;
					length = data.length;
					if (dataLength <= 0) {
						System.out.print("polygon_09 & ");
						return null;
					}
					posPointer = 0;
				}
				System.arraycopy(data.buffer, posPointer, bTemp, 0,
						bTemp.length);
				posPointer += 8;
				double y = BigEndian.littleToDouble(bTemp);
				coordList.add(new Coordinate(x, y), true);
			}
			Polygon polygon = GeoKit.getGeometryFactory().createPolygon(coordList.toCoordinateArray());
			polygons[j] = polygon;
		}
		MultiPolygon mpolygon = GeoKit.getGeometryFactory().createMultiPolygon(polygons);
		return mpolygon;
	}
	
	public RandomAccessFile getShpRandomAccessFile() {
		return shpRandomAccessFile;
	}
	
	public void readRecordBuf(byte[] buf, int offset, int length) throws IOException {
		synchronized (shpRandomAccessFile) {
			shpRandomAccessFile.seek(offset);
			shpRandomAccessFile.read(buf, 0, buf.length);
		}
	}
	
	/**
	 * 获得当前地图文件最大矩形范围
	 * 
	 * @return 地图矩形范围
	 */
	public Envelope getExtent() {
		return this.mExtent;
	}

	public int getShpFileLength() {
		return shpFileLength;
	}
	
	public int getRecordCount() {
		return recordCount;
	}
	
	public int getShpType() {
		return shpType;
	}
}
