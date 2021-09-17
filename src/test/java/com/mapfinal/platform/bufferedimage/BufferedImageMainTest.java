package com.mapfinal.platform.bufferedimage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.mapfinal.Mapfinal;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.map.layer.TileLayer;
import com.mapfinal.resource.Resource;

public class BufferedImageMainTest {
	
	public static void main(String[] args) {
		BufferedImageScene scene = new BufferedImageScene();
		Mapfinal.me().init(scene, new BufferedImagePlatform());
        Mapfinal.me().setCacheFolder("/Users/yangyong/data/gisdata");
        
        String url = "http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer/tile/{z}/{y}/{x}";
        TileLayer tileLayer = new TileLayer("grey", url, Resource.FileType.http);
        tileLayer.addTo(Mapfinal.me().getMap());
        Mapfinal.map().setCenter(new Latlng(42.946,89.183));
        Mapfinal.map().setZoom(17);
        
        BufferedImage image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
        scene.draw(image.getGraphics(), 256, 256);
        try {
			ImageIO.write(image, "png", new File("/Users/yangyong/data/gisdata/test.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("-------------- over -------------");
	}

}
