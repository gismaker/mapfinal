package com.mapfinal;

import com.mapfinal.processor.GeoCompress;
import com.mapfinal.render.RenderCompress;
import com.mapfinal.resource.image.ImageHandle;

public abstract class MapfinalFactory {
	
	public void init() {
		initGeometryFactory();
	}

	public abstract String getCacheFolder();
	
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
	 * 创建一个图像处理句柄
	 * @return
	 */
	public abstract ImageHandle getImageHandle();
}
