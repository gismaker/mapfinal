package com.mapfinal.platform.develop.graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JPanel;

import com.mapfinal.Mapfinal;
import com.mapfinal.converter.ConverterKit;
import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.event.Event;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.geometry.LatlngBounds;
import com.mapfinal.map.Feature;
import com.mapfinal.map.GeoImage;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.Renderer;
import com.mapfinal.render.style.LineSymbol;
import com.mapfinal.resource.image.ImageHandle;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

/**
 * Java绘图: 使用 Graphics 类绘制线段、矩形、椭圆/圆弧/扇形、图片、文本
 * https://blog.csdn.net/xietansheng/article/details/55669157
 * @author yangyong
 *
 */
public class GraphicsRenderEngine implements RenderEngine {
	
	private Graphics graphics;
	private JPanel panel;
	
	private int cx =0, cy =0;
	
	public GraphicsRenderEngine(JPanel panel) {
		this.panel = panel;
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
	public void renderInit(Coordinate translate) {
		// TODO Auto-generated method stub
		getGraphics2D().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		translate(translate);
	}
	
	@Override
	public void translate(Coordinate coordinate) {
		// TODO Auto-generated method stub
		cx += (int)coordinate.x;
		cy += (int)coordinate.y;
		getGraphics().translate((int)coordinate.x, (int)coordinate.y);
	}

	@Override
	public void renderEnd() {
		// TODO Auto-generated method stub
//		getGraphics().dispose();
		getGraphics().translate(-cx, -cy);
		cx = cy = 0;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		panel.updateUI();
	}

	@Override
	public void render(Event event, Renderer renderer, Geometry geometry) {
		// TODO Auto-generated method stub
		if("MultiPolygon".equals(geometry.getGeometryType()) || geometry instanceof MultiPolygon) {
			renderMultiPolygon(event, renderer, (MultiPolygon)geometry);
		} else if("Polygon".equals(geometry.getGeometryType()) || geometry instanceof Polygon) {
			renderPolygon(event, renderer, (Polygon)geometry);
		} 
	}
	
	public void renderMultiPolygon(Event event, Renderer renderer, MultiPolygon geometry) {
		int numberPolygon = geometry.getNumGeometries();
		for (int i=0; i<numberPolygon; i++) {
			renderPolygon(event, renderer, (Polygon)geometry.getGeometryN(i));
		}
	}
	
	public void renderPolygon(Event event, Renderer renderer, Polygon geometry) {
		int[] xPoints = new int[geometry.getNumPoints()];
		int[] yPoints = new int[geometry.getNumPoints()];
		int nPoints = geometry.getNumPoints();
		MapContext context = event.get("map");
		double zoom = context.getZoom();
		//List<ScenePoint> points = new ArrayList<>();
		for (int j = 0; j < geometry.getNumPoints(); j++) {
			Coordinate coordinate = geometry.getCoordinates()[j];
			Latlng latlng = Latlng.create(coordinate);
			double[] wgs1 = ConverterKit.wgs2gcj(latlng.lat(), latlng.lng());
			latlng.x  = wgs1[1];
			latlng.y  = wgs1[0];
			ScenePoint sp = context.latLngToPoint(latlng, zoom);
			xPoints[j] = sp.getSx();
			yPoints[j] = sp.getSy();
			//points.add(sp);
		}
		java.awt.Polygon polygon = new java.awt.Polygon(xPoints, yPoints, nPoints);
		getGraphics2D().setColor(new Color(255, 0, 0, 125));
		getGraphics2D().drawPolygon(polygon);
		getGraphics2D().setColor(new Color(0, 255, 0, 125));
		getGraphics2D().fillPolygon(polygon);
	}
	
	int dnum = 1000;
	int[] xPoints = new int[dnum];
	int[] yPoints = new int[dnum];
	public void renderFeature(Event event, Renderer renderer, Feature feature) {
		// TODO Auto-generated method stub
		MapContext context = event.get("map");
		double zoom = context.getZoom();
		int numberPolygon = feature.getGeometry().getNumGeometries();
		for (int i=0; i<numberPolygon; i++) {
			Polygon geometry = (Polygon) feature.getGeometry().getGeometryN(i);
			LineString line = geometry.getExteriorRing();
			CoordinateSequence gcs = line.getCoordinateSequence();
			int nPoints = line.getNumPoints();
			int t = (int) zoom;
			t = t < 10 ? 10-t : 1;
			int lens = nPoints / t + 1;
			if(lens > dnum) {
				//动态长度配置
				dnum = lens;
				xPoints = Arrays.copyOf(xPoints, dnum);
				yPoints = Arrays.copyOf(yPoints, dnum);
			}
			//按照级别较小t个点的绘制，zoom越小，跳过的点越多，多边形越粗糙（t = t < 10 ? 10-t : 1;）
			//nPoints = gcs.toPolygon(context, xPoints, yPoints, nPoints);
			int s = 0;
			for(int j=0; j<nPoints; j+=t) {
				ScenePoint sp = context.latLngToPoint(Latlng.create(gcs.getCoordinate(j)), zoom);
				xPoints[s] = sp.getSx();
				yPoints[s] = sp.getSy();
				s++;
			}
			getGraphics2D().setColor(new Color(0, 255, 0, 125));
			getGraphics2D().fillPolygon(xPoints, yPoints, s);
			getGraphics2D().setColor(new Color(255, 0, 0, 125));
			getGraphics2D().drawPolygon(xPoints, yPoints, s);
		}
		//xPoints=null;
		//yPoints=null;
	}

	@Override
	public void renderImageFeature(Event event, Renderer renderer, GeoImage feature) {
		// TODO Auto-generated method stub
		if(feature==null || feature.getImage()==null) return;
		Graphics2D g2d = getGraphics2D();
		MapContext context = event.get("map");
		double mapZoom = context.getZoom();
		//左上角
		Coordinate c1 = feature.getTopLeft();
		//右下角
		Coordinate c2 = feature.getBottomRight();
		ScenePoint p1 = context.coordinateToPoint(c1, mapZoom);
		ScenePoint p2 = context.coordinateToPoint(c2, mapZoom);
		int x = (int) Math.round(p1.getX());
		int y = (int) Math.round(p1.getY());
		int w = (int) Math.round(p2.x) - x;
		int h = (int) Math.round(p2.y) - y;
		//System.out.println("[renderImageFeature] draw: " + x + ", " + y + ", " + w + ", " + h);
        // 绘制图片（如果宽高传的不是图片原本的宽高, 则图片将会适当缩放绘制）
        g2d.drawImage((Image) feature.getImage(), x, y, w, h, null);
        
//        g2d.setColor(Color.lightGray);
//        g2d.drawLine(x, y, p2.getSx(), y);
//        g2d.drawLine(p2.getSx(), y, p2.getSx(), p2.getSy());
//        g2d.drawLine(p2.getSx(), p2.getSy(), x, p2.getSy());
//        g2d.drawLine(x, p2.getSy(), x, y);
	}

	protected boolean IsEqual(double a,double b) {
	    return Math.abs(a-b) < 0.000001;
	}

	@Override
	public void renderImage(Event event, Latlng latlng, com.mapfinal.resource.image.Image image, float opacity) {
		// TODO Auto-generated method stub
		if(image==null || latlng==null) return;
		Graphics2D g2d = getGraphics2D();
		MapContext context = event.get("map");
		ScenePoint p1 = context.latLngToPoint(latlng);
		BufferedImage img = (BufferedImage) image.getData();
		int x = (int) Math.round(p1.getX()-img.getWidth()/2);
		int y = (int) Math.round(p1.getY()-img.getHeight()/2);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, opacity));
		if(event.hasData("image:scale")) {
			float scale = event.get("image:scale");
			if(scale==1) {
				g2d.drawImage(img, x, y, null);
			} else {
				int width = img.getWidth(); // 得到源图宽
		        int height = img.getHeight(); // 得到源图长
		        width = (int) (width * scale);
	            height = (int) (height * scale);
	            x = (int) Math.round(p1.getX()-width/2);
	    		y = (int) Math.round(p1.getY()-height/2);
		        Image scaleImage = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		        g2d.drawImage(scaleImage, x, y, null);
			}
		} else {
			g2d.drawImage(img, x, y, null);
		}
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
	}

	@Override
	public void renderImage(Event event, LatlngBounds latlngBounds, com.mapfinal.resource.image.Image image,
			float opacity) {
		if(image==null || latlngBounds==null) return;
		if(image.getData()==null) return;
		if(!latlngBounds.isValid()) return;
		Graphics2D g2d = getGraphics2D();
		MapContext context = event.get("map");
		double mapZoom = context.getZoom();
		//左上角
		Latlng c1 = latlngBounds.getNorthWest();
		//右下角
		Latlng c2 = latlngBounds.getSouthEast();
		ScenePoint p1 = context.latLngToPoint(c1, mapZoom);
		ScenePoint p2 = context.latLngToPoint(c2, mapZoom);
		int x = (int) Math.round(p1.getX());
		int y = (int) Math.round(p1.getY());
		int w = (int) Math.round(p2.x) - x;
		int h = (int) Math.round(p2.y) - y;
		//System.out.println("[renderImage] draw: " + x + ", " + y + ", " + w + ", " + h);
        // 绘制图片（如果宽高传的不是图片原本的宽高, 则图片将会适当缩放绘制）
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, opacity));
		//ImageHandle handle = Mapfinal.me().getFactory().getImageHandle();
		//g2d.drawImage((Image)handle.scale2(image.getData(), w, h, false), x, y, w, h, null);
		g2d.drawImage((Image)image.getData(), x, y, w, h, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
	}

	@Override
	public void renderLine(LineSymbol symbol, float sx, float sy, float ex, float ey) {
		// TODO Auto-generated method stub
		int color = symbol.getColor();
		int alpha = symbol.getAlpha();
		float width = symbol.getWidth();
		Graphics2D g2d = getGraphics2D();
		g2d.setColor(ColorUtil.intToColor(color));
		g2d.drawLine((int)sx, (int)sy, (int)ex, (int)ey);
	}
}
