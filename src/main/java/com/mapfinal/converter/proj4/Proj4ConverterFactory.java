package com.mapfinal.converter.proj4;

import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.CoordinateTransform;
import org.osgeo.proj4j.CoordinateTransformFactory;
import org.osgeo.proj4j.io.Proj4FileReader;

import com.mapfinal.converter.CRS;
import com.mapfinal.converter.Converter;
import com.mapfinal.converter.ConverterFactory;
import com.mapfinal.converter.ConverterManager;
import com.mapfinal.kit.StringKit;

/**
 * 待完善处理OGR的空间参数
 * 
 * @author yangyong
 *
 */
public class Proj4ConverterFactory implements ConverterFactory {

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return CRS.PROJ4;
	}
	
	@Override
	public CRS getParameters(String crsName) {
		// TODO Auto-generated method stub
		Proj4FileReader proj4FileReader = new Proj4FileReader();
		String[] param = proj4FileReader.getParameters(crsName);
		return new CRS(crsName, param);
	}

	@Override
	public Converter build(CRS sourceCRS, CRS targetCRS) {
		// TODO Auto-generated method stub
		if(sourceCRS==null || targetCRS==null) return null;
		if(sourceCRS.getType()!=CRS.PROJ4 || targetCRS.getType()!=CRS.PROJ4) return null;
		CRSFactory targetFactory = new CRSFactory();
		CoordinateReferenceSystem source = targetFactory.createFromParameters(sourceCRS.getName(), sourceCRS.getParam());
        CoordinateReferenceSystem target = targetFactory.createFromParameters(targetCRS.getName(), targetCRS.getParam());
		CoordinateTransformFactory ctf = new CoordinateTransformFactory();
		CoordinateTransform transform = ctf.createTransform(source, target);
		return new Proj4Converter(transform);
	}

	@Override
	public Converter build(String sourceCRS, String targetCRS) {
		// TODO Auto-generated method stub
		CRS srcCRS = ConverterManager.me().getCRS(sourceCRS, CRS.PROJ4);
		CRS tgtCRS = ConverterManager.me().getCRS(targetCRS, CRS.PROJ4);
		return build(srcCRS, tgtCRS);
	}

	@Override
	public CRS parse(String name, String code) {
		// TODO Auto-generated method stub
		if(StringKit.isBlank(code)) return null;
		code = code.trim();
		if(code.startsWith("+")) {
			String[] params = code.split(" ");
			return new CRS(name, params, CRS.PROJ4);
		}
		return null;
	}

	@Override
	public CRS parseWKT(String name, String wkt) {
		// TODO Auto-generated method stub
		if(StringKit.isBlank(wkt)) return null;
		CRSFactory targetFactory = new CRSFactory();
		Proj4WKTParser parser = new Proj4WKTParser(targetFactory.getRegistry());
		return parser.parse(name, wkt);
	}

}
