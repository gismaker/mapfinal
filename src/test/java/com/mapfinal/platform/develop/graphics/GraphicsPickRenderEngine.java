package com.mapfinal.platform.develop.graphics;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.event.Event;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.geometry.LatlngBounds;
import com.mapfinal.kit.ColorKit;
import com.mapfinal.map.Feature;
import com.mapfinal.map.GeoImage;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.style.SimpleMarkerSymbol;
import com.mapfinal.render.style.Symbol;
import com.mapfinal.render.style.FillSymbol;
import com.mapfinal.render.style.PictureMarkerSymbol;
import com.mapfinal.render.style.LineSymbol;
import com.mapfinal.render.style.MarkerSymbol;
import com.mapfinal.render.style.SimpleFillSymbol;
import com.mapfinal.render.style.SimpleLineSymbol;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

/**
 * Java绘图: 使用 Graphics 类绘制线段、矩形、椭圆/圆弧/扇形、图片、文本
 * https://blog.csdn.net/xietansheng/article/details/55669157
 * 
 * @author yangyong
 *
 */
public class GraphicsPickRenderEngine extends GraphicsRenderEngine {

	private Graphics graphics;
	private BufferedImage pickImage;

	public GraphicsPickRenderEngine() {
	}

	public Graphics getGraphics() {
		return graphics;
	}

	public Graphics2D getGraphics2D() {
		return (Graphics2D) graphics;
	}

	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}
	
	@Override
	public void update() {
		graphics.dispose();
	}
	
	@Override
	public void translate(Coordinate coordinate) {
		// TODO Auto-generated method stub
		getGraphics().translate((int) coordinate.x, (int) coordinate.y);
	}

	@Override
	public void renderStart() {
		pickImage = new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);
		graphics = pickImage.createGraphics();
		getGraphics2D().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	@Override
	public void renderEnd() {
		// TODO Auto-generated method stub
		graphics.dispose();
		pickImage = null;
	}
}
