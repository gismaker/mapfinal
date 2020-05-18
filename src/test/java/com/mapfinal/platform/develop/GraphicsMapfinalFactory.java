package com.mapfinal.platform.develop;

import java.io.File;

import com.mapfinal.MapfinalFactory;
import com.mapfinal.geometry.ArrayMapCS;
import com.mapfinal.geometry.DoublePackedCS;
import com.mapfinal.geometry.FloatPackedCS;
import com.mapfinal.geometry.GeoKit;
import com.mapfinal.geometry.ListMapCS;
import com.mapfinal.geometry.MapCSFactory;
import com.mapfinal.platform.develop.graphics.BufferedImageHandle;
import com.mapfinal.processor.GeoCompress;
import com.mapfinal.render.RenderCompress;
import com.mapfinal.render.SimpleRenderCompress;
import com.mapfinal.resource.image.ImageHandle;

public class GraphicsMapfinalFactory extends MapfinalFactory {

	private RenderCompress renderCompress;
	private BufferedImageHandle imageHandle;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
	}
	
	@Override
	public void initGeometryFactory() {
		// TODO Auto-generated method stub
		//GeoKit.initGeometryFactory(new MapCSFactory(ListMapCS.class));
		GeoKit.initGeometryFactory(new MapCSFactory(FloatPackedCS.class));
	}
	
	@Override
	public RenderCompress getRenderCompress(GeoCompress.Type type) {
		// TODO Auto-generated method stub
		if(renderCompress==null) {
			renderCompress = new SimpleRenderCompress(type);
		}
		return renderCompress;
	}

	@Override
	public ImageHandle getImageHandle() {
		// TODO Auto-generated method stub
		if(imageHandle==null) {
			imageHandle = new BufferedImageHandle();
		}
		return imageHandle;
	}

	@Override
	public String getCacheFolder() {
		return System.getProperty("user.dir") + File.separator + "cache";
	}

}
