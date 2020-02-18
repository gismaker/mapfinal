package com.mapfinal.render.style;

/**
 * 所有线样式的基类
 */
public abstract class LineSymbol implements Symbol {

	/**
	 * 获取线样式的透明度
	 * @return
	 */
	public abstract int	getAlpha();
	/**
	 * 获取线样式的颜色
	 * @return
	 */
	public abstract int	getColor();
	/**
	 * 设置线样式的宽度
	 * @return
	 */
	public abstract float	getWidth();
	/**
	 * 设置线样式的透明值
	 * @param alpha
	 */
	public abstract void	setAlpha(int alpha);
	/**
	 * 设置线样式的颜色值
	 * @param color
	 */
	public abstract void	setColor(int color);
	/**
	 * 获取线样式的宽度值
	 * @param width
	 */
	public abstract void	setWidth(float width);
}
