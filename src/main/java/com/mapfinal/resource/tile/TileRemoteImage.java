package com.mapfinal.resource.tile;

import java.io.File;

import com.mapfinal.Mapfinal;
import com.mapfinal.event.EventKit;
import com.mapfinal.resource.image.ImageManager;
import com.mapfinal.resource.image.RemoteImage;

public class TileRemoteImage<M> extends RemoteImage<M> {

	public TileRemoteImage(String name, String url) {
		super(name, url);
		setFileExt("png");
	}

	@Override
	public String getFileName() {
		String objType = ImageManager.me().getCacheFolder();
		String objName = "default";
		if(this.collection!=null) {
			objType = this.collection.getType();
			objName = this.collection.getName();
		}
		String cpath = Mapfinal.me().getCacheFolder() + File.separator + objType + File.separator + objName;
		String[] names = this.getName().split("_");
		String cachePath = cpath;
		if(names.length > 3) {
			String z = names[1];
			String x = names[2];
			String y = names[3];
			cpath += File.separator + z;
			cachePath = cpath + File.separator + y + "_" + x;
		}
		
		File f = new File(cpath);
		if(!f.exists()) {
			f.mkdirs();
		}
		String fileName = cachePath + "." + getFileExt().toString();
		return fileName;
	}
	
	@Override
	public void setData(M image) {
		// TODO Auto-generated method stub
		super.setData(image);
		EventKit.sendEvent("redraw");
	}
}
