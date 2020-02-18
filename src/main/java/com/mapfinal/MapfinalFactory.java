package com.mapfinal;

import com.mapfinal.operator.GeoCompress;
import com.mapfinal.render.RenderCompress;
import com.mapfinal.resource.ImageCacheService;
import com.mapfinal.resource.Resource;

public abstract class MapfinalFactory {
	
	public void init() {
		initGeometryFactory();
	}

	/**
	 * 初始化GeometryFactory, 仅系统启动时调用一次
	 */
	public abstract void initGeometryFactory();
	
	/**
	 * 获取不同渲染级别下，图形渲染时如何较少渲染点数量
	 * @param context
	 * @param nPoints
	 * @return
	 */
	public abstract RenderCompress getRenderCompress(GeoCompress.Type type);
	
	/**
	 * 创建一个图像IO
	 * @return
	 */
	public abstract ImageCacheService createImageCacheService(Resource.CacheType type, int cacheSize);
}
