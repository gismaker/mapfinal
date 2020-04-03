package com.mapfinal.platform.develop.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Map;

import javax.swing.JPanel;

import com.mapfinal.converter.ConverterKit;
import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.event.Event;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.map.Feature;
import com.mapfinal.map.GeoImage;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.Renderer;

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
	private Event event;
	
	private BufferedImage image;
	private Graphics imageGraphics = null;
	private int tx = 0, ty = 0;
	
	public GraphicsRenderEngine(JPanel panel) {
		this.panel = panel;
	}

	public Graphics getGraphics() {
		return imageGraphics !=null ? imageGraphics : graphics;
	}
	
	public Graphics2D getGraphics2D() {
		return (Graphics2D) (imageGraphics !=null ? imageGraphics : graphics);
	}
	
	public Graphics getMainGraphics() {
		return graphics;
	}
	
	public Graphics2D getMainGraphics2D() {
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
		getGraphics().translate((int)coordinate.x, (int)coordinate.y);
	}

	@Override
	public void renderEnd() {
		// TODO Auto-generated method stub
		getGraphics().dispose();
	}
	
	
	public Graphics getImageGraphics() {
		return imageGraphics;
	}
	
	public Graphics2D getImageGraphics2D() {
		return (Graphics2D) imageGraphics;
	}

	public void setImageGraphics(Graphics imageGraphics) {
		this.imageGraphics = imageGraphics;
	}

	@Override
	public void renderImageModeInit(MapContext map, Coordinate translate) {
		// TODO Auto-generated method stub
		getMainGraphics2D().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		tx = (int)translate.x;
		ty = (int)translate.y;
		if(image!=null) {
			getMainGraphics2D().drawImage(image, tx, ty, image.getWidth(), image.getHeight(), null);
			getMainGraphics2D().dispose();
			image=null;
		}
		ScenePoint sceneSize = map.getSceneSize();
		this.image = new BufferedImage(sceneSize.getSx(), sceneSize.getSy(), BufferedImage.TYPE_INT_ARGB);
		this.imageGraphics = image.getGraphics();
	}
	
	@Override
	public void translateImageMode(Coordinate coordinate) {
		// TODO Auto-generated method stub
		getImageGraphics().translate((int)coordinate.x, (int)coordinate.y);
	}

	@Override
	public void renderImageModeEnd() {
		// TODO Auto-generated method stub
		getMainGraphics2D().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		getMainGraphics2D().drawImage(image, tx, ty, image.getWidth(), image.getHeight(), null);
		getImageGraphics().dispose();
		getMainGraphics2D().dispose();
	}
	
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		panel.updateUI();
	}

	@Override
	public Event getEvent() {
		// TODO Auto-generated method stub
		return event;
	}

	@Override
	public void setEvent(Event event) {
		// TODO Auto-generated method stub
		this.event = event;
	}

	@Override
	public void setEventData(String name, Object data) {
		// TODO Auto-generated method stub
		if(event!=null) {
			event.set(name, data);
		}
	}

	@Override
	public <M> M getEventData(String name) {
		// TODO Auto-generated method stub
		return event!=null ? event.get(name) : null;
	}

	@Override
	public Map<String, Object> getEventData() {
		// TODO Auto-generated method stub
		return event!=null ? event.getData() : null;
	}

	@Override
	public String getEventAction() {
		// TODO Auto-generated method stub
		return event!=null ? event.getAction() : null;
	}

	@Override
	public void render(Renderer renderer, MapContext context, Geometry geometry) {
		// TODO Auto-generated method stub
		if("MultiPolygon".equals(geometry.getGeometryType()) || geometry instanceof MultiPolygon) {
			renderMultiPolygon(renderer, context, (MultiPolygon)geometry);
		} else if("Polygon".equals(geometry.getGeometryType()) || geometry instanceof Polygon) {
			renderPolygon(renderer, context, (Polygon)geometry);
		} 
	}
	
	public void renderMultiPolygon(Renderer renderer, MapContext context, MultiPolygon geometry) {
		int numberPolygon = geometry.getNumGeometries();
		for (int i=0; i<numberPolygon; i++) {
			renderPolygon(renderer, context, (Polygon)geometry.getGeometryN(i));
		}
	}
	
	public void renderPolygon(Renderer renderer, MapContext context, Polygon geometry) {
		int[] xPoints = new int[geometry.getNumPoints()];
		int[] yPoints = new int[geometry.getNumPoints()];
		int nPoints = geometry.getNumPoints();
		double zoom = context.getZoom();
		//List<ScenePoint> points = new ArrayList<>();
		for (int j = 0; j < geometry.getNumPoints(); j++) {
			Coordinate coordinate = geometry.getCoordinates()[j];
			Latlng latlng = Latlng.create(coordinate);
			double[] wgs1 = ConverterKit.wgs2gcj(latlng.lat(), latlng.lng());
			latlng.x  = wgs1[1];
			latlng.y  = wgs1[0];
			ScenePoint sp = context.getSceneCRS().latLngToPoint(latlng, zoom);
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
	public void renderFeature(Renderer renderer, MapContext context, Feature feature) {
		// TODO Auto-generated method stub
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
				ScenePoint sp = context.getSceneCRS().latLngToPoint(Latlng.create(gcs.getCoordinate(j)), zoom);
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
	public void renderImageFeature(Renderer renderer, MapContext context, GeoImage feature) {
		// TODO Auto-generated method stub
		if(feature==null || feature.getImage()==null) return;
		Graphics2D g2d = getGraphics2D();
		double mapZoom = context.getZoom();
		//左上角
		Coordinate c1 = feature.getTopLeft();
		//右下角
		Coordinate c2 = feature.getBottomRight();
		ScenePoint p1 = context.getSceneCRS().coordinateToPoint(c1, mapZoom);
		ScenePoint p2 = context.getSceneCRS().coordinateToPoint(c2, mapZoom);
		int x = (int) Math.round(p1.getX());
		int y = (int) Math.round(p1.getY());
		int w = (int) Math.round(p2.x) - x;
		int h = (int) Math.round(p2.y) - y;
		//System.out.println("[renderImageFeature] draw: " + x + ", " + y + ", " + w + ", " + h);
        // 绘制图片（如果宽高传的不是图片原本的宽高, 则图片将会适当缩放绘制）
        g2d.drawImage((Image) feature.getImage(), x, y, w, h, null);
        
        g2d.setColor(Color.lightGray);
        g2d.drawLine(x, y, p2.getSx(), y);
        g2d.drawLine(p2.getSx(), y, p2.getSx(), p2.getSy());
        g2d.drawLine(p2.getSx(), p2.getSy(), x, p2.getSy());
        g2d.drawLine(x, p2.getSy(), x, y);
	}

	protected boolean IsEqual(double a,double b) {
	    return Math.abs(a-b) < 0.000001;
	}
}
