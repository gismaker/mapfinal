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
import java.util.Map;

import javax.swing.JPanel;

import com.mapfinal.Mapfinal;
import com.mapfinal.event.Event;
import com.mapfinal.event.EventListener;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.geometry.LatlngBounds;
import com.mapfinal.map.layer.ImageOverlay;
import com.mapfinal.map.layer.Marker;
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
       
        //tile
        //String url = "D:\\web\\gwzw\\tomcat\\webapps\\tile\\grey\\{z}\\{y}_{x}.png";
        //TileLayer tileLayer = new TileLayer("grey", url, Resource.FileType.file);
        String url = "http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer/tile/{z}/{y}/{x}";
        TileLayer tileLayer = new TileLayer("grey", url, Resource.FileType.http);
        tileLayer.addTo(Mapfinal.me().getMap());
        
        Marker marker = new Marker(new Latlng(39.9, 117), new LocalImage("test", "E:\\前端素材\\图标-ico\\地图图标\\loc.png"));
        marker.setScale(0.5f);
        marker.addTo(Mapfinal.me().getMap());
        System.out.println("[layer event name] " + marker.getEventAction("Click"));
        marker.markerClick(new EventListener() {
			@Override
			public boolean onEvent(Event event) {
				System.out.println(event.toString() );
				return false;
			}
		});
        
        LatlngBounds bounds = new LatlngBounds(new Latlng(30, 110), new Latlng(35, 115));
        ImageOverlay imgo = new ImageOverlay(bounds, new LocalImage("test", "E:\\前端素材\\素材-地图素材\\psds17397.jpg"));
        imgo.setOpacity(0.8f);
        imgo.addTo(Mapfinal.me().getMap());
        
        
        
//        //shp
//        ShapefileLayer layer = new ShapefileLayer("D:\\GISDATA\\map_province_region.shp");
//        layer.addTo(Mapfinal.me().getMap());
//        
        //bundle
//        String bundle = "D:\\lambkit-gis-earth\\data\\_alllayers";
//        ArcGISBundleLayer bundleLayer = new ArcGISBundleLayer("default", bundle);
//        bundleLayer.addTo(Mapfinal.me().getMap());
        
        //Mapfinal.me().getMap().setBackgroundRenderer(new GraphicsMapBackgroundRenderer());
        
//        Mapfinal.me().getMap().setCenter(new Latlng(35.43800418056032,102.98341606580078));
//        Mapfinal.me().getMap().setZoom(13);
        
        addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent paramMouseEvent) {
				// TODO Auto-generated method stub
				Event event = new Event("mouseUp", "event", paramMouseEvent);
		    	event.set("x", paramMouseEvent.getX()).set("y", paramMouseEvent.getY());
		    	scene.handleEvent(event);
			}
			
			@Override
			public void mousePressed(MouseEvent paramMouseEvent) {
				// TODO Auto-generated method stub
				Event event = new Event("mouseDown", "event", paramMouseEvent);
		    	event.set("x", paramMouseEvent.getX()).set("y", paramMouseEvent.getY());
		    	scene.handleEvent(event);
			}
			
			@Override
			public void mouseExited(MouseEvent paramMouseEvent) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent paramMouseEvent) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent paramMouseEvent) {
				// TODO Auto-generated method stub
				Event event = new Event("mouseClick", "event", paramMouseEvent);
		    	event.set("x", paramMouseEvent.getX()).set("y", paramMouseEvent.getY());
		    	scene.handleEvent(event);
			}
		});
        
        addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent paramMouseEvent) {
				// TODO Auto-generated method stub
				Event event = new Event("mouseCoordinate", "event", paramMouseEvent);
		    	event.set("x", paramMouseEvent.getX()).set("y", paramMouseEvent.getY());
		    	//scene.onEvent(event);
			}
			
			@Override
			public void mouseDragged(MouseEvent paramMouseEvent) {
				// TODO Auto-generated method stub
				Event event = new Event("mouseMove", "event", paramMouseEvent);
		    	event.set("x", paramMouseEvent.getX()).set("y", paramMouseEvent.getY());
		    	scene.handleEvent(event);
			}
		});
        
        addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent paramMouseWheelEvent) {
				// TODO Auto-generated method stub
				Event event = new Event("mouseWheel", "event", paramMouseWheelEvent);
		    	event.set("rotation", paramMouseWheelEvent.getWheelRotation());
		    	scene.handleEvent(event);
			}
		});
    }

    String fpsString = "fps: 0";
    int frames = 0;
    long startTime;
    float fps;
    
    /**
     * 绘制面板的内容: 创建 JPanel 后会调用一次该方法绘制内容,
     * 之后如果数据改变需要重新绘制, 可调用 updateUI() 方法触发
     * 系统再次调用该方法绘制更新 JPanel 的内容。
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //setBackground(Color.lightGray); //背景色
        Mapfinal.me().getMap().resize(this.getWidth(), this.getHeight());
        
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
        drawString(g, "zoom: " + Mapfinal.me().getMap().getZoom(), 10, 40);
        
        Map<Thread,StackTraceElement[]> map = Thread.getAllStackTraces();
        drawString(g, "ThreadSize: " + map.size(), 10, 60);
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
