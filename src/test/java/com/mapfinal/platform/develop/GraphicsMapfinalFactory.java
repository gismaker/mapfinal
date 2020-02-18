package com.mapfinal.platform.develop;

import com.mapfinal.MapfinalFactory;
import com.mapfinal.geometry.GeoKit;
import com.mapfinal.operator.GeoCompress;
import com.mapfinal.platform.develop.cache.ImageMapCacheService;
import com.mapfinal.platform.develop.cache.ImageLruCacheService;
import com.mapfinal.render.RenderCompress;
import com.mapfinal.render.SimpleRenderCompress;
import com.mapfinal.resource.ImageCacheService;
import com.mapfinal.resource.Resource;

public class GraphicsMapfinalFactory extends MapfinalFactory {

	private RenderCompress renderCompress;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
	}
	
	@Override
	public void initGeometryFactory() {
		// TODO Auto-generated method stub
		GeoKit.initGeometryFactory();
	}
	
	@Override
	public ImageCacheService createImageCacheService(Resource.CacheType type, int cacheSize) {
		// TODO Auto-generated method stub
		switch (type) {
		case LRU:
			return new ImageLruCacheService(cacheSize);
		case SCENE:
			return new ImageMapCacheService();
		default:
			return new ImageLruCacheService(cacheSize);
		}
	}

	@Override
	public RenderCompress getRenderCompress(GeoCompress.Type type) {
		// TODO Auto-generated method stub
		if(renderCompress==null) {
			renderCompress = new SimpleRenderCompress(type);
		}
		return renderCompress;
	}

}
