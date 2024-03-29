package com.mapfinal;

import com.mapfinal.processor.simplify.GeoSimplifier;
import com.mapfinal.render.RenderCompress;
import com.mapfinal.resource.image.ImageHandle;

public interface Platform {

	/**
	 * 初始化
	 */
	public void init();
	
	/**
	 * 系统缓存地址
	 * @return
	 */
	public String getCacheFolder();
	
	/**
	 * 初始化GeometryFactory, 仅系统启动时调用一次
	 */
	public void initGeometryFactory();
	
	/**
	 * 获取不同渲染级别下，图形渲染时如何较少渲染点数量
	 * @param context
	 * @param nPoints
	 * @return
	 */
	public RenderCompress getRenderCompress(GeoSimplifier.Type type);
	
	/**
	 * 创建一个图像处理句柄
	 * @return
	 */
	public ImageHandle getImageHandle();
	
	/**
	 * 点拾取的时候，放大多少像素
	 * @return
	 */
	default public int pickPointZommPixel() {
		return 5;
	}
	
	/**
	 * 线拾取的时候，放大多少像素
	 * @return
	 */
	default public int pickLineZommPixel() {
		return 10;
	}
}
