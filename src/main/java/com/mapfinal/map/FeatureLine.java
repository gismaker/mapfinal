package com.mapfinal.map;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;

import com.mapfinal.geometry.GeoKit;
import com.mapfinal.geometry.MapCS;

public class FeatureLine<K> extends Feature<K> {

	public FeatureLine(Coordinate[] coordinates) {
		this.geometry = GeoKit.createLine(coordinates);
	}
	
	protected MapCS getMapCS() {
		if(this.geometry==null) return null;
		LineString lineString = (LineString) this.geometry;
		return this.geometry==null ? null : (MapCS) lineString.getCoordinateSequence();
	}
	
	protected boolean createGeomtry(Coordinate coordinate) {
		if(this.geometry==null) {
			this.geometry = GeoKit.createLine(new Coordinate[]{coordinate});
			return true;
		}
		return false;
	}
	
	public void addCoordinate(Coordinate coordinate) {
		if(!createGeomtry(coordinate)) {
			MapCS mapcs = getMapCS();
			if(mapcs!=null) {
				mapcs.addCoordinate(coordinate);
			}
		}
	}
	
	public void addCoordinate(int pt_index, Coordinate coordinate) {
		if(!createGeomtry(coordinate)) {
			MapCS mapcs = getMapCS();
			if(mapcs!=null) {
				mapcs.addCoordinate(pt_index, coordinate);
			}
		}
	}
	
	public void removeCoordinate(int pt_index) {
		if(this.geometry!=null) {
			MapCS mapcs = getMapCS();
			if(mapcs!=null) {
				mapcs.removeCoordinate(pt_index);
			}
		}
	}
}
