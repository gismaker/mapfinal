package com.mapfinal.converter;

/**
 * 格式转换
 * <br> geojson 转  kml
 * <br> geojson 转 geometry
 * <br> geojson 转 pbf
 * @author yangyong
 *
 * @param <S>
 * @param <T>
 */
public interface DataConverter<S, T> {
	  public abstract T convert(S paramS);
}
