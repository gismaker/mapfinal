package com.mapfinal.render.style;

import com.mapfinal.MapfinalObject;
import com.mapfinal.converter.JsonStore;

public interface Symbol extends MapfinalObject, JsonStore {
	//https://blog.csdn.net/Smart3S/article/details/81122564
	//http://www.njmap.gov.cn/geomap-api/apidoc/androidJavaApiDoc/com/geostar/android/core/symbol/package-summary.html
	
	Symbol getPickSymbol(int color);
}
