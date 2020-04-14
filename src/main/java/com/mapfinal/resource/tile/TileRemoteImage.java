package com.mapfinal.resource.tile;

import java.io.File;

import com.mapfinal.Mapfinal;
import com.mapfinal.resource.image.RemoteImage;

public class TileRemoteImage<M> extends RemoteImage<M> {
	
	private String tileResourceName;

	public TileRemoteImage(String name, String url, String tileResourceName, boolean renderOnCache) {
		super(name, url, renderOnCache);
		this.setTileResourceName(tileResourceName);
		setImageType(ImageType.png);
	}

	@Override
	public String getFileName() {
		String cpath = Mapfinal.me().getCacheFolder() + File.separator + "tile" + File.separator + tileResourceName;
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
		String fileName = cachePath + "." + getImageType().toString();
		return fileName;
	}
	
	public String getTileResourceName() {
		return tileResourceName;
	}

	public void setTileResourceName(String tileResourceName) {
		this.tileResourceName = tileResourceName;
	}
}
