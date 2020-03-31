package com.mapfinal.converter;

import java.util.Map;

import com.mapfinal.common.SyncWriteMap;
import com.mapfinal.converter.proj4.Proj4ConverterFactory;
import com.mapfinal.kit.StringKit;

import org.locationtech.jts.geom.Coordinate;

public class ConverterManager {
	
	private static final ConverterManager me = new ConverterManager();
	
	private ConverterFactory factory;
	private Converter mainFransform;
	private Map<String, Converter> converterMap = new SyncWriteMap<String, Converter>(32, 0.25F);
	private Map<String, CRS> crsMap = new SyncWriteMap<String, CRS>(32, 0.25F);
	
	private ConverterManager() {
		factory = new Proj4ConverterFactory();
	}
	
	public static ConverterManager me() {
		return me;
	}

	public static Converter use() {
		return me.getMainFransform();
	}
	
	public static Converter use(String transformName) {
		return StringKit.notBlank(transformName) ? me.getConverterMap().get(transformName) : null;
	}
	
	public static Converter use(String transformName, String sourceCRS, String targetCRS) {
		if(StringKit.isBlank(transformName)) return null;
		Converter transform = me.getConverter(transformName);
		if(transform==null) {
			transform = use(sourceCRS, targetCRS);
			me.getConverterMap().put(transformName, transform);
		}
		return transform;
	}

	public static Converter use(String sourceCRS, String targetCRS) {
		return me.getFactory()!=null ? me.getFactory().build(sourceCRS, targetCRS) : null;
	}

	public static Coordinate transform(Coordinate coordinate) {
		return me.getMainFransform()!=null ? me.getMainFransform().transform(coordinate) : null;
	}
	
	public static Coordinate transform(String transformName, Coordinate coordinate) {
		Converter transform = me.getConverter(transformName);
		if(transform!=null) {
			return transform.transform(coordinate);
		}
		return null;
	}
	
	public static Coordinate transform(Coordinate srouce, Coordinate target) {
		return me.getMainFransform()!=null ? me.getMainFransform().transform(srouce, target) : null;
	}
	
	public static Coordinate transform(String transformName, Coordinate srouce, Coordinate target) {
		Converter transform = me.getConverter(transformName);
		if(transform!=null) {
			return transform.transform(srouce, target);
		}
		return null;
	}
	
	public Converter getConverter(String name) {
		return converterMap.get(name);
	}
	
	/**
	 * tile use epsg:3857
	 * @param coordinate
	 * @return
	 */
	public Coordinate wgs84ToMercator(Coordinate coordinate) {
		Converter converter = ConverterManager.use("wgs84ToMercator", "EPSG:4326", "EPSG:3857");
		return converter.transform(coordinate);
	}
	
	/**
	 * tile use epsg:3857
	 * @param coordinate
	 * @return
	 */
	public Coordinate mercatorToWgs84(Coordinate coordinate) {
		Converter converter = ConverterManager.use("mercatorToWgs84", "EPSG:3857", "EPSG:4326");
		return converter.transform(coordinate);
	}
	
	////////////////////////////////////////////////
	
	public String getCRSName(String crsName) {
		return crsName + "_" + factory.getType();
	}
	
	public String getCRSName(String crsName, String type) {
		return crsName + "_" + type;
	}
	
	public CRS getCRS(String crsName) {
		CRS crsParam = crsMap.get(getCRSName(crsName));
		if(crsParam==null) {
			crsParam = factory.getParameters(crsName);
			if(crsParam!=null) {
				crsMap.put(getCRSName(crsName), crsParam);
			}
		}
		return crsParam;
	}
	
	public CRS getCRS(String crsName, String type) {
		CRS crsParam = crsMap.get(getCRSName(crsName, type));
		if(crsParam==null && factory.getType().equals(type)) {
			crsParam = factory.getParameters(crsName);
			if(crsParam!=null) {
				crsMap.put(getCRSName(crsName), crsParam);
			}
		}
		return crsParam;
	}
	
	public String registCRS(String crsName, CRS crsParam) {
		String name = getCRSName(crsName, crsParam.getType());
		crsMap.put(name, crsParam);
		return name;
	}
	
	public CRS unRegistCRS(String crsName) {
		return crsMap.remove(crsName);
	}
	
/////////////////////////////////////////////////////

	public Converter getMainFransform() {
		return mainFransform;
	}

	public void setMainFransform(Converter mainFransform) {
		this.mainFransform = mainFransform;
	}

	public Map<String, CRS> getCrsMap() {
		return crsMap;
	}

	public ConverterFactory getFactory() {
		return factory;
	}

	public void setFactory(ConverterFactory factory) {
		this.factory = factory;
	}

	public Map<String, Converter> getConverterMap() {
		return converterMap;
	}
}
