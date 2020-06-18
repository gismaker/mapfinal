package com.mapfinal.map;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;

import com.mapfinal.geometry.GeoKit;
import com.mapfinal.geometry.MapCS;

public class FeaturePolygon<K> extends Feature<K> {

	public FeaturePolygon(Coordinate[] coordinates) {
		this.geometry = GeoKit.createPolygon(coordinates);
	}
	
	protected MapCS getMapCS() {
		if(this.geometry==null) return null;
		Polygon polygon = (Polygon) this.geometry;
		return this.geometry==null ? null : (MapCS) polygon.getExteriorRing().getCoordinateSequence();
	}
	
	protected boolean createGeomtry(Coordinate coordinate) {
		if(this.geometry==null) {
			this.geometry = GeoKit.createPolygon(new Coordinate[]{coordinate});
			return true;
		}
		return false;
	}
	
	public void addCoordinate(Coordinate coordinate) {
		if(!createGeomtry(coordinate)) {
			MapCS mapcs = getMapCS();
			if(mapcs!=null) {
				mapcs.addCoordinate(mapcs.size()-1, coordinate);
			}
		}
	}
	
	public void addCoordinate(int pt_index, Coordinate coordinate) {
		if(!createGeomtry(coordinate)) {
			MapCS mapcs = getMapCS();
			if(mapcs!=null && pt_index < mapcs.size()) {
				mapcs.addCoordinate(pt_index, coordinate);
			}
		}
	}
	
	public void removeCoordinate(int pt_index) {
		if(this.geometry!=null) {
			MapCS mapcs = getMapCS();
			if(mapcs!=null && pt_index < mapcs.size()) {
				mapcs.removeCoordinate(pt_index);
			}
		}
	}
}
