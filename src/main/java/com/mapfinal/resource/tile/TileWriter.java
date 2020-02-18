package com.mapfinal.resource.tile;

import java.util.List;

import com.mapfinal.Mapfinal;
import com.mapfinal.event.Callback;
import com.mapfinal.event.Event;
import com.mapfinal.map.GeoElement;
import com.mapfinal.map.ImageFeature;
import com.mapfinal.resource.ImageCache;
import com.mapfinal.resource.ResourceWriter;

public class TileWriter implements ResourceWriter {

	private TileResourceObject resource;

	public TileWriter(TileResourceObject resource) {
		this.resource = resource;
	}

	@Override
	public void open() {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(List<GeoElement> elements) {
		// TODO Auto-generated method stub
		for (GeoElement geoElement : elements) {
			write(geoElement);
		}
	}

	@Override
	public void write(GeoElement element) {
		// TODO Auto-generated method stub
		if (element!=null && element instanceof ImageFeature) {
			if(resource.getCache()!=null && resource.getCache() instanceof ImageCache) {
				ImageCache cache = (ImageCache) resource.getCache();
				ImageFeature feature = (ImageFeature) element;
				resource.getCache().getAsync(resource.getUrl(), new Callback() {
					@Override
					public void execute(Event event) {
						// TODO Auto-generated method stub
						Object img = event.get("image");
						if(img!=null) {
							String filename = resource.imageFileName(feature.getImageName(), Mapfinal.me().getCacheFolder());
							cache.getService().writerToLocal(filename, img);
						}
					}
					@Override
					public void error(Event event) {
						// TODO Auto-generated method stub
					}
				});
			}
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	public TileResourceObject getResource() {
		return resource;
	}

	public void setResource(TileResourceObject resource) {
		this.resource = resource;
	}

}
