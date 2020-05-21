package com.mapfinal.render;

import com.mapfinal.event.Event;
import com.mapfinal.render.style.Symbol;

/**
 * 渲染器，管理渲染样式和渲染操作类<br>
 * SimpleRenderer简单符号化渲染<br>
 * UniqueValueRenderer唯一值符号化渲染<br>
 * BiUniqueValueRender双变量唯一值符号化渲染<br>
 * ChartRender图表符号化渲染<br>
 * ClassBreakRenderer分类等级符号化渲染<br>
 * ProportionalSymbolRenderer根据属性值设置符号大小进行符号化渲染<br>
 * HeatmapRenderer热力图渲染<br>
 * DotDensityRenderer点密度符号化渲染<br>
 * BlendRenderer融合渲染<br>
 * TemporalRenderer 时态渲染<br>
 * ScaleDependentRenderer依比例尺符号化渲染<br>
 * VectorFieldRenderer向量场渲染<br>
 * RepresentationRenderer制图表达符号化渲染<br>
 * 
 * 参考ArcGIS实现https://developers.arcgis.com/javascript/3/jshelp/intro_bettermaps.html<br>
 * https://blog.csdn.net/qq_29066959/article/details/50788044<br>
 * @author yangyong
 */
public interface Renderer {
	
	public static final String EVENT_CANCELDRAW = "render:cancelDraw";

	public Symbol getSymbol();
	public void setSymbol(Symbol symbol);
	
	public Renderable getRenderable();
	public void setRenderable(Renderable renderable);
	
	public void draw(Event event, RenderEngine engine);
	public void handleEvent(Event event);
}
