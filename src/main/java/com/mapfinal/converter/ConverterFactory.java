package com.mapfinal.converter;

public interface ConverterFactory {
	public String getType();
	public CRS getParameters(String crsName);
	public Converter build(String sourceCRS, String targetCRS);
	public Converter build(CRS sourceCRS, CRS targetCRS);
	
	public CRS parse(String name, String code);
	public CRS parseWKT(String name, String wkt);
}
