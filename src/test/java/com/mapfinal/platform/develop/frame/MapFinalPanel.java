package com.mapfinal.platform.develop.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.Map;

import javax.swing.JPanel;

import org.locationtech.jts.geom.Coordinate;

import com.mapfinal.Mapfinal;
import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.event.Event;
import com.mapfinal.event.EventListener;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.geometry.LatlngBounds;
import com.mapfinal.geometry.ScreenPoint;
import com.mapfinal.map.Feature;
import com.mapfinal.map.layer.ArcGISBundleLayer;
import com.mapfinal.map.layer.ImageOverlay;
import com.mapfinal.map.layer.Marker;
import com.mapfinal.map.layer.PolygonLayer;
import com.mapfinal.map.layer.PolylineLayer;
import com.mapfinal.map.layer.TileLayer;
import com.mapfinal.platform.develop.GraphicsMapfinalFactory;
import com.mapfinal.platform.develop.graphics.GraphicsScene;
import com.mapfinal.resource.Resource;
import com.mapfinal.resource.image.LocalImage;
import com.mapfinal.resource.shapefile.ShapefileLayer;

public class MapFinalPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private GraphicsScene scene;

    public MapFinalPanel(MapFinalFrame frame) {
        super();
        this.scene = new GraphicsScene(this);
        //GeoMap map = new GeoMap();
        //scene.addNode(map);
        Mapfinal.me().init(scene, new GraphicsMapfinalFactory());
        Mapfinal.me().setCacheFolder("D:\\lambkit-gis-earth\\data\\cache");
       
        //tile
        //String url = "D:\\web\\gwzw\\tomcat\\webapps\\tile\\grey\\{z}\\{y}_{x}.png";
        //TileLayer tileLayer = new TileLayer("grey", url, Resource.FileType.file);
        String url = "http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer/tile/{z}/{y}/{x}";
        TileLayer tileLayer = new TileLayer("grey", url, Resource.FileType.http);
        tileLayer.addTo(Mapfinal.me().getMap());
        
      //shp
        ShapefileLayer layer = new ShapefileLayer(Mapfinal.me().getCacheFolder() + File.separator + "states.shp");
        layer.addTo(Mapfinal.me().getMap());
        layer.addListener("featureSelected", new EventListener() {
			@Override
			public boolean onEvent(Event event) {
				// TODO Auto-generated method stub
				if(event.hasData("featureSelected")) {
					Feature feature = event.get("featureSelected");
					Object id = feature.getId();
					System.out.println("Listener: featureId: " + id.toString());
					System.out.println("Listener: NAME: " + feature.getAttr("STATE_NAME"));
				}
				return false;
			}
		});
        
//        ShapefileLayer layerPl = new ShapefileLayer(Mapfinal.me().getCacheFolder() + File.separator + "map_province_region.shp");
//        layerPl.addTo(Mapfinal.me().getMap());
//        
//        ShapefileLayer layerLine = new ShapefileLayer(Mapfinal.me().getCacheFolder() + File.separator + "Rivers.shp");
//        layerLine.addTo(Mapfinal.me().getMap());
        
        Marker marker = new Marker(new Latlng(39.9, 117), new LocalImage("test", Mapfinal.me().getCacheFolder() + File.separator + "loc.png"));
        marker.setScale(0.5f);
        marker.addTo(Mapfinal.me().getMap());
        System.out.println("[layer event name] " + marker.getEventAction("Click"));
        marker.addClick(new EventListener() {
			@Override
			public boolean onEvent(Event event) {
				System.out.println(event.toString() );
				return false;
			}
		});
        
//        LatlngBounds bounds = new LatlngBounds(new Latlng(30, 110), new Latlng(35, 115));
//        ImageOverlay imgo = new ImageOverlay(bounds, new LocalImage("test", Mapfinal.me().getCacheFolder() + File.separator + "psds17397.jpg"));
//        imgo.setOpacity(0.8f);
//        imgo.addTo(Mapfinal.me().getMap());
        

//        //bundle
//        String bundle = "D:\\lambkit-gis-earth\\data\\_alllayers";
//        ArcGISBundleLayer bundleLayer = new ArcGISBundleLayer("bundle0", bundle);
//        bundleLayer.addTo(Mapfinal.me().getMap());
        
        // polyline
        PolylineLayer polyline = new PolylineLayer(new Coordinate[]{new Coordinate(0, 0), new Coordinate(50, 10)}, null);
        polyline.addCoordinate(new Coordinate(80, 10));
        polyline.addCoordinate(new Coordinate(100, 20));
        polyline.addCoordinate(new Coordinate(116, 30));
        polyline.addTo(Mapfinal.me().getMap());
        polyline.addClick(new EventListener() {
			@Override
			public boolean onEvent(Event event) {
				System.out.println("polyline picked....");
				return false;
			}
		});
        
        //polygon
        Coordinate[] coordinates = new  Coordinate[]{new Coordinate(110, 0), new Coordinate(105, 10), new Coordinate(108, 15), new Coordinate(110, 0)};
        PolygonLayer polygon = new PolygonLayer(coordinates, null);
        polygon.addCoordinate(new Coordinate(120, 10));
        polygon.addCoordinate(new Coordinate(130, 30));
        polygon.addTo(Mapfinal.me().getMap());
        polygon.addClick(new EventListener() {
			@Override
			public boolean onEvent(Event event) {
				System.out.println("polygon picked....");
				return false;
			}
		});
        
        
        //Mapfinal.me().getMap().setBackgroundRenderer(new GraphicsMapBackgroundRenderer());
        
//        Mapfinal.map().setCenter(new Latlng(35.43800418056032,102.98341606580078));
//        Mapfinal.map().setZoom(13);
        
        addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent event) {
				// TODO Auto-generated method stub
		    	scene.handleEvent(Event.by("mouseUp", "event", event).setScreenPoint(event.getX(), event.getY()));
			}
			
			@Override
			public void mousePressed(MouseEvent event) {
				// TODO Auto-generated method stub
		    	scene.handleEvent(Event.by("mouseDown", "event", event).setScreenPoint(event.getX(), event.getY()));
			}
			
			@Override
			public void mouseExited(MouseEvent event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent event) {
				// TODO Auto-generated method stub
		    	scene.handleEvent(Event.by("mouseClick", "event", event).setScreenPoint(event.getX(), event.getY()));
		    	scene.drawPick(event.getX(), event.getY());
			}
		});
        
        addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent event) {
				// TODO Auto-generated method stub
				center = Mapfinal.context().pointToLatLng(ScenePoint.by(event.getX(), event.getY()));
				Mapfinal.redraw();
			}
			
			@Override
			public void mouseDragged(MouseEvent event) {
				// TODO Auto-generated method stub
		    	scene.handleEvent(Event.by("mouseMove", "event", event).setScreenPoint(event.getX(), event.getY()));
			}
		});
        
        addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent event) {
				// TODO Auto-generated method stub
		    	scene.handleEvent(Event.by("mouseWheel", "event", event).set("rotation", event.getWheelRotation()));
			}
		});
    }

    String fpsString = "fps: 0";
    int frames = 0;
    long startTime;
    float fps;
    Latlng center = null;
    
    /**
     * 绘制面板的内容: 创建 JPanel 后会调用一次该方法绘制内容,
     * 之后如果数据改变需要重新绘制, 可调用 updateUI() 方法触发
     * 系统再次调用该方法绘制更新 JPanel 的内容。
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //setBackground(Color.lightGray); //背景色
        //Mapfinal.me().getMap().resize(this.getWidth(), this.getHeight());
        
        scene.draw(g, this.getWidth(), this.getHeight());
        
        // fps counter: count how many frames we draw and once a second calculate the
        // frames per second
        ++frames;
        long nowTime = System.currentTimeMillis();
        long deltaTime = nowTime - startTime;
        if (deltaTime > 1000) {
            float secs = (float) deltaTime / 1000f;
            fps = (float) frames / secs;
            fpsString = "fps: " + fps;
            startTime = nowTime;
            frames = 0;
        }
        drawString(g, fpsString, 10, 20);
        Map<Thread,StackTraceElement[]> map = Thread.getAllStackTraces();
        drawString(g, "ThreadSize: " + map.size(), 10, 40);
        drawString(g, "zoom: " + Mapfinal.me().getMap().getZoom(), 10, 60);
        String centerStr = center==null ? "NaN" : String.format("(lat:%f,lng:%f)", center.lat(), center.lng());
        drawString(g, "center: " + centerStr, 10, 80);
        g.dispose();
    }
    
    /**
     * 6. 文本
     */
    private void drawString(Graphics g, String text, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 设置字体样式, null 表示使用默认字体, Font.PLAIN 为普通样式, 大小为 25px
        g2d.setFont(new Font(null, Font.PLAIN, 18));
        g2d.setColor(Color.red);
        // 绘制文本, 其中坐标参数指的是文本绘制后的 左下角 的位置
        // 首次绘制需要初始化字体, 可能需要较耗时
        g2d.drawString(text, x, y);
        
    }
}
