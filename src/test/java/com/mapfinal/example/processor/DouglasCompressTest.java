package com.mapfinal.example.processor;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;

import com.mapfinal.Mapfinal;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.platform.swing.JavaSwingPlatform;
import com.mapfinal.processor.DouglasCompress;
import com.mapfinal.resource.shapefile.Shapefile;
import com.mapfinal.resource.shapefile.ShapefileFeature;
import com.mapfinal.resource.shapefile.ShapefileManager;

public class DouglasCompressTest {

	public static void main(String[] args) {
		Mapfinal.me().init(new JavaSwingPlatform());
        Mapfinal.me().setCacheFolder("/Users/yangyong/data/gisdata");
        
		String filepath = "/Users/yangyong/data/gisdata/china_city_region.shp";
		Shapefile shp = ShapefileManager.me().create(filepath);
		shp.prepare(null);
		ShapefileFeature feature = shp.read(new SpatialIndexObject("1", "shp", "Polygon", null));
		for (String key : feature.getAttributes().keySet()) {
			System.out.println(key + ": " + feature.getAttr(key).toString());
		}
		long start = System.currentTimeMillis();
		//道格拉斯算法抽稀
		Coordinate[] coordinates = feature.getGeometry().getCoordinates(); 
		System.out.println("original:" + coordinates[0].x + ", " + coordinates[0].y);
		System.out.println("original:" + coordinates[coordinates.length-1].x + ", " + coordinates[coordinates.length-1].y);
		CoordinateSequence coordinateSequence = (CoordinateSequence) new CoordinateArraySequence(coordinates);
		DouglasCompress douglasCompress = new DouglasCompress();
		List<Integer> excute = douglasCompress.excute(null, coordinateSequence, 0.0000001);
		ArrayList<Coordinate> corlist = new ArrayList<>();
		for (Integer i : excute) {
		    corlist.add(coordinates[i]);
		}
		Coordinate[] objects = new Coordinate[corlist.size()];
		corlist.toArray(objects);
		System.out.println("[DouglasCompress] times: " + (System.currentTimeMillis() - start));
		System.out.println(coordinates.length);
		System.out.println(excute.size());
		System.out.println(excute.get(0)+"---"+excute.get(1));
	}
}
