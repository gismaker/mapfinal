package com.mapfinal.resource.shapefile.shpx;

import java.util.List;

import com.mapfinal.map.GeoElement;
import com.mapfinal.resource.ResourceWriter;

/**
 * DBF写入https://blog.csdn.net/weixin_34090562/article/details/85899496
 * 
 * @author yangyong
 *
 */
public class ShapefileWriter implements ResourceWriter {

	@Override
	public void open() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(List<GeoElement> features) {
		// TODO Auto-generated method stub
		for (GeoElement geoElement : features) {
			write(geoElement);
		}
	}

	@Override
	public void write(GeoElement feature) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
