package com.mapfinal.example.converter;

import org.locationtech.jts.geom.Coordinate;
import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.CoordinateTransform;
import org.osgeo.proj4j.CoordinateTransformFactory;
import org.osgeo.proj4j.ProjCoordinate;

import com.mapfinal.Mapfinal;
import com.mapfinal.converter.CRS;
import com.mapfinal.converter.Converter;
import com.mapfinal.converter.ConverterManager;
import com.mapfinal.converter.proj4.Proj4WKTParser;
import com.mapfinal.converter.proj4.WKTParser;
import com.mapfinal.kit.Prop;

/**
 * 测试GEOGCS或PROJCS转proj4
 * @author yangyong
 *
 */
public class TestProj4 {

	public static void main(String[] args) {
		String wktString =
				"PROJCS[\"PCS Name = CGCS2000_3_Degree_GK_Zone_41\", \r\n" + 
				"  GEOGCS[\"GCS Name = GCS_China_Geodetic_Coordinate_System_2000\", \r\n" + 
				"    DATUM[\"Datum = China_2000\", \r\n" + 
				"      SPHEROID[\"Ellipsoid = CGCS2000\", 6378137.0, 298.257222101]], \r\n" + 
				"    PRIMEM[\"Greenwich\", 0.0], \r\n" + 
				"    UNIT[\"degree\", 0.017453292519943295], \r\n" + 
				"    AXIS[\"Geodetic longitude\", EAST], \r\n" + 
				"    AXIS[\"Geodetic latitude\", NORTH]], \r\n" + 
				"  PROJECTION[\"Transverse_Mercator\"], \r\n" + 
				"  PARAMETER[\"central_meridian\", 123.0], \r\n" + 
				"  PARAMETER[\"latitude_of_origin\", 0.0], \r\n" + 
				"  PARAMETER[\"scale_factor\", 1.0], \r\n" + 
				"  PARAMETER[\"false_easting\", 41500000.0], \r\n" + 
				"  PARAMETER[\"false_northing\", 0.0], \r\n" + 
				"  UNIT[\"m\", 1.0], \r\n" + 
				"  AXIS[\"Easting\", EAST], \r\n" + 
				"  AXIS[\"Northing\", NORTH]]";
				//"PROJCS[\"WGS 84 / Pseudo-Mercator\",GEOGCS[\"WGS 84\",DATUM[\"WGS_1984\",SPHEROID[\"WGS 84\",6378137,298.257223563,AUTHORITY[\"EPSG\",\"7030\"]],AUTHORITY[\"EPSG\",\"6326\"]],PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],UNIT[\"degree\",0.0174532925199433,AUTHORITY[\"EPSG\",\"9122\"]],AUTHORITY[\"EPSG\",\"4326\"]],PROJECTION[\"Mercator_1SP\"],PARAMETER[\"central_meridian\",0],PARAMETER[\"scale_factor\",1],PARAMETER[\"false_easting\",0],PARAMETER[\"false_northing\",0],UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],AXIS[\"X\",EAST],AXIS[\"Y\",NORTH],EXTENSION[\"PROJ4\",\"+proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +wktext +no_defs\"],AUTHORITY[\"EPSG\",\"3857\"]]";
		//"PROJCS[\"NAD83 / Massachusetts Mainland\",GEOGCS[\"NAD83\",DATUM[\"North_American_Datum_1983\",SPHEROID[\"GRS 1980\",6378137,298.257222101,AUTHORITY[\"EPSG\",\"7019\"]],AUTHORITY[\"EPSG\",\"6269\"]],PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],UNIT[\"degree\",0.01745329251994328,AUTHORITY[\"EPSG\",\"9122\"]],AUTHORITY[\"EPSG\",\"4269\"]],UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],PROJECTION[\"Lambert_Conformal_Conic_2SP\"],PARAMETER[\"standard_parallel_1\",42.68333333333333],PARAMETER[\"standard_parallel_2\",41.71666666666667],PARAMETER[\"latitude_of_origin\",41],PARAMETER[\"central_meridian\",-71.5],PARAMETER[\"false_easting\",200000],PARAMETER[\"false_northing\",750000],AUTHORITY[\"EPSG\",\"26986\"],AXIS[\"X\",EAST],AXIS[\"Y\",NORTH]]";
		//"GEOGCS[\"Lat Long for MAPINFO type 0 Datum\",DATUM[\"D_MAPINFO\",SPHEROID[\"World_Geodetic_System_of_1984\",6378137,298.257222932867]],PRIMEM[\"Greenwich\",0],UNIT[\"Degree\",0.017453292519943295]]";
		WKTParser.ParamMap lisp = WKTParser.parseString(wktString);
		System.out.println("type: " + lisp.getType());
		System.out.println("name: " + lisp.getName());
		CRSFactory targetFactory = new CRSFactory();
		Proj4WKTParser parser = new Proj4WKTParser(targetFactory.getRegistry());
		CoordinateReferenceSystem wkt = parser.parse(lisp.get("name"), lisp.getObjs());
		
		System.out.println("wkt_proj4: " + wkt.getParameterString());
		
//		String pcs = "PCS Name = CGCS2000_3_Degree_GK_Zone_41";
//		pcs = pcs.substring(pcs.indexOf("=") + 1).trim();
//		System.out.println("pcs: "+ pcs);
//		Prop prop = new Prop("epsg.txt");
//		String projcs = prop.get(pcs);
//		System.out.println("projcs: " + projcs);
		
		CoordinateReferenceSystem mecator = targetFactory.createFromName("epsg:3857");
		CRS targetCRS = ConverterManager.me().getCRS("EPSG:4326");
		CoordinateReferenceSystem wgs84 = targetFactory.createFromParameters(targetCRS.getName(), targetCRS.getParam());
		CoordinateTransformFactory ctf = new CoordinateTransformFactory();
		
		ProjCoordinate pc1 = new ProjCoordinate(-71,41, 0);
		ProjCoordinate pc2 = new ProjCoordinate(242075.00535055372, 750123.32090043, 0);
		ProjCoordinate res = new ProjCoordinate();
		
		//wgs84转墨卡托
		mecator.getProjection().project(pc1, res);
		System.out.println("mercator: " + pc1.toString() + " to " + res.toString());
		
		//wgs84转wkt
		wkt.getProjection().project(pc1, res);
		System.out.println("wkt: " + pc1.toString() + " to " + res.toString());
		
		//wgs84转wkt
		CoordinateTransform transform = ctf.createTransform(wgs84, wkt);
		res = transform.transform(pc1, res);
		System.out.println("wgs84: " + pc1.toString() + " to " + res.toString());
		//Coordinate result = new Coordinate(res.x, res.y, res.z);
		
		//wkt转wgs84
		CRS wktCRS = parser.parse(null, wktString);
		
		Converter converter = ConverterManager.me().getFactory().build(wktCRS, targetCRS);
		Coordinate pc3 = new Coordinate(41360998.3505, 3193598.870000001, 0);
		Coordinate pres = converter.transform(pc3);
		System.out.println("wkt_wgs84: " + pc3.toString() + " to " + pres.toString());
		System.out.println("--------121.57556404759752, 28.851090461561842----------------");
	}
}
