package com.mapfinal.resource.shapefile;

import java.io.IOException;

import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.map.Feature;
import com.mapfinal.resource.shapefile.dbf.MapRecordSet;

import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;

public interface ShapefileReaderFactory {
	public Object read(ShpRecordRandomReader shpRecord);

	public Point readRecordPoint(ShpRecordRandomReader shpRecord) throws IOException;

	public LineString readRecordPolyline(ShpRecordRandomReader shpRecord) throws IOException;

	public MultiPolygon readRecordPolygon(ShpRecordRandomReader shpRecord) throws IOException;

	public Feature read(ShpRecordRandomReader shpRecord, MapRecordSet recordSet, SpatialIndexObject obj);

	public Feature readRecordPoint(MapRecordSet recordSet, SpatialIndexObject obj) throws IOException;

	public Feature readRecordPoint(ShpRecordRandomReader shpRecord, MapRecordSet recordSet, SpatialIndexObject obj) throws IOException;

	public Feature readRecordPolyline(ShpRecordRandomReader shpRecord, MapRecordSet recordSet, SpatialIndexObject obj) throws IOException;

	public Feature readRecordPolygon(ShpRecordRandomReader shpRecord, MapRecordSet recordSet, SpatialIndexObject obj) throws IOException;
}
