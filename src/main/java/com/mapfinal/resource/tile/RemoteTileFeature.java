package com.mapfinal.resource.tile;

import com.mapfinal.map.Tile;
import com.mapfinal.resource.image.RemoteImage;

public class RemoteTileFeature extends TileFeature {
	
	public RemoteTileFeature(String url, Tile tile) {
		super(url, tile, new RemoteImage<>(tile.getId(), url));
	}
}
