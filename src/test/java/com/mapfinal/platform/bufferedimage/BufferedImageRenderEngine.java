package com.mapfinal.platform.bufferedimage;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.event.Event;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.geometry.LatlngBounds;
import com.mapfinal.geometry.ScreenPoint;
import com.mapfinal.geometry.ScreenRect;
import com.mapfinal.kit.ColorKit;
import com.mapfinal.map.Feature;
import com.mapfinal.map.GeoImage;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.Label;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.style.FillSymbol;
import com.mapfinal.render.style.LabelSymbol;
import com.mapfinal.render.style.LineSymbol;
import com.mapfinal.render.style.MarkerSymbol;
import com.mapfinal.render.style.PictureMarkerSymbol;
import com.mapfinal.render.style.SimpleFillSymbol;
import com.mapfinal.render.style.SimpleMarkerSymbol;
import com.mapfinal.render.style.Symbol;

/**
 * Java绘图: 使用 Graphics 类绘制线段、矩形、椭圆/圆弧/扇形、图片、文本
 * https://blog.csdn.net/xietansheng/article/details/55669157
 * @author yangyong
 *
 */
public class BufferedImageRenderEngine implements RenderEngine {

	private Graphics graphics;

	private int cx = 0, cy = 0;
	
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
	public void translate(Coordinate coordinate) {
		// TODO Auto-generated method stub
		cx += (int) coordinate.x;
		cy += (int) coordinate.y;
		getGraphics().translate((int) coordinate.x, (int) coordinate.y);
	}

	@Override
	public void renderStart() {
		// TODO Auto-generated method stub
		getGraphics2D().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	@Override
	public void renderEnd() {
		// TODO Auto-generated method stub
//		getGraphics().dispose();
		getGraphics().translate(-cx, -cy);
		cx = cy = 0;
	}

	int dnum = 1000;
	int[] xPoints = new int[dnum];
	int[] yPoints = new int[dnum];

	public void renderFeature(Event event, Symbol symbol, Feature feature) {
		// TODO Auto-generated method stub
		/*
		MapContext context = event.get("map");
		double zoom = context.getZoom();
		FillSymbol symbol = null;
//		if (renderer == null || renderer.getSymbol() == null || !(renderer.getSymbol() instanceof FillSymbol)) {
			symbol = new SimpleFillSymbol(ColorKit.GREEN);
			symbol.setAlpha(125);
			symbol.setOutline(new SimpleLineSymbol(ColorKit.RED, 1));
			symbol.getOutline().setAlpha(125);
//		}
		int numberPolygon = feature.getGeometry().getNumGeometries();
		for (int i = 0; i < numberPolygon; i++) {
			Polygon geometry = (Polygon) feature.getGeometry().getGeometryN(i);
			LineString line = geometry.getExteriorRing();
			CoordinateSequence gcs = line.getCoordinateSequence();
			int nPoints = line.getNumPoints();
			int t = (int) zoom;
			t = t < 10 ? 10 - t : 1;
			int lens = nPoints / t + 1;
			if (lens > dnum) {
				// 动态长度配置
				dnum = lens;
				xPoints = Arrays.copyOf(xPoints, dnum);
				yPoints = Arrays.copyOf(yPoints, dnum);
			}
			// 按照级别较小t个点的绘制，zoom越小，跳过的点越多，多边形越粗糙（t = t < 10 ? 10-t : 1;）
			// nPoints = gcs.toPolygon(context, xPoints, yPoints, nPoints);
			int s = 0;
			for (int j = 0; j < nPoints; j += t) {
				ScenePoint sp = context.latLngToPoint(Latlng.create(gcs.getCoordinate(j)), zoom);
				xPoints[s] = sp.getSx();
				yPoints[s] = sp.getSy();
				s++;
			}
			if (symbol.getAlpha() > 0) {
				Graphics2D g2d = getGraphics2D(symbol);
				g2d.fillPolygon(xPoints, yPoints, s);
			}
			if (symbol.getOutline() != null) {
				Graphics2D g2d = getGraphics2D(symbol.getOutline());
				g2d.drawPolygon(xPoints, yPoints, s);
			}
		}
		// xPoints=null;
		// yPoints=null;
		 * */
		
		if(feature==null || feature.getGeometry()==null) return;
		Geometry geometry = feature.getGeometry();
		if("MultiLineString".equals(geometry.getGeometryType()) || geometry instanceof MultiLineString) {
		    symbol = symbol!=null && symbol instanceof LineSymbol ? symbol : null;
		    renderPolyline(event, (LineSymbol) symbol, geometry);
		} else if("LineString".equals(geometry.getGeometryType()) || geometry instanceof LineString) {
		    symbol = symbol!=null && symbol instanceof LineSymbol ? symbol : null;
		    renderPolyline(event, (LineSymbol) symbol, geometry);
		} else if("LineRing".equals(geometry.getGeometryType()) || geometry instanceof LineString) {
		    symbol = symbol!=null && symbol instanceof LineSymbol ? symbol : null;
		    renderPolyline(event, (LineSymbol) symbol, geometry);
		} else if("MultiPolygon".equals(geometry.getGeometryType()) || geometry instanceof MultiPolygon) {
		    symbol = symbol!=null && symbol instanceof FillSymbol ? symbol : null;
		    if(symbol==null) {
		    	SimpleFillSymbol fs = FillSymbol.DEFAULT();
		    	renderPolygon(event, fs, geometry);
		    } else {
		    	renderPolygon(event, (FillSymbol) symbol, geometry);
		    }
		} else if("Polygon".equals(geometry.getGeometryType()) || geometry instanceof Polygon) {
		    symbol = symbol!=null && symbol instanceof FillSymbol ? symbol : null;
		    if(symbol==null) {
		    	SimpleFillSymbol fs = FillSymbol.DEFAULT();
		    	renderPolygon(event, fs, geometry);
		    } else {
		    	renderPolygon(event, (FillSymbol) symbol, geometry);
		    }
		} else if("Point".equals(geometry.getGeometryType()) || geometry instanceof org.locationtech.jts.geom.Point) {
		    symbol = symbol!=null && symbol instanceof MarkerSymbol ? symbol : null;
		    renderPoint(event, (MarkerSymbol) symbol, geometry);
		} else if("MultiPoint".equals(geometry.getGeometryType()) || geometry instanceof MultiPoint) {
		    symbol = symbol!=null && symbol instanceof MarkerSymbol ? symbol : null;
		    renderPoint(event, (MarkerSymbol) symbol, geometry);
		}
	}

	/**
	 * TexturePaint画多边形纹理图
	 */
	@Override
	public void renderImageFeature(Event event, Symbol symbol, GeoImage feature) {
		// TODO Auto-generated method stub
		if (feature == null || feature.getImage(event) == null)
			return;
		Graphics2D g2d = getGraphics2D();
		MapContext context = event.get("map");
		double mapZoom = context.getZoom();
		// 左上角
		Coordinate c1 = feature.getTopLeft();
		// 右下角
		Coordinate c2 = feature.getBottomRight();
		ScenePoint p1 = context.coordinateToPoint(c1, mapZoom);
		ScenePoint p2 = context.coordinateToPoint(c2, mapZoom);
		int x = (int) Math.round(p1.getX());
		int y = (int) Math.round(p1.getY());
		int w = (int) Math.round(p2.x) - x;
		int h = (int) Math.round(p2.y) - y;
		// System.out.println("[renderImageFeature] draw: " + x + ", " + y + ", " + w +
		// ", " + h);
		//设置为透明覆盖
		//g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
		// 绘制图片（如果宽高传的不是图片原本的宽高, 则图片将会适当缩放绘制）
		g2d.drawImage((Image) feature.getImage(event), x, y, w, h, null);
		//g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

//        g2d.setColor(Color.lightGray);
//        g2d.drawLine(x, y, p2.getSx(), y);
//        g2d.drawLine(p2.getSx(), y, p2.getSx(), p2.getSy());
//        g2d.drawLine(p2.getSx(), p2.getSy(), x, p2.getSy());
//        g2d.drawLine(x, p2.getSy(), x, y);
	}

	protected boolean IsEqual(double a, double b) {
		return Math.abs(a - b) < 0.000001;
	}

	@Override
	public void renderImage(Event event, Latlng latlng, com.mapfinal.resource.image.Image image, float opacity) {
		// TODO Auto-generated method stub
		if (image == null || latlng == null)
			return;
		if (image.getData(event) == null)
			return;
		Graphics2D g2d = getGraphics2D();
		MapContext context = event.get("map");
		ScenePoint p1 = context.latLngToPoint(latlng);
		BufferedImage img = (BufferedImage) image.getData(event);
		int x = (int) Math.round(p1.getX() - img.getWidth() * 0.5);
		int y = (int) Math.round(p1.getY() - img.getHeight() * 0.5);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, opacity));
		if (event.hasData("image:scale")) {
			float scale = event.get("image:scale");
			if (scale == 1) {
				g2d.drawImage(img, x, y, null);
			} else {
				int width = img.getWidth(); // 得到源图宽
				int height = img.getHeight(); // 得到源图长
				width = (int) (width * scale);
				height = (int) (height * scale);
				x = (int) Math.round(p1.getX() - width * 0.5);
				y = (int) Math.round(p1.getY() - height * 0.5);
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
		if (image == null || latlngBounds == null)
			return;
		if (image.getData(event) == null)
			return;
		if (!latlngBounds.isValid())
			return;
		Graphics2D g2d = getGraphics2D();
		MapContext context = event.get("map");
		double mapZoom = context.getZoom();
		// 左上角
		Latlng c1 = latlngBounds.getNorthWest();
		// 右下角
		Latlng c2 = latlngBounds.getSouthEast();
		ScenePoint p1 = context.latLngToPoint(c1, mapZoom);
		ScenePoint p2 = context.latLngToPoint(c2, mapZoom);
		int x = (int) Math.round(p1.getX());
		int y = (int) Math.round(p1.getY());
		int w = (int) Math.round(p2.x) - x;
		int h = (int) Math.round(p2.y) - y;
		// System.out.println("[renderImage] draw: " + x + ", " + y + ", " + w + ", " +
		// h);
		// 绘制图片（如果宽高传的不是图片原本的宽高, 则图片将会适当缩放绘制）
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, opacity));
		// ImageHandle handle = Mapfinal.me().getFactory().getImageHandle();
		// g2d.drawImage((Image)handle.scale2(image.getData(), w, h, false), x, y, w, h,
		// null);
		g2d.drawImage((Image) image.getData(event), x, y, w, h, null);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
	}

	@Override
	public void renderLine(LineSymbol symbol, Coordinate sp, Coordinate ep) {
		// TODO Auto-generated method stub
		Graphics2D g2d = getGraphics2D(symbol);
		g2d.drawLine((int) sp.getX(), (int) sp.getY(), (int) ep.getX(), (int) ep.getY());
	}

	private Graphics2D getGraphics2D(LineSymbol symbol) {
		if (symbol == null || !(symbol instanceof LineSymbol)) {
			symbol = LineSymbol.DEFAULT();
		}
		Graphics2D g2d = getGraphics2D();
		// 设置画笔颜色
		int color = ColorKit.argb(symbol.getAlpha(), symbol.getColor());
		g2d.setColor(ColorUtil.intToColor(color));
		// 笔画的轮廓（画笔宽度/线宽为5px）
		BasicStroke bs1 = new BasicStroke(symbol.getWidth());
		g2d.setStroke(bs1);
		return g2d;
	}

	private Graphics2D getGraphics2D(FillSymbol symbol) {
		if (symbol == null || !(symbol instanceof FillSymbol)) {
			symbol = FillSymbol.DEFAULT();
		}
		Graphics2D g2d = getGraphics2D();
		// 设置画笔颜色
		int color = ColorKit.argb(symbol.getAlpha(), symbol.getColor());
		g2d.setColor(ColorUtil.intToColor(color));
		return g2d;
	}
	
	private Graphics2D getGraphics2D(LabelSymbol symbol) {
		if (symbol == null || !(symbol instanceof LabelSymbol)) {
			symbol = new LabelSymbol();
		}
		Graphics2D g2d = getGraphics2D();
		int style = Font.PLAIN;
		if("BOLD".equalsIgnoreCase(symbol.getFontStyle())) {
			style = Font.BOLD;
		} else if("ITALIC".equalsIgnoreCase(symbol.getFontStyle())) {
			style = Font.ITALIC;
		} else if("BOLD_ITALIC".equalsIgnoreCase(symbol.getFontStyle())) {
			style = Font.BOLD + Font.ITALIC;
		}
		Font font = new Font(symbol.getFontFamily(), style, symbol.getFontSize());
		g2d.setFont(font);
		g2d.setColor(ColorUtil.colorFromHexValue(symbol.getFontColor()));
		return g2d;
	}

	@Override
	public void renderPoint(Event event, MarkerSymbol symbol, Coordinate coordinate) {
		// TODO Auto-generated method stub
		if (symbol == null || !(symbol instanceof MarkerSymbol)) {
			renderPointFill(MarkerSymbol.DEFAULT(), coordinate);
		} else if (symbol instanceof SimpleMarkerSymbol) {
			renderPointFill((SimpleMarkerSymbol) symbol, coordinate);
		} else if (symbol instanceof PictureMarkerSymbol) {
			renderPointImage(event, (PictureMarkerSymbol) symbol, coordinate);
		}
	}

	private void renderPointFill(SimpleMarkerSymbol symbol, Coordinate coordinate) {
		if (symbol == null) {
			symbol = MarkerSymbol.DEFAULT();
		}
		int width = (int) symbol.getWidth();
		int height = (int) symbol.getHeight();
		int x = (int) symbol.getX((float)coordinate.getX());
		int y = (int) symbol.getY((float)coordinate.getY());
		switch (symbol.getStyle()) {
		case CIRCLE:
			drawOval(symbol.getFill(), x, y, width, width);
			break;
		case RECT:
			drawRect(symbol.getFill(), x, y, width, height);
			break;
		case OVAL:
			drawOval(symbol.getFill(), x, y, width, height);
			break;
		default:
			break;
		}
	}
	
	/**
     * 椭圆 (实际上通过绘制360度的圆弧/扇形也能达到绘制圆/椭圆的效果)
     */
    private void drawOval(FillSymbol symbol, int x, int y, int width, int height) {
    	if (symbol == null || !(symbol instanceof FillSymbol)) {
			symbol = FillSymbol.DEFAULT();
		}
    	if (symbol.getOutline() != null) {
			Graphics2D g2d = getGraphics2D(symbol.getOutline());
			// 1. 绘制一个圆: 圆的外切矩形 左上角坐标为(0, 0), 宽高为100
	        g2d.drawOval(x, y, width, height);
		}
		if (symbol.getAlpha() > 0) {
			Graphics2D g2d = getGraphics2D(symbol);
			// 2. 填充一个椭圆
	        g2d.fillOval(x, y, width, height);
		}
    }
    
    /**
     * 矩形
     */
    private void drawRect(FillSymbol symbol, int x, int y, int width, int height) {
    	if (symbol == null || !(symbol instanceof FillSymbol)) {
			symbol = FillSymbol.DEFAULT();
		}
    	if (symbol.getOutline() != null) {
			Graphics2D g2d = getGraphics2D(symbol.getOutline());
			// 1. 绘制一个圆: 圆的外切矩形 左上角坐标为(0, 0), 宽高为100
	        g2d.drawRect(x, y, width, height);
		}
		if (symbol.getAlpha() > 0) {
			Graphics2D g2d = getGraphics2D(symbol);
			// 2. 填充一个椭圆
	        g2d.fillRect(x, y, width, height);
		}
    }

	private void renderPointImage(Event event, PictureMarkerSymbol symbol, Coordinate coordinate) {
		if (symbol == null || symbol.getImage()==null) return;
		int w = (int) symbol.getWidth();
		int h = (int) symbol.getHeight();
		int x = (int) symbol.getX((float)coordinate.getX());
		int y = (int) symbol.getY((float)coordinate.getY());
		Graphics2D g2d = getGraphics2D();
		g2d.drawImage((Image) symbol.getImage().getData(event), x, y, w, h, null);
	}

	@Override
	public void renderPolygon(Event event, FillSymbol symbol, Polygon geometry) {
		// TODO Auto-generated method stub
		int nPoints = geometry.getNumPoints();
		MapContext context = event.get("map");
		double zoom = context.getZoom();
		if (symbol == null || !(symbol instanceof FillSymbol)) {
			symbol = FillSymbol.DEFAULT();
		}
		LineString line = geometry.getExteriorRing();
		CoordinateSequence gcs = line.getCoordinateSequence();
		int t = (int) zoom;
		int lens = nPoints;
		if(nPoints > 10000) {
			t = t < 10 ? 10 - t : 1;
			lens = nPoints / t + 1;
		} else {
			t=1;
		}
		
		if (lens > dnum) {
			// 动态长度配置
			dnum = lens;
			xPoints = Arrays.copyOf(xPoints, dnum);
			yPoints = Arrays.copyOf(yPoints, dnum);
		}
		// 按照级别较小t个点的绘制，zoom越小，跳过的点越多，多边形越粗糙（t = t < 10 ? 10-t : 1;）
		// nPoints = gcs.toPolygon(context, xPoints, yPoints, nPoints);
		int s = 0;
		for (int j = 0; j < nPoints; j += t) {
			ScenePoint sp = context.latLngToPoint(Latlng.create(gcs.getCoordinate(j)), zoom);
			xPoints[s] = sp.getSx();
			yPoints[s] = sp.getSy();
			s++;
		}
		
		if (symbol.getAlpha() > 0) {
			Graphics2D g2d = getGraphics2D(symbol);
			g2d.fillPolygon(xPoints, yPoints, s);
		}
		if (symbol.getOutline() != null) {
			Graphics2D g2d = getGraphics2D(symbol.getOutline());
			g2d.drawPolygon(xPoints, yPoints, s);
		}
		/*
		int[] xPoints = new int[geometry.getNumPoints()];
		int[] yPoints = new int[geometry.getNumPoints()];
		for (int j = 0; j < geometry.getNumPoints(); j++) {
			Coordinate coordinate = geometry.getCoordinates()[j];
			Latlng latlng = Latlng.create(coordinate);
			double[] wgs1 = ConverterKit.wgs2gcj(latlng.lat(), latlng.lng());
			latlng.x = wgs1[1];
			latlng.y = wgs1[0];
			ScenePoint sp = context.latLngToPoint(latlng, zoom);
			xPoints[j] = sp.getSx();
			yPoints[j] = sp.getSy();
			// points.add(sp);
		}
		java.awt.Polygon polygon = new java.awt.Polygon(xPoints, yPoints, nPoints);
		if (symbol.getOutline() != null) {
			Graphics2D g2d = getGraphics2D(symbol.getOutline());
			g2d.drawPolygon(polygon);
		}
		if (symbol.getAlpha() > 0) {
			Graphics2D g2d = getGraphics2D(symbol);
			g2d.fillPolygon(polygon);
		}
		*/
	}
	
	@Override
	public void renderLabel(Event event, Label label) {
		// TODO Auto-generated method stub
		renderLabel(event, label.getText(), label.getPosition(), label.getSymbol());
	}
	
	@Override
	public void renderLabel(Event event, String text, Latlng center, LabelSymbol symbol) {
		MapContext context = event.get("map");
		double zoom = context.getZoom();
		ScenePoint sp = context.latLngToPoint(center, zoom);
		Graphics2D g2d = getGraphics2D(symbol);
		if(symbol.isBackground() || symbol.isBorder()) {
			int x = sp.getSx() + symbol.getOffsetX();
			int y = sp.getSy() + symbol.getOffsetY();
			ScreenPoint tp = getLabelBox(text, symbol);
			int w = Math.round(tp.x + symbol.getPadding() * 2);
			int h = Math.round(tp.y + symbol.getPadding() * 2);
			if(symbol.isBackground()) {
				g2d.setColor(ColorUtil.colorFromHexValue(symbol.getFillColor()));
				g2d.fillRect(x,y-h,w,h);//画着色块
			}
			if(symbol.isBorder()) {
				g2d.setColor(ColorUtil.colorFromHexValue(symbol.getBorderColor()));
				g2d.drawRect(x,y-h,w,h);//画线框
			}
		}
		int px = sp.getSx() + symbol.getOffsetX() + symbol.getPadding();
		int py = sp.getSy() + symbol.getOffsetY() - symbol.getPadding();
		g2d.drawString(text, px, py);
	}
	
	@Override
	public ScreenPoint getLabelBox(Label label) {
		// TODO Auto-generated method stub
		return getLabelBox(label.getText(), label.getSymbol());
	}
	
	public ScreenPoint getLabelBox(String text, LabelSymbol symbol) {
		// TODO Auto-generated method stub
		int style = Font.PLAIN;
		if("BOLD".equalsIgnoreCase(symbol.getFontStyle())) {
			style = Font.BOLD;
		} else if("ITALIC".equalsIgnoreCase(symbol.getFontStyle())) {
			style = Font.ITALIC;
		} else if("BOLD_ITALIC".equalsIgnoreCase(symbol.getFontStyle())) {
			style = Font.BOLD + Font.ITALIC;
		}
		Font font = new Font(symbol.getFontFamily(), style, symbol.getFontSize());
	
		FontMetrics fm = getGraphics().getFontMetrics(font);
		int width = fm.stringWidth(text);
		int height = fm.getAscent();
		return new ScreenPoint(width, height);
	}
	
	public ScreenRect getRect(MapContext context, String text, Latlng center, LabelSymbol symbol) {
		ScenePoint p1 = context.latLngToPoint(center);
		
		ScreenPoint lp = getLabelBox(text, symbol);
		float sw = lp.getX();
		float sh = lp.getY();
		
		int fx = symbol!=null ? symbol.getOffsetX() : 0;
		int fy = symbol!=null ? symbol.getOffsetY() : 0;
		
		int padding = symbol!=null ? symbol.getPadding() : 0;
		
		ScreenPoint sp1 = new ScreenPoint(p1.getSx() + fx - padding, p1.getSy() + fy + padding);
		ScreenPoint sp2 = new ScreenPoint(p1.getSx() + fx + padding + sw, p1.getSy() + fy + padding);
		ScreenPoint sp3 = new ScreenPoint(p1.getSx() + fx + padding + sw, p1.getSy() + fy - padding - sh);
		ScreenPoint sp4 = new ScreenPoint(p1.getSx() + fx - padding, p1.getSy() + fy - padding - sh);
		return new ScreenRect(sp1, sp2, sp3, sp4);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}