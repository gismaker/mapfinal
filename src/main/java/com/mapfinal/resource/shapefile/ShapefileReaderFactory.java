package com.mapfinal.resource.shapefile;

import java.io.IOException;

import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.resource.shapefile.dbf.MapRecordSet;
import com.mapfinal.resource.shapefile.shpx.ShpRecordRandomReader;

import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;

/**
 * shx索引对应的一个record的解析工厂类
 * @author yangyong
 *
 */
public interface ShapefileReaderFactory {
	
	public Object read(ShpRecordRandomReader shpRecord);

	public Point readRecordPoint(ShpRecordRandomReader shpRecord) throws IOException;

	public MultiLineString readRecordPolyline(ShpRecordRandomReader shpRecord) throws IOException;

	public MultiPolygon readRecordPolygon(ShpRecordRandomReader shpRecord) throws IOException;

	public ShapefileFeature read(ShpRecordRandomReader shpRecord, MapRecordSet recordSet, SpatialIndexObject obj);

	public ShapefileFeature readRecordPoint(MapRecordSet recordSet, SpatialIndexObject obj) throws IOException;

	public ShapefileFeature readRecordPoint(ShpRecordRandomReader shpRecord, MapRecordSet recordSet, SpatialIndexObject obj) throws IOException;

	public ShapefileFeature readRecordPolyline(ShpRecordRandomReader shpRecord, MapRecordSet recordSet, SpatialIndexObject obj) throws IOException;

	public ShapefileFeature readRecordPolygon(ShpRecordRandomReader shpRecord, MapRecordSet recordSet, SpatialIndexObject obj) throws IOException;
}
