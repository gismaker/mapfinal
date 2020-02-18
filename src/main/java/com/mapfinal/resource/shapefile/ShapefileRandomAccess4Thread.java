package com.mapfinal.resource.shapefile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mapfinal.dispatcher.Dispatcher;
import com.mapfinal.dispatcher.FeatureDispatcher;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.dispatcher.SpatialIndexer;
import com.mapfinal.dispatcher.indexer.jts.KdTreeSpatialIndexer;
import com.mapfinal.dispatcher.indexer.jts.QuadtreeSpatialIndexer;
import com.mapfinal.dispatcher.indexer.jts.STRTreeSpatialIndexer;
import com.mapfinal.map.Feature;
import com.mapfinal.resource.shapefile.dbf.MapRecordSet;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;

public class ShapefileRandomAccess4Thread {

	private ShapefileReaderFactory readerFactory;
	
	public ShapefileRandomAccess4Thread(ShapefileReaderFactory readerFactory) {
		// TODO Auto-generated constructor stub
		this.setReaderFactory(readerFactory);
	}
	
	public Dispatcher buildDispatcher(ShapefileResourceObject resource, ShapefileRandomAccess randomAccess) throws IOException {
		// TODO Auto-generated method stub
		Dispatcher dispatchar = null;
		SpatialIndexer indexer = null;
		int shpType = randomAccess.getShpType();
		switch (shpType) {
		case ShpType.NULL_SHAPE:
			break;
		case ShpType.POINT:
			indexer = new KdTreeSpatialIndexer();
			dispatchar = new FeatureDispatcher(indexer, resource);
			buildKdTreeSpatialIndex(indexer, shpType, randomAccess);
			break;
		case ShpType.POLYLINE:
			indexer = new STRTreeSpatialIndexer();
			dispatchar = new FeatureDispatcher(indexer, resource);
			buildSpatialIndex(indexer, shpType, randomAccess);
			break;
		case ShpType.POLYGON:
			indexer = new QuadtreeSpatialIndexer();
			buildSpatialIndex(indexer, shpType, randomAccess);
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
			buildSpatialIndex(indexer, shpType, randomAccess);
			break;
		case ShpType.POLYGONZ:
			indexer = new QuadtreeSpatialIndexer();
			dispatchar = new FeatureDispatcher(indexer, resource);
			buildSpatialIndex(indexer, shpType, randomAccess);
			break;
		case ShpType.MULTIPOINT_Z:
			indexer = new STRTreeSpatialIndexer();
			dispatchar = new FeatureDispatcher(indexer, resource);
			buildSpatialIndex(indexer, shpType, randomAccess);
			break;
		case ShpType.POINT_M:
			dispatchar = new FeatureDispatcher(new KdTreeSpatialIndexer(), resource);
			break;
		case ShpType.POLYLINE_M:
			indexer = new STRTreeSpatialIndexer();
			dispatchar = new FeatureDispatcher(indexer, resource);
			buildSpatialIndex(indexer, shpType, randomAccess);
			break;
		case ShpType.POLYGON_M:
			indexer = new QuadtreeSpatialIndexer();
			dispatchar = new FeatureDispatcher(indexer, resource);
			buildSpatialIndex(indexer, shpType, randomAccess);
			break;
		case ShpType.MULTIPOINT_M:
			dispatchar = new FeatureDispatcher(new KdTreeSpatialIndexer(), resource);
			break;
		case ShpType.MULTIPATCH:
			indexer = new QuadtreeSpatialIndexer();
			dispatchar = new FeatureDispatcher(indexer, resource);
			buildSpatialIndex(indexer, shpType, randomAccess);
			break;
		}
		return dispatchar;
	}
	
	public void buildKdTreeSpatialIndex(SpatialIndexer indexer, int shpType, ShapefileRandomAccess randomAccess) throws IOException {
		ShpPointContent pointContent;
		int length = 0;
		// 读入点记录,记录从1开始
		for (int i = 1; i <= randomAccess.getRecordCount(); i++) {
			ShxRecord shx = randomAccess.getShxRandomAccess().getRecordPosition(i);
			length = shx.length();
			if (length <= 0) {
				continue;
			}
			int offset = shx.offset();
			byte[] buf = new byte[length];
			randomAccess.getShpRandomAccess().readRecordBuf(buf, offset, length);
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
			if (pointContent.iShpType != randomAccess.getShpType()) {
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
	public void buildSpatialIndex(SpatialIndexer indexer, int shpType, ShapefileRandomAccess randomAccess) throws IOException {
		System.out.println("[ShpRandomAccess] buildSpatialIndex");
		int length = 0;
		for (int i = 1; i <= randomAccess.getRecordCount(); i++) {
			ShxRecord shx = randomAccess.getShxRandomAccess().getRecordPosition(i);
			//System.out.println("ShxRecord: " + shx.toString());
			length = shx.length();
			if (length <= 0) {
				continue;
			}
			int offset = shx.offset();
			byte[] buf = new byte[length];
			randomAccess.getShpRandomAccess().readRecordBuf(buf, offset, length);
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
	
	public List<Object> readRecordAll(ShapefileRandomAccess randomAccess) throws IOException {
		List<Object> points = new ArrayList<Object>();
		for (int i = 1; i <= randomAccess.getRecordCount(); i++) {
			ShxRecord shx = randomAccess.getShxRandomAccess().getRecordPosition(i);
			ShpRandomAccess shp = randomAccess.getShpRandomAccess();
			int shpType = randomAccess.getShpType();
			switch (shpType) {
			case ShpType.NULL_SHAPE:
				break;
			case ShpType.POINT: /* 已测试 */
				Point pt = readRecordPoint(shx, shp, shpType);
				pt.setUserData(i-1);
				points.add(pt);
				break;
			case ShpType.POLYLINE: /* 已测试 */
				LineString line = readRecordPolyline(shx, shp, shpType);
				line.setUserData(i-1);
				points.add(line);
				break;
			case ShpType.POLYGON:/* 已测试 */
				MultiPolygon polygon = readRecordPolygon(shx, shp, shpType);
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
	
	public Feature readRecord(SpatialIndexObject obj, ShapefileRandomAccess randomAccess) throws IOException {
		Integer i = Integer.valueOf(obj.getId());
		//System.out.println("[shp_thread] sio: " + obj.getId() + ", recordCount: " + randomAccess.getRecordCount());
		if(i==null || i <1 || i>randomAccess.getRecordCount()) return null;
		ShxRecord shx = randomAccess.getShxRandomAccess().getRecordPosition(i);
		//System.out.println("[shp_thread] shxRecord: " + shx.toString());
		ShpRandomAccess shp = randomAccess.getShpRandomAccess();
		MapRecordSet recordSet = randomAccess.getRecordSet();
		int shpType = randomAccess.getShpType();
		switch (shpType) {
		case ShpType.NULL_SHAPE:
			break;
		case ShpType.POINT: /* 已测试 */
			return readerFactory.readRecordPoint(recordSet, obj);
		case ShpType.POLYLINE: /* 已测试 */
			return readRecordPolyline(shx, shp, recordSet, obj);
		case ShpType.POLYGON:/* 已测试 */
			return readRecordPolygon(shx, shp, recordSet, obj);
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
		return null;
	}
	
	/**
	 * 读取一条Point记录
	 * @param shx
	 * @return
	 * @throws IOException
	 */
	public Point readRecordPoint(ShxRecord shx, ShpRandomAccess shp, int shpType) throws IOException {
		int length = shx.length();
		if (length <= 0) {
			return null;
		}
		int offset = shx.offset();
		byte[] buf = new byte[length];
		shp.readRecordBuf(buf, offset, length);
		ShpRecordRandomReader shpRecord = new ShpRecordRandomReader(buf, length);
		return readerFactory.readRecordPoint(shpRecord);
	}
	
	/**
	 * 读取一条ShpType.POLYLINE记录
	 * @param shx
	 * @return
	 * @throws IOException
	 */
	public LineString readRecordPolyline(ShxRecord shx, ShpRandomAccess shp, int shpType) throws IOException {
		int length = shx.length();
		if (length <= 0) {
			return null;
		}
		int offset = shx.offset();
		byte[] buf = new byte[length];
		shp.readRecordBuf(buf, offset, length);
		ShpRecordRandomReader shpRecord = new ShpRecordRandomReader(buf, length);
		return readerFactory.readRecordPolyline(shpRecord);
	}
	
	/**
	 * 读取一条ShpType.POLYGON记录
	 * @param shx
	 * @return
	 * @throws IOException
	 */
	public MultiPolygon readRecordPolygon(ShxRecord shx, ShpRandomAccess shp, int shpType) throws IOException {
		//long start = System.currentTimeMillis();
		// 读入多边形i记录
		int length = shx.length();
		if (length <= 0) {
			System.out.print("polygon_01 & ");
			return null;
		}
		int offset = shx.offset();
		byte[] buf = new byte[length];
		shp.readRecordBuf(buf, offset, length);
		ShpRecordRandomReader shpRecord = new ShpRecordRandomReader(buf, length);
		return readerFactory.readRecordPolygon(shpRecord);
	}
	
	
	/**
	 * 读取一条Point记录
	 * @param shx
	 * @return
	 * @throws IOException
	 */
	public Feature readRecordPoint(ShxRecord shx, ShpRandomAccess shp, MapRecordSet recordSet, SpatialIndexObject sio) throws IOException {
		int length = shx.length();
		if (length <= 0) {
			return null;
		}
		int offset = shx.offset();
		byte[] buf = new byte[length];
		shp.readRecordBuf(buf, offset, length);
		ShpRecordRandomReader shpRecord = new ShpRecordRandomReader(buf, length);
		return readerFactory.readRecordPoint(shpRecord, recordSet, sio);
	}
	
	/**
	 * 读取一条ShpType.POLYLINE记录
	 * @param shx
	 * @return
	 * @throws IOException
	 */
	public Feature readRecordPolyline(ShxRecord shx, ShpRandomAccess shp, MapRecordSet recordSet, SpatialIndexObject sio) throws IOException {
		int length = shx.length();
		if (length <= 0) {
			return null;
		}
		int offset = shx.offset();
		byte[] buf = new byte[length];
		shp.readRecordBuf(buf, offset, length);
		ShpRecordRandomReader shpRecord = new ShpRecordRandomReader(buf, length);
		return readerFactory.readRecordPolyline(shpRecord, recordSet, sio);
	}
	
	/**
	 * 读取一条ShpType.POLYGON记录
	 * @param shx
	 * @return
	 * @throws IOException
	 */
	public Feature readRecordPolygon(ShxRecord shx, ShpRandomAccess shp, MapRecordSet recordSet, SpatialIndexObject sio) throws IOException {
		//long start = System.currentTimeMillis();
		// 读入多边形i记录
		int length = shx.length();
		if (length <= 0) {
			System.out.print("polygon_01 & ");
			return null;
		}
		int offset = shx.offset();
		byte[] buf = new byte[length];
		shp.readRecordBuf(buf, offset, length);
		ShpRecordRandomReader shpRecord = new ShpRecordRandomReader(buf, length);
		return readerFactory.readRecordPolygon(shpRecord, recordSet, sio);
	}

	public ShapefileReaderFactory getReaderFactory() {
		return readerFactory;
	}

	public void setReaderFactory(ShapefileReaderFactory readerFactory) {
		this.readerFactory = readerFactory;
	}

}
