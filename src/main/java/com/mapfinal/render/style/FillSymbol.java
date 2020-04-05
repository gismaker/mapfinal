package com.mapfinal.render.style;

/**
 * 所有面样式的基类
 */
public abstract class FillSymbol implements Symbol {

	/**
	 * 获取面样式的透明度
	 * @return
	 */
	public abstract int	getAlpha();
	/**
	 * 获取面样式颜色
	 * @return
	 */
	public abstract int	getColor();
	/**
	 * 获取面样式边框样式
	 * @return
	 */
	public abstract LineSymbol	getOutline();
	/**
	 * 设置面样式的透明度
	 * @param alpha
	 */
	public abstract void	setAlpha(int alpha);
	/**
	 * 设置面样式颜色
	 * @param color
	 */
	public abstract void	setColor(int color);
	/**
	 * 设置面样式边框样式
	 * @param outline
	 */
	public abstract void	setOutline(LineSymbol outline);
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
}
