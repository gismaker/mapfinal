package com.mapfinal.resource.shapefile;

import java.io.IOException;

import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.geometry.GeoKit;
import com.mapfinal.resource.shapefile.dbf.MapField;
import com.mapfinal.resource.shapefile.dbf.MapFields;
import com.mapfinal.resource.shapefile.dbf.MapRecordSet;
import com.mapfinal.resource.shapefile.dbf.RecordStart;
import com.mapfinal.resource.shapefile.shpx.BigEndian;
import com.mapfinal.resource.shapefile.shpx.BytePacket;
import com.mapfinal.resource.shapefile.shpx.ShpInfo;
import com.mapfinal.resource.shapefile.shpx.ShpPoint;
import com.mapfinal.resource.shapefile.shpx.ShpPointContent;
import com.mapfinal.resource.shapefile.shpx.ShpRecordHeader;
import com.mapfinal.resource.shapefile.shpx.ShpRecordRandomReader;
import com.mapfinal.resource.shapefile.shpx.ShpType;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;


public class DefaultShapefileReaderFactory implements ShapefileReaderFactory {

	private int shpType = ShpType.NULL_SHAPE;

	public DefaultShapefileReaderFactory() {
	}

	@Override
	public Object read(ShpRecordRandomReader shpRecord) {
		// TODO Auto-generated method stub
		if (shpRecord.getShpType() != ShpType.NULL_SHAPE) {
			shpType = shpRecord.getShpType();
		}
		try {
			switch (this.shpType) {
			case ShpType.NULL_SHAPE:
				break;
			case ShpType.POINT: /* 已测试 */
				return readRecordPoint(shpRecord);
			case ShpType.POLYLINE: /* 已测试 */
				return readRecordPolyline(shpRecord);
			case ShpType.POLYGON:/* 已测试 */
				return readRecordPolygon(shpRecord);
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ShapefileFeature read(ShpRecordRandomReader shpRecord, MapRecordSet recordSet, SpatialIndexObject obj) {
		// TODO Auto-generated method stub
		if (shpRecord.getShpType() != ShpType.NULL_SHAPE) {
			shpType = shpRecord.getShpType();
		}
		try {
			switch (this.shpType) {
			case ShpType.NULL_SHAPE:
				break;
			case ShpType.POINT: /* 已测试 */
				return readRecordPoint(shpRecord, recordSet, obj);
			case ShpType.POLYLINE: /* 已测试 */
				return readRecordPolyline(shpRecord, recordSet, obj);
			case ShpType.POLYGON:/* 已测试 */
				return readRecordPolygon(shpRecord, recordSet, obj);
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getShpType() {
		return shpType;
	}

	public void setShpType(int shpType) {
		this.shpType = shpType;
	}

	@Override
	public Point readRecordPoint(ShpRecordRandomReader shpRecord) throws IOException {
		int length = shpRecord.getLength();
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
		if (pointContent.iShpType != this.shpType) {
			return null;
		}
		Point point = GeoKit.getGeometryFactory().createPoint(new Coordinate(pointContent.x, pointContent.y));
		return point;
	}
	
	@Override
	public ShapefileFeature readRecordPoint(MapRecordSet recordSet, SpatialIndexObject obj) throws IOException {
		Point point = GeoKit.getGeometryFactory().createPoint(obj.getEnvelope().centre());
		Integer i = Integer.valueOf(obj.getId());
		point.setUserData(i - 1);
		ShapefileFeature feature = new ShapefileFeature(obj.getId(), obj, point, shpType);
		feature.setEnvelope(point.getEnvelopeInternal());
		//属性
		recordSet.move(i, RecordStart.BookmarkFirst);
		MapFields mFields = recordSet.getFields(0);
		for (short k = 0; k < mFields.getCount(); k++) {
			MapField mField = mFields.getField(k);
			feature.putAttr(mField.getName(), mField.getValue());
		}
		return feature;	
	}

	@Override
	public ShapefileFeature readRecordPoint(ShpRecordRandomReader shpRecord, MapRecordSet recordSet, SpatialIndexObject obj) throws IOException {
		Point point = GeoKit.getGeometryFactory().createPoint(obj.getEnvelope().centre());
		Integer i = Integer.valueOf(obj.getId());
		point.setUserData(i - 1);
		ShapefileFeature feature = new ShapefileFeature(obj.getId(), obj, point, shpType);
		feature.setEnvelope(point.getEnvelopeInternal());
		//属性
		recordSet.move(i, RecordStart.BookmarkFirst);
		MapFields mFields = recordSet.getFields(0);
		for (short k = 0; k < mFields.getCount(); k++) {
			MapField mField = mFields.getField(k);
			feature.putAttr(mField.getName(), mField.getValue());
		}
		return feature;	
	}

	/**
	 * 读取一条ShpType.POLYLINE记录
	 * 
	 * @param
	 * @return
	 * @throws IOException
	 */
	@Override
	public LineString readRecordPolyline(ShpRecordRandomReader shpRecord) throws IOException {
		// 获得记录头信息
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
		// line.setExtent(shpInfo.ptBox[0].x, shpInfo.ptBox[0].y,
		// shpInfo.ptBox[1].x, shpInfo.ptBox[1].y);
		// 读入多义线段索引
		byte[] partBuffer = new byte[shpInfo.iNumParts * 4];
		int readCount = shpRecord.read(partBuffer, 0, partBuffer.length);
		if (readCount != partBuffer.length) {
			return null;
		}
		// 读出的字节数组转换为整数数组
		int[] partsIndex = BigEndian.littleToIntArray(partBuffer);
		// 点坐标存储所占字节数
		int length = shpInfo.iNumPoints * ShpPoint.SIZE;
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
				System.arraycopy(data.buffer, posPointer, bTemp, 0, bTemp.length);
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
				System.arraycopy(data.buffer, posPointer, bTemp, 0, bTemp.length);
				posPointer += 8;
				double y = BigEndian.littleToDouble(bTemp);
				coordList.add(new Coordinate(x, y), true);
			}
		}
		LineString line = GeoKit.getGeometryFactory().createLineString(coordList.toCoordinateArray());
		return line;
	}

	@Override
	public ShapefileFeature readRecordPolyline(ShpRecordRandomReader shpRecord, MapRecordSet recordSet, SpatialIndexObject obj) throws IOException {
		LineString line = readRecordPolyline(shpRecord);
		Integer i = Integer.valueOf(obj.getId());
		line.setUserData(i - 1);
		ShapefileFeature feature = new ShapefileFeature(obj.getId(), obj, line, shpType);
		feature.setEnvelope(line.getEnvelopeInternal());
		//属性
		recordSet.move(i, RecordStart.BookmarkFirst);
		MapFields mFields = recordSet.getFields(0);
		for (short k = 0; k < mFields.getCount(); k++) {
			MapField mField = mFields.getField(k);
			feature.putAttr(mField.getName(), mField.getValue());
		}
		return feature;	
	}

	/**
	 * 读取一条ShpType.POLYGON记录
	 * 
	 * @param
	 * @return
	 * @throws IOException
	 */
	@Override
	public MultiPolygon readRecordPolygon(ShpRecordRandomReader shpRecord) throws IOException {
		// 获得记录头信息
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
		// polygon.setExtent(shpInfo.ptBox[0].x, shpInfo.ptBox[0].y,
		// shpInfo.ptBox[1].x, shpInfo.ptBox[1].y);
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
		int length = shpInfo.iNumPoints * ShpPoint.SIZE;
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
				System.arraycopy(data.buffer, posPointer, bTemp, 0, bTemp.length);
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
				System.arraycopy(data.buffer, posPointer, bTemp, 0, bTemp.length);
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

	@Override
	public ShapefileFeature readRecordPolygon(ShpRecordRandomReader shpRecord, MapRecordSet recordSet, SpatialIndexObject obj) throws IOException {
		MultiPolygon mpolygon = readRecordPolygon(shpRecord);
		Integer i = Integer.valueOf(obj.getId());
		mpolygon.setUserData(i - 1);
		// System.out.println("read shp polygon times: " +
		// (System.currentTimeMillis() - start));
		ShapefileFeature feature = new ShapefileFeature(obj.getId(), obj, mpolygon, shpType);
		feature.setEnvelope(mpolygon.getEnvelopeInternal());
		//属性
		recordSet.move(i, RecordStart.BookmarkFirst);
		MapFields mFields = recordSet.getFields(0);
		for (short k = 0; k < mFields.getCount(); k++) {
			MapField mField = mFields.getField(k);
			feature.putAttr(mField.getName(), mField.getValue());
		}
		return feature;	
	}
}
