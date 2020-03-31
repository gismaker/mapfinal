package com.mapfinal.platform.develop.frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

import com.mapfinal.Mapfinal;
import com.mapfinal.event.Event;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.map.layer.ArcGISBundleLayer;
import com.mapfinal.map.layer.TileLayer;
import com.mapfinal.platform.develop.GraphicsMapfinalFactory;
import com.mapfinal.platform.develop.graphics.GraphicsMapBackgroundRenderer;
import com.mapfinal.platform.develop.graphics.GraphicsScene;
import com.mapfinal.resource.Resource;
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
        String url = "D:\\web\\gwzw\\tomcat\\webapps\\tile\\grey\\{z}\\{y}_{x}.png";
        TileLayer tileLayer = new TileLayer("grey", url, Resource.FileType.file);
        //String url = "http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer/tile/{z}/{y}/{x}";
        //TileLayer tileLayer = new TileLayer("grey", url, Resource.FileType.http);
        tileLayer.addTo(Mapfinal.me().getMap());
        
        //shp
        ShapefileLayer layer = new ShapefileLayer("D:\\GISDATA\\map_province_region.shp");
        layer.addTo(Mapfinal.me().getMap());
        
        //bundle
        String bundle = "D:\\lambkit-gis-earth\\data\\_alllayers";
        ArcGISBundleLayer bundleLayer = new ArcGISBundleLayer("default", bundle);
        //String url = "http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer/tile/{z}/{y}/{x}";
        //TileLayer tileLayer = new TileLayer("grey", url, Resource.FileType.http);
        bundleLayer.addTo(Mapfinal.me().getMap());
        
        Mapfinal.me().getMap().setBackgroundRenderer(new GraphicsMapBackgroundRenderer());
        
        Mapfinal.me().getMap().setCenter(new Latlng(35.43800418056032,102.98341606580078));
        Mapfinal.me().getMap().setZoom(13);
        
        addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent paramMouseEvent) {
				// TODO Auto-generated method stub
				Event event = new Event("mouseUp", "event", paramMouseEvent);
		    	event.set("x", paramMouseEvent.getX()).set("y", paramMouseEvent.getY());
		    	scene.onEvent(event);
			}
			
			@Override
			public void mousePressed(MouseEvent paramMouseEvent) {
				// TODO Auto-generated method stub
				Event event = new Event("mouseDown", "event", paramMouseEvent);
		    	event.set("x", paramMouseEvent.getX()).set("y", paramMouseEvent.getY());
		    	scene.onEvent(event);
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
		    	scene.onEvent(event);
			}
		});
        
        addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent paramMouseWheelEvent) {
				// TODO Auto-generated method stub
				Event event = new Event("mouseWheel", "event", paramMouseWheelEvent);
		    	event.set("rotation", paramMouseWheelEvent.getWheelRotation());
		    	scene.onEvent(event);
			}
		});
    }

    /**
     * 绘制面板的内容: 创建 JPanel 后会调用一次该方法绘制内容,
     * 之后如果数据改变需要重新绘制, 可调用 updateUI() 方法触发
     * 系统再次调用该方法绘制更新 JPanel 的内容。
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.lightGray); //背景色
        Mapfinal.me().getMap().resize(this.getWidth(), this.getHeight());
        scene.onRender(g, this.getWidth(), this.getHeight());
    }
}
